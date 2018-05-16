package android.foodmap;

import android.foodmap.R;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MarkerDetailFragment extends BottomSheetDialogFragment {
    private ImageButton btnDirection;
    private MapsActivity Main;
    public MarkerDetailFragment() {
        // Required empty public constructor
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
        btnDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Main.onMsgFromFragToMain("DIRECT", "DIRECT");
                onDetach();
            }
        });
        return layout_marker_detail;

    }
}
