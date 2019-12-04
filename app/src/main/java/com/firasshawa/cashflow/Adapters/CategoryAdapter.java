package com.firasshawa.cashflow.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firasshawa.cashflow.DataModels.Category;
import com.firasshawa.cashflow.DataModels.Log;
import com.firasshawa.cashflow.DataModels.User;
import com.firasshawa.cashflow.Database.DB;
import com.firasshawa.cashflow.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Viewholder> {
    Context context ;
    ArrayList<Category> categories;
    User user;

    Date date = new Date();
    SimpleDateFormat formatDay = new SimpleDateFormat("yyyy-mm-dd");
    SimpleDateFormat formatTime = new SimpleDateFormat("HH:MM:SS");
    public CategoryAdapter(Context context, ArrayList<Category> categories, User user) {
        this.context = context;
        this.categories = categories;
        this.user = user;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.category,parent,false);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        final Category category = categories.get(position);
        holder.name.setText(category.name);
        holder.categoryCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowAlertDialog(category.getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        TextView name;
        CardView categoryCV;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.categoryNameTv);
            categoryCV = itemView.findViewById(R.id.categoryCV);



        }
    }

    public void ShowAlertDialog(final String category){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.payment,null);
        TextView payCategoryTv = view.findViewById(R.id.payCategoryTv);

        Button payBtn = view.findViewById(R.id.payBtn);
        final EditText valueEt = view.findViewById(R.id.valueEt);
        final EditText descEt =  view.findViewById(R.id.descEt);

        payCategoryTv.setText(category);
        builder.setView(view);

        final AlertDialog dialog = builder.create();

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(valueEt.getText().toString().length() != 0) {

                    Log log = new Log();
                    log.setCategory(category);
                    log.setDate(formatDay.format(new Date()));
                    log.setTime(formatTime.format(new Date()));
                    log.setOldCurrent(user.getCurrent());
                    log.setUser(user.getName());
                    log.setType("-");
                    log.setValue(Integer.parseInt(valueEt.getText().toString()));
                    log.setNewCurrent(user.getCurrent() - Integer.parseInt(valueEt.getText().toString()));
                    if(descEt.getText().toString().length() != 0)
                        log.setDesc(descEt.getText().toString().trim());
                    else
                        log.setDesc("مافي تفاصيل...");

                    new DB().AddLog(log,user);

                    Toast.makeText(context, "شكرا...تم تسجيل عملية الشراء!", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }else{
                    Toast.makeText(context, "ادخل قيمة الشراء... وشكرا!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }
}
