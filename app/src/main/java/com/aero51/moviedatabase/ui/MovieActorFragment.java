package com.aero51.moviedatabase.ui;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.databinding.FragmentActorBinding;
import com.aero51.moviedatabase.repository.model.tmdb.credits.Actor;
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorImagesResponse;
import com.aero51.moviedatabase.repository.model.tmdb.credits.MoviesWithPerson;
import com.aero51.moviedatabase.ui.adapter.MediaWithPersonAdapter;
import com.aero51.moviedatabase.ui.adapter.SliderImageAdapter;
import com.aero51.moviedatabase.utils.Constants;
import com.aero51.moviedatabase.utils.DateHelper;
import com.aero51.moviedatabase.utils.Resource;
import com.aero51.moviedatabase.utils.Status;
import com.aero51.moviedatabase.viewmodel.DetailsViewModel;
import com.aero51.moviedatabase.viewmodel.SharedViewModel;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.aero51.moviedatabase.utils.Constants.BASE_IMAGE_URL;
import static com.aero51.moviedatabase.utils.Constants.PROFILE_SIZE_H632;
import static com.aero51.moviedatabase.utils.Constants.PROFILE_SIZE_W185;

public class MovieActorFragment extends Fragment {
    private DetailsViewModel viewModel;
    private SharedViewModel sharedViewModel;
    private FragmentActorBinding binding;
    private SliderImageAdapter adapter;
    private String actorName;
    private MediaWithPersonAdapter mediaWithPersonAdapter;
    public MovieActorFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(DetailsViewModel.class);
        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentActorBinding.inflate(inflater, container, false);


        adapter = new SliderImageAdapter(getContext());
        binding.imageSlider.setSliderAdapter(adapter);
        binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        binding.imageSlider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        binding.imageSlider.setIndicatorSelectedColor(Color.WHITE);
        binding.imageSlider.setIndicatorUnselectedColor(Color.GRAY);
        binding.imageSlider.setScrollTimeInSec(3);
        binding.imageSlider.setAutoCycle(true);
        binding.imageSlider.startAutoCycle();

        binding.starredInRecyclerViewHorizontal.setHasFixedSize(true);
        binding.starredInRecyclerViewHorizontal.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        binding.starredInRecyclerViewHorizontal.setLayoutManager(linearLayoutManager);
        mediaWithPersonAdapter=new MediaWithPersonAdapter();
        binding.starredInRecyclerViewHorizontal.setAdapter(mediaWithPersonAdapter);

        sharedViewModel.getLiveDataActorId().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer actorId) {
                registerActorObservers(actorId);
            }
        });
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        //toolbar.setTitle("text");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
                Log.d(Constants.LOG, "Toolbar clicked!");
                showBackButton(true);
            }
        });
        showBackButton(true);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }

    public void showBackButton(boolean show) {
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(show);
        }
    }


    private void registerActorObservers(Integer actorId) {
        Log.d(Constants.LOG, "registerActorObservers cast: " + actorId);
        viewModel.getActorDetails(actorId).observe(getViewLifecycleOwner(), new Observer<Resource<Actor>>() {
            @Override
            public void onChanged(Resource<Actor> actorResource) {
                Log.d(Constants.LOG, " status: " + actorResource.getStatus() + " list size: " + " ,message: " + actorResource.getMessage());
                if (actorResource.getStatus() == Status.SUCCESS) {
                    Actor actor = actorResource.getData();
                    actorName=actor.getName();
                    binding.textViewActorName.setText(actorName);
                    binding.textViewActorBirthday.setText(DateHelper.Companion.formatDateStringToDefaultLocale(actor.getBirthday(), "yyyy-MM-dd", "dd MMMM yyyy"));
                    binding.textViewActorPlaceOfBirth.setText(actor.getPlace_of_birth());
                    binding.textViewActorHomepage.setText(actor.getHomepage());
                    binding.textViewImdb.setText(String.valueOf(actor.getId()));
                    binding.textViewBiography.setText(actor.getBiography());

                    String  imageUrl = BASE_IMAGE_URL + PROFILE_SIZE_W185 + actor.getProfile_path();
                    Picasso.get().load(imageUrl).fit().centerCrop().into(binding.posterImageView);
                }

            }
        });

        viewModel.getActorImages(actorId).observe(getViewLifecycleOwner(), new Observer<Resource<List<ActorImagesResponse.ActorImage>>>() {
            @Override
            public void onChanged(Resource<List<ActorImagesResponse.ActorImage>> listResource) {
                if (listResource.getData() != null) {
                    Log.d(Constants.LOG, " status: " + listResource.getStatus() + " list size: " + listResource.getData().size() + " ,message: " + listResource.getMessage());
                    //ActorImagesAdapter adapter = new ActorImagesAdapter(getContext(), listResource.getData());
                    //binding.actorImagesRecyclerView.setAdapter(adapter);
                    adapter.renewItems(listResource.getData());
                }
            }
        });
        viewModel.getMoviesWithPerson(actorId).observe(getViewLifecycleOwner(), new Observer<Resource<MoviesWithPerson>>() {
            @Override
            public void onChanged(Resource<MoviesWithPerson> moviesWithPersonResource) {
                if(moviesWithPersonResource.getData()!=null) {
                   mediaWithPersonAdapter.setList(moviesWithPersonResource.component2().getCast());
                    Log.d("nikola", " list size: " + moviesWithPersonResource.getData().component2().size());
                }
            }
        });
    }
}