package com.aero51.moviedatabase.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.aero51.moviedatabase.databinding.FragmentTvShowsBinding
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowGenresResponse.TvShowGenre
import com.aero51.moviedatabase.ui.adapter.AiringTvShowsPagedListAdapter
import com.aero51.moviedatabase.ui.adapter.PopularTvShowsPagedListAdapter
import com.aero51.moviedatabase.ui.adapter.TrendingTvShowsPagedListAdapter
import com.aero51.moviedatabase.ui.adapter.TvShowGenresAdapter
import com.aero51.moviedatabase.utils.*
import com.aero51.moviedatabase.viewmodel.SharedViewModel
import com.aero51.moviedatabase.viewmodel.TvShowsViewModel

class TvShowsFragment : Fragment(), ObjectClickListener, GenreObjectClickListener {
    private var binding: FragmentTvShowsBinding? = null
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var tvShowsViewModel: TvShowsViewModel
    private var popularAdapter: PopularTvShowsPagedListAdapter? = null
    private var airingAdapter: AiringTvShowsPagedListAdapter? = null
    private var trendingAdapter: TrendingTvShowsPagedListAdapter? = null
    private var hasEpgTvFragmentFinishedLoading: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvShowsViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(TvShowsViewModel::class.java)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentTvShowsBinding.inflate(inflater, container, false)
        binding!!.textViewPopularTvShow.text = "Popular tv shows:"
        binding!!.textViewAiringTvShow.text = "Airing tv shows:"
        binding!!.textViewTrendingTvShow.text = "trending tv shows:"
        binding!!.popularTvShowsRecyclerViewHorizontal.setHasFixedSize(true)
        popularAdapter = PopularTvShowsPagedListAdapter(this)
        binding!!.popularTvShowsRecyclerViewHorizontal.adapter = popularAdapter
        val popularlinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding!!.popularTvShowsRecyclerViewHorizontal.layoutManager = popularlinearLayoutManager
        binding!!.popularTvShowsRecyclerViewHorizontal.isNestedScrollingEnabled = false
        binding!!.airingTvShowsRecyclerViewHorizontal.setHasFixedSize(true)
        airingAdapter = AiringTvShowsPagedListAdapter(this)
        binding!!.airingTvShowsRecyclerViewHorizontal.adapter = airingAdapter
        val airinglinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding!!.airingTvShowsRecyclerViewHorizontal.layoutManager = airinglinearLayoutManager
        binding!!.airingTvShowsRecyclerViewHorizontal.isNestedScrollingEnabled = false
        binding!!.trendingTvShowsRecyclerViewHorizontal.setHasFixedSize(true)
        trendingAdapter = TrendingTvShowsPagedListAdapter(this)
        binding!!.trendingTvShowsRecyclerViewHorizontal.adapter = trendingAdapter
        val trendinglinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding!!.trendingTvShowsRecyclerViewHorizontal.layoutManager = trendinglinearLayoutManager
        binding!!.trendingTvShowsRecyclerViewHorizontal.isNestedScrollingEnabled = false
        binding!!.tvShowGenresRecyclerViewHorizontal.setHasFixedSize(true)
        binding!!.tvShowGenresRecyclerViewHorizontal.isNestedScrollingEnabled = false
        val genreslinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding!!.tvShowGenresRecyclerViewHorizontal.layoutManager = genreslinearLayoutManager

        Log.d(Constants.LOG2, "hasEpgTvFragmentFinishedLoading: $hasEpgTvFragmentFinishedLoading")
        if(hasEpgTvFragmentFinishedLoading){
            registerObservers()
        }else{
            registerHasEpgTvFragmentFinishedLoadingObserver()
        }

        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun registerHasEpgTvFragmentFinishedLoadingObserver() {
        sharedViewModel!!.hasEpgTvFragmentFinishedLoading.observe(viewLifecycleOwner, Observer { aBoolean -> //Log.d(Constants.LOG, "getHasEpgTvFragmentFinishedLoading: " + aBoolean);
            hasEpgTvFragmentFinishedLoading = aBoolean
            if (hasEpgTvFragmentFinishedLoading) {
                registerObservers()
            }
        })
    }


    private fun registerObservers() {

        registerPopularTvShowsObservers()
        registerTrendingTvShowsObservers()
        registerTvShowGenresObservers()
        registerAiringTvShowsObservers()
    }

    private fun registerPopularTvShowsObservers() {
        tvShowsViewModel.popularShowsDataValidationCheck()
        tvShowsViewModel!!.popularLiveTvShowPage.observe(viewLifecycleOwner, Observer { popularTvShowsPage ->
            val page_number: Int
            page_number = if (popularTvShowsPage == null) {
                0
            } else {
                popularTvShowsPage.page
            }
            Log.d(Constants.LOG, "popular Tv shows Fragment onChanged popular tv show page: $page_number")
        })
        tvShowsViewModel!!.tvPopularResultsPagedList!!.observe(viewLifecycleOwner) { popularMovies -> //Log.d(Constants.LOG, "popular Tv shows Fragment  onChanged list size: " + popularMovies.size());
            popularAdapter!!.submitList(popularMovies)
        }
    }

    private fun registerAiringTvShowsObservers() {
        tvShowsViewModel.airingShowsDataValidationCheck()
        tvShowsViewModel!!.tvAiringResultsPagedList!!.observe(viewLifecycleOwner) { airingTvShows -> airingAdapter!!.submitList(airingTvShows) }
        tvShowsViewModel!!.airingLiveTvShowPage.observe(viewLifecycleOwner, Observer { airingTvShowsPage ->
            val page_number: Int
            page_number = if (airingTvShowsPage == null) {
                0
            } else {
                airingTvShowsPage.page
            }
            Log.d(Constants.LOG, "airing Tv shows Fragment onChanged tv_page: $page_number")
        })
    }

    private fun registerTrendingTvShowsObservers() {
        tvShowsViewModel.trendingShowsDataValidationCheck()
        tvShowsViewModel?.tvTrendingResultsPagedList!!.observe(viewLifecycleOwner) { trendingTvShows -> trendingAdapter!!.submitList(trendingTvShows) }
        tvShowsViewModel!!.trendingLiveTvShowPage.observe(viewLifecycleOwner, Observer { trendingTvShowsPage ->
            val page_number: Int
            page_number = if (trendingTvShowsPage == null) {
                0
            } else {
                trendingTvShowsPage.page
            }
            Log.d(Constants.LOG, "trending Tv shows Fragment onChanged tv_page: $page_number")
        })
    }

    private fun registerTvShowGenresObservers() {
        tvShowsViewModel!!.tvShowsGenres.observe(viewLifecycleOwner, Observer<Resource<List<TvShowGenre?>>> { (status, data) ->
            if (status === Status.SUCCESS) {
                Log.d(Constants.LOG, "TvShowGenresObserver list size  " + data!!.size)
                val tvShowGenresAdapter = TvShowGenresAdapter(data, this)
                binding!!.tvShowGenresRecyclerViewHorizontal.adapter = tvShowGenresAdapter
            }
        })
    }

    override fun onObjectItemClick(tvShow: Any, position: Int) {
        sharedViewModel.changeToTvShowDetailsFragment(tvShow,position)
        Log.d(Constants.LOG, "TvShowsFragment onObjectItemClick position: $position")
    }

    override fun onGenreItemClick(genreId: Int, position: Int) {
        Log.d(Constants.LOG, " TvShowsFragment onGenreItemClick position: $position")
        sharedViewModel.changeToTvShowsByGenreListFragment(genreId, position)
    }
}