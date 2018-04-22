package utpb.team8.eventviewer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

/*When an album listing is expanded this class controls the corresponding individual album page
Each individual album page will load different information but it will all be loaded in the same way as defined in this class

 This class makes use of a list adapter class to define the way that the images will be displayed on the page. The use of the list
 adapter class also makes the page dynamic so that no matter the amount of items passed the page will populate all of them
 */
public class AlbumDetailActivity extends AppCompatActivity {

    String [] nameArray;
    int[] imagesArrayPre;
    Integer[] imageArray;

    ImageButton camera;

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_detail);

        //the extras that are passed from the album page are gathered here and set to usable variables
        Bundle extras = getIntent().getExtras();

        final String eventTitle = extras.getString("Title");
        TextView myTitle = (TextView) findViewById(R.id.albumName);
        myTitle.setText(eventTitle);


        nameArray = extras.getStringArray("Names");
        //the images array is passed as an int[] array from the Albums class but we want to use it as an Integer[] array
        //so first we initialize a temporary array that is an int[] array
        imagesArrayPre = extras.getIntArray("Album");
        //this is the actual Integer[] array that will be used with the list adapter
        imageArray = new Integer[imagesArrayPre.length];
        //we load the Integer[] to match the temporary int[] array we got from the Albums class
        for(int i = 0; i < imagesArrayPre.length; i++)
        {
          imageArray[i] = imagesArrayPre[i];
        }

        //this creates the instance of the adapter class and sends the info that was gathered in this class to the adapter class
        AlbumDetailAdapter albumDetail = new AlbumDetailAdapter(this, nameArray, imageArray);
        listView = (ListView) findViewById(R.id.albumDetailListView);
        listView.setAdapter(albumDetail);

        //the camera button is made clickable
        camera = (ImageButton) findViewById(R.id.addToAlbum);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //when the camera button is clicked, the ImageUpload activity is called
                //this class will also send the album name(definition) and an identifier(called by)
                // to let the ImageUpload class know exactly what class called it
                Intent intent = new Intent(AlbumDetailActivity.this, ImageUpload.class);
                String calledBy = "album";
                String definition = eventTitle;
                intent.putExtra("CalledBy", calledBy);
                intent.putExtra("Definition", definition);
                startActivity(intent);
            }
        });

    }


}