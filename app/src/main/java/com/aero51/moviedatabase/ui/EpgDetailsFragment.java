package com.aero51.moviedatabase.ui;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aero51.moviedatabase.BuildConfig;
import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.epg.EpgProgram;
import com.aero51.moviedatabase.utils.Constants;
import com.aero51.moviedatabase.viewmodel.SharedViewModel;
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EpgDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EpgDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SharedViewModel sharedViewModel;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView text_view_title;
    private TextView text_view_date;
    private TextView text_view_description;
    private ImageView image_view_program;
    private ImageView image_view_channel;
    private TextView text_view_cast;

    public EpgDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EpgTvDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EpgDetailsFragment newInstance(String param1, String param2) {
        EpgDetailsFragment fragment = new EpgDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            //  epgProgram = (EpgProgram) getArguments().getSerializable("EpgProgram");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_epg_details, container, false);


        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        //toolbar.setTitle("text");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
                Log.d(Constants.LOG, "Toolbar clicked!");
                showBackButton(false);
            }
        });

        showBackButton(true);
        showBottomNavigation();

        text_view_title = view.findViewById(R.id.text_view_title);
        text_view_date = view.findViewById(R.id.text_view_date);
        text_view_description = view.findViewById(R.id.text_view_description);
        image_view_program = view.findViewById(R.id.image_view_program);
        image_view_channel = view.findViewById(R.id.image_view_channel);
        text_view_cast = view.findViewById(R.id.text_view_cast);

        sharedViewModel.getLiveDataProgram().observe(getViewLifecycleOwner(), new Observer<EpgProgram>() {
            @Override
            public void onChanged(EpgProgram epgProgram) {
                // sharedViewModel.getLiveDataProgram().removeObserver(this);
                text_view_title.setText(epgProgram.getTitle());
                text_view_date.setText(epgProgram.getDate() + "");
                text_view_description.setText(epgProgram.getDesc());
                // Picasso.get().load(epgProgram.getIcon()).into(image_view);
                Log.d(Constants.LOG, "icon: " + epgProgram.getIcon());
                //  Picasso.get().load(epgProgram.getIcon()).into(image_view);
                Picasso.get().load(epgProgram.getIcon()).fit().centerCrop().placeholder(R.drawable.picture_template).into(image_view_program, new Callback() {


                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        image_view_program.setBackgroundResource(R.drawable.picture_template);
                    }
                });

                Uri picture_path = Uri.parse("android.resource://" + BuildConfig.APPLICATION_ID +  "/drawable/"+ epgProgram.getChannel());
                Picasso.get().load(picture_path).placeholder(R.drawable.picture_template).into(image_view_channel);
                text_view_cast.setText(epgProgram.getCredits());
            }
        });

        return view;
    }



private void showBottomNavigation(){
    BottomNavigationView bottomNavigationView = (BottomNavigationView) getActivity().findViewById(R.id.bottom_navigation);
    ViewGroup.LayoutParams layoutParams = bottomNavigationView.getLayoutParams();
    if (layoutParams instanceof CoordinatorLayout.LayoutParams) {
        CoordinatorLayout.Behavior behavior =
                ((CoordinatorLayout.LayoutParams) layoutParams).getBehavior();
        if (behavior instanceof HideBottomViewOnScrollBehavior) {
            HideBottomViewOnScrollBehavior<BottomNavigationView> hideShowBehavior =
                    (HideBottomViewOnScrollBehavior<BottomNavigationView>) behavior;
            hideShowBehavior.slideUp(bottomNavigationView);
        }
    }

}

    private void showBackButton(boolean show) {
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(show);
        }
    }
}