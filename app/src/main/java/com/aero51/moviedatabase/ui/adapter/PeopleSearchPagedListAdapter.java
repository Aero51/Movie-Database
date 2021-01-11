package com.aero51.moviedatabase.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorSearchResponse;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.aero51.moviedatabase.utils.Constants.BASE_IMAGE_URL;
import static com.aero51.moviedatabase.utils.Constants.PROFILE_SIZE_W185;


public class PeopleSearchPagedListAdapter extends PagedListAdapter<ActorSearchResponse.ActorSearch, PeopleSearchPagedListAdapter.ViewHolder> {
    private CastAdapter.ItemClickListener mClickListener;


    public PeopleSearchPagedListAdapter() {
        super(DIFF_CALLBACK);
    }


    @NonNull
    @Override
    public PeopleSearchPagedListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.actor_search_item, parent, false);
        return new PeopleSearchPagedListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleSearchPagedListAdapter.ViewHolder holder, int position) {
        ActorSearchResponse.ActorSearch person = getItem(position);
        String imageUrl = BASE_IMAGE_URL + PROFILE_SIZE_W185 + person.getProfile_path();
        Picasso.get().load(imageUrl).into(holder.castProfileImageView);
        holder.textViewRealName.setText(person.getName());
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView castProfileImageView;
        TextView textViewRealName;

        ViewHolder(View itemView) {
            super(itemView);
            castProfileImageView = itemView.findViewById(R.id.cast_profile_image_view);
            textViewRealName = itemView.findViewById(R.id.text_view_real_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Integer adapter_position = getBindingAdapterPosition();
            if (mClickListener != null)
                mClickListener.onItemClick(view, getItem(adapter_position).getId(), adapter_position);
        }
    }

    // allows clicks events to be caught
    public void setClickListener(CastAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, Integer actorId, int position);
    }


    private static DiffUtil.ItemCallback<ActorSearchResponse.ActorSearch> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<ActorSearchResponse.ActorSearch>() {
                @Override
                public boolean areItemsTheSame(ActorSearchResponse.ActorSearch oldItem, ActorSearchResponse.ActorSearch newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(ActorSearchResponse.ActorSearch oldItem, ActorSearchResponse.ActorSearch newItem) {
                    return oldItem.getName().equals(newItem.getName());
                }
            };
}
