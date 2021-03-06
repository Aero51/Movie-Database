package com.aero51.moviedatabase.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.epg.ChannelWithPrograms;
import com.aero51.moviedatabase.ui.adapter.EpgAllProgramsAdapter;
import com.aero51.moviedatabase.utils.Constants;
import com.aero51.moviedatabase.viewmodel.SharedViewModel;


public class EpgAllProgramsFragment extends Fragment {



    private SharedViewModel sharedViewModel;
    private RecyclerView recycler_view_all_programs;

    public EpgAllProgramsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_epg_all_programs, container, false);
        recycler_view_all_programs = view.findViewById(R.id.all_programs_recycler_view);
        recycler_view_all_programs.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recycler_view_all_programs.setLayoutManager(linearLayoutManager);
        recycler_view_all_programs.addItemDecoration(new DividerItemDecoration(recycler_view_all_programs.getContext(), linearLayoutManager.getOrientation()));

        sharedViewModel.getLiveDataChannelWithPrograms().observe(getViewLifecycleOwner(), new Observer<ChannelWithPrograms>() {
            @Override
            public void onChanged(ChannelWithPrograms channelWithPrograms) {

                EpgAllProgramsAdapter adapter =new EpgAllProgramsAdapter(channelWithPrograms);
                recycler_view_all_programs.setAdapter(adapter);
                recycler_view_all_programs.scrollToPosition(channelWithPrograms.getNearestTimePosition());
            }
        });
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        //toolbar.setTitle("text");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
                Log.d(Constants.LOG, "Toolbar clicked!");
                showBackButton(false);
            }
        });
        showBackButton(true);
        return view;
    }
    public void showBackButton(boolean show) {
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(show);
        }
    }
}