package com.example.android2024;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Edit extends AppCompatActivity {
    EditText phone_number1,email1,viewUsername1,viewPassword1;
    Spinner spinner1;
    Button update1,delete1;
    Database myDB;
    String phone_number2,email2,viewUsername2,viewPassword2,spinner2,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.edit_page);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;});

        phone_number1=findViewById(R.id.phone_number);
        email1=findViewById(R.id.email);
        viewUsername1=findViewById(R.id.viewUsername);
        viewPassword1=findViewById(R.id.viewPassword);
        update1=findViewById(R.id.update);
        delete1=findViewById(R.id.delete);
        spinner1=findViewById(R.id.spinner);

        getAndSetIntentData();

        update1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDB = new Database(Edit.this);
                myDB.updateData(id,phone_number1.getText().toString(),email1.getText().toString()
                        ,viewUsername1.getText().toString(),viewPassword1.getText().toString()
                        ,spinner1.getSelectedItem().toString());
                Intent intent = new Intent(Edit.this, Login.class);
                startActivity(intent);
            }
        });

        delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
    }

    public void  startLogin(View view)
    {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("viewUsername") && getIntent().hasExtra("viewPassword")
                && getIntent().hasExtra("phone_number") && getIntent().hasExtra("email")
                && getIntent().hasExtra("spinner") && getIntent().hasExtra("id"))
        {
            //Getting data from intent
            id=getIntent().getStringExtra("id");
            viewUsername2=getIntent().getStringExtra("viewUsername");
            viewPassword2=getIntent().getStringExtra("viewPassword");
            phone_number2=getIntent().getStringExtra("phone_number");
            email2=getIntent().getStringExtra("email");
            spinner2=getIntent().getStringExtra("spinner");
            //Setting intent data
            viewUsername1.setText(viewUsername2);
            viewPassword1.setText(viewPassword2);
            phone_number1.setText(phone_number2);
            email1.setText(email2);

            int pos=0;
            pos=helpWithSpinner(spinner2);
            if(pos==-1)
            {
                Toast.makeText(this, "Something Wrong", Toast.LENGTH_SHORT).show();
            }else
            {
                spinner1.setSelection(pos);
            }
        }
        else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    int helpWithSpinner(String mastoras)
    {
        if(mastoras.equals("Painter")) {
            return 0;
        }else if(mastoras.equals("Carpenter"))
        {
            return 1;
        }else if(mastoras.equals("Gardener"))
        {
            return 2;
        }else if(mastoras.equals("Electrician"))
        {
            return 3;
        }else if(mastoras.equals("Car Engineer"))
        {
            return 4;
        }else if(mastoras.equals("Builder"))
        {
            return 5;
        }else if(mastoras.equals("Refrigerant"))
        {
            return 6;
        }else if(mastoras.equals("Plumber"))
        {
            return 7;
        }else {
            return -1;
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + viewUsername1.getText().toString() + " ?");
        builder.setMessage("Are you sure you want to delete " + viewUsername1.getText().toString() + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Database myDB = new Database(Edit.this);
                myDB.deleteOneRow(id);
                Intent intent = new Intent(Edit.this,Login.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}