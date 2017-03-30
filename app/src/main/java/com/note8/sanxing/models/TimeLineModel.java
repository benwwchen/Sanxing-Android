package com.note8.sanxing.models;

import java.util.ArrayList;

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

    public static ArrayList<TimeLineModel> sampleTimelineData = initSampleData();

    private static ArrayList<TimeLineModel> initSampleData() {
        ArrayList<TimeLineModel> timeLineList = new ArrayList<>();
        timeLineList.add(new TimeLineModel("Item successfully delivered", "", "Happy"));
        timeLineList.add(new TimeLineModel("Courier is out to delivery your order", "2017-02-12 08:00", "Happy"));
        timeLineList.add(new TimeLineModel("Item has reached courier facility at New Delhi", "2017-02-11 21:00", "Happy"));
        timeLineList.add(new TimeLineModel("Item has been given to the courier", "2017-02-11 18:00", "Happy"));
        timeLineList.add(new TimeLineModel("Item is packed and will dispatch soon", "2017-02-11 09:30", "Happy"));
        timeLineList.add(new TimeLineModel("Order is being readied for dispatch", "2017-02-11 08:00", "Happy"));
        timeLineList.add(new TimeLineModel("Order processing initiated", "2017-02-10 15:00", "Happy"));
        timeLineList.add(new TimeLineModel("Order confirmed by seller", "2017-02-10 14:30", "Happy"));
        timeLineList.add(new TimeLineModel("Order placed successfully", "2017-02-10 14:00", "Happy"));

        return timeLineList;
    }
}

