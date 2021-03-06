package se.moosechrunchers.appfrontend.api;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Reroute {
    public String Id;
    public String Name;
    public List<Coordinate> Coordinates;
    public List<Integer> AffectedLines;

    @Override
    public int hashCode() {
        return Id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Reroute)){
            return false;
        }

        if (obj == this) {
            return true;
        }

        Reroute r = (Reroute)obj;
        return r.Id.equals(this.Id);
    }

    public static Reroute fromJson(final Object arg) {
        Reroute reroute = new Reroute();

        try {
            JSONObject json = (JSONObject) arg;

            if (json.has("name")) {
                reroute.Name = json.getString("name");
            } else {
                reroute.Name = "undefined";
            }

            reroute.Id = json.getString("_id");

            JSONArray jsonCoordinates = json.getJSONArray("coordinates");
            reroute.Coordinates = new ArrayList<>();

            JSONObject jsonCoord;
            for(int i = 0; i < jsonCoordinates.length(); i++) {
                jsonCoord = jsonCoordinates.getJSONObject(i);
                reroute.Coordinates.add(Coordinate.fromJson(jsonCoord));
            }

            JSONArray jsonLines = json.getJSONArray("affectedLines");
            reroute.AffectedLines = new ArrayList<>();

            for(int i = 0; i < jsonLines.length(); i++) {
                int line = jsonLines.getInt(i);
                reroute.AffectedLines.add(line);
            }

        } catch (JSONException ex) {
            Log.e("Reroute", "could not parse json from server:" + ex.getMessage());
        }

        return reroute;
    }
}
