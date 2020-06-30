package com.aero51.moviedatabase.ui;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.aero51.moviedatabase.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {
    private CustomViewPager customViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customViewPager = findViewById(R.id.view_pager);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        customViewPager.setAdapter(sectionsPagerAdapter);


        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(customViewPager);

        //added because first tab contains nested horizontal recycler views
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
               // Log.d("moviedatabaselog", "onTabSelected, tab:" + tab.getPosition());
                if (tab.getPosition() == 0) {
                    customViewPager.setPagingEnabled(false);
                } else {
                    customViewPager.setPagingEnabled(true);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
               // Log.d("moviedatabaselog", "onTabUnselected, tab:" + tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
               // Log.d("moviedatabaselog", "onTabReselected, tab:" + tab.getPosition());
                if (tab.getPosition() == 0) {
                    customViewPager.setPagingEnabled(false);
                } else {
                    customViewPager.setPagingEnabled(true);
                }
            }
        });

        tabs.getTabAt(0).select();


        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    private static class SectionsPagerAdapter extends FragmentPagerAdapter {


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);

        }


        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = EpgTvFragment.newInstance("", "");
                    break;
                case 1:

                    fragment = MoviesFragment.newInstance("", "");
                    break;

            }
            return fragment;
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
                    return "Tv guide";
                case 1:
                    return "Movies";

            }
            return null;
        }
    }
}

