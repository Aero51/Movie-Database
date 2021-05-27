package com.aero51.moviedatabase.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aero51.moviedatabase.R
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowVideoResponse.TvShowVideo
import com.aero51.moviedatabase.utils.Constants
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubeThumbnailLoader
import com.google.android.youtube.player.YouTubeThumbnailLoader.OnThumbnailLoadedListener
import com.google.android.youtube.player.YouTubeThumbnailView

class YouTubeTvShowVideoAdapter(private val context: Context, private val youtubeVideoModelArrayList: List<TvShowVideo>?) : RecyclerView.Adapter<YouTubeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YouTubeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.youtube_video_custom_layout, parent, false)
        return YouTubeViewHolder(view)
    }

    override fun onBindViewHolder(holder: YouTubeViewHolder, position: Int) {
        val youtubeVideoModel = youtubeVideoModelArrayList!![position]
        holder.videoTitle.text = youtubeVideoModel.name


        /*  initialize the thumbnail image view , we need to pass Developer Key */holder.videoThumbnailImageView.initialize(Constants.YOUTUBE_API_KEY, object : YouTubeThumbnailView.OnInitializedListener {
            override fun onInitializationSuccess(youTubeThumbnailView: YouTubeThumbnailView, youTubeThumbnailLoader: YouTubeThumbnailLoader) {
                //when initialization is sucess, set the video id to thumbnail to load
                youTubeThumbnailLoader.setVideo(youtubeVideoModel.key)
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(object : OnThumbnailLoadedListener {
                    override fun onThumbnailLoaded(youTubeThumbnailView: YouTubeThumbnailView, s: String) {
                        //when thumbnail loaded successfully release the thumbnail loader as we are showing thumbnail in adapter
                        youTubeThumbnailLoader.release()
                    }

                    override fun onThumbnailError(youTubeThumbnailView: YouTubeThumbnailView, errorReason: YouTubeThumbnailLoader.ErrorReason) {
                        //print or show error when thumbnail load failed
                        Log.e(TAG, "Youtube Thumbnail Error")
                    }
                })
            }

            override fun onInitializationFailure(youTubeThumbnailView: YouTubeThumbnailView, youTubeInitializationResult: YouTubeInitializationResult) {
                //print or show error when initialization failed
                Log.e(TAG, "Youtube Initialization Failure")
            }
        })
    }

    override fun getItemCount(): Int {
        return youtubeVideoModelArrayList?.size ?: 0
    }

    companion object {
        private val TAG = YouTubeTvShowVideoAdapter::class.java.simpleName
    }
}