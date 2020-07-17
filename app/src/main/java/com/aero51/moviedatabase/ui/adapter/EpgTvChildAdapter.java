package com.aero51.moviedatabase.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.epg.EpgProgram;
import com.aero51.moviedatabase.utils.ProgramItemClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;


public class EpgTvChildAdapter extends RecyclerView.Adapter<EpgTvChildAdapter.ViewHolder> {
    private List<EpgProgram> epgPrograms;
    private ProgramItemClickListener mClickListener;

    public EpgTvChildAdapter() {


    }

    public void setList(List<EpgProgram> epgPrograms) {
        this.epgPrograms = epgPrograms;
        notifyDataSetChanged();
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
        SimpleDateFormat fromUser = new SimpleDateFormat("yyyyMMddHHmmSS");
        SimpleDateFormat myFormat = new SimpleDateFormat("HH:mm");
        try {
            String reformattedStr = myFormat.format(fromUser.parse(epgPrograms.get(position).getStart()));
            holder.tv_epg_tv_child_start.setText(reformattedStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.tv_epg_tv_child_position.setText(position+"");
        holder.tv_epg_tv_child_item.setText(epgPrograms.get(position).getTitle());

    }


    @Override
    public int getItemCount() {
        if (epgPrograms == null) {
            return 0;
        } else {
            return epgPrograms.size();
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv_epg_tv_child_position;
        private TextView tv_epg_tv_child_start;
        private TextView tv_epg_tv_child_item;


        ViewHolder(View itemView) {
            super(itemView);
            tv_epg_tv_child_position = itemView.findViewById(R.id.tv_epg_tv_child_position);
            tv_epg_tv_child_start = itemView.findViewById(R.id.tv_epg_tv_child_start);
            tv_epg_tv_child_item = itemView.findViewById(R.id.tv_epg_tv_child_item);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            Integer adapter_position = getAdapterPosition();
            mClickListener.onItemClick(adapter_position, epgPrograms.get(adapter_position).getDb_id(), epgPrograms.get(adapter_position));
        }
    }

    public void setClickListener(ProgramItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

}
