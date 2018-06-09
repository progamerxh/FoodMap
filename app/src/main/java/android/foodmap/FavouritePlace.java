package android.foodmap;


public class FavouritePlace {
    public int ID;
    public String Name;
    public double Latitude;
    public double Longitude;
    public String MobilePicturePath;
    public String Address;
    public FavouritePlace(int ID, String name, double latitude, double longitude,String mobilePicturePath, String address) {
        this.ID = ID;
        Name = name;
        Latitude = latitude;
        Longitude = longitude;
        MobilePicturePath = mobilePicturePath;
        Address = address;
    }
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
