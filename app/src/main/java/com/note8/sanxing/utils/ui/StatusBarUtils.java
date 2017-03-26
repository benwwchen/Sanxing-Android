package com.note8.sanxing.utils.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.WindowManager;

/**
 * Created by BenWwChen on 2017/3/27.
 */

public class StatusBarUtils {
    public static void setContentToTop(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 5.0及以上，不设置透明状态栏，设置会有半透明阴影
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 使内容沉浸到状态栏上
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            // 设置状态栏颜色透明。
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
