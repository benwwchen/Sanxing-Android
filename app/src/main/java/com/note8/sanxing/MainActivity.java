package com.note8.sanxing;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.note8.sanxing.fragments.BroadcastFragment;
import com.note8.sanxing.fragments.TodayFragment;
import com.note8.sanxing.utils.CustomGradientDrawable;

public class MainActivity extends AppCompatActivity
        implements BroadcastFragment.OnFragmentInteractionListener, TodayFragment.OnFragmentInteractionListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    /**
     * TodayFragment and BroadcastFragment
     */
    private TodayFragment todayFragment;
    private BroadcastFragment broadcastFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set status bar and toolbar color
        CustomGradientDrawable gradientDrawable = new CustomGradientDrawable(
                new int[] {0xfff78ca0, 0xfff9748f, 0xfffd868c, 0xfffe9a8b},
                new float[] {0, 0.19f, 0.60f, 1});

        getSupportActionBar().setBackgroundDrawable(gradientDrawable);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setBackground(gradientDrawable);


        initToolbar();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    private void initToolbar() {
        ImageButton weeklyImageButton = (ImageButton) findViewById(R.id.image_button_weekly);
        ImageButton personalImageButton = (ImageButton) findViewById(R.id.image_button_personal);
        ImageButton calendarImageButton = (ImageButton) findViewById(R.id.image_button_calender);

        ImageButton[] buttons = new ImageButton[]
                {weeklyImageButton, personalImageButton, calendarImageButton};

        Class[] activities = new Class[]
                {WeeklyActivity.class, MeActivity.class, CalenderActivity.class};

        for (int i = 0; i < buttons.length; ++i) {
            setListener(buttons[i], activities[i]);
        }
    }

    private void setListener(ImageButton button, final Class activity) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, activity);
                startActivity(intent);
            }
        });
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    todayFragment = TodayFragment.newInstance();
                    return todayFragment;
                case 1:
                    broadcastFragment = BroadcastFragment.newInstance();
                    return broadcastFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 2 total pages. (Today and Broadcast)
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.title_today);
                case 1:
                    return getString(R.string.title_broadcast);
            }
            return null;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
