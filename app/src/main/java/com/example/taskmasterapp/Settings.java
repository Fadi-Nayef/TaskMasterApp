package com.example.taskmasterapp;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Team;

import java.util.ArrayList;
import java.util.List;

public class Settings extends AppCompatActivity {
    private static final String TAG = "setting";
    private List<Team> teams;
    private List<String> teamsName;
    private Handler handler;

    EditText userName = findViewById(R.id.UserName);
    Spinner teamSpinner = findViewById(R.id.team_spinner);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSpinnerData();
    }

public void  onSubmit(View v ) {
    String team = teamSpinner.getSelectedItem().toString();

//    EditText userName = findViewById(R.id.UserName);
//    Spinner teamSpinner = findViewById(R.id.team_spinner);
//
//    team = teamSpinner.getSelectedItem().toString();

    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();



    preferenceEditor.putString("team", team);
    preferenceEditor.putString("UserName", userName.getText().toString());
    preferenceEditor.apply();
    Toast toast = Toast.makeText(getApplicationContext(), "User Name Updated", Toast.LENGTH_LONG);
    toast.show();
}
void getSpinnerData(){

    Amplify.API.query(ModelQuery.list(Team.class),
            response ->{
                for(Team team : response.getData()){
                    teamsName.add(team.getName());
                }
                ArrayAdapter<String> teamAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,teamsName);
                runOnUiThread(() -> {
                    teamSpinner.setAdapter(teamAdapter);
                });
            },
            error -> {
                Log.e(TAG,"FAILED To Get",error);
            });
}
}
