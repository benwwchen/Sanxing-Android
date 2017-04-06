package com.note8.sanxing.models;

import com.note8.sanxing.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WIKI on 2017/3/26 0026.
 */
public class LikedQuestionsClass{
    public int id;
    public String time;
    public String title;
    public String answerTxt;
    public int answerImg;
    public int broadcastImg;
    public boolean isBroadcastQuestion;

    public LikedQuestionsClass(int _id, String _time, String _title, String _answerTxt, int _answerImg, int _broadcastImg, boolean _isBroadcastQuestion) {
        id = _id;
        time = _time;
        title = _title;
        answerTxt = _answerTxt;
        answerImg = _answerImg;
        broadcastImg = _broadcastImg;
        isBroadcastQuestion = _isBroadcastQuestion;
    }
    public static List<LikedQuestionsClass> likedQuestionsList = initLikedQuestionsList();
    private static ArrayList<LikedQuestionsClass> initLikedQuestionsList() {
        ArrayList<LikedQuestionsClass> list = new ArrayList<>();
        LikedQuestionsClass temp = new LikedQuestionsClass(0, "2017年3月21日22:00", "深夜最能安抚人心的食物是什么","" ,0, R.drawable.broadcast_question_background1, true);
        list.add(temp);
        temp = new LikedQuestionsClass(1, "2017年3月20日7:00", "生命剩下最后一个小时里你最想要吃什么","",0, R.drawable.broadcast_question_background2, true);
        list.add(temp);
        temp = new LikedQuestionsClass(2, "2017年3月19日16:40","你看腻了哪些打着极简旗号的东西？","没有图案的东西都叫性冷淡",R.drawable.broadcast_question_background2 , 0, false);
        list.add(temp);
        temp = new LikedQuestionsClass(3, "2017年3月18日16:40", "用一首歌形容今天下午","",0, R.drawable.broadcast_question_background3, true);
        list.add(temp);
        return list;
    }
}
