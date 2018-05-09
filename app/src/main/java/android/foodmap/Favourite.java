package android.foodmap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

/**
 * Created by HP on 5/5/2018.
 */

public class Favourite extends AppCompatActivity {
    Database database;
    ListView lvFavourite;
    ArrayList<FavouritePlace> coordinates;
    PlaceAdapter adapter;

    private Marker mPerth;
    private Marker mSydney;
    private Marker mBrisbane;

    int REQUEST_CODE=123;
    public static String LatCoor ="latCoor";
    public static String LngCoor ="lngCoor";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_favourite);

        //Tạo database:
        database = new Database(this, "Favourite-Places", null, 1);
        //database.QueryData("INSERT INTO PlaceCoordinates VALUES(null, 'thu', '234','22')");
        //Tạo bảng Place-Coordinates:
        database.QueryData("CREATE TABLE IF NOT EXISTS PlaceCoordinates(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " Name VARCHAR(100)," +
                " Latitude DOUBLE," +
                " Longitude DOUBLE)");
        //đổ dữ liệu vào listview.
        coordinates = new ArrayList<>();
        lvFavourite = (ListView) findViewById(R.id.lvFavourite);
//        adapter = new PlaceAdapter(this, R.layout.row_place, coordinates);
        lvFavourite.setAdapter(adapter);

        Intent intent=getIntent();
        String name,check;
        double kinhdo,vido;

        check=intent.getStringExtra("check");

        if (check.equals("true")){
            name=intent.getStringExtra("name");
            kinhdo=intent.getDoubleExtra("insertlatCoor",0);
            vido=intent.getDoubleExtra("insertlngCoor",0);
            database.QueryData("INSERT INTO PlaceCoordinates VALUES(null, '" + name + "', '" + kinhdo + "','" + vido + "')");
            getDataFavouritePlace();
        }
        ///////////////

        lvFavourite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Favourite.this,MapsActivity.class);
                intent.putExtra(LatCoor, coordinates.get(i).Latitude);
                intent.putExtra(LngCoor, coordinates.get(i).Longitude);
                intent.putExtra("Name",coordinates.get(i).Name);
                startActivity(intent);
            }
        });


        getDataFavouritePlace();
    }

    public void DialogDelete(String name, final int id){
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(this);
        dialogDelete.setMessage("Bạn có muốn xóa " + name +" không?");
        dialogDelete.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.QueryData("DELETE FROM PlaceCoordinates WHERE ID='"+ id +"'");
                Toast.makeText(Favourite.this, "Đã xóa", Toast.LENGTH_SHORT).show();
                getDataFavouritePlace();
            }
        });
        dialogDelete.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogDelete.show();
    }
    public void DialogEdit(String name, final int id){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView((R.layout.dialog_edit));

        final EditText edtNewName = (EditText) dialog.findViewById(R.id.edtNewName);
        Button btnOK = (Button) dialog.findViewById(R.id.btnOK);
        Button btnHuy = (Button) dialog.findViewById(R.id.btnHuy);

        edtNewName.setText(name);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Favourite.this,"thanh công",Toast.LENGTH_LONG).show();
                String newName = edtNewName.getText().toString().trim();
                database.QueryData("UPDATE PlaceCoordinates SET Name ='"+ newName +"' WHERE ID = '"+id+"'");

                dialog.dismiss();
                getDataFavouritePlace();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void getDataFavouritePlace() {
        //select data
        Cursor dataPlace = database.GetData("SELECT * FROM PlaceCoordinates");
        coordinates.clear();
        while (dataPlace.moveToNext()) {
            String Name = dataPlace.getString(1);
            int id = dataPlace.getInt(0);
            double lat = dataPlace.getDouble(2);
            double lng = dataPlace.getDouble(3);
            coordinates.add(new FavouritePlace(id,Name, lat, lng));
        }
        adapter.notifyDataSetChanged();
    }
}
