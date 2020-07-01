package com.aero51.moviedatabase.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.epg.EpgProgram;
import com.aero51.moviedatabase.utils.GetCroChannelsLogoResource;
import com.aero51.moviedatabase.utils.Resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class EpgTvCroChannelsAdapter extends RecyclerView.Adapter<EpgTvCroChannelsAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<List<EpgProgram>> sortedList;
    private Context context;
    private int lastPosition = -1;

    public EpgTvCroChannelsAdapter(Context context, Resource<List<EpgProgram>> listResource) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
       // Log.d("moviedatabaselog", "before process");
        sortedList = new ArrayList<>();
        List<EpgProgram> hrt1List = new ArrayList<>();
        List<EpgProgram> hrt2List = new ArrayList<>();
        List<EpgProgram> hrt3List = new ArrayList<>();
        List<EpgProgram> novaTvList = new ArrayList<>();
        List<EpgProgram> rtlTelevizijaList = new ArrayList<>();
        List<EpgProgram> rtl2List = new ArrayList<>();
        List<EpgProgram> domaTvList = new ArrayList<>();
        List<EpgProgram> rtlKockicaList = new ArrayList<>();
        List<EpgProgram> hrt4List = new ArrayList<>();

        List<EpgProgram> programList = listResource.data;
        for (EpgProgram program : programList) {
            if (program.getChannel().equals("HRT1")) {
                hrt1List.add(program);
            }
            if (program.getChannel().equals("HRT2")) hrt2List.add(program);
            if (program.getChannel().equals("HRT3")) hrt3List.add(program);
            if (program.getChannel().equals("NOVATV")) novaTvList.add(program);
            if (program.getChannel().equals("RTLTELEVIZIJA")) rtlTelevizijaList.add(program);
            if (program.getChannel().equals("RTL2")) rtl2List.add(program);
            if (program.getChannel().equals("DOMATV")) domaTvList.add(program);
            if (program.getChannel().equals("RTLKOCKICA")) rtlKockicaList.add(program);
            if (program.getChannel().equals("HRT4")) hrt4List.add(program);
        }

        sortedList.add(hrt1List);
        sortedList.add(hrt2List);
        sortedList.add(hrt3List);
        sortedList.add(novaTvList);
        sortedList.add(rtlTelevizijaList);
        sortedList.add(rtl2List);
        sortedList.add(domaTvList);
        sortedList.add(rtlKockicaList);
        sortedList.add(hrt4List);

      //  Log.d("moviedatabaselog", "after process ");

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.epg_tv_cro_parent_item, parent, false);
        return new EpgTvCroChannelsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setAnimation(holder.itemView, position);

        holder.imageViewTvChannelLogo.setImageDrawable(GetCroChannelsLogoResource.getDrawableForChannel(context, position));
        holder.tv_epg_tv_parent_item.setText(sortedList.get(position).get(0).getChannel());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.child_recycler.getContext(), LinearLayoutManager.HORIZONTAL, false);
        holder.child_recycler.setHasFixedSize(true);
        holder.child_recycler.setLayoutManager(linearLayoutManager);
        holder.child_recycler.setRecycledViewPool(viewPool);
        EpgTvCroChannelsChildAdapter epgTvCroChannelsChildAdapter = new EpgTvCroChannelsChildAdapter(sortedList.get(position));
        holder.child_recycler.setAdapter(epgTvCroChannelsChildAdapter);
        Integer currentProgramIndex = getNearestTime(sortedList.get(position));
        holder.child_recycler.scrollToPosition(currentProgramIndex);

    }

    //needed to override this method in order to make setHasStableIds(true);  work properly
    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewTvChannelLogo;
        private TextView tv_epg_tv_parent_item;
        private RecyclerView child_recycler;

        ViewHolder(View itemView) {
            super(itemView);
            imageViewTvChannelLogo = itemView.findViewById(R.id.image_view_tv_channel_logo);
            tv_epg_tv_parent_item = itemView.findViewById(R.id.tv_epg_tv_parent_item);
            child_recycler = itemView.findViewById(R.id.rv_child);
        }


    }

    //used to calculate position of program which start time is closest to now
    public Integer getNearestTime(List<EpgProgram> list) {
        List<Date> dates = new ArrayList<>();
        for (EpgProgram program : list) {
            try {
                Date programStartTime = new SimpleDateFormat("yyyyMMddHHmmSS").parse(program.getStart());
                dates.add(programStartTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        String currentTime = new SimpleDateFormat("yyyyMMddHHmmSS ", Locale.getDefault()).format(new Date());
        Date currentDate = null;
        try {
            currentDate = new SimpleDateFormat("yyyyMMddHHmmSS").parse(currentTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date nearestDate = null;
        int index = 0;
        long prevDiff = -1;
        long targetTS = currentDate.getTime();
        for (int i = 0; i < dates.size(); i++) {
            Date date = dates.get(i);
            long currDiff = Math.abs(date.getTime() - targetTS);
            if (prevDiff == -1 || currDiff < prevDiff) {
                prevDiff = currDiff;
                nearestDate = date;
                index = i;
            }
        }

        return index;
    }
}
