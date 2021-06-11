package com.aero51.moviedatabase.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aero51.moviedatabase.R
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorSearchResponse.ActorSearch
import com.aero51.moviedatabase.utils.ActorClickListener
import com.aero51.moviedatabase.utils.Constants.BASE_IMAGE_URL
import com.aero51.moviedatabase.utils.Constants.PROFILE_SIZE_W185
import com.aero51.moviedatabase.utils.ObjectClickListener
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class PeopleSearchPagedListAdapter(private val itemClickListener: ActorClickListener) : PagedListAdapter<ActorSearch, PeopleSearchPagedListAdapter.ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.actor_search_item, parent, false)
        return ViewHolder(view,itemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = getItem(position)
        val imageUrl: String = BASE_IMAGE_URL + PROFILE_SIZE_W185 + person!!.profile_path
        Picasso.get().load(imageUrl).into(holder.castProfileImageView)
        holder.textViewRealName.text = person.name
    }

    inner class ViewHolder internal constructor(itemView: View, itemClickListener: ActorClickListener?) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var castProfileImageView: CircleImageView
        var textViewRealName: TextView
        private val itemClickListener: ActorClickListener?
        init {
            castProfileImageView = itemView.findViewById(R.id.actor_profile_image_view)
            textViewRealName = itemView.findViewById(R.id.text_view_actor_search_name)
            this.itemClickListener = itemClickListener
            itemView.setOnClickListener(this)
        }
        override fun onClick(view: View) {
            val adapter_position = bindingAdapterPosition
            if (itemClickListener != null) {
                getItem(adapter_position)?.let { itemClickListener.onActorItemClick(it.id, adapter_position) }
            } // call the onClick in the OnItemClickListener
        }


    }


    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<ActorSearch> = object : DiffUtil.ItemCallback<ActorSearch>() {
            override fun areItemsTheSame(oldItem: ActorSearch, newItem: ActorSearch): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ActorSearch, newItem: ActorSearch): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
}