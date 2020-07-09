package com.aero51.moviedatabase.ui;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
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
    private DynamicFragmentPagerAdapter.FragmentIdentifier actorFragmentIdentifier;

    private SharedViewModel sharedViewModel;
    private Integer actorFragmentBackPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        actorFragmentBackPosition = sharedPref.getInt("Saved ActorFragment back navigation", -1);


        viewPager = findViewById(R.id.view_pager);

        dynamicFragmentPagerAdapter = new DynamicFragmentPagerAdapter(getSupportFragmentManager());
        //  viewPager.setPagingEnabled(false);
        viewPager.setAdapter(dynamicFragmentPagerAdapter);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().hide();

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);


        initFirstFragmentIdentifiers();
        registerShouldSwitchEpgFragmentsObserver();
        registerShouldSwitchMovieFragmentsObservers();


        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    private void initFirstFragmentIdentifiers() {
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

    }


    private void registerShouldSwitchEpgFragmentsObserver() {
        sharedViewModel.getSingleLiveShouldSwitchEpgFragments().observe(this, new Observer<Boolean>() {
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
    }

    private void registerShouldSwitchMovieFragmentsObservers() {
        sharedViewModel.getSingleLiveShouldSwitchTopRatedMovieFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                topRatedMovieFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier("TopRatedMovieDetailsFragment", null) {
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

                actorFragmentBackPosition = 0;

            }
        });

        sharedViewModel.getSingleLiveShouldSwitchPopularMovieFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                popularMovieFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier("PopularMovieDetailsFragment", null) {
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

                actorFragmentBackPosition = 1;
            }
        });

        sharedViewModel.getSingleLiveShouldSwitchActorFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                actorFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier("ActorFragment", null) {
                    @Override
                    protected Fragment createFragment() {
                        return ActorFragment.newInstance("");
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }
                };
                dynamicFragmentPagerAdapter.replaceFragment(1, actorFragmentIdentifier);
                viewPager.setAdapter(null);
                viewPager.setAdapter(dynamicFragmentPagerAdapter);
                viewPager.setCurrentItem(1);
            }
        });


    }

    @Override
    public void onBackPressed() {
        //implement for each tab
        int currentViewPagerItem = viewPager.getCurrentItem();
        String currentFragmentTag = dynamicFragmentPagerAdapter.getFragmentTagForPosition(currentViewPagerItem);

        if (currentViewPagerItem == 0) {
            if (currentFragmentTag.equals("EpgTvFragment")) {
                super.onBackPressed();
            }
            if (currentFragmentTag.equals("EpgTvDetailsFragment")) {
                dynamicFragmentPagerAdapter.replaceFragment(0, epgtvFragmentIdentifier);
                viewPager.setAdapter(null);
                viewPager.setAdapter(dynamicFragmentPagerAdapter);
            }
        }
        if (currentViewPagerItem == 1) {
            if (currentFragmentTag.equals("MoviesFragment")) {
                super.onBackPressed();
            }
            if (currentFragmentTag.equals("TopRatedMovieDetailsFragment") || currentFragmentTag.equals("PopularMovieDetailsFragment")) {
                dynamicFragmentPagerAdapter.replaceFragment(1, moviesFragmentIdentifier);
                viewPager.setAdapter(null);
                viewPager.setAdapter(dynamicFragmentPagerAdapter);
                viewPager.setCurrentItem(1);
            }

            if (currentFragmentTag.equals("ActorFragment")) {
                if (actorFragmentBackPosition == 0) {
                    dynamicFragmentPagerAdapter.replaceFragment(1, topRatedMovieFragmentIdentifier);
                    viewPager.setAdapter(null);
                    viewPager.setAdapter(dynamicFragmentPagerAdapter);
                    viewPager.setCurrentItem(1);

                }
                if (actorFragmentBackPosition == 1) {
                    dynamicFragmentPagerAdapter.replaceFragment(1, popularMovieFragmentIdentifier);
                    viewPager.setAdapter(null);
                    viewPager.setAdapter(dynamicFragmentPagerAdapter);
                    viewPager.setCurrentItem(1);
                }

            }

        }


    }

    @Override
    protected void onStop() {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("Saved ActorFragment back navigation", actorFragmentBackPosition);
        editor.commit();

        super.onStop();
    }


}

