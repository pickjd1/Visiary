package bit.walshbj2.pickjd1.visiary.visiary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseManager extends SQLiteOpenHelper{

    //table contents
    public static final String TABLE_NAME = "tblJournalEntries";
    public static final String COLUMN_ID = "journalID";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_LOCATION = "loaction";
    public static final String COLUMN_PIC_FILEPATH = "picFilePath";
    public static final String COLUMN_BLURB = "blurb";

    private static final String DATABASE_NAME = "journalEntree.db";
    private static final int DATABASE_VERSION = 1;

    //Database creation sql statement
    private static final String createQuery = "CREATE TABLE "
            + TABLE_NAME + "("+COLUMN_ID + "INEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_DATE + " DATE NOT NULL, "
            + COLUMN_LOCATION + " TEXT NOT NULL, "
            + COLUMN_PIC_FILEPATH + " BLOB NOT NULL, "
            + COLUMN_BLURB + " TEXT NOT NULL);" ;

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Create Database
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(createQuery);
    }

    //Method if upgrade needed
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseManager.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
