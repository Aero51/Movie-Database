package com.aero51.moviedatabase.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.databinding.EpgAllProgramsItemBinding;
import com.aero51.moviedatabase.repository.model.epg.ChannelWithPrograms;
import com.aero51.moviedatabase.repository.model.epg.EpgProgram;
import com.aero51.moviedatabase.utils.Constants;
import com.aero51.moviedatabase.utils.ProgramItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class EpgAllProgramsAdapter extends RecyclerView.Adapter<EpgAllProgramsAdapter.EpgAllProgramsViewHolder> {

    private ChannelWithPrograms channelWithPrograms;
    private SimpleDateFormat fromUser;
    private SimpleDateFormat myFormat;
    private ProgramItemClickListener mClickListener;

    public EpgAllProgramsAdapter(ChannelWithPrograms channelWithPrograms,  ProgramItemClickListener clickListener) {
        this.channelWithPrograms = channelWithPrograms;
        this.mClickListener = clickListener;
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
      //holder.binding.tvEpgAllProgramsPosition.setText(String.valueOf(position));
      EpgProgram epgProgram= channelWithPrograms.getProgramsList().get(position);
        try {
            String reformattedStartString = myFormat.format(fromUser.parse(epgProgram.getStart()));
            holder.binding.tvEpgAllProgramsStart.setText(reformattedStartString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<String> titles=extractJsonTitles(epgProgram.getTitle());
        holder.binding.tvEpgAllProgramsTitlePrimary.setText(titles.get(0));
        if(titles.size()>1){
            holder.binding.tvEpgAllProgramsTitleSecondary.setText(titles.get(1));
        }else{
            holder.binding.tvEpgAllProgramsTitleSecondary.setText("");
        }

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

    private List<String> extractJsonTitles(String titles) {

        List<String> titlesList = new ArrayList<>();
        if (titles != null) {
            try {
                JSONObject jsonObjTitles = new JSONObject(titles);
                JSONArray ja_titles = jsonObjTitles.getJSONArray("Titles");

                for (int i = 0; i < ja_titles.length(); i++) {
                    titlesList.add(ja_titles.getString(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return titlesList;
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
            mClickListener.onItemClick(adapter_position,channelWithPrograms.getProgramsList().get(adapter_position).getDb_id(),channelWithPrograms.getProgramsList().get(adapter_position));

        }
    }


}
