package com.aero51.moviedatabase.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.databinding.ActorSearchItemBinding;
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorSearchResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.aero51.moviedatabase.utils.Constants.BASE_IMAGE_URL;
import static com.aero51.moviedatabase.utils.Constants.PROFILE_SIZE_W185;

public class ActorSearchAdapter extends RecyclerView.Adapter<ActorSearchAdapter.ViewHolder> {

    private List<ActorSearchResponse.ActorSearch> actorSearchList;
    private ItemClickListener mClickListener;


    public ActorSearchAdapter(List<ActorSearchResponse.ActorSearch> actorSearchList, ItemClickListener clickListener) {
        this.actorSearchList = actorSearchList;
        this.mClickListener = clickListener;
    }

    @NonNull
    @Override
    public ActorSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ActorSearchAdapter.ViewHolder(ActorSearchItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ActorSearchAdapter.ViewHolder holder, int position) {
        ActorSearchResponse.ActorSearch actorSearch = actorSearchList.get(position);
        String imageUrl = BASE_IMAGE_URL + PROFILE_SIZE_W185 + actorSearch.getProfile_path();
        Picasso.get().load(imageUrl).into(holder.binding.actorProfileImageView);
        holder.binding.textViewActorSearchName.setText(actorSearch.getName());
    }

    @Override
    public int getItemCount() {
        return actorSearchList.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ActorSearchItemBinding binding;

        ViewHolder(ActorSearchItemBinding b) {
            super(b.getRoot());
            binding=b;
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Integer adapter_position = getBindingAdapterPosition();
            if (mClickListener != null)
                mClickListener.onItemClick(actorSearchList.get(adapter_position), adapter_position);
        }
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(ActorSearchResponse.ActorSearch actorSearch, int position);
    }
}
