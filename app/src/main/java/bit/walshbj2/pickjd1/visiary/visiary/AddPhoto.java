package bit.walshbj2.pickjd1.visiary.visiary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AddPhoto extends ActionBarActivity {

    String mPhotoFileName;
    File mPhotoFile;
    Uri mPhotoFileUri;
    ImageButton btnAddImage;
    String timeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        //Retrieve layout resources.
        btnAddImage = (ImageButton) findViewById(R.id.btnAddPhoto);
        Button btnAddEntry = (Button) findViewById(R.id.btnSubmitEntry);
        TextView tvCurrentDate = (TextView) findViewById(R.id.tvCurrentDate);

        //Create a new formatted timestamp showing todays date and time
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date currentTime = new Date();
        timeStamp = timeStampFormat.format(currentTime);

        //Set the timestamp to be displayed in the date text view
        tvCurrentDate.setText(currentTime.toString());

        //Create a new on click handler for the image button
        View.OnClickListener addImageOnClick = new AddImageOnClick();

        //Create a new on click handler for the add entry button
        View.OnClickListener addEntryOnClick = new AddEntryOnClick();

        //Set the listener to the image button
        btnAddImage.setOnClickListener(addImageOnClick);

        //Set the listener to the add entry button
        btnAddEntry.setOnClickListener(addEntryOnClick);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public File createTimeStampedFile()
    {
        File imageRootPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File imageStorageDirectory = new File(imageRootPath, "Visiary");
        if(!imageStorageDirectory.exists())
        {
            imageStorageDirectory.mkdirs();
        }

        mPhotoFileName = "IMG_" + timeStamp + ".jpg";

        File photoFile = new File((imageStorageDirectory.getPath()+ File.separator + mPhotoFileName));

        return photoFile;
    }

    //Inner class to handle the on click event of the add entry button******************************
    public class AddEntryOnClick implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {
            //Retrieve the edit text resource
            EditText etAboutPhoto = (EditText) findViewById(R.id.etAboutPhoto);

            //Set the text input from the edit text to a string
            String diaryEntry = etAboutPhoto.getText().toString();

            //If the string is empty...
            if(!(diaryEntry.isEmpty()) && !(diaryEntry.equals("")))
            {
               //then check the photo is not the default image...
                if(btnAddImage.getDrawable() == getResources().getDrawable(R.drawable.addimage))
                {
                    //if it is then show a toast stating to add a photo.
                    Toast.makeText(AddPhoto.this,"Please add an image to your entry.", Toast.LENGTH_LONG).show();
                }
                //Otherwise...
                else
                {
                    //Create a new database object to add the entry to the database and save it.
                    Toast.makeText(AddPhoto.this,"Entry added to your diary.", Toast.LENGTH_LONG).show();
                }

            }
            //Otherwise...
            else
            {
                //show a toast asking the user to fill in a diary entry about the image
                Toast.makeText(AddPhoto.this,"Please add a diary entry about this photo.", Toast.LENGTH_LONG).show();
            }

        }
    }


    //Inner class to handle the on click event of the image button**********************************
    public class AddImageOnClick implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {
            mPhotoFile = createTimeStampedFile();

            mPhotoFileUri = Uri.fromFile(mPhotoFile);

            Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoFileUri);

            startActivityForResult(imageCaptureIntent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 1)
        {
            if (resultCode == RESULT_OK)
            {
                String realFilePath = mPhotoFile.getPath();

                Bitmap userPhoto = BitmapFactory.decodeFile(realFilePath);

                btnAddImage.setImageBitmap(Bitmap.createScaledBitmap(userPhoto,btnAddImage.getWidth(),btnAddImage.getHeight(),false));

            }
            else
            {
                Toast.makeText(AddPhoto.this, "No photo saved", Toast.LENGTH_LONG).show();
            }
        }

    }
}
