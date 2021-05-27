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
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowSearchResult
import com.aero51.moviedatabase.utils.Constants.BASE_IMAGE_URL
import com.aero51.moviedatabase.utils.Constants.POSTER_SIZE_W154
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class TvShowsPagedListAdapter : PagedListAdapter<TvShowSearchResult.TvShow, TvShowsPagedListAdapter.ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentTvShow = getItem(position)
        //holder.textViewPosition.setText(String.valueOf(position + 1));
        holder.textViewtitle.text = currentTvShow!!.name
        val imageUrl: String = BASE_IMAGE_URL + POSTER_SIZE_W154 + currentTvShow.poster_path
        //.placeholder(R.drawable.picture_template)
        Picasso.get().load(imageUrl).fit().centerCrop().into(holder.imageView, object : Callback {
            override fun onSuccess() {}
            override fun onError(e: Exception) {
                holder.imageView.setBackgroundResource(R.drawable.picture_template)
            }
        })
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val imageView: ImageView

        //private TextView textViewPosition;
        val textViewtitle: TextView
        override fun onClick(view: View) {
            val adapter_position = bindingAdapterPosition
        }

        init {
            imageView = itemView.findViewById(R.id.image_view_program)
            //textViewPosition = itemView.findViewById(R.id.text_view_position);
            textViewtitle = itemView.findViewById(R.id.text_view_title)
            itemView.setOnClickListener(this)
        }
    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(position: Int)
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<TvShowSearchResult.TvShow> = object : DiffUtil.ItemCallback<TvShowSearchResult.TvShow>() {
            override fun areItemsTheSame(oldItem: TvShowSearchResult.TvShow, newItem: TvShowSearchResult.TvShow): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvShowSearchResult.TvShow, newItem: TvShowSearchResult.TvShow): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
}