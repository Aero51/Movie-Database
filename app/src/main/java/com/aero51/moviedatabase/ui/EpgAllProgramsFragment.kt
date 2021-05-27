package com.aero51.moviedatabase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aero51.moviedatabase.R
import com.aero51.moviedatabase.databinding.FragmentEpgAllProgramsBinding
import com.aero51.moviedatabase.ui.adapter.EpgAllProgramsAdapter
import com.aero51.moviedatabase.viewmodel.SharedViewModel

class EpgAllProgramsFragment : Fragment() {
    private var binding: FragmentEpgAllProgramsBinding? = null
    private var sharedViewModel: SharedViewModel? = null
    private val recycler_view_all_programs: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentEpgAllProgramsBinding.inflate(inflater, container, false)
        binding!!.allProgramsRecyclerView.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(context)
        binding!!.allProgramsRecyclerView.layoutManager = linearLayoutManager
        binding!!.allProgramsRecyclerView.addItemDecoration(DividerItemDecoration(context, linearLayoutManager.orientation))
        sharedViewModel!!.liveDataChannelWithPrograms.observe(viewLifecycleOwner, { channelWithPrograms ->
            val adapter = EpgAllProgramsAdapter(channelWithPrograms)
            binding!!.allProgramsRecyclerView.adapter = adapter
            binding!!.allProgramsRecyclerView.scrollToPosition(channelWithPrograms.nearestTimePosition!!)
        })
        val toolbar = requireActivity().findViewById<View>(R.id.toolbar) as Toolbar
        //toolbar.setTitle("text");
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
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
}