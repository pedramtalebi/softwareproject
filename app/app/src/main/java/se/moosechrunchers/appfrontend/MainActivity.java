package se.moosechrunchers.appfrontend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import se.moosechrunchers.appfrontend.api.Reroute;
import se.moosechrunchers.appfrontend.api.WebSocket;

public class MainActivity extends AppCompatActivity implements WebSocket.WebSocketListener {
    private final static String TAG = "moosecrunchers";
    WebSocket mSocket = new WebSocket();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSocket.addListener(this);
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
        Log.d(TAG, "New reroute added!");
    }

    public void OnRerouteChange(Reroute r) {
        Log.d(TAG, "Reroute " + r.Id + " changed!");
    }

    public void OnRerouteRemove(String id) {
        Log.d(TAG, "Reroute " + id + " removed");
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
