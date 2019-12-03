package com.firasshawa.cashflow.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firasshawa.cashflow.DataModels.Main;
import com.firasshawa.cashflow.Database.DB;
import com.firasshawa.cashflow.R;

public class Register extends AppCompatActivity {

    EditText nameEt;
    Button registerBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        nameEt = findViewById(R.id.nameEt);
        registerBtn = findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!nameEt.getText().equals("")){
                    String key = new DB().NewUser(nameEt.getText().toString().trim());
                    SaveUserKey(key);
                    startActivity(new Intent(Register.this, MainActivity.class));
                }else{
                    nameEt.requestFocus();
                    nameEt.setError("Worng Name!");
                }
            }
        });
    }
    public void SaveUserKey(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(getString(R.string.UserKeyToken), key);
        edit.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean previouslyStarted = prefs.getBoolean(getString(R.string.StartToken), false);
        if(!previouslyStarted) {
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean(getString(R.string.StartToken), Boolean.TRUE);
            edit.commit();
        }else{
            startActivity(new Intent(Register.this, MainActivity.class));
            finish();
        }
    }
}
