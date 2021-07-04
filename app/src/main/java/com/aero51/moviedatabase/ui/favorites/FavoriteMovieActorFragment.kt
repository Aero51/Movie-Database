package com.aero51.moviedatabase.ui.favorites

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aero51.moviedatabase.R
import com.aero51.moviedatabase.databinding.FragmentActorBinding
import com.aero51.moviedatabase.ui.adapter.MoviesWithPersonCastAdapter
import com.aero51.moviedatabase.ui.adapter.SliderImageAdapter
import com.aero51.moviedatabase.ui.listeners.MediaClickListener
import com.aero51.moviedatabase.utils.Constants
import com.aero51.moviedatabase.utils.DateHelper
import com.aero51.moviedatabase.utils.Status
import com.aero51.moviedatabase.viewmodel.DetailsViewModel
import com.aero51.moviedatabase.viewmodel.SharedViewModel
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import com.squareup.picasso.Picasso

class FavoriteMovieActorFragment : Fragment(),
    MediaClickListener {
    private var viewModel: DetailsViewModel? = null
    private var sharedViewModel: SharedViewModel? = null
    private var binding: FragmentActorBinding? = null
    private var adapter: SliderImageAdapter? = null
    private var actorName: String? = null
    private var moviesWithPersonCastAdapter: MoviesWithPersonCastAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(DetailsViewModel::class.java)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentActorBinding.inflate(inflater, container, false)
        adapter = SliderImageAdapter(context)
        binding!!.imageSlider.setSliderAdapter(adapter!!)
        binding!!.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM) //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        binding!!.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        binding!!.imageSlider.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        binding!!.imageSlider.indicatorSelectedColor = Color.WHITE
        binding!!.imageSlider.indicatorUnselectedColor = Color.GRAY
        binding!!.imageSlider.scrollTimeInSec = 3
        binding!!.imageSlider.isAutoCycle = true
        binding!!.imageSlider.startAutoCycle()
        binding!!.starredInRecyclerViewHorizontal.setHasFixedSize(true)
        binding!!.starredInRecyclerViewHorizontal.isNestedScrollingEnabled = false
        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding!!.starredInRecyclerViewHorizontal.layoutManager = linearLayoutManager
        moviesWithPersonCastAdapter = MoviesWithPersonCastAdapter(this)
        binding!!.starredInRecyclerViewHorizontal.adapter = moviesWithPersonCastAdapter
        sharedViewModel!!.liveDataFavoriteMovieActorId.observe(
            viewLifecycleOwner,
            { actorId -> registerActorObservers(actorId) })
        val toolbar = requireActivity().findViewById<View>(R.id.toolbar) as Toolbar
        //toolbar.setTitle("text");
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
            //showBackButton(true);
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

    private fun registerActorObservers(actorId: Int) {
        viewModel!!.getActorDetails(actorId).observe(viewLifecycleOwner, { (status, actor) ->
            if (status === Status.SUCCESS) {
                actorName = actor!!.name
                binding!!.textViewActorName.text = actorName
                binding!!.textViewActorBirthday.text = actor.birthday?.let {
                    DateHelper.formatDateStringToDefaultLocale(
                        it,
                        "yyyy-MM-dd",
                        "dd MMMM yyyy"
                    )
                }
                binding!!.textViewActorPlaceOfBirth.text = actor.place_of_birth
                binding!!.textViewActorHomepage.text = actor.homepage
                binding!!.textViewBiography.text = actor.biography
                val imageUrl: String = Constants.BASE_IMAGE_URL + Constants.PROFILE_SIZE_W185 + actor.profile_path
                Picasso.get().load(imageUrl).fit().centerCrop().into(binding!!.posterImageView)
            }
        })
        viewModel!!.getActorImages(actorId).observe(viewLifecycleOwner, { (_, data) ->
            if (data != null) {
                //ActorImagesAdapter adapter = new ActorImagesAdapter(getContext(), listResource.getData());
                //binding.actorImagesRecyclerView.setAdapter(adapter);
                adapter!!.renewItems(data)
            }
        })
        viewModel!!.getMoviesWithPerson(actorId).observe(viewLifecycleOwner, { (_, data) ->
            if (data != null) {
                moviesWithPersonCastAdapter!!.setList(data.cast)
            }
        })
    }


    override fun onMediaItemClick(movie: Any?, position: Int) {
        sharedViewModel?.changeToFavoriteMovieDetailsFragmentFromMovieActorFragment(movie)
    }
}