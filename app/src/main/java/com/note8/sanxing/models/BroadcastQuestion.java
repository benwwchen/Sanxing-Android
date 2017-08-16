package com.note8.sanxing.models;

import com.note8.sanxing.utils.ui.DateTimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by BenWwChen on 2017/3/24.
 */

public class BroadcastQuestion extends Question {

    String releaseTime;

    public BroadcastQuestion(String questionId, String content, Integer answerCount, Integer likeCount,
                             boolean isAnswered, List<String> tags, boolean isFavorite, String releaseTime) {
        super.questionId = questionId;
        super.content = content;
        super.answerCount = answerCount;
        super.likeCount = likeCount;
        super.isAnswered = isAnswered;
        super.tags = tags;
        super.isFavorite = isFavorite;
        this.releaseTime = releaseTime;
    }

    public String getReleaseTime() {
        return DateTimeUtils.parseDateTime(this.date, "yyyy-MM-dd HH:mm", "yyyy-MM-dd");
    }

    private BroadcastQuestion(String content, String time) {
        this("0", content, 0, 0, false, null, false, time);
    }

    public static ArrayList<BroadcastQuestion> sampleQuestions = getSampleQuestions();

    private static ArrayList<BroadcastQuestion> getSampleQuestions() {
        ArrayList<BroadcastQuestion> sample= new ArrayList<>();
        sample.add(new BroadcastQuestion("为什么你考不上北大", "1小时前"));
        sample.add(new BroadcastQuestion("为什么你考不上北大", "1小时前"));
        sample.add(new BroadcastQuestion("为什么你考不上北大", "1小时前"));
        return sample;
    }

}
