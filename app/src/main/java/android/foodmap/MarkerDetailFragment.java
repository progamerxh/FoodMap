package android.foodmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

public class MarkerDetailFragment extends BottomSheetDialogFragment {
    private ImageButton btnDirection;
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
        imgPhoto = (ImageView) layout_marker_detail.findViewById(R.id.imgPhoto);
        txtPlaceName = (TextView) layout_marker_detail.findViewById(R.id.txtPlaceName);
        txtPlaceAddress = (TextView) layout_marker_detail.findViewById(R.id.txtPlaceAddress);

        txtPlaceName.setText(favouritePlace.Name);
        txtPlaceAddress.setText(favouritePlace.Address);
        DownloadImageTask downloadImageTask = new DownloadImageTask();
        downloadImageTask.execute(favouritePlace.MobilePicturePath);

        btnDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Main.onMsgFromFragToMain("DIRECT", "DIRECT");
                onDetach();
            }
        });
        return layout_marker_detail;

    }

    public void onMsgFromMainToFragment(FavouritePlace favouriteplace) {
        favouritePlace = favouriteplace;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Drawable> {
        protected Drawable doInBackground(String... urls) {
            try {
                InputStream is = (InputStream) new URL(urls[0]).getContent();
                Drawable d = Drawable.createFromStream(is, "src name");
                return d;
            } catch (Exception e) {
                return null;
            }
        }

        protected void onPostExecute(Drawable result) {
            imgPhoto.setImageDrawable(result);
        }
    }
}
