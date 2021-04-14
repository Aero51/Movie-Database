package com.aero51.moviedatabase.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;


import com.aero51.moviedatabase.databinding.ActorImageItemBinding;
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorImagesResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.aero51.moviedatabase.utils.Constants.BASE_IMAGE_URL;
import static com.aero51.moviedatabase.utils.Constants.PROFILE_SIZE_H632;

public class ActorImagesAdapter extends RecyclerView.Adapter<ActorImagesAdapter.ViewHolder> {

    private List<ActorImagesResponse.ActorImage> imagesList;


    // data is passed into the constructor
    public ActorImagesAdapter(Context context, List<ActorImagesResponse.ActorImage> imagesList) {
        this.imagesList = imagesList;
    }

    // inflates the row layout from xml when needed
    @Override
    public ActorImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ActorImagesAdapter.ViewHolder(ActorImageItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ActorImagesAdapter.ViewHolder holder, int position) {
        ActorImagesResponse.ActorImage image  = imagesList.get(position);
        String imageUrl = BASE_IMAGE_URL + PROFILE_SIZE_H632 + image.getFile_path();
        Picasso.get().load(imageUrl).into(holder.binding.actorImageImageView);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        ActorImageItemBinding binding;

        ViewHolder(ActorImageItemBinding b) {
            super(b.getRoot());
            binding=b;
        }
    }

    // convenience method for getting data at click position
    private ActorImagesResponse.ActorImage getItem(int id) {
        return imagesList.get(id);
    }


}
