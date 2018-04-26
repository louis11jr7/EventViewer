package utpb.team8.eventviewer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

/*When an event listing is expanded this class controls the corresponding individual album page
Each individual event page will load different information but it will all be loaded in the same way as defined in this class
 */
public class DetailActivity extends AppCompatActivity {


    private DatabaseReference mRef;

    private StorageReference mStorage;

    private int guestCounter;

    private int eventID;

    private String eventTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //extras that were passed from the NewEvents page are gathered here
        Bundle extras = getIntent().getExtras();

        eventTitle = extras.getString("Title");
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

    //when the cancel button is pressed, this method is called which simply finished the activity
    public void goBack(View view)
    {
        finish();
    }

    /*when the submit button is pressed, this method is called
      currently the method simply creates a toast that tells the user the method has been added to MyEvents
      This method is where there would be code to write to the database to update the event list with this user as
      being subscribed to it, therefore changing the location of where the event would show up from NewEvents to MyEvents*/
    public void submit(View view)
    {
        mRef = FirebaseDatabase.getInstance().getReference().child("Events");

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                DataSnapshot names = dataSnapshot.child("Name");
                if(names.getValue() != null){
                    if (names.getValue().toString().equals(eventTitle)){
                        Log.v("ID: ",dataSnapshot.getKey().toString());
                        eventID = Integer.parseInt(dataSnapshot.getKey().toString());
                        DataSnapshot guestNumber = dataSnapshot.child("GuestCount");
                        if(guestNumber.getValue() != null){
                            guestCounter = Integer.parseInt(guestNumber.getValue().toString());
                            Log.v("counter:",guestNumber.getValue().toString());
                            submission();
                        }
                    }
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

    }

    private void submission() {
        String eventIDString = Integer.toString(eventID);
        Log.v("eventIDString", eventIDString);

        DatabaseReference newPost = mRef.child(eventIDString);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String nameString = user.getEmail();
        int count = guestCounter;
        count = count +1;

        String guestCountString = Integer.toString(count);
        Log.v("guestCountString",guestCountString);
        newPost.child("Guests").child("User"+guestCountString).setValue(nameString);
        newPost.child("GuestCount").setValue(count);

        //this will be used to add this event to the user's myEvents list
        //the newEvents page will also need to be reloaded
        Toast.makeText(DetailActivity.this, "Added to My Events",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
