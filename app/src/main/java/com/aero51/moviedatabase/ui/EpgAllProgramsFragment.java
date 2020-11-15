package com.aero51.moviedatabase.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.epg.ChannelWithPrograms;
import com.aero51.moviedatabase.ui.adapter.EpgAllProgramsAdapter;
import com.aero51.moviedatabase.viewmodel.SharedViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EpgAllProgramsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EpgAllProgramsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SharedViewModel sharedViewModel;
    private RecyclerView recycler_view_all_programs;

    public EpgAllProgramsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EpgAllProgramsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EpgAllProgramsFragment newInstance(String param1, String param2) {
        EpgAllProgramsFragment fragment = new EpgAllProgramsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

        sharedViewModel.getLiveDataChannelWithPrograms().observe(getViewLifecycleOwner(), new Observer<ChannelWithPrograms>() {
            @Override
            public void onChanged(ChannelWithPrograms channelWithPrograms) {

                EpgAllProgramsAdapter adapter =new EpgAllProgramsAdapter(channelWithPrograms);
                recycler_view_all_programs.setAdapter(adapter);
            }
        });
        return view;
    }
}