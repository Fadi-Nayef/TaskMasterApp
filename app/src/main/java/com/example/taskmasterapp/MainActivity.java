package com.example.taskmasterapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Tasks;
import com.amplifyframework.datastore.generated.model.Todo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView taskView = findViewById(R.id.task_title);
    public static final String TITLE = "title";

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        TextView userTask = findViewById(R.id.my_task);
        userTask.setText(sharedPreferences.getString("UserName", "Tasks"));
        TextView teamName=findViewById(R.id.team_spinnerview);
        teamName.setText(sharedPreferences.getString("teamName","Team Name"));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amplifyConfig();
    }

    public void addTaskView(View view) {
        Intent intent = new Intent(this, AddTaskActivity.class);
        startActivity(intent);
    }

    public void allTextView(View view) {
        Intent intent = new Intent(this, AllTasksActivity.class);
        startActivity(intent);
    }

    public void firstTask(View view) {
        Button firstTaskbtn = findViewById(R.id.btnfirstTask);
        String taskTitle = firstTaskbtn.getText().toString();
        Intent intent = new Intent(MainActivity.this, TaskDetail.class);
        intent.putExtra(TITLE, taskTitle);
        startActivity(intent);
    }

    public void secondTaskDetail(View v) {
        Button secondTaskBtn = findViewById(R.id.btnSecondTask);
        String taskTitle = secondTaskBtn.getText().toString();
        Intent intent = new Intent(getApplicationContext(), TaskDetail.class);
        intent.putExtra(TITLE, taskTitle);
        startActivity(intent);
    }

    public void toThirdTask(View v) {
        Button thirdTaskBtn = findViewById(R.id.btnThirdTask);
        String taskTitle = thirdTaskBtn.getText().toString();
        Intent intent = new Intent(getApplicationContext(), TaskDetail.class);
        intent.putExtra(TITLE, taskTitle);
        startActivity(intent);
    }

    public void toSettings(View v) {
        FloatingActionButton toSettingsBtn = findViewById(R.id.settings);
        Intent intent = new Intent(MainActivity.this, Settings.class);
        startActivity(intent);
    }
    // Hard Coded Task To Api
    void hardCodedToApi(){
        Tasks task = Tasks.builder().title("first title").status("my status").body("description body").build();
        Amplify.DataStore.save(task,
                success -> Log.i("ONMAINCREATE", "Item Saved " + success.item().getTitle()),
                error -> Log.e("ONMAINCREATE", "failed to save on data store", error)
        );
        Amplify.DataStore.query(Tasks.class,
                todos -> {
                    while (todos.hasNext()) {
                        Tasks todo = todos.next();

                        Log.i("ONMAINCREATE", "============TASk Goes Here==========");
                        Log.i("ONMAINCREATE", "Task Name" + todo.getTitle());
                        taskView.setText(todo.getTitle());

                    }
                },
                failure -> Log.e("ONMAINCREATE", "no data source query", failure)
        );
    }


    void amplifyConfig(){
        /* initialize Amplify Configurations */
        try {
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());

            Log.i("ONMAINCREATE", "Initialized Amplify");
        } catch (AmplifyException e) {
            Log.e("ONMAINCREATE", "Could not initialize Amplify", e);
        }
    }
}