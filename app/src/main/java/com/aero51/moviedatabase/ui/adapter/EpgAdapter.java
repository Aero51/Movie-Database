package com.aero51.moviedatabase.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.BuildConfig;
import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.epg.EpgChannel;
import com.aero51.moviedatabase.repository.model.epg.ChannelWithPrograms;
import com.aero51.moviedatabase.utils.ChannelItemClickListener;
import com.aero51.moviedatabase.utils.ProgramItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EpgAdapter extends RecyclerView.Adapter<EpgAdapter.EpgTvViewHolder> {
    private List<EpgChannel> channelList;
    private List<ChannelWithPrograms> programsForChannellList;
    private ProgramItemClickListener programItemClickListener;
    private ChannelItemClickListener channelItemClickListener;
    private Context context;
    private RecyclerView.RecycledViewPool viewPool;

    public EpgAdapter(Context context, List<EpgChannel> channelList, List<ChannelWithPrograms> programsForChannellList, ProgramItemClickListener programItemClickListener, ChannelItemClickListener channelItemClickListener) {
        this.channelList = channelList;
        this.programsForChannellList = programsForChannellList;
        this.programItemClickListener = programItemClickListener;
        this.channelItemClickListener = channelItemClickListener;
        this.context = context;
        viewPool = new RecyclerView.RecycledViewPool();

    }

    @NonNull
    @Override
    public EpgTvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.epg_parent_item, parent, false);
        return new EpgTvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EpgTvViewHolder holder, int position) {
        ChannelWithPrograms currentChannelChildItem = programsForChannellList.get(position);
        Uri picture_path = Uri.parse("android.resource://" + BuildConfig.APPLICATION_ID + "/drawable/" + channelList.get(position).getName());
        Picasso.get().load(picture_path).placeholder(R.drawable.picture_template).into(holder.image_view_channel);

        holder.text_view_channel_name.setText(channelList.get(position).getDisplay_name());
        holder.child_recycler.setRecycledViewPool(viewPool);

        holder.epgChildAdapter.setList(currentChannelChildItem);
        holder.child_recycler.scrollToPosition(currentChannelChildItem.getNearestTimePosition());
    }

    @Override
    public int getItemCount() {
        return programsForChannellList.size();
    }


    public class EpgTvViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RelativeLayout channel_relative_layout;
        public ImageView image_view_channel;
        public TextView text_view_channel_name;

        public ConcatAdapter mainAdapter;
        public RecyclerView child_recycler;

        public EpgHeaderChildAdapter epgHeaderChildAdapter;
        public EpgChildAdapter epgChildAdapter;

        EpgTvViewHolder(View itemView) {
            super(itemView);

            channel_relative_layout = itemView.findViewById(R.id.channel_relative_layout);
            image_view_channel = itemView.findViewById(R.id.image_view_channel);
            text_view_channel_name = itemView.findViewById(R.id.text_view_channel_name);
            channel_relative_layout.setOnClickListener(this);

            child_recycler = itemView.findViewById(R.id.rv_child);
            child_recycler.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(child_recycler.getContext(), LinearLayoutManager.HORIZONTAL, false);
            child_recycler.setLayoutManager(linearLayoutManager);
            child_recycler.addItemDecoration(new DividerItemDecoration(child_recycler.getContext(), linearLayoutManager.getOrientation()));
           // child_recycler.addItemDecoration(new DividerItemDecoration(child_recycler.getContext(), DividerItemDecoration.HORIZONTAL));

            child_recycler.setNestedScrollingEnabled(false);
            epgHeaderChildAdapter = new EpgHeaderChildAdapter();
            epgChildAdapter = new EpgChildAdapter(programItemClickListener);

            mainAdapter = new ConcatAdapter();
            //mainAdapter.addAdapter(epgTvHeaderChildAdapter);
            mainAdapter.addAdapter(epgChildAdapter);
            child_recycler.setAdapter(mainAdapter);
            //child_recycler.setAdapter(epgTvChildAdapter);
        }

        @Override
        public void onClick(View v) {
            Integer adapter_position = getBindingAdapterPosition();
            ChannelWithPrograms channelWithPrograms = programsForChannellList.get(adapter_position);
            channelWithPrograms.setChannel(channelList.get(adapter_position));
            channelItemClickListener.onItemClick(channelWithPrograms);
        }
    }
}
