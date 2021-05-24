package com.aero51.moviedatabase.ui

import android.os.Bundle
import android.util.Log
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
import com.aero51.moviedatabase.ui.adapter.MoviesByGenrePagedListAdapter
import com.aero51.moviedatabase.utils.Constants
import com.aero51.moviedatabase.utils.Constants.LOG2
import com.aero51.moviedatabase.utils.ObjectClickListener
import com.aero51.moviedatabase.viewmodel.MoviesViewModel
import com.aero51.moviedatabase.viewmodel.SharedViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior
import com.google.android.material.bottomnavigation.BottomNavigationView

class MoviesByGenreListFragment : Fragment(), ObjectClickListener {

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var moviesViewModel: MoviesViewModel

    private var binding: FragmentGenreListBinding? = null
    private lateinit var moviesByGenreAdapter: MoviesByGenrePagedListAdapter
    private var genreId: Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        moviesViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(MoviesViewModel::class.java)

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentGenreListBinding.inflate(inflater, container, false)
        binding!!.genreListRecyclerView.setHasFixedSize(true)
        moviesByGenreAdapter = MoviesByGenrePagedListAdapter(this)
        binding!!.genreListRecyclerView.adapter = moviesByGenreAdapter
        binding!!.genreListRecyclerView.layoutManager = GridLayoutManager(context, 2)
        binding!!.genreListRecyclerView.itemAnimator=null


        binding!!.pullToRefresh.setOnRefreshListener {
            //refreshData(); // your code
            binding!!.pullToRefresh.isRefreshing = false
            moviesViewModel.moviesByGenreDataValidationCheck(genreId)
        }
        /*
        val toolbar = requireActivity().findViewById<View>(R.id.toolbar) as Toolbar
        //toolbar.setTitle("text");
        //toolbar.setTitle("text");
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
            Log.d(Constants.LOG, "Toolbar clicked!")
            showBackButton(false)
        }
*/


        //showToolbar(true)
        //showBackButton(true)
        //showBottomNavigation(true)

        registerSharedViewModelObserver()

        return binding!!.root
    }


    private fun registerSharedViewModelObserver() {
        binding?.progressBar?.setVisibility(View.VISIBLE)
        sharedViewModel.liveDataGenreId.observe(viewLifecycleOwner, Observer { genreId ->
            this.genreId=genreId
            Log.d(LOG2,"GenreListFragment  genreId: "+genreId)
            moviesViewModel.moviesByGenreDataValidationCheck(genreId)
            registerMoviesByGenrePagedListObserver(genreId)
            registerMoviesByGenrePage()

        })
    }
    private fun registerMoviesByGenrePagedListObserver(genreId: Int) {
        moviesViewModel.getMoviesByGenre(genreId)?.observe(viewLifecycleOwner, Observer { pagedList ->
            binding?.progressBar?.setVisibility(View.GONE)
            moviesByGenreAdapter.submitList(pagedList)
            Log.d(LOG2,"registerMoviesByGenrePagedListObserver list size: "+pagedList.size)
        })
    }

    private fun registerMoviesByGenrePage(){
       moviesViewModel.moviesByGenrePage.observe(viewLifecycleOwner, Observer {moviesByGenrePage ->
           val page_number: Int
           page_number = if (moviesByGenrePage == null) {
               0
           } else {
               moviesByGenrePage.page
           }
           Log.d(Constants.LOG, "GenreListFragment onChanged page: $page_number")
       })
    }


    override fun onObjectItemClick(movie: Any?, position: Int) {
        sharedViewModel.changeToMoviedetailsFragment(movie, position)
    }

    private fun showBackButton(show: Boolean) {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(show)
        }
    }
    private fun showToolbar(isShown: Boolean) {
        val appBarLayout: AppBarLayout = requireActivity().findViewById(R.id.app_bar)
        appBarLayout.setExpanded(isShown, true)
    }

    private fun showBottomNavigation(isShown: Boolean) {
        val bottomNavigationView = requireActivity().findViewById<View>(R.id.bottom_navigation) as BottomNavigationView
        val layoutParams = bottomNavigationView.layoutParams
        if (layoutParams is CoordinatorLayout.LayoutParams) {
            val behavior = layoutParams.behavior
            if (behavior is HideBottomViewOnScrollBehavior<*>) {
                val hideShowBehavior = behavior as HideBottomViewOnScrollBehavior<BottomNavigationView>
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

}