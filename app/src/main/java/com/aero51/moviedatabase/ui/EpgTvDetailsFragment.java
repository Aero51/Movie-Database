package com.aero51.moviedatabase.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.epg.EpgProgram;
import com.aero51.moviedatabase.viewmodel.SharedViewModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EpgTvDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EpgTvDetailsFragment extends Fragment {

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
    private ImageView image_view;

    public EpgTvDetailsFragment() {
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
    public static EpgTvDetailsFragment newInstance(String param1, String param2) {
        EpgTvDetailsFragment fragment = new EpgTvDetailsFragment();
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
        View view = inflater.inflate(R.layout.fragment_epg_tv_details, container, false);
        text_view_title = view.findViewById(R.id.text_view_title);
        text_view_date = view.findViewById(R.id.text_view_date);
        text_view_description = view.findViewById(R.id.text_view_description);
        image_view=view.findViewById(R.id.image_view);

        sharedViewModel.getLiveDataProgram().observe(getViewLifecycleOwner(), new Observer<EpgProgram>() {
            @Override
            public void onChanged(EpgProgram epgProgram) {
                sharedViewModel.getLiveDataProgram().removeObserver(this);
                text_view_title.setText(epgProgram.getTitle());
                text_view_date.setText(epgProgram.getDate()+"");
                text_view_description.setText(epgProgram.getDesc());
                // Picasso.get().load(epgProgram.getIcon()).into(image_view);
                Log.d("moviedatabaselog", "icon: "+epgProgram.getIcon());
              //  Picasso.get().load(epgProgram.getIcon()).into(image_view);
                Picasso.get().load(epgProgram.getIcon()).fit().centerCrop().placeholder(R.drawable.picture_template).into(image_view, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        image_view.setBackgroundResource(R.drawable.picture_template);
                    }
                });



            }
        });




        return view;
    }
}