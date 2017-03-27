package com.note8.sanxing;

import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ScrollingView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.note8.sanxing.adapters.LikeAnswersAdapter;
import com.note8.sanxing.adapters.LikeQuestionsAdapter;
import com.note8.sanxing.models.LikedAnswersClass;
import com.note8.sanxing.models.LikedQuestionsClass;
import com.note8.sanxing.utils.ui.CustomGradientDrawable;
import com.note8.sanxing.utils.ui.StatusBarUtils;

import java.util.List;

public class MeActivity extends AppCompatActivity{

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private ImageView mainUserImg;
    private RelativeLayout me_topPanel;
    private ScrollView me_scrollView;

    private ImageButton returnBtn;
    private ImageButton setBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

        StatusBarUtils.setContentToTop(this);
        initBackgroundGradient();

        findViewById();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container_me);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_me);
        tabLayout.setupWithViewPager(mViewPager);

        //点击返回按钮，结束当前页面
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"该功能尚未开通,敬请期待", Toast.LENGTH_SHORT).show();
            }
        });


//    @Override
//    public void onResume(){
//        super.onResume();
//        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
//        mViewPager = (ViewPager) findViewById(R.id.container);
//        mViewPager.setAdapter(mSectionsPagerAdapter);
//    }

        /*mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP)
                    me_scrollView.requestDisallowInterceptTouchEvent(true);
                else
                    me_scrollView.requestDisallowInterceptTouchEvent(true);
                return false; }
        });*/
    }

        private void findViewById(){
            returnBtn = (ImageButton) findViewById(R.id.mePageReturn);
            setBtn = (ImageButton) findViewById(R.id.mePageSet);
        }

        private void initBackgroundGradient() {
            StatusBarUtils.setContentToTop(this);
            // set background gradient color
            CustomGradientDrawable gradientDrawable = new CustomGradientDrawable(
                    new int[] {0xfff78ca0, 0xfff9748f, 0xfffd868c, 0xfffe9a8b},
                    new float[] {0, 0.19f, 0.60f, 1});
            me_topPanel = (RelativeLayout) findViewById(R.id.me_topPanel);
            me_topPanel.setBackground(gradientDrawable);
        }

        public static class PlaceholderFragment extends Fragment {

            private static final String ARG_SECTION_NUMBER = "section_number";

            public PlaceholderFragment() {
            }

            public static PlaceholderFragment newInstance(int sectionNumber) {
                PlaceholderFragment fragment = new PlaceholderFragment();
                Bundle args = new Bundle();
                args.putInt(ARG_SECTION_NUMBER, sectionNumber);
                fragment.setArguments(args);
                return fragment;
            }


            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                int num = getArguments().getInt(ARG_SECTION_NUMBER);
                View rootView = null;
                switch (num) {
                    case 1: //Favourite questions
                        rootView = inflater.inflate(R.layout.recycler_view_like_questions, container, false);
                        initLikedQuestions(rootView);
                        break;
                    case 2: //Favourite answers
                        rootView = inflater.inflate(R.layout.recycler_view_like_answers, container, false);
                        initLikedAnswers(rootView);
                        break;
                    default:
                        break;
                }
                return rootView;
            }

            private void initLikedQuestions(final View rootView) {
                ListView listView = (ListView) rootView.findViewById(R.id.like_questions_list);
                List<LikedQuestionsClass> likedQuestionsList = LikedQuestionsClass.likedQuestionsList;
                LikeQuestionsAdapter adapter = new LikeQuestionsAdapter(rootView.getContext(), R.layout.item_like_questions, likedQuestionsList);
                listView.setAdapter(adapter);
            }

            private void initLikedAnswers(final View rootView) {
                ListView listView = (ListView) rootView.findViewById(R.id.like_answers_list);
                List<LikedAnswersClass> likeAnswersList = LikedAnswersClass.likedAnswersList;
                LikeAnswersAdapter adapter = new LikeAnswersAdapter(rootView.getContext(), R.layout.item_like_answers, likeAnswersList);
                listView.setAdapter(adapter);
            }

        }

        public class SectionsPagerAdapter extends FragmentPagerAdapter {

            public SectionsPagerAdapter(FragmentManager fm) {
                super(fm);
            }

            @Override
            public Fragment getItem(int position) {
                return PlaceholderFragment.newInstance(position + 1);
            }

            @Override
            public int getCount() {
                // Show 3 total pages.
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "喜欢的问题";
                    case 1:
                        return "喜欢的回答";
                }
                return null;
            }
        }
    }
