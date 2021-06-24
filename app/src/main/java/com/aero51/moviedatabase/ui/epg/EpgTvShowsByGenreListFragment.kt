package com.aero51.moviedatabase.ui.epg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.aero51.moviedatabase.R
import com.aero51.moviedatabase.databinding.FragmentGenreListBinding
import com.aero51.moviedatabase.ui.adapter.TvShowsByGenrePagedListAdapter
import com.aero51.moviedatabase.ui.listeners.MediaClickListener
import com.aero51.moviedatabase.viewmodel.SharedViewModel
import com.aero51.moviedatabase.viewmodel.TvShowsViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior
import com.google.android.material.bottomnavigation.BottomNavigationView

class EpgTvShowsByGenreListFragment : Fragment(),
    MediaClickListener {

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var tvShowsViewModel: TvShowsViewModel

    private var binding: FragmentGenreListBinding? = null
    private lateinit var tvShowsByGenreAdapter: TvShowsByGenrePagedListAdapter
    private var genreId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        tvShowsViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(TvShowsViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGenreListBinding.inflate(inflater, container, false)
        binding!!.genreListRecyclerView.setHasFixedSize(true)
        tvShowsByGenreAdapter = TvShowsByGenrePagedListAdapter(this)
        binding!!.genreListRecyclerView.adapter = tvShowsByGenreAdapter
        binding!!.genreListRecyclerView.layoutManager = GridLayoutManager(context, 2)
        binding!!.genreListRecyclerView.itemAnimator = null


        binding!!.pullToRefresh.setOnRefreshListener {
            //refreshData(); // your code
            binding!!.pullToRefresh.isRefreshing = false
            tvShowsViewModel.tvShowsByGenreDataValidationCheck(genreId)
        }
        /*
        val toolbar = requireActivity().findViewById<View>(R.id.toolbar) as Toolbar
        //toolbar.setTitle("text");
        //toolbar.setTitle("text");
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
            showBackButton(false)
        }
*/

/*
        showToolbar(true)
        showBackButton(true)
        showBottomNavigation(true)
*/
        registerSharedViewModelObserver()

        return binding!!.root
    }


    private fun registerSharedViewModelObserver() {
        binding?.progressBar?.setVisibility(View.VISIBLE)
        sharedViewModel.liveDataEpgGenreId.observe(viewLifecycleOwner, Observer { genreId ->
            this.genreId = genreId
            tvShowsViewModel.tvShowsByGenreDataValidationCheck(genreId)
            registerTvShowsByGenrePagedListObserver(genreId)
            registerTvShowsByGenrePage()

        })
    }

    private fun registerTvShowsByGenrePagedListObserver(genreId: Int) {
        tvShowsViewModel.getTvShowsByGenre(genreId)
            ?.observe(viewLifecycleOwner, Observer { pagedList ->
                binding?.progressBar?.setVisibility(View.GONE)
                tvShowsByGenreAdapter.submitList(pagedList)
            })
    }

    private fun registerTvShowsByGenrePage() {
        tvShowsViewModel.tvShowsByGenrePage.observe(
            viewLifecycleOwner,
            Observer { moviesByGenrePage ->
                val page_number: Int
                page_number = if (moviesByGenrePage == null) {
                    0
                } else {
                    moviesByGenrePage.page
                }
            })
    }



    fun showBackButton(show: Boolean) {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(show)
        }
    }

    private fun showToolbar(isShown: Boolean) {
        val appBarLayout: AppBarLayout = requireActivity().findViewById(R.id.app_bar)
        appBarLayout.setExpanded(isShown, true)
    }

    private fun showBottomNavigation(isShown: Boolean) {
        val bottomNavigationView =
            requireActivity().findViewById<View>(R.id.bottom_navigation) as BottomNavigationView
        val layoutParams = bottomNavigationView.layoutParams
        if (layoutParams is CoordinatorLayout.LayoutParams) {
            val behavior = layoutParams.behavior
            if (behavior is HideBottomViewOnScrollBehavior<*>) {
                val hideShowBehavior =
                    behavior as HideBottomViewOnScrollBehavior<BottomNavigationView>
                if (isShown) {
                    hideShowBehavior.slideUp(bottomNavigationView)
                } else {
                    hideShowBehavior.slideDown(bottomNavigationView)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onMediaItemClick(movie: Any?, position: Int) {
        sharedViewModel.changeToTvShowDetailsFragment(movie)
    }

}