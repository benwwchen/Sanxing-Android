package com.note8.sanxing;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.note8.sanxing.utils.ui.CustomGradientDrawable;
import com.note8.sanxing.utils.ui.StatusBarUtils;

public class WeeklyActivity extends AppCompatActivity {

    private ImageButton returnBtn;
    private ImageButton calendarBtn;
    private WebView webView;
    private RelativeLayout topPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly);
        // init
        returnBtn = (ImageButton)findViewById(R.id.weekly_return);
        calendarBtn = (ImageButton)findViewById(R.id.weekly_calendar);
        webView = (WebView)findViewById(R.id.weekly_web_view);
        topPanel = (RelativeLayout)findViewById(R.id.weekly_top_panel);

        initBackgroundGradient();

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
    }

    // change the bg of top panel
    private void initBackgroundGradient() {
        StatusBarUtils.setContentToTop(this);
        // set background gradient color
        CustomGradientDrawable gradientDrawable = new CustomGradientDrawable(
                new int[] {0xfff78ca0, 0xfff9748f, 0xfffd868c, 0xfffe9a8b},
                new float[] {0, 0.19f, 0.60f, 1});
        topPanel.setBackground(gradientDrawable);
    }
}
