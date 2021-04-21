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
import com.aero51.moviedatabase.utils.Constants.BACKDROP_SIZE_W780
import com.aero51.moviedatabase.utils.Constants.BASE_IMAGE_URL
import com.aero51.moviedatabase.utils.Constants.POSTER_SIZE_W154
import com.aero51.moviedatabase.viewmodel.DetailsViewModel
import com.aero51.moviedatabase.viewmodel.SharedViewModel
import com.squareup.picasso.Picasso

class MovieDetailsFragment : Fragment(), CastAdapter.ItemClickListener {
    private var binding: FragmentMovieDetailsBinding? = null
    private var detailsViewModel: DetailsViewModel? = null
    private var castAdapter: CastAdapter? = null
    private var sharedViewModel: SharedViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        detailsViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(DetailsViewModel::class.java)
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    fun showBackButton(show: Boolean) {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(show)
        }
    }

    private fun registerSharedViewModelObserver() {
        sharedViewModel!!.liveDataMovie.observe(viewLifecycleOwner, Observer { movie ->
            binding!!.title.text = movie.title
            binding!!.releaseDate.text = movie.id.toString()
            binding!!.overview.text = movie.overview
            binding!!.tmdbRating.text= movie.vote_average.toString()
            val imageUrl: String = BASE_IMAGE_URL + BACKDROP_SIZE_W780 + movie.backdrop_path
            val posterUrl = BASE_IMAGE_URL + POSTER_SIZE_W154 + movie.poster_path
            Picasso.get().load(imageUrl).fit().centerCrop().placeholder(R.drawable.picture_template).into(binding!!.coverImageView)
            Picasso.get().load(posterUrl).fit().centerCrop().placeholder(R.drawable.picture_template).into(binding!!.posterImageView)
            binding!!.coverImageView.visibility = View.VISIBLE
            registerMovieCastObserver(movie.id)
            registerOmdbMovieDetailsObserver(movie.title)

        })
    }

    private fun registerMovieCastObserver(movieId: Int) {
        detailsViewModel!!.getMovieCast(movieId).observe(viewLifecycleOwner, Observer { (status, data) -> // movieDetailsViewModel.getMovieCast(topRatedMovieId).removeObserver(this);
            if (data != null) {
                Log.d(Constants.LOG, " status: " + status + " list size: " + data.size)
                castAdapter = CastAdapter(context, data)
                castAdapter!!.setClickListener { view: View, actorId: Int, position: Int -> onItemClick(view, actorId, position) }
                binding!!.castRecyclerView.adapter = castAdapter
            }
        })

    }

    private fun registerOmdbMovieDetailsObserver(movieTitle: String) {

        detailsViewModel!!.getOmbdDetails(movieTitle).observe(viewLifecycleOwner, Observer { (status,data) ->
            if (data != null) {
                Log.d("nikola", "imdbRating: " + (data?.imdbRating))
                Log.d("nikola", "ratings: " + (data?.ratings?.get(0)?.source) + " , " + data?.ratings?.get(0)?.value)
                binding!!.imdbRating.text=data?.ratings[0].value
                binding!!.rottenTomatoesRating.text=data?.ratings[1].value
                binding!!.metacriticRating.text=data?.ratings[2].value
            }
        })


    }

    override fun onItemClick(view: View, actorId: Int, position: Int) {
        sharedViewModel!!.changeToActorFragment(position, actorId)
    }
}