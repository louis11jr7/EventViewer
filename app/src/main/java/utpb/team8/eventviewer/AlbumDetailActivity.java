package utpb.team8.eventviewer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.util.ArrayList;

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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/*When an album listing is expanded this class controls the corresponding individual album page
Each individual album page will load different information but it will all be loaded in the same way as defined in this class

 This class makes use of a list adapter class to define the way that the images will be displayed on the page. The use of the list
 adapter class also makes the page dynamic so that no matter the amount of items passed the page will populate all of them
 */
public class AlbumDetailActivity extends AppCompatActivity {

    private DatabaseReference mRef;

    private ArrayList<String> namesArray = new ArrayList<>();
    private ArrayList<String> images = new ArrayList<>();

    ImageButton camera;

    ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_detail);

        //the extras that are passed from the album page are gathered here and set to usable variables
        Bundle extras = getIntent().getExtras();

        final String eventTitle = extras.getString("Title");
        TextView myTitle = (TextView) findViewById(R.id.albumName);
        myTitle.setText(eventTitle);

        mRef = FirebaseDatabase.getInstance().getReference().child("Events");

        mListView = (ListView) findViewById(R.id.albumDetailListView);

        final AlbumDetailAdapter adapter = new AlbumDetailAdapter(AlbumDetailActivity.this, namesArray, images);
        mListView.setAdapter(adapter);

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                DataSnapshot names = dataSnapshot.child("Name");
                if(names.getValue().equals(eventTitle)){
                    DataSnapshot album = dataSnapshot.child(eventTitle+"Album");
                    DataSnapshot imageCounter = album.child("ImageCount");
                    String imageCountString = imageCounter.getValue().toString();
                    int imageCountInt = Integer.parseInt(imageCountString);
                    for(int i=1; i < imageCountInt+1; i++){
                        String j = Integer.toString(i);
                        String image = album.child("uri"+j).getValue().toString();
                        Log.v("IMAGEID", image);
                        images.add(image);
                        namesArray.add("name");
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mRef.addChildEventListener(childEventListener);


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