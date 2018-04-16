package utpb.team8.eventviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/*this class gathers information from the database and passes it to the customListAdapter class
in order to generate a list view of the events. Specifically, this will show events that the user
is subscribed to*/
public class MyEvents extends Fragment {

    //the name of the events
    String[] nameArray = {"Lunch","THOR","Football"};

    //the details of the events
    String[] infoArray = {
            "Meet us for lunch at the SAC",
            "Come watch Thor in the ST @ 8 PM",
            "Football at the park 5 PM"
    };

    //the user defined picture for the event. or if no picture is selected then a default image will need to be loaded into the array for the event
    Integer[] imageArray = {R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background};

    ListView listView;

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

        //the following creates an instance of the adapter and sends the info gathered in this class to the customListAdapter class
        CustomListAdapter whatever = new CustomListAdapter(getActivity(), nameArray, infoArray, imageArray);
        listView = (ListView) getView().findViewById(R.id.listviewID);
        listView.setAdapter(whatever);

        //if a list item is clicked then this will pass the information of that event to a details page and open it up
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(getActivity(), DetailActivityMyEvents.class);
                String eventTitle = nameArray[position];
                String eventInfo = infoArray[position];

                intent.putExtra("Title", eventTitle);
                intent.putExtra("Info", eventInfo);
                startActivity(intent);

            }
        });
    }
}