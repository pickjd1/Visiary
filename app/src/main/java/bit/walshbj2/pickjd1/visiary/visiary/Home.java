package bit.walshbj2.pickjd1.visiary.visiary;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;


public class Home extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //setListView();
        //Retrieve resources
        ImageButton addEntry = (ImageButton) findViewById(R.id.imageButtonAddEntry);

        //Set the onClickListener to the add a Journal Entry image button.
        addEntry.setOnClickListener(new addEntryButton());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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

    public class addEntryButton implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(Home.this, AddPhoto.class);
            startActivity(intent);
        }
    }

//    public class JournalEntry {
//        String date;
//        String location;
//        Drawable journalImage;
//        String blurb;
//
//        public JournalEntry(String date, String location, Drawable journalImage, String blurb) {
//            this.date = date;
//            this.location = location;
//            this.journalImage = journalImage;
//            this.blurb = blurb;
//        }
//    }

    private void setListView() {

        ListView journalListView = (ListView) findViewById(R.id.listViewJornalEntries);

    }
}
