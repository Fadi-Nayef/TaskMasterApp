package com.example.taskmasterapp;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addTaskView(View view){
        Intent intent=new Intent(this,AddTaskActivity.class);
        startActivity(intent);
    }

    public void allTextView(View view){
        Intent intent=new Intent(this,AllTasksActivity.class);
        startActivity(intent);
    }

}