package com.aero51.moviedatabase.ui.tvshows

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aero51.moviedatabase.R
import com.aero51.moviedatabase.YoutubePlayerActivity
import com.aero51.moviedatabase.databinding.FragmentTvShowDetailsBinding
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowDetailsResponse
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowVideoResponse
import com.aero51.moviedatabase.ui.adapter.*
import com.aero51.moviedatabase.ui.listeners.GenreObjectClickListener
import com.aero51.moviedatabase.ui.listeners.RecyclerViewOnClickListener
import com.aero51.moviedatabase.utils.*
import com.aero51.moviedatabase.viewmodel.DetailsViewModel
import com.aero51.moviedatabase.viewmodel.SharedViewModel
import com.squareup.picasso.Picasso

class TvShowDetailsFragmentSecond : Fragment(), MovieCastAdapter.ItemClickListener,
    GenreObjectClickListener {
    private var binding: FragmentTvShowDetailsBinding? = null
    private var detailsViewModel: DetailsViewModel? = null
    private var tvShowCastAdapter: TvShowCastAdapter? = null
    private var sharedViewModel: SharedViewModel? = null
    private lateinit var videosGlobalList: List<TvShowVideoResponse.TvShowVideo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        detailsViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(DetailsViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentTvShowDetailsBinding.inflate(inflater, container, false)
        //cover_image_view = getActivity().findViewById(R.id.expandedImage);
        binding!!.castRecyclerView.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding!!.castRecyclerView.layoutManager = linearLayoutManager
        //castRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        binding!!.movieGenresRecyclerViewHorizontal.setHasFixedSize(true)
        binding!!.movieGenresRecyclerViewHorizontal.isNestedScrollingEnabled = false
        val genresLinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding!!.movieGenresRecyclerViewHorizontal.layoutManager = genresLinearLayoutManager

        binding!!.youtubeRecyclerView.setHasFixedSize(true)
        val youtubeLinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding!!.youtubeRecyclerView.layoutManager = youtubeLinearLayoutManager

        binding!!.youtubeRecyclerView.addOnItemTouchListener(
            RecyclerViewOnClickListener(
                requireContext(),
                RecyclerViewOnClickListener.OnItemClickListener { view, position -> //start youtube player activity by passing selected video id via intent
                    startActivity(
                        Intent(requireContext(), YoutubePlayerActivity::class.java)
                            .putExtra("video_id", videosGlobalList[position].key)
                    )
                })
        )

        registerSharedViewModelObserver()
        val toolbar = requireActivity().findViewById<View>(R.id.toolbar) as Toolbar
        //toolbar.setTitle("text");
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
            //showBackButton(false)
        }
        showBackButton(true)
        return binding!!.root
    }

    private fun registerSharedViewModelObserver() {
        sharedViewModel!!.liveDataTvShowFromTvShowActorFragment.observe(viewLifecycleOwner, Observer { tvShow ->
            binding!!.title.text = tvShow.name
            binding!!.releaseYear.text= tvShow.first_air_date?.let { DateHelper.formatDateStringToDefaultLocale(it,"yyyy-MM-dd","yyyy") }
            binding!!.releaseDate.text = tvShow.id.toString()
            binding!!.overview.text = tvShow.overview
            binding!!.tmdbRating.text = tvShow.vote_average.toString()
            val imageUrl: String = Constants.BASE_IMAGE_URL + Constants.BACKDROP_SIZE_W780 + tvShow.backdrop_path
            val posterUrl = Constants.BASE_IMAGE_URL + Constants.POSTER_SIZE_W154 + tvShow.poster_path
            Picasso.get().load(imageUrl).fit().centerCrop().placeholder(R.drawable.picture_template).into(binding!!.coverImageView)
            Picasso.get().load(posterUrl).fit().centerCrop().placeholder(R.drawable.picture_template).into(binding!!.posterImageView)
            binding!!.coverImageView.visibility = View.VISIBLE
            registerTvShowCastObserver(tvShow.id)
            tvShow.name?.let { registerOmdbDetailsObserver(it) }
            registerTvShowVideosObserver(tvShow.id)
            registerTvShowDetailsObserver(tvShow.id)
            isTvShowFavourite(tvShow.id )
        })
    }

    private fun registerTvShowCastObserver(tvShowId: Int) {
        detailsViewModel!!.getTvShowCast(tvShowId).observe(viewLifecycleOwner, Observer { (status, data) -> // movieDetailsViewModel.getMovieCast(topRatedMovieId).removeObserver(this);
            if (data != null) {
                tvShowCastAdapter = TvShowCastAdapter(context, data)
                tvShowCastAdapter!!.setClickListener { view: View, actorId: Int, position: Int -> onItemClick(view, actorId, position) }
                binding!!.castRecyclerView.adapter = tvShowCastAdapter
            }
        })
    }

    private fun registerOmdbDetailsObserver(tvShowTitle: String) {
        //TODO  upcoming movies are not yet present on omd api
        detailsViewModel!!.getOmbdDetails(tvShowTitle).observe(viewLifecycleOwner, Observer { (status, data, errorMsg) ->
            if (data != null && status == Status.SUCCESS) {
                //TODO   implement hiding of different rating text views (drawables) based if they are present in the list
                val tvShowRatingsList = data.ratings
                if (tvShowRatingsList != null) {
                    for (tvShowRating in tvShowRatingsList) {
                        if (tvShowRating.source.equals("Internet Movie Database")) {
                            binding!!.imdbRating.text = tvShowRating.value
                        }
                        if (tvShowRating.source.equals("Rotten Tomatoes")) {
                            binding!!.rottenTomatoesRating.text = tvShowRating.value
                        }
                        if (tvShowRating.source.equals("Metacritic")) {
                            binding!!.metacriticRating.text = tvShowRating.value
                        }
                    }
                }
            }
        })
    }

    private fun registerTvShowVideosObserver(movieId: Int) {
        // TODO   implement traversing trough list to exclude Vimeo videos
        detailsViewModel?.getVideosForTvShow(movieId)?.observe(viewLifecycleOwner, Observer { videosList ->
            if (videosList != null && videosList.status == Status.SUCCESS) {
                for (tvShowVideo in videosList.data!!) {
                }
                videosGlobalList = videosList.data
                val adapter = YouTubeTvShowVideoAdapter(requireContext(), videosGlobalList)
                binding?.youtubeRecyclerView?.adapter = adapter

            }
        })
    }

    private fun registerTvShowDetailsObserver(tvShowId: Int) {
        //TODO  put tvShowDetails.data  in variable
        detailsViewModel?.getDetailsForTvShow(tvShowId)?.observe(viewLifecycleOwner, Observer { tvShowDetails ->
            if (tvShowDetails != null && tvShowDetails.status == Status.SUCCESS) {

                binding?.originalTitleTextView?.text = tvShowDetails.data?.original_name.toString()
                binding?.releasedTextView?.text = tvShowDetails.data?.first_air_date?.let { DateHelper.formatDateStringToDefaultLocale(it, "yyyy-MM-dd", "dd MMMM yyyy") }
                binding?.numberOfEpisodesTextView?.text = tvShowDetails.data?.number_of_episodes.toString()
                binding?.numberOfSeasonsTextView?.text = tvShowDetails.data?.number_of_seasons.toString()
                val runtimes: MutableList<Int> = mutableListOf()
                for (runtime in tvShowDetails.data?.episode_run_time!!) {
                    runtime.let { runtimes.add(it) }
                }
                binding?.runtimeTextView?.text = StringHelper.joinInts(", ", runtimes) + " minuta"
                val tvShowGenresAdapter = tvShowDetails.data?.genres?.let { TvShowGenresAdapter(it, this) }
                binding?.movieGenresRecyclerViewHorizontal?.adapter = tvShowGenresAdapter

                val creatorsList: MutableList<String> = mutableListOf()
                for (created_by in tvShowDetails.data?.created_by!!) {
                    created_by.name?.let { creatorsList.add(it) }
                }
                binding!!.createdByTextView.text = StringHelper.joinStrings(", ", creatorsList)

                val productionCompanies: MutableList<String> = mutableListOf()
                for (production_company in tvShowDetails.data?.production_companies!!) {
                    production_company.name?.let { productionCompanies.add(it) }
                }
                binding!!.productionCompaniesTextView.text = StringHelper.joinStrings(", ", productionCompanies)
                setFavouriteOnClickListener(tvShowDetails.data!!)

            }
        })
    }
    private fun isTvShowFavourite(tvShowId: Int) {
        //Checking if already added to favourite

        detailsViewModel?.checkIfTvShowIsFavourite(tvShowId)?.observe(viewLifecycleOwner, Observer {
            binding?.addToFavouritesCheckBox!!.isChecked = it != null

        })

    }

    private fun setFavouriteOnClickListener(tvShow: TvShowDetailsResponse) {
        val addToFavoritesCheckBox = binding?.addToFavouritesCheckBox
        addToFavoritesCheckBox!!.setOnClickListener {

            if (addToFavoritesCheckBox.isChecked) {
                detailsViewModel!!.insertFavouriteTvShow(tvShow)
                Toast.makeText(context, tvShow.original_name + " dodan u listu favorita.", Toast.LENGTH_LONG).show();
            } else {
                detailsViewModel!!.deleteFavouriteTvShow(tvShow)
                Toast.makeText(context, tvShow.original_name + " maknut iz liste favorita.", Toast.LENGTH_LONG).show();
            }

        }

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
        sharedViewModel!!.changeToTvActorFragment( actorId)
    }

    override fun onGenreItemClick(genreId: Int, position: Int) {
        sharedViewModel?.changeToTvShowsByGenreListFragment(genreId)
    }
}
