package com.aero51.moviedatabase.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aero51.moviedatabase.R
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieGenresResponse.MovieGenre
import com.aero51.moviedatabase.utils.GenreObjectClickListener

class MovieGenresAdapter // private ActorImagesResponse.ActorImage getItem(int id) {
//    return imagesList.get(id);
//}
// data is passed into the constructor
(private val movieGenreList: List<MovieGenre>, private val mClickListener: GenreObjectClickListener) : RecyclerView.Adapter<MovieGenresAdapter.ViewHolder>() {

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.genre_item, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the TextView in each row
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val genre = movieGenreList[position].name
        holder.genreTextView.text = genre
    }

    // total number of rows
    override fun getItemCount(): Int {
        return movieGenreList.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var genreTextView: TextView

        init {
            genreTextView = itemView.findViewById(R.id.tv_genre)
            genreTextView.setOnClickListener(this)
        }
        override fun onClick(v: View) {
            val adapter_position = bindingAdapterPosition
            mClickListener.onGenreItemClick(movieGenreList[adapter_position].id, adapter_position)
        }


    } // convenience method for getting data at click position

}