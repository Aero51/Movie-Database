package com.aero51.moviedatabase.ui;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.aero51.moviedatabase.R;


public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragmentsContainer) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
            // Create a new Fragment to be placed in the activity layout

           // TopRatedMoviesFragment moviesListFragment = TopRatedMoviesFragment.newInstance("","");
            MoviesFragment moviesFragment=MoviesFragment.newInstance("","");


            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments

          //  moviesListFragment.setArguments(getIntent().getExtras());
            moviesFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragmentsContainer' FrameLayout

          //  getSupportFragmentManager().beginTransaction().add(R.id.fragmentsContainer, moviesListFragment).commit();
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentsContainer, moviesFragment).commit();
        }
    }

}

