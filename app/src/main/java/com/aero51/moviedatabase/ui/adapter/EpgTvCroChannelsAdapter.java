package com.aero51.moviedatabase.ui.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.epg.EpgProgram;
import com.aero51.moviedatabase.utils.GetCroChannelsLogoResource;
import com.aero51.moviedatabase.utils.NearestTimeHelper;
import com.aero51.moviedatabase.utils.ProgramItemClickListener;
import com.aero51.moviedatabase.utils.Resource;

import java.util.ArrayList;

import java.util.List;


public class EpgTvCroChannelsAdapter extends RecyclerView.Adapter<EpgTvCroChannelsAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<List<EpgProgram>> sortedList;
    private Context context;
    private int lastPosition = -1;
    private ProgramItemClickListener mClickListener;

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


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.epg_tv_cro_parent_item, parent, false);

        return new EpgTvCroChannelsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<EpgProgram> listForCurrentChannel=sortedList.get(position);
        holder.text_view_channel_name.setText(listForCurrentChannel.get(0).getChannel());
        holder.epgTvCroChannelsHeaderChildAdapter.setDrawableId(GetCroChannelsLogoResource.getResIdForChannelLogo(position));
        holder.epgTvCroChannelsChildAdapter.setList(listForCurrentChannel);
        Integer currentProgramIndex = NearestTimeHelper.getNearestTime(listForCurrentChannel);
        setAnimation(holder.itemView, position);
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

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {

            //  Animation animation = AnimationUtils.loadAnimation(context,android.R.anim.slide_in_left );
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right);
            // animation.setDuration(2000);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView text_view_channel_name;
        private ConcatAdapter mainAdapter;
        private RecyclerView child_recycler;
        private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        private EpgTvCroChannelsHeaderChildAdapter epgTvCroChannelsHeaderChildAdapter;
        private EpgTvCroChannelsChildAdapter epgTvCroChannelsChildAdapter;

        ViewHolder(View itemView) {
            super(itemView);


            text_view_channel_name = itemView.findViewById(R.id.text_view_channel_name);
            child_recycler = itemView.findViewById(R.id.rv_child);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(child_recycler.getContext(), LinearLayoutManager.HORIZONTAL, false);
            child_recycler.setHasFixedSize(true);
            child_recycler.setLayoutManager(linearLayoutManager);
            child_recycler.setRecycledViewPool(viewPool);
            epgTvCroChannelsHeaderChildAdapter = new EpgTvCroChannelsHeaderChildAdapter();
            epgTvCroChannelsChildAdapter = new EpgTvCroChannelsChildAdapter();
            epgTvCroChannelsChildAdapter.setClickListener(mClickListener);
            mainAdapter = new ConcatAdapter();

            mainAdapter.addAdapter(epgTvCroChannelsHeaderChildAdapter);
            mainAdapter.addAdapter(epgTvCroChannelsChildAdapter);
            child_recycler.setAdapter(mainAdapter);
        }
    }
    // allows clicks events to be caught
    public void setClickListener(ProgramItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


}
