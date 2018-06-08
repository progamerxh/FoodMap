package android.foodmap;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PlacesJSONParser {
    /**
     * Receives a JSONObject and returns a list of lists containing latitude and longitude
     */
    public List<FavouritePlace> parse(JSONObject jObject) {
        List<FavouritePlace> placeList = new ArrayList<>();
        try {
            JSONArray jArray = jObject.getJSONArray("searchItems");
            for(int i=0; i < jArray.length(); i++) {

                JSONObject mjObject = jArray.getJSONObject(i);
                int ID = mjObject.getInt("Id");
                String Name = mjObject.getString("Name");
                double Latitude = mjObject.getDouble("Latitude");
                double Longitude = mjObject.getDouble("Longitude");
                FavouritePlace fp = new FavouritePlace(ID, Name, Latitude, Longitude);
                placeList.add(fp);
            } // End Loop

        } catch (JSONException e) {
            Log.e("JSONException", "Error: " + e.toString());
        } // catch (JSONException e)
        return placeList;
    } // protected void onPostExecute(Void v)

}
