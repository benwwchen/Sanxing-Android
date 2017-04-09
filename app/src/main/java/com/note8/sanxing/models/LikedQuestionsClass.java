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
    public int publicStatus;
    public int mood;
    public int answerCount;
    public int broadcastImg;
    public boolean isBroadcastQuestion;

    public LikedQuestionsClass(int _id, String _time, String _title, String _answerTxt, int _answerImg, int _publicStatus, int _mood, int _answerCount, int _broadcastImg, boolean _isBroadcastQuestion) {
        id = _id;
        time = _time;
        title = _title;
        answerTxt = _answerTxt;
        answerImg = _answerImg;
        publicStatus = _publicStatus;
        mood = _mood;
        answerCount = _answerCount;
        broadcastImg = _broadcastImg;
        isBroadcastQuestion = _isBroadcastQuestion;
    }
    public static List<LikedQuestionsClass> likedQuestionsList = initLikedQuestionsList();
    private static ArrayList<LikedQuestionsClass> initLikedQuestionsList() {
        ArrayList<LikedQuestionsClass> list = new ArrayList<>();
        LikedQuestionsClass temp = new LikedQuestionsClass(0, "2017年4月9日22:00", "深夜最能安抚人心的食物是什么","蒸羊羔、蒸熊掌、蒸鹿尾儿、烧花鸭、烧雏鸡、烧子鹅、卤猪、卤鸭、酱鸡、腊肉、松花小肚儿、晾肉、香肠儿、什锦苏盘、熏鸡白肚儿、清蒸八宝猪、江米酿鸭子、罐儿野鸡、罐儿鹌鹑、卤什件儿、卤子鹅、山鸡、兔脯、菜蟒、银鱼、清蒸哈什蚂、烩鸭丝、烩鸭腰、烩鸭条、清拌鸭丝、黄心管儿、焖白鳝、焖黄鳝、豆豉鲇鱼、锅烧鲤鱼、烀烂甲鱼、抓炒鲤鱼、抓炒对儿虾、软炸里脊、软炸鸡、什锦套肠儿、卤煮寒鸦儿、麻酥油卷儿、熘鲜蘑、熘鱼脯、熘鱼肚、熘鱼片儿、醋熘肉片儿、烩三鲜、烩白蘑、烩鸽子蛋、炒银丝、烩鳗鱼、炒白虾、炝青蛤、炒面鱼、炒竹笋、芙蓉燕菜、炒虾仁儿、烩虾仁儿、烩腰花儿、烩海参、炒蹄筋儿、锅烧海参、锅烧白菜、炸木耳、炒肝尖儿、桂花翅子、清蒸翅子、炸飞禽。炸汁儿、炸排骨、清蒸江瑶柱、糖熘芡仁米、拌鸡丝、拌肚丝、什锦豆腐、什锦丁儿、糟鸭、糟熘鱼片儿、熘蟹肉、炒蟹肉、烩蟹肉、清拌蟹肉、蒸南瓜、酿倭瓜、炒丝瓜、酿冬瓜．烟鸭掌儿、焖鸭掌儿、焖笋、炝茭白、茄子晒炉肉、鸭羹、蟹肉羹、鸡血汤、三鲜木樨汤、红丸子、白丸子、南煎丸子、四喜丸子、三鲜丸子、氽丸子、鲜虾丸子、鱼脯丸子、饹炸丸子、豆腐丸子、樱桃肉、马牙肉、米粉肉、一品肉、栗子肉、坛子肉、红焖肉、黄焖肉、酱豆腐肉、晒炉肉、炖肉、黏糊肉、烀肉、扣肉、松肉、罐儿肉、烧肉、大肉、烤肉、白肉、红肘子、白肘子、熏肘子、水晶肘子、蜜蜡肘子、锅烧肘子、扒肘条、炖羊肉、酱羊肉、烧羊肉、烤羊肉、清羔羊肉、五香羊肉、氽三样儿、爆三样儿、炸卷果儿、烩散丹、烩酸燕儿、烩银丝、烩白杂碎、氽节子、烩节子、炸绣球、三鲜鱼翅、栗子鸡、氽鲤鱼、酱汁鲫鱼、活钻鲤鱼、板鸭、筒子鸡、烩脐肚、烩南荠、爆肚仁儿、盐水肘花儿、锅烧猪蹄儿、拌稂子、炖吊子、烧肝尖儿、烧肥肠儿、烧心、烧肺、烧紫盖儿、烧连帖、烧宝盖儿、油炸肺、酱瓜丝儿、山鸡丁儿、拌海蜇、龙须菜、炝冬笋、玉兰片、烧鸳鸯、烧鱼头、烧槟子、烧百合、炸豆腐、炸面筋、炸软巾、糖熘饹儿、拔丝山药、糖焖莲子、酿山药、杏仁儿酪、小炒螃蟹、氽大甲、炒荤素儿、什锦葛仙米、鳎目鱼、八代鱼、海鲫鱼、黄花鱼、鲥鱼、带鱼、扒海参、扒燕窝、扒鸡腿儿、扒鸡块儿、扒肉、扒面筋、扒三样儿、油泼肉、酱泼肉、炒虾黄、熘蟹黄、炒子蟹、炸子蟹、佛手海参、炸烹儿、炒芡子米、奶汤、翅子汤、三丝汤、熏斑鸠、卤斑鸠、海白米、烩腰丁儿、火烧茨菰、炸鹿尾儿、焖鱼头、拌皮渣儿、氽肥肠儿、炸紫盖儿、鸡丝豆苗、十二台菜、汤羊、鹿肉、驼峰、鹿大哈、插根儿、炸花件儿，清拌粉皮儿、炝莴笋、烹芽韭、木樨菜、烹丁香、烹大肉、烹白肉、麻辣野鸡、烩酸蕾、熘脊髓、咸肉丝儿、白肉丝儿、荸荠一品锅、素炝春不老、清焖莲子、酸黄菜、烧萝卜、脂油雪花儿菜、烩银耳、炒银枝儿、八宝榛子酱、黄鱼锅子、白菜锅子、什锦锅子、汤圆锅子、菊花锅子、杂烩锅子、煮饽饽锅子、肉丁辣酱、炒肉丝、炒肉片儿、烩酸菜、烩白菜、烩豌豆、焖扁豆、氽毛豆、炒豇豆，外加腌苤蓝丝儿。" ,0, 1, 100, 1, R.drawable.broadcast_question_background1, true);
        list.add(temp);
        temp = new LikedQuestionsClass(1, "2017年4月8日7:00", "若生命只剩下最后一个小时，你最想要吃什么","妈妈做的饭",0, 1, 20, 2, R.drawable.broadcast_question_background2, true);
        list.add(temp);
        temp = new LikedQuestionsClass(2, "2017年4月6日16:40","最近看了什么电影？有什么感悟？","最近重温了《模仿游戏》。\n\n不得不说《模仿游戏》是一部不错的电影，电影讲述了“计算机科学之父”艾伦·图灵的传奇人生，表现了图灵对英国历史的贡献和短暂的一生，主演也是大家比较熟悉的本尼迪克特·康伯巴奇和凯拉·奈特莉（至少我个人比较熟悉）。但今天要谈的不是艺术层面上的观后感，而是从影片中聚焦于图灵协助盟军破译德国密码系统Enigma的历程。\n" +
                "\n从网上我了解到了Enigma采用复式字母替换加密方法，利用键盘、转子、跳线、反射板、显示器进行对称加密/解密。简单来说就是当你按键盘上任意一个字母的时候，该字母会经过键盘-转子-跳线-反射板-显示器而被转换为另一个字母。每转换一次，转子会转动一格。这些在影片中都有体现。而Enigma属于算法和密钥分离的加密方法，破解Enigma的难度在于不知道当前密钥，包括转子的初始位置和跳线设置。在日常使用时，Enigma的配置可以通过密码本、事先商定和使用前一天密钥加密传送等方式。\n" +
                "\n图灵是在前人基础上完成Enigma最终破解的，之前使用雷耶夫斯基破解方法时，破解依赖于德国人使用Enigma时的一个坏习惯，即：使用特定的开头来进行校验，防止传送错误（电影里是CILLY，实际应该是CILLY的缩写CILCIL），这就给推算密钥提供了方便。（典型的信息安全管理漏洞）随后图灵则是从整个密文入手，设计了“图灵炸弹机”来进行破解（电影里把雷耶夫斯基的功劳也给了图灵）。\n" +
                "\n图灵采用的方法今天被叫做基于明文的攻击，因为德国人会在特定时间发送特定的电报，比如早上发天气预报，那么破解者就可以根据德语“天气”这个词的明文/密文来推断密钥，因此，接收到密文后，首先由密文分析员（就是图灵的太太从事的需要很高填字技巧的职业）对密文进行分析，试图找出特定的明文，接着使用图灵制造的三台连接在一起的Enigma自动破解机进行破解尝试。（三台串接可以使转接盘无效化）“天气”和“希特勒”这两个词就是“明文库”中最常见的两个单词。所以，图灵只需要在早上六点多的电报密文中寻找“天气”和“希特勒”两个单词加密后的密文，就能根据明文和密文的对应关系计算出密钥。\n" +
                "\n在实际破解过程中采用的是暴力破解方法，即是使用明文尝试所有可能的密钥，直到生成与密文中任意字符串匹配的结果，此时使用的密钥就是当天的Engima密钥。\n" +
                "\n回到影片主人公图灵上，百科上描述到：他对计算机的重要贡献在于他提出的有限状态自动机也就是图灵机的概念，对于人工智能，他提出了重要的衡量标准“图灵测试”，如果有机器能够通过图灵测试，那他就是一个完全意义上的智能机，和人没有区别了。他杰出的贡献使他成为计算机界的第一人，人们为了纪念这位伟大的科学家将计算机界的最高奖定名为“图灵奖”。图灵奖也成为了所有从事计算机行业的人所追求的最高荣誉。\n",R.drawable.liked_question_img2,1,70,5,0, false);
        list.add(temp);
        temp = new LikedQuestionsClass(3, "2017年4月1日16:40","你看腻了哪些打着极简旗号的东西","没有图案的东西都叫性冷淡",R.drawable.liked_question_img1 , 0,0,1,0, false);
        list.add(temp);
        temp = new LikedQuestionsClass(4, "2017年3月18日16:40", "用一首歌形容今天下午","痒",0, 1, 50, 2, R.drawable.broadcast_question_background3, true);
        list.add(temp);
        return list;
    }
}
