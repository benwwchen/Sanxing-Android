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
    public int publicStatus;
    public int mood;
    public int answerCount;
    public boolean hasAnswerTxt;
    public boolean hasAnswerImg;

    public LikedAnswersClass(int _id, int _portraitImg, String _title, String _answerTxt, int _answerImg,
                             int _publicStatus, int _mood, int _answerCount, boolean _hasAnswerTxt, boolean _hasAnswerImg) {
        id = _id;
        portraitImg = _portraitImg;
        title = _title;
        answerTxt = _answerTxt;
        answerImg = _answerImg;
        publicStatus = _publicStatus;
        mood = _mood;
        answerCount = _answerCount;
        hasAnswerTxt = _hasAnswerTxt;
        hasAnswerImg = _hasAnswerImg;

    }
    public static List<LikedAnswersClass> likedAnswersList = initLikedAnswersList();
    private static ArrayList<LikedAnswersClass> initLikedAnswersList() {
        ArrayList<LikedAnswersClass> list = new ArrayList<>();
        LikedAnswersClass temp = new LikedAnswersClass(0, R.drawable.portrait1, "你跑步的时候在想什么", "卧槽我这是第几圈？！", R.drawable.liked_answer_img1, 1, 0, 2, true ,true);
        list.add(temp);
        temp = new LikedAnswersClass(1, R.drawable.portrait2, "你感到最孤独的时刻是", "第二杯半价", 0, 0, 25, 1, true ,false);
        list.add(temp);
        temp = new LikedAnswersClass(2, R.drawable.portrait3, "怎么克服「晚睡拖延症」", "明明困的要死 就是死死抓着手机不放 \n" + "可能今晚过去了 就再也没有这样的晚上", R.drawable.liked_answer_img2, 1, 50, 3, true ,false);
        list.add(temp);
        temp = new LikedAnswersClass(3, R.drawable.portrait4, "大圆脸是什么样的一种体验", "有一种被岁月磨平了棱角的感觉", 0, 0, 80, 1, true ,false);
        list.add(temp);
        temp = new LikedAnswersClass(4, R.drawable.portrait5, "黑镜第三季第六集的结局是什么意思？", "政府肯定不想让凶手落入法网，因为凶手是通过国家安全局秘密监视系统的后门操控蜜蜂进行攻击的，抓到凶手就等于把自己监视民众的行为暴露无疑，这届政府就药丸。所以为了平息民愤，政府开始问责当时的案件查办者，并且关闭人造蜜蜂系统防止此类事件再次发生。女主知道自己和助手会被当成挡箭牌，所以伪造了助手自杀的假象保护助手寻找真凶。", 0, 0, 50, 0, true ,false);
        list.add(temp);
        return list;
    }
}
