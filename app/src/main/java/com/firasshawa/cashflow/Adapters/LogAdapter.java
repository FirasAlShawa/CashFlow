package com.firasshawa.cashflow.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firasshawa.cashflow.DataModels.Log;
import com.firasshawa.cashflow.R;

import java.util.ArrayList;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.Viewholder> {
    Context context ;
    ArrayList<Log> logs;

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.log_message,parent,false);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Log log = logs.get(position);

        holder.user.setText(log.user);
        holder.time.setText(log.time);
        holder.date.setText(log.date);
        holder.oldCurrent.setText(log.oldCurrent);
        holder.value.setText(log.value);
        holder.category.setText(log.category);

    }

    @Override
    public int getItemCount() {
        return logs.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        TextView user,time,date,oldCurrent,value,category;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            user=itemView.findViewById(R.id.userTv);
            time=itemView.findViewById(R.id.timeTv);
            date=itemView.findViewById(R.id.dateTv);
            oldCurrent=itemView.findViewById(R.id.oldCurrentTv);
            value=itemView.findViewById(R.id.valueTv);
            category=itemView.findViewById(R.id.categoryTv);
        }
    }
}
