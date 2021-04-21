package com.aero51.moviedatabase.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aero51.moviedatabase.R
import com.aero51.moviedatabase.databinding.FragmentMovieDetailsBinding
import com.aero51.moviedatabase.ui.adapter.CastAdapter
import com.aero51.moviedatabase.utils.Constants
import com.aero51.moviedatabase.viewmodel.DetailsViewModel
import com.aero51.moviedatabase.viewmodel.SharedViewModel
import com.squareup.picasso.Picasso

class TvShowDetailsFragment: Fragment(), CastAdapter.ItemClickListener {
    private var binding: FragmentMovieDetailsBinding? = null
    private var tmdbDetailsViewModel: DetailsViewModel? = null
    private var castAdapter: CastAdapter? = null
    private var sharedViewModel: SharedViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        tmdbDetailsViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(DetailsViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        // Log.d(Constants.LOG, "TopRatedMovieDetailsFragment onCreateView " );
        //cover_image_view = getActivity().findViewById(R.id.expandedImage);
        binding!!.castRecyclerView.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding!!.castRecyclerView.layoutManager = linearLayoutManager
        //castRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        registerSharedViewModelObserver()
        val toolbar = requireActivity().findViewById<View>(R.id.toolbar) as Toolbar
        //toolbar.setTitle("text");
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
            Log.d(Constants.LOG, "Toolbar clicked!")
            showBackButton(false)
        }
        showBackButton(true)
        return binding!!.root
    }

    private fun registerSharedViewModelObserver() {
        sharedViewModel!!.liveDataTvShow.observe(viewLifecycleOwner, Observer { tvShow ->
            binding!!.title.text = tvShow.name
            binding!!.releaseDate.text = tvShow.id.toString()
            binding!!.overview.text = tvShow.overview
            val imageUrl: String = Constants.BASE_IMAGE_URL + Constants.BACKDROP_SIZE_W780 + tvShow.backdrop_path
            Picasso.get().load(imageUrl).fit().centerCrop().placeholder(R.drawable.picture_template).into(binding!!.coverImageView)
            binding!!.coverImageView.visibility = View.VISIBLE
            registerTvShowDetailsObserver(tvShow.id)
        })
    }
    private fun registerTvShowDetailsObserver(tvShowId: Int) {
        tmdbDetailsViewModel!!.getMovieCast(tvShowId).observe(viewLifecycleOwner, Observer { (status, data) -> // movieDetailsViewModel.getMovieCast(topRatedMovieId).removeObserver(this);
            if (data != null) {
                Log.d(Constants.LOG, " status: " + status + " list size: " + data.size)
                castAdapter = CastAdapter(context, data)
                castAdapter!!.setClickListener { view: View, actorId: Int, position: Int -> onItemClick(view, actorId, position) }
                binding!!.castRecyclerView.adapter = castAdapter
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    fun showBackButton(show: Boolean) {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(show)
        }
    }

    override fun onItemClick(view: View?, actorId: Int, position: Int) {
        sharedViewModel!!.changeToActorFragment(position, actorId)
    }
}
