package com.example.taskmasterapp;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

public void  onSubmit(View v ) {
    EditText userName = findViewById(R.id.UserName);

    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();
    preferenceEditor.putString("UserName", userName.getText().toString());
    preferenceEditor.apply();
    Toast toast = Toast.makeText(getApplicationContext(), "User Name Updated", Toast.LENGTH_LONG);
    toast.show();
}
}
