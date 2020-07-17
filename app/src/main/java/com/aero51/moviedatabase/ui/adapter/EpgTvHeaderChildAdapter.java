package com.aero51.moviedatabase.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.aero51.moviedatabase.R;
import com.squareup.picasso.Picasso;



public class EpgTvHeaderChildAdapter extends RecyclerView.Adapter <EpgTvHeaderChildAdapter.ViewHolder> {
private Integer drawableId;

    public EpgTvHeaderChildAdapter(){
    }

    public void setDrawableId(Integer drawableId) {
        this.drawableId=drawableId;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.epg_tv_cro_channels_header_child_item, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setImage(holder.imageViewTvChannelLogo);
        setOnClick(( holder).imageViewTvChannelLogo, position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewTvChannelLogo;


        ViewHolder(View itemView) {
            super(itemView);

            imageViewTvChannelLogo = itemView.findViewById(R.id.image_view_tv_channel_logo);
        }
    }



    private void setImage(final ImageView imageView) {
        Picasso.get().load(drawableId).fit().into(imageView);

    }

    private void setOnClick(ImageView imageView, final int position) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


}