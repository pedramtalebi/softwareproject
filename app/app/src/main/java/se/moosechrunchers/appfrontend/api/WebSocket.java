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
        void OnConnect();
        void OnDisconnect();

        void OnError(String error);
        void OnTimeout();

        void OnRerouteAdd(Reroute r);
        void OnRerouteChange(Reroute r);
        void OnRerouteRemove(String id);
    }

    private List<WebSocketListener> listeners;

    public WebSocket() {
        listeners = new ArrayList<WebSocketListener>();

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
                    listener.OnTimeout();
                }
            }
        });

        mSocket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                String error = "";
                for (Object arg : args) {
                    Log.e("moosecrunchers", arg.toString());
                    error += arg.toString();
                }

                for (WebSocketListener listener : listeners) {
                    listener.OnError(error);
                }
            }
        });

        mSocket.on(Constants.EVENT_NEW_REROUTE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Reroute r = Reroute.fromJson(args[0]);
                for(WebSocketListener listener : listeners) {
                    listener.OnRerouteAdd(r);
                }
            }
        });

        mSocket.on(Constants.EVENT_DEL_REROUTE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Reroute r = Reroute.fromJson(args[0]);
                for(WebSocketListener listener : listeners) {
                    listener.OnRerouteRemove(r.Id);
                }
            }
        });

        mSocket.on(Constants.EVENT_CHG_REROUTE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Reroute r = Reroute.fromJson(args[0]);
                for(WebSocketListener listener : listeners) {
                    listener.OnRerouteChange(r);
                }
            }
        });
    }

    /*
        Connect to the server
     */
    public void Connect() {
        mSocket.connect();

        for (WebSocketListener listener : listeners) {
            listener.OnConnect();
        }
    }

    /*
        Disconnect from the server
     */
    public void Disconnect() {
        mSocket.disconnect();

        for (WebSocketListener listener : listeners) {
            listener.OnDisconnect();
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
