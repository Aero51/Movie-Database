package com.aero51.moviedatabase.repository.networkonlynotused.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.R;

import java.util.ArrayList;

public class GroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<Group> groups;
    private MSimpleAdapter simpleAdapter;
    private RecyclerView group_recycler_view;

    private PagedList<Movie> topRatedMoviesPagedList;
    private PagedList<Movie> popularMoviesPagedList;

    //todo 2. Create a new list with the new object

    public GroupAdapter(Context context, ArrayList<Group> groups) {
        //todo 3. Add it to the constructor
        this.context = context;
        this.groups = groups;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_item, parent, false);



        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        setGroupTitle(((GroupViewHolder) holder).group_title, groups.get(position).getGroupTitle());
        setGroupButtonTitle(((GroupViewHolder) holder).view_all, groups.get(position).getGroupButtonTitle());
        setOnClickViewAll(((GroupViewHolder) holder).head_parent, groups.get(position).getGroupTitle());
        //setLists(((GroupViewHolder) holder).group_recycler_view, position);
        group_recycler_view = ((GroupViewHolder) holder).group_recycler_view;
        ((GroupViewHolder) holder).simpleAdapter.submitList(topRatedMoviesPagedList);
        //setLists(group_recycler_view,position);


    }

    @Override
    public int getItemCount() {
        // return groups.size();
        return 1;
    }

    private void setGroupTitle(TextView textView, String text) {
        textView.setText(text);
    }

    private void setGroupButtonTitle(Button button, String text) {
        button.setText(text);
    }

    private void setOnClickViewAll(RelativeLayout button, final String groupTitle) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "View all " + groupTitle, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setLists(RecyclerView recyclerView, int position) {
        //todo 4. Create a new adapter for it and display it in the list
        switch (position) {
            case 0:
                //setHorizontalCoverList(recyclerView);
                setHorizontalSimpleList(recyclerView);
                break;
            case 1:
                //setHorizontalCoverList(recyclerView);
                // setHorizontalSimpleList(recyclerView);
                break;
            case 2:
                // setVerticalList(recyclerView);
                break;
        }
    }

    public void setTopRatedMoviesPagedList(PagedList<Movie> topRatedMoviesPagedList) {
        this.topRatedMoviesPagedList = topRatedMoviesPagedList;
       // simpleAdapter.submitList(topRatedMoviesPagedList);
       // simpleAdapter.submitList(topRatedMoviesPagedList);
        //setHorizontalSimpleList(group_recycler_view);
        //  group_recycler_view.setAdapter(simpleAdapter);
        // simpleAdapter.submitList(topRatedMoviesPagedList);


    }

    private void setHorizontalSimpleList(RecyclerView recyclerView) {
        simpleAdapter = new MSimpleAdapter();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(simpleAdapter);
        recyclerView.setNestedScrollingEnabled(true);
    }

    public void setPopularMoviesPagedList(PagedList<Movie> popularMoviesPagedList) {
        this.popularMoviesPagedList = popularMoviesPagedList;

    }

    /*
    private void setVerticalList(RecyclerView recyclerView) {
        VerticalAdapter verticalAdapter = new VerticalAdapter(context, vertical);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(verticalAdapter);
        recyclerView.setNestedScrollingEnabled(true);
    }

     */
    private void setHorizontalCoverList(RecyclerView recyclerView) {
        /*
        HorizontalCoverList horizontalCoverList = new HorizontalCoverList(context, cover);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(horizontalCoverList);
        recyclerView.setNestedScrollingEnabled(true);

         */
    }
}