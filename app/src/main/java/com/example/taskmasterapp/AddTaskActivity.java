package com.example.taskmasterapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Tasks;
import com.amplifyframework.datastore.generated.model.Team;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AddTaskActivity extends AppCompatActivity {
    public static final String TASK_COLLECTION = "task_collection";
    private static final String TAG = "add_task";
    private static final int REQUEST_FOR_FILE = 999;
    private TasksDao tasksDao;
    private AppDB db;
    private String selectedTeam;
    private List<Team> teams;
    private String fileUploaded;
    private String fileName;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        Intent intent = getIntent();
        String action = intent.getAction();
        String type   = intent.getType();

        if (type != null)
            if (type.equals("image/*"))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    sendImage(intent) ;
                }


//        db = Room.databaseBuilder(getApplicationContext(),AppDB.class,TASK_COLLECTION).allowMainThreadQueries().build();
//        tasksDao=db.tasksDao();
        Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                Toast toast = Toast.makeText(AddTaskActivity.this, "Task has been added", Toast.LENGTH_LONG);
                toast.show();
                return false;
            }
        });

        findViewById(R.id.upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                uploadFileToS3();
                getFileFromDevice();
            }
        });
//    }
//        @RequiresApi(api = Build.VERSION_CODES.Q)
//        @Override
//        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//            super.onActivityResult(requestCode, resultCode, data);
//
//            if (requestCode == REQUEST_FOR_FILE && resultCode == RESULT_OK) {
//                Log.i(TAG, "onActivityResult: returned from file explorer");
//                Log.i(TAG, "onActivityResult: => " + data.getData());
//
//                File uploadFile = new File(getApplicationContext().getFilesDir(), "uploadFile");
//                 fileUploaded = new Date().toString();
//                try {
//                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
//                    FileUtils.copy(inputStream, new FileOutputStream(uploadFile));
//                } catch (Exception exception) {
//                    Log.e(TAG, "onActivityResult: file upload failed" + exception.toString());
//                }
//
//                Amplify.Storage.uploadFile(
//                        fileUploaded ,
//                        uploadFile,
//                        success -> {
//                            Log.i(TAG, "uploadFileToS3: succeeded " + success.getKey());
//                        },
//                        error -> {
//                            Log.e(TAG, "uploadFileToS3: failed " + error.toString());
//                        }
//                );
//
//        }


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

            EditText title=findViewById(R.id.task_details_title);
            String titleTxt=title.getText().toString();

            EditText body=findViewById(R.id.description);
            String bodyTxt=body.getText().toString();

            EditText status=findViewById(R.id.status);
            String statusTxt=status.getText().toString();

            if(fileUploaded == null){
                fileName= "";
            }else{
                fileName = fileUploaded;
            }
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor preferenceEditor = preferences.edit();
            preferenceEditor.putString("FileName", fileName);
            preferenceEditor.apply();

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

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void sendImage(Intent intent) {
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        String path = getPathFromUri( getApplicationContext(), imageUri) ;
        Log.i(TAG, "sendImage: path" + path);
        path = path.replace(" " , "");
        File uploadFile = new File(path);
        try {
            InputStream inputStream = getContentResolver().openInputStream(intent.getData());
            FileUtils.copy(inputStream , new FileOutputStream(uploadFile));

        } catch(Exception exception){
            Log.i(TAG, "sendImage: called" + path);
        }

        uploadExternalFileToS3(uploadFile);
    }

    private void uploadExternalFileToS3(File uploadFile) {
        String key =String.format("defaultTask%s.jpg" , new Date().getTime());

        Amplify.Storage.uploadFile(
                key,
                uploadFile ,
                success -> Log.i(TAG, "uploadFileToS3: succeeded " + success.getKey()) ,
                failure -> Log.e(TAG, "uploadFileToS3: failed " + failure.toString())
        );
    }


    String getPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
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
        return teams ;}
    private void getFileFromDevice() {
        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("*/*");
        chooseFile = Intent.createChooser(chooseFile, "Choose a File");
        startActivityForResult(chooseFile, REQUEST_FOR_FILE); // deprecated
    }


    private void uploadFileToS3() {
        File testFile = new File(getApplicationContext().getFilesDir(), "test");

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(testFile));
            bufferedWriter.append("This is a test file to demonstrate S3 functionality");
            bufferedWriter.close();
        } catch (Exception exception) {
            Log.e(TAG, "uploadFileToS3: failed" + exception.toString());
        }

        Amplify.Storage.uploadFile(
                "test",
                testFile,
                success -> {
                    Log.i(TAG, "uploadFileToS3: succeeded " + success.getKey());
                },
                error -> {
                    Log.e(TAG, "uploadFileToS3: failed " + error.toString());
                }
        );
    }


        // Method Tests the network availability
    public boolean isNetworkAvailable(Context context){
    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

    return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
