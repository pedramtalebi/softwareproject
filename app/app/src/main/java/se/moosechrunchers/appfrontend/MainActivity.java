package se.moosechrunchers.appfrontend;

import android.content.res.Resources;
import android.graphics.Color;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import se.moosechrunchers.appfrontend.api.Coordinate;
import se.moosechrunchers.appfrontend.api.Reroute;
import se.moosechrunchers.appfrontend.api.WebSocket;

public class MainActivity extends AppCompatActivity implements WebSocket.WebSocketListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private final static String TAG = "moosecrunchers";

    private WebSocket mSocket = new WebSocket();
    private GoogleMap mMap = null;

    private Semaphore mRoutesMutex = new Semaphore(1);
    private List<Reroute> mActiveReroutes = null;
    private int currentRerouteIndex = -1;

    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation = null;
    private Circle mCurrentLocationCircle = null;
    private boolean mLocationEnabled = false;

    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lockActiveReroutes();
        mActiveReroutes = new ArrayList<>();
        unlockActiveReroutes();

        mSocket.addListener(this);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    protected void onDestroy() {
        mSocket.removeListener(this);
        super.onDestroy();
    }

    public void onConnect() {
        Log.i(TAG, "Connected to server");
    }

    public void onDisconnect() {
        Log.i(TAG, "Disconnected from server");
    }

    public void onError(String error) {
        Log.e(TAG, "Error occurred: " + error);
    }

    public void onTimeout() {
        Log.e(TAG, "Timeout while connecting to server!");
    }

    public void onRerouteAdd(Reroute r) {
        if (mMap == null) {
            Log.e(TAG, "Could not get gmap!");
            return;
        }

        Log.d(TAG, "New reroute added!");

        lockActiveReroutes();
        mActiveReroutes.add(r);
        unlockActiveReroutes();

        updateNumberStatus();

        if (currentRerouteIndex == -1) {
            currentRerouteIndex = 0;
            showReroute(r);
        }
    }

    public void onRerouteChange(Reroute r) {
        lockActiveReroutes();

        int index = mActiveReroutes.indexOf(r);
        if (index < 0) { // Not found
            Log.e(TAG, "Could not change an non-existing reroute ..");

            unlockActiveReroutes();
            return;
        }

        Reroute existingReroute = mActiveReroutes.get(index);
        existingReroute.AffectedLines = r.AffectedLines;
        existingReroute.Coordinates = r.Coordinates;

        unlockActiveReroutes();

        Log.d(TAG, "Reroute " + r.Id + " changed!");
    }

    public void onRerouteRemove(String id) {
        Reroute r = new Reroute();
        r.Id = id;

        lockActiveReroutes();
        int index = mActiveReroutes.indexOf(r);
        if (index < 0) { // Not found
            Log.e(TAG, "Could not remove an non-existing reroute ..");
            unlockActiveReroutes();
            return;
        }

        mActiveReroutes.remove(index);

        if (index == currentRerouteIndex) {
            currentRerouteIndex++;
            if (currentRerouteIndex >= mActiveReroutes.size()) {
                currentRerouteIndex = 0;
            }

            try {
                Reroute nextReroute = mActiveReroutes.get(currentRerouteIndex);
                showReroute(nextReroute);
            } catch (Resources.NotFoundException ex) {
                // Ignore this case ..
                mMap.clear();
            }
        }

        updateNumberStatus();

        unlockActiveReroutes();

        Log.d(TAG, "Reroute " + id + " removed");
    }

    private void showReroute(final Reroute r) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMap.clear();

                drawCurrentLocation();

                // Collect latitude-longitude points from coordinates
                List<LatLng> coords = new ArrayList<>();
                for (Coordinate coord : r.Coordinates) {
                    coords.add(new LatLng(coord.latitude, coord.longitude));
                }

                // Draw the polygon line
                PolylineOptions opts = new PolylineOptions()
                        .addAll(coords)
                        .width(10)
                        .color(Color.RED);
                mMap.addPolyline(opts);

                // Zoom in the camera to include the bounds
                LatLngBounds.Builder mapBoundsBuilder = LatLngBounds.builder();
                for (LatLng lt : coords) {
                    mapBoundsBuilder.include(lt);
                }
                LatLngBounds mapBounds = mapBoundsBuilder.build();
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(mapBounds, 50));
            }
        });
    }

    private void drawCurrentLocation() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mLocationEnabled && mCurrentLocation != null) {
                    if (mCurrentLocationCircle != null) {
                        mCurrentLocationCircle.remove();
                    }

                    CircleOptions opts = new CircleOptions()
                            .center(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()))
                            .radius(20)
                            .strokeWidth(0)
                            .fillColor(Color.BLUE);

                    mCurrentLocationCircle = mMap.addCircle(opts);
                }
            }
        });
    }

    private void lockActiveReroutes() {
        try {
            mRoutesMutex.acquire();
        } catch (InterruptedException ex) {
            Log.e(TAG, "could not acquire activeReroute semaphore: " + ex.getMessage());
            throw new RuntimeException("could not acquire semaphore!");
        }
    }

    private void unlockActiveReroutes() {
        mRoutesMutex.release();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        mMap.getUiSettings().setAllGesturesEnabled(false);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mSocket.disconnect();

        // Disconnecting the client invalidates it.
        mGoogleApiClient.disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mSocket.connect();

        mGoogleApiClient.connect();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_next: {
                lockActiveReroutes();

                int upperLimit = mActiveReroutes.size();
                currentRerouteIndex++;

                if (currentRerouteIndex >= upperLimit) {
                    currentRerouteIndex = 0;
                }

                Reroute currentReroute = mActiveReroutes.get(currentRerouteIndex);
                showReroute(currentReroute);

                unlockActiveReroutes();
            }
            break;
            case R.id.action_prev: {
                lockActiveReroutes();

                int lowerLimit = -1;
                currentRerouteIndex--;

                if (currentRerouteIndex <= lowerLimit) {
                    currentRerouteIndex = mActiveReroutes.size() - 1;
                }

                Reroute currentReroute = mActiveReroutes.get(currentRerouteIndex);
                showReroute(currentReroute);

                unlockActiveReroutes();
            }
            break;
            default:
                break;
        }
        updateNumberStatus();

        return true;
    }

    private void updateNumberStatus() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mMenu != null) {
                    MenuItem status = mMenu.getItem(1);
                    status.setTitle((currentRerouteIndex+1) + "/" + mActiveReroutes.size());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        mMenu = menu;

        return true;
    }

    @Override
    public void onConnected(Bundle bundle) {
        LocationRequest mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(15 * 1000); // Update location every second

        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
            mLocationEnabled = true;
        } catch (SecurityException ex) {
            mLocationEnabled = false;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "GoogleApiClient connection has been suspend");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(TAG, "GoogleApiClient connection has failed");
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;

        drawCurrentLocation();
    }
}
