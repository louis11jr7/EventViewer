package utpb.team8.eventviewer;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

/*When an event listing is expanded this class will show expanded details of the event
Each individual event page will load different information but it will all be loaded in the same way as defined in this class
 */
public class DetailActivityMyEvents extends AppCompatActivity {

    private StorageReference mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_my_events);

        Bundle extras = getIntent().getExtras();

        String eventTitle = extras.getString("Title");
        TextView myTitle = (TextView) findViewById(R.id.eventTitle);
        myTitle.setText(eventTitle);

        String eventInfo = extras.getString("Info");
        TextView myInfo = (TextView) findViewById(R.id.eventInfo);
        myInfo.setText(eventInfo);


        final ImageView myImage = (ImageView) findViewById(R.id.eventImage);

        mStorage = FirebaseStorage.getInstance().getReference();
        mStorage.child(eventTitle).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerInside().into(myImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //handle errors
            }
        });


    }
}
