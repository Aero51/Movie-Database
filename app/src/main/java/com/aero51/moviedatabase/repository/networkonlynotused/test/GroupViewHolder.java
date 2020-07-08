package com.aero51.moviedatabase.repository.networkonlynotused.test;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.R;

public class GroupViewHolder extends RecyclerView.ViewHolder {
    public RelativeLayout head_parent;
    public TextView group_title;
    public Button view_all;
    public RecyclerView group_recycler_view;
    public MSimpleAdapter simpleAdapter;

    public GroupViewHolder(@NonNull View itemView) {
        super(itemView);
        head_parent = itemView.findViewById(R.id.head_parent);
        group_title = itemView.findViewById(R.id.group_title);
        view_all = itemView.findViewById(R.id.view_all);
        group_recycler_view = itemView.findViewById(R.id.group_recycler_view);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(group_recycler_view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        group_recycler_view.setHasFixedSize(true);
        group_recycler_view.setLayoutManager(linearLayoutManager);
        simpleAdapter = new MSimpleAdapter();
        group_recycler_view.setAdapter(simpleAdapter);
        group_recycler_view.setNestedScrollingEnabled(true);

    }
}