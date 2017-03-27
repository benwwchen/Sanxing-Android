package com.note8.sanxing.models;

import com.note8.sanxing.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WIKI on 2017/3/26 0026.
 */
public class LikedAnswersClass {
    public int id;
    public int portraitImg;
    public String title;
    public String answerTxt;
    public int answerImg;
    public boolean hasAnswerTxt;
    public boolean hasAnswerImg;

    public LikedAnswersClass(int _id, int _portraitImg, String _title,
                            String _answerTxt, int _answerImg, boolean _hasAnswerTxt, boolean _hasAnswerImg) {
        id = _id;
        portraitImg = _portraitImg;
        title = _title;
        answerTxt = _answerTxt;
        answerImg = _answerImg;
        hasAnswerTxt = _hasAnswerTxt;
        hasAnswerImg = _hasAnswerImg;

    }
    public static List<LikedAnswersClass> likedAnswersList = initLikedAnswersList();
    private static ArrayList<LikedAnswersClass> initLikedAnswersList() {
        ArrayList<LikedAnswersClass> list = new ArrayList<>();
        LikedAnswersClass temp = new LikedAnswersClass(0, R.drawable.portrait1, "说一句你最喜欢的歌词", "你的无言，说出最美的誓言", 0, true ,false);
        list.add(temp);
        temp = new LikedAnswersClass(1, R.drawable.portrait2, "说一句你最喜欢的歌词", "有时候听这些歌就是在听自己的心情，任凭思绪慢慢流淌", 0, true ,false);
        list.add(temp);
        temp = new LikedAnswersClass(2, R.drawable.portrait3, "说一句你最喜欢的歌词", "第一次听他的歌，却意外的喜欢", 0, true ,false);
        list.add(temp);
        temp = new LikedAnswersClass(3, R.drawable.portrait4, "说一句你最喜欢的歌词", "一别两宽 我却生不起欢喜", 0, true ,false);
        list.add(temp);
        temp = new LikedAnswersClass(4, R.drawable.portrait5, "说一句你最喜欢的歌词", "宁愿本也不要悔恨", 0, true ,false);
        list.add(temp);
        return list;
    }
}
