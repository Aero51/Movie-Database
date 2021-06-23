package com.aero51.moviedatabase.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.databinding.EpgChildItemBinding;
import com.aero51.moviedatabase.repository.model.epg.ChannelWithPrograms;
import com.aero51.moviedatabase.repository.model.epg.EpgProgram;
import com.aero51.moviedatabase.ui.listeners.ProgramItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class EpgChildAdapter extends RecyclerView.Adapter<EpgChildAdapter.ViewHolder> {
    private ChannelWithPrograms currentChannelChildItem;
    private ProgramItemClickListener mClickListener;
    private SimpleDateFormat fromUser;
    private SimpleDateFormat myFormat;


    public EpgChildAdapter(ProgramItemClickListener listener) {
        this.mClickListener = listener;

        fromUser = new SimpleDateFormat("yyyyMMddHHmmSS");
        myFormat = new SimpleDateFormat("HH:mm");
    }

    public void setList(ChannelWithPrograms currentChannelChildItem) {
        this.currentChannelChildItem = currentChannelChildItem;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(EpgChildItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EpgProgram program = currentChannelChildItem.getProgramsList().get(position);
        try {
            String reformattedStartString = myFormat.format(fromUser.parse(program.getStart()));
            holder.binding.tvEpgTvChildStart.setText(reformattedStartString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.binding.tvEpgTvChildTitle.setText(extractJsonTitles(program.getTitle()).get(0));
        //holder.binding.tvEpgTvChildCategory.setText(program.getCategory());
        holder.binding.childItemProgressBar.setProgress(0);
        if (position == currentChannelChildItem.getNearestTimePosition()) {
            holder.binding.childItemProgressBar.setProgress(currentChannelChildItem.getNowPlayingPercentage());
        }
    }

    @Override
    public int getItemCount() {
        if (currentChannelChildItem == null) {
            return 0;
        } else {
            return currentChannelChildItem.getProgramsList().size();
        }

    }

    private List<String> extractJsonTitles(String titles) {

        List<String> titlesList = new ArrayList<>();
        if (titles != null) {
            try {
                JSONObject jsonObjCredits = new JSONObject(titles);
                JSONArray ja_titles = jsonObjCredits.getJSONArray("Titles");

                for (int i = 0; i < ja_titles.length(); i++) {
                    titlesList.add(ja_titles.getString(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return titlesList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        EpgChildItemBinding binding;

        ViewHolder(EpgChildItemBinding b) {
            super(b.getRoot());
            binding = b;

            //relativeLayout.setMinimumWidth(100);
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Integer adapter_position = getBindingAdapterPosition();
            mClickListener.onItemClick(adapter_position, currentChannelChildItem.getProgramsList().get(adapter_position).getDb_id(), currentChannelChildItem.getProgramsList().get(adapter_position));

        }
    }
}
