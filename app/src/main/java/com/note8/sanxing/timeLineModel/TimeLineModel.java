package com.note8.sanxing.timeLineModel;

public class TimeLineModel {
    private String mMessage;
    private String mDate;
    private String mMood;  //  happy or sad or just-so-so

    public TimeLineModel(String msg, String date, String mood) {
        this.mMessage = msg;
        this.mDate = date;
        this.mMood = mood;
    }

    public String getMessage() {
        return this.mMessage;
    }

    public String getDate() {
        return this.mDate;
    }

    public String getMood() {
        return this.mMood;
    }
}

