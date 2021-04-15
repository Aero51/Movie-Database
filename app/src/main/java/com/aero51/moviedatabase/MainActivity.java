package com.aero51.moviedatabase;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.aero51.moviedatabase.ui.ActorFragment;
import com.aero51.moviedatabase.ui.CustomViewPager;
import com.aero51.moviedatabase.ui.GenreListFragment;
import com.aero51.moviedatabase.ui.MovieDetailsFragment;
import com.aero51.moviedatabase.ui.TvShowsFragment;
import com.aero51.moviedatabase.ui.adapter.DynamicFragmentPagerAdapter;
import com.aero51.moviedatabase.ui.EpgAllProgramsFragment;
import com.aero51.moviedatabase.ui.EpgDetailsFragment;
import com.aero51.moviedatabase.ui.EpgFragment;
import com.aero51.moviedatabase.ui.MoviesFragment;
import com.aero51.moviedatabase.utils.ChannelsPreferenceHelper;
import com.aero51.moviedatabase.utils.CheckAppStart;
import com.aero51.moviedatabase.utils.Constants;
import com.aero51.moviedatabase.utils.PrePopulatedChannels;
import com.aero51.moviedatabase.viewmodel.SharedViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;


import static com.aero51.moviedatabase.utils.Constants.BOSNIAN_CHANNELS_PREFERENCE;
import static com.aero51.moviedatabase.utils.Constants.CROATIAN_CHANNELS_PREFERENCE;
import static com.aero51.moviedatabase.utils.Constants.LOG2;
import static com.aero51.moviedatabase.utils.Constants.NEWS_CHANNELS_PREFERENCE;
import static com.aero51.moviedatabase.utils.Constants.SERBIAN_CHANNELS_PREFERENCE;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private CustomViewPager customViewPager;
    private DynamicFragmentPagerAdapter dynamicFragmentPagerAdapter;
    private DynamicFragmentPagerAdapter.FragmentIdentifier epgFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier epgDetailsFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier epgAllProgramsFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier moviesFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier tvShowsFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier movieDetailsFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier actorFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier genreListFragmentIdentifier;
    private FirebaseAnalytics mFirebaseAnalytics;
    private SharedViewModel sharedViewModel;
    private Integer actorFragmentBackPosition;

    private MenuItem prevMenuItem;
    private BottomNavigationView bottomNavigationView;
    //private AnimatedBottomBar animatedBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        switch (CheckAppStart.checkAppStart(getPackageManager(), getPackageName())) {
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
                TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
                String countryCodeValue = tm.getNetworkCountryIso();
                //populating channel preferences on first app run to avoid empty channels list
                if (countryCodeValue != null) {
                    PrePopulatedChannels prePopulatedChannels = new PrePopulatedChannels();
                    if (countryCodeValue.equalsIgnoreCase("hr")) {
                        ChannelsPreferenceHelper.firstRunPreferencePopulater(prePopulatedChannels.getCroatianList(), CROATIAN_CHANNELS_PREFERENCE);
                    } else if (countryCodeValue.equalsIgnoreCase("rs")) {
                        ChannelsPreferenceHelper.firstRunPreferencePopulater(prePopulatedChannels.getSerbianList(), SERBIAN_CHANNELS_PREFERENCE);
                    } else if (countryCodeValue.equalsIgnoreCase("ba")) {
                        ChannelsPreferenceHelper.firstRunPreferencePopulater(prePopulatedChannels.getBosnianList(), BOSNIAN_CHANNELS_PREFERENCE);
                    } else {
                        ChannelsPreferenceHelper.firstRunPreferencePopulater(prePopulatedChannels.getNewsList(), NEWS_CHANNELS_PREFERENCE);
                    }
                }
                //Log.d(Constants.LOG2, "countryCodeValue: " + countryCodeValue + " ,getSimCountryIso: " + tm.getSimCountryIso() + " ,phonetype: " + tm.getPhoneType());
                break;
            default:
                break;
        }

        setContentView(R.layout.activity_main);


        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        SharedPreferences sharedPrefPrivate = this.getPreferences(Context.MODE_PRIVATE);
        actorFragmentBackPosition = sharedPrefPrivate.getInt("Saved ActorFragment back navigation", -1);

        customViewPager = findViewById(R.id.view_pager);
        //customViewPager.setPagingEnabled(false);
        dynamicFragmentPagerAdapter = new DynamicFragmentPagerAdapter(getSupportFragmentManager(), MyApplication.getAppContext());

        customViewPager.setAdapter(dynamicFragmentPagerAdapter);
        // getSupportActionBar().setDisplayShowTitleEnabled(false);
        // getSupportActionBar().hide();

        //Initializing the bottomNavigationView
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

/*
        animatedBottomBar=(AnimatedBottomBar) findViewById(R.id.bottom_navigation);
        animatedBottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int i, @Nullable AnimatedBottomBar.Tab tab, int i1, @NotNull AnimatedBottomBar.Tab tab1) {
                Log.d(Constants.LOG2, "onTabSelected: "+i1);
                switch (i1) {
                    case 0:
                        viewPager.setCurrentItem(0);
                        break;
                    case 1:
                        viewPager.setCurrentItem(1);
                        break;
                    case 2:
                        viewPager.setCurrentItem(2);
                        break;
                }
            }

            @Override
            public void onTabReselected(int i, @NotNull AnimatedBottomBar.Tab tab) {

            }
        });
*/

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        //collapsingToolbarLayout.setTitle(getTitle());

        //for transparent status bar
      /*  getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );*/


        //tab layout not used anymore, instead bottom navigation view is used
        //TabLayout tabs = findViewById(R.id.tabs);
        //tabs.setupWithViewPager(viewPager);

        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        customViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d(Constants.LOG2, "onPageSelected: " + position);

                bottomNavigationView.getMenu().getItem(position).setChecked(true);

                prevMenuItem = bottomNavigationView.getMenu().getItem(position);


                String currentFragmentTag = dynamicFragmentPagerAdapter.getFragmentTagForPosition(position);
                addRemoveBackButton(position, currentFragmentTag);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        initFirstFragmentIdentifiers();
        registerShouldSwitchEpgFragmentsObservers();
        registerShouldSwitchMovieFragmentsObservers();
        //transparentStatusAndNavigation();
    }


    private void initFirstFragmentIdentifiers() {

        epgFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier(EpgFragment.class.getSimpleName(), null) {
            @Override
            protected Fragment createFragment() {
                EpgFragment epgFragment = new EpgFragment();
                return epgFragment;
            }

            @Override
            public int describeContents() {
                return 0;
            }
        };

        moviesFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier(MoviesFragment.class.getSimpleName(), null) {
            @Override
            protected Fragment createFragment() {
                return new MoviesFragment();
            }

            @Override
            public int describeContents() {
                return 0;
            }
        };
        tvShowsFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier(TvShowsFragment.class.getSimpleName(), null) {
            @Override
            protected Fragment createFragment() {
                return new TvShowsFragment();
            }

            @Override
            public int describeContents() {
                return 0;
            }
        };

        dynamicFragmentPagerAdapter.addFragment(epgFragmentIdentifier);
        dynamicFragmentPagerAdapter.addFragment(moviesFragmentIdentifier);
        dynamicFragmentPagerAdapter.addFragment(tvShowsFragmentIdentifier);
    }


    private void registerShouldSwitchEpgFragmentsObservers() {
        sharedViewModel.getSingleLiveShouldSwitchToEpgTvDetailsFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Log.d(Constants.LOG, " main activity getSingleLiveShouldSwitchToEpgTvDetailsFragment on changed");

                epgDetailsFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier(EpgDetailsFragment.class.getSimpleName(), null) {
                    @Override
                    protected Fragment createFragment() {
                        return new EpgDetailsFragment();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }
                };
                replaceFragment(0, epgDetailsFragmentIdentifier);
            }
        });

        sharedViewModel.getSingleLiveShouldSwitchToEpgAllProgramsFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                epgAllProgramsFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier(EpgAllProgramsFragment.class.getSimpleName(), null) {
                    @Override
                    protected Fragment createFragment() {
                        return new EpgAllProgramsFragment();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }
                };
                replaceFragment(0, epgAllProgramsFragmentIdentifier);
            }
        });

    }

    private void registerShouldSwitchMovieFragmentsObservers() {

        sharedViewModel.getSingleLiveShouldSwitchMovieDetailsFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                movieDetailsFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier(MovieDetailsFragment.class.getSimpleName(), null) {
                    @Override
                    protected Fragment createFragment() {
                        return new MovieDetailsFragment();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }
                };

                replaceFragment(1, movieDetailsFragmentIdentifier);
                customViewPager.setCurrentItem(1);
            }
        });

        sharedViewModel.getSingleLiveShouldSwitchActorFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                actorFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier(ActorFragment.class.getSimpleName(), null) {
                    @Override
                    protected Fragment createFragment() {
                        return new ActorFragment();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }
                };
                replaceFragment(1, actorFragmentIdentifier);
                customViewPager.setCurrentItem(1);
            }
        });


        sharedViewModel.getSingleLiveShouldSwitchGenreListFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                genreListFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier(GenreListFragment.class.getSimpleName(), null) {
                    @Override
                    protected Fragment createFragment() {
                        return new GenreListFragment();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }
                };
                replaceFragment(1, genreListFragmentIdentifier);
                customViewPager.setCurrentItem(1);
            }
        });


    }


    @Override
    public void onBackPressed() {
        //implement for each tab
        int currentViewPagerItem = customViewPager.getCurrentItem();
        Log.d(Constants.LOG, " backstack count: " + getSupportFragmentManager().getBackStackEntryCount());
        String currentFragmentTag = dynamicFragmentPagerAdapter.getFragmentTagForPosition(currentViewPagerItem);

        switch (currentViewPagerItem) {
            case 0:
                if (currentFragmentTag.equals(EpgFragment.class.getSimpleName())) {
                    super.onBackPressed();
                } else if (currentFragmentTag.equals(EpgDetailsFragment.class.getSimpleName())) {
                    replaceFragment(0, epgFragmentIdentifier);
                } else if (currentFragmentTag.equals(EpgAllProgramsFragment.class.getSimpleName())) {
                    replaceFragment(0, epgFragmentIdentifier);
                }
                break;

            case 1:
                if (currentFragmentTag.equals(MoviesFragment.class.getSimpleName())) {
                    super.onBackPressed();
                } else if (currentFragmentTag.equals(MovieDetailsFragment.class.getSimpleName())) {
                    replaceFragment(1, moviesFragmentIdentifier);
                    customViewPager.setCurrentItem(1);
                } else if (currentFragmentTag.equals(ActorFragment.class.getSimpleName())) {
                    replaceFragment(1, movieDetailsFragmentIdentifier);
                    customViewPager.setCurrentItem(1);
                } else if (currentFragmentTag.equals(GenreListFragment.class.getSimpleName())) {
                    replaceFragment(1, moviesFragmentIdentifier);
                    customViewPager.setCurrentItem(1);
                }
                break;
        }


    }

    private void addRemoveBackButton(int position, String currentFragmentTag) {

        switch (position) {
            case 0:
                if (currentFragmentTag.equals(EpgFragment.class.getSimpleName())) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                } else if (currentFragmentTag.equals(EpgDetailsFragment.class.getSimpleName())) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                } else if (currentFragmentTag.equals(EpgAllProgramsFragment.class.getSimpleName())) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
                break;

            case 1:
                if (currentFragmentTag.equals(MoviesFragment.class.getSimpleName())) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                } else if (currentFragmentTag.equals(MovieDetailsFragment.class.getSimpleName())) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                } else if (currentFragmentTag.equals(ActorFragment.class.getSimpleName())) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                } else if (currentFragmentTag.equals(GenreListFragment.class.getSimpleName())){
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }

                break;
        }

    }

    private void replaceFragment(int index, DynamicFragmentPagerAdapter.FragmentIdentifier fragmentIdentifier) {
        dynamicFragmentPagerAdapter.replaceFragment(index, fragmentIdentifier);
        //required for custom view pager to work properly
        customViewPager.setAdapter(null);
        customViewPager.setAdapter(dynamicFragmentPagerAdapter);
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
        switch (item.getItemId()) {

            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_search:
                Intent intent2 = new Intent(this, SearchActivity.class);
                startActivity(intent2);
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        String currentFragmentTag = "";
        switch (item.getItemId()) {
            case R.id.action_tv_guide:
                customViewPager.setCurrentItem(0);
                currentFragmentTag = dynamicFragmentPagerAdapter.getFragmentTagForPosition(0);
                addRemoveBackButton(0, currentFragmentTag);
                break;
            case R.id.action_movies:
                customViewPager.setCurrentItem(1);
                currentFragmentTag = dynamicFragmentPagerAdapter.getFragmentTagForPosition(1);
                addRemoveBackButton(0, currentFragmentTag);
                break;
            case R.id.action_tv_shows:
                customViewPager.setCurrentItem(2);
                currentFragmentTag = dynamicFragmentPagerAdapter.getFragmentTagForPosition(2);
                addRemoveBackButton(0, currentFragmentTag);
                break;
        }
        return false;
    }

    //for transparent status bar
    private void transparentStatusAndNavigation() {
        //make full transparent statusBar
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            );
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    private void setWindowFlag(final int bits, boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


}

