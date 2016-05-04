package se.moosechrunchers.appfrontend.api;

import org.json.JSONException;
import org.json.JSONObject;

public class Coordinate {
    public float Lat, Long;

    public static Coordinate fromJson(JSONObject json) throws JSONException {
        Coordinate coord = new Coordinate();

        coord.Lat = (float)json.getDouble("lat");
        coord.Long = (float)json.getDouble("long");

        return coord;
    }
}
