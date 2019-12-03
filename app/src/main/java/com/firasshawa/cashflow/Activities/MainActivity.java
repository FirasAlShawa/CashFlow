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
import com.firasshawa.cashflow.Callback;
import com.firasshawa.cashflow.DataModels.Category;
import com.firasshawa.cashflow.DataModels.User;
import com.firasshawa.cashflow.Database.*;
import com.firasshawa.cashflow.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    User CurrentUser = new User();
    String key;

    RecyclerView CategoriesRC;
    TextView currentTv;
    Button increaseBtn,editBtn,supportBtn;

    CategoryAdapter adapter;

    DB db = new DB();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CategoriesRC = findViewById(R.id.categoriesRC);
        currentTv = findViewById(R.id.currentTv);
        increaseBtn = findViewById(R.id.increaseBtn);
        editBtn = findViewById(R.id.editBtn);
        supportBtn = findViewById(R.id.supportBtn);


        CategoryRC_Setup();

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditCurrent();
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
            public void onCallbackUser(User user) {
                CurrentUser = user;
                currentTv.setText(CurrentUser.getCurrent()+"");
            }
        });
    }

    //Update all the user widgets
    public void UpdateUser(){
        currentTv.setText(CurrentUser.getCurrent()+"");
        System.out.println("User Should be Updated");
    }

    //Show Edit Dialog for the user to update the DB
    public void EditCurrent(){
        EditCurrentDialog(MainActivity.this);
    }

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
                        EditUser.setCurrent(Integer.parseInt(editValueEt.getText().toString().trim()));
                        EditUser.setOldCurrent(Integer.parseInt(currentTv.getText().toString().trim()));

                        //Update the db
                        CurrentUser = db.UpdateUser(EditUser);

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
                        = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                adapter = new CategoryAdapter(MainActivity.this,categories);
                CategoriesRC.setLayoutManager(layoutManager);
                CategoriesRC.setAdapter(adapter);
            }

            @Override
            public void onCallbackUser(User user) {
                System.out.println("this should not be printed !");
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
}

