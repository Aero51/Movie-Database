package com.aero51.moviedatabase.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.epg.ChannelWithPrograms;
import com.aero51.moviedatabase.repository.model.epg.EpgProgram;
import com.aero51.moviedatabase.utils.ProgramItemClickListener;

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
        //parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.epg_child_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EpgProgram program = currentChannelChildItem.getProgramsList().get(position);
        try {
            String reformattedStartString = myFormat.format(fromUser.parse(program.getStart()));
            holder.tv_epg_tv_child_start.setText(reformattedStartString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.tv_epg_tv_child_title.setText(extractJsonTitles(program.getTitle()).get(0));
        holder.tv_epg_tv_child_category.setText(program.getCategory());
        holder.progressBar.setProgress(0);
        if (position == currentChannelChildItem.getNearestTimePosition()) {
            holder.progressBar.setProgress(currentChannelChildItem.getNowPlayingPercentage());
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
        private RelativeLayout relativeLayout;
        private ProgressBar progressBar;
        private TextView tv_epg_tv_child_start;
        private TextView tv_epg_tv_child_title;
        private TextView tv_epg_tv_child_category;


        ViewHolder(View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.relative_layout);
            //relativeLayout.setMinimumWidth(100);
            progressBar = itemView.findViewById(R.id.child_item_progress_bar);
            tv_epg_tv_child_start = itemView.findViewById(R.id.tv_epg_tv_child_start);
            tv_epg_tv_child_title = itemView.findViewById(R.id.tv_epg_tv_child_title);
            tv_epg_tv_child_category = itemView.findViewById(R.id.tv_epg_tv_child_category);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Integer adapter_position = getBindingAdapterPosition();
            mClickListener.onItemClick(adapter_position, currentChannelChildItem.getProgramsList().get(adapter_position).getDb_id(), currentChannelChildItem.getProgramsList().get(adapter_position));
            //Log.d(Constants.LOG, "tv_epg_tv_child_title getWidth: " + tv_epg_tv_child_title.getWidth());

        }
    }
}
