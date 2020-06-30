package com.aero51.moviedatabase.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.R;


public class EpgTvCroChannelsAdapter extends RecyclerView.Adapter<EpgTvCroChannelsAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public EpgTvCroChannelsAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.epg_tv_cro_parent_item, parent, false);
        return new EpgTvCroChannelsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //  val parent = parents[position]
        holder.tv_epg_tv_parent_item.setText("HTV 1");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.child_recycler.getContext(), LinearLayoutManager.HORIZONTAL, false);
        holder.child_recycler.setHasFixedSize(true);
        holder.child_recycler.setLayoutManager(linearLayoutManager);
        holder.child_recycler.setRecycledViewPool(viewPool);
        EpgTvCroChannelsChildAdapter epgTvCroChannelsChildAdapter = new EpgTvCroChannelsChildAdapter();
        holder.child_recycler.setAdapter(epgTvCroChannelsChildAdapter);


    }

    @Override
    public int getItemCount() {
        return 50;
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_epg_tv_parent_item;
        private RecyclerView child_recycler;

        ViewHolder(View itemView) {
            super(itemView);
            tv_epg_tv_parent_item = itemView.findViewById(R.id.tv_epg_tv_parent_item);
            child_recycler = itemView.findViewById(R.id.rv_child);
        }


    }

}
