package com.example.taskmasterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TaskDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        Intent intent =getIntent();
        String taskTitle = intent.getStringExtra(AllTasksActivity.TASK_TITLE);
         String taskDesc = intent.getStringExtra(AllTasksActivity.TASK_BODY);
                String taskStatus=intent.getStringExtra(AllTasksActivity.TASK_STATUS);
        TextView title = findViewById(R.id.title_TaskPage);
        TextView desc= findViewById(R.id.taskDes);
        TextView status = findViewById(R.id.statusView);
        title.setText(taskTitle);
        desc.setText(taskDesc);
        status.setText(taskStatus);
    }
}