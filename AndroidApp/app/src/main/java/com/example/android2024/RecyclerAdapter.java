package com.example.android2024;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    Context context;
    private ArrayList name, email, phone, job;

    public RecyclerAdapter(Context context, ArrayList name, ArrayList email, ArrayList phone, ArrayList job) {
        this.context = context;
        this.name=name;
        this.phone=phone;
        this.email=email;
        this.job=job;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //creates new view holders for recycler items
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.recycler_item,parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //binds the data from the list to the views

        holder.name.setText(String.valueOf(name.get(position)));
        holder.email.setText(String.valueOf(email.get(position)));
        holder.phone.setText(String.valueOf(phone.get(position)));
        holder.job.setText(String.valueOf(job.get(position)));
    }

    @Override
    public int getItemCount()
    {
        return name.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        //holds references to the views within the layout
        TextView name, email, phone, job;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.viewName);
            email = itemView.findViewById(R.id.viewEmail);
            phone = itemView.findViewById(R.id.viewPhone);
            job = itemView.findViewById(R.id.viewJob);
        }

    }
}
