package com.example.taskmasterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
        TextView title = findViewById(R.id.task_details_title);
        TextView desc= findViewById(R.id.task_details_Desc);
        TextView status = findViewById(R.id.task_details_status);
        title.setText(taskTitle);
        desc.setText(taskDesc);
        status.setText(taskStatus);
    }
    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        TextView fileName = findViewById(R.id.file_details);

        fileName.setText(preferences.getString("FileName","File Name"));
    }
}
