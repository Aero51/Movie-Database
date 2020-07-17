package com.aero51.moviedatabase.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.epg.EpgChannel;
import com.aero51.moviedatabase.repository.model.epg.EpgOtherChannel;
import com.aero51.moviedatabase.repository.model.epg.EpgProgram;
import com.aero51.moviedatabase.ui.adapter.EpgTvAdapter;
import com.aero51.moviedatabase.utils.EndlessRecyclerViewScrollListener;
import com.aero51.moviedatabase.utils.OtherChannelItemClickListener;
import com.aero51.moviedatabase.utils.ProgramItemClickListener;
import com.aero51.moviedatabase.utils.Resource;
import com.aero51.moviedatabase.viewmodel.EpgTvViewModel;
import com.aero51.moviedatabase.viewmodel.SharedViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EpgTvFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EpgTvFragment extends Fragment implements ProgramItemClickListener, OtherChannelItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private EpgTvViewModel epgTvViewModel;
    private RecyclerView recycler_view_epg_tv;
    private EpgTvAdapter epgTvAdapter;

    private TextView text_view_fragment_epg_tv;
    private SharedViewModel sharedViewModel;

    private EndlessRecyclerViewScrollListener scrollListener;

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

        epgTvViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(EpgTvViewModel.class);
        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_epg_tv, container, false);

        recycler_view_epg_tv = view.findViewById(R.id.recycler_view_cro_parent);
       // recycler_view_epg_tv_cro_channels.setHasFixedSize(true);

        //  recycler_view_epg_tv_cro_channels.setLayoutManager(new SpeedyLinearLayoutManager(getContext(),SpeedyLinearLayoutManager.VERTICAL,false));

        recycler_view_epg_tv.setNestedScrollingEnabled(true);
        text_view_fragment_epg_tv = view.findViewById(R.id.tv_fragment_epg_tv_cro);




        registerAllChannelsObserver();

        return view;
    }


    
    private void registerAllChannelsObserver() {
        epgTvViewModel.getChannels().observe(getViewLifecycleOwner(), new Observer<Resource<List<EpgChannel>>>() {
            @Override
            public void onChanged(Resource<List<EpgChannel>> listResource) {
                Log.d("moviedatabaselog", "EpgTvFragment onChanged getChannels code: " + listResource.code + " , status: " + listResource.status + " list size: " + listResource.data.size() + " ,message: " + listResource.message);
                if (listResource.data.size() > 0) {
                    registerProgramsPagedListObserver(listResource.data);
                }
            }
        });
    }

    private void registerProgramsPagedListObserver(List<EpgChannel> channelList) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycler_view_epg_tv.setLayoutManager(linearLayoutManager);
        epgTvAdapter = new EpgTvAdapter(channelList);
        recycler_view_epg_tv.setAdapter(epgTvAdapter);
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.d("moviedatabaselog", "EndlessRecyclerViewScrollListener page: " + page + " total items count: " + totalItemsCount);
                // registerGetProgramsForChannel(channelList.get(totalItemsCount).getName(), epgTvCroChannelsNewAdapter);
            }
        };
        recycler_view_epg_tv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
              //  super.onScrolled(recyclerView, dx, dy);
                if ( !recyclerView.canScrollVertically(1)) {

                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == epgTvAdapter.getItemCount() - 1) {
                        Log.d("moviedatabaselog", "Load more data! findLastCompletelyVisibleItemPosition: " + linearLayoutManager.findLastVisibleItemPosition());
                       // registerGetProgramsForChannel(channelList.get(epgTvCroChannelsNewAdapter.getItemCount()).getName());

                    }
                }

            }
        });
        registerGetProgramsForChannel("HRT1");


    }

    public void registerGetProgramsForChannel(String channelName) {


        epgTvViewModel.getProgramsForChannel(channelName).observe(getViewLifecycleOwner(), new Observer<Resource<List<EpgProgram>>>() {
            @Override
            public void onChanged(Resource<List<EpgProgram>> listResource) {

                if (listResource.data.size() > 0) {
                    Log.d("moviedatabaselog", "EpgTvFragment onChanged channelName: " + channelName + " ,get Programs code: " + listResource.code + " , status: " + listResource.status + " list size: " + listResource.data.size() + " ,message: " + listResource.message);
                    // epgTvViewModel.getOtherChannelPrograms(channelName).removeObserver(this);
                    //epgTvViewModel.getOtherChannelPrograms(channelName).removeObservers(getViewLifecycleOwner());
                    epgTvViewModel.getResourceLiveData().removeObserver(this);
                    epgTvAdapter.setList(listResource.data);
                }
            }
        });

    }

    @Override
    public void onItemClick(int position, int db_id, EpgProgram epgProgram) {
        sharedViewModel.changeEpgTvFragment(position, epgProgram);

    }

    @Override
    public void onItemClick(int position, EpgOtherChannel otherChannel) {
        sharedViewModel.changeEpgTvOtherChannelDetailFragment(position, otherChannel);
    }
}