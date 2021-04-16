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
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TrendingTvShowsPage;
import com.aero51.moviedatabase.utils.Constants;
import com.aero51.moviedatabase.utils.ObjectClickListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import static com.aero51.moviedatabase.utils.Constants.BASE_IMAGE_URL;
import static com.aero51.moviedatabase.utils.Constants.POSTER_SIZE_W154;

public class TrendingTvShowsPagedListAdapter  extends PagedListAdapter<TrendingTvShowsPage.TrendingTvShow, RecyclerView.ViewHolder> {
    private ObjectClickListener itemClickListener;
    private NetworkState networkState;


    public TrendingTvShowsPagedListAdapter(ObjectClickListener itemClickListener) {
        super(DIFF_CALLBACK);
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.movie_item, parent, false);
        TrendingTvShowHolder viewHolder = new TrendingTvShowHolder(view, itemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TrendingTvShowsPage.TrendingTvShow currentResult = getItem(position);
        ((TrendingTvShowHolder) holder).bindTo(currentResult, position);
    }
    private static DiffUtil.ItemCallback<TrendingTvShowsPage.TrendingTvShow> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<TrendingTvShowsPage.TrendingTvShow>() {
                @Override
                public boolean areItemsTheSame(TrendingTvShowsPage.TrendingTvShow oldItem, TrendingTvShowsPage.TrendingTvShow newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(TrendingTvShowsPage.TrendingTvShow oldItem, TrendingTvShowsPage.TrendingTvShow newItem) {
                    return oldItem.getName().equals(newItem.getName());
                }
            };
    public static class TrendingTvShowHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TrendingTvShowsPage.TrendingTvShow result;
        private int position;
        private ImageView imageView;
        //private TextView textViewPosition;
        private TextView textViewtitle;
        private TextView textViewVoteAverage;
        private ObjectClickListener itemClickListener;

        public TrendingTvShowHolder(@NonNull View itemView, ObjectClickListener itemClickListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view_program);
            //textViewPosition = itemView.findViewById(R.id.text_view_position);
            textViewtitle = itemView.findViewById(R.id.text_view_title);
            this.itemClickListener = itemClickListener;
            itemView.setOnClickListener(this);
        }
        public void bindTo(TrendingTvShowsPage.TrendingTvShow result, int position) {
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
                Log.d(Constants.LOG, " Item clicked inside trending holder : " + position);
            }
        }
    }
}
