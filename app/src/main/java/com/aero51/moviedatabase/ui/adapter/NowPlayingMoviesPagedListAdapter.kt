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
import com.aero51.moviedatabase.repository.model.tmdb.movie.NowPlayingMoviesPage.NowPlayingMovie
import com.aero51.moviedatabase.utils.Constants.BASE_IMAGE_URL
import com.aero51.moviedatabase.utils.Constants.POSTER_SIZE_W154
import com.aero51.moviedatabase.utils.ObjectClickListener
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class NowPlayingMoviesPagedListAdapter(private val itemClickListener: ObjectClickListener) : PagedListAdapter<NowPlayingMovie, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    private val networkState: NetworkState? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.movie_item, parent, false)
        return NowPlayingngMovieHolder(view, itemClickListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentResult = getItem(position)
        (holder as NowPlayingngMovieHolder).bindTo(currentResult, position)
    }

    class NowPlayingngMovieHolder(itemView: View, itemClickListener: ObjectClickListener?) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var result: NowPlayingMovie? = null
        private var bindingadapterPosition = 0
        private val imageView: ImageView

        //private TextView textViewPosition;
        private val textViewtitle: TextView
        private val textViewVoteAverage: TextView? = null
        private val itemClickListener: ObjectClickListener?

        init {
            imageView = itemView.findViewById(R.id.image_view_program)
            //textViewPosition = itemView.findViewById(R.id.text_view_position);
            textViewtitle = itemView.findViewById(R.id.text_view_title)
            this.itemClickListener = itemClickListener
            itemView.setOnClickListener(this)
        }

        fun bindTo(result: NowPlayingMovie?, position: Int) {
            this.result = result
            bindingadapterPosition = position
            textViewtitle.text = result!!.title
            val imageUrl: String = BASE_IMAGE_URL + POSTER_SIZE_W154 + result.poster_path
            //.placeholder(R.drawable.picture_template)
            Picasso.get().load(imageUrl).fit().centerCrop().into(imageView, object : Callback {
                override fun onSuccess() {}
                override fun onError(e: Exception) {
                    imageView.setBackgroundResource(R.drawable.picture_template)
                }
            })
        }

        override fun onClick(v: View) {
            if (itemClickListener != null && bindingadapterPosition != RecyclerView.NO_POSITION) {
                itemClickListener.onObjectItemClick(result, bindingadapterPosition) // call the onClick in the OnItemClickListener
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<NowPlayingMovie> = object : DiffUtil.ItemCallback<NowPlayingMovie>() {
            override fun areItemsTheSame(oldItem: NowPlayingMovie, newItem: NowPlayingMovie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: NowPlayingMovie, newItem: NowPlayingMovie): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }
}