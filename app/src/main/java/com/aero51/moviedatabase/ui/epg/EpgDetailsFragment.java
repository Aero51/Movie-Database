package com.aero51.moviedatabase.ui.epg;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aero51.moviedatabase.BuildConfig;
import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.databinding.FragmentEpgDetailsBinding;
import com.aero51.moviedatabase.repository.model.epg.EpgProgram;
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorSearchResponse;
import com.aero51.moviedatabase.ui.adapter.ActorSearchAdapter;
import com.aero51.moviedatabase.utils.EndlessRecyclerViewScrollListener;
import com.aero51.moviedatabase.utils.Resource;
import com.aero51.moviedatabase.utils.Status;
import com.aero51.moviedatabase.utils.StringHelper;
import com.aero51.moviedatabase.viewmodel.EpgDetailsViewModel;
import com.aero51.moviedatabase.viewmodel.SharedViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class EpgDetailsFragment extends Fragment implements ActorSearchAdapter.ItemClickListener {

    private FragmentEpgDetailsBinding binding;
    private SharedViewModel sharedViewModel;
    private EpgDetailsViewModel epgDetailsViewModel;
    private ActorSearchAdapter actorSearchAdapter;
    private List<ActorSearchResponse.ActorSearch> actorSearchList;
    private MutableLiveData<Boolean> isLoading;
    private EndlessRecyclerViewScrollListener scrollListener;
    private List<String> actors;
    private List<String> writers;
    private List<String> directors;

    public EpgDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        epgDetailsViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(EpgDetailsViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEpgDetailsBinding.inflate(inflater, container, false);


        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        //toolbar.setTitle("text");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
                //showBackButton(false);
            }
        });

        showBackButton(true);
        showToolbar(true);
        showBottomNavigation(true);


        //actorSearchRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.actorSearchRecyclerView.setLayoutManager(linearLayoutManager);

        actorSearchList = new ArrayList<>();
        actorSearchAdapter = new ActorSearchAdapter(actorSearchList, EpgDetailsFragment.this::onItemClick);
        binding.actorSearchRecyclerView.setAdapter(actorSearchAdapter);

        scrollListener = new EndlessRecyclerViewScrollListener(5, linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                multipleActorsFetch();
            }
        };
        binding.actorSearchRecyclerView.addOnScrollListener(scrollListener);

        sharedViewModel.getLiveDataProgram().observe(getViewLifecycleOwner(), new Observer<EpgProgram>() {
            @Override
            public void onChanged(EpgProgram epgProgram) {
                // sharedViewModel.getLiveDataProgram().removeObserver(this);
                extractJsonCredits(epgProgram.getCredits());
                List<String> titlesList = extractJsonTitles(epgProgram.getTitle());
                binding.textViewTitlePrimary.setText(titlesList.get(0));
                if (titlesList.size() > 1) {
                    binding.textViewTitleSecondary.setText(titlesList.get(1));
                } else {
                    binding.textViewTitleSecondary.setText("");
                }
                if (epgProgram.getDate() == null) epgProgram.setDate("");
                binding.textViewDate.setText(epgProgram.getDate());
                binding.textViewDescription.setText(epgProgram.getDesc());
                //TODO   implement and test this:

                /*
                if(epgProgram.getDesc().equals("Nema informačia.")){
                    binding.textViewDescription.setText("Nema informacija.");
                }else{ binding.textViewDescription.setText(epgProgram.getDesc());}
                */
                //.placeholder(R.drawable.picture_template)
                if (epgProgram.getIcon().contains("epg.bnet.hr/images/")) {
                    binding.imageViewProgram.setImageResource(R.drawable.no_photo);
                } else {
                    Picasso.get().load(epgProgram.getIcon()).fit().centerCrop().into(binding.imageViewProgram, new Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError(Exception e) {
                            if (binding != null) {
                                binding.imageViewProgram.setImageResource(R.drawable.no_photo);
                            }
                        }
                    });
                }
                Uri picture_path = Uri.parse("android.resource://" + BuildConfig.APPLICATION_ID + "/drawable/" + epgProgram.getChannel());
                Picasso.get().load(picture_path).placeholder(R.drawable.picture_template).into(binding.imageViewChannel);

                if(directors.size()>0){
                    binding.textViewDirectors.setText(getResources().getString(R.string.director) + StringHelper.Companion.joinStrings(", ", directors));
                }
                if(writers.size()>0) {
                    binding.textViewWriters.setText(getResources().getString(R.string.writer) + StringHelper.Companion.joinStrings(", ", writers));
                }
                multipleActorsFetch();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private List<String> extractJsonTitles(String titles) {

        List<String> titlesList = new ArrayList<>();
        if (titles != null) {
            try {
                JSONObject jsonObjTitles = new JSONObject(titles);
                JSONArray ja_titles = jsonObjTitles.getJSONArray("Titles");

                for (int i = 0; i < ja_titles.length(); i++) {
                    titlesList.add(ja_titles.getString(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return titlesList;
    }

    private void extractJsonCredits(String credits) {

        writers = new ArrayList<>();
        directors = new ArrayList<>();
        actors = new ArrayList<>();
        if (credits != null) {
            try {
                JSONObject jsonObjCredits = new JSONObject(credits);
                JSONArray ja_writers = jsonObjCredits.getJSONArray("Writers");

                for (int i = 0; i < ja_writers.length(); i++) {
                    writers.add(ja_writers.getString(i));
                }

                JSONArray ja_directors = jsonObjCredits.getJSONArray("Directors");
                for (int i = 0; i < ja_directors.length(); i++) {
                    directors.add(ja_directors.getString(i));
                }

                JSONArray ja_actors = jsonObjCredits.getJSONArray("Actors");
                for (int i = 0; i < ja_actors.length(); i++) {
                    actors.add(ja_actors.getString(i));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void multipleActorsFetch() {
        isLoading = new MutableLiveData<>();
        isLoading.setValue(false);

        binding.actorSearchRecyclerView.removeOnScrollListener(scrollListener);

        int temp = actorSearchAdapter.getItemCount();
        isLoading.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loading) {
                if (!loading) {
                    int adapterItemCount = actorSearchAdapter.getItemCount();
                    if (adapterItemCount < temp + 5 && actors.size() > adapterItemCount) {
                        registerGetActor(actors.get(adapterItemCount));
                    } else {
                        binding.actorSearchRecyclerView.addOnScrollListener(scrollListener);
                        isLoading.removeObserver(this);
                    }
                }
            }
        });

    }

    private void registerGetActor(String actor_name) {
        isLoading.setValue(true);
        epgDetailsViewModel.getActorSearch(actor_name).observe(getViewLifecycleOwner(), new Observer<Resource<ActorSearchResponse.ActorSearch>>() {
            @Override
            public void onChanged(Resource<ActorSearchResponse.ActorSearch> actorSearchResource) {
                if (actorSearchResource.getStatus() == Status.SUCCESS && actorSearchResource.getData() != null) {
                    epgDetailsViewModel.getLiveActorSearchResult().removeObserver(this);
                    actorSearchList.add(actorSearchResource.getData());
                    actorSearchAdapter.notifyItemInserted(actorSearchList.size() - 1);
                    isLoading.setValue(false);
                }
            }
        });


    }

    private void showToolbar(boolean isShown) {
        AppBarLayout appBarLayout = getActivity().findViewById(R.id.app_bar);
        appBarLayout.setExpanded(isShown, true);
    }

    private void showBottomNavigation(boolean isShown) {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) getActivity().findViewById(R.id.bottom_navigation);
        ViewGroup.LayoutParams layoutParams = bottomNavigationView.getLayoutParams();
        if (layoutParams instanceof CoordinatorLayout.LayoutParams) {
            CoordinatorLayout.Behavior behavior =
                    ((CoordinatorLayout.LayoutParams) layoutParams).getBehavior();
            if (behavior instanceof HideBottomViewOnScrollBehavior) {
                HideBottomViewOnScrollBehavior<BottomNavigationView> hideShowBehavior =
                        (HideBottomViewOnScrollBehavior<BottomNavigationView>) behavior;
                if (isShown) {
                    hideShowBehavior.slideUp(bottomNavigationView);
                } else {
                    hideShowBehavior.slideDown(bottomNavigationView);
                }
            }
        }
    }

    private void showBackButton(boolean isShown) {
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(isShown);
        }
    }


    @Override
    public void onItemClick(ActorSearchResponse.ActorSearch actorSearch, int position) {
        sharedViewModel.changeToEpgActorFragment(actorSearch.getId());
    }


}