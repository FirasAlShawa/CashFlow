package com.firasshawa.cashflow.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firasshawa.cashflow.Adapters.CategoryAdapter;
import com.firasshawa.cashflow.Adapters.LogAdapter;
import com.firasshawa.cashflow.Callback;
import com.firasshawa.cashflow.DataModels.Category;
import com.firasshawa.cashflow.DataModels.Log;
import com.firasshawa.cashflow.DataModels.Main;
import com.firasshawa.cashflow.DataModels.User;
import com.firasshawa.cashflow.Database.*;
import com.firasshawa.cashflow.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    User CurrentUser = new User();
    String key;

    RecyclerView CategoriesRC,LogRC;
    TextView currentTv,FullLogsTv;
    Button increaseBtn,editBtn,supportBtn;

    CategoryAdapter categoryAdapter;
    LogAdapter logAdapter;

    Date date = new Date();
    SimpleDateFormat formatDay = new SimpleDateFormat("yyyy-mm-dd");
    SimpleDateFormat formatTime = new SimpleDateFormat("HH:MM:SS");

    DB db = new DB();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CategoriesRC = findViewById(R.id.categoriesRC);
        LogRC = findViewById(R.id.LogRC);
        currentTv = findViewById(R.id.currentTv);
        increaseBtn = findViewById(R.id.increaseBtn);
        editBtn = findViewById(R.id.editBtn);
        supportBtn = findViewById(R.id.supportBtn);
        FullLogsTv = findViewById(R.id.FullLogsTv);


        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditCurrent();
            }
        });
        increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increaseCurrentDialog(MainActivity.this);
            }
        });
        supportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                supportCurrentDialog(MainActivity.this);
            }
        });

        FullLogsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,LogsActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        GetUser();
    }

    //get the user object from the DB
    //1.get the userKey from SharedPreferences
    //2.call GetUser(Key,Callback function)
        //2.1 key => will get it from SharedPreferences
        //2.2 callback => new instance of Callback interface , i should override the
        //the desired method and receive the data i want!
    public void GetUser(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        key = prefs.getString(getString(R.string.UserKeyToken), "");

        db.GetUser(key,new Callback(){
            @Override
            public void onCallbackCategories(ArrayList<Category> categories) {

            }

            @Override
            public void onCallbackLogs(ArrayList<Log> logs) {

            }

            @Override
            public void onCallbackUser(User user) {
                CurrentUser = user;
                currentTv.setText(CurrentUser.getCurrent()+"");
                CategoryRC_Setup();
                LogsRC_Setup();
            }
        });
    }

    //Update all the user widgets
    public void UpdateUser(){
        currentTv.setText(CurrentUser.getCurrent()+"");
        System.out.println(CurrentUser);
        System.out.println("User Should be Updated");
    }

    //Show Edit Dialog for the user to update the DB
    public void EditCurrent(){
        EditCurrentDialog(MainActivity.this);
    }

    public void increaseCurrent(){}

    //Build and show Edit Dialog that used in EditCurrent()
    public void EditCurrentDialog(Context context){
        //AlertDialog bulider => is a object to build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        //Get the Custom layout for your dialog
        final View layout = getLayoutInflater().inflate(R.layout.edit_current_layout,null);

        //Dialog Spec
            //1.title
                builder.setTitle("كم معك بالجزدان؟");
            //2.custom layout
                builder.setView(layout);

            //3.Set the Okay Button for the Dialog and it's logic
                builder.setPositiveButton("تمام", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //get the views in the custom layout
                        EditText editValueEt = layout.findViewById(R.id.editValueEt);

                        //copy the current user to edit it
                        User EditUser = CurrentUser;

                        //Set values
//                        EditUser.setCurrent(Integer.parseInt(editValueEt.getText().toString()));
//                        EditUser.setOldCurrent(Integer.parseInt(currentTv.getText().toString().trim()));

                        //log this operation
                        Log log = new Log();
                        log.setUser(CurrentUser.getName());
                        log.setDesc("بداية نضيفه!!");
                        log.setDate(formatDay.format(date));
                        log.setTime(formatTime.format(date));
                        log.setType(" ");
                        log.setValue(Integer.parseInt(editValueEt.getText().toString()));
                        log.setOldCurrent(Integer.parseInt(currentTv.getText().toString().trim()));
                        log.setNewCurrent(Integer.parseInt(editValueEt.getText().toString().trim()));
                        log.setCategory("تعديل");

                        db.AddLog(log,EditUser);

                        //Update the db
//                        CurrentUser = db.UpdateUser(EditUser);

                        //Update the UI
                        UpdateUser();
                    }
                });

                builder.setNegativeButton("خلص بطلت", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    //Build and show Edit Dialog that used in EditCurrent()
    public void increaseCurrentDialog(Context context){
        //AlertDialog bulider => is a object to build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        //Get the Custom layout for your dialog
        final View layout = getLayoutInflater().inflate(R.layout.edit_current_layout,null);

        //Dialog Spec
        //1.title
        builder.setTitle("كم اخدت؟");
        //2.custom layout
        builder.setView(layout);

        //3.Set the Okay Button for the Dialog and it's logic
        builder.setPositiveButton("تمام", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //get the views in the custom layout
                EditText editValueEt = layout.findViewById(R.id.editValueEt);

                //copy the current user to edit it
                User EditUser = CurrentUser;

                //Set values
                EditUser.setCurrent(Integer.parseInt(editValueEt.getText().toString().trim())+EditUser.getCurrent());
                EditUser.setOldCurrent(Integer.parseInt(currentTv.getText().toString().trim()));

                //Update the db
                CurrentUser = db.UpdateUser(EditUser);

                Log log = new Log();
                log.setUser(CurrentUser.getName());
                log.setDesc("اخدت كاش يا بوووي!");
                log.setDate(formatDay.format(date));
                log.setTime(formatTime.format(date));
                log.setType("+");
                log.setValue(Integer.parseInt(editValueEt.getText().toString()));
                log.setOldCurrent(0);
                log.setNewCurrent(EditUser.getCurrent()+Integer.parseInt(editValueEt.getText().toString().trim()));
                log.setCategory("زيادة");

                db.AddLog(log,CurrentUser);

                //Update the UI
                UpdateUser();
            }
        });

        builder.setNegativeButton("خلص بطلت", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    //Build and show Edit Dialog that used in EditCurrent()
    public void supportCurrentDialog(Context context){
        //AlertDialog bulider => is a object to build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        //Get the Custom layout for your dialog
        final View layout = getLayoutInflater().inflate(R.layout.edit_current_layout,null);

        //Dialog Spec
        //1.title
        builder.setTitle("كم رح تدعم؟");
        //2.custom layout
        builder.setView(layout);

        //3.Set the Okay Button for the Dialog and it's logic
        builder.setPositiveButton("تمام", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //get the views in the custom layout
                EditText editValueEt = layout.findViewById(R.id.editValueEt);

                //copy the current user to edit it
                User EditUser = CurrentUser;

                //Set values
                EditUser.setCurrent(EditUser.getCurrent()-Integer.parseInt(editValueEt.getText().toString().trim()));
                EditUser.setOldCurrent(Integer.parseInt(currentTv.getText().toString().trim()));

                //Update the db
                CurrentUser = db.UpdateUser(EditUser);

                Log log = new Log();
                log.setUser(CurrentUser.getName());
                log.setDesc("دعمت!");
                log.setDate(formatDay.format(date));
                log.setTime(formatTime.format(date));
                log.setType("-");
                log.setValue(Integer.parseInt(editValueEt.getText().toString()));
                log.setOldCurrent(Integer.parseInt(currentTv.getText().toString().trim()));
                log.setNewCurrent(EditUser.getCurrent()-Integer.parseInt(editValueEt.getText().toString().trim()));
                log.setCategory("دعم");

                db.AddLog(log,CurrentUser);
                //Update the UI
                UpdateUser();
            }
        });

        builder.setNegativeButton("خلص بطلت", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    //Set up the Category RecyclerView
    public void CategoryRC_Setup(){
        db.GetCategories(new Callback() {
            @Override
            public void onCallbackCategories(ArrayList<Category> categories) {
                //set the RecyclerView as horizontal list
                LinearLayoutManager layoutManager
                        = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, true);
                categoryAdapter = new CategoryAdapter(MainActivity.this,categories,CurrentUser);
                CategoriesRC.setLayoutManager(layoutManager);
                CategoriesRC.setAdapter(categoryAdapter);
            }

            @Override
            public void onCallbackUser(User user) {
                System.out.println("this should not be printed !");
            }

            @Override
            public void onCallbackLogs(ArrayList<Log> logs) {

            }
        });
    }

    public void CatagoriesFB_Setup(){
        DB db = new DB();
        db.AddCategory("غراض بيت");
        db.AddCategory("أكل جاهز");
        db.AddCategory("ع ابو جنب");
        db.AddCategory("كوي");
        db.AddCategory("صدقه");
        db.AddCategory("بنزين");
    }

    public void LogsRC_Setup(){
        new DB().GetLogs(new Callback() {
            @Override
            public void onCallbackCategories(ArrayList<Category> categories) {

            }

            @Override
            public void onCallbackLogs(ArrayList<Log> logs) {
                LinearLayoutManager layoutManager
                        = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                logAdapter = new LogAdapter(MainActivity.this,logs);
                LogRC.setLayoutManager(layoutManager);
                LogRC.setAdapter(logAdapter);
            }

            @Override
            public void onCallbackUser(User user) {

            }
        });
    }
}

