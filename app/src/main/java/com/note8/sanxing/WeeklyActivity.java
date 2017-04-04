package com.note8.sanxing;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.note8.sanxing.utils.ui.CustomGradientDrawable;
import com.note8.sanxing.utils.ui.StatusBarUtils;

public class WeeklyActivity extends AppCompatActivity {

    private class MyObject {
        Context mContext;
        MyObject(Context c) {
            mContext = c;
        }
        @JavascriptInterface
        public void showPage(String name) {
            Toast.makeText(mContext, name, Toast.LENGTH_SHORT).show();
            Intent intent;
            Bundle bundle = new Bundle();
            bundle.putString("title", name);
//              bundle.putString("date", today.date);
//                if (position == 0) {  //  第一个item进入问题回答界面
//                    bundle.putBoolean("newAns", true);
//                } else {              //  其他进入当天问题及回答浏览界面
//                    bundle.putBoolean("newAns", false);
//                }
            intent = new Intent(mContext, QuestionDetailActivity.class);
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        }
    }

    private ImageButton returnBtn;
    private ImageButton calendarBtn;
    private WebView webView;
    private RelativeLayout topPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly);

        StatusBarUtils.setContentToTop(this);
        initToolbar();

        // init
        returnBtn = (ImageButton)findViewById(R.id.weekly_return);
        calendarBtn = (ImageButton)findViewById(R.id.weekly_calendar);
        webView = (WebView)findViewById(R.id.weekly_web_view);

        String url = "http://bogobogo.cn/SanXing/weekly.html";
        initWeb(url);
        // listeners
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // reset fullscreen mode
        webView.setSystemUiVisibility( View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    // set status bar and toolbar color
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_weekly);
        setSupportActionBar(toolbar);

        CustomGradientDrawable gradientDrawable = new CustomGradientDrawable(
                new int[] {0xfff78ca0, 0xfff9748f, 0xfffd868c, 0xfffe9a8b},
                new float[] {0, 0.19f, 0.60f, 1});

        getSupportActionBar().setBackgroundDrawable(gradientDrawable);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.weekly_appbar);
        appBarLayout.setBackground(gradientDrawable);
    }

    // show web page
    private void initWeb(String url) {
        // enable javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        // hide navigation
        webView.setSystemUiVisibility( View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//                | View.SYSTEM_UI_FLAG_FULLSCREEN);
        webView.loadUrl(url);
        // bind the onclick events on js
        webView.addJavascriptInterface(new MyObject(this), "androidObj");
    }

    // change the bg of top panel

}
