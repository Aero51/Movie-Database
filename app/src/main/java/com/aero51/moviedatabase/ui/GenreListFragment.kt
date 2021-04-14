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
import com.aero51.moviedatabase.ui.adapter.GenrePagedListAdapter
import com.aero51.moviedatabase.utils.Constants.LOG2
import com.aero51.moviedatabase.utils.MovieClickListener
import com.aero51.moviedatabase.viewmodel.SharedViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior
import com.google.android.material.bottomnavigation.BottomNavigationView

class GenreListFragment : Fragment(),MovieClickListener {

    private lateinit var sharedViewModel: SharedViewModel
    private var binding: FragmentGenreListBinding? = null
    private lateinit var genreAdapter: GenrePagedListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentGenreListBinding.inflate(inflater, container, false)
        binding!!.genreListRecyclerView.setHasFixedSize(true)
        genreAdapter = GenrePagedListAdapter(this)
        binding!!.genreListRecyclerView.adapter = genreAdapter
        binding!!.genreListRecyclerView.layoutManager = GridLayoutManager(context, 3)

        showBackButton(true)
        showToolbar(true)
        showBottomNavigation(true)

        registerSharedViewModelObserver()

        return binding!!.root
    }


    private fun registerSharedViewModelObserver() {
        sharedViewModel.liveDataGenreId.observe(viewLifecycleOwner, Observer { genreId ->
                   Log.d(LOG2,"GenreListFragment  genreId: "+genreId)
        })
    }


    override fun onObjectItemClick(movie: Any?, position: Int) {
        TODO("Not yet implemented")
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

}