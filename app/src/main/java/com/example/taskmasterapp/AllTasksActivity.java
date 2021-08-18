package com.example.taskmasterapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class AllTasksActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<Tasks> tasksList;
    ViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);

        initData();
        initRecycler();
    }

    private void initData() {
        tasksList=new ArrayList<>();
        tasksList.add(new Tasks("title 1","body 1","status 1"));
        tasksList.add(new Tasks("title 2","body 2","status 2"));
        tasksList.add(new Tasks("title 3","body 3","status 3"));
        tasksList.add(new Tasks("title 4","body 4","status 4"));
        tasksList.add(new Tasks("title 5","body 5","status 5"));
        tasksList.add(new Tasks("title 6","body 6","status 6"));
        tasksList.add(new Tasks("title 7","body 7","status 7"));
        tasksList.add(new Tasks("title 8","body 8","status 8"));
        tasksList.add(new Tasks("title 9","body 9","status 9"));
        tasksList.add(new Tasks("title 10","body 10","status 10"));
        tasksList.add(new Tasks("title 11","body 11","status 11"));
        tasksList.add(new Tasks("title 12","body 12","status 12"));
        tasksList.add(new Tasks("title 13","body 13","status 13"));
        tasksList.add(new Tasks("title 14","body 14","status 14"));
        tasksList.add(new Tasks("title 15","body 15","status 15"));
        tasksList.add(new Tasks("title 16","body 16","status 16"));
    }

    private void initRecycler() {
        recyclerView=findViewById(R.id.recycler);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new ViewAdapter(tasksList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}