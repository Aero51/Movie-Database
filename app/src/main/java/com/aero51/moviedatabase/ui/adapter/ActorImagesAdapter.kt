package com.aero51.moviedatabase.ui.adapter

import android.content.Context
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorImagesResponse.ActorImage
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.aero51.moviedatabase.databinding.ActorImageItemBinding
import com.aero51.moviedatabase.utils.Constants.BASE_IMAGE_URL
import com.aero51.moviedatabase.utils.Constants.PROFILE_SIZE_H632
import com.squareup.picasso.Picasso

class ActorImagesAdapter     // data is passed into the constructor
(context: Context?, private val imagesList: List<ActorImage>) : RecyclerView.Adapter<ActorImagesAdapter.ViewHolder>() {
    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ActorImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    // binds the data to the TextView in each row
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image =  imagesList.get(position)
        val imageUrl: String = BASE_IMAGE_URL + PROFILE_SIZE_H632 + image.file_path
        Picasso.get().load(imageUrl).into(holder.binding.actorImageImageView)
    }

    // total number of rows
    override fun getItemCount(): Int {
        return imagesList.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(var binding: ActorImageItemBinding) : RecyclerView.ViewHolder(binding.root)

    // convenience method for getting data at click position
    private fun getItem(id: Int): ActorImage {

        return   imagesList.get(id)
    }
}