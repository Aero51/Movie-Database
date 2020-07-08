package com.aero51.moviedatabase.repository.networkonlynotused.test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import static com.aero51.moviedatabase.utils.Constants.BASE_IMAGE_URL;
import static com.aero51.moviedatabase.utils.Constants.POSTER_SIZE_W154;

public class MSimpleAdapter  extends PagedListAdapter<Movie, RecyclerView.ViewHolder> {


    public MSimpleAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        setImage(((SimpleViewHolder) holder).item_simple_picture, getItem(position).getPoster_path());
        setOnClick(((SimpleViewHolder) holder).simple_parent, position);

    }



    private void setImage(final ImageView imageView, String image_URL) {
        String imageUrl = BASE_IMAGE_URL + POSTER_SIZE_W154 + image_URL;
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

    private void setOnClick(RelativeLayout button, final int position) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private static DiffUtil.ItemCallback<Movie> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Movie>() {
                @Override
                public boolean areItemsTheSame(Movie oldItem, Movie newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(Movie oldItem, Movie newItem) {
                    return oldItem.getTitle().equals(newItem.getTitle());
                }
            };
}