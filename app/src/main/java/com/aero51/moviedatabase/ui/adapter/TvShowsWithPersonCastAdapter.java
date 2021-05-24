package com.aero51.moviedatabase.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.databinding.MediaWithPersonItemBinding;
import com.aero51.moviedatabase.repository.model.tmdb.credits.TvShowsWithPerson;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.aero51.moviedatabase.utils.Constants.BASE_IMAGE_URL;
import static com.aero51.moviedatabase.utils.Constants.POSTER_SIZE_W154;

public class TvShowsWithPersonCastAdapter extends RecyclerView.Adapter<TvShowsWithPersonCastAdapter.ViewHolder> {

    private List<TvShowsWithPerson.Cast> tvShowCastList;
    private MoviesWithPersonCastAdapter.ItemClickListener mClickListener;

    public TvShowsWithPersonCastAdapter() {
    }

    // inflates the row layout from xml when needed
    @Override
    public TvShowsWithPersonCastAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TvShowsWithPersonCastAdapter.ViewHolder(MediaWithPersonItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(TvShowsWithPersonCastAdapter.ViewHolder holder, int position) {
        TvShowsWithPerson.Cast tvCast = tvShowCastList.get(position);
        String imageUrl = BASE_IMAGE_URL + POSTER_SIZE_W154 + tvCast.getPoster_path();
        Picasso.get().load(imageUrl).into(holder.binding.imageViewMedia);
        holder.binding.textViewTitle.setText(tvCast.getName());
        holder.binding.textViewRole.setText(tvCast.getCharacter());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        if (tvShowCastList != null) {
            return tvShowCastList.size();
        } else {
            return 0;
        }

    }

    public void setList(List<TvShowsWithPerson.Cast> tvCastList) {
        this.tvShowCastList = tvCastList;
        notifyDataSetChanged();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MediaWithPersonItemBinding binding;

        ViewHolder(MediaWithPersonItemBinding b) {
            super(b.getRoot());
            binding = b;
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Integer adapter_position = getBindingAdapterPosition();
            if (mClickListener != null)
                mClickListener.onItemClick(view, getItem(adapter_position).getId(), adapter_position);
        }
    }

    // convenience method for getting data at click position
    private TvShowsWithPerson.Cast getItem(int id) {
        return tvShowCastList.get(id);
    }

    // allows clicks events to be caught
    //TODO implement click listener
    public void setClickListener(MoviesWithPersonCastAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, Integer actorId, int position);
    }
}