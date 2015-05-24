package bit.walshbj2.pickjd1.visiary.visiary;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        ImageView picIV = (ImageView) customView.findViewById(R.id.imageViewJournalPic);
        TextView blurbTV = (TextView) customView.findViewById(R.id.textViewBlurb);

        //Get the current inputItem
        JournalEntry currentEntry = getItem(position);

        //Format Date
        //Create a new formatted timestamp showing todays date and time

        String date = currentEntry.getDate();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");

        Date formatInDateFrom = null;
        // Parse String date to Date
        try {
            formatInDateFrom = (Date) formatter.parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Set up new format ("WeekDay, Day, Month ' Year Hour:Mintues M")
        SimpleDateFormat newFormat = new SimpleDateFormat(
                "EEE, d MMM, ''yy h:mm aaa");
        // reformat date and put back into a string
        String formattedDate = newFormat.format(formatInDateFrom);

        //Get the filePath of the image, make it into a bitmap.
        String filePath = currentEntry.getPicFilePath();
        Bitmap userPhoto = BitmapFactory.decodeFile(filePath);

        //Use the instance data to initialise the view controls
        dateTV.setText(formattedDate);
        picIV.setImageBitmap(userPhoto);
        blurbTV.setText(currentEntry.getBlurb());

        //Return the view!!
        return customView;
    }


}
