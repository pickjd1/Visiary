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

import java.sql.SQLException;
import java.util.List;


public class Home extends ActionBarActivity {

    List<JournalEntry> journalEntries;
   JournalDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        dataSource = new JournalDataSource(this);
        try {
            dataSource.open();
            //dataSource.UpdateDatbase(); ***************Used to update the database
        } catch (SQLException e) {
            e.printStackTrace();
        }

        journalEntries = dataSource.getJournalEntryList();

        setListView();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_view_locations) {
            Intent allLocations = new Intent(this, ViewAllLocations.class);
            startActivity(allLocations);
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

    private void setListView() {

        ListView journalListView = (ListView) findViewById(R.id.listViewJornalEntries);

        JournalEntryAdapter adapter = new JournalEntryAdapter(this, R.layout.journal_entries_layout, journalEntries );

        //Bind the listView to the above adapter
        journalListView.setAdapter(adapter);
    }
}
