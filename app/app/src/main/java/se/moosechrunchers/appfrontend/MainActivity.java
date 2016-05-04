package se.moosechrunchers.appfrontend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {

    private TextView txtView;

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
