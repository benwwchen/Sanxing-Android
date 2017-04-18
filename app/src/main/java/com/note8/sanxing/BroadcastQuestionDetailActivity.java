package com.note8.sanxing;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.note8.sanxing.adapters.BroadcastQuestionsAnswersAdapter;
import com.note8.sanxing.models.Answer;
import com.note8.sanxing.models.BroadcastQuestion;
import com.note8.sanxing.models.Question;
import com.note8.sanxing.utils.network.SanxingApiClient;
import com.note8.sanxing.utils.network.VolleyUtil;

import java.util.ArrayList;
import java.util.List;

public class BroadcastQuestionDetailActivity extends AppCompatActivity implements BroadcastQuestionsAnswersAdapter.BroadcastQustionsAnswersAdapterCallback {

    // data
    private BroadcastQuestion mQuestion;
    private ArrayList<Answer> mAnswers;
    private boolean isAnswer;
    private ArrayList<Question> favoriteQuestions;

    // views
    private RecyclerView mAnswersRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Menu mMenu;

    // utils
    private Context mContext;
    private ImageLoader mImageLoader;
    private int picMaxWidth;
    private int picMaxHeight;

    // adapter, handlers
    private BroadcastQuestionsAnswersAdapter mBroadcastQuestionsAnswersAdapter;
    private Handler mDataHandler;
    private Handler mFavoriteHandler;

    // request/response code
    private static final int REQUEST_CODE_ANSWER = 0;
    private static final int RESPONSE_CODE_SUCCESS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = BroadcastQuestionDetailActivity.this;

        mImageLoader = VolleyUtil.getInstance(this).getImageLoader();

        initView();

        loadData(); // load data from args

        refresh(); // get data from server

        setResult(RESPONSE_CODE_SUCCESS);
    }

    private void initView() {
        setContentView(R.layout.activity_broadcast_question_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mAnswersRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_broadcast_questions_answers);
        // set layout manager
        mAnswersRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        // swipe refresh layout
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.layout_swipe_refresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorBrilliant);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            public void onRefresh() {
                updateData();
            }
        });

        // Get pic max width/height (display width/height)
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        picMaxWidth = dm.widthPixels;
        picMaxHeight = dm.heightPixels;
    }

    private void loadData() {
        Intent intent = getIntent();
        mQuestion = (BroadcastQuestion) intent.getSerializableExtra("question");

        // load question, pic
        CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitle(mQuestion.getContent());

        TextView dateTextView = (TextView) findViewById(R.id.text_view_date);
        dateTextView.setText(mQuestion.getDate());

        ImageView coverImageView = (ImageView) findViewById(R.id.cover);
        String coverUrl = mQuestion.getPicture() + "?imageView2/2/w/" + picMaxWidth;
        mImageLoader.get(coverUrl, ImageLoader.getImageListener(coverImageView,
                R.color.cardview_light_background, R.color.cardview_light_background));

        // answers
        mAnswers = new ArrayList<>();
        mBroadcastQuestionsAnswersAdapter = new BroadcastQuestionsAnswersAdapter(mAnswers, mContext, this);
        mAnswersRecyclerView.setAdapter(mBroadcastQuestionsAnswersAdapter);

        mDataHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                List<Answer> answers;

                if (msg.what == SanxingApiClient.SUCCESS_CODE) {
                    answers = (List<Answer>) msg.obj;
                } else {
                    Log.d("error", (String) msg.obj);
                    answers = Answer.sampleAnswerData;
                }

                // update data & notify the adapter
                mAnswers.clear();
                mAnswers.addAll(answers);
                mBroadcastQuestionsAnswersAdapter.notifyDataSetChanged();
                if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        };

        // add/remove favorite handler
        mFavoriteHandler = new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                boolean isSuccess;

                if(msg.what == 1){
                    isSuccess = (boolean) msg.obj;
                } else {
                    Log.d("error", (String) msg.obj);
                    Toast.makeText(mContext, "错误:" + msg.obj, Toast.LENGTH_SHORT).show();
                    isSuccess = false;
                }

                if (isSuccess) {
                    String message = "已收藏";
                    if (mQuestion.isFavorite()) message = "已取消收藏";
                    Snackbar.make(mAnswersRecyclerView, message, Snackbar.LENGTH_SHORT).show();
                    checkIfFavorited();
                }
            }
        };
    }

    // trigger swipe to refresh
    public void refresh() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                updateData();
            }
        });
    }

    private void updateData() {
        SanxingApiClient.getInstance(mContext).getBroadcastQuestionsAnswers(mQuestion, mDataHandler);
        checkIfAnswered();
        checkIfFavorited();
        updateMenu();
    }

    private void updateMenu() {
        if (mQuestion.isFavorite()) {
            mMenu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_like, null));
        } else {
            mMenu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_notlike, null));
        }
    }

    private void checkIfAnswered() {
        AsyncTask check = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                isAnswer = SanxingApiClient.getInstance(mContext).isAnswer(mQuestion.getQuestionId());
                setFABStatus();
                return null;
            }
        };
        check.execute();
    }

    private void checkIfFavorited() {
        AsyncTask check = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                boolean isFavorite = SanxingApiClient.getInstance(mContext).isFavorite(mQuestion.getQuestionId());
                mQuestion.setFavorite(isFavorite);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                updateMenu();
                super.onPostExecute(o);
            }
        };
        check.execute();
    }

    private void setFABStatus() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (isAnswer) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,"你已经回答过这个问题了", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startAnswerActivity();
                }
            });
        }

    }

    private void startAnswerActivity() {
        Intent intent = new Intent(BroadcastQuestionDetailActivity.this, AnswerActivity.class);
        intent.putExtra("question", mQuestion);
        startActivity(intent);
    }

    private void favoritePressed() {
        Log.d("favorite pressed", "curState:" + mQuestion.isFavorite());
        SanxingApiClient.getInstance(mContext).addOrRemoveAFavoriteQuestion(mQuestion.getQuestionId(),
                !mQuestion.isFavorite(), mFavoriteHandler);
    }

    @Override
    public void onFavoriteButtonClick(String answerId, Boolean favorite) {
        Handler favoriteAnswerHandler = new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                boolean isSuccess;

                if(msg.what == 1){
                    isSuccess = (boolean) msg.obj;
                } else {
                    Log.d("error", (String) msg.obj);
                    Toast.makeText(mContext, "错误:" + msg.obj, Toast.LENGTH_SHORT).show();
                    isSuccess = false;
                }

                updateData();
            }
        };
        SanxingApiClient.getInstance(mContext).addOrRemoveAFavoriteAnswer(answerId, favorite, favoriteAnswerHandler);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_broadcast_question_detail, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_favorite:
                favoritePressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
