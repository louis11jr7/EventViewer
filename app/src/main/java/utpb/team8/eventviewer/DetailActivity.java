package utpb.team8.eventviewer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/*When an event listing is expanded this class controls the corresponding individual album page
Each individual event page will load different information but it will all be loaded in the same way as defined in this class
 */
public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //extras that were passed from the NewEvents page are gathered here
        Bundle extras = getIntent().getExtras();

        String eventTitle = extras.getString("Title");
        TextView myTitle = (TextView) findViewById(R.id.eventTitle);
        myTitle.setText(eventTitle);

        String eventInfo = extras.getString("Info");
        TextView myInfo = (TextView) findViewById(R.id.eventInfo);
        myInfo.setText(eventInfo);

        Integer eventImage = extras.getInt("Image");
        ImageView myImage = (ImageView) findViewById(R.id.eventImage);
        myImage.setImageResource(eventImage);



    }

    //when the cancel button is pressed, this method is called which simply finished the activity
    public void goBack(View view)
    {
        finish();
    }

    /*when the submit button is pressed, this method is called
      currently the method simply creates a toast that tells the user the method has been added to MyEvents
      This method is where there would be code to write to the database to update the event list with this user as
      being subscribed to it, therefore changing the location of where the event would show up from NewEvents to MyEvents*/
    public void submit(View view)
    {
        //this will be used to add this event to the user's myEvents list
        //the newEvents page will also need to be reloaded
        Toast.makeText(DetailActivity.this, "Added to My Events",
                Toast.LENGTH_SHORT).show();
        finish();
    }

}
