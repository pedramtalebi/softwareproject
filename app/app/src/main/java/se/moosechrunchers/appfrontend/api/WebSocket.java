package se.moosechrunchers.appfrontend.api;

import android.util.Log;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class WebSocket {
    private Socket mSocket;

    public interface WebSocketListener {
        void onConnect();
        void onDisconnect();

        void onError(String error);
        void onTimeout();

        void onRerouteAdd(Reroute r);
        void onRerouteChange(Reroute r);
        void onRerouteRemove(String id);
    }

    private List<WebSocketListener> listeners;

    public WebSocket() {
        listeners = new ArrayList<>();

        try {
            mSocket = IO.socket(Constants.BACKEND_URL);
        } catch (URISyntaxException e) {
            Log.e("moosecrunchers", e.getMessage());
        }

        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.e("moosecrunchers", "connection timed out!");

                for (WebSocketListener listener : listeners) {
                    listener.onTimeout();
                }
            }
        });

        mSocket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                StringBuilder buf = new StringBuilder();
                for (Object arg : args) {
                    Log.e("moosecrunchers", arg.toString());
                    buf.append(arg.toString());
                }

                String error = buf.toString();
                for (WebSocketListener listener : listeners) {
                    listener.onError(error);
                }
            }
        });

        mSocket.on(Constants.EVENT_NEW_REROUTE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Reroute r = Reroute.fromJson(args[0]);
                for(WebSocketListener listener : listeners) {
                    listener.onRerouteAdd(r);
                }
            }
        });

        mSocket.on(Constants.EVENT_DEL_REROUTE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                String id = args[0].toString();
                for(WebSocketListener listener : listeners) {
                    listener.onRerouteRemove(id);
                }
            }
        });

        mSocket.on(Constants.EVENT_CHG_REROUTE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Reroute r = Reroute.fromJson(args[0]);
                for(WebSocketListener listener : listeners) {
                    listener.onRerouteChange(r);
                }
            }
        });
    }

    /*
        connect to the server
     */
    public void connect() {
        mSocket.connect();

        for (WebSocketListener listener : listeners) {
            listener.onConnect();
        }
    }

    /*
        disconnect from the server
     */
    public void disconnect() {
        mSocket.disconnect();

        for (WebSocketListener listener : listeners) {
            listener.onDisconnect();
        }
    }

    /*
        Add the listener to the listeners (event-listeners)
     */
    public void addListener(WebSocketListener listener) {
        listeners.add(listener);
    }

    /*
        Removes the listener from the listeners (event-listeners)
     */
    public void removeListener(WebSocketListener listener) {
        listeners.remove(listener);
    }
}
