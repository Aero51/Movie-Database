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
import com.aero51.moviedatabase.repository.model.tmdb.movie.MoviesByGenrePage
import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMoviesPage.PopularMovie
import com.aero51.moviedatabase.ui.adapter.PopularMoviesPagedListAdapter.PopularMovieHolder
import com.aero51.moviedatabase.utils.Constants
import com.aero51.moviedatabase.utils.Constants.BASE_IMAGE_URL
import com.aero51.moviedatabase.utils.Constants.POSTER_SIZE_W154
import com.aero51.moviedatabase.utils.MovieClickListener
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class GenrePagedListAdapter(private val itemClickListener: MovieClickListener): PagedListAdapter<MoviesByGenrePage.GenreMovie, RecyclerView.ViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.movie_item, parent, false)
        val viewHolder = GenreMovieHolder(view, itemClickListener)
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentResult: MoviesByGenrePage.GenreMovie? = getItem(position)
        (holder as GenreMovieHolder).bindTo(currentResult, position)
    }


    class GenreMovieHolder(itemView: View, itemClickListener: MovieClickListener?) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var result: MoviesByGenrePage.GenreMovie? = null
        private var adapterposition = 0
        private val imageView: ImageView
        private val textViewPosition: TextView
        private val textViewtitle: TextView
        private val textViewVoteAverage: TextView? = null
        private val itemClickListener: MovieClickListener?

        fun bindTo(result: MoviesByGenrePage.GenreMovie?, position: Int) {
            this.result = result
            this.adapterposition = position
            textViewPosition.text = (position + 1).toString()
            textViewtitle.text = result!!.title
            val imageUrl: String = BASE_IMAGE_URL + POSTER_SIZE_W154 + result.poster_path
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
                itemClickListener.onObjectItemClick(result, adapterposition) // call the onClick in the OnItemClickListener
                Log.d(Constants.LOG, " Item clicked inside popular holder : $adapterposition")
            }
        }

        init {
            imageView = itemView.findViewById(R.id.image_view_program)
            textViewPosition = itemView.findViewById(R.id.text_view_position)
            textViewtitle = itemView.findViewById(R.id.text_view_title)
            this.itemClickListener = itemClickListener
            itemView.setOnClickListener(this)
        }
    }






    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<MoviesByGenrePage.GenreMovie> = object : DiffUtil.ItemCallback<MoviesByGenrePage.GenreMovie>() {
            override fun areItemsTheSame(oldItem: MoviesByGenrePage.GenreMovie, newItem: MoviesByGenrePage.GenreMovie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MoviesByGenrePage.GenreMovie, newItem: MoviesByGenrePage.GenreMovie): Boolean {
                return oldItem.title == newItem.title
            }
        }


    }


}