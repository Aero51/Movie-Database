package com.aero51.moviedatabase.ui;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.utils.CheckAppStart;
import com.aero51.moviedatabase.utils.Constants;
import com.aero51.moviedatabase.viewmodel.SharedViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.analytics.FirebaseAnalytics;


public class MainActivity extends AppCompatActivity {
    private CustomViewPager viewPager;
    private DynamicFragmentPagerAdapter dynamicFragmentPagerAdapter;
    private DynamicFragmentPagerAdapter.FragmentIdentifier epgFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier epgDetailsFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier epgAllProgramsFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier moviesFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier topRatedMovieFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier popularMovieFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier actorFragmentIdentifier;
    private FirebaseAnalytics mFirebaseAnalytics;
    private SharedViewModel sharedViewModel;
    private Integer actorFragmentBackPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        switch (CheckAppStart.checkAppStart(getPackageManager(),getPackageName())) {
            case NORMAL:
                // We don't want to get on the user's nerves
                Log.d(Constants.LOG2, "not first time, normal  run. ");
                break;
            case FIRST_TIME_VERSION:
                // TODO show what's new
                Log.d(Constants.LOG2, "first time version run ");
                break;
            case FIRST_TIME:
                // TODO show a tutorial
                Log.d(Constants.LOG2, "first time  run ");
                break;
            default:
                break;
        }





        setContentView(R.layout.activity_main);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        SharedPreferences sharedPrefPrivate = this.getPreferences(Context.MODE_PRIVATE);
        actorFragmentBackPosition = sharedPrefPrivate.getInt("Saved ActorFragment back navigation", -1);

        viewPager = findViewById(R.id.view_pager);
        dynamicFragmentPagerAdapter = new DynamicFragmentPagerAdapter(getSupportFragmentManager(), getApplicationContext());
        //  viewPager.setPagingEnabled(false);

        viewPager.setAdapter(dynamicFragmentPagerAdapter);
       // getSupportActionBar().setDisplayShowTitleEnabled(false);
       // getSupportActionBar().hide();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        initFirstFragmentIdentifiers();
        registerShouldSwitchEpgFragmentsObservers();
        registerShouldSwitchMovieFragmentsObservers();

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(intent);
            }
        });


        PreferenceManager
                .setDefaultValues(this, R.xml.preferences, false);

        SharedPreferences sharedPref =
                PreferenceManager
                        .getDefaultSharedPreferences(this);
        Boolean switchPref = sharedPref.getBoolean
                (SettingsActivity.KEY_PREF_EXAMPLE_SWITCH, false);
        Toast.makeText(this, switchPref.toString(),
                Toast.LENGTH_SHORT).show();


    }


    private void initFirstFragmentIdentifiers() {

        epgFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier("EpgTvFragment", null) {
            @Override
            protected Fragment createFragment() {
                EpgFragment epgFragment = EpgFragment.newInstance("", "");
                return epgFragment;
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

        dynamicFragmentPagerAdapter.addFragment(epgFragmentIdentifier);
        dynamicFragmentPagerAdapter.addFragment(moviesFragmentIdentifier);

    }


    private void registerShouldSwitchEpgFragmentsObservers() {
        sharedViewModel.getSingleLiveShouldSwitchToEpgTvDetailsFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Log.d(Constants.LOG, " main activity getSingleLiveShouldSwitchToEpgTvDetailsFragment on changed");

                epgDetailsFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier("EpgTvDetailsFragment", null) {
                    @Override
                    protected Fragment createFragment() {
                        return EpgDetailsFragment.newInstance("", "");
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }
                };
                dynamicFragmentPagerAdapter.replaceFragment(0, epgDetailsFragmentIdentifier);
                viewPager.setAdapter(null);
                viewPager.setAdapter(dynamicFragmentPagerAdapter);
            }
        });

        sharedViewModel.getSingleLiveShouldSwitchToEpgAllProgramsFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                epgAllProgramsFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier("EpgAllProgramsFragment", null) {
                    @Override
                    protected Fragment createFragment() {
                        return EpgAllProgramsFragment.newInstance("", "");
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }
                };
                dynamicFragmentPagerAdapter.replaceFragment(0, epgAllProgramsFragmentIdentifier);
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
                        return TopRatedMovieDetailsFragment.newInstance("", "");
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
        Log.d(Constants.LOG, " backstack count: " + getSupportFragmentManager().getBackStackEntryCount());
        String currentFragmentTag = dynamicFragmentPagerAdapter.getFragmentTagForPosition(currentViewPagerItem);

        if (currentViewPagerItem == 0) {
            if (currentFragmentTag.equals("EpgTvFragment")) {
                super.onBackPressed();
            }
            if (currentFragmentTag.equals("EpgTvDetailsFragment")) {
                dynamicFragmentPagerAdapter.replaceFragment(0, epgFragmentIdentifier);
                viewPager.setAdapter(null);
                viewPager.setAdapter(dynamicFragmentPagerAdapter);
            }
            if (currentFragmentTag.equals("EpgAllProgramsFragment")) {
                dynamicFragmentPagerAdapter.replaceFragment(0, epgFragmentIdentifier);
                viewPager.setAdapter(null);
                viewPager.setAdapter(dynamicFragmentPagerAdapter);
            }

        }
        if (currentViewPagerItem == 1) {
            if (currentFragmentTag.equals("MoviesFragment")) {
                super.onBackPressed();
            }
            if (currentFragmentTag.equals("TopRatedMovieDetailsFragment") || currentFragmentTag.equals("PopularMovieDetailsFragment")) {
                Log.d(Constants.LOG, " currentFragmentTag.equals: " + currentFragmentTag);
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
        Log.d(Constants.LOG, " onStop: ");
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("Saved ActorFragment back navigation", actorFragmentBackPosition);
        editor.commit();

        super.onStop();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

