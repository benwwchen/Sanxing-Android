package com.note8.sanxing.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Answer {
    @SerializedName("_id")
    private String answerId;
    private String content;
    private String questionContent;
    private Integer mood;
    @SerializedName("likes")
    private Integer likeCount;
    private String date;
    private Answerer answerer;

    public static class Answerer {
        public String username;
        public String avatar;
    }

    public Answer(String answerId, String content, Integer mood, Integer likeCount, String date, Answerer answerer) {
        this.answerId = answerId;
        this.content = content;
        this.mood = mood;
        this.likeCount = likeCount;
        this.date = date;
        this.answerer = answerer;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public Integer getMood() {
        return mood;
    }

    public void setMood(Integer mood) {
        this.mood = mood;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Answerer getAnswerer() {
        return answerer;
    }

    public void setAnswerer(Answerer answerer) {
        this.answerer = answerer;
    }

    private Answer(String content, String date, Integer mood) {
        this.questionContent = content;
        this.date = date;
        this.mood = mood;
    }

    public static ArrayList<Answer> sampleAnswerData = initSampleData();

    private static ArrayList<Answer> initSampleData() {
        ArrayList<Answer> answerList = new ArrayList<>();
        answerList.add(new Answer("Item successfully delivered", "", 80));
        answerList.add(new Answer("Courier is out to delivery your order", "2017-02-12 08:00", 80));
        answerList.add(new Answer("Item has reached courier facility at New Delhi", "2017-02-11 21:00", 80));
        answerList.add(new Answer("Item has been given to the courier", "2017-02-11 18:00", 80));
        answerList.add(new Answer("Item is packed and will dispatch soon", "2017-02-11 09:30", 80));
        answerList.add(new Answer("Order is being readied for dispatch", "2017-02-11 08:00", 80));
        answerList.add(new Answer("Order processing initiated", "2017-02-10 15:00", 80));
        answerList.add(new Answer("Order confirmed by seller", "2017-02-10 14:30", 80));
        answerList.add(new Answer("Order placed successfully", "2017-02-10 14:00", 80));

        return answerList;
    }
}

