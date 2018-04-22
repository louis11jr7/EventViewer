package utpb.team8.eventviewer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;


public class AlbumDetailAdapter extends ArrayAdapter {

    private final Activity context;

    private final Integer[] imagesArray;

    private final String[] nameArray;

    public AlbumDetailAdapter(Activity context, String[] nameArrayParam, Integer[] imageIDarray) {
        super(context, R.layout.album_detail_listview_row, nameArrayParam);

        this.context=context;
        this.imagesArray = imageIDarray;
        this.nameArray=nameArrayParam;
    }

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.album_detail_listview_row, null, true);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageViewAlbumDetail);

        imageView.setImageResource((imagesArray[position]));

        return rowView;

    }

}
