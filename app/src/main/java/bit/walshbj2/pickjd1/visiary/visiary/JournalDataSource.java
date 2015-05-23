package bit.walshbj2.pickjd1.visiary.visiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.net.ContentHandler;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JournalDataSource {

    //Database fields
    private SQLiteDatabase database;
    private DatabaseManager dbManager;
    private String[] allColumns = {DatabaseManager.COLUMN_ID, DatabaseManager.COLUMN_DATE, DatabaseManager.COLUMN_LOCATION, DatabaseManager.COLUMN_PIC_FILEPATH, DatabaseManager.COLUMN_BLURB};

    public  JournalDataSource(Context context) {
        dbManager = new DatabaseManager(context);
    }

    public void open() throws SQLException {
        // Gets the data repository in write mode
        database = dbManager.getWritableDatabase();
    }

    public void close() {
        dbManager.close();
    }

    //Add a new journal entry
    public void createJournalEntry(String date, String location, String picFilePath, String blurb) {
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DatabaseManager.COLUMN_DATE, date);
        values.put(DatabaseManager.COLUMN_LOCATION, location);
        values.put(DatabaseManager.COLUMN_PIC_FILEPATH, picFilePath);
        values.put(DatabaseManager.COLUMN_BLURB, blurb);

        // Insert the new row, returning the primary key value of the new row
        long insertRow = database.insert(DatabaseManager.TABLE_NAME, null, values);

        close();
    }

    //delete a JournalEntry
    public void deleteJournalEntry(JournalEntry journalEntry) {
        int id = journalEntry.getJournalID();
        database.delete(DatabaseManager.TABLE_NAME, DatabaseManager.COLUMN_ID + " = " + id, null);
    }

    public List<JournalEntry> getJournalEntryList() {
        List<JournalEntry> journalEntries = new ArrayList<>();

       String sortOrder = DatabaseManager.COLUMN_DATE + " DESC";

        Cursor cursor = database.query(DatabaseManager.TABLE_NAME, allColumns, null, null, null, null, sortOrder);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            JournalEntry entry= cursorToJournalEntry(cursor);
            journalEntries.add(entry);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();

        return journalEntries;
    }

    private JournalEntry cursorToJournalEntry(Cursor cursor) {
        JournalEntry je = new JournalEntry();
        je.setJournalID(cursor.getInt(0));
        je.setDate(cursor.getString(1));
        je.setLocation(cursor.getString(2));
        je.setPicFilePath(cursor.getString(3));
        je.setBlurb(cursor.getString(4));
        return je;
    }
}
