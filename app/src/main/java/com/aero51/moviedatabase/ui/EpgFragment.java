package com.aero51.moviedatabase.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.databinding.FragmentEpgBinding;
import com.aero51.moviedatabase.repository.model.epg.EpgChannel;
import com.aero51.moviedatabase.repository.model.epg.ChannelWithPrograms;
import com.aero51.moviedatabase.repository.model.epg.EpgProgram;
import com.aero51.moviedatabase.ui.adapter.EpgAdapter;
import com.aero51.moviedatabase.utils.ChannelItemClickListener;
import com.aero51.moviedatabase.utils.ChannelsPreferenceHelper;
import com.aero51.moviedatabase.utils.Constants;
import com.aero51.moviedatabase.utils.EndlessRecyclerViewScrollListener;
import com.aero51.moviedatabase.utils.ProgramItemClickListener;
import com.aero51.moviedatabase.utils.Resource;
import com.aero51.moviedatabase.utils.SpeedyLinearLayoutManager;
import com.aero51.moviedatabase.utils.Status;
import com.aero51.moviedatabase.viewmodel.EpgViewModel;
import com.aero51.moviedatabase.viewmodel.SharedViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


public class EpgFragment extends Fragment implements ProgramItemClickListener, ChannelItemClickListener {
    private FragmentEpgBinding binding;

    private EpgViewModel epgViewModel;
    private EpgAdapter epgAdapter;

    private SharedViewModel sharedViewModel;
    private List<ChannelWithPrograms> programsForChannellList;
    private MutableLiveData<Boolean> isLoading;
    private LinearLayoutManager linearLayoutManager;

    private List<EpgChannel> channelList;

    private EndlessRecyclerViewScrollListener scrollListener;

    public EpgFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        channelList = ChannelsPreferenceHelper.extractChannels();
        epgViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(EpgViewModel.class);
        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEpgBinding.inflate(inflater, container, false);
        binding.pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setUpRecyclerView();
                //refreshData(); // your code
                binding.pullToRefresh.setRefreshing(false);
            }
        });

        binding.epgProgressBar.setVisibility(View.GONE);
        //this messes up scroll listener
        //recycler_view_epg_tv.setHasFixedSize(true);
        //recycler_view_epg_tv.setNestedScrollingEnabled(true);

        setUpRecyclerView();
        //sharedViewModel.setHasEpgTvFragmentFinishedLoading(true);
        showBackButton(false);
        //Log.d(Constants.LOG2, "EpgTvFragment before: " );

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }

    private void showBackButton(boolean show) {
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(show);
        }
    }


    private void setUpRecyclerView() {

        linearLayoutManager = new SpeedyLinearLayoutManager(getContext(), SpeedyLinearLayoutManager.VERTICAL, false);
        binding.recyclerViewEpgParent.setLayoutManager(linearLayoutManager);

        programsForChannellList = new ArrayList<>();
        epgAdapter = new EpgAdapter(getContext(), channelList, programsForChannellList, this, this);
        binding.recyclerViewEpgParent.setAdapter(epgAdapter);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.d(Constants.LOG, "EndlessRecyclerViewScrollListener page: " + page + " total items count: " + totalItemsCount);
                fetchProgramsForMultipleChannels();
            }
        };
        binding.recyclerViewEpgParent.addOnScrollListener(scrollListener);
        fetchProgramsForMultipleChannels();

    }

    private void fetchProgramsForMultipleChannels() {
        binding.epgProgressBar.setVisibility(View.VISIBLE);
        isLoading = new MutableLiveData<>();
        isLoading.setValue(false);
        sharedViewModel.setHasEpgTvFragmentFinishedLoading(false);

        binding.recyclerViewEpgParent.removeOnScrollListener(scrollListener);
        int temp = epgAdapter.getItemCount();
        isLoading.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loading) {
                //for one at a time network calls
                if (!loading) {
                    int adapterItemCount = epgAdapter.getItemCount();
                    if (adapterItemCount < temp + 5 && channelList.size() > adapterItemCount) {
                        registerGetProgramsForChannel(channelList.get(adapterItemCount).getName());

                    } else {
                        binding.recyclerViewEpgParent.addOnScrollListener(scrollListener);
                        //for notifying movie fragment that this one has finished loading
                        sharedViewModel.setHasEpgTvFragmentFinishedLoading(true);
                        isLoading.removeObserver(this);
                        binding.epgProgressBar.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    private void registerGetProgramsForChannel(String channelName) {
        isLoading.setValue(true);
        epgViewModel.getProgramsForChannel(channelName).observe(getViewLifecycleOwner(), new Observer<Resource<List<EpgProgram>>>() {
            @Override
            public void onChanged(Resource<List<EpgProgram>> listResource) {
                Log.d(Constants.LOG, "EpgTvFragment onChanged channelName: " + channelName + " ,status: " + listResource.getStatus() + " ,message: " + listResource.getMessage());
                if (listResource.getStatus() == Status.LOADING) {
                    if (!isNetworkAvailable()) {
                        showSnackbar(getResources().getString(R.string.no_internet_message), Snackbar.LENGTH_INDEFINITE);
                    }
                } else if (listResource.getData().size() > 0 && listResource.getStatus() == Status.SUCCESS) {
                    binding.epgProgressBar.setVisibility(View.GONE);
                    epgViewModel.getResourceLiveData().removeObserver(this);
                    ChannelWithPrograms item = epgViewModel.calculateTimeStuff(listResource.getData());
                    programsForChannellList.add(item);
                    epgAdapter.notifyItemInserted(programsForChannellList.size() - 1);
                    isLoading.setValue(false);

                } else if (listResource.getStatus() == Status.ERROR) {
                    epgViewModel.getResourceLiveData().removeObserver(this);
                    //happens when epg server is restarting
                    if (listResource.getMessage().equals("timeout")) {
                        showSnackbar(getResources().getString(R.string.server_timeout_message), Snackbar.LENGTH_LONG);
                    }
                }
            }
        });
    }

    @Override
    public void onItemClick(int position, int db_id, EpgProgram epgProgram) {
        //intentional crash
        // Toast.makeText(null, "Crashed before shown.", Toast.LENGTH_SHORT).show();
        sharedViewModel.changeToEpgDetailsFragment(position, epgProgram);

    }

    @Override
    public void onItemClick(ChannelWithPrograms channelWithPrograms) {
        Log.d(Constants.LOG, "channel item clicked: " + channelWithPrograms.getChannel().getName());
        sharedViewModel.changeToEpgAllProgramsFragment(channelWithPrograms);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private void showSnackbar(String message, int snackbarLength) {
        View mainActivityView = getActivity().findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(mainActivityView, message, snackbarLength);
        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setUpRecyclerView();
                fetchProgramsForMultipleChannels();
                snackbar.dismiss();
            }
        });
        snackbar.show();

    }

}