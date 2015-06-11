package bit.walshbj2.pickjd1.visiary.visiary;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
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

        // Get data from database
        journalEntries = dataSource.getJournalEntryList();

        // If Database is Empty set Empty text view
        if(journalEntries.isEmpty())
        {
        ImageView empty = (ImageView) findViewById(R.id.imageViewEmptyList);
        empty.setVisibility(View.VISIBLE);
        }
        else { // If database is not empty add journal entries to setListView method
            setListView(journalEntries);
        }

        // Setup custom library button for onclick event
        at.markushi.ui.CircleButton addEntryButton = (at.markushi.ui.CircleButton) findViewById(R.id.imageButtonAddEntry);

        //Set the onClickListener to the add a Journal Entry image button.
        addEntryButton.setOnClickListener(new addEntryButton());

        // Initiate Edit Text
        EditText inputSearch = (EditText)findViewById(R.id.inputSearch);

        //Set TextWater
        inputSearch.addTextChangedListener(new searchController());
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

        Intent goTo = new Intent();
        switch (item.getItemId()) {
            case R.id.action_view_home:
                goTo = new Intent(this, Home.class);
                startActivity(goTo);
                startActivity(goTo);
                return true;

            case R.id.action_view_locations:
            goTo = new Intent(this, ViewAllLocations.class);
                startActivity(goTo);
            startActivity(goTo);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //*****************************************************************
    //  Start Intent: Go to addPhoto class
    //*****************************************************************
    public class addEntryButton implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Home.this, AddPhoto.class);
            startActivity(intent);
        }
    }

    //*****************************************************************
    //  Class that sets custom adapter and List View
    //*****************************************************************
    private void setListView(List<JournalEntry> journalEntry) {

        //Initiate ListView
        ListView journalListView = (ListView) findViewById(R.id.listViewJornalEntries);

        // Setup adapter class
        JournalEntryAdapter adapter = new JournalEntryAdapter(this, R.layout.journal_entries_layout, journalEntry );

        //Bind the listView to the above adapter
        journalListView.setAdapter(adapter);
    }

    //*****************************************************************
    //  Searches listView blurb data and resets ListView
    //*****************************************************************
    private class searchController implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence cs, int i, int i1, int i2) {
            //Set new JournalEntry List that will be sent to setListView()
            List<JournalEntry> nEntriesList = new ArrayList<>();
            // When user changes the Text, get string change to uppercase
            String charString = cs.toString().toUpperCase();

            //search JournalEntry Blurbs for characters that contain same characaters as charString
            for (JournalEntry je : journalEntries) {
                if(je.getBlurb().toUpperCase().contains(charString))nEntriesList.add(je);
            }

            // If ListView is not null, or empty update setListView to nEntriesList
            if(nEntriesList != null && !nEntriesList.isEmpty()) {
                setListView(nEntriesList);
            }
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }
}
