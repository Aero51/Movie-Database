package com.aero51.moviedatabase.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.aero51.moviedatabase.R;

public class RootEpgTvFragment extends Fragment {

    private static final String TAG = "RootEpgTvFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /* Inflate the layout for this fragment */
        View view = inflater.inflate(R.layout.root_epg_tv_fragment, container, false);

        FragmentTransaction transaction = getChildFragmentManager()
                .beginTransaction();
        /*
         * When this container fragment is created, we fill it with our first
         * "real" fragment
         */
        transaction.replace(R.id.root_epg_frame, EpgTvFragment.newInstance("",""));

        transaction.commit();

        return view;
    }

}