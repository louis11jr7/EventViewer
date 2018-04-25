package utpb.team8.eventviewer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

/*This custom list adapter class is used to build the list view for the albumDetailActivity page
The list layout is defined in the activity_album_detail.xml and this class loads information into those blank lists
 */
public class AlbumDetailAdapter extends ArrayAdapter {

    private final Activity context;

    private final Integer[] imagesArray;

    private final String[] nameArray;

    static class ViewHolder{
        public ImageView imageView;
    }

    public AlbumDetailAdapter(Activity context, String[] nameArrayParam, Integer[] imageIDarray) {
        super(context, R.layout.album_detail_listview_row, nameArrayParam);

        this.context=context;
        this.imagesArray = imageIDarray;
        this.nameArray=nameArrayParam;
    }

    //this is a very basic layout because there is only an imageView on each row
    public View getView(int position, View view, ViewGroup parent){

        View rowView = view;

        if(rowView == null){
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.album_detail_listview_row,null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) rowView.findViewById(R.id.imageViewAlbumDetail);
            rowView.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder) rowView.getTag();
        viewHolder.imageView.setImageResource(imagesArray[position]);

        return rowView;


        /* LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.album_detail_listview_row, null, true);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageViewAlbumDetail);

        imageView.setImageResource((imagesArray[position]));

        return rowView;*/

    }

}
