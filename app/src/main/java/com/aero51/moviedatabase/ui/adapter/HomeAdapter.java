package com.aero51.moviedatabase.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.Top_Rated_Result;
import com.aero51.moviedatabase.utils.ItemClickListener;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {


    private Context context;


    private TopRatedMoviesPagedListAdapter horizontal_top_rated_adapter;
    private ItemClickListener itemClickListener;


    private RecyclerView.RecycledViewPool recycledViewPool;

    public HomeAdapter(Context context, ItemClickListener itemClickListener) {
        this.context = context;
        this.itemClickListener = itemClickListener;
        recycledViewPool = new RecyclerView.RecycledViewPool();
        horizontal_top_rated_adapter = new TopRatedMoviesPagedListAdapter(itemClickListener);
        Log.d("moviedatabaselog", "HomeAdapter constructor");

    }


    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View theView = LayoutInflater.from(context).inflate(R.layout.row_layout_home, parent, false);
        return new HomeViewHolder(theView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, final int position) {
        holder.textViewCategory.setText("Top rated movies:");
        holder.recyclerViewHorizontal.setAdapter(horizontal_top_rated_adapter);
        holder.recyclerViewHorizontal.setRecycledViewPool(recycledViewPool);
    }

    @Override
    public int getItemCount() {
        return 1;

    }

    public void submitInsideList(PagedList<Top_Rated_Result> top_rated_results_list) {
        horizontal_top_rated_adapter.submitList(top_rated_results_list);
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView recyclerViewHorizontal;
        private TextView textViewCategory;

        private LinearLayoutManager horizontalManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        public HomeViewHolder(View itemView) {
            super(itemView);

            recyclerViewHorizontal = itemView.findViewById(R.id.home_recycler_view_horizontal);
            recyclerViewHorizontal.setHasFixedSize(true);
            recyclerViewHorizontal.setNestedScrollingEnabled(false);
            recyclerViewHorizontal.setLayoutManager(horizontalManager);
            recyclerViewHorizontal.setItemAnimator(new DefaultItemAnimator());

            textViewCategory = itemView.findViewById(R.id.tv_movie_category);

        }
    }
}