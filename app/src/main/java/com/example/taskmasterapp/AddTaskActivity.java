package com.example.taskmasterapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.room.Room;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Tasks;
import com.google.android.material.snackbar.Snackbar;

public class AddTaskActivity extends AppCompatActivity {
    public static final String TASK_COLLECTION = "task_collection";
    private static final String TAG = "add_task";
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

            // Save Data to Room
            TaskItem taskItem=new TaskItem(titleTxt,bodyTxt,statusTxt);
            tasksDao.addTask(taskItem);

                // Save Data to API
            Tasks task = Tasks.builder().title(titleTxt).body(bodyTxt).status(statusTxt).build();
if (isNetworkAvailable(getApplicationContext())){
    Log.i(TAG,"On Submit :Connected To Network ");
}else {
    Log.i(TAG,"On submit : No Internet Connection");
}
saveTaskToAPI(task);
            @SuppressLint("ShowToast") Toast toast=Toast.makeText(this,"Submitted",Toast.LENGTH_LONG);

//            Intent intent=new Intent(this,AllTasksActivity.class);
//            startActivity(intent);
        });
    }


    private void saveTaskToAPI (Tasks task){
        Amplify.API.mutate(ModelMutation.create(task),
                success -> Log.i(TAG, "Saved item: " + success.getData().getTitle ()),
                error -> Log.e(TAG, "Unable to Save Item To API/Dynamodb", error)
        );
    }

    // Method Tests the network availability
public boolean isNetworkAvailable(Context context){
    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

    return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
}
}
