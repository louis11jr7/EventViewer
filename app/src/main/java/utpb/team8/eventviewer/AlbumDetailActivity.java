package utpb.team8.eventviewer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/*When an album listing is expanded this class controls the corresponding individual album page
Each individual album page will load different information but it will all be loaded in the same way as defined in this class
 */

public class AlbumDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_detail);

        String savedExtra = getIntent().getStringExtra("Title");
        TextView myText = (TextView) findViewById(R.id.textID);
        myText.setText(savedExtra);
    }
}
