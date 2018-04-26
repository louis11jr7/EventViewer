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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/*This class is used to gather the data needed for the list view and pass that information to the
AlbumListAdapter class that will then generate the list.
The class will load the same events as the MyEvents page so that the user can only see albums
of events they have subscribed to
 */
public class Albums extends Fragment {

    private DatabaseReference mRef;

    private ArrayList<String> nameList = new ArrayList<>();
    private ArrayList<String>image1= new ArrayList<>();
    private ArrayList<String> image2= new ArrayList<>();
    private ArrayList<String>image3= new ArrayList<>();
    private ArrayList<String>image4= new ArrayList<>();

    ListView mListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_albums, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Albums");

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userNameString = currentUser.getEmail();

        mRef = FirebaseDatabase.getInstance().getReference().child("Events");

        mListView = (ListView) getView().findViewById(R.id.albumListViewID);

        final AlbumListAdapter adapter = new AlbumListAdapter(getActivity(), nameList, image1, image2, image3, image4);
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
                            if(users.getValue().toString().equals(userNameString)){
                                match = "true";
                                String nameValue = names.getValue().toString();
                                String eventName = names.getValue().toString();
                                DataSnapshot album = dataSnapshot.child(eventName+"Album");
                                DataSnapshot imageCounter = album.child("ImageCount");
                                String imageCountString = imageCounter.getValue().toString();
                                int imageCountInt = Integer.parseInt(imageCountString);

                                if(imageCountInt >= 4)
                                {
                                    DataSnapshot uri1 = album.child("uri1");
                                    String uri1String1 = uri1.getValue().toString();
                                    DataSnapshot uri2 = album.child("uri2");
                                    String uri1String2 = uri2.getValue().toString();
                                    DataSnapshot uri3 = album.child("uri3");
                                    String uri1String3 = uri3.getValue().toString();
                                    DataSnapshot uri4 = album.child("uri4");
                                    String uri1String4 = uri4.getValue().toString();
                                    image1.add(uri1String1);
                                    image2.add(uri1String2);
                                    image3.add(uri1String3);
                                    image4.add(uri1String4);
                                }
                                else if(imageCountInt == 3)
                                {
                                    DataSnapshot uri1 = album.child("uri1");
                                    String uri1String1 = uri1.getValue().toString();
                                    DataSnapshot uri2 = album.child("uri2");
                                    String uri1String2 = uri2.getValue().toString();
                                    DataSnapshot uri3 = album.child("uri3");
                                    String uri1String3 = uri3.getValue().toString();
                                    image1.add(uri1String1);
                                    image2.add(uri1String2);
                                    image3.add(uri1String3);
                                    image4.add(null);
                                }
                                else if(imageCountInt == 2)
                                {
                                    DataSnapshot uri1 = album.child("uri1");
                                    String uri1String1 = uri1.getValue().toString();
                                    DataSnapshot uri2 = album.child("uri2");
                                    String uri1String2 = uri2.getValue().toString();
                                    image1.add(uri1String1);
                                    image2.add(uri1String2);
                                    image3.add(null);
                                    image4.add(null);

                                }
                                else if(imageCountInt == 1)
                                {
                                    DataSnapshot uri1 = album.child("uri1");
                                    String uri1String1 = uri1.getValue().toString();
                                    Log.v("URI1",uri1String1);
                                    image1.add(uri1String1);
                                    image2.add(null);
                                    image3.add(null);
                                    image4.add(null);
                                }
                                else if(imageCountInt == 0)
                                {
                                    image1.add(null);
                                    image2.add(null);
                                    image3.add(null);
                                    image4.add(null);
                                }

                                nameList.add(nameValue);
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



        //If a list item is clicked then the details page will be called to expand the info. Information from that individual item will be passed.
             mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                //the AlbumDetailActivity is called using this intent and all necessary information is passed as extras
                Intent intent = new Intent(getActivity(), AlbumDetailActivity.class);
                String eventTitle = nameList.get(position);

                intent.putExtra("Title", eventTitle);

                startActivity(intent);

            }
        });
    }
}
