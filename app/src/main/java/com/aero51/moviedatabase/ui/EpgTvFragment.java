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
import android.widget.TextView;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.epg.EpgChannel;
import com.aero51.moviedatabase.repository.model.epg.EpgProgram;
import com.aero51.moviedatabase.ui.adapter.EpgTvCroChannelsAdapter;
import com.aero51.moviedatabase.utils.ProgramItemClickListener;
import com.aero51.moviedatabase.utils.Resource;
import com.aero51.moviedatabase.viewmodel.EpgTvViewModel;
import com.aero51.moviedatabase.viewmodel.SharedViewModel;

import java.text.SimpleDateFormat;
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
    private RecyclerView recycler_view_epg_tv_cro_channels;
    private TextView tv_fragment_epg_tv;
    private List<EpgProgram> epgProgramList;
    private SharedViewModel sharedViewModel;

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

        recycler_view_epg_tv_cro_channels = view.findViewById(R.id.recycler_view_parent);
        recycler_view_epg_tv_cro_channels.setHasFixedSize(true);
        //  recycler_view_epg_tv_cro_channels.setLayoutManager(new SpeedyLinearLayoutManager(getContext(),SpeedyLinearLayoutManager.VERTICAL,false));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycler_view_epg_tv_cro_channels.setLayoutManager(linearLayoutManager);
        recycler_view_epg_tv_cro_channels.setNestedScrollingEnabled(true);
        tv_fragment_epg_tv = view.findViewById(R.id.tv_fragment_epg_tv);

        registerCroProgramsObserver();

        return view;
    }

private void registerCroProgramsObserver()
{
    epgTvViewModel.getCroPrograms().observe(getViewLifecycleOwner(), new Observer<Resource<List<EpgProgram>>>() {
        @Override
        public void onChanged(Resource<List<EpgProgram>> listResource) {
            Log.d("moviedatabaselog", "EpgTvFragment onChanged getCroPrograms code: " + listResource.code + " , status: " + listResource.status + " list size: " + listResource.data.size() + " ,message: " + listResource.message);
            if (listResource.data.size() > 0) {
                EpgTvCroChannelsAdapter epgTvCroChannelsAdapter = new EpgTvCroChannelsAdapter(getContext(), listResource);
                epgTvCroChannelsAdapter.setClickListener(EpgTvFragment.this::onItemClick);
                //this improved scrolling speed
                epgTvCroChannelsAdapter.setHasStableIds(true);
                recycler_view_epg_tv_cro_channels.setAdapter(epgTvCroChannelsAdapter);
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                tv_fragment_epg_tv.setText(currentTime);
                epgProgramList = listResource.data;
            }
        }
    });

}

private void registerAllChannelsObserver(){
    epgTvViewModel.getChannels().observe(getViewLifecycleOwner(), new Observer<Resource<List<EpgChannel>>>() {
        @Override
        public void onChanged(Resource<List<EpgChannel>> listResource) {
            Log.d("moviedatabaselog", "EpgTvFragment onChanged getChannels code: " + listResource.code + " , status: " + listResource.status + " list size: " + listResource.data.size() + " ,message: " + listResource.message);
        }
    });


}
    @Override
    public void onItemClick(int position, int db_id, EpgProgram epgProgram) {
        sharedViewModel.ChangeEpgTvFragment(position,epgProgram);

    }
}