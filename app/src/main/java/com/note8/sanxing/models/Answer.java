package com.note8.sanxing.models;

import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.note8.sanxing.utils.ui.DateTimeUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Answer{
    @SerializedName("_id")
    private String answerId;
    private String content;
    private String questionId;
    private String questionContent;
    private Integer mood;
    private Integer publicStatus;
    @SerializedName("likes")
    private Integer likeCount;
    @SerializedName("date_formatted")
    private String date;
    @SerializedName("time_from_now")
    private String timeFromNow;
    private Answerer answerer;
    private Boolean isFavorite;
    @SerializedName("public")
    private boolean isPublic;

    private boolean isFirst;

    public static class Answerer {
        public Answerer(String username) {
            this.username = username;
        }

        public String username;
        public String avatar;
    }

    public static HashMap<String, Integer> positionMap = new HashMap<>();

    public Answer(String answerId, String content, Integer mood,Integer publicStatus,
                  Integer likeCount, String date, Answerer answerer) {
        this.answerId = answerId;
        this.content = content;
        this.mood = mood;
        this.publicStatus = publicStatus;
        this.likeCount = likeCount;
        this.date = date;
        this.answerer = answerer;
    }

    private Answer(String content, String date, Integer mood, boolean isFirst) {
        this.questionContent = content;
        this.date = date;
        this.mood = mood;
        this.isFirst = isFirst;

        String aKey = DateTimeUtils.parseDateTime(this.date, "yyyy-MM-dd HH:mm", "yyyy-M-d");
        if (isFirst) {
            Answer.positionMap.put(aKey, Answer.positionMap.size() * 3);
        }
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

    public Integer getPublicStatus() {
        return publicStatus;
    }

    public void setPublicStatus(Integer publicStatus) {
        this.publicStatus = publicStatus;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public String getTime() {
        return DateTimeUtils.parseDateTime(this.date, "yyyy-MM-dd HH:mm", "HH:mm");
    }

    public String getDate() {
        return DateTimeUtils.parseDateTime(this.date, "yyyy-MM-dd HH:mm", "yyyy-MM-dd");
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isFirst() {
        return this.isFirst;
    }

    public void setFirst(boolean first) {this.isFirst = first;}

    public Answerer getAnswerer() {
        return answerer;
    }

    public void setAnswerer(Answerer answerer) {
        this.answerer = answerer;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }

    public String getTimeFromNow() {
        return timeFromNow;
    }

    public void setTimeFromNow(String timeFromNow) {
        this.timeFromNow = timeFromNow;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public Answer(String content, String questionContent, Integer mood, Integer publicStatus, Integer likeCount, String date, String timeFromNow, Answerer answerer, Boolean isFavorite) {
        this.content = content;
        this.questionContent = questionContent;
        this.mood = mood;
        this.publicStatus = publicStatus;
        this.likeCount = likeCount;
        this.date = date;
        this.timeFromNow = timeFromNow;
        this.answerer = answerer;
        this.isFavorite = isFavorite;
    }

    public static ArrayList<Answer> sampleAnswerData = initSampleData();

    private static ArrayList<Answer> initSampleData() {
        ArrayList<Answer> answerList = new ArrayList<>();
        answerList.add(new Answer("答案", "问题", 80, 0, 2, "2017", "1 minute ago", new Answerer("name"), false));
        answerList.add(new Answer("答案2", "问题", 80, 0, 2, "2017", "1 minute ago", new Answerer("name"), false));
        answerList.add(new Answer("答案3", "问题", 80, 0, 2, "2017", "1 minute ago", new Answerer("name"), false));
        answerList.add(new Answer("答案4", "问题", 80, 0, 2, "2017", "1 minute ago", new Answerer("name"), false));

        return answerList;
    }
}

