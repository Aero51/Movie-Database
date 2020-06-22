package com.aero51.moviedatabase.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.credits.ActorImage;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.aero51.moviedatabase.utils.Constants.BASE_IMAGE_URL;
import static com.aero51.moviedatabase.utils.Constants.PROFILE_SIZE_H632;
import static com.aero51.moviedatabase.utils.Constants.PROFILE_SIZE_W185;

public class ActorImagesAdapter extends RecyclerView.Adapter<ActorImagesAdapter.ViewHolder> {

    private List<ActorImage> imagesList;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    public ActorImagesAdapter(Context context, List<ActorImage> imagesList) {
        this.mInflater = LayoutInflater.from(context);
        this.imagesList = imagesList;
    }

    // inflates the row layout from xml when needed
    @Override
    public ActorImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.actor_image_item, parent, false);
        return new ActorImagesAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ActorImagesAdapter.ViewHolder holder, int position) {
        ActorImage image  = imagesList.get(position);
        String imageUrl = BASE_IMAGE_URL + PROFILE_SIZE_H632 + image.getFile_path();
        Picasso.get().load(imageUrl).into(holder.actorImageView);

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return imagesList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView actorImageView;


        ViewHolder(View itemView) {
            super(itemView);
            actorImageView =itemView.findViewById(R.id.actor_image_image_view);
        }


    }

    // convenience method for getting data at click position
    private ActorImage getItem(int id) {
        return imagesList.get(id);
    }


}
