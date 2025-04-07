package com.example.android2024;

import static android.widget.Toast.LENGTH_SHORT;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
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

public class SignUp extends AppCompatActivity {

    EditText name_input, email_input, phone_input, username_input, password_input;
    Spinner job_input;
    Button sign_up;
    Database db;
    ArrayList<String> usernames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.signup_page);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new Database(SignUp.this);
        name_input = findViewById(R.id.name);
        email_input = findViewById(R.id.emailSignup);
        phone_input = findViewById(R.id.phoneSignup);
        username_input = findViewById(R.id.viewUsername);
        password_input = findViewById(R.id.viewPassword);

        usernames = new ArrayList<>();

        job_input = findViewById(R.id.spinner);

        sign_up = findViewById(R.id.signup);
        sign_up.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Database db = new Database(SignUp.this);
                if (checkInfo(username_input.getText().toString()))
                {
                    db.addUser(name_input.getText().toString().trim(),
                            email_input.getText().toString().trim(),
                            phone_input.getText().toString().trim(),
                            job_input.getSelectedItem().toString().trim(),
                            username_input.getText().toString().trim(),
                            password_input.getText().toString().trim());
                    Intent intent = new Intent(SignUp.this, Login.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean checkInfo(String username)
    {
        Cursor cursor = db.readAllData();
        if(cursor.getCount() != 0)
        {
            while (cursor.moveToNext())
            {
                usernames.add(cursor.getString(5));
            }
        }
        if(usernames.contains(username))
        {
            Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            return true;
        }
    }
}