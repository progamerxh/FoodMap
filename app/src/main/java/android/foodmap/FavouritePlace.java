package android.foodmap;


public class FavouritePlace {
    public int ID;
    public String Name;
    public double Latitude;
    public double Longitude;

    public FavouritePlace(int ID, String name, double latitude, double longitude) {
        this.ID = ID;
        Name = name;
        Latitude = latitude;
        Longitude = longitude;
    }
    public FavouritePlace( String name, double latitude, double longitude) {
        Name = name;
        Latitude = latitude;
        Longitude = longitude;
    }
}
