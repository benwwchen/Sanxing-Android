package com.note8.sanxing.models;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by BenWwChen on 2017/3/24.
 */

public class BroadcastQuestion {
    public BroadcastQuestion(String questionContent, String time) {
        this.questionContent = questionContent;
        this.time = time;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String questionContent;
    private String time;

    public static ArrayList<BroadcastQuestion> sampleQuestions = getSampleQuestions();

    private static ArrayList<BroadcastQuestion> getSampleQuestions() {
        ArrayList<BroadcastQuestion> sample= new ArrayList<>();
        sample.add(new BroadcastQuestion("为什么你考不上北大", "1小时前"));
        sample.add(new BroadcastQuestion("为什么你考不上北大", "1小时前"));
        sample.add(new BroadcastQuestion("为什么你考不上北大", "1小时前"));
        return sample;
    }

}
