package bit.walshbj2.pickjd1.visiary.visiary;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ViewAllLocations extends FragmentActivity {

    List<JournalEntry> journalEntries;
    JournalDataSource dataSource;
    JournalEntry focusEntry;

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_locations);

        dataSource = new JournalDataSource(this);
        try {
            dataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Need to check if bundle is not empty, get the date of the selected entry, and set focus to that specific entry.
        Intent startIntent = getIntent();
        String focusDateString = "";
        focusDateString = startIntent.getStringExtra("entryDate");

        journalEntries = dataSource.getJournalEntryList();

        if(!(focusDateString == null)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");

            Date focusDate = null;

            // Parse String date to Date
            try {
                focusDate = (Date) formatter.parse(focusDateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            for(JournalEntry j : journalEntries)
            {
                if(j.getDate().equals(focusDate))
                {
                    focusEntry = j;
                }
            }
        }

        setUpMapIfNeeded();

        //setUpMap();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));

        LatLng latLng = new LatLng(0,0);


        for(JournalEntry je : journalEntries)
        {
            latLng = new LatLng(je.getLocationLat(), je.getLocationLong());

            String date = je.getDate();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");

            Date formatInDateFrom = null;
            // Parse String date to Date
            try {
                formatInDateFrom = (Date) formatter.parse(date);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // Set up new format ("WeekDay, Day, Month ' Year Hour:Minutes M")
            SimpleDateFormat newFormat = new SimpleDateFormat(
                    "EEE, d MMM, ''yy h:mm aaa");
            // reformat date and put back into a string
            String formattedDate = newFormat.format(formatInDateFrom);

           // mMap.addMarker(new MarkerOptions().position(new LatLng(je.getLocationLat(), je.getLocationLong())).title(je.getDate()));
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(je.getBlurb());
            mMap.addMarker(options);
        }

        if(focusEntry != null)
        {
            latLng = new LatLng(focusEntry.getLocationLat(), focusEntry.getLocationLong());
        }

        float zoomLevel = (float) 16.0; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));

        //Set up the info click listener to return to the journal entry when the info window is clicked
        GoogleMap.OnInfoWindowClickListener infoClick = new InfoClick();
        mMap.setOnInfoWindowClickListener(infoClick);
    }

    public class InfoClick implements GoogleMap.OnInfoWindowClickListener {
        @Override
        public void onInfoWindowClick(Marker marker) {
            Intent goToJournal = new Intent(ViewAllLocations.this, Home.class);
            goToJournal.putExtra("markerDescription", marker.getTitle());
            startActivity(goToJournal);
        }
    }
}
