package com.aero51.moviedatabase.ui

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
import com.aero51.moviedatabase.utils.ObjectClickListener
import com.aero51.moviedatabase.viewmodel.SearchViewModel

class PeopleSearchFragment : Fragment(), ObjectClickListener {
    private var binding: FragmentPeopleSearchBinding? = null
    private var searchViewModel: SearchViewModel? = null
    private var peopleAdapter: PeopleSearchPagedListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchViewModel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
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

    override fun onObjectItemClick(actor: Any?, position: Int) {
        Log.d("nikola","position: "+position)

    }
}