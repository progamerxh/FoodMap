package android.foodmap;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MarkerDetailFragment extends BottomSheetDialogFragment {
    private ImageButton btnDirection;
    private ImageButton btnAddFav;
    private TextView txtPlaceName;
    private TextView txtPlaceAddress;
    private ImageView imgPhoto;
    private MapsActivity Main;
    private FavouritePlace favouritePlace;

    public MarkerDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Main = (MapsActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LinearLayout layout_marker_detail = (LinearLayout) inflater.inflate(R.layout.fragment_bottom_sheet_dialog, null);
        btnDirection = (ImageButton) layout_marker_detail.findViewById(R.id.btnDirection);
        btnAddFav = (ImageButton) layout_marker_detail.findViewById(R.id.btnAddFav);
        imgPhoto = (ImageView) layout_marker_detail.findViewById(R.id.imgPhoto);
        txtPlaceName = (TextView) layout_marker_detail.findViewById(R.id.txtPlaceName);
        txtPlaceAddress = (TextView) layout_marker_detail.findViewById(R.id.txtPlaceAddress);

        txtPlaceName.setText(favouritePlace.Name);
        txtPlaceAddress.setText(favouritePlace.Address);
//        DownloadImageTask downloadImageTask = new DownloadImageTask(imgPhoto);
//        downloadImageTask.execute("https://images.foody.vn/res/g23/228705/prof/s640x400/foody-mobile-1-jpg-351-636334665072405328.jpg");
        Picasso.get().load("https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Good_Food_Display_-_NCI_Visuals_Online.jpg/1200px-Good_Food_Display_-_NCI_Visuals_Online.jpg").into(imgPhoto);
        btnDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Main.onMsgFromFragToMain("DIRECT", "DIRECT");
                onDetach();
            }
        });
        btnAddFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Main.onMsgFromFragToMain("ADDFAV","ADDFAV");
            }
        });
        return layout_marker_detail;

    }

    public void onMsgFromMainToFragment(FavouritePlace favouriteplace) {
        favouritePlace = favouriteplace;
    }

    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView;
        private Bitmap image;

        public DownloadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                image = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                image = null;
            }
            return image;
        }

        @SuppressLint("NewApi")
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                imageView.setImageBitmap(result);
            }
        }
    }
}
