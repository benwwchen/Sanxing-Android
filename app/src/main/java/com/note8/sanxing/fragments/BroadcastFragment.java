package com.note8.sanxing.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.note8.sanxing.R;
import com.note8.sanxing.adapters.BroadcastQuestionsAdapter;
import com.note8.sanxing.adapters.TodayQuestionsAdapter;
import com.note8.sanxing.models.BroadcastQuestion;
import com.note8.sanxing.timeLineModel.TimeLineAdapter;

import java.util.ArrayList;

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
    private View broadcastView;
    private RecyclerView broadcastRecyclerView;

    // adapter
    private BroadcastQuestionsAdapter broadcastQuestionsAdapter;

    // data
    private ArrayList<BroadcastQuestion> broadcastQuestions = BroadcastQuestion.sampleQuestions;

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
        broadcastView = inflater.inflate(R.layout.fragment_broadcast, container, false);

        initView();

        loadData();

        return broadcastView;
    }

    private void initView() {
        // setup views
        broadcastRecyclerView = (RecyclerView) broadcastView.findViewById(R.id.recycler_view_broadcast_questions);

        // set layout manager
        broadcastRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }

    private void loadData() {
        broadcastQuestionsAdapter = new BroadcastQuestionsAdapter(broadcastQuestions, mContext);
        broadcastRecyclerView.setAdapter(broadcastQuestionsAdapter);
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
