package com.aero51.moviedatabase.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.epg.EpgProgram;

import java.util.List;

public class EpgTvCroChannelsChildAdapter extends RecyclerView.Adapter<EpgTvCroChannelsChildAdapter.ViewHolder> {
    private List<EpgProgram> epgPrograms;

    public EpgTvCroChannelsChildAdapter(List<EpgProgram> epgPrograms) {
        this.epgPrograms = epgPrograms;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.epg_tv_cro_child_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_epg_tv_child_item.setText(epgPrograms.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return epgPrograms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_epg_tv_child_item;


        ViewHolder(View itemView) {
            super(itemView);
            tv_epg_tv_child_item = itemView.findViewById(R.id.tv_epg_tv_child_item);

        }


    }
}
