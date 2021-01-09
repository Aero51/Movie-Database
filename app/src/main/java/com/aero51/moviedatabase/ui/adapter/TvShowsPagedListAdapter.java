package com.aero51.moviedatabase.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShow;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


import static com.aero51.moviedatabase.utils.Constants.BASE_IMAGE_URL;
import static com.aero51.moviedatabase.utils.Constants.POSTER_SIZE_W154;

public class TvShowsPagedListAdapter extends PagedListAdapter<TvShow, TvShowsPagedListAdapter.ViewHolder> {



    public TvShowsPagedListAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public TvShowsPagedListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.movie_item, parent, false);
        return new TvShowsPagedListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowsPagedListAdapter.ViewHolder holder, int position) {
        TvShow currentTvShow = getItem(position);
        holder.textViewPosition.setText(String.valueOf(position + 1));
        holder.textViewtitle.setText(currentTvShow.getName());

        String imageUrl = BASE_IMAGE_URL + POSTER_SIZE_W154 + currentTvShow.getPoster_path();
        //.placeholder(R.drawable.picture_template)
        Picasso.get().load(imageUrl).fit().centerCrop().into(holder.imageView, new Callback() {
            @Override
            public void onSuccess() {
            }
            @Override
            public void onError(Exception e) {
                holder.imageView.setBackgroundResource(R.drawable.picture_template);
            }
        });

    }
    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private TextView textViewPosition;
        private TextView textViewtitle;


        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view_program);
            textViewPosition = itemView.findViewById(R.id.text_view_position);
            textViewtitle = itemView.findViewById(R.id.text_view_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Integer adapter_position = getBindingAdapterPosition();

        }
    }
    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick( int position);
    }




    private static DiffUtil.ItemCallback<TvShow> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<TvShow>() {
                @Override
                public boolean areItemsTheSame(TvShow oldItem, TvShow newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(TvShow oldItem, TvShow newItem) {
                    return oldItem.getName().equals(newItem.getName());
                }
            };
}
