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
import com.aero51.moviedatabase.repository.model.epg.EpgChildItem;
import com.aero51.moviedatabase.repository.model.epg.EpgProgram;
import com.aero51.moviedatabase.utils.AsyncTaskEpgTvChildLoader;
import com.aero51.moviedatabase.utils.NearestTimeHelper;
import com.aero51.moviedatabase.utils.ProgramItemClickListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EpgTvAdapter extends RecyclerView.Adapter<EpgTvAdapter.EpgTvViewHolder> {
    private List<EpgChannel> channelList;
    private List<EpgChildItem> programsForChannellList;
    private ProgramItemClickListener listener;
    private Context context;
    private RecyclerView.RecycledViewPool viewPool;
    private String currentTime ;


    public EpgTvAdapter(Context context, List<EpgChannel> channelList, List<EpgChildItem> programsForChannellList, ProgramItemClickListener listener) {
        this.channelList = channelList;
        this.programsForChannellList = programsForChannellList;
        this.listener = listener;
        this.context = context;
        viewPool = new RecyclerView.RecycledViewPool();
        currentTime = new SimpleDateFormat("yyyyMMddHHmmSS ", Locale.getDefault()).format(new Date());
    }

    @NonNull
    @Override
    public EpgTvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.epg_tv_cro_parent_item, parent, false);
        return new EpgTvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EpgTvViewHolder holder, int position) {
        EpgChildItem currentChannelChildItem = programsForChannellList.get(position);
        holder.text_view_channel_name.setText(channelList.get(position).getDisplay_name());
        holder.child_recycler.setRecycledViewPool(viewPool);

        holder.epgTvChildAdapter.setProgress(currentTime,currentChannelChildItem.getNearestTimePosition());
        holder.epgTvChildAdapter.setList(currentChannelChildItem.getProgramsList());
        holder.child_recycler.scrollToPosition(currentChannelChildItem.getNearestTimePosition());

    }


    @Override
    public int getItemCount() {
        // return channelList.size();
        return programsForChannellList.size();
    }


    public class EpgTvViewHolder extends RecyclerView.ViewHolder {

        public TextView text_view_channel_name;
        public ConcatAdapter mainAdapter;
        public RecyclerView child_recycler;

        public EpgTvHeaderChildAdapter epgTvHeaderChildAdapter;
        public EpgTvChildAdapter epgTvChildAdapter;

        EpgTvViewHolder(View itemView) {
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
