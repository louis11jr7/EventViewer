package utpb.team8.eventviewer;

import android.content.Intent;
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

import java.util.ArrayList;

/*this class gathers information from the database and passes it to the customListAdapter class
in order to generate a list view of the events. Specifically, this will show events that the user
is subscribed to*/
public class MyEvents extends Fragment {
    //the three arrays defined here are hardcoded with information but they would be where
    //information from the database would be loaded
    private DatabaseReference mRef;

    private ArrayList<String> nameList = new ArrayList<>();
    private ArrayList<String> infoList = new ArrayList<>();

    ListView mListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_my_events, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("My Events");

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

        //if a list item is clicked then this will pass the information of that event to a details page and open it up
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(getActivity(), DetailActivityMyEvents.class);
                String eventTitle = nameList.get(position);
                String eventInfo = infoList.get(position);

                intent.putExtra("Title", eventTitle);
                intent.putExtra("Info", eventInfo);
                startActivity(intent);
              // Intent intent = new Intent(getActivity(), FirebasePractice.class);
              // startActivity(intent);

            }
        });
    }
}