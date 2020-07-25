package com.aero51.moviedatabase.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
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
import com.aero51.moviedatabase.repository.model.epg.EpgChildItem;
import com.aero51.moviedatabase.repository.model.epg.EpgProgram;
import com.aero51.moviedatabase.ui.adapter.EpgTvAdapter;
import com.aero51.moviedatabase.utils.EndlessRecyclerViewScrollListener;
import com.aero51.moviedatabase.utils.NearestTimeHelper;
import com.aero51.moviedatabase.utils.ProgramItemClickListener;
import com.aero51.moviedatabase.utils.Resource;
import com.aero51.moviedatabase.utils.SpeedyLinearLayoutManager;
import com.aero51.moviedatabase.utils.Status;
import com.aero51.moviedatabase.viewmodel.EpgTvViewModel;
import com.aero51.moviedatabase.viewmodel.SharedViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EpgTvFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EpgTvFragment extends Fragment implements ProgramItemClickListener {

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
    private List<EpgChildItem> programsForChannellList;
    private MutableLiveData<Boolean> isLoading;
    private LinearLayoutManager linearLayoutManager;

    private SimpleDateFormat fromUser;
    private SimpleDateFormat myFormat;
    private String currentTimeString;

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

        isLoading = new MutableLiveData<>();
        isLoading.setValue(false);

        fromUser = new SimpleDateFormat("yyyyMMddHHmmSS");
        myFormat = new SimpleDateFormat("HH:mm");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_epg_tv, container, false);

        recycler_view_epg_tv = view.findViewById(R.id.recycler_view_cro_parent);
        //this messes up scroll listener
        //recycler_view_epg_tv.setHasFixedSize(true);
        //recycler_view_epg_tv.setNestedScrollingEnabled(true);
        text_view_fragment_epg_tv = view.findViewById(R.id.tv_fragment_epg_tv_cro);
        registerAllChannelsObserver();
        return view;
    }


    private void registerAllChannelsObserver() {
        sharedViewModel.setHasEpgTvFragmentFinishedLoading(false);
        epgTvViewModel.getChannels().observe(getViewLifecycleOwner(), new Observer<Resource<List<EpgChannel>>>() {
            @Override
            public void onChanged(Resource<List<EpgChannel>> listResource) {
                Log.d("moviedatabaselog", "EpgTvFragment onChanged getChannels code: " + listResource.code + " , status: " + listResource.status + " list size: " + listResource.data.size() + " ,message: " + listResource.message);
                if (listResource.data.size() > 0) {
                    setUpRecyclerView(listResource.data);
                }
            }
        });
    }

    private void setUpRecyclerView(List<EpgChannel> channelList) {

        linearLayoutManager = new SpeedyLinearLayoutManager(getContext(), SpeedyLinearLayoutManager.VERTICAL, false);
        recycler_view_epg_tv.setLayoutManager(linearLayoutManager);
        recycler_view_epg_tv.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                Integer MAX_VELOCITY_Y = 2000;
                if (Math.abs(velocityY) > MAX_VELOCITY_Y) {
                    // Log.d("moviedatabaselog", "velocityX : "+velocityX+" ,velocityY:"+velocityY);
                    velocityY = MAX_VELOCITY_Y * (int) Math.signum((double) velocityY);
                    recycler_view_epg_tv.fling(velocityX, velocityY);
                    return true;
                }

                return false;
            }

        });

        programsForChannellList = new ArrayList<>();
        epgTvAdapter = new EpgTvAdapter(getContext(), channelList, programsForChannellList, this);
        recycler_view_epg_tv.setAdapter(epgTvAdapter);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.d("moviedatabaselog", "EndlessRecyclerViewScrollListener page: " + page + " total items count: " + totalItemsCount);
                fetchProgramsForMultipleChannels(channelList);
            }
        };
        recycler_view_epg_tv.addOnScrollListener(scrollListener);
        fetchProgramsForMultipleChannels(channelList);
        // registerGetProgramsForChannel("HRT1");

    }

    private void fetchProgramsForMultipleChannels(List<EpgChannel> channelList) {
        recycler_view_epg_tv.removeOnScrollListener(scrollListener);
        int temp = epgTvAdapter.getItemCount();
        isLoading.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loading) {
                if (!loading) {
                    int adapterItemCount = epgTvAdapter.getItemCount();
                    if (adapterItemCount < temp + 10) {
                        registerGetProgramsForChannel(channelList.get(adapterItemCount).getName());

                    } else {
                        recycler_view_epg_tv.addOnScrollListener(scrollListener);
                        sharedViewModel.setHasEpgTvFragmentFinishedLoading(true);
                        isLoading.removeObserver(this);
                    }
                }

            }
        });
    }

    private void registerGetProgramsForChannel(String channelName) {
        isLoading.setValue(true);
        epgTvViewModel.getProgramsForChannel(channelName).observe(getViewLifecycleOwner(), new Observer<Resource<List<EpgProgram>>>() {
            @Override
            public void onChanged(Resource<List<EpgProgram>> listResource) {
                if (listResource.data.size() > 0 && listResource.status == Status.SUCCESS) {
                    Log.d("moviedatabaselog", "EpgTvFragment onChanged channelName: " + channelName + " ,get Programs code: " + listResource.code + " , status: " + listResource.status + " list size: " + listResource.data.size() + " ,message: " + listResource.message);
                    epgTvViewModel.getResourceLiveData().removeObserver(this);

                    EpgChildItem item=new EpgChildItem();
                    item.setProgramsList(listResource.data);
                    int nearestTimePosition = NearestTimeHelper.getNearestTime(listResource.data)-1;
                    item.setNearestTimePosition(nearestTimePosition);

                    currentTimeString = new SimpleDateFormat("yyyyMMddHHmmSS ", Locale.getDefault()).format(new Date());

                    Date startDate = null;
                    Date stopDate = null;
                    Date currentDate= null;
                    try {
                        startDate = fromUser.parse(listResource.data.get(nearestTimePosition).getStart());
                        stopDate = fromUser.parse(listResource.data.get(nearestTimePosition).getStop());
                        currentDate=fromUser.parse(currentTimeString);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    double startTime = startDate.getTime();
                    double stopTime = stopDate.getTime();
                    double currentTime=currentDate.getTime();
                    //double percentage = (currentValue - minValue) / (maxValue - minValue);
                    double percentage = (((currentTime - startTime) / (stopTime - startTime))* 100);
                    //  Log.d("moviedatabaselog", "startTime: " + startTime+", stopTime: "+stopTime+" , currentTime: "+currentTime);
                    //  Log.d("moviedatabaselog", "percentage: " + percentage);
                   // Log.d("moviedatabaselog", "child position: " + position );
                    item.setNowPlayingPercentage((int) percentage);

                    programsForChannellList.add(item);
                    epgTvAdapter.notifyItemInserted(programsForChannellList.size() - 1);
                    isLoading.setValue(false);
                }
            }
        });

    }

    @Override
    public void onItemClick(int position, int db_id, EpgProgram epgProgram) {
        sharedViewModel.changeEpgTvFragment(position, epgProgram);
    }

}