package com.aero51.moviedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TopRatedMoviesAdapter adapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private TextView textView;
    private TopRatedResultViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new TopRatedMoviesAdapter();
        recyclerView.setAdapter(adapter);
        textView = findViewById(R.id.text_view_top_rated_movies);

        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(TopRatedResultViewModel.class);
        viewModel.getAllResults().observe(this, new Observer<List<Top_Rated_Result>>() {
            @Override
            public void onChanged(List<Top_Rated_Result> top_rated_results) {
               // Log.d("moviedatabaselog", "onChanged " + " top_rated_results.size: " + top_rated_results.size());
                    adapter.addData(top_rated_results);
            }
        });

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.d("moviedatabaselog", "EndlessRecyclerViewScrollListener   page: " + page + " total items count: " + totalItemsCount);
                viewModel.fetchNewPage(page + 1);
            }
        };
        // Adds the scroll listener to RecyclerView
        recyclerView.addOnScrollListener(scrollListener);
        adapter.setOnItemClickListener(new TopRatedMoviesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Top_Rated_Result result) {
            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                Toast.makeText(MainActivity.this, "onMove", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //  noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Result deleted", Toast.LENGTH_SHORT).show();
            }
        });//.attachToRecyclerView(recyclerView);
    }

}

