package com.aero51.moviedatabase.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.aero51.moviedatabase.databinding.FragmentPeopleSearchBinding
import com.aero51.moviedatabase.ui.adapter.PeopleSearchPagedListAdapter
import com.aero51.moviedatabase.ui.listeners.ActorClickListener
import com.aero51.moviedatabase.viewmodel.SearchViewModel
import com.aero51.moviedatabase.viewmodel.SharedViewModel

class PeopleSearchFragment : Fragment(), ActorClickListener {
    private var binding: FragmentPeopleSearchBinding? = null
    private var searchViewModel: SearchViewModel? = null
    private var peopleAdapter: PeopleSearchPagedListAdapter? = null
    private lateinit var sharedViewModel: SharedViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchViewModel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentPeopleSearchBinding.inflate(inflater, container, false)
        binding!!.peopleRecyclerView.setHasFixedSize(true)
        peopleAdapter = PeopleSearchPagedListAdapter(this)
        binding!!.peopleRecyclerView.adapter = peopleAdapter
        binding!!.peopleRecyclerView.layoutManager = GridLayoutManager(context, 3)
        registerPeopleSearchObserver()
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun registerPeopleSearchObserver() {
        searchViewModel!!.peopleSearchResult.observe(viewLifecycleOwner, { actorSearches -> peopleAdapter!!.submitList(actorSearches) })
    }

    override fun onActorItemClick(actorId: Int, position: Int) {
        Log.d("nikola","position: "+position+" ,actorId: "+actorId)
        sharedViewModel.changeToMoviesAndTvShowsActorFragment(actorId)
    }


}