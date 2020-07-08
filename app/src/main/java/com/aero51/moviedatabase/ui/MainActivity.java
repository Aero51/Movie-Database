package com.aero51.moviedatabase.ui;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.viewmodel.SharedViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {
    private CustomViewPager viewPager;
    private DynamicFragmentPagerAdapter dynamicFragmentPagerAdapter;
    private DynamicFragmentPagerAdapter.FragmentIdentifier epgtvFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier epgTvDetailsFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier moviesFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier topRatedMovieFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier popularMovieFragmentIdentifier;
    private SharedViewModel sharedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.view_pager);

        dynamicFragmentPagerAdapter = new DynamicFragmentPagerAdapter(getSupportFragmentManager());
        //  viewPager.setPagingEnabled(false);
        viewPager.setAdapter(dynamicFragmentPagerAdapter);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().hide();

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        // EpgTvFragment epgTvFragment= EpgTvFragment.newInstance("","");
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        epgtvFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier("EpgTvFragment", null) {
            @Override
            protected Fragment createFragment() {
                EpgTvFragment epgTvFragment = EpgTvFragment.newInstance("", "");
                return epgTvFragment;
            }

            @Override
            public int describeContents() {
                return 0;
            }
        };
        /*
        moviesFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier("NewMoviesFragment", null) {
            @Override
            protected Fragment createFragment() {
                NewMoviesFragment moviesFragment = NewMoviesFragment.newInstance("","");
                return moviesFragment;
            }

            @Override
            public int describeContents() {
                return 0;
            }
        };
        */


        moviesFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier("MoviesFragment", null) {
            @Override
            protected Fragment createFragment() {
                MoviesFragment moviesFragment = MoviesFragment.newInstance("", "");
                return moviesFragment;
            }

            @Override
            public int describeContents() {
                return 0;
            }
        };


        dynamicFragmentPagerAdapter.addFragment(epgtvFragmentIdentifier);
        dynamicFragmentPagerAdapter.addFragment(moviesFragmentIdentifier);

        sharedViewModel.getSingleLiveShouldSwitchFragments().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Log.d("moviedatabaselog", " main activity on changed");


                epgTvDetailsFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier("EpgTvDetailsFragment", null) {
                    @Override
                    protected Fragment createFragment() {
                        return EpgTvDetailsFragment.newInstance("", "");
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }
                };

                dynamicFragmentPagerAdapter.replaceFragment(0, epgTvDetailsFragmentIdentifier);
                viewPager.setAdapter(null);
                viewPager.setAdapter(dynamicFragmentPagerAdapter);

            }
        });

        sharedViewModel.getSingleLiveShouldSwitchTopRatedMovieFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                topRatedMovieFragmentIdentifier=new DynamicFragmentPagerAdapter.FragmentIdentifier("TopRatedMovieDetailsFragment",null) {
                    @Override
                    protected Fragment createFragment() {
                        return new TopRatedMovieDetailsFragment();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }
                };
                dynamicFragmentPagerAdapter.replaceFragment(1, topRatedMovieFragmentIdentifier);
                viewPager.setAdapter(null);
                viewPager.setAdapter(dynamicFragmentPagerAdapter);
                viewPager.setCurrentItem(1);

            }
        });

        sharedViewModel.getSingleLiveShouldSwitchPopularMovieFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
              popularMovieFragmentIdentifier=new DynamicFragmentPagerAdapter.FragmentIdentifier("PopularMovieDetailsFragment",null) {
                  @Override
                  protected Fragment createFragment() {
                      return new PopularMovieDetailsFragment();
                  }

                  @Override
                  public int describeContents() {
                      return 0;
                  }
              };
                dynamicFragmentPagerAdapter.replaceFragment(1, popularMovieFragmentIdentifier);
                viewPager.setAdapter(null);
                viewPager.setAdapter(dynamicFragmentPagerAdapter);
                viewPager.setCurrentItem(1);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    @Override
    public void onBackPressed() {
        //implement for each tab
        if (viewPager.getCurrentItem() == 0) {
            EpgTvDetailsFragment fragment2 = (EpgTvDetailsFragment) getSupportFragmentManager().findFragmentByTag("EpgTvDetailsFragment");
            if (fragment2 != null) {
                Log.d("moviedatabaselog", " EpgTvDetailsFragment getBackStackEntryCount():  " + fragment2.getFragmentManager().getBackStackEntryCount());
                dynamicFragmentPagerAdapter.replaceFragment(0, epgtvFragmentIdentifier);
                viewPager.setAdapter(null);
                viewPager.setAdapter(dynamicFragmentPagerAdapter);
            } else {

            }

        }
        // super.onBackPressed();

    }


}

