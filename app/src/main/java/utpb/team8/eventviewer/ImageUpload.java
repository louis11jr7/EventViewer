package utpb.team8.eventviewer;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

/*Will be called by other pages whenever a user wants to upload an image from
either their camera or gallery*/
public class ImageUpload extends AppCompatActivity {

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Button btnSelect;
    private ImageView ivImage;
    private String userChoosenTask;

    Button btnUpload;
    String calledBy, definition;


    Bitmap result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_image);

        btnSelect = (Button) findViewById(R.id.btnSelectPhoto);
        btnSelect.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        ivImage = (ImageView) findViewById(R.id.ivImage);

        /*the classes that are able to call this activity will each send two variables: calledBy
        which is an identifier for which class called this activity and definition which is
        a variable that will have either the album, event, or user information that is
        needed to make an insertion into the database
         */
        Bundle extras = getIntent().getExtras();

        calledBy = extras.getString("CalledBy");
        definition = extras.getString("Definition");

        btnUpload = (Button) findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //when the upload button is clicked, the uploadImage method is called
                uploadImage(calledBy, definition);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };//Shows options to user

        AlertDialog.Builder builder = new AlertDialog.Builder(ImageUpload.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(ImageUpload.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();//Open camera

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();//Open gallery

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();//Cancels request
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }//User selects image from their gallery

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }//User opens up camera app

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        //Creates a file with name based on timestamp in .jpg format

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ivImage.setImageBitmap(thumbnail);
        //the result is set to the image that was selected
        result = thumbnail;

    }//shows preview of captured image

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ivImage.setImageBitmap(bm);
        //the result is set to the image that was selected
        result = bm;
    }//shows preview of gallery image after selection



    /*this method uses information that was passed from the class that called this activity
    and uses them to decide how the image that is selected will be uploaded.
    At the moment the method simply creates a toast message with information relating to the
    activity that called it. However, this is where database insertion code would be placed.
    The class uses switch case so that multiple activities can call this activity and we would be able to
    handle the information differently based on what activity was the caller.
     */
    private void uploadImage(String calledBy, String definition){
        switch(calledBy){
            case "album":
                Toast.makeText(ImageUpload.this, "Added to " + definition + " album.",
                        Toast.LENGTH_SHORT).show();
                finish();
                break;

            case "settings":
                Toast.makeText(ImageUpload.this, "Added to " + definition + "'s profile.",
                        Toast.LENGTH_SHORT).show();
                finish();
                break;

            case "create":
                Toast.makeText(ImageUpload.this, "Added to the" + definition + " event.",
                        Toast.LENGTH_SHORT).show();
                //This intent is used to return the result (the image that was selected) to the CreateEvent activity
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",result);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
                break;

        }

    }

}