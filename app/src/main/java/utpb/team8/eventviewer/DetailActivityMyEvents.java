package utpb.team8.eventviewer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/*When an event listing is expanded this class controls the corresponding individual album page
Each individual event page will load different information but it will all be loaded in the same way as defined in this class
 */
public class DetailActivityMyEvents extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_my_events);

        String eventTitle = getIntent().getStringExtra("Title");
        TextView myTitle = (TextView) findViewById(R.id.eventTitle);
        myTitle.setText(eventTitle);

        String eventInfo = getIntent().getStringExtra("Info");
        TextView myInfo = (TextView) findViewById(R.id.eventInfo);
        myInfo.setText(eventInfo);


    }
}
