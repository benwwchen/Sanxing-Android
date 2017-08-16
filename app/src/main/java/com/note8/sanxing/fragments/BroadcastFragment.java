package com.note8.sanxing.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.note8.sanxing.AnswerActivity;
import com.note8.sanxing.BroadcastQuestionDetailActivity;
import com.note8.sanxing.R;
import com.note8.sanxing.adapters.BroadcastQuestionsAdapter;
import com.note8.sanxing.listeners.OnItemClickListener;
import com.note8.sanxing.models.BroadcastQuestion;
import com.note8.sanxing.models.Question;
import com.note8.sanxing.models.TodayQuestion;
import com.note8.sanxing.utils.network.SanxingApiClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BroadcastFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BroadcastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BroadcastFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private Context mContext;

    // views
    private View mBroadcastView;
    private RecyclerView mBroadcastRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    // adapter
    private BroadcastQuestionsAdapter mBroadcastQuestionsAdapter;

    // data
    private ArrayList<BroadcastQuestion> mBroadcastQuestions;
    private ArrayList<Question> favoriteQuestions;

    // handler
    private Handler mBroadcastQuestionsHandler;

    // request/response code
    private static final int REQUEST_CODE_DETAIL = 0;
    private static final int RESPONSE_CODE_SUCCESS = 1;

    public BroadcastFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BroadcastFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BroadcastFragment newInstance() {
        BroadcastFragment fragment = new BroadcastFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getContext();

        // Inflate the layout for this fragment
        mBroadcastView = inflater.inflate(R.layout.fragment_broadcast, container, false);

        initView();

        initData();

        refresh();

        return mBroadcastView;
    }

    private void initView() {
        // setup views
        mBroadcastRecyclerView = (RecyclerView) mBroadcastView.findViewById(R.id.recycler_view_broadcast_questions);

        // set layout manager
        mBroadcastRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        // swipe refresh layout
        mSwipeRefreshLayout = (SwipeRefreshLayout) mBroadcastView.findViewById(R.id.layout_swipe_refresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorBrilliant);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            public void onRefresh() {
                updateData();
            }
        });
    }

    private void initData() {
        mBroadcastQuestions = new ArrayList<>();
        mBroadcastQuestionsAdapter = new BroadcastQuestionsAdapter(mBroadcastQuestions, mContext);
        mBroadcastQuestionsAdapter.setOnItemClickListener(onItemClickListener);
        mBroadcastRecyclerView.setAdapter(mBroadcastQuestionsAdapter);
        mBroadcastQuestionsHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                List<BroadcastQuestion> broadcastQuestions;

                if(msg.what == SanxingApiClient.SUCCESS_CODE){
                    broadcastQuestions = (List<BroadcastQuestion>) msg.obj;
                } else {
                    Log.d("error", (String) msg.obj);
                    broadcastQuestions = BroadcastQuestion.sampleQuestions;
                }

                // TODO: remove the following line after demo
                if (broadcastQuestions.size() < 0) return;

                // update data & notify the adapter
                mBroadcastQuestions.clear();
                mBroadcastQuestions.addAll(broadcastQuestions);
                mBroadcastQuestionsAdapter.notifyDataSetChanged();
                if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
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

    /**
     * Retrieve data from server
     */
    private void updateData() {
        SanxingApiClient.getInstance(mContext).getBroadcastQuestions(mBroadcastQuestionsHandler);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_DETAIL && resultCode == RESPONSE_CODE_SUCCESS) {
            refresh();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Item click listener, start AnswerActivity
     */
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            if (mBroadcastQuestions.get(position).isAnswered() != true) {
                Intent intent = new Intent(mContext, BroadcastQuestionDetailActivity.class);
                intent.putExtra("question", mBroadcastQuestions.get(position));
                startActivityForResult(intent, REQUEST_CODE_DETAIL);
            } else {
                Toast.makeText(mContext,"你已经回答过这个问题了", Toast.LENGTH_SHORT).show();
            }
        }
    };

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
