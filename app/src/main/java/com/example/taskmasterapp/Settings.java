package com.example.taskmasterapp;

import android.content.Intent;
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
    private List<String> teamsList;
    private Handler handler;
private Team newTeam;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();


        Spinner teamSpinner = findViewById(R.id.settings_spinner);

        teamsList = new ArrayList<>();
        Amplify.API.query(ModelQuery.list(Team.class),
                response -> {
                    for (Team team : response.getData()) {

                        teamsList.add(team.getName());
Log.i(TAG,team.getName());
                    }
                    ArrayAdapter<String> teamAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, teamsList);
                    runOnUiThread(() -> {
                        teamSpinner.setAdapter(teamAdapter);
                    });
                },
                error -> {
                    Log.e(TAG, "FAILED To Get", error);
                });


        findViewById(R.id.save_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String team = teamSpinner
                        .getSelectedItem()
                        .toString();

                EditText userName = findViewById(R.id.UserName);

                String username = userName.getText().toString();


                handler = new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "User Name Updated " + userName + "Team Updated " + team,
                                Toast.LENGTH_LONG);
                        toast.show();
                    }
                };

                Amplify.API.query(ModelQuery.list(Team.class, Team.NAME.eq(team)),
                        response -> {
                            List<Team> list = new ArrayList<>();
                            for (Team team1 : response.getData()) {
                                list.add(team1);
                            }
                            preferenceEditor.putString("UserName", username);
                            preferenceEditor.putString("teamName", team);
                            preferenceEditor.putString("teamId", list.get(0).getId());

                            Message message = handler.obtainMessage(1);
                            message.sendToTarget();
                            preferenceEditor.apply();
                        },
                        error -> {
                            Log.e(TAG, "Failed", error);
                        });

                Toast toast = Toast.makeText(Settings.this, "Saved!", Toast.LENGTH_LONG);
                toast.show();
            }

        });

        findViewById(R.id.home_button).setOnClickListener((view) -> {
            Intent intent = new Intent(Settings.this, MainActivity.class);
            Settings.this.startActivity(intent);
        });
    }
    List<Team> getTeamFromApi() {
        Amplify.API.query(ModelQuery.list(Team.class),
                response -> {
                    for (Team team : response.getData()) {
                        Log.i(TAG, "succeed to getTeamFromApi: Team Name --> " + team.getName());
                        teams.add(team);
                    }
                },

                error -> Log.i(TAG, "failed to getTeamFromApi: Team Name -->" + error)
        );
        return teams;
    }
}



