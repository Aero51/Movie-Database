package com.aero51.moviedatabase.ui.viewholder;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMovie;
import com.aero51.moviedatabase.utils.TopRatedItemClickListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import static com.aero51.moviedatabase.utils.Constants.BASE_IMAGE_URL;
import static com.aero51.moviedatabase.utils.Constants.POSTER_SIZE_W154;

public class TopRatedMovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TopRatedMovie result;
    private int position;

    private ImageView imageView;
    private TextView textViewPosition;
    private TextView textViewtitle;
    private TextView textViewVoteAverage;
    private TopRatedItemClickListener itemClickListener;


    public TopRatedMovieHolder(@NonNull View itemView, TopRatedItemClickListener itemClickListener) {
        super(itemView);
        imageView = itemView.findViewById(R.id.image_view);
        textViewPosition = itemView.findViewById(R.id.text_view_position);
        textViewtitle = itemView.findViewById(R.id.text_view_title);

        this.itemClickListener = itemClickListener;
        itemView.setOnClickListener(this);

    }

    public void bindTo(TopRatedMovie result, int position) {
        this.result = result;
        this.position=position;

        textViewPosition.setText(String.valueOf(position + 1));
        textViewtitle.setText(result.getTitle());

        String imageUrl = BASE_IMAGE_URL + POSTER_SIZE_W154 + result.getPoster_path();
        //.placeholder(R.drawable.picture_template)
        Picasso.get().load(imageUrl).fit().centerCrop().into(imageView, new Callback() {
            @Override
            public void onSuccess() {
            }
            @Override
            public void onError(Exception e) {
                imageView.setBackgroundResource(R.drawable.picture_template);
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (itemClickListener != null&& position != RecyclerView.NO_POSITION) {
            itemClickListener.OnItemClick(result,position); // call the onClick in the OnItemClickListener
            Log.d("moviedatabaselog", " Item clicked inside top rated holder : " + position);
        }
    }
}