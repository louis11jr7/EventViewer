package utpb.team8.eventviewer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/*This custom list adapter class is used to build the list view for the albums page
The list layout is defined in the album_listview.xml and this class loads information into those blank lists
The list view will only show up to 4 images from the album as to not clutter the page
 */
public class AlbumListAdapter extends ArrayAdapter {

    private final Activity context;

    private final String[] nameArray;

    private final Integer[][] imagesArray;

    private Integer image1;
    private Integer image2;
    private Integer image3;
    private Integer image4;

    static class ViewHolder{
        public TextView nameTextField;
        public ImageView imageView;
        public ImageView imageView2;
        public ImageView imageView3;
        public ImageView imageView4;
    }

    public AlbumListAdapter(Activity context, String[] nameArrayParam, Integer[][] imageIDArrayParam){

        super(context,R.layout.album_listview , nameArrayParam);

        this.context=context;
        this.imagesArray = imageIDArrayParam;
        this.nameArray=nameArrayParam;
    }

    public View getView(int position, View view, ViewGroup parent) {
        View rowView = view;

        if(rowView == null){
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.album_listview, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.nameTextField = (TextView)rowView.findViewById(R.id.albumTitles);
            viewHolder.imageView = (ImageView) rowView.findViewById(R.id.albumImage1);
            viewHolder.imageView2 = (ImageView) rowView.findViewById(R.id.albumImage2);
            viewHolder.imageView3 = (ImageView) rowView.findViewById(R.id.albumImage3);
            viewHolder.imageView4 = (ImageView) rowView.findViewById(R.id.albumImage4);
            rowView.setTag(viewHolder);
        }

        getImages(position);

        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.nameTextField.setText(nameArray[position]);
        holder.imageView.setImageResource(image1);
        holder.imageView2.setImageResource(image2);
        holder.imageView3.setImageResource(image3);
        holder.imageView4.setImageResource(image4);


        return rowView;

        /*LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.album_listview, null,true);

        //this code gets references to objects in the album_listview.xml file
        TextView nameTextField = (TextView) rowView.findViewById(R.id.albumTitles);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.albumImage1);
        ImageView imageView2 = (ImageView) rowView.findViewById(R.id.albumImage2);
        ImageView imageView3 = (ImageView) rowView.findViewById(R.id.albumImage3);
        ImageView imageView4 = (ImageView) rowView.findViewById(R.id.albumImage4);

        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(nameArray[position]);


        return rowView;*/

    }

    private void getImages(int position) {
        //If there are 4 or more images in the album then the list view will show the first 4 of the album
        if(imagesArray[position].length >= 4){
            image1 = imagesArray[position][0];
            image2 = imagesArray[position][1];
            image3 = imagesArray[position][2];
            image4 = imagesArray[position][3];
        }
        //If there are only 3 images in the album then it will show those and generate a blank drawable so as to meet the parameters of the listView
        else if(imagesArray[position].length == 3){
            image1 = imagesArray[position][0];
            image2 = imagesArray[position][1];
            image3 = imagesArray[position][2];
            image4 = 0;
        }
        //If there are only 2 images in the album then it will show those and generate 2 blank drawables
        else if(imagesArray[position].length == 2){
            image1 = imagesArray[position][0];
            image2 = imagesArray[position][1];
            image3 = 0;
            image4 = 0;
        }
        //If there is only 1 image in the album then it will show that one and generate 3 blank drawables
        else if(imagesArray[position].length == 1){
            image1 = imagesArray[position][0];
            image2 = 0;
            image3 = 0;
            image4 = 0;
        }
        //If there are no albums in the image then 4 blank drawables will be generated for the list
        else if(imagesArray[position].length == 0){
            image1 = 0;
            image2 = 0;
            image3 = 0;
            image4 = 0;
        }


    }
}
