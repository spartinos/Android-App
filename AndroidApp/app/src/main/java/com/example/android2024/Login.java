package com.example.android2024;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Login extends AppCompatActivity {

    private Database db;
    private HashMap<String, String> info;
    private ArrayList<String> user;
    private EditText usernameField, passwordField;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_page);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;});

        db = new Database(Login.this);
        info = new HashMap<>();
        user = new ArrayList<>();
        usernameField = findViewById(R.id.viewUsername);
        passwordField = findViewById(R.id.viewPassword);
        loginButton = findViewById(R.id.login);
        loadInfo();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(checkInfo(usernameField.getText().toString(), passwordField.getText().toString()))
                {
                    user = loadSpecificInfo(usernameField.getText().toString());
                    Intent intent = new Intent(Login.this, Edit.class);

                    intent.putExtra("viewUsername",user.get(5));
                    intent.putExtra("viewPassword",user.get(6));
                    intent.putExtra("id",user.get(0));
                    intent.putExtra("phone_number",user.get(2));
                    intent.putExtra("email",user.get(3));
                    intent.putExtra("spinner",user.get(4));
                    startActivity(intent);
                }
            }
        });
    }


    private void loadInfo()
    {
        Cursor cursor = db.readAllData();
        if(cursor.getCount() != 0)
        {
            while (cursor.moveToNext())
            {
                info.put(cursor.getString(5),cursor.getString(6));
            }
        }
    }

    private ArrayList<String> loadSpecificInfo(String username){
        Cursor cursor = db.readAllData();
        ArrayList<String> userData;

        userData = new ArrayList<>();
        if(cursor.getCount() != 0)
        {
            while (cursor.moveToNext())
            {
                if(Objects.equals(cursor.getString(5),username))
                {
                    userData.add(cursor.getString(0));
                    userData.add(cursor.getString(1));
                    userData.add(cursor.getString(2));
                    userData.add(cursor.getString(3));
                    userData.add(cursor.getString(4));
                    userData.add(cursor.getString(5));
                    userData.add(cursor.getString(6));
                }
            }
        }
        return userData;
    }
    boolean checkInfo(String username, String password)
    {
        if (info.containsKey(username))
        {
            if(Objects.equals(info.get(username), password))
            {
                return true;
            }
            else
            {
                Toast.makeText(this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        else
        {
            Toast.makeText(this, "Wrong username or password", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void  startSignUp(View view)
    {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    public void startFind(View view)
    {
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
    }
}