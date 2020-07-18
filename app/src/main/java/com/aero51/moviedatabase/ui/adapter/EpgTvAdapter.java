package com.aero51.moviedatabase.ui.adapter;

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
import com.aero51.moviedatabase.utils.ProgramItemClickListener;

import java.util.List;

public class EpgTvAdapter extends RecyclerView.Adapter<EpgTvAdapter.ViewHolder> {
    private List<EpgChannel> channelList;
    private List<List<EpgProgram>> programsForChannellList;
    private ProgramItemClickListener listener;


    public EpgTvAdapter(List<EpgChannel> channelList, List<List<EpgProgram>> programsForChannellList, ProgramItemClickListener listener) {
        this.channelList = channelList;
        this.programsForChannellList=programsForChannellList;
        this.listener=listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.epg_tv_cro_parent_item, parent, false);
        return new EpgTvAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.text_view_channel_name.setText(channelList.get(position).getDisplay_name());
        //  holder.epgTvCroChannelsHeaderChildAdapter.setDrawableId(GetChannelsLogoResource.getResIdForChannelLogo(position));
        holder.epgTvChildAdapter.setList(programsForChannellList.get(position));
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
        private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        private EpgTvHeaderChildAdapter epgTvHeaderChildAdapter;
        private EpgTvChildAdapter epgTvChildAdapter;

        ViewHolder(View itemView) {
            super(itemView);


            text_view_channel_name = itemView.findViewById(R.id.text_view_channel_name);
            child_recycler = itemView.findViewById(R.id.rv_child);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(child_recycler.getContext(), LinearLayoutManager.HORIZONTAL, false);
            child_recycler.setHasFixedSize(true);
            child_recycler.setLayoutManager(linearLayoutManager);
            child_recycler.setRecycledViewPool(viewPool);
            //   epgTvCroChannelsHeaderChildAdapter = new EpgTvCroChannelsHeaderChildAdapter();
            epgTvChildAdapter = new EpgTvChildAdapter(listener);
            //  epgTvCroChannelsChildAdapter.setClickListener(mClickListener);
            mainAdapter = new ConcatAdapter();

            //   mainAdapter.addAdapter(epgTvCroChannelsHeaderChildAdapter);
            mainAdapter.addAdapter(epgTvChildAdapter);
            child_recycler.setAdapter(mainAdapter);
        }
    }


}
