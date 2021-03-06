package com.aero51.moviedatabase.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.aero51.moviedatabase.databinding.CastItemBinding;
import com.aero51.moviedatabase.repository.model.tmdb.credits.MovieCredits;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.aero51.moviedatabase.utils.Constants.BASE_IMAGE_URL;
import static com.aero51.moviedatabase.utils.Constants.PROFILE_SIZE_W185;

public class MovieCastAdapter extends RecyclerView.Adapter<MovieCastAdapter.ViewHolder> {

    private List<MovieCredits.MovieCast> movieCastList;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public MovieCastAdapter( List<MovieCredits.MovieCast> movieCastList) {
        this.movieCastList = movieCastList;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(CastItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MovieCredits.MovieCast movieCast = movieCastList.get(position);
        String imageUrl = BASE_IMAGE_URL + PROFILE_SIZE_W185 + movieCast.getProfile_path();
        Picasso.get().load(imageUrl).into(holder.binding.castProfileImageView);
        holder.binding.textViewCastName.setText(movieCast.getCharacter());
        holder.binding.textViewRealName.setText(movieCast.getName());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return movieCastList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CastItemBinding binding;

        ViewHolder(CastItemBinding b) {
            super(b.getRoot());
            binding=b;
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Integer adapter_position = getBindingAdapterPosition();
            if (mClickListener != null) mClickListener.onItemClick(view,getItem(adapter_position).getId(), adapter_position);
        }
    }

    // convenience method for getting data at click position
   private MovieCredits.MovieCast getItem(int id) {
        return movieCastList.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view,Integer actorId, int position);
    }
}