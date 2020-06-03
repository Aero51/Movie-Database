package com.aero51.moviedatabase;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TopRatedMoviesAdapter extends RecyclerView.Adapter<TopRatedMoviesAdapter.NoteHolder> {
    private AdapterView.OnItemClickListener listener;
    private List<Top_Rated_Result> mresultsList = new ArrayList<>();


    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {

        Top_Rated_Result currentResult = mresultsList.get(position);

        holder.textViewPosition.setText(String.valueOf(position + 1));
        holder.textViewtitle.setText(currentResult.getTitle());
        holder.textViewVoteAverage.setText(String.valueOf(currentResult.getVote_average()));
        holder.textViewOverview.setText(currentResult.getOverview());


        String baseUrl = "https://image.tmdb.org/t/p/w92";
        String imageUrl = baseUrl + currentResult.getPoster_path();
        //    Log.d("moviesadapter", "imageUrl: " + imageUrl);
        Picasso.get().load(imageUrl).into(holder.imageView);
/*
        poster
        https://image.tmdb.org/t/p/w92/5KCVkau1HEl7ZzfPsKAPM0sMiKc.jpg

        backdrop
        w300
        /avedvodAZUcwqevBfm8p4G2NziQ.jpg
        https://image.tmdb.org/t/p/w300/avedvodAZUcwqevBfm8p4G2NziQ.jpg
 */
    }

    @Override
    public int getItemCount() {
        //  return  mresultsList == null ? 0 : mresultsList.size();
        if (mresultsList == null)
            return 0;
        else
            return mresultsList.size();

    }

    public void setResults(List<Top_Rated_Result> results) {
        //this.mresultsList = results;
        int cached_size = mresultsList.size();
        mresultsList.addAll(results);
        if (cached_size == 0) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeInserted(cached_size,results.size());
        }

    }


    class NoteHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textViewPosition;
        private TextView textViewtitle;
        private TextView textViewVoteAverage;
        private TextView textViewOverview;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            textViewPosition = itemView.findViewById(R.id.text_view_position);
            textViewtitle = itemView.findViewById(R.id.text_view_title);
            textViewVoteAverage = itemView.findViewById(R.id.text_view_vote_average);
            textViewOverview = itemView.findViewById(R.id.text_view_overview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        // listener.onItemClick(mresultsList.get(position));


                    }
                }
            });
        }
    }
}
