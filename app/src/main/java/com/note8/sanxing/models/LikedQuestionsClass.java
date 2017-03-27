package com.note8.sanxing.models;

import com.note8.sanxing.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WIKI on 2017/3/26 0026.
 */
public class LikedQuestionsClass{
    public int id;
    public String title;
    public String time;
    public int backgroundImg;
    public boolean hasBackground;

    public LikedQuestionsClass(int _id, String _title, String _time, int _backgroundImg, boolean _hasBackground) {
        id = _id;
        title = _title;
        time = _time;
        backgroundImg = _backgroundImg;
        hasBackground = _hasBackground;
    }
    public static List<LikedQuestionsClass> likedQuestionsList = initLikedQuestionsList();
    private static ArrayList<LikedQuestionsClass> initLikedQuestionsList() {
        ArrayList<LikedQuestionsClass> list = new ArrayList<>();
        LikedQuestionsClass temp = new LikedQuestionsClass(0, "深夜最能安抚人心的食物是什么", "2017年3月21日22:00", R.drawable.broadcast_question_background1, true);
        list.add(temp);
        temp = new LikedQuestionsClass(1, "生命剩下最后一个小时里你最想要吃什么", "2017年3月20日7:00", R.drawable.broadcast_question_background2, true);
        list.add(temp);
        temp = new LikedQuestionsClass(2, "最近上映电影《美女与野兽》，你看了吗", "2017年3月19日16:40", 0, false);
        list.add(temp);
        temp = new LikedQuestionsClass(3, "用一首歌形容今天下午", "2017年3月18日16:40", R.drawable.broadcast_question_background3, true);
        list.add(temp);
        return list;
    }
}
