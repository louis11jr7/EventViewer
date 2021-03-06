package utpb.team8.eventviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

//this will handle all the users settings
public class Settings extends Fragment {

    ImageButton camera;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Settings");

        camera = (ImageButton) getView().findViewById(R.id.profilePicture);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //when the camera button is called, the ImageUpload class is called
                Intent intent = new Intent(getActivity(), ImageUpload.class);

                String calledBy = "settings";
                //go back and make this generated by what the user's name is
                String definition = "Steve";

                intent.putExtra("CalledBy", calledBy);
                intent.putExtra("Definition", definition);
                startActivity(intent);
            }
        });

    }
}