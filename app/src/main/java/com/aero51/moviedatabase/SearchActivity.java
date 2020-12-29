package com.aero51.moviedatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.aero51.moviedatabase.ui.CustomViewPager;
import com.aero51.moviedatabase.ui.DynamicFragmentPagerAdapter;
import com.aero51.moviedatabase.ui.MovieSearchFragment;
import com.aero51.moviedatabase.ui.MoviesFragment;
import com.aero51.moviedatabase.ui.TvShowsSearchFragment;
import com.aero51.moviedatabase.utils.Constants;
import com.google.android.material.tabs.TabLayout;

public class SearchActivity extends AppCompatActivity {
    private CustomViewPager viewPager;
    private DynamicFragmentPagerAdapter dynamicFragmentPagerAdapter;
    private DynamicFragmentPagerAdapter.FragmentIdentifier moviesSearchFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier tvShowsSearchFragmentIdentifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        viewPager = findViewById(R.id.pager);
        dynamicFragmentPagerAdapter = new DynamicFragmentPagerAdapter(getSupportFragmentManager(), MyApplication.getAppContext());
        viewPager.setAdapter(dynamicFragmentPagerAdapter);
        TabLayout tabs = findViewById(R.id.tablayout);
        tabs.setupWithViewPager(viewPager);
        initFirstFragmentIdentifiers();
    }
    private void initFirstFragmentIdentifiers() {

        moviesSearchFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier("MovieSearchFragment", null) {
            @Override
            protected Fragment createFragment() {
                MovieSearchFragment movieSearchFragment = MovieSearchFragment.newInstance("", "");
                return movieSearchFragment;
            }

            @Override
            public int describeContents() {
                return 0;
            }
        };

        tvShowsSearchFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier("TvShowsSearchFragment", null) {
            @Override
            protected Fragment createFragment() {
                TvShowsSearchFragment tvShowsSearchFragment = TvShowsSearchFragment.newInstance("", "");

                return tvShowsSearchFragment;
            }

            @Override
            public int describeContents() {
                return 0;
            }
        };

        dynamicFragmentPagerAdapter.addFragment(moviesSearchFragmentIdentifier);
        dynamicFragmentPagerAdapter.addFragment(tvShowsSearchFragmentIdentifier);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_activity, menu);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search_action_search).getActionView();
        searchView.setIconified(false);
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(Constants.LOG, " onQueryTextSubmit: " + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(Constants.LOG, " onQueryTextChange: " + newText);
                return false;
            }
        });
        /*
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this, SearchActivity.class)));
        searchView.setQueryHint(getResources().getString(R.string.search_hint));

         */
        return super.onCreateOptionsMenu(menu);
    }
}