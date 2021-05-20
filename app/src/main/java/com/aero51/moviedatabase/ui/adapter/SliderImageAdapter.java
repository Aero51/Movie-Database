package com.aero51.moviedatabase.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorImagesResponse;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.aero51.moviedatabase.utils.Constants.BASE_IMAGE_URL;
import static com.aero51.moviedatabase.utils.Constants.PROFILE_SIZE_H632;
import static com.aero51.moviedatabase.utils.Constants.PROFILE_SIZE_W185;

public class SliderImageAdapter extends
        SliderViewAdapter<SliderImageAdapter.SliderAdapterVH> {

    private Context context;
    private List<ActorImagesResponse.ActorImage> mSliderItems = new ArrayList<>();

    public SliderImageAdapter(Context context) {
        this.context = context;
    }

    public void renewItems(List<ActorImagesResponse.ActorImage> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(ActorImagesResponse.ActorImage sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        ActorImagesResponse.ActorImage sliderItem = mSliderItems.get(position);

        //viewHolder.textViewDescription.setText("Tim Robbins");
        viewHolder.textViewDescription.setTextSize(16);
        viewHolder.textViewDescription.setTextColor(Color.WHITE);
        //Picasso.get().load(epgProgram.getIcon()).fit().centerCrop().into(binding.i
        String  imageUrl = BASE_IMAGE_URL + PROFILE_SIZE_H632 + sliderItem.getFile_path();
        Picasso.get().load(imageUrl).fit().centerCrop().into(viewHolder.imageViewBackground);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        ImageView imageGifContainer;
        TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView = itemView;
        }
    }

}