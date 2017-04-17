package com.note8.sanxing.models;

import com.google.gson.annotations.SerializedName;
import com.note8.sanxing.utils.ui.DateTimeUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by BenWwChen on 2017/4/4.
 */

public class Question implements Serializable {
    @SerializedName("_id")
    protected String questionId;
    protected String content;
    @SerializedName("answers")
    protected Integer answerCount;
    @SerializedName("likes")
    protected Integer likeCount;
    protected List<String> tags;
    @SerializedName("answered")
    protected boolean isAnswered;
    protected boolean isFavorite;
    @SerializedName("date_formatted")
    protected String date;
    protected String picture;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(Integer answerCount) {
        this.answerCount = answerCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDate() {
        return DateTimeUtils.parseDateTime(this.date, "yyyy-MM-dd HH:mm", "yyyy 年 MM 月 dd 日");
    }

    public void setDate(String date) {
        this.date = date;
    }
}
