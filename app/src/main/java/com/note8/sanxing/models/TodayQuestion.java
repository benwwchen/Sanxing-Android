package com.note8.sanxing.models;

import java.util.ArrayList;

/**
 * Created by BenWwChen on 2017/3/24.
 */

public class TodayQuestion {
    public TodayQuestion(String questionContent, Integer answerCount, Integer favoriteCount) {
        this.questionContent = questionContent;
        this.answerCount = answerCount;
        this.favoriteCount = favoriteCount;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public Integer getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(Integer answerCount) {
        this.answerCount = answerCount;
    }

    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(Integer favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    private String questionContent;
    private Integer answerCount;
    private Integer favoriteCount;

    public static ArrayList<TodayQuestion> sampleQuestions = getSampleQuestions();

    private static ArrayList<TodayQuestion> getSampleQuestions() {
        ArrayList<TodayQuestion> sample= new ArrayList<>();
        sample.add(new TodayQuestion("为什么你考不上北大", 345, 68));
        sample.add(new TodayQuestion("为什么你考不上北大", 345, 68));
        sample.add(new TodayQuestion("为什么你考不上北大", 345, 68));
        return sample;
    }

}
