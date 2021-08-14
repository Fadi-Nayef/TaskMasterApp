package com.example.taskmasterapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    SharedPreferences.Editor prefrenceEditor = sharedPreferences.edit();
    prefrenceEditor.putString("UserName", userName.getText().toString());
    prefrenceEditor.apply();
    Toast toast = Toast.makeText(getApplicationContext(), "User Name Updated", Toast.LENGTH_LONG);
    toast.show();
}}
