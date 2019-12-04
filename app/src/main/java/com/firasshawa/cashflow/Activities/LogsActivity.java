package com.firasshawa.cashflow.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firasshawa.cashflow.Adapters.CategoryAdapter;
import com.firasshawa.cashflow.Adapters.LogAdapter;
import com.firasshawa.cashflow.Callback;
import com.firasshawa.cashflow.DataModels.Category;
import com.firasshawa.cashflow.DataModels.Log;
import com.firasshawa.cashflow.DataModels.User;
import com.firasshawa.cashflow.Database.DB;
import com.firasshawa.cashflow.R;

import java.util.ArrayList;

public class LogsActivity extends AppCompatActivity {

    DB db ;
    RecyclerView LogFullRC;
    LogAdapter logAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs);
        LogFullRC = findViewById(R.id.LogFullRC);

    }

    @Override
    protected void onStart() {
        super.onStart();
        LogsFullRC_Setup();
    }

    public void LogsFullRC_Setup(){
        new DB().GetLogs(new Callback() {
            @Override
            public void onCallbackCategories(ArrayList<Category> categories) {

            }

            @Override
            public void onCallbackLogs(ArrayList<Log> logs) {
                LinearLayoutManager layoutManager
                        = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                logAdapter = new LogAdapter(LogsActivity.this,logs);
                LogFullRC.setLayoutManager(layoutManager);
                LogFullRC.setAdapter(logAdapter);
            }

            @Override
            public void onCallbackUser(User user) {

            }
        });
    }

}
