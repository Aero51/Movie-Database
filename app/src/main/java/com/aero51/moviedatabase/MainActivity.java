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

import com.aero51.moviedatabase.ui.epg.EpgMovieDetailsFragment;
import com.aero51.moviedatabase.ui.epg.EpgMoviesAndTvShowsActorFragment;
import com.aero51.moviedatabase.ui.epg.EpgMoviesByGenreListFragment;
import com.aero51.moviedatabase.ui.epg.EpgTvShowDetailsFragment;
import com.aero51.moviedatabase.ui.epg.EpgTvShowsByGenreListFragment;
import com.aero51.moviedatabase.ui.favorites.FavoriteMovieActorFragment;
import com.aero51.moviedatabase.ui.favorites.FavoriteMovieDetailsFragment;
import com.aero51.moviedatabase.ui.favorites.FavoriteMovieDetailsFragmentSecond;
import com.aero51.moviedatabase.ui.favorites.FavoriteMoviesByGenreListFragment;
import com.aero51.moviedatabase.ui.favorites.FavoriteTvShowActorFragment;
import com.aero51.moviedatabase.ui.favorites.FavoriteTvShowDetailsFragment;
import com.aero51.moviedatabase.ui.favorites.FavoriteTvShowDetailsFragmentSecond;
import com.aero51.moviedatabase.ui.favorites.FavoriteTvShowsByGenreListFragment;
import com.aero51.moviedatabase.ui.movies.MovieActorFragment;
import com.aero51.moviedatabase.ui.CustomViewPager;
import com.aero51.moviedatabase.ui.favorites.FavouritesFragment;
import com.aero51.moviedatabase.ui.movies.MovieDetailsFragmentSecond;
import com.aero51.moviedatabase.ui.movies.MoviesByGenreListFragment;
import com.aero51.moviedatabase.ui.movies.MovieDetailsFragment;
import com.aero51.moviedatabase.ui.movies.MoviesByGenreListFragmentSecond;
import com.aero51.moviedatabase.ui.tvshows.TvShowActorFragment;
import com.aero51.moviedatabase.ui.tvshows.TvShowDetailsFragment;
import com.aero51.moviedatabase.ui.tvshows.TvShowDetailsFragmentSecond;
import com.aero51.moviedatabase.ui.tvshows.TvShowsByGenreListFragment;
import com.aero51.moviedatabase.ui.tvshows.TvShowsByGenreListFragmentSecond;
import com.aero51.moviedatabase.ui.tvshows.TvShowsFragment;
import com.aero51.moviedatabase.ui.adapter.DynamicFragmentPagerAdapter;
import com.aero51.moviedatabase.ui.epg.EpgAllProgramsFragment;
import com.aero51.moviedatabase.ui.epg.EpgDetailsFragment;
import com.aero51.moviedatabase.ui.epg.EpgFragment;
import com.aero51.moviedatabase.ui.movies.MoviesFragment;
import com.aero51.moviedatabase.utils.ChannelsPreferenceHelper;
import com.aero51.moviedatabase.utils.CheckAppStart;
import com.aero51.moviedatabase.utils.Constants;
import com.aero51.moviedatabase.utils.PrePopulatedChannels;
import com.aero51.moviedatabase.viewmodel.SharedViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;


import java.util.Locale;

import static com.aero51.moviedatabase.utils.Constants.BOSNIAN_CHANNELS_PREFERENCE;
import static com.aero51.moviedatabase.utils.Constants.CHILDREN_CHANNELS_PREFERENCE;
import static com.aero51.moviedatabase.utils.Constants.CROATIAN_CHANNELS_PREFERENCE;
import static com.aero51.moviedatabase.utils.Constants.NEWS_CHANNELS_PREFERENCE;
import static com.aero51.moviedatabase.utils.Constants.SERBIAN_CHANNELS_PREFERENCE;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private CustomViewPager customViewPager;
    private DynamicFragmentPagerAdapter dynamicFragmentPagerAdapter;
    private DynamicFragmentPagerAdapter.FragmentIdentifier epgFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier epgDetailsFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier epgDetailsFragmentIdentifierFromEpgAllProgramsFragment;
    private DynamicFragmentPagerAdapter.FragmentIdentifier epgAllProgramsFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier epgMovieDetailsFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier epgTvShowDetailsFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier moviesFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier tvShowsFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier favouritesFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier movieDetailsFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier movieDetailsFragmentIdentifierFromMovieActorFragment;
    private DynamicFragmentPagerAdapter.FragmentIdentifier movieDetailsFragmentIdentifierFromMoviesByGenreListFragment;
    private DynamicFragmentPagerAdapter.FragmentIdentifier favoriteMovieDetailsFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier favoriteTvShowDetailsFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier favoriteMovieActorFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier favoriteTvShowActorFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier favoriteMoviesByGenreListFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier favoriteTvShowsByGenreListFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier tvShowDetailsFragmentIdentifierFromTvShowActorFragment;
    private DynamicFragmentPagerAdapter.FragmentIdentifier tvShowDetailsFragmentIdentifierFromTvShowByGenreListFragment;
    private DynamicFragmentPagerAdapter.FragmentIdentifier epgActorFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier movieActorFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier tvActorFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier moviesByGenreListFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier moviesByGenreListFragmentFromMovieDetailsIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier tvShowsByGenreListFragmentFromTvShowDetailsIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier favoriteMovieDetailsFragmentFromMoviesByGenreIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier favoriteTvShowDetailsFragmentFromTvShowsByGenreIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier favoriteTvShowDetailsFragmentFromTvShowActorIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier favoriteMovieDetailsFragmentFromMovieActorIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier tvShowsByGenreListFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier epgMoviesByGenreListFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier epgTvShowsByGenreListFragmentIdentifier;
    private DynamicFragmentPagerAdapter.FragmentIdentifier tvShowDetailsFragmentIdentifier;
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
                Log.d(Constants.LOG2, "first time  run ");
                TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
                String countryCodeValue = tm.getNetworkCountryIso();
                //populating channel preferences on first app run to avoid empty channels list
                PrePopulatedChannels prePopulatedChannels = new PrePopulatedChannels();
                if (countryCodeValue != null) {
                    if (countryCodeValue.equalsIgnoreCase("hr")) {
                        ChannelsPreferenceHelper.firstRunPreferencePopulater(prePopulatedChannels.getCroatianList(), CROATIAN_CHANNELS_PREFERENCE);
                    } else if (countryCodeValue.equalsIgnoreCase("rs")) {
                        ChannelsPreferenceHelper.firstRunPreferencePopulater(prePopulatedChannels.getSerbianList(), SERBIAN_CHANNELS_PREFERENCE);
                    } else if (countryCodeValue.equalsIgnoreCase("sr")) {
                        ChannelsPreferenceHelper.firstRunPreferencePopulater(prePopulatedChannels.getSerbianList(), SERBIAN_CHANNELS_PREFERENCE);
                    } else if (countryCodeValue.equalsIgnoreCase("ba")) {
                        ChannelsPreferenceHelper.firstRunPreferencePopulater(prePopulatedChannels.getBosnianList(), BOSNIAN_CHANNELS_PREFERENCE);
                    }else if (countryCodeValue.equalsIgnoreCase("bs")) {
                        ChannelsPreferenceHelper.firstRunPreferencePopulater(prePopulatedChannels.getBosnianList(), BOSNIAN_CHANNELS_PREFERENCE);
                    }  else {
                        ChannelsPreferenceHelper.firstRunPreferencePopulater(prePopulatedChannels.getNewsList(), NEWS_CHANNELS_PREFERENCE);
                        ChannelsPreferenceHelper.firstRunPreferencePopulater(prePopulatedChannels.getChildrenList(), CHILDREN_CHANNELS_PREFERENCE);
                    }
                }else {
                    String language = Locale.getDefault().getLanguage();
                    if (language.equalsIgnoreCase(new Locale("hr").getLanguage())) {
                        ChannelsPreferenceHelper.firstRunPreferencePopulater(prePopulatedChannels.getCroatianList(), CROATIAN_CHANNELS_PREFERENCE);
                    } else if (language.equalsIgnoreCase(new Locale("rs").getLanguage())) {
                        ChannelsPreferenceHelper.firstRunPreferencePopulater(prePopulatedChannels.getSerbianList(), SERBIAN_CHANNELS_PREFERENCE);
                    } else if (language.equalsIgnoreCase(new Locale("sr").getLanguage())) {
                        ChannelsPreferenceHelper.firstRunPreferencePopulater(prePopulatedChannels.getSerbianList(), SERBIAN_CHANNELS_PREFERENCE);
                    } else if (language.equalsIgnoreCase(new Locale("ba").getLanguage())) {
                        ChannelsPreferenceHelper.firstRunPreferencePopulater(prePopulatedChannels.getBosnianList(), BOSNIAN_CHANNELS_PREFERENCE);
                    } else if (language.equalsIgnoreCase(new Locale("bs").getLanguage())) {
                        ChannelsPreferenceHelper.firstRunPreferencePopulater(prePopulatedChannels.getBosnianList(), BOSNIAN_CHANNELS_PREFERENCE);
                    } else {
                        ChannelsPreferenceHelper.firstRunPreferencePopulater(prePopulatedChannels.getNewsList(), NEWS_CHANNELS_PREFERENCE);
                        ChannelsPreferenceHelper.firstRunPreferencePopulater(prePopulatedChannels.getChildrenList(), CHILDREN_CHANNELS_PREFERENCE);
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
        dynamicFragmentPagerAdapter = new DynamicFragmentPagerAdapter(getSupportFragmentManager(), MyApplication.getAppContext());

        customViewPager.setAdapter(dynamicFragmentPagerAdapter);


        //Initializing the bottomNavigationView
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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
        registerShouldSwitchTvShowFragmentsObservers();
        registerShouldSwitchFavoriteMovieFragmentsObservers();
        registerShouldSwitchFavoriteTvShowFragmentsObservers();
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
        favouritesFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier(FavouritesFragment.class.getSimpleName(), null) {
            @Override
            protected Fragment createFragment() {
                return new FavouritesFragment();
            }

            @Override
            public int describeContents() {
                return 0;
            }
        };

        dynamicFragmentPagerAdapter.addFragment(epgFragmentIdentifier);
        dynamicFragmentPagerAdapter.addFragment(moviesFragmentIdentifier);
        dynamicFragmentPagerAdapter.addFragment(tvShowsFragmentIdentifier);
        dynamicFragmentPagerAdapter.addFragment(favouritesFragmentIdentifier);
    }


    private void registerShouldSwitchEpgFragmentsObservers() {
        sharedViewModel.getSingleLiveShouldSwitchToEpgTvDetailsFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

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

        sharedViewModel.getSingleLiveShouldSwitchToEpgTvDetailsFragmentFromEpgAllProgramsFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                epgDetailsFragmentIdentifierFromEpgAllProgramsFragment = new DynamicFragmentPagerAdapter.FragmentIdentifier(EpgDetailsFragment.class.getSimpleName() + "FromEpgAllProgramsFragment", null) {
                    @Override
                    protected Fragment createFragment() {
                        return new EpgDetailsFragment();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }
                };
                replaceFragment(0, epgDetailsFragmentIdentifierFromEpgAllProgramsFragment);
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
        sharedViewModel.getSingleLiveShouldSwitchEpgActorFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                epgActorFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier(EpgMoviesAndTvShowsActorFragment.class.getSimpleName(), null) {
                    @Override
                    protected Fragment createFragment() {
                        return new EpgMoviesAndTvShowsActorFragment();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }
                };
                replaceFragment(0, epgActorFragmentIdentifier);
                //customViewPager.setCurrentItem(0);
            }
        });
        sharedViewModel.getSingleLiveShouldSwitchEpgMovieDetailsFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                epgMovieDetailsFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier(EpgMovieDetailsFragment.class.getSimpleName(), null) {
                    @Override
                    protected Fragment createFragment() {
                        return new EpgMovieDetailsFragment();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }
                };
                replaceFragment(0, epgMovieDetailsFragmentIdentifier);
                //customViewPager.setCurrentItem(0);
            }
        });
        sharedViewModel.getSingleLiveShouldSwitchEpgTvShowDetailsFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                epgTvShowDetailsFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier(EpgTvShowDetailsFragment.class.getSimpleName(), null) {
                    @Override
                    protected Fragment createFragment() {
                        return new EpgTvShowDetailsFragment();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }
                };
                replaceFragment(0, epgTvShowDetailsFragmentIdentifier);
                //customViewPager.setCurrentItem(0);
            }
        });
        sharedViewModel.getSingleLiveShouldSwitchEpgMoviesByGenreListFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                epgMoviesByGenreListFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier(EpgMoviesByGenreListFragment.class.getSimpleName(), null) {
                    @Override
                    protected Fragment createFragment() {
                        return new EpgMoviesByGenreListFragment();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }
                };
                replaceFragment(0, epgMoviesByGenreListFragmentIdentifier);
            }
        });
        sharedViewModel.getSingleLiveShouldSwitchEpgTvShowsByGenreListFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                epgTvShowsByGenreListFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier(EpgTvShowsByGenreListFragment.class.getSimpleName(), null) {
                    @Override
                    protected Fragment createFragment() {
                        return new EpgTvShowsByGenreListFragment();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }
                };
                replaceFragment(0, epgTvShowsByGenreListFragmentIdentifier);
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
        sharedViewModel.getSingleLiveShouldSwitchMovieDetailsFragmentFromMovieActorFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                movieDetailsFragmentIdentifierFromMovieActorFragment = new DynamicFragmentPagerAdapter.FragmentIdentifier(MovieDetailsFragmentSecond.class.getSimpleName(), null) {
                    @Override
                    protected Fragment createFragment() {
                        return new MovieDetailsFragmentSecond();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }
                };

                replaceFragment(1, movieDetailsFragmentIdentifierFromMovieActorFragment);
                customViewPager.setCurrentItem(1);
            }
        });
        sharedViewModel.getSingleLiveShouldSwitchMovieDetailFragmentFromMoviesByGenreListFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                movieDetailsFragmentIdentifierFromMoviesByGenreListFragment = new DynamicFragmentPagerAdapter.FragmentIdentifier(MovieDetailsFragmentSecond.class.getSimpleName() + "FromMoviesByGenreListFragment", null) {
                    @Override
                    protected Fragment createFragment() {
                        return new MovieDetailsFragmentSecond();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }
                };

                replaceFragment(1, movieDetailsFragmentIdentifierFromMoviesByGenreListFragment);
                customViewPager.setCurrentItem(1);
            }
        });


        sharedViewModel.getSingleLiveShouldSwitchMoviesByGenreListFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                moviesByGenreListFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier(MoviesByGenreListFragment.class.getSimpleName(), null) {
                    @Override
                    protected Fragment createFragment() {
                        return new MoviesByGenreListFragment();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }
                };
                replaceFragment(1, moviesByGenreListFragmentIdentifier);
                customViewPager.setCurrentItem(1);
            }
        });
        sharedViewModel.getSingleLiveShouldSwitchMoviesByGenreListFragmentFromMovieDetailsFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                moviesByGenreListFragmentFromMovieDetailsIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier(MoviesByGenreListFragmentSecond.class.getSimpleName(), null) {
                    @Override
                    protected Fragment createFragment() {
                        return new MoviesByGenreListFragmentSecond();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }
                };
                replaceFragment(1, moviesByGenreListFragmentFromMovieDetailsIdentifier);
                customViewPager.setCurrentItem(1);
            }
        });

        sharedViewModel.getSingleLiveShouldSwitchMovieActorFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                movieActorFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier(MovieActorFragment.class.getSimpleName(), null) {
                    @Override
                    protected Fragment createFragment() {
                        return new MovieActorFragment();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }
                };
                replaceFragment(1, movieActorFragmentIdentifier);
                customViewPager.setCurrentItem(1);
            }
        });

    }

    private void registerShouldSwitchTvShowFragmentsObservers() {

        sharedViewModel.getSingleLiveShouldSwitchTvShowDetailsFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                tvShowDetailsFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier(TvShowDetailsFragment.class.getSimpleName(), null) {
                    @Override
                    protected Fragment createFragment() {
                        return new TvShowDetailsFragment();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }
                };
                replaceFragment(2, tvShowDetailsFragmentIdentifier);
                customViewPager.setCurrentItem(2);
            }
        });


        sharedViewModel.getSingleLiveShouldSwitchTvShowsByGenreListFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                tvShowsByGenreListFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier(TvShowsByGenreListFragment.class.getSimpleName(), null) {
                    @Override
                    protected Fragment createFragment() {
                        return new TvShowsByGenreListFragment();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }
                };
                replaceFragment(2, tvShowsByGenreListFragmentIdentifier);
                customViewPager.setCurrentItem(2);
            }
        });
        sharedViewModel.getSingleLiveShouldSwitchTvShowsByGenreListFragmentFromTvShowDetailsFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                tvShowsByGenreListFragmentFromTvShowDetailsIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier(TvShowsByGenreListFragmentSecond.class.getSimpleName(), null) {
                    @Override
                    protected Fragment createFragment() {
                        return new TvShowsByGenreListFragmentSecond();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }
                };
                replaceFragment(2, tvShowsByGenreListFragmentFromTvShowDetailsIdentifier);
                customViewPager.setCurrentItem(2);
            }
        });

        sharedViewModel.getSingleLiveShouldSwitchTvActorFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                tvActorFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier(TvShowActorFragment.class.getSimpleName(), null) {
                    @Override
                    protected Fragment createFragment() {
                        return new TvShowActorFragment();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }
                };
                replaceFragment(2, tvActorFragmentIdentifier);
                customViewPager.setCurrentItem(2);
            }
        });
        sharedViewModel.getSingleLiveShouldSwitchTvShowDetailFragmentFromTvShowsByGenreListFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                tvShowDetailsFragmentIdentifierFromTvShowByGenreListFragment = new DynamicFragmentPagerAdapter.FragmentIdentifier(TvShowDetailsFragmentSecond.class.getSimpleName() + "FromTvShowsByGenreListFragment", null) {
                    @Override
                    protected Fragment createFragment() {
                        return new TvShowDetailsFragmentSecond();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }
                };
                replaceFragment(2, tvShowDetailsFragmentIdentifierFromTvShowByGenreListFragment);
                customViewPager.setCurrentItem(2);
            }
        });

        sharedViewModel.getSingleLiveShouldSwitchTvShowDetailsFragmentFromTvShowActorFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                tvShowDetailsFragmentIdentifierFromTvShowActorFragment = new DynamicFragmentPagerAdapter.FragmentIdentifier(TvShowDetailsFragmentSecond.class.getSimpleName(), null) {
                    @Override
                    protected Fragment createFragment() {
                        return new TvShowDetailsFragmentSecond();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }
                };

                replaceFragment(2, tvShowDetailsFragmentIdentifierFromTvShowActorFragment);
                customViewPager.setCurrentItem(2);
            }
        });


    }

    private void registerShouldSwitchFavoriteMovieFragmentsObservers() {
        sharedViewModel.getSingleLiveShouldSwitchFavoriteMovieDetailsFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                favoriteMovieDetailsFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier(FavoriteMovieDetailsFragment.class.getSimpleName(), null) {
                    @Override
                    protected Fragment createFragment() {
                        return new FavoriteMovieDetailsFragment();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }
                };
                replaceFragment(3, favoriteMovieDetailsFragmentIdentifier);
                customViewPager.setCurrentItem(3);
            }
        });


        sharedViewModel.getSingleLiveShouldSwitchFavoriteMovieActorFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                favoriteMovieActorFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier(FavoriteMovieActorFragment.class.getSimpleName(), null) {
                    @Override
                    protected Fragment createFragment() {
                        return new FavoriteMovieActorFragment();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }
                };
                replaceFragment(3, favoriteMovieActorFragmentIdentifier);
                customViewPager.setCurrentItem(3);
            }
        });
        sharedViewModel.getSingleLiveShouldSwitchFavoriteMoviesByGenreListFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                favoriteMoviesByGenreListFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier(FavoriteMoviesByGenreListFragment.class.getSimpleName(), null) {
                    @Override
                    protected Fragment createFragment() {
                        return new FavoriteMoviesByGenreListFragment();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }
                };
                replaceFragment(3, favoriteMoviesByGenreListFragmentIdentifier);
                customViewPager.setCurrentItem(3);
            }
        });
        sharedViewModel.getSingleLiveShouldSwitchFavoriteMovieDetailsFragmentFromMovieByGenreFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                favoriteMovieDetailsFragmentFromMoviesByGenreIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier(FavoriteMovieDetailsFragmentSecond.class.getSimpleName(), null) {
                    @Override
                    protected Fragment createFragment() {
                        return new FavoriteMovieDetailsFragmentSecond();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }
                };
                replaceFragment(3, favoriteMovieDetailsFragmentFromMoviesByGenreIdentifier);
                customViewPager.setCurrentItem(3);
            }
        });

        sharedViewModel.getSingleLiveShouldSwitchFavoriteMovieDetailFragmentFromMovieActorFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                favoriteMovieDetailsFragmentFromMovieActorIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier(FavoriteMovieDetailsFragmentSecond.class.getSimpleName() + "fromMovieActorFragment", null) {
                    @Override
                    protected Fragment createFragment() {
                        return new FavoriteMovieDetailsFragmentSecond();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }
                };
                replaceFragment(3, favoriteMovieDetailsFragmentFromMovieActorIdentifier);
                customViewPager.setCurrentItem(3);
            }
        });

    }

    private void registerShouldSwitchFavoriteTvShowFragmentsObservers() {
        sharedViewModel.getSingleLiveShouldSwitchFavoriteTvShowDetailsFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                favoriteTvShowDetailsFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier(FavoriteTvShowDetailsFragment.class.getSimpleName(), null) {
                    @Override
                    protected Fragment createFragment() {
                        return new FavoriteTvShowDetailsFragment();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }
                };
                replaceFragment(3, favoriteTvShowDetailsFragmentIdentifier);
                customViewPager.setCurrentItem(3);

            }
        });

        sharedViewModel.getSingleLiveShouldSwitchFavoriteTvActorFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                favoriteTvShowActorFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier(FavoriteTvShowActorFragment.class.getSimpleName(), null) {
                    @Override
                    protected Fragment createFragment() {
                        return new FavoriteTvShowActorFragment();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }
                };
                replaceFragment(3, favoriteTvShowActorFragmentIdentifier);
                customViewPager.setCurrentItem(3);
            }
        });
        sharedViewModel.getSingleLiveShouldSwitchFavoriteTvShowsByGenreListFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                favoriteTvShowsByGenreListFragmentIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier(FavoriteTvShowsByGenreListFragment.class.getSimpleName(), null) {
                    @Override
                    protected Fragment createFragment() {
                        return new FavoriteTvShowsByGenreListFragment();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }
                };
                replaceFragment(3, favoriteTvShowsByGenreListFragmentIdentifier);
                customViewPager.setCurrentItem(3);
            }
        });

        sharedViewModel.getSingleLiveShouldSwitchFavoriteTvShowDetailsFragmentFromTvShowByGenreFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                favoriteTvShowDetailsFragmentFromTvShowsByGenreIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier(FavoriteTvShowDetailsFragmentSecond.class.getSimpleName(), null) {
                    @Override
                    protected Fragment createFragment() {
                        return new FavoriteTvShowDetailsFragmentSecond();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }
                };
                replaceFragment(3, favoriteTvShowDetailsFragmentFromTvShowsByGenreIdentifier);
                customViewPager.setCurrentItem(3);
            }
        });
        sharedViewModel.getSingleLiveShouldSwitchFavoriteTvShowDetailsFragmentFromTvShowActorFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                favoriteTvShowDetailsFragmentFromTvShowActorIdentifier = new DynamicFragmentPagerAdapter.FragmentIdentifier(FavoriteTvShowDetailsFragmentSecond.class.getSimpleName() + "fromTvActorFragment", null) {
                    @Override
                    protected Fragment createFragment() {
                        return new FavoriteTvShowDetailsFragmentSecond();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }
                };
                replaceFragment(3, favoriteTvShowDetailsFragmentFromTvShowActorIdentifier);
                customViewPager.setCurrentItem(3);
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
                } else if (currentFragmentTag.equals(EpgDetailsFragment.class.getSimpleName() + "FromEpgAllProgramsFragment")) {
                    replaceFragment(0, epgAllProgramsFragmentIdentifier);
                } else if (currentFragmentTag.equals(EpgAllProgramsFragment.class.getSimpleName())) {
                    replaceFragment(0, epgFragmentIdentifier);
                } else if (currentFragmentTag.equals(EpgMoviesAndTvShowsActorFragment.class.getSimpleName())) {
                    replaceFragment(0, epgDetailsFragmentIdentifier);
                } else if (currentFragmentTag.equals(EpgMovieDetailsFragment.class.getSimpleName())) {
                    replaceFragment(0, epgActorFragmentIdentifier);
                } else if (currentFragmentTag.equals(EpgTvShowDetailsFragment.class.getSimpleName())) {
                    replaceFragment(0, epgActorFragmentIdentifier);
                } else if (currentFragmentTag.equals(EpgMoviesByGenreListFragment.class.getSimpleName())) {
                    replaceFragment(0, epgMovieDetailsFragmentIdentifier);
                } else if (currentFragmentTag.equals(EpgTvShowsByGenreListFragment.class.getSimpleName())) {
                    replaceFragment(0, epgTvShowDetailsFragmentIdentifier);
                }

                break;

            case 1:
                if (currentFragmentTag.equals(MoviesFragment.class.getSimpleName())) {
                    super.onBackPressed();
                } else if (currentFragmentTag.equals(MovieDetailsFragment.class.getSimpleName())) {
                    replaceFragment(1, moviesFragmentIdentifier);
                    customViewPager.setCurrentItem(1);
                } else if (currentFragmentTag.equals(MovieActorFragment.class.getSimpleName())) {
                    replaceFragment(1, movieDetailsFragmentIdentifier);
                    customViewPager.setCurrentItem(1);
                } else if (currentFragmentTag.equals(MoviesByGenreListFragment.class.getSimpleName())) {
                    replaceFragment(1, moviesFragmentIdentifier);
                    customViewPager.setCurrentItem(1);
                } else if (currentFragmentTag.equals(MoviesByGenreListFragmentSecond.class.getSimpleName())) {
                    replaceFragment(1, movieDetailsFragmentIdentifier);
                    customViewPager.setCurrentItem(1);
                } else if (currentFragmentTag.equals(MovieDetailsFragmentSecond.class.getSimpleName())) {
                    replaceFragment(1, movieActorFragmentIdentifier);
                    customViewPager.setCurrentItem(1);
                } else if (currentFragmentTag.equals(MovieDetailsFragmentSecond.class.getSimpleName() + "FromMoviesByGenreListFragment")) {
                    replaceFragment(1, moviesByGenreListFragmentFromMovieDetailsIdentifier);
                    customViewPager.setCurrentItem(1);
                }

                break;
            case 2:
                if (currentFragmentTag.equals(TvShowsFragment.class.getSimpleName())) {
                    super.onBackPressed();
                } else if (currentFragmentTag.equals(TvShowDetailsFragment.class.getSimpleName())) {
                    replaceFragment(2, tvShowsFragmentIdentifier);
                    customViewPager.setCurrentItem(2);

                } else if (currentFragmentTag.equals(TvShowsByGenreListFragment.class.getSimpleName())) {
                    replaceFragment(2, tvShowsFragmentIdentifier);
                    customViewPager.setCurrentItem(2);
                } else if (currentFragmentTag.equals(TvShowActorFragment.class.getSimpleName())) {
                    replaceFragment(2, tvShowDetailsFragmentIdentifier);
                    customViewPager.setCurrentItem(2);
                } else if (currentFragmentTag.equals(TvShowDetailsFragmentSecond.class.getSimpleName())) {
                    replaceFragment(2, tvActorFragmentIdentifier);
                    customViewPager.setCurrentItem(2);
                } else if (currentFragmentTag.equals(TvShowsByGenreListFragmentSecond.class.getSimpleName())) {
                    replaceFragment(2, tvShowDetailsFragmentIdentifier);
                    customViewPager.setCurrentItem(2);
                } else if (currentFragmentTag.equals(TvShowDetailsFragmentSecond.class.getSimpleName() + "FromTvShowsByGenreListFragment")) {
                    replaceFragment(2, tvShowsByGenreListFragmentFromTvShowDetailsIdentifier);
                    customViewPager.setCurrentItem(2);
                }


                break;
            case 3:
                if (currentFragmentTag.equals(FavouritesFragment.class.getSimpleName())) {
                    super.onBackPressed();
                } else if (currentFragmentTag.equals(FavoriteMovieDetailsFragment.class.getSimpleName())) {
                    replaceFragment(3, favouritesFragmentIdentifier);
                    customViewPager.setCurrentItem(3);
                } else if (currentFragmentTag.equals(FavoriteTvShowDetailsFragment.class.getSimpleName())) {
                    replaceFragment(3, favouritesFragmentIdentifier);
                    customViewPager.setCurrentItem(3);
                } else if (currentFragmentTag.equals(FavoriteMovieActorFragment.class.getSimpleName())) {
                    replaceFragment(3, favoriteMovieDetailsFragmentIdentifier);
                    customViewPager.setCurrentItem(3);
                } else if (currentFragmentTag.equals(FavoriteMoviesByGenreListFragment.class.getSimpleName())) {
                    replaceFragment(3, favoriteMovieDetailsFragmentIdentifier);
                    customViewPager.setCurrentItem(3);
                } else if (currentFragmentTag.equals(FavoriteMovieDetailsFragmentSecond.class.getSimpleName())) {
                    replaceFragment(3, favoriteMoviesByGenreListFragmentIdentifier);
                    customViewPager.setCurrentItem(3);
                } else if (currentFragmentTag.equals(FavoriteMovieDetailsFragmentSecond.class.getSimpleName() + "fromMovieActorFragment")) {
                    replaceFragment(3, favoriteMovieActorFragmentIdentifier);
                    customViewPager.setCurrentItem(3);
                } else if (currentFragmentTag.equals(FavoriteTvShowActorFragment.class.getSimpleName())) {
                    replaceFragment(3, favoriteTvShowDetailsFragmentIdentifier);
                    customViewPager.setCurrentItem(3);
                } else if (currentFragmentTag.equals(FavoriteTvShowsByGenreListFragment.class.getSimpleName())) {
                    replaceFragment(3, favoriteTvShowDetailsFragmentIdentifier);
                    customViewPager.setCurrentItem(3);
                } else if (currentFragmentTag.equals(FavoriteTvShowDetailsFragmentSecond.class.getSimpleName())) {
                    replaceFragment(3, favoriteTvShowsByGenreListFragmentIdentifier);
                    customViewPager.setCurrentItem(3);
                } else if (currentFragmentTag.equals(FavoriteTvShowDetailsFragmentSecond.class.getSimpleName() + "fromTvActorFragment")) {
                    replaceFragment(3, favoriteTvShowActorFragmentIdentifier);
                    customViewPager.setCurrentItem(3);
                }
        }


    }

    private void addRemoveBackButton(int position, String currentFragmentTag) {

        switch (position) {
            case 0:
                if (currentFragmentTag.equals(EpgFragment.class.getSimpleName())) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                } else {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
                break;

            case 1:
                if (currentFragmentTag.equals(MoviesFragment.class.getSimpleName())) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                } else {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
                break;

            case 2:
                if (currentFragmentTag.equals(TvShowsFragment.class.getSimpleName())) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                } else {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
                break;

            case 3:
                if (currentFragmentTag.equals(FavouritesFragment.class.getSimpleName())) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                } else {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
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
                addRemoveBackButton(1, currentFragmentTag);
                break;
            case R.id.action_tv_shows:
                customViewPager.setCurrentItem(2);
                currentFragmentTag = dynamicFragmentPagerAdapter.getFragmentTagForPosition(2);
                addRemoveBackButton(2, currentFragmentTag);
                break;
            case R.id.action_favourites:
                customViewPager.setCurrentItem(3);
                currentFragmentTag = dynamicFragmentPagerAdapter.getFragmentTagForPosition(3);
                addRemoveBackButton(3, currentFragmentTag);
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

