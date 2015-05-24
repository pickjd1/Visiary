package bit.walshbj2.pickjd1.visiary.visiary;

import android.graphics.drawable.Drawable;

public class JournalEntry {

    private  int journalID;
    private String date;
    private double locationLat;
    private double locationLong;
    private String picFilePath;
    private String blurb;

    public int getJournalID(){
        return journalID;
    }

    public void setJournalID(int journalID){
        this.journalID = journalID;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public double getLocationLat(){
        return locationLat;
    }

    public void setLocationLat(double locationLat){
        this.locationLat = locationLat;
    }

    public double getLocationLong(){
        return locationLong;
    }

    public void setLocationLong(double locationLong){
        this.locationLong = locationLong;
    }

    public String getPicFilePath(){
        return picFilePath;
    }

    public void setPicFilePath(String picFilePath){
        this.picFilePath=picFilePath;
    }

    public String getBlurb(){
        return blurb;
    }

    public void setBlurb(String blurb){
        this.blurb = blurb;
    }
}
