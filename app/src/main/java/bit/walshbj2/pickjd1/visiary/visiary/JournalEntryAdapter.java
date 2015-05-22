package bit.walshbj2.pickjd1.visiary.visiary;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class JournalEntryAdapter extends ArrayAdapter<JournalEntry>{

    Context context;
    List<JournalEntry> entries;

    public JournalEntryAdapter (Context context, int resource, List<JournalEntry> entries) {
        super(context, resource, entries);

        this.entries = entries;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {

        //Get an inflater from the activity
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        //Inflate custom view
        View customView = inflater.inflate(R.layout.journal_entries_layout, container, false);

        //Grab references to controls
        TextView dateTV = (TextView) customView.findViewById(R.id.textViewDate);
        TextView locationTV = (TextView) customView.findViewById(R.id.textViewLocation);
        ImageView picIV = (ImageView) customView.findViewById(R.id.imageViewJournalPic);
        TextView blurbTV = (TextView) customView.findViewById(R.id.textViewBlurb);

        //Get the current inputItem
        JournalEntry currentEntry = getItem(position);

        //Use the instance data to initialise the view controls
        dateTV.setText(currentEntry.getDate());
        locationTV.setText(currentEntry.getLocation());

        blurbTV.setText(currentEntry.getBlurb());

        //Return the view!!
        return customView;
    }


}
