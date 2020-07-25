package com.aero51.moviedatabase.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMovie;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
private  TopRatedMoviesPagedListAdapter  adapter;


    public void setList(PagedList<TopRatedMovie> topRatedMoviePagedList) {
       adapter.submitList(topRatedMoviePagedList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_item, parent, false);
        MoviesAdapter.ViewHolder viewHolder= new ViewHolder(view);
          adapter = new TopRatedMoviesPagedListAdapter(null);
        viewHolder.setAdapter(adapter);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.adapter.submitList(topRatedMoviePagedList);
    }

    @Override
    public int getItemCount() {
        /*
        if (topRatedMoviePagedList == null) {
            return 0;
        } else {
            return 1;
        }

         */
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private RecyclerView child_recycler;



        private TopRatedMoviesPagedListAdapter adapter;

        public void setAdapter(TopRatedMoviesPagedListAdapter adapter) {
            this.adapter = adapter;
            child_recycler.setAdapter(adapter);
        }


        ViewHolder(View itemView) {
            super(itemView);


            child_recycler = itemView.findViewById(R.id.rv_child);
            child_recycler.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(child_recycler.getContext(), LinearLayoutManager.HORIZONTAL, false);
            child_recycler.setLayoutManager(linearLayoutManager);
            // child_recycler.setNestedScrollingEnabled(true);

        }
    }
}
