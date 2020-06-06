package com.aero51.moviedatabase.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.Top_Rated_Result;
import com.aero51.moviedatabase.utils.ItemClickListener;

public class MainActivity extends AppCompatActivity implements ItemClickListener {

    private TopRatedMoviesPagedListAdapter adapter;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        textView = findViewById(R.id.text_view_top_rated_movies);
        TopRatedResultViewModel viewModel =new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(TopRatedResultViewModel.class);

        adapter =new TopRatedMoviesPagedListAdapter(this);
        viewModel.getTopRatedResultsPagedList().observe(this, new Observer<PagedList<Top_Rated_Result>>() {
            @Override
            public void onChanged(PagedList<Top_Rated_Result> top_rated_results) {
                Log.d("moviedatabaselog", "onChanged list size: " + top_rated_results.size());
                adapter.submitList(top_rated_results);
            }
        });
        recyclerView.setAdapter(adapter);

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


    @Override
    public void OnItemClick(Top_Rated_Result result, int position) {

    }
}

