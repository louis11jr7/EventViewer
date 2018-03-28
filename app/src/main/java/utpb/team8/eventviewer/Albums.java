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

import java.util.List;

public class Albums extends Fragment {

    String[] nameArray = {"Lunch","Volleyball","Movie Night"};

    Integer[][] imageArray = {{R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground},
            {R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_background},
            {R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_background},};

    ListView listView;

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

        AlbumListAdapter albums = new AlbumListAdapter(getActivity(), nameArray, imageArray);

        listView = getView().findViewById(R.id.albumListViewID);
        listView.setAdapter(albums);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(getActivity(), AlbumDetailActivity.class);
                String eventTitle = nameArray[position];

                intent.putExtra("Title", eventTitle);
                startActivity(intent);

            }
        });
    }
}
