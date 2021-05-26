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
import com.aero51.moviedatabase.repository.model.tmdb.movie.UpcomingMoviesPage.UpcomingMovie
import com.aero51.moviedatabase.utils.Constants.BASE_IMAGE_URL
import com.aero51.moviedatabase.utils.Constants.POSTER_SIZE_W154
import com.aero51.moviedatabase.utils.ObjectClickListener
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class UpcomingMoviesPagedListAdapter(private val itemClickListener: ObjectClickListener) : PagedListAdapter<UpcomingMovie, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    private val networkState: NetworkState? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.movie_item, parent, false)
        return UpcomingMovieHolder(view, itemClickListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentResult = getItem(position)
        (holder as UpcomingMovieHolder).bindTo(currentResult, position)
    }

    class UpcomingMovieHolder(itemView: View, itemClickListener: ObjectClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var result: UpcomingMovie? = null
        private var mposition = 0
        private val imageView: ImageView = itemView.findViewById(R.id.image_view_program)

        //private TextView textViewPosition;
        private val textViewtitle: TextView = itemView.findViewById(R.id.text_view_title)
        private val textViewVoteAverage: TextView? = null
        private val itemClickListener: ObjectClickListener

        init {
            //textViewPosition = itemView.findViewById(R.id.text_view_position);
            this.itemClickListener = itemClickListener
            itemView.setOnClickListener(this)
        }

        fun bindTo(result: UpcomingMovie?, position: Int) {
            this.result = result
            this.mposition = position

            //textViewPosition.setText(String.valueOf(position + 1));
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
            if (itemClickListener != null && mposition != RecyclerView.NO_POSITION) {
                itemClickListener.onObjectItemClick(result, mposition) // call the onClick in the OnItemClickListener
            }
        }


    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<UpcomingMovie> = object : DiffUtil.ItemCallback<UpcomingMovie>() {
            override fun areItemsTheSame(oldItem: UpcomingMovie, newItem: UpcomingMovie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UpcomingMovie, newItem: UpcomingMovie): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }
}