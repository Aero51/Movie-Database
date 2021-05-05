package com.aero51.moviedatabase.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.aero51.moviedatabase.databinding.FragmentMovieDetailsBinding
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieDetailsResponse
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieVideosResponse
import com.aero51.moviedatabase.ui.adapter.MovieCastAdapter
import com.aero51.moviedatabase.ui.adapter.MovieGenresAdapter
import com.aero51.moviedatabase.ui.adapter.YouTubeMovieVideoAdapter
import com.aero51.moviedatabase.utils.*
import com.aero51.moviedatabase.utils.Constants.BACKDROP_SIZE_W780
import com.aero51.moviedatabase.utils.Constants.BASE_IMAGE_URL
import com.aero51.moviedatabase.utils.Constants.POSTER_SIZE_W154
import com.aero51.moviedatabase.viewmodel.DetailsViewModel
import com.aero51.moviedatabase.viewmodel.SharedViewModel
import com.squareup.picasso.Picasso

class MovieDetailsFragment : Fragment(), MovieCastAdapter.ItemClickListener, GenreObjectClickListener {
    private var binding: FragmentMovieDetailsBinding? = null
    private var detailsViewModel: DetailsViewModel? = null
    private var movieCastAdapter: MovieCastAdapter? = null
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var videosGlobalList: List<MovieVideosResponse.MovieVideo>
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
        val castLinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding!!.castRecyclerView.layoutManager = castLinearLayoutManager
        //castRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        binding!!.youtubeRecyclerView.setHasFixedSize(true)
        val youtubeLinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding!!.youtubeRecyclerView.layoutManager = youtubeLinearLayoutManager

        binding!!.movieGenresRecyclerViewHorizontal.setHasFixedSize(true)
        binding!!.movieGenresRecyclerViewHorizontal.isNestedScrollingEnabled = false
        val genresLinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding!!.movieGenresRecyclerViewHorizontal.layoutManager = genresLinearLayoutManager


        binding!!.youtubeRecyclerView.addOnItemTouchListener(RecyclerViewOnClickListener(requireContext(), RecyclerViewOnClickListener.OnItemClickListener { view, position -> //start youtube player activity by passing selected video id via intent
            startActivity(Intent(requireContext(), YoutubePlayerActivity::class.java)
                    .putExtra("video_id", videosGlobalList[position].key))
        }))


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
            binding!!.tmdbRating.text = movie.vote_average.toString()
            val imageUrl: String = BASE_IMAGE_URL + BACKDROP_SIZE_W780 + movie.backdrop_path
            val posterUrl = BASE_IMAGE_URL + POSTER_SIZE_W154 + movie.poster_path
            Picasso.get().load(imageUrl).fit().centerCrop().placeholder(R.drawable.picture_template).into(binding!!.coverImageView)
            Picasso.get().load(posterUrl).fit().centerCrop().placeholder(R.drawable.picture_template).into(binding!!.posterImageView)
            binding!!.coverImageView.visibility = View.VISIBLE
            registerMovieCastObserver(movie.id)
            registerOmdbMovieDetailsObserver(movie.title)
            registerMovieVideosObserver(movie.id)
            registerMovieDetailsObserver(movie.id)
            isMovieFavourite(movie.id)
            Log.d("nikola", "registerSharedViewModelObserver movieId: " + movie.id)


        })
    }

    private fun registerMovieCastObserver(movieId: Int) {
        detailsViewModel!!.getMovieCast(movieId).observe(viewLifecycleOwner, Observer { (status, data) -> // movieDetailsViewModel.getMovieCast(topRatedMovieId).removeObserver(this);
            if (data != null) {
                Log.d(Constants.LOG, " status: " + status + " list size: " + data.size)
                movieCastAdapter = MovieCastAdapter(context, data)
                movieCastAdapter!!.setClickListener { view: View, actorId: Int, position: Int -> onItemClick(view, actorId, position) }
                binding!!.castRecyclerView.adapter = movieCastAdapter
            }
        })

    }

    private fun registerOmdbMovieDetailsObserver(movieTitle: String) {
        //TODO  upcoming movies are not yet present on omd api
        detailsViewModel!!.getOmbdDetails(movieTitle).observe(viewLifecycleOwner, Observer { (status, data) ->
            if (data != null && status == Status.SUCCESS) {
                Log.d("nikola", "imdbRating: " + (data?.imdbRating))
                Log.d("nikola", "ratings: " + (data?.ratings?.get(0)?.source) + " , " + data?.ratings?.get(0)?.value)

                //TODO   implement hiding of different rating text views (drawables) based if they are present in the list
                val movieRatingsList = data.ratings
                for (movieRating in movieRatingsList) {
                    if (movieRating.source.equals("Internet Movie Database")) {
                        binding!!.imdbRating.text = movieRating.value
                    }
                    if (movieRating.source.equals("Rotten Tomatoes")) {
                        binding!!.rottenTomatoesRating.text = movieRating.value
                    }
                    if (movieRating.source.equals("Metacritic")) {
                        binding!!.metacriticRating.text = movieRating.value
                    }
                }
            }
        })
    }


    private fun registerMovieVideosObserver(movieId: Int) {
        // TODO   implement traversing trough list to exclude Vimeo videos
        detailsViewModel?.getVideosForMovie(movieId)?.observe(viewLifecycleOwner, Observer { videosList ->
            if (videosList != null && videosList.status == Status.SUCCESS) {
                for (movieVideo in videosList.data!!) {
                    Log.d("nikola", "movie video  : " + movieVideo.name + " ,site: " + movieVideo.site)

                }
                videosGlobalList = videosList.data
                val adapter = YouTubeMovieVideoAdapter(requireContext(), videosGlobalList)
                binding?.youtubeRecyclerView?.adapter = adapter

            }
        })
    }

    private fun registerMovieDetailsObserver(movieId: Int) {

        detailsViewModel?.getDetailsForMovie(movieId)?.observe(viewLifecycleOwner, Observer { movieDetails ->
            if (movieDetails != null && movieDetails.status == Status.SUCCESS) {
                Log.d("nikola", "MovieDetailsObserver videosList.status revenue: " + (movieDetails.data?.revenue))

                binding!!.originalTitleTextView.text = movieDetails.data?.original_title
                binding!!.releasedTextView.text = movieDetails.data?.release_date?.let { DateHelper.formatDateStringToDefaultLocale(it, "yyyy-MM-dd", "dd MMMM yyyy") }
                binding!!.runtimeTextView.text = movieDetails.data?.runtime.toString() + " minutes"
                binding!!.productionCompaniesTextView.text = "TODO" // movieDetails.data?.production_companies.toString()
                binding!!.budgetTextView.text = movieDetails.data?.budget?.toDouble()?.let { CurrencyConverter.currencyFormat(it) }
                binding!!.revenueTextView.text = movieDetails.data?.revenue?.toDouble()?.let { CurrencyConverter.currencyFormat(it) }


                val movieGenresAdapter = movieDetails.data?.genres?.let { MovieGenresAdapter(it, this) }
                binding?.movieGenresRecyclerViewHorizontal?.adapter = movieGenresAdapter

                setFavouriteOnClickListener(movieDetails.data!!)

            }
        })
    }

    private fun isMovieFavourite(movieId: Int) {
        //Checking if already added to favourite
        //val entry: LiveData<MovieFavourite>? = detailsViewModel?.checkIfMovieIsFavourite(movieId)

        detailsViewModel?.checkIfMovieIsFavourite(movieId)?.observe(viewLifecycleOwner, Observer {
            binding?.addToFavouritesCheckBox!!.isChecked = it != null

        })

    }

    private fun setFavouriteOnClickListener(movie: MovieDetailsResponse) {
        val addToFavoritesCheckBox = binding?.addToFavouritesCheckBox
        addToFavoritesCheckBox!!.setOnClickListener {

            if (addToFavoritesCheckBox.isChecked) {
                detailsViewModel!!.insertFavouriteMovie(movie)
                Toast.makeText(context, movie.original_title + " dodan u listu favorita.", Toast.LENGTH_LONG).show();
            } else {
                detailsViewModel!!.deleteFavouriteMovie(movie)
                Toast.makeText(context, movie.original_title + " maknut iz liste favorita.", Toast.LENGTH_LONG).show();
            }

        }

    }


    override fun onItemClick(view: View, actorId: Int, position: Int) {
        sharedViewModel!!.changeToActorFragment(position, actorId)
    }

    override fun onGenreItemClick(genreId: Int, position: Int) {
        Log.d("nikola", "onGenreItemClick genre Item clicked: " + genreId + " ,position: " + position)
        sharedViewModel.changeToMoviesByGenreListFragmentFromMovieDetailsFragment(genreId, position)
    }

}