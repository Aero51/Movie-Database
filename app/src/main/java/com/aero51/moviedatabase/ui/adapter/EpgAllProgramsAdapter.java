package com.aero51.moviedatabase.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.databinding.EpgAllProgramsItemBinding;
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
        return new EpgAllProgramsAdapter.EpgAllProgramsViewHolder(EpgAllProgramsItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EpgAllProgramsAdapter.EpgAllProgramsViewHolder holder, int position) {
      holder.binding.tvEpgAllProgramsPosition.setText(String.valueOf(position));
      EpgProgram epgProgram= channelWithPrograms.getProgramsList().get(position);
        try {
            String reformattedStartString = myFormat.format(fromUser.parse(epgProgram.getStart()));
            holder.binding.tvEpgAllProgramsStart.setText(reformattedStartString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.binding.tvEpgAllProgramsTitle.setText(epgProgram.getTitle());
        holder.binding.tvEpgAllProgramsCategory.setText(epgProgram.getCategory());

        holder.binding.allProgramsProgressBar.setProgress(0);
        if (position == channelWithPrograms.getNearestTimePosition()) {
            holder.binding.allProgramsProgressBar.setProgress(channelWithPrograms.getNowPlayingPercentage());
        }


    }

    @Override
    public int getItemCount() {
        return channelWithPrograms.getProgramsList().size();
    }


    public class EpgAllProgramsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        EpgAllProgramsItemBinding binding;

        EpgAllProgramsViewHolder(EpgAllProgramsItemBinding b) {
            super(b.getRoot());
            binding=b;
            binding.getRoot().setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            Integer adapter_position = getBindingAdapterPosition();
            Log.d(Constants.LOG, "EpgAllProgramsAdapter onClick " + adapter_position);


        }
    }


}
