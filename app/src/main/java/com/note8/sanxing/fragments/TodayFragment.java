package com.note8.sanxing.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.note8.sanxing.AnswerActivity;
import com.note8.sanxing.MainActivity;
import com.note8.sanxing.QuestionDetailActivity;
import com.note8.sanxing.R;
import com.note8.sanxing.adapters.TodayQuestionsAdapter;
import com.note8.sanxing.listeners.OnItemClickListener;
import com.note8.sanxing.models.TodayQuestion;
import com.note8.sanxing.models.Answer;
import com.note8.sanxing.utils.network.SanxingApiClient;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TodayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TodayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodayFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private Context mContext;

    // request/response code
    private static final int REQUEST_CODE_ANSWER = 0;
    private static final int RESPONSE_CODE_SUCCESS = 1;

    // views
    View mTodayView; // rootView
    private SwipeMenuRecyclerView mTodayQuestionsRecyclerView;

    // adapters
    private TodayQuestionsAdapter mTodayQuestionsAdapter;

    // data
    ArrayList<TodayQuestion> mTodayQuestions;
    List<Answer> mAnswers = Answer.sampleAnswerData;

    // handlers
    Handler mTodayQuestionsHandler;
    Handler mAnswersHandler;

    public TodayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TodayFragment.
     */
    public static TodayFragment newInstance() {
        TodayFragment fragment = new TodayFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getContext();

        // Inflate the layout for this fragment
        mTodayView = inflater.inflate(R.layout.fragment_today, container, false);

        initView();

        initData();

        updateData();

        return mTodayView;
    }

    private void initView() {
        // setup views
        mTodayQuestionsRecyclerView = (SwipeMenuRecyclerView) mTodayView.findViewById(R.id.recycler_view_today_questions);

        // set layout managers
        mTodayQuestionsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }

    private void initData() {
        mTodayQuestions = new ArrayList<>();
        mTodayQuestionsAdapter = new TodayQuestionsAdapter(mTodayQuestions, mAnswers, mContext);
        mTodayQuestionsAdapter.setOnItemClickListener(onItemClickListener);
        mTodayQuestionsRecyclerView.setAdapter(mTodayQuestionsAdapter);
        mTodayQuestionsHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                List<TodayQuestion> todayQuestions;

                if (msg.what == SanxingApiClient.SUCCESS_CODE) {
                    todayQuestions = (List<TodayQuestion>) msg.obj;
                } else {
                    Log.d("error", (String) msg.obj);
                    todayQuestions = TodayQuestion.sampleQuestions;
                }

                // remove answered today questions
                Iterator<TodayQuestion> iterator = todayQuestions.iterator();
                while (iterator.hasNext()) {
                    TodayQuestion todayQuestion = iterator.next();
                    if (todayQuestion.isAnswered()) iterator.remove();
                }

                // update data & notify the adapter
                mTodayQuestions.clear();
                mTodayQuestions.addAll(todayQuestions);
                mTodayQuestionsAdapter.notifyDataSetChanged();
            }
        };
        mAnswersHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                List<Answer> answers;

                if (msg.what == SanxingApiClient.SUCCESS_CODE) {
                    answers = (List<Answer>) msg.obj;
                } else {
                    Log.d("error", (String) msg.obj);
                    answers = Answer.sampleAnswerData;
                }

                // TODO: remove the following if statement after demo
                if (answers.size() == 0) {
                    answers = Answer.sampleAnswerData;
                }

                // update data & notify the adapter
                mAnswers.clear();
                mAnswers.addAll(answers);
                mTodayQuestionsAdapter.notifyDataSetChanged();
            }
        };
    }

    /**
     * Retrieve data from server
     */
    private void updateData() {
        SanxingApiClient.getInstance(mContext).getTodayQuestions(mTodayQuestionsHandler);
        SanxingApiClient.getInstance(mContext).getAnswerHistory(mAnswersHandler);
    }

    /**
     * Item click listener, start AnswerActivity / QuestionDetailActivity
     */
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            if (position < mTodayQuestions.size()) {
                Intent intent = new Intent(mContext, AnswerActivity.class);
                intent.putExtra("question", mTodayQuestions.get(position));
                startActivityForResult(intent, REQUEST_CODE_ANSWER);
            } else {
                // timeline click
                int index = position - mTodayQuestions.size();
                Bundle bundle = new Bundle();
                bundle.putString("title", mAnswers.get(index).getQuestionContent());
                bundle.putString("answerTxt", mAnswers.get(index).getContent());
                Intent intent = new Intent(mContext, QuestionDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ANSWER && resultCode == RESPONSE_CODE_SUCCESS) {
            updateData();
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
