package com.aero51.moviedatabase.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.epg.EpgChannel;
import com.aero51.moviedatabase.repository.model.epg.EpgProgram;
import com.aero51.moviedatabase.utils.NearestTimeHelper;
import com.aero51.moviedatabase.utils.ProgramItemClickListener;

import java.util.List;

public class EpgTvAdapter extends RecyclerView.Adapter<EpgTvAdapter.ViewHolder> {
    private List<EpgChannel> channelList;
    private List<List<EpgProgram>> programsForChannellList;
    private ProgramItemClickListener listener;
    private Context context;
    private RecyclerView.RecycledViewPool viewPool;


    public EpgTvAdapter(Context context, List<EpgChannel> channelList, List<List<EpgProgram>> programsForChannellList, ProgramItemClickListener listener) {
        this.channelList = channelList;
        this.programsForChannellList = programsForChannellList;
        this.listener = listener;
        this.context = context;
        viewPool = new RecyclerView.RecycledViewPool();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.epg_tv_cro_parent_item, parent, false);
        return new EpgTvAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<EpgProgram> currentChannelProgramList = programsForChannellList.get(position);
        holder.text_view_channel_name.setText(channelList.get(position).getDisplay_name());
        holder.child_recycler.setRecycledViewPool(viewPool);
        //  holder.epgTvHeaderChildAdapter.setDrawableId(GetChannelsLogoResource.getResIdForChannelLogo(0));
        // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.child_recycler.getContext(), LinearLayoutManager.HORIZONTAL, false);
        //holder.child_recycler.setLayoutManager(linearLayoutManager);

        //EpgTvChildAdapter epgTvChildAdapter = new EpgTvChildAdapter(currentChannelProgramList, listener);
        //  epgTvChildAdapter.setList(currentChannelProgramList);
        // holder.child_recycler.setAdapter(null);
        //holder.child_recycler.swapAdapter(epgTvChildAdapter, true);
        holder.epgTvChildAdapter.setList(currentChannelProgramList);
        int nearestTimePosition = NearestTimeHelper.getNearestTime(currentChannelProgramList);
        Log.d("moviedatabaselog", "position: " + position + " ,nearestTimePosition: " + nearestTimePosition);

        // linearLayoutManager.scrollToPositionWithOffset(currentChannelProgramList.size()-2,0);
        holder.child_recycler.scrollToPosition(nearestTimePosition);

    }


    @Override
    public int getItemCount() {
        // return channelList.size();
        return programsForChannellList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView text_view_channel_name;
        private ConcatAdapter mainAdapter;
        private RecyclerView child_recycler;

        private EpgTvHeaderChildAdapter epgTvHeaderChildAdapter;
        private EpgTvChildAdapter epgTvChildAdapter;

        ViewHolder(View itemView) {
            super(itemView);


            text_view_channel_name = itemView.findViewById(R.id.text_view_channel_name);
            child_recycler = itemView.findViewById(R.id.rv_child);
            child_recycler.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(child_recycler.getContext(), LinearLayoutManager.HORIZONTAL, false);
            child_recycler.setLayoutManager(linearLayoutManager);
            // child_recycler.setNestedScrollingEnabled(false);
            epgTvHeaderChildAdapter = new EpgTvHeaderChildAdapter();
            epgTvChildAdapter = new EpgTvChildAdapter(listener);

            mainAdapter = new ConcatAdapter();
            //   mainAdapter.addAdapter(epgTvHeaderChildAdapter);
            mainAdapter.addAdapter(epgTvChildAdapter);
            child_recycler.setAdapter(mainAdapter);

            //child_recycler.setAdapter(epgTvChildAdapter);
        }
    }


}
