package com.aero51.moviedatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.aero51.moviedatabase.ui.CustomViewPager;
import com.aero51.moviedatabase.ui.adapter.DynamicFragmentPagerAdapter;
import com.aero51.moviedatabase.ui.ListsSearchFragment;
import com.aero51.moviedatabase.ui.MovieSearchFragment;
import com.aero51.moviedatabase.ui.PeopleSearchFragment;
import com.aero51.moviedatabase.ui.TvShowsSearchFragment;
import com.aero51.moviedatabase.utils.Constants;
import com.aero51.moviedatabase.viewmodel.SearchViewModel;
import com.google.android.material.tabs.TabLayout;

public class SearchActivity extends AppCompatActivity {
    private CustomViewPager viewPager;
    private DynamicFragmentPagerAdapter dynamicFragmentPagerAdapter;
    private DynamicFragmentPagerAdapter.FragmentIdentifier moviesSearchFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier tvShowsSearchFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier peopleSearchFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier listsSearchFragmentIdentifier;

    private SearchViewModel searchViewModel;
    private String searchQuery;

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
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d(Constants.LOG2, "onPageSelected: " + position);
                if (searchQuery == null) searchQuery = "";
                switch (position) {
                    //MovieSearchFragment
                    case 0:
                        searchViewModel.setMovieSearchText(searchQuery);
                        break;
                    //TvShowsSearchFragment
                    case 1:
                        searchViewModel.setTvShowSearchText(searchQuery);
                        break;
                    case 2:
                        searchViewModel.setPeopleSearchText(searchQuery);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initFirstFragmentIdentifiers() {

        moviesSearchFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier(MovieSearchFragment.class.getSimpleName(), null) {
            @Override
            protected Fragment createFragment() {
                MovieSearchFragment movieSearchFragment = new MovieSearchFragment();
                return movieSearchFragment;
            }

            @Override
            public int describeContents() {
                return 0;
            }
        };

        tvShowsSearchFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier(TvShowsSearchFragment.class.getSimpleName(), null) {
            @Override
            protected Fragment createFragment() {
                TvShowsSearchFragment tvShowsSearchFragment = new TvShowsSearchFragment();

                return tvShowsSearchFragment;
            }

            @Override
            public int describeContents() {
                return 0;
            }
        };


        peopleSearchFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier(PeopleSearchFragment.class.getSimpleName(), null) {
            @Override
            protected Fragment createFragment() {
                PeopleSearchFragment peopleSearchFragment = new PeopleSearchFragment();

                return peopleSearchFragment;
            }

            @Override
            public int describeContents() {
                return 0;
            }
        };
        listsSearchFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier(ListsSearchFragment.class.getSimpleName(), null) {
            @Override
            protected Fragment createFragment() {
                ListsSearchFragment listsSearchFragment = new ListsSearchFragment();

                return listsSearchFragment;
            }

            @Override
            public int describeContents() {
                return 0;
            }
        };


        dynamicFragmentPagerAdapter.addFragment(moviesSearchFragmentIdentifier);
        dynamicFragmentPagerAdapter.addFragment(tvShowsSearchFragmentIdentifier);
        dynamicFragmentPagerAdapter.addFragment(peopleSearchFragmentIdentifier);
        dynamicFragmentPagerAdapter.addFragment(listsSearchFragmentIdentifier);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_activity, menu);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search_action_search).getActionView();
        searchView.setIconified(false);
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(Constants.LOG, " onQueryTextSubmit: " + query);
                searchQuery = query;
                execSelectedFragmentSearch();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(Constants.LOG, " onQueryTextChange: " + newText);
                searchQuery = newText;
                execSelectedFragmentSearch();
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

    private void execSelectedFragmentSearch() {
        int currentViewPagerItem = viewPager.getCurrentItem();
        String currentFragmentTag = dynamicFragmentPagerAdapter.getFragmentTagForPosition(currentViewPagerItem);
        switch (currentViewPagerItem) {
            //MovieSearchFragment
            case 0:
                searchViewModel.setMovieSearchText(searchQuery);
                break;
            //TvShowsSearchFragment
            case 1:
                searchViewModel.setTvShowSearchText(searchQuery);
                break;
            case 2:
                searchViewModel.setPeopleSearchText(searchQuery);
                break;

        }
    }
}