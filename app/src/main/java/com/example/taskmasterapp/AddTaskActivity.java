package com.example.taskmasterapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.Room;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Tasks;
import com.amplifyframework.datastore.generated.model.Team;
import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class AddTaskActivity extends AppCompatActivity {
    public static final String TASK_COLLECTION = "task_collection";
    private static final String TAG = "add_task";
    private TasksDao tasksDao;
    private AppDB db;
    private String selectedTeam;
    private List<Team> teams;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
//        db = Room.databaseBuilder(getApplicationContext(),AppDB.class,TASK_COLLECTION).allowMainThreadQueries().build();
//        tasksDao=db.tasksDao();
     Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                Toast toast = Toast.makeText(AddTaskActivity.this , "Task has been added" , Toast.LENGTH_LONG);
                toast.show();
                return false;
            }
        }) ;

        Spinner teamSpinner = findViewById(R.id.add_task_spinner);
        String [] teamsGroup = getResources().getStringArray(R.array.team_spinner_array);
//        saveTeamToApi(teamsGroup);

        teams = new ArrayList<>();
        getTeamsFromApi();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.team_spinner_array,
                android.R.layout.simple_spinner_item);

        adapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teamSpinner
                .setAdapter(adapter);

        teamSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTeam = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedTeam = (String) parent.getItemAtPosition(0);
            }
        });


        Toast toast = Toast.makeText(getApplicationContext(),"Submitted",Toast.LENGTH_LONG);


        findViewById(R.id.submit).setOnClickListener((v)->{

            EditText title=findViewById(R.id.task_title);
            String titleTxt=title.getText().toString();

            EditText body=findViewById(R.id.description);
            String bodyTxt=body.getText().toString();

            EditText status=findViewById(R.id.status);
            String statusTxt=status.getText().toString();

            // Save Data to Room
            TaskItem taskItem=new TaskItem(titleTxt,bodyTxt,statusTxt);
//            tasksDao.addTask(taskItem);

                // Save Data to API
            Team team1 = teams.stream().filter(team2 -> team2.getName().equals(selectedTeam)).collect(Collectors.toList()).get(0);
            Tasks task = Tasks.builder().title(titleTxt).body(bodyTxt).status(statusTxt).apartOf(team1).build();
            saveTaskToAPI(task);

            toast.show();
            if (isNetworkAvailable(getApplicationContext())){
            Log.i(TAG,"On Submit :Connected To Network ");
                toast.show();
    }else {
            Log.i(TAG,"On submit : No Internet Connection");

            }


//            @SuppressLint("ShowToast") Toast toast=Toast.makeText(this,"Submitted",Toast.LENGTH_LONG);

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
    void saveTeamToApi(String[] teams){
        for (String name : teams){
            Team team = Team.builder().name(name).build();
            Amplify.API.mutate(ModelMutation.create(team),
                    success -> Log.i(TAG, "Successfully"),
                    error -> Log.i(TAG, "failed " + error)
            );
        }}

    List<Team> getTeamsFromApi(){
        teams=new ArrayList<>();
        Amplify.API.query(ModelQuery.list(Team.class) ,
                response -> {
                    for (Team team : response.getData()) {
                        Log.i(TAG, "succeed to getTeamFromApi: Team Name --> "+ team.getName());
                        teams.add(team) ;
                    }
                },

                error -> Log.i(TAG, "failed to getTeamFromApi: Team Name -->" + error)
        );
        return teams ;
    }

        // Method Tests the network availability
    public boolean isNetworkAvailable(Context context){
    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

    return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
