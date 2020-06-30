package com.aero51.moviedatabase.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.epg.EpgChannel;
import com.aero51.moviedatabase.utils.Resource;
import com.aero51.moviedatabase.viewmodel.EpgTvViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EpgTvFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EpgTvFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EpgTvViewModel epgTvViewModel;

    public EpgTvFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EpgTvFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EpgTvFragment newInstance(String param1, String param2) {
        EpgTvFragment fragment = new EpgTvFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    epgTvViewModel= new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(EpgTvViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        epgTvViewModel.getChannels().observe(getViewLifecycleOwner(), new Observer<Resource<List<EpgChannel>>>() {
            @Override
            public void onChanged(Resource<List<EpgChannel>> listResource) {
                Log.d("moviedatabaselog", "EpgTvFragment onChanged getChannels code: " + listResource.code + " , status: " + listResource.status + " list size: " + listResource.data.size() + " ,message: " + listResource.message);

            }
        });

        return inflater.inflate(R.layout.fragment_epg_tv, container, false);
    }
}