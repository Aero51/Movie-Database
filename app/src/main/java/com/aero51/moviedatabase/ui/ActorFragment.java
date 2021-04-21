package com.aero51.moviedatabase.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.databinding.FragmentActorBinding;
import com.aero51.moviedatabase.repository.model.tmdb.credits.Actor;
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorImagesResponse;
import com.aero51.moviedatabase.ui.adapter.ActorImagesAdapter;
import com.aero51.moviedatabase.utils.Constants;
import com.aero51.moviedatabase.utils.Resource;
import com.aero51.moviedatabase.utils.Status;
import com.aero51.moviedatabase.viewmodel.DetailsViewModel;
import com.aero51.moviedatabase.viewmodel.SharedViewModel;

import java.util.List;

public class ActorFragment extends Fragment {
    private DetailsViewModel viewModel;
    private SharedViewModel sharedViewModel;
    private FragmentActorBinding binding;

    public ActorFragment() {
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

        binding.actorImagesRecyclerView.setHasFixedSize(true);
        binding.actorImagesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

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
                    binding.textViewActorName.setText(actor.getName());
                    binding.textViewActorBirthday.setText(actor.getBirthday());
                    binding.textViewActorPlaceOfBirth.setText(actor.getPlace_of_birth());
                    binding.textViewActorHomepage.setText(actor.getHomepage());
                    binding.textViewImdb.setText(actor.getImdb_id());
                    binding.textViewBiography.setText(actor.getBiography());
                }

            }
        });

        viewModel.getActorImages(actorId).observe(getViewLifecycleOwner(), new Observer<Resource<List<ActorImagesResponse.ActorImage>>>() {
            @Override
            public void onChanged(Resource<List<ActorImagesResponse.ActorImage>> listResource) {
                if (listResource.getData() != null) {
                    Log.d(Constants.LOG, " status: " + listResource.getStatus() + " list size: " + listResource.getData().size() + " ,message: " + listResource.getMessage());
                    ActorImagesAdapter adapter = new ActorImagesAdapter(getContext(), listResource.getData());
                    binding.actorImagesRecyclerView.setAdapter(adapter);
                }
            }
        });


    }
}