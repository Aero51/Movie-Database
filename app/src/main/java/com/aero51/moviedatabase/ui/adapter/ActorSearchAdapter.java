package com.aero51.moviedatabase.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorSearchResponse.ActorSearch;
import com.aero51.moviedatabase.repository.model.tmdb.credits.Cast;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.aero51.moviedatabase.utils.Constants.BASE_IMAGE_URL;
import static com.aero51.moviedatabase.utils.Constants.PROFILE_SIZE_W185;

public class ActorSearchAdapter extends RecyclerView.Adapter<ActorSearchAdapter.ViewHolder>{

    private List<ActorSearch> actorSearchList;
    private ItemClickListener mClickListener;



    public ActorSearchAdapter(List<ActorSearch> actorSearchList,ItemClickListener clickListener) {
        this.actorSearchList = actorSearchList;
        this.mClickListener=clickListener;
    }

    @NonNull
    @Override
    public ActorSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.actor_search_item, parent, false);
        return new ActorSearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActorSearchAdapter.ViewHolder holder, int position) {
        ActorSearch actorSearch=actorSearchList.get(position);
        String imageUrl = BASE_IMAGE_URL + PROFILE_SIZE_W185 + actorSearch.getProfile_path();
        Picasso.get().load(imageUrl).into(holder.actorProfileImageView);
        holder.textViewActorName.setText(actorSearch.getName());
    }

    @Override
    public int getItemCount() {
        return actorSearchList.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView actorProfileImageView;
        TextView textViewActorName;


        ViewHolder(View itemView) {
            super(itemView);
            actorProfileImageView=itemView.findViewById(R.id.actor_profile_image_view);
            textViewActorName = itemView.findViewById(R.id.text_view_actor_search_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Integer adapter_position = getBindingAdapterPosition();
            if (mClickListener != null) mClickListener.onItemClick(actorSearchList.get(adapter_position), adapter_position);
        }
    }
    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(ActorSearch actorSearch, int position);
    }
}
