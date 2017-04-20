package com.note8.sanxing;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.note8.sanxing.utils.network.SanxingApiClient;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ChooseTagsActivity extends AppCompatActivity {

    private Context mContext;
    private TagFlowLayout mTagFlowLayout;
    private TagAdapter mTagAdapter;

    private ArrayList<String> mTags;
    private Set<Integer> mPreviousSeletedIndexs;
    private ArrayList<String> mSelectedTags;

    private LoadTagsTask mLoadTagsTask;
    private SaveTagsTask mSaveTagsTask;
    private ProgressDialog mProgressDialog;

    private LayoutInflater mInflater;

    private static final int RESULT_CODE_SUCCESS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_tags);

        mContext = ChooseTagsActivity.this;

        initViews();

        loadTags();

        setResult(RESULT_CODE_SUCCESS);
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("请选择感兴趣的标签");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        mInflater = getLayoutInflater();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTags();
            }
        });
    }

    private void loadTags() {
        mTags = new ArrayList<>();
        mSelectedTags = new ArrayList<>();
        mPreviousSeletedIndexs = new HashSet<>();

        mProgressDialog = new ProgressDialog(mContext);
        // 设置对话框显示的内容
        mProgressDialog.setMessage("获取中");
        // 设置对话框能用“取消”按钮关闭
        mProgressDialog.setCancelable(false);
        // 设置对话框的进度条风格
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // 设置对话框的进度条是否为无进度
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
        mLoadTagsTask = new LoadTagsTask(mContext);
        mLoadTagsTask.execute((Void) null);
    }

    private void stopProgress() {
        mProgressDialog.dismiss();
    }

    public class LoadTagsTask extends AsyncTask<Void, Void, Boolean> {

        Context context;

        LoadTagsTask(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            ArrayList<String> allTags;
            ArrayList<String> userTags;

            try {
                // network access.
                allTags = SanxingApiClient.getInstance(context).getTags();
                userTags = SanxingApiClient.getInstance(context).getUser().getTags();
                if (allTags != null && userTags != null) {
                    mTags.clear();
                    mTags.addAll(allTags);
                    mSelectedTags.clear();
                    mSelectedTags.addAll(userTags);
                    // get indexs of selected tags
                    for (String selectedTag: mSelectedTags) {
                        int index = mTags.indexOf(selectedTag);
                        if (index != -1) {
                            mPreviousSeletedIndexs.add(index);
                        }
                    }
                } else return false;
            } catch (Exception e) {

            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean isSuccess) {
            if (isSuccess) {
                mLoadTagsTask = null;
                stopProgress();
                setUpTagFlowLayout();
            } else {
                // fail
                Toast.makeText(mContext, "获取标签失败", Toast.LENGTH_SHORT).show();
                stopProgress();
                finish();
            }

        }
    }

    private void setUpTagFlowLayout() {
        mTagAdapter = new TagAdapter<String>(mTags) {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                        mTagFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        };
        mTagFlowLayout = (TagFlowLayout) findViewById(R.id.flowlayout_tags);
        mTagFlowLayout.setAdapter(mTagAdapter);

        mTagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener()
        {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                String curTag = mTags.get(position);
                if (mSelectedTags.indexOf(curTag) != -1) {
                    mSelectedTags.remove(curTag);
                } else {
                    mSelectedTags.add(curTag);
                }
                return true;
            }
        });
        // set selected
        mTagAdapter.setSelectedList(mPreviousSeletedIndexs);
    }

    private void saveTags() {
        mProgressDialog.setMessage("保存中");
        mProgressDialog.show();
        mSaveTagsTask = new SaveTagsTask(mContext);
        mSaveTagsTask.execute((Void) null);
    }

    public class SaveTagsTask extends AsyncTask<Void, Void, Boolean> {

        Context context;

        SaveTagsTask(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Boolean isSuccess = false;

            try {
                // network access.
                isSuccess = SanxingApiClient.getInstance(context).saveTags(mSelectedTags);
            } catch (Exception e) {

            }

            return isSuccess;
        }

        @Override
        protected void onPostExecute(final Boolean isSuccess) {
            if (isSuccess) {
                mLoadTagsTask = null;
                stopProgress();
                Toast.makeText(mContext, "已保存", Toast.LENGTH_SHORT).show();
                setResult(RESULT_CODE_SUCCESS);
                finish();
            } else {
                // fail
                Toast.makeText(mContext, "保存标签失败", Toast.LENGTH_SHORT).show();
                stopProgress();
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
