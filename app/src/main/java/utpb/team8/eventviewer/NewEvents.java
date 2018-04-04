package utpb.team8.eventviewer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import java.io.ByteArrayOutputStream;

/*This class gathers information from the database and sends that information to the customListAdapter class
 to generate lists of the events. Specifically this class will gather the information from events
 that the user has not subscribed to
 */
public class NewEvents extends Fragment {
    //name of the events
    String[] nameArray = {"Party","Meeting","Breakfast","Cleanup","Movie Night","Basketball" };
    //details of the events
    String[] infoArray = {
            "Party at the SAC 6-9 PM",
            "Business meeting @ FishBowl 8-9 AM",
            "Breakfast at the RDH 9-11 AM",
            "Help cleanup the quad 5-7 PM",
            "Come watch Spiderman @ the SAC 7 PM",
            "Basketball at the dorms 5 PM"
    };
    //user defined image for the event. If no image is chosen, then a default image will need to be used
    Integer[] imageArray = {R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background};

    ListView listView;

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
        CustomListAdapter whatever = new CustomListAdapter(getActivity(), nameArray, infoArray, imageArray);
        listView = (ListView) getView().findViewById(R.id.listviewID);
        listView.setAdapter(whatever);
        //if a list item is clicked then the information will be passed to a details page
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                String eventTitle = nameArray[position];
                String eventInfo = infoArray[position];

                intent.putExtra("Title", eventTitle);
                intent.putExtra("Info", eventInfo);
                startActivity(intent);

            }
        });
    }
}
