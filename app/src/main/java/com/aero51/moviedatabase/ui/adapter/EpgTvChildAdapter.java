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
import com.aero51.moviedatabase.repository.model.epg.EpgProgram;
import com.aero51.moviedatabase.utils.ProgramItemClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class EpgTvChildAdapter extends RecyclerView.Adapter<EpgTvChildAdapter.ViewHolder> {
    private List<EpgProgram> epgPrograms;
    private ProgramItemClickListener mClickListener;
    private String currentTimeString;
    private Integer progressPosition;
    private SimpleDateFormat fromUser;
    private SimpleDateFormat myFormat;


    public EpgTvChildAdapter(ProgramItemClickListener listener) {
        this.mClickListener = listener;
        this.epgPrograms = epgPrograms;
        fromUser = new SimpleDateFormat("yyyyMMddHHmmSS");
        myFormat = new SimpleDateFormat("HH:mm");
    }

    public void setList(List<EpgProgram> epgPrograms) {
        this.epgPrograms = epgPrograms;
        notifyDataSetChanged();
    }

    public void setProgress(String currentTimeString, int progressPosition) {
        this.currentTimeString = currentTimeString;
        this.progressPosition = progressPosition;
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

        try {
            String reformattedStartString = myFormat.format(fromUser.parse(epgPrograms.get(position).getStart()));
            holder.tv_epg_tv_child_start.setText(reformattedStartString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.tv_epg_tv_child_position.setText(position + "");
        holder.tv_epg_tv_child_description.setText(epgPrograms.get(position).getTitle());
        holder.progressBar.setProgress(0);
        if (position == progressPosition) {

            Date startDate = null;
            Date stopDate = null;
            Date currentDate=null;
            try {
                startDate = fromUser.parse(epgPrograms.get(position).getStart());
                stopDate = fromUser.parse(epgPrograms.get(position).getStop());
                currentDate=fromUser.parse(currentTimeString);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            double startTime = startDate.getTime();
            double stopTime = stopDate.getTime();
            double currentTime=currentDate.getTime();
            //double percentage = (currentValue - minValue) / (maxValue - minValue);
            double percentage = (((currentTime - startTime) / (stopTime - startTime))* 100);
          //  Log.d("moviedatabaselog", "startTime: " + startTime+", stopTime: "+stopTime+" , currentTime: "+currentTime);
          //  Log.d("moviedatabaselog", "percentage: " + percentage);
            Log.d("moviedatabaselog", "child position: " + position );
            holder.progressBar.setProgress((int) percentage);
        }


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
        private ProgressBar progressBar;
        private TextView tv_epg_tv_child_position;
        private TextView tv_epg_tv_child_start;
        private TextView tv_epg_tv_child_description;


        ViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress_bar);
            tv_epg_tv_child_position = itemView.findViewById(R.id.tv_epg_tv_child_position);
            tv_epg_tv_child_start = itemView.findViewById(R.id.tv_epg_tv_child_start);
            tv_epg_tv_child_description = itemView.findViewById(R.id.tv_epg_tv_child_description);
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
