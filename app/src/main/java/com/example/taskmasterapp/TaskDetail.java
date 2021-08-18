package com.example.taskmasterapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TaskDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        String taskTitle = getIntent().getStringExtra(MainActivity.TITLE);
        TextView title = findViewById(R.id.title_TaskPage);
        title.setText(taskTitle);
    }

}