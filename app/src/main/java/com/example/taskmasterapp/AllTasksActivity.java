package com.example.taskmasterapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class AllTasksActivity extends AppCompatActivity {
    public static final String TASK_TITLE = "taskTitle";
    public static final String TASK_BODY = "taskBody";
    public static final String TASK_STATUS = "taskStatus";
    private List<TaskItem> taskItemList;
    private TasksDao tasksDao;
    private AppDB db;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
//    List<TaskItem> taskItemList;
    ViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);
db= Room.databaseBuilder(getApplicationContext(),AppDB.class,AddTaskActivity.TASK_COLLECTION).allowMainThreadQueries().build();
    tasksDao = db.tasksDao();
    taskItemList=tasksDao.findAll();
//        initData();
        initRecycler();
   adapter=new ViewAdapter(taskItemList, new ViewAdapter.OnTaskClickedListener() {
       @Override
       public void onItemClicked(int position) {
           Intent intent = new Intent(getApplicationContext(),TaskDetail.class);
      intent.putExtra(TASK_TITLE,taskItemList.get(position).getTaskTitle());
      intent.putExtra(TASK_BODY,taskItemList.get(position).getTaskBody());
      intent.putExtra(TASK_STATUS,taskItemList.get(position).getTaskStatus());
      startActivity(intent);
       }

   });
    }

    private void initData() {
        taskItemList =new ArrayList<>();
        taskItemList.add(new TaskItem("title 1","body 1","status 1"));
        taskItemList.add(new TaskItem("title 2","body 2","status 2"));
        taskItemList.add(new TaskItem("title 3","body 3","status 3"));
        taskItemList.add(new TaskItem("title 4","body 4","status 4"));
        taskItemList.add(new TaskItem("title 5","body 5","status 5"));
        taskItemList.add(new TaskItem("title 6","body 6","status 6"));
        taskItemList.add(new TaskItem("title 7","body 7","status 7"));
        taskItemList.add(new TaskItem("title 8","body 8","status 8"));
        taskItemList.add(new TaskItem("title 9","body 9","status 9"));
        taskItemList.add(new TaskItem("title 10","body 10","status 10"));
        taskItemList.add(new TaskItem("title 11","body 11","status 11"));
        taskItemList.add(new TaskItem("title 12","body 12","status 12"));
        taskItemList.add(new TaskItem("title 13","body 13","status 13"));
        taskItemList.add(new TaskItem("title 14","body 14","status 14"));
        taskItemList.add(new TaskItem("title 15","body 15","status 15"));
        taskItemList.add(new TaskItem("title 16","body 16","status 16"));
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initRecycler() {
        recyclerView=findViewById(R.id.recycler);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new ViewAdapter(taskItemList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}