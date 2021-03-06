package com.aero51.moviedatabase.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aero51.moviedatabase.R
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieFavourite
import com.aero51.moviedatabase.utils.Constants.BASE_IMAGE_URL
import com.aero51.moviedatabase.utils.Constants.POSTER_SIZE_W154
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class MovieFavoritesAdapter(private val movieFavouritesList: List<MovieFavourite>, private val mClickListener: OnMovieItemClickListener) : RecyclerView.Adapter<MovieFavoritesAdapter.ViewHolder>() {

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the TextView in each row
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movieFavourite = movieFavouritesList[position]
        holder.titleTextView.text = movieFavourite.title
        val imageUrl: String = BASE_IMAGE_URL + POSTER_SIZE_W154 + movieFavourite.poster_path
        Picasso.get().load(imageUrl).fit().centerCrop().into(holder.imageView, object : Callback {
            override fun onSuccess() {}
            override fun onError(e: Exception) {
                holder.imageView.setBackgroundResource(R.drawable.picture_template)
            }
        })
    }

    // total number of rows
    override fun getItemCount(): Int {
        return movieFavouritesList.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var titleTextView: TextView
        var imageView: ImageView

        init {
            titleTextView = itemView.findViewById(R.id.text_view_title)
            imageView=itemView.findViewById(R.id.image_view_program)
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View) {
            val adapterPosition = bindingAdapterPosition
            mClickListener.onMovieItemClick(movieFavouritesList[adapterPosition], adapterPosition)
        }


    } // convenience method for getting data at click position

    interface OnMovieItemClickListener {
        fun onMovieItemClick(movieFavorite: MovieFavourite, position: Int)
    }
}