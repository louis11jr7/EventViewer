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

/*This class is used to gather the data needed for the list view and pass that information to the
AlbumListAdapter class that will then generate the list.
The class will load the same events as the MyEvents page so that the user can only see albums
of events they have subscribed to
 */
public class Albums extends Fragment {

    //This array will be loaded with the titles of the events
    String[] nameArray = {"Lunch","THOR","Football","Project Meeting","Study Session"};
    /*This is a 2d array that will load with the images from the album. There must be an array for each corresponding
    event, even if it is a blank array. This is to avoid error when loading the lists in the AlbumListAdapter class.
    This will ensure that the length of the nameArray matches the amount of arrays within the imageArray. The amount of images
    in each array does not matter but the amount of arrays does.
     */
    Integer[][] imageArray = {{R.drawable.lunch1, R.drawable.lunch2, R.drawable.lunch3, R.drawable.lunch4},
            {R.drawable.thor1, R.drawable.thor2},
            {R.drawable.football1, R.drawable.football2, R.drawable.football3},
            {},
            {R.drawable.study1}
            };

    ListView listView;

    int[] album;
    String[] names;

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

        //the following code creates an instance of the adapter class and sends the information gathered in this class to that one
        AlbumListAdapter albums = new AlbumListAdapter(getActivity(), nameArray, imageArray);
        listView = getView().findViewById(R.id.albumListViewID);
        listView.setAdapter(albums);



        //If a list item is clicked then the details page will be called to expand the info. Information from that individual item will be passed.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                album = new int[imageArray[position].length];
                names = new String[imageArray[position].length];

                /*the imageArray we have is of Integer[] type but an array of that type cannot be passed through an intent
                so an int[] array must be used in it's place. Here the int[] array is loaded to match the original imageArray
                 */
                if(imageArray[position].length > 0) {
                    for (int i = 0; i < imageArray[position].length; i++) {
                        album[i] = imageArray[position][i];
                        names[i] = "name";
                    }
                }
                //the AlbumDetailActivity is called using this intent and all necessary information is passed as extras
                Intent intent = new Intent(getActivity(), AlbumDetailActivity.class);
                String eventTitle = nameArray[position];

                intent.putExtra("Title", eventTitle);
                intent.putExtra("Album", album);
                intent.putExtra("Names", names);

                startActivity(intent);

            }
        });
    }
}
