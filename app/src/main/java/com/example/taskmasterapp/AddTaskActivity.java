package com.example.taskmasterapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.room.Room;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class AddTaskActivity extends AppCompatActivity {
    public static final String TASK_COLLECTION = "task_collection";
    private TasksDao tasksDao;
private AppDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        db = Room.databaseBuilder(getApplicationContext(),AppDB.class,TASK_COLLECTION).allowMainThreadQueries().build();
        tasksDao=db.tasksDao();


        findViewById(R.id.submit).setOnClickListener((v)->{
            EditText title=findViewById(R.id.task_title);
            String titleTxt=title.getText().toString();
            EditText body=findViewById(R.id.description);
            String bodyTxt=body.getText().toString();
            EditText status=findViewById(R.id.status);
            String statusTxt=status.getText().toString();

            TaskItem taskItem=new TaskItem(titleTxt,bodyTxt,statusTxt);

            tasksDao.addTask(taskItem);

            @SuppressLint("ShowToast") Toast toast=Toast.makeText(this,"Submitted",Toast.LENGTH_LONG);

            Intent intent=new Intent(this,AllTasksActivity.class);
            startActivity(intent);
        });
    }

//    public void submitTxtHandler(View view){
//        Snackbar.make(view,"Submitted!",Snackbar.LENGTH_LONG).show();
//    }
}