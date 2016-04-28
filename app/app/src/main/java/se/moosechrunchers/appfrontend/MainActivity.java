package se.moosechrunchers.appfrontend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.URISyntaxException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {

    private TextView txtView;

    private Socket mSocket;
    {
        try {
            String BACKEND_URL = "http://192.168.99.1:3000";
            mSocket = IO.socket(BACKEND_URL);
        } catch (URISyntaxException e) {
            Log.e("moosecrunchers", e.getMessage());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtView = (TextView)findViewById(R.id.txtView);
        Button btnIncrease = (Button)findViewById(R.id.btnIncrease);

        assert btnIncrease != null;
        btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSocket != null) {
                    mSocket.emit("update request", "");
                }
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
                for (Object arg : args) {
                    Log.e("moosecrunchers", arg.toString());
                }
            }
        });

        mSocket.on("updated counter", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                txtView = (TextView) findViewById(R.id.txtView);
                if (txtView == null) {
                    Log.e("moosecrunchers", "Could not find txtView!");
                    return;
                }

                if (args.length < 1) {
                    setText("Error occurred, wrong argument from foo");
                    return;
                }

                int counter = (int) args[0];
                setText("Counter: " + counter);
            }
        });

        Log.i("moosecrunchers", "trying to connect..");
        mSocket.connect();
    }

    protected void setText(String str) {
        if (txtView == null) {
            txtView = (TextView) findViewById(R.id.txtView);
        }

        final String text = str;
        txtView.post(new Runnable() {
            @Override
            public void run() {
                txtView.setText(text);
            }
        });
    }
}
