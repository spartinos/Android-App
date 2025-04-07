package com.example.android2024;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.jar.Attributes;

public class Main extends AppCompatActivity {

    private RecyclerView recyclerView;

    private RecyclerAdapter adapter;

    private Database db;
    private ArrayList<String> name, email, phone, job;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.main_page);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;});

        db = new Database(Main.this);
        name = new ArrayList<>();
        email = new ArrayList<>();
        phone = new ArrayList<>();
        job = new ArrayList<>();
        storeData();

        recyclerView = findViewById(R.id.Recycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerAdapter(Main.this, name, email, phone, job);
        recyclerView.setAdapter(adapter);
    }

     void storeData()
     {
         Cursor cursor = db.readAllData();
         if(cursor.getCount() == 0)
         {
             Toast.makeText(this, "No data. ", Toast.LENGTH_SHORT).show();
         }
         else
         {
             while (cursor.moveToNext())
             {
                 name.add(cursor.getString(1));
                 phone.add(cursor.getString(2));
                 email.add(cursor.getString(3));
                 job.add(cursor.getString(4));
             }

         }
     }

    public void startLogin(View view)
    {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

}