package com.note8.sanxing.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BenWwChen on 2017/3/24.
 */

public class TodayQuestion extends Question {

    private String time;

    public TodayQuestion(String questionId, String content, Integer answerCount, Integer likeCount,
                         boolean isAnswered, List<String> tags, boolean isFavorite, String time) {
        super.questionId = questionId;
        super.content = content;
        super.answerCount = answerCount;
        super.likeCount = likeCount;
        super.isAnswered = isAnswered;
        super.tags = tags;
        super.isFavorite = isFavorite;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private TodayQuestion(String content, Integer answerCount, Integer likeCount, String time) {
        this("0",content,answerCount,likeCount,false,null,false,time);
    }

    public static ArrayList<TodayQuestion> sampleQuestions = getSampleQuestions();

    private static ArrayList<TodayQuestion> getSampleQuestions() {
        ArrayList<TodayQuestion> sample= new ArrayList<>();
        sample.add(new TodayQuestion("为什么你考不上北大", 345, 68, "morning"));
        sample.add(new TodayQuestion("为什么你考不上北大", 345, 68, "noon"));
        sample.add(new TodayQuestion("为什么你考不上北大", 345, 68, "evening"));
        return sample;
    }

}
