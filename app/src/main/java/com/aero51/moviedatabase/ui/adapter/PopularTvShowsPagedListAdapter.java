package com.aero51.moviedatabase.ui.adapter;

import android.util.Log;
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
import com.aero51.moviedatabase.repository.model.NetworkState;
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.PopularTvShowsPage;
import com.aero51.moviedatabase.utils.Constants;
import com.aero51.moviedatabase.utils.ObjectClickListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import static com.aero51.moviedatabase.utils.Constants.BASE_IMAGE_URL;
import static com.aero51.moviedatabase.utils.Constants.POSTER_SIZE_W154;

public class PopularTvShowsPagedListAdapter  extends PagedListAdapter<PopularTvShowsPage.PopularTvShow, RecyclerView.ViewHolder> {
    private ObjectClickListener itemClickListener;
    private NetworkState networkState;



    public PopularTvShowsPagedListAdapter(ObjectClickListener itemClickListener) {
        super(DIFF_CALLBACK);
        this.itemClickListener = itemClickListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.movie_item, parent, false);
        PopularTvShowHolder viewHolder = new PopularTvShowHolder(view, itemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PopularTvShowsPage.PopularTvShow currentResult = getItem(position);
        ((PopularTvShowHolder) holder).bindTo(currentResult, position);
    }


    private static DiffUtil.ItemCallback<PopularTvShowsPage.PopularTvShow> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<PopularTvShowsPage.PopularTvShow>() {
                @Override
                public boolean areItemsTheSame(PopularTvShowsPage.PopularTvShow oldItem, PopularTvShowsPage.PopularTvShow newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(PopularTvShowsPage.PopularTvShow oldItem, PopularTvShowsPage.PopularTvShow newItem) {
                    return oldItem.getName().equals(newItem.getName());
                }
            };

    public static class PopularTvShowHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private PopularTvShowsPage.PopularTvShow result;
        private int position;
        private ImageView imageView;
        //private TextView textViewPosition;
        private TextView textViewtitle;
        private TextView textViewVoteAverage;
        private ObjectClickListener itemClickListener;

        public PopularTvShowHolder(@NonNull View itemView, ObjectClickListener itemClickListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view_program);
            //textViewPosition = itemView.findViewById(R.id.text_view_position);
            textViewtitle = itemView.findViewById(R.id.text_view_title);
            this.itemClickListener = itemClickListener;
            itemView.setOnClickListener(this);
        }
        public void bindTo(PopularTvShowsPage.PopularTvShow result, int position) {
            this.result = result;
            this.position=position;

            //textViewPosition.setText(String.valueOf(position + 1));
            textViewtitle.setText(result.getName());

            String imageUrl = BASE_IMAGE_URL + POSTER_SIZE_W154 + result.getPoster_path();
            // .placeholder(R.drawable.picture_template)
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
                itemClickListener.onObjectItemClick(result,position); // call the onClick in the OnItemClickListener
            }
        }
    }
}
