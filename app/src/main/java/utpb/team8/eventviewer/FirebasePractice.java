package utpb.team8.eventviewer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FirebasePractice extends AppCompatActivity {

    private DatabaseReference mRef;

    private ArrayList<String> nameList = new ArrayList<>();
    private ArrayList<String> infoList = new ArrayList<>();

    private ListView mListView;

    private static final int GALLERY_INTENT = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_practice);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String nameString = currentUser.getEmail();

        mRef = FirebaseDatabase.getInstance().getReference().child("Events");

        mListView = (ListView)findViewById(R.id.listViewFirebase);

        final CustomListAdapterFirebase adapter = new CustomListAdapterFirebase(FirebasePractice.this, nameList, infoList);
        mListView.setAdapter(adapter);

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.v("onChildAdded:", dataSnapshot.getKey());
                DataSnapshot names = dataSnapshot.child("Name");
                if(names.getValue() != null) {
                    Log.v("Name:", names.getValue().toString());
                }
                DataSnapshot information = dataSnapshot.child("Info");
                if(information.getValue() != null){
                    Log.v("Info:", information.getValue().toString());
                }
                DataSnapshot guests = dataSnapshot.child("Guests");
                if(guests.getValue() != null){
                    Log.v("Guests:", guests.getValue().toString());
                }
                DataSnapshot guestNumber = dataSnapshot.child("GuestCount");
                if(guestNumber.getValue() != null){
                    String match= "";
                    Log.v("GuestCount:", guestNumber.getValue().toString());
                    int guestCounter = Integer.parseInt(guestNumber.getValue().toString());
                    for(int i = 1; i < guestCounter+1; i++){
                        String guestCount = Integer.toString(i);
                        DataSnapshot users = guests.child("User"+guestCount);
                        if(users.getValue() != null){
                            Log.v("Users:", users.getValue().toString());
                            if(users.getValue().toString().equals(nameString)){
                                match = "true";
                                String nameValue = names.getValue().toString();
                                nameList.add(nameValue);
                                String infoValue = information.getValue().toString();
                                infoList.add(infoValue);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                    Log.v("User match:", match);
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
}
