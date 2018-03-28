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

public class NewEvents extends Fragment {
    String[] nameArray = {"Party","Meeting","Breakfast","Cleanup","Movie Night","Basketball" };

    String[] infoArray = {
            "Party at the SAC 6-9 PM",
            "Business meeting @ FishBowl 8-9 AM",
            "Breakfast at the RDH 9-11 AM",
            "Help cleanup the quad 5-7 PM",
            "Come watch Spiderman @ the SAC 7 PM",
            "Basketball at the dorms 5 PM"
    };

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

        CustomListAdapter whatever = new CustomListAdapter(getActivity(), nameArray, infoArray, imageArray);
        listView = (ListView) getView().findViewById(R.id.listviewID);
        listView.setAdapter(whatever);

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
