package se.moosechrunchers.appfrontend.api;

import org.json.JSONException;
import org.json.JSONObject;

public class Coordinate {
    public float latitude, longitude;

    public static Coordinate fromJson(JSONObject json) throws JSONException {
        Coordinate coord = new Coordinate();

        coord.latitude = (float)json.getDouble("lat");
        coord.longitude = (float)json.getDouble("long");

        return coord;
    }
}
