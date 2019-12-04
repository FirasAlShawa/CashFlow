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

    public LogAdapter(Context context, ArrayList<Log> logs) {
        this.context = context;
        this.logs = logs;
    }

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
        System.out.println(logs.get(position));
        holder.user.setText(log.user);
        holder.time.setText(log.time);
        holder.date.setText(log.date);
        System.out.println(log.getNewCurrent());
        holder.oldCurrent.setText(log.oldCurrent+" - "+log.value+"\n="+log.getNewCurrent());
        holder.value.setText(log.type+log.value);
        holder.category.setText(log.category);
        holder.desc.setText(log.getDesc());

        switch (log.getType()){
            case "-":
                holder.value.setTextColor(context.getResources().getColor(R.color.Error));
                break;
            case "+":
                holder.value.setTextColor(context.getResources().getColor(R.color.current));
                break;
            case " ":
                holder.value.setTextColor(context.getResources().getColor(R.color.oldCurrent));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return logs.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        TextView user,time,date,oldCurrent,value,category,desc;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            user=itemView.findViewById(R.id.userTv);
            time=itemView.findViewById(R.id.timeTv);
            date=itemView.findViewById(R.id.dateTv);
            oldCurrent=itemView.findViewById(R.id.oldCurrentTv);
            value=itemView.findViewById(R.id.valueTv);
            category=itemView.findViewById(R.id.categoryTv);
            desc = itemView.findViewById(R.id.descTv);
        }
    }
}
