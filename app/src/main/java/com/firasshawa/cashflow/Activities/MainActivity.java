package com.firasshawa.cashflow.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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


    public void GetUser(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        key = prefs.getString(getString(R.string.UserKeyToken), "");
        db.GetUser(key,new Callback(){
            @Override
            public void onCallbackCategories(ArrayList<Category> categories) {

            }

            @Override
            public void onCallbackUser(User user) {
//                CurrentUser.setKey(user.getKey());
//                CurrentUser.setName(user.getKey());
                CurrentUser = user;
                currentTv.setText(CurrentUser.getCurrent()+"");
            }

        });
    }
    public void UpdateUser(){
        currentTv.setText(CurrentUser.getCurrent()+"");
        System.out.println("User Should be Updated");
    }

    public void EditCurrent(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final View view = getLayoutInflater().inflate(R.layout.edit_current_layout,null);
        builder.setTitle("كم معك بالجزدان؟");
        builder.setView(view);
        builder.setPositiveButton("تمام", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
              EditText editValueEt = view.findViewById(R.id.editValueEt);

              User EditUser = CurrentUser;

              EditUser.setCurrent(Integer.parseInt(editValueEt.getText().toString().trim()));
              EditUser.setOldCurrent(Integer.parseInt(currentTv.getText().toString().trim()));

               CurrentUser = db.UpdateUser(EditUser.getKey(),EditUser);
               UpdateUser();
            }
        });
        builder.setNegativeButton("خلص بطلت", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void CategoryRC_Setup(){
        db.GetCategories(new Callback() {
            @Override
            public void onCallbackCategories(ArrayList<Category> categories) {
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

