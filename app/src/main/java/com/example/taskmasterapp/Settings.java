package com.example.taskmasterapp;

import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amplifyframework.datastore.generated.model.Team;

import java.util.List;

public class Settings extends AppCompatActivity {
    private static final String TAG = "setting";
    private List<Team> teams;
    private List<String> teamsName;
    private String team;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

public void  onSubmit(View v ) {

    EditText userName = findViewById(R.id.UserName);
    Spinner team = findViewById(R.id.team_spinner);

    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();
    preferenceEditor.putString("team",team.g)
    preferenceEditor.putString("UserName", userName.getText().toString());
    preferenceEditor.apply();
    Toast toast = Toast.makeText(getApplicationContext(), "User Name Updated", Toast.LENGTH_LONG);
    toast.show();
}
}
