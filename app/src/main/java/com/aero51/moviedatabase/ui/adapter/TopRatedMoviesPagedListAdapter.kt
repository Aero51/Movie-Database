package com.aero51.moviedatabase.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aero51.moviedatabase.R
import com.aero51.moviedatabase.repository.model.NetworkState
import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMoviesPage.TopRatedMovie
import com.aero51.moviedatabase.utils.Constants.BASE_IMAGE_URL
import com.aero51.moviedatabase.utils.Constants.POSTER_SIZE_W154
import com.aero51.moviedatabase.utils.MediaClickListener
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class TopRatedMoviesPagedListAdapter(private val itemClickListener: MediaClickListener) : PagedListAdapter<TopRatedMovie, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    private val networkState: NetworkState? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.movie_item, parent, false)
        return TopRatedMovieHolder(view, itemClickListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentResult = getItem(position)
        (holder as TopRatedMovieHolder).bindTo(currentResult, position)
    }

    class TopRatedMovieHolder(itemView: View, itemClickListener: MediaClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var result: TopRatedMovie? = null
        private var mPosition = 0
        private val imageView: ImageView = itemView.findViewById(R.id.image_view_program)

        private val textViewtitle: TextView = itemView.findViewById(R.id.text_view_title)
        private val textViewVoteAverage: TextView? = null
        private val itemClickListener: MediaClickListener

        init {
            this.itemClickListener = itemClickListener
            itemView.setOnClickListener(this)
        }


        fun bindTo(result: TopRatedMovie?, position: Int) {
            this.result = result
            this.mPosition = position
            textViewtitle.text = result!!.title
            val imageUrl: String = BASE_IMAGE_URL + POSTER_SIZE_W154 + result.poster_path
            Picasso.get().load(imageUrl).fit().centerCrop().into(imageView, object : Callback {
                override fun onSuccess() {}
                override fun onError(e: Exception) {
                    imageView.setBackgroundResource(R.drawable.picture_template)
                }
            })
        }

        override fun onClick(v: View) {
            if (itemClickListener != null && mPosition != RecyclerView.NO_POSITION) {
                itemClickListener.onMediaItemClick(result, mPosition) // call the onClick in the OnItemClickListener
            }
        }


    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<TopRatedMovie> = object : DiffUtil.ItemCallback<TopRatedMovie>() {
            override fun areItemsTheSame(oldItem: TopRatedMovie, newItem: TopRatedMovie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TopRatedMovie, newItem: TopRatedMovie): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }
}