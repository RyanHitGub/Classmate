package com.example.studymate.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studymate.appActivities.AdminPageActivity;
import com.example.studymate.DbHandler;
import com.example.studymate.R;
import com.example.studymate.appActivities.StudentPageActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static int userID;

    DbHandler dbHandler = new DbHandler(this);

    TextView textView;
    EditText userNameText;
    EditText passwordText;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ensure there is only one lot of table data
        dbHandler.checkTable();

        //access signup page
        textView = findViewById(R.id.txtSignup);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessSignupActivity();
            }
        });

        //login
        //access the student page or admin page
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkLoginInformation()) {
                    userID = dbHandler.GetCurrentUserID(userNameText.getText().toString());
                    if (checkIsAdmin())
                        accessAdminPageActivity();
                    else
                        accessStudentPageActivity();
                }
                else
                    createCustomToast();
            }
        });

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
    }

    //custom toast to match Classmate custom style
    private void createCustomToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast));

        TextView txtLoginToast = layout.findViewById(R.id.txtCustomToast);
        txtLoginToast.setText(R.string.loginToast);
        txtLoginToast.setGravity(Gravity.CENTER);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    //check student's username and password input against the database, looking for a match
    private boolean checkLoginInformation() {

        DbHandler dbHandler = new DbHandler(this);

        userNameText = findViewById(R.id.editUsername);
        passwordText = findViewById(R.id.editPassword);

        return dbHandler.CheckLogin(userNameText.getText().toString(), passwordText.getText().toString());
    }

    //admin account accesses separate activity from students
    private boolean checkIsAdmin(){

        userNameText = findViewById(R.id.editUsername);
        passwordText = findViewById(R.id.editPassword);

       return userNameText.getText().toString().equals("admin") &&
              passwordText.getText().toString().equals("Administrator1!");
    }

    private void accessStudentPageActivity() {
        Intent intent = new Intent(MainActivity.this, StudentPageActivity.class);
        startActivity(intent);
    }

    private void accessSignupActivity() {
        Intent intent = new Intent(MainActivity.this, SignupActivity.class);
        startActivity(intent);
    }

    private void accessAdminPageActivity(){
        Intent intent = new Intent(MainActivity.this, AdminPageActivity.class);
        startActivity(intent);
    }
}
