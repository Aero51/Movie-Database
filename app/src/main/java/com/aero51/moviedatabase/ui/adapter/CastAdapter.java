package com.aero51.moviedatabase.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.tmdb.credits.Cast;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.aero51.moviedatabase.utils.Constants.BASE_IMAGE_URL;
import static com.aero51.moviedatabase.utils.Constants.PROFILE_SIZE_W185;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {

    private List<Cast> castList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public CastAdapter(Context context, List<Cast> castList) {
        this.mInflater = LayoutInflater.from(context);
        this.castList=castList;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.cast_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Cast cast  = castList.get(position);
        String imageUrl = BASE_IMAGE_URL + PROFILE_SIZE_W185 + cast.getProfile_path();
        Picasso.get().load(imageUrl).into(holder.castProfileImageView);
        holder.textViewCastName.setText(cast.getCharacter());
        holder.textViewRealName.setText(cast.getName());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return castList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView castProfileImageView;
        TextView textViewCastName;
        TextView textViewRealName;

        ViewHolder(View itemView) {
            super(itemView);
            castProfileImageView=itemView.findViewById(R.id.cast_profile_image_view);
            textViewCastName= itemView.findViewById(R.id.text_view_cast_name);
            textViewRealName=itemView.findViewById(R.id.text_view_real_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Integer adapter_position = getBindingAdapterPosition();
            if (mClickListener != null) mClickListener.onItemClick(view,getItem(adapter_position).getId(), adapter_position);
        }
    }

    // convenience method for getting data at click position
   private Cast getItem(int id) {
        return castList.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view,Integer actorId, int position);
    }
}