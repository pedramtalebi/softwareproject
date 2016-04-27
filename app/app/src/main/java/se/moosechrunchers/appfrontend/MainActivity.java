package se.moosechrunchers.appfrontend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.net.URISyntaxException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {
    private final String BACKEND_URL = "http://192.168.99.1:3000";

    private TextView txtView;

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(BACKEND_URL);
        } catch (URISyntaxException e) {
            Log.e("moosecrunchers", e.getMessage());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        txtView = (TextView)findViewById(R.id.txtView);

        IO.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.e("moosecrunchers", "connection timed out!");
            }
        });
        mSocket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                for (int i = 0; i < args.length; i++) {
                    Log.e("moosecrunchers", args[i].toString());
                }
            }
        });

        // Send hello!
        mSocket.emit("hello", "wooot");

        mSocket.on("foo", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                txtView.setText("Count: " + args[0].toString());
            }
        });

        Log.i("moosecrunchers", "trying to connect..");
        mSocket.connect();

        setContentView(R.layout.activity_main);
    }
}
