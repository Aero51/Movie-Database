package com.aero51.moviedatabase.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.credits.Actor;
import com.aero51.moviedatabase.repository.model.credits.Cast;
import com.aero51.moviedatabase.repository.model.movie.PopularMovie;
import com.aero51.moviedatabase.utils.Resource;
import com.aero51.moviedatabase.utils.Status;
import com.aero51.moviedatabase.viewmodel.MovieDetailsViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private Cast castItem;
    private String mParam1;
    private MovieDetailsViewModel viewModel;

    private TextView text_view_actor_name;
    private TextView text_view_actor_birthday;
    private TextView text_view_actor_place_of_birth;
    private TextView text_view_actor_homepage;
    private TextView text_view_imdb;
    private TextView text_view_biography;

    public ActorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment PeopleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActorFragment newInstance(String param1) {
        ActorFragment fragment = new ActorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            castItem = (Cast) getArguments().getSerializable("Cast");
        }
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(MovieDetailsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_actor, container, false);
        text_view_actor_name = view.findViewById(R.id.text_view_actor_name);
        text_view_actor_birthday = view.findViewById(R.id.text_view_actor_birthday);
        text_view_actor_place_of_birth = view.findViewById(R.id.text_view_actor_place_of_birth);
        text_view_actor_homepage = view.findViewById(R.id.text_view_actor_homepage);
        text_view_imdb = view.findViewById(R.id.text_view_imdb);
        text_view_biography = view.findViewById(R.id.text_view_biography);

        viewModel.getActorDetails(castItem.getId()).observe(getViewLifecycleOwner(), new Observer<Resource<Actor>>() {
            @Override
            public void onChanged(Resource<Actor> actorResource) {
                Log.d("moviedatabaselog", "getActorDetails code: " + actorResource.code + " , status: " + actorResource.status + " list size: " + " ,message: " + actorResource.message);
                if (actorResource.status == Status.SUCCESS) {
                    Actor actor=actorResource.data;
                    text_view_actor_name.setText(actor.getName());
                    text_view_actor_birthday.setText(actor.getBirthday());
                    text_view_actor_place_of_birth.setText(actor.getPlace_of_birth());
                    text_view_actor_homepage.setText(actor.getHomepage());
                    text_view_imdb.setText(actor.getImdb_id());
                    text_view_biography.setText(actor.getBiography());
                }

            }
        });


        return view;
    }
}