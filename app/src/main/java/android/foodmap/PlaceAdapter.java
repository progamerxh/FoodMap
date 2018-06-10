package android.foodmap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class PlaceAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<FavouritePlace> favouritePlacesList;
    private Context fcontext;

    public PlaceAdapter(Context context, int layout, List<FavouritePlace> favouritePlaces) {
        this.context = context;
        this.layout = layout;
        this.favouritePlacesList = favouritePlaces;
    }


    @Override
    public int getCount() {
        return favouritePlacesList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        TextView txtPlaceName;
        ImageView imgDelete, imgEdit;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder Holder;
        if (view == null) {
            Holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            //ánh xạ view
            Holder.txtPlaceName = (TextView) view.findViewById(R.id.txtPlaceName);
            Holder.imgDelete = (ImageView) view.findViewById(R.id.imgDelete);
            Holder.imgEdit = (ImageView) view.findViewById(R.id.imgEdit);
            view.setTag(Holder);
        } else {
            Holder = (ViewHolder) view.getTag();
        }
        //Gán giá trị
        final FavouritePlace favouritePlace = favouritePlacesList.get(i);
        Holder.txtPlaceName.setText(favouritePlace.Name);

        //Bắt sự kiện xóa, sửa
        Holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavouriteFragment.DialogEdit(favouritePlace.Name, favouritePlace.ID);
            }
        });
        Holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavouriteFragment.DialogDelete(favouritePlace.Name, favouritePlace.ID);
            }
        });
        return view;
    }

}
