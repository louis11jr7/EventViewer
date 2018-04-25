package utpb.team8.eventviewer;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;


/*This class is used to gather user input from the CreateEvent page and insert it into the database as a new event
 */
public class CreateEvent extends Fragment {

    EditText title;
    EditText details;
    EditText date;
    Button set;
    Button submit;
    ImageButton camera;

    String titleString;
    String detailsString;
    String dateString;
    ImageView image;

    private DatabaseReference mDatabase;
    private String childCount;
    private Long count;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        return inflater.inflate(R.layout.fragment_create_event, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Create Event");

        mDatabase = FirebaseDatabase.getInstance().getReference();

        DatabaseReference mChildCount = mDatabase.child("ChildCount");
        mChildCount.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count = dataSnapshot.getValue(Long.class);
                childCount = count.toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //setting the editText views
        title = getView().findViewById(R.id.createTitle);
        details = getView().findViewById(R.id.createDetails);
        //setting the set button and making it clickable
        set = (Button) getView().findViewById(R.id.set);
        set.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //when this button is clicked, the getInformation method is called
                getInformation(title, details);
            }

        });
        //setting the camera button
        camera = (ImageButton) getView().findViewById(R.id.createEventImage);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //when the camera button is clicked, the ImageUpload activity is called
                Intent intent = new Intent(getActivity(), ImageUpload.class);
                String calledBy = "create";
                String definition = titleString;
                intent.putExtra("CalledBy", calledBy);
                intent.putExtra("Definition", definition);
                //startActivityForResult is used so that the ImageUpload Activity can send back an
                //image to this page as seen in the onActivityResult method
                startActivityForResult(intent, 1);
            }
        });
        //setting the submit button
        submit = (Button) getView().findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //when the submit button is clicked, the uploadToDatabase method is called
                uploadToDatabase();
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });
    }

    //this method gathers information from what the user has typed in. This is necessary to use before
    //the imageUpload activity is called so that necessary information can be passed to that method
    private void getInformation(EditText title, EditText details) {

        titleString = title.getText().toString();
        detailsString = details.getText().toString();


    }
    //this method is where all code for database upload would be placed
    private void uploadToDatabase() {
        DatabaseReference newPost = mDatabase.child("Events").child(childCount);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String nameString = user.getEmail();

        newPost.child("Name").setValue(titleString);
        newPost.child("Info").setValue(detailsString);
        newPost.child("GuestCount").setValue("1");
        newPost.child("Guests").child("User1").setValue(nameString);

        count = count +1;
        DatabaseReference mChildCount2 = mDatabase.child("ChildCount");
        mChildCount2.setValue(count);

    }

    /*this method gathers the information that was passed back from the ImageUpload Activity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                //the result should be a Bitmap value that we can use to set the image of the event
                Bitmap result = data.getParcelableExtra("result");
                image.setImageBitmap(result);

            }
            if(resultCode == Activity.RESULT_CANCELED){
                //nothing happens
            }
        }
    }*/

}
