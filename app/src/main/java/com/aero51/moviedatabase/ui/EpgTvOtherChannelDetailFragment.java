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
import android.widget.ImageView;
import android.widget.TextView;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.epg.EpgOtherChannel;
import com.aero51.moviedatabase.repository.model.epg.EpgProgram;
import com.aero51.moviedatabase.ui.adapter.EpgTvOtherChannelDetailAdapter;
import com.aero51.moviedatabase.utils.ProgramItemClickListener;
import com.aero51.moviedatabase.utils.Resource;
import com.aero51.moviedatabase.viewmodel.EpgTvViewModel;
import com.aero51.moviedatabase.viewmodel.SharedViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EpgTvOtherChannelDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EpgTvOtherChannelDetailFragment extends Fragment implements ProgramItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EpgTvViewModel epgTvViewModel;
    private SharedViewModel sharedViewModel;
    private TextView text_view_tv_channel;
    private ImageView image_view_tv_channel_logo;
    private RecyclerView other_channel_recycler_view;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EpgTvOtherChannelDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EpgTvOtherChannelDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EpgTvOtherChannelDetailFragment newInstance(String param1, String param2) {
        EpgTvOtherChannelDetailFragment fragment = new EpgTvOtherChannelDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        epgTvViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(EpgTvViewModel.class);
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
        View view = inflater.inflate(R.layout.fragment_epg_tv_other_channel_detail, container, false);

        text_view_tv_channel = view.findViewById(R.id.text_view_tv_channel);
        image_view_tv_channel_logo = view.findViewById(R.id.image_view_tv_channel_logo);
        other_channel_recycler_view = view.findViewById(R.id.other_channel_recycler_view);
        other_channel_recycler_view.setHasFixedSize(true);
        other_channel_recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));

        sharedViewModel.getLiveDataOtherChannel().observe(getViewLifecycleOwner(), new Observer<EpgOtherChannel>() {
            @Override
            public void onChanged(EpgOtherChannel otherChannel) {
                registerGetProgramsForChannel(otherChannel.getChannelName());
                text_view_tv_channel.setText(otherChannel.getChannelDisplayName());
                Picasso.get().load(otherChannel.getDrawableInteger()).fit().into(image_view_tv_channel_logo);

            }
        });


        return view;
    }

    public void registerGetProgramsForChannel(String channelName) {
        epgTvViewModel.getOtherChannelPrograms(channelName).observe(getViewLifecycleOwner(), new Observer<Resource<List<EpgProgram>>>() {
            @Override
            public void onChanged(Resource<List<EpgProgram>> listResource) {
                Log.d("moviedatabaselog", "EpgTvOtherChannelDetailFragment onChanged get Programs code: " + listResource.code + " , status: " + listResource.status + " list size: " + listResource.data.size() + " ,message: " + listResource.message);
                if(listResource.data.size()>0) {
                    EpgTvOtherChannelDetailAdapter epgTvOtherChannelDetailAdapter = new EpgTvOtherChannelDetailAdapter(listResource.data, EpgTvOtherChannelDetailFragment.this::onItemClick);
                    other_channel_recycler_view.setAdapter(epgTvOtherChannelDetailAdapter);
                }
            }
        });

    }

    @Override
    public void onItemClick(int position, int db_id, EpgProgram epgProgram) {
        sharedViewModel.changeEpgTvFragment(position, epgProgram);
    }
}