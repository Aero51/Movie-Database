package com.aero51.moviedatabase.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aero51.moviedatabase.R
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowsByGenrePage
import com.aero51.moviedatabase.utils.Constants
import com.aero51.moviedatabase.ui.listeners.MediaClickListener
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class TvShowsByGenrePagedListAdapter(private val itemClickListener: MediaClickListener): PagedListAdapter<TvShowsByGenrePage.TvShowByGenre, RecyclerView.ViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.media_by_genre_grid_item, parent, false)
        val viewHolder = GenreTvShowHolder(view, itemClickListener)
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentResult: TvShowsByGenrePage.TvShowByGenre? = getItem(position)
        (holder as GenreTvShowHolder).bindTo(currentResult, position)
    }


    class GenreTvShowHolder(itemView: View, itemClickListener: MediaClickListener?) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var result: TvShowsByGenrePage.TvShowByGenre? = null
        private var adapterposition = 0
        private val imageView: ImageView
        //private val textViewPosition: TextView
        private val textViewtitle: TextView
        private val textViewVoteAverage: TextView? = null
        private val itemClickListener: MediaClickListener?

        init {
            imageView = itemView.findViewById(R.id.image_view_program)
            //textViewPosition = itemView.findViewById(R.id.text_view_position)
            textViewtitle = itemView.findViewById(R.id.text_view_title)
            this.itemClickListener = itemClickListener
            itemView.setOnClickListener(this)
        }

        fun bindTo(result: TvShowsByGenrePage.TvShowByGenre?, position: Int) {
            this.result = result
            this.adapterposition = position
            //textViewPosition.text = (position + 1).toString()

            textViewtitle.text = result!!.name
            val imageUrl: String = Constants.BASE_IMAGE_URL + Constants.POSTER_SIZE_W154 + result.poster_path
            // .placeholder(R.drawable.picture_template)
            Picasso.get().load(imageUrl).fit().centerCrop().into(imageView, object : Callback {
                override fun onSuccess() {}
                override fun onError(e: Exception) {
                    imageView.setBackgroundResource(R.drawable.picture_template)
                }
            })
        }

        override fun onClick(v: View) {
            if (itemClickListener != null && adapterposition != RecyclerView.NO_POSITION) {
                itemClickListener.onMediaItemClick(result, adapterposition) // call the onClick in the OnItemClickListener
            }
        }

    }



    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<TvShowsByGenrePage.TvShowByGenre> = object : DiffUtil.ItemCallback<TvShowsByGenrePage.TvShowByGenre>() {
            override fun areItemsTheSame(oldItem: TvShowsByGenrePage.TvShowByGenre, newItem: TvShowsByGenrePage.TvShowByGenre): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvShowsByGenrePage.TvShowByGenre, newItem: TvShowsByGenrePage.TvShowByGenre): Boolean {
                return oldItem.name == newItem.name
            }
        }


    }


}