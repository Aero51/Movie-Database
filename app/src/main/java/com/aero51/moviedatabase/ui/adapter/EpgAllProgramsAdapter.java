package com.aero51.moviedatabase.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.epg.ChannelWithPrograms;

public class EpgAllProgramsAdapter extends RecyclerView.Adapter<EpgAllProgramsAdapter.EpgAllProgramsViewHolder> {

    private ChannelWithPrograms channelWithPrograms;

    public EpgAllProgramsAdapter(ChannelWithPrograms channelWithPrograms) {
        this.channelWithPrograms = channelWithPrograms;
    }

    @NonNull
    @Override
    public EpgAllProgramsAdapter.EpgAllProgramsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.epg_all_programs_item, parent, false);

        return new EpgAllProgramsAdapter.EpgAllProgramsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EpgAllProgramsAdapter.EpgAllProgramsViewHolder holder, int position) {
        holder.tv_epg_all_programs_start.setText(channelWithPrograms.getProgramsList().get(position).getStart());
        holder.tv_epg_all_programs_description.setText(channelWithPrograms.getProgramsList().get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return channelWithPrograms.getProgramsList().size();
    }


    public class EpgAllProgramsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tv_epg_all_programs_start;
        public TextView tv_epg_all_programs_description;


        EpgAllProgramsViewHolder(View itemView) {
            super(itemView);
            tv_epg_all_programs_start = itemView.findViewById(R.id.tv_epg_all_programs_start);
            tv_epg_all_programs_description = itemView.findViewById(R.id.tv_epg_all_programs_description);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            Integer adapter_position = getBindingAdapterPosition();
            Log.d("moviedatabaselog", "EpgAllProgramsAdapter onClick " + adapter_position);


        }
    }


}
