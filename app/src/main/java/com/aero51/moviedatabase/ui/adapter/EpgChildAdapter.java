package com.aero51.moviedatabase.ui.adapter;

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
import com.aero51.moviedatabase.utils.ProgramItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;


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
        parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.epg_cro_child_item, parent, false);
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
        holder.tv_epg_tv_child_position.setText(position + "");
        holder.tv_epg_tv_child_description.setText(program.getTitle());

        String category = "";
        try {
            JSONObject jsonObj = new JSONObject(program.getCategory());
            JSONArray ja_data = jsonObj.getJSONArray("Category");
            int length = jsonObj.length();
            for (int i = 0; i < length; i++) {
                category = category + " " + ja_data.getString(i);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        holder.tv_epg_tv_child_category.setText(category);

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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ProgressBar progressBar;
        private TextView tv_epg_tv_child_position;
        private TextView tv_epg_tv_child_start;
        private TextView tv_epg_tv_child_description;
        private TextView tv_epg_tv_child_category;


        ViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress_bar);
            tv_epg_tv_child_position = itemView.findViewById(R.id.tv_epg_tv_child_position);
            tv_epg_tv_child_start = itemView.findViewById(R.id.tv_epg_tv_child_start);
            tv_epg_tv_child_description = itemView.findViewById(R.id.tv_epg_tv_child_description);
            tv_epg_tv_child_category = itemView.findViewById(R.id.tv_epg_tv_child_category);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            Integer adapter_position = getBindingAdapterPosition();
            mClickListener.onItemClick(adapter_position, currentChannelChildItem.getProgramsList().get(adapter_position).getDb_id(), currentChannelChildItem.getProgramsList().get(adapter_position));
        }
    }

}
