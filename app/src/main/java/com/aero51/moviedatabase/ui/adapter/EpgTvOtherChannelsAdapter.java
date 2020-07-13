package com.aero51.moviedatabase.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.epg.EpgOtherChannel;
import com.aero51.moviedatabase.utils.OtherChannelItemClickListener;
import com.squareup.picasso.Picasso;


import java.util.List;

public class EpgTvOtherChannelsAdapter extends RecyclerView.Adapter<EpgTvOtherChannelsAdapter.ViewHolder>{
    private OtherChannelItemClickListener mClickListener;
private List<EpgOtherChannel> otherChannelList;



    public EpgTvOtherChannelsAdapter(OtherChannelItemClickListener mClickListener, List<EpgOtherChannel> otherChannelList) {
        this.mClickListener = mClickListener;
        this.otherChannelList = otherChannelList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.epg_tv_other_channel_item, parent, false);
        return new EpgTvOtherChannelsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EpgTvOtherChannelsAdapter.ViewHolder holder, int position) {

       holder.text_view_channel_name.setText(otherChannelList.get(position).getChannelDisplayName());
        setImage(holder.image_view_tv_channel_logo,otherChannelList.get(position));

    }


    @Override
    public int getItemCount() {
        if (otherChannelList == null) {
            return 0;
        } else {
            return otherChannelList.size();
        }

    }

    private void setImage(final ImageView imageView,EpgOtherChannel otherChannel) {
        Picasso.get().load(otherChannel.getDrawableInteger()).fit().into(imageView);

    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView text_view_channel_name;
        private ImageView image_view_tv_channel_logo;



        ViewHolder(View itemView) {
            super(itemView);
            text_view_channel_name = itemView.findViewById(R.id.text_view_channel_name);
            image_view_tv_channel_logo = itemView.findViewById(R.id.image_view_tv_channel_logo);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            Integer adapter_position = getBindingAdapterPosition();
            mClickListener.onItemClick(adapter_position,otherChannelList.get(adapter_position));
        }
    }




}
