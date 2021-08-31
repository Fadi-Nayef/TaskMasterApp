package com.example.taskmasterapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Tasks;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String TITLE = "title";
    private static final String TAG = "MainActivity";
    private static PinpointManager pinpointManager;
    private FirebaseAnalytics mFirebaseAnalytics;


    public static PinpointManager getPinpointManager(final Context applicationContext) {
        if (pinpointManager == null) {
            final AWSConfiguration awsConfig = new AWSConfiguration(applicationContext);
            AWSMobileClient.getInstance().initialize(applicationContext, awsConfig, new Callback<UserStateDetails>() {
                @Override
                public void onResult(UserStateDetails userStateDetails) {
                    Log.i("INIT", userStateDetails.getUserState().toString());
                }

                @Override
                public void onError(Exception e) {
                    Log.e("INIT", "Initialization error.", e);
                }
            });

            PinpointConfiguration pinpointConfig = new PinpointConfiguration(
                    applicationContext,
                    AWSMobileClient.getInstance(),
                    awsConfig);

            pinpointManager = new PinpointManager(pinpointConfig);

            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                                return;
                            }
                            final String token = task.getResult();
                            Log.d(TAG, "Registering push notifications token: " + token);
                            pinpointManager.getNotificationClient().registerDeviceToken(token);
                        }
                    });
        }
        return pinpointManager;
    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        TextView userName= findViewById(R.id.User_Name);
        userName.setText(sharedPreferences.getString("UserName", "Go to Settings To Add your Name"));
        TextView accountUser = findViewById(R.id.account);
        accountUser.setText(sharedPreferences.getString("userName","Application User"));
//        TextView teamName=findViewById(R.id.team_spinnerview);

//        teamName.setText(sharedPreferences.getString("teamName","Manager"));

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize PinpointManager
        getPinpointManager(getApplicationContext());
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        findViewById(R.id.mainsignup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Amplify.Auth.signOut(
                        () -> {
                            Log.i("AuthQuickstart", "Signed out successfully");
                            Intent intent = new Intent(getBaseContext(), SignInActivity.class);
                            startActivity(intent);

                        },
                        error -> {
                            Log.e("AuthQuickstart", error.toString());
                        }
                );


            }
        });


    }

    public void addTaskView(View view) {
        Intent intent = new Intent(this, AddTaskActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID,findViewById(R.id.mainsignup).toString());
//                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
//                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
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
        TextView taskView = findViewById(R.id.task_details_title);

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