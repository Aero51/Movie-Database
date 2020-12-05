package com.aero51.moviedatabase.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.epg.ChannelWithPrograms;
import com.aero51.moviedatabase.repository.model.epg.EpgProgram;
import com.aero51.moviedatabase.utils.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EpgAllProgramsAdapter extends RecyclerView.Adapter<EpgAllProgramsAdapter.EpgAllProgramsViewHolder> {

    private ChannelWithPrograms channelWithPrograms;
    private SimpleDateFormat fromUser;
    private SimpleDateFormat myFormat;

    public EpgAllProgramsAdapter(ChannelWithPrograms channelWithPrograms) {
        this.channelWithPrograms = channelWithPrograms;
        fromUser = new SimpleDateFormat("yyyyMMddHHmmSS");
        myFormat = new SimpleDateFormat("HH:mm");
    }

    @NonNull
    @Override
    public EpgAllProgramsAdapter.EpgAllProgramsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.epg_all_programs_item, parent, false);

        return new EpgAllProgramsAdapter.EpgAllProgramsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EpgAllProgramsAdapter.EpgAllProgramsViewHolder holder, int position) {
      holder.tv_epg_all_programs_position.setText(String.valueOf(position));
      EpgProgram epgProgram= channelWithPrograms.getProgramsList().get(position);
        try {
            String reformattedStartString = myFormat.format(fromUser.parse(epgProgram.getStart()));
            holder.tv_epg_all_programs_start.setText(reformattedStartString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.tv_epg_all_programs_title.setText(epgProgram.getTitle());
        holder.tv_epg_all_programs_category.setText(epgProgram.getCategory());

        holder.progressBar.setProgress(0);
        if (position == channelWithPrograms.getNearestTimePosition()) {
            holder.progressBar.setProgress(channelWithPrograms.getNowPlayingPercentage());
        }


    }

    @Override
    public int getItemCount() {
        return channelWithPrograms.getProgramsList().size();
    }


    public class EpgAllProgramsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ProgressBar progressBar;
        public TextView tv_epg_all_programs_position;
        public TextView tv_epg_all_programs_start;
        public TextView tv_epg_all_programs_title;
        public TextView tv_epg_all_programs_category;


        EpgAllProgramsViewHolder(View itemView) {
            super(itemView);
            progressBar=itemView.findViewById(R.id.progress_bar);
            tv_epg_all_programs_position = itemView.findViewById(R.id.tv_epg_all_programs_position);
            tv_epg_all_programs_start = itemView.findViewById(R.id.tv_epg_all_programs_start);
            tv_epg_all_programs_title = itemView.findViewById(R.id.tv_epg_all_programs_title);
            tv_epg_all_programs_category = itemView.findViewById(R.id.tv_epg_all_programs_category);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            Integer adapter_position = getBindingAdapterPosition();
            Log.d(Constants.LOG, "EpgAllProgramsAdapter onClick " + adapter_position);


        }
    }


}
