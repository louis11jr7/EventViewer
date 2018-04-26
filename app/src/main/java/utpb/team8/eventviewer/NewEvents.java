package utpb.team8.eventviewer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/*This class gathers information from the database and sends that information to the customListAdapter class
 to generate lists of the events. Specifically this class will gather the information from events
 that the user has not subscribed to
 */
public class NewEvents extends Fragment {
    private DatabaseReference mRef;

    private ArrayList<String> nameList = new ArrayList<>();
    private ArrayList<String> infoList = new ArrayList<>();

    ListView mListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_new_events, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("New Events");
        //creates an instance and sends the information gathered in this class to the adapter class
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String nameString = currentUser.getEmail();

        mRef = FirebaseDatabase.getInstance().getReference().child("Events");

        mListView = (ListView)getView().findViewById(R.id.listviewID);

        final CustomListAdapter adapter = new CustomListAdapter(getActivity(), nameList, infoList);
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
                                break;
                            }
                            else{match = "false";}
                        }
                    }
                    Log.v("User match:", match);
                    if(match.equals("false")){
                        String nameValue = names.getValue().toString();
                        nameList.add(nameValue);
                        String infoValue = information.getValue().toString();
                        infoList.add(infoValue);
                        adapter.notifyDataSetChanged();
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
        //if a list item is clicked then the information will be passed to a details page
       mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                String eventTitle = nameList.get(position);
                String eventInfo = infoList.get(position);

                intent.putExtra("Title", eventTitle);
                intent.putExtra("Info", eventInfo);
                startActivity(intent);

            }
        });
    }
}
