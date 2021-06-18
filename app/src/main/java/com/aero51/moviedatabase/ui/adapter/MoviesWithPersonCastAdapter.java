package com.aero51.moviedatabase.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.databinding.MediaWithPersonItemBinding;
import com.aero51.moviedatabase.repository.model.tmdb.credits.MoviesWithPerson;
import com.aero51.moviedatabase.utils.MediaClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.aero51.moviedatabase.utils.Constants.BASE_IMAGE_URL;
import static com.aero51.moviedatabase.utils.Constants.POSTER_SIZE_W154;

public class MoviesWithPersonCastAdapter extends RecyclerView.Adapter<MoviesWithPersonCastAdapter.ViewHolder> {

    private List<MoviesWithPerson.Cast> movieCastList;
    private MediaClickListener mClickListener;

    public MoviesWithPersonCastAdapter(MediaClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // inflates the row layout from xml when needed
    @Override
    public MoviesWithPersonCastAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MoviesWithPersonCastAdapter.ViewHolder(MediaWithPersonItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(MoviesWithPersonCastAdapter.ViewHolder holder, int position) {
        MoviesWithPerson.Cast movieCast = movieCastList.get(position);
        String imageUrl = BASE_IMAGE_URL + POSTER_SIZE_W154 + movieCast.getPoster_path();
        Picasso.get().load(imageUrl).into(holder.binding.imageViewMedia);
        holder.binding.textViewTitle.setText(movieCast.getTitle());
        holder.binding.textViewRole.setText(movieCast.getCharacter());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        if (movieCastList != null) {
            return movieCastList.size();
        } else {
            return 0;
        }

    }

    public void setList(List<MoviesWithPerson.Cast> movieCastList) {
        this.movieCastList = movieCastList;
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
                mClickListener.onMediaItemClick( getItem(adapter_position), adapter_position);
        }
    }

    // convenience method for getting data at click position
    private MoviesWithPerson.Cast getItem(int id) {
        return movieCastList.get(id);
    }


}
