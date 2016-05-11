package se.moosechrunchers.appfrontend;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import se.moosechrunchers.appfrontend.api.Coordinate;
import se.moosechrunchers.appfrontend.api.Reroute;
import se.moosechrunchers.appfrontend.api.WebSocket;

public class MainActivity extends AppCompatActivity implements WebSocket.WebSocketListener, OnMapReadyCallback {
    private final static String TAG = "moosecrunchers";
    WebSocket mSocket = new WebSocket();
    GoogleMap mMap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSocket.addListener(this);

        MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
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

        List<LatLng> coords = new ArrayList<>();
        for(Coordinate coord : r.Coordinates) {
            coords.add(new LatLng(coord.Lat, coord.Long));
        }

        PolylineOptions opts = new PolylineOptions()
                .addAll(coords)
                .width(2)
                .color(Color.GREEN);
        mMap.addPolyline(opts);

        Log.d(TAG, "New reroute added!");
    }

    public void OnRerouteChange(Reroute r) {
        Log.d(TAG, "Reroute " + r.Id + " changed!");
    }

    public void OnRerouteRemove(String id) {
        Log.d(TAG, "Reroute " + id + " removed");
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        JSONObject json = null;

        try {
            json = new JSONObject("{\"_id\":\"5732fc927df2d2e9cee580c5\",\"id\":\"f3ea93ba-1514-49ef-a1f6-8c9f13435fb9\",\"created_at\":\"2016-05-11T09:34:10.344Z\",\"updated_at\":\"2016-05-11T09:34:10.344Z\",\"__v\":0,\"affectedLines\":[17],\"coordinates\":[{\"lat\":57.71014,\"long\":11.970170000000001,\"_id\":\"5732fc927df2d2e9cee58141\"},{\"lat\":57.71020000000001,\"long\":11.970030000000001,\"_id\":\"5732fc927df2d2e9cee58140\"},{\"lat\":57.71032,\"long\":11.96987,\"_id\":\"5732fc927df2d2e9cee5813f\"},{\"lat\":57.71047000000001,\"long\":11.96972,\"_id\":\"5732fc927df2d2e9cee5813e\"},{\"lat\":57.710550000000005,\"long\":11.969660000000001,\"_id\":\"5732fc927df2d2e9cee5813d\"},{\"lat\":57.710660000000004,\"long\":11.969610000000001,\"_id\":\"5732fc927df2d2e9cee5813c\"},{\"lat\":57.710730000000005,\"long\":11.969610000000001,\"_id\":\"5732fc927df2d2e9cee5813b\"},{\"lat\":57.71094000000001,\"long\":11.96967,\"_id\":\"5732fc927df2d2e9cee5813a\"},{\"lat\":57.711510000000004,\"long\":11.969830000000002,\"_id\":\"5732fc927df2d2e9cee58139\"},{\"lat\":57.71182,\"long\":11.969840000000001,\"_id\":\"5732fc927df2d2e9cee58138\"},{\"lat\":57.712,\"long\":11.969830000000002,\"_id\":\"5732fc927df2d2e9cee58137\"},{\"lat\":57.71222,\"long\":11.96977,\"_id\":\"5732fc927df2d2e9cee58136\"},{\"lat\":57.712320000000005,\"long\":11.969740000000002,\"_id\":\"5732fc927df2d2e9cee58135\"},{\"lat\":57.71262000000001,\"long\":11.969570000000001,\"_id\":\"5732fc927df2d2e9cee58134\"},{\"lat\":57.71296,\"long\":11.969320000000002,\"_id\":\"5732fc927df2d2e9cee58133\"},{\"lat\":57.713060000000006,\"long\":11.9692,\"_id\":\"5732fc927df2d2e9cee58132\"},{\"lat\":57.713300000000004,\"long\":11.96887,\"_id\":\"5732fc927df2d2e9cee58131\"},{\"lat\":57.71354,\"long\":11.968530000000001,\"_id\":\"5732fc927df2d2e9cee58130\"},{\"lat\":57.713730000000005,\"long\":11.96826,\"_id\":\"5732fc927df2d2e9cee5812f\"},{\"lat\":57.71468,\"long\":11.966890000000001,\"_id\":\"5732fc927df2d2e9cee5812e\"},{\"lat\":57.71585,\"long\":11.965230000000002,\"_id\":\"5732fc927df2d2e9cee5812d\"},{\"lat\":57.716010000000004,\"long\":11.96503,\"_id\":\"5732fc927df2d2e9cee5812c\"},{\"lat\":57.716210000000004,\"long\":11.96484,\"_id\":\"5732fc927df2d2e9cee5812b\"},{\"lat\":57.71654,\"long\":11.964620000000002,\"_id\":\"5732fc927df2d2e9cee5812a\"},{\"lat\":57.717220000000005,\"long\":11.964260000000001,\"_id\":\"5732fc927df2d2e9cee58129\"},{\"lat\":57.71746,\"long\":11.96414,\"_id\":\"5732fc927df2d2e9cee58128\"},{\"lat\":57.717800000000004,\"long\":11.964,\"_id\":\"5732fc927df2d2e9cee58127\"},{\"lat\":57.718920000000004,\"long\":11.96349,\"_id\":\"5732fc927df2d2e9cee58126\"},{\"lat\":57.719150000000006,\"long\":11.96338,\"_id\":\"5732fc927df2d2e9cee58125\"},{\"lat\":57.719390000000004,\"long\":11.963180000000001,\"_id\":\"5732fc927df2d2e9cee58124\"},{\"lat\":57.71958000000001,\"long\":11.962980000000002,\"_id\":\"5732fc927df2d2e9cee58123\"},{\"lat\":57.719860000000004,\"long\":11.962530000000001,\"_id\":\"5732fc927df2d2e9cee58122\"},{\"lat\":57.72008,\"long\":11.96208,\"_id\":\"5732fc927df2d2e9cee58121\"},{\"lat\":57.720290000000006,\"long\":11.96151,\"_id\":\"5732fc927df2d2e9cee58120\"},{\"lat\":57.720420000000004,\"long\":11.9609,\"_id\":\"5732fc927df2d2e9cee5811f\"},{\"lat\":57.72043000000001,\"long\":11.960840000000001,\"_id\":\"5732fc927df2d2e9cee5811e\"},{\"lat\":57.720510000000004,\"long\":11.959560000000002,\"_id\":\"5732fc927df2d2e9cee5811d\"},{\"lat\":57.72054000000001,\"long\":11.9594,\"_id\":\"5732fc927df2d2e9cee5811c\"},{\"lat\":57.720530000000004,\"long\":11.95846,\"_id\":\"5732fc927df2d2e9cee5811b\"},{\"lat\":57.72054000000001,\"long\":11.95766,\"_id\":\"5732fc927df2d2e9cee5811a\"},{\"lat\":57.72057,\"long\":11.95667,\"_id\":\"5732fc927df2d2e9cee58119\"},{\"lat\":57.72055,\"long\":11.956280000000001,\"_id\":\"5732fc927df2d2e9cee58118\"},{\"lat\":57.720510000000004,\"long\":11.956050000000001,\"_id\":\"5732fc927df2d2e9cee58117\"},{\"lat\":57.72044,\"long\":11.95589,\"_id\":\"5732fc927df2d2e9cee58116\"},{\"lat\":57.720380000000006,\"long\":11.955820000000001,\"_id\":\"5732fc927df2d2e9cee58115\"},{\"lat\":57.72037,\"long\":11.95569,\"_id\":\"5732fc927df2d2e9cee58114\"},{\"lat\":57.72037,\"long\":11.955660000000002,\"_id\":\"5732fc927df2d2e9cee58113\"},{\"lat\":57.72035,\"long\":11.95541,\"_id\":\"5732fc927df2d2e9cee58112\"},{\"lat\":57.720330000000004,\"long\":11.955050000000002,\"_id\":\"5732fc927df2d2e9cee58111\"},{\"lat\":57.72028,\"long\":11.953640000000002,\"_id\":\"5732fc927df2d2e9cee58110\"},{\"lat\":57.72017,\"long\":11.953640000000002,\"_id\":\"5732fc927df2d2e9cee5810f\"},{\"lat\":57.71994000000001,\"long\":11.953520000000001,\"_id\":\"5732fc927df2d2e9cee5810e\"},{\"lat\":57.71985000000001,\"long\":11.95348,\"_id\":\"5732fc927df2d2e9cee5810d\"},{\"lat\":57.71979,\"long\":11.953460000000002,\"_id\":\"5732fc927df2d2e9cee5810c\"},{\"lat\":57.719750000000005,\"long\":11.953460000000002,\"_id\":\"5732fc927df2d2e9cee5810b\"},{\"lat\":57.719710000000006,\"long\":11.95348,\"_id\":\"5732fc927df2d2e9cee5810a\"},{\"lat\":57.71963,\"long\":11.953600000000002,\"_id\":\"5732fc927df2d2e9cee58109\"},{\"lat\":57.71941,\"long\":11.954160000000002,\"_id\":\"5732fc927df2d2e9cee58108\"},{\"lat\":57.71936,\"long\":11.954210000000002,\"_id\":\"5732fc927df2d2e9cee58107\"},{\"lat\":57.71929000000001,\"long\":11.954220000000001,\"_id\":\"5732fc927df2d2e9cee58106\"},{\"lat\":57.719100000000005,\"long\":11.954170000000001,\"_id\":\"5732fc927df2d2e9cee58105\"},{\"lat\":57.71884000000001,\"long\":11.953890000000001,\"_id\":\"5732fc927df2d2e9cee58104\"},{\"lat\":57.718450000000004,\"long\":11.95339,\"_id\":\"5732fc927df2d2e9cee58103\"},{\"lat\":57.718,\"long\":11.952660000000002,\"_id\":\"5732fc927df2d2e9cee58102\"},{\"lat\":57.716390000000004,\"long\":11.949760000000001,\"_id\":\"5732fc927df2d2e9cee58101\"},{\"lat\":57.71495,\"long\":11.94721,\"_id\":\"5732fc927df2d2e9cee58100\"},{\"lat\":57.71455,\"long\":11.946530000000001,\"_id\":\"5732fc927df2d2e9cee580ff\"},{\"lat\":57.71452000000001,\"long\":11.94635,\"_id\":\"5732fc927df2d2e9cee580fe\"},{\"lat\":57.71428,\"long\":11.945810000000002,\"_id\":\"5732fc927df2d2e9cee580fd\"},{\"lat\":57.71408,\"long\":11.945290000000002,\"_id\":\"5732fc927df2d2e9cee580fc\"},{\"lat\":57.71405000000001,\"long\":11.94486,\"_id\":\"5732fc927df2d2e9cee580fb\"},{\"lat\":57.71408,\"long\":11.944830000000001,\"_id\":\"5732fc927df2d2e9cee580fa\"},{\"lat\":57.714110000000005,\"long\":11.944790000000001,\"_id\":\"5732fc927df2d2e9cee580f9\"},{\"lat\":57.714150000000004,\"long\":11.94467,\"_id\":\"5732fc927df2d2e9cee580f8\"},{\"lat\":57.71414000000001,\"long\":11.944540000000002,\"_id\":\"5732fc927df2d2e9cee580f7\"},{\"lat\":57.7141,\"long\":11.944410000000001,\"_id\":\"5732fc927df2d2e9cee580f6\"},{\"lat\":57.714040000000004,\"long\":11.944350000000002,\"_id\":\"5732fc927df2d2e9cee580f5\"},{\"lat\":57.71396000000001,\"long\":11.944360000000001,\"_id\":\"5732fc927df2d2e9cee580f4\"},{\"lat\":57.713950000000004,\"long\":11.944370000000001,\"_id\":\"5732fc927df2d2e9cee580f3\"},{\"lat\":57.71383,\"long\":11.94425,\"_id\":\"5732fc927df2d2e9cee580f2\"},{\"lat\":57.71354,\"long\":11.94378,\"_id\":\"5732fc927df2d2e9cee580f1\"},{\"lat\":57.71334,\"long\":11.943520000000001,\"_id\":\"5732fc927df2d2e9cee580f0\"},{\"lat\":57.713240000000006,\"long\":11.943460000000002,\"_id\":\"5732fc927df2d2e9cee580ef\"},{\"lat\":57.71293000000001,\"long\":11.943430000000001,\"_id\":\"5732fc927df2d2e9cee580ee\"},{\"lat\":57.71238,\"long\":11.943470000000001,\"_id\":\"5732fc927df2d2e9cee580ed\"},{\"lat\":57.712140000000005,\"long\":11.9434,\"_id\":\"5732fc927df2d2e9cee580ec\"},{\"lat\":57.711920000000006,\"long\":11.943090000000002,\"_id\":\"5732fc927df2d2e9cee580eb\"},{\"lat\":57.711710000000004,\"long\":11.942680000000001,\"_id\":\"5732fc927df2d2e9cee580ea\"},{\"lat\":57.711650000000006,\"long\":11.9425,\"_id\":\"5732fc927df2d2e9cee580e9\"},{\"lat\":57.711580000000005,\"long\":11.942380000000002,\"_id\":\"5732fc927df2d2e9cee580e8\"},{\"lat\":57.71150000000001,\"long\":11.942240000000002,\"_id\":\"5732fc927df2d2e9cee580e7\"},{\"lat\":57.711270000000006,\"long\":11.941930000000001,\"_id\":\"5732fc927df2d2e9cee580e6\"},{\"lat\":57.71116000000001,\"long\":11.94172,\"_id\":\"5732fc927df2d2e9cee580e5\"},{\"lat\":57.71107000000001,\"long\":11.94147,\"_id\":\"5732fc927df2d2e9cee580e4\"},{\"lat\":57.71094000000001,\"long\":11.940930000000002,\"_id\":\"5732fc927df2d2e9cee580e3\"},{\"lat\":57.71069000000001,\"long\":11.93973,\"_id\":\"5732fc927df2d2e9cee580e2\"},{\"lat\":57.710530000000006,\"long\":11.939250000000001,\"_id\":\"5732fc927df2d2e9cee580e1\"},{\"lat\":57.708890000000004,\"long\":11.936380000000002,\"_id\":\"5732fc927df2d2e9cee580e0\"},{\"lat\":57.70826,\"long\":11.935270000000001,\"_id\":\"5732fc927df2d2e9cee580df\"},{\"lat\":57.70817,\"long\":11.935070000000001,\"_id\":\"5732fc927df2d2e9cee580de\"},{\"lat\":57.708040000000004,\"long\":11.934880000000001,\"_id\":\"5732fc927df2d2e9cee580dd\"},{\"lat\":57.70788,\"long\":11.934750000000001,\"_id\":\"5732fc927df2d2e9cee580dc\"},{\"lat\":57.70783,\"long\":11.934550000000002,\"_id\":\"5732fc927df2d2e9cee580db\"},{\"lat\":57.70779,\"long\":11.93449,\"_id\":\"5732fc927df2d2e9cee580da\"},{\"lat\":57.70774000000001,\"long\":11.934460000000001,\"_id\":\"5732fc927df2d2e9cee580d9\"},{\"lat\":57.7077,\"long\":11.934460000000001,\"_id\":\"5732fc927df2d2e9cee580d8\"},{\"lat\":57.70763,\"long\":11.93448,\"_id\":\"5732fc927df2d2e9cee580d7\"},{\"lat\":57.707550000000005,\"long\":11.934610000000001,\"_id\":\"5732fc927df2d2e9cee580d6\"},{\"lat\":57.707530000000006,\"long\":11.934830000000002,\"_id\":\"5732fc927df2d2e9cee580d5\"},{\"lat\":57.707550000000005,\"long\":11.93495,\"_id\":\"5732fc927df2d2e9cee580d4\"},{\"lat\":57.707600000000006,\"long\":11.93505,\"_id\":\"5732fc927df2d2e9cee580d3\"},{\"lat\":57.70763,\"long\":11.9364,\"_id\":\"5732fc927df2d2e9cee580d2\"},{\"lat\":57.70765000000001,\"long\":11.937470000000001,\"_id\":\"5732fc927df2d2e9cee580d1\"},{\"lat\":57.70767000000001,\"long\":11.937700000000001,\"_id\":\"5732fc927df2d2e9cee580d0\"},{\"lat\":57.7077,\"long\":11.937850000000001,\"_id\":\"5732fc927df2d2e9cee580cf\"},{\"lat\":57.70778000000001,\"long\":11.937970000000002,\"_id\":\"5732fc927df2d2e9cee580ce\"},{\"lat\":57.70814000000001,\"long\":11.938590000000001,\"_id\":\"5732fc927df2d2e9cee580cd\"},{\"lat\":57.70832000000001,\"long\":11.938880000000001,\"_id\":\"5732fc927df2d2e9cee580cc\"},{\"lat\":57.70871,\"long\":11.939580000000001,\"_id\":\"5732fc927df2d2e9cee580cb\"},{\"lat\":57.708650000000006,\"long\":11.939670000000001,\"_id\":\"5732fc927df2d2e9cee580ca\"},{\"lat\":57.70859000000001,\"long\":11.939750000000002,\"_id\":\"5732fc927df2d2e9cee580c9\"},{\"lat\":57.70816000000001,\"long\":11.94012,\"_id\":\"5732fc927df2d2e9cee580c8\"},{\"lat\":57.70754,\"long\":11.940690000000002,\"_id\":\"5732fc927df2d2e9cee580c7\"},{\"lat\":57.707530000000006,\"long\":11.940700000000001,\"_id\":\"5732fc927df2d2e9cee580c6\"}]}");
        } catch(JSONException ex) {
            Log.e(TAG, ex.getMessage());
        }

        Reroute r = Reroute.fromJson(json);

        List<LatLng> coords = new ArrayList<>();
        for(Coordinate coord : r.Coordinates) {
            coords.add(new LatLng(coord.Lat, coord.Long));
        }

        PolylineOptions opts = new PolylineOptions()
                .addAll(coords)
                .width(20)
                .color(Color.RED);
        mMap.addPolyline(opts);
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
