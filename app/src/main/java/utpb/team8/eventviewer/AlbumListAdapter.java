package utpb.team8.eventviewer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class AlbumListAdapter extends ArrayAdapter {

    private final Activity context;

    private final String[] nameArray;

    private final Integer[][] imagesArray;

    public AlbumListAdapter(Activity context, String[] nameArrayParam, Integer[][] imageIDArrayParam){

        super(context,R.layout.album_listview , nameArrayParam);

        this.context=context;
        this.imagesArray = imageIDArrayParam;
        this.nameArray=nameArrayParam;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.album_listview, null,true);

        //this code gets references to objects in the album_listview.xml file
        TextView nameTextField = (TextView) rowView.findViewById(R.id.albumTitles);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.albumImage1);
        ImageView imageView2 = (ImageView) rowView.findViewById(R.id.albumImage2);
        ImageView imageView3 = (ImageView) rowView.findViewById(R.id.albumImage3);
        ImageView imageView4 = (ImageView) rowView.findViewById(R.id.albumImage4);

        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(nameArray[position]);

        if(imagesArray[position].length >= 4){
            imageView.setImageResource(imagesArray[position][0]);
            imageView2.setImageResource(imagesArray[position][1]);
            imageView3.setImageResource(imagesArray[position][2]);
            imageView4.setImageResource(imagesArray[position][3]);
        }
        else if(imagesArray[position].length == 3){
            imageView.setImageResource(imagesArray[position][0]);
            imageView2.setImageResource(imagesArray[position][1]);
            imageView3.setImageResource(imagesArray[position][2]);
            imageView4.setImageResource(0);
        }
        else if(imagesArray[position].length == 2){
            imageView.setImageResource(imagesArray[position][0]);
            imageView2.setImageResource(imagesArray[position][1]);
            imageView3.setImageResource(0);
            imageView4.setImageResource(0);
        }
        else if(imagesArray[position].length == 1){
            imageView.setImageResource(imagesArray[position][0]);
            imageView2.setImageResource(0);
            imageView3.setImageResource(0);
            imageView4.setImageResource(0);
        }
        else if(imagesArray[position].length == 0){
            imageView.setImageResource(0);
            imageView2.setImageResource(0);
            imageView3.setImageResource(0);
            imageView4.setImageResource(0);
        }


        return rowView;

    };
}
