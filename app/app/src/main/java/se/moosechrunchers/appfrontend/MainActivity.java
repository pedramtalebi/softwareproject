package se.moosechrunchers.appfrontend;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import se.moosechrunchers.appfrontend.api.Coordinate;
import se.moosechrunchers.appfrontend.api.Reroute;
import se.moosechrunchers.appfrontend.api.WebSocket;

public class MainActivity extends AppCompatActivity implements WebSocket.WebSocketListener, OnMapReadyCallback {
    private final static String TAG = "moosecrunchers";

    private WebSocket mSocket = new WebSocket();
    private GoogleMap mMap = null;

    private Semaphore mRoutesMutex = new Semaphore(1);
    private List<Reroute> mActiveReroutes = null;

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
    }

    @Override
    protected void onDestroy() {
        mSocket.removeListener(this);
        super.onDestroy();
    }

    public void OnConnect() {
        Log.i(TAG, "Connected to server");
    }

    public void OnDisconnect() {
        Log.i(TAG, "Disconnected from server");
    }

    public void OnError(String error) {
        Log.e(TAG, "Error occurred: " + error);
    }

    public void OnTimeout() {
        Log.e(TAG, "Timeout while connecting to server!");
    }

    public void OnRerouteAdd(Reroute r) {
        if (mMap == null) {
            Log.e(TAG, "Could not get gmap!");
            return;
        }

        Log.d(TAG, "New reroute added!");

        lockActiveReroutes();
        mActiveReroutes.add(r);
        unlockActiveReroutes();

        showReroute(r);
    }

    public void OnRerouteChange(Reroute r) {
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

    public void OnRerouteRemove(String id) {
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
        unlockActiveReroutes();

        Log.d(TAG, "Reroute " + id + " removed");
    }

    private void showReroute(final Reroute r) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Collect latitude-longitude points from coordinates
                List<LatLng> coords = new ArrayList<>();
                for (Coordinate coord : r.Coordinates) {
                    coords.add(new LatLng(coord.Lat, coord.Long));
                }

                // Draw the polygon line
                PolylineOptions opts = new PolylineOptions()
                        .addAll(coords)
                        .width(15)
                        .color(Color.GREEN);
                mMap.addPolyline(opts);

                // Zoom in the camera to include the bounds
                LatLngBounds.Builder mapBoundsBuilder = LatLngBounds.builder();
                for (LatLng lt : coords) {
                    mapBoundsBuilder.include(lt);
                }
                LatLngBounds mapBounds = mapBoundsBuilder.build();
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(mapBounds, 20));
            }
        });
    }

    private void lockActiveReroutes() {
        try {
            mRoutesMutex.acquire();
        } catch (InterruptedException ex) {
            Log.e(TAG, "could not acquire activeReroute semaphore: " + ex.getMessage());
            System.exit(1);
        }
    }

    private void unlockActiveReroutes() {
        mRoutesMutex.release();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
    }

    @Override
    protected void onPause() {
        super.onPause();

        mSocket.Disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mSocket.Connect();
    }
}
