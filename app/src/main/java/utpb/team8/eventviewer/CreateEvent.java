package utpb.team8.eventviewer;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;


/*This class is used to gather user input from the CreateEvent page and insert it into the database as a new event
 */
public class CreateEvent extends Fragment {

    EditText title;
    EditText details;
    EditText date;
    Button set;
    Button submit;
    ImageButton camera;

    String titleString;
    String detailsString;
    String dateString;
    ImageView image;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        return inflater.inflate(R.layout.fragment_create_event, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Create Event");

        //setting the editText views
        title = getView().findViewById(R.id.createTitle);
        details = getView().findViewById(R.id.createDetails);
        date = getView().findViewById(R.id.createDate);
        //setting the image view
        image = getView().findViewById(R.id.eventImageCreate);
        //setting the set button and making it clickable
        set = (Button) getView().findViewById(R.id.set);
        set.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //when this button is clicked, the getInformation method is called
                getInformation(title, details, date);
            }

        });
        //setting the camera button
        camera = (ImageButton) getView().findViewById(R.id.createEventImage);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //when the camera button is clicked, the ImageUpload activity is called
                Intent intent = new Intent(getActivity(), ImageUpload.class);
                String calledBy = "create";
                String definition = titleString;
                intent.putExtra("CalledBy", calledBy);
                intent.putExtra("Definition", definition);
                //startActivityForResult is used so that the ImageUpload Activity can send back an
                //image to this page as seen in the onActivityResult method
                startActivityForResult(intent, 1);
            }
        });
        //setting the submit button
        submit = (Button) getView().findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //when the submit button is clicked, the uploadToDatabase method is called
                uploadToDatabase(titleString, detailsString, dateString);
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });
    }

    //this method gathers information from what the user has typed in. This is necessary to use before
    //the imageUpload activity is called so that necessary information can be passed to that method
    private void getInformation(EditText title, EditText details, EditText date) {

        titleString = title.getText().toString();
        detailsString = details.getText().toString();
        dateString = date.getText().toString();


    }
    //this method is where all code for database upload would be placed
    private void uploadToDatabase(String titleString, String detailsString, String dateString) {

    }

    //this method gathers the information that was passed back from the ImageUpload Activity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                //the result should be a Bitmap value that we can use to set the image of the event
                Bitmap result = data.getParcelableExtra("result");
                image.setImageBitmap(result);
            }
            if(resultCode == Activity.RESULT_CANCELED){
                //nothing happens
            }
        }
    }

}
