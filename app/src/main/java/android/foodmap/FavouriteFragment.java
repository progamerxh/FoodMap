package android.foodmap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FavouriteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavouriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavouriteFragment extends Fragment {

    private MapsActivity main;
    static Context context;
    private OnFragmentInteractionListener mListener;
    static Database database;
    ListView lvFavourite;
    static ArrayList<FavouritePlace> coordinates;
    static PlaceAdapter adapter;
    LinearLayout layout_favourite;

    // TODO: Rename and change types and number of parameters
    public static FavouriteFragment newInstance() {
        FavouriteFragment fragment = new FavouriteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity(); // use this reference to invoke main callbacks

        main = (MapsActivity) getActivity();
        database = new Database(context, "Favourite-Places", null, 1);
        //database.QueryData("INSERT INTO PlaceCoordinates VALUES(null, 'thu', '234','22')");
        //Tạo bảng Place-Coordinates:
        database.QueryData("CREATE TABLE IF NOT EXISTS PlaceCoordinates(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " Name VARCHAR(100)," +
                " Latitude DOUBLE," +
                " Longitude DOUBLE)");
        //đổ dữ liệu vào listview.
        coordinates = new ArrayList<>();

        adapter = new PlaceAdapter(main, R.layout.row_place, coordinates);


        getDataFavouritePlace();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout_favourite = (LinearLayout) inflater.inflate(R.layout.fragment_favourite, null);
        lvFavourite = (ListView) layout_favourite.findViewById(R.id.lvFavourite);
        lvFavourite.setAdapter(adapter);
        lvFavourite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                main.onMsgFromFragToMain("PLACE", coordinates.get(position));
            }
        });
        return layout_favourite;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    static void getDataFavouritePlace() {
        //select data
        Cursor dataPlace = database.GetData("SELECT * FROM PlaceCoordinates");
        coordinates.clear();
        while (dataPlace.moveToNext()) {
            String Name = dataPlace.getString(1);
            int id = dataPlace.getInt(0);
            double lat = dataPlace.getDouble(2);
            double lng = dataPlace.getDouble(3);
            coordinates.add(new FavouritePlace(id, Name, lat, lng));
        }
        adapter.notifyDataSetChanged();
    }

    public void onMsgFromMainToFragment(FavouritePlace favouritePlace) {
        database.QueryData("INSERT INTO PlaceCoordinates VALUES(null, '"
                + favouritePlace.Name + "', '"
                + favouritePlace.Latitude + "','"
                + favouritePlace.Longitude + "')");
    }

    static public void DialogDelete(String name, final int id) {
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(context);
        dialogDelete.setMessage("Bạn có muốn xóa " + name + " không?");
        dialogDelete.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.QueryData("DELETE FROM PlaceCoordinates WHERE ID='" + id + "'");
                Toast.makeText(context, "Đã xóa", Toast.LENGTH_SHORT).show();
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

    static void DialogEdit(String name, final int id) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView((R.layout.dialog_edit));

        final EditText edtNewName = (EditText) dialog.findViewById(R.id.edtNewName);
        Button btnOK = (Button) dialog.findViewById(R.id.btnOK);
        Button btnHuy = (Button) dialog.findViewById(R.id.btnHuy);

        edtNewName.setText(name);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "thanh công", Toast.LENGTH_LONG).show();
                String newName = edtNewName.getText().toString().trim();
                database.QueryData("UPDATE PlaceCoordinates SET Name ='" + newName + "' WHERE ID = '" + id + "'");

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
}
