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
import com.aero51.moviedatabase.ui.DynamicFragmentPagerAdapter;
import com.aero51.moviedatabase.ui.EpgAllProgramsFragment;
import com.aero51.moviedatabase.ui.EpgDetailsFragment;
import com.aero51.moviedatabase.ui.EpgFragment;
import com.aero51.moviedatabase.ui.MoviesFragment;
import com.aero51.moviedatabase.ui.PopularMovieDetailsFragment;
import com.aero51.moviedatabase.ui.TopRatedMovieDetailsFragment;
import com.aero51.moviedatabase.utils.ChannelsPreferenceHelper;
import com.aero51.moviedatabase.utils.CheckAppStart;
import com.aero51.moviedatabase.utils.Constants;
import com.aero51.moviedatabase.utils.PrePopulatedChannels;
import com.aero51.moviedatabase.viewmodel.SharedViewModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.analytics.FirebaseAnalytics;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static com.aero51.moviedatabase.utils.Constants.BOSNIAN_CHANNELS_PREFERENCE;
import static com.aero51.moviedatabase.utils.Constants.CROATIAN_CHANNELS_PREFERENCE;
import static com.aero51.moviedatabase.utils.Constants.MOVIE_CHANNELS_PREFERENCE;
import static com.aero51.moviedatabase.utils.Constants.MUSIC_CHANNELS_PREFERENCE;
import static com.aero51.moviedatabase.utils.Constants.NEWS_CHANNELS_PREFERENCE;
import static com.aero51.moviedatabase.utils.Constants.SERBIAN_CHANNELS_PREFERENCE;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
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

    private MenuItem prevMenuItem;
    private BottomNavigationView bottomNavigationView;

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

        viewPager = findViewById(R.id.view_pager);
        dynamicFragmentPagerAdapter = new DynamicFragmentPagerAdapter(getSupportFragmentManager(), MyApplication.getAppContext());
        //viewPager.setPagingEnabled(false);

        viewPager.setAdapter(dynamicFragmentPagerAdapter);
        // getSupportActionBar().setDisplayShowTitleEnabled(false);
        // getSupportActionBar().hide();

        //Initializing the bottomNavigationView
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

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
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {


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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_call:
                viewPager.setCurrentItem(0);
                break;
            case R.id.action_chat:
                viewPager.setCurrentItem(1);
                break;
            case R.id.action_contact:
                viewPager.setCurrentItem(2);
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

