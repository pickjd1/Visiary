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
        EditText etAboutPhoto = (EditText) findViewById(R.id.etAboutPhoto);
        TextView tvCurrentDate = (TextView) findViewById(R.id.tvCurrentDate);

        //Create a new formatted timestamp showing todays date and time
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date currentTime = new Date();
        timeStamp = timeStampFormat.format(currentTime);

        //Set the timestamp to be displayed in the date text view
        tvCurrentDate.setText(timeStamp);

        //Create a new on click handler for the image button
        View.OnClickListener addImageOnClick = new AddImageOnClick();

        //Set the listener to the image button
        btnAddImage.setOnClickListener(addImageOnClick);
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

        File imageStorageDirectory = new File(imageRootPath, "CameraDemo1");
        if(!imageStorageDirectory.exists())
        {
            imageStorageDirectory.mkdirs();
        }

        mPhotoFileName = "IMG_" + timeStamp + ".jpg";

        File photoFile = new File((imageStorageDirectory.getPath()+ File.separator + mPhotoFileName));

        return photoFile;
    }


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
            if (requestCode == RESULT_OK)
            {
                String realFilePath =mPhotoFile.getPath();

                Bitmap userPhoto = BitmapFactory.decodeFile(realFilePath);

                btnAddImage.setImageBitmap(userPhoto);

            }
            else
            {
                Toast.makeText(AddPhoto.this, "No photo saved", Toast.LENGTH_LONG).show();
            }
        }

    }
}
