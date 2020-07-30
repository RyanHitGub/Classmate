package com.example.studymate.appActivities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studymate.DbHandler;
import com.example.studymate.R;
import com.example.studymate.login.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AccountDetails extends AppCompatActivity {

    int userID = MainActivity.userID;

    Intent intent;
    DbHandler dbHandler = new DbHandler(this);

//    TextView txtFirstName = findViewById(R.id.db_firstName);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView txtFirstName = findViewById(R.id.db_firstName);
        TextView txtLastName = findViewById(R.id.db_lastName);
        TextView txtInstitution = findViewById(R.id.db_institution);
        TextView txtEmail = findViewById(R.id.db_emailAddress);
        TextView txtUsername = findViewById(R.id.db_username);
        final TextView txtPassword = findViewById(R.id.db_password);

        String firstName = "";
        String lastName = "";
        String institution = "";
        String email = "";
        String username = "";
        String password = "";
//        String id = "";

        ArrayList<HashMap<String, String>> currentUserList = dbHandler.GetCurrentUser(String.valueOf(userID));

        for (Map<String, String> item : currentUserList){
            firstName = item.get("firstName");
            lastName = item.get("lastName");
            institution = item.get("institution");
            email = item.get("emailAddress");
            username = item.get("username");
            password = item.get("password");
//            id = item.get("id");
        }

        txtFirstName.setText(firstName);
        txtLastName.setText(lastName);
        txtInstitution.setText(institution);
        txtEmail.setText(email);
        txtUsername.setText(username);
        txtPassword.setText(password);

//        txtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);



//        //create an array list that contains a hash map that will receive the returned value from the GetCurrentUsers() method
//        ArrayList<HashMap<String, String>> currentUserList = dbHandler.GetCurrentUser(String.valueOf(userID));
//        ListView listView = findViewById(R.id.user_list);
//        //list adapter transforms list to a readable format
//        ListAdapter adapter = new SimpleAdapter(AccountDetails.this, currentUserList,
//                R.layout.database_list_row,
//                new String[]{"id", "firstName", "lastName", "institution", "emailAddress", "username", "password"},
//                new int[]{R.id.db_userId, R.id.db_firstName, R.id.db_lastName, R.id.db_institution, R.id.db_emailAddress, R.id.db_username, R.id.db_password});
//        listView.setAdapter(adapter);

        //delete the account, but you have to press twice
        final int[] deleteCount = {1};
        Button deleteBtn = findViewById(R.id.btnDelete);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteCount[0] == 1) {
                    createCustomToast(R.string.firstDeleteToast);
                }
                if (deleteCount[0] == 2) {
                    dbHandler.DeleteUser(userID);
                    intent = new Intent(AccountDetails.this, MainActivity.class);
                    startActivity(intent);
                    createCustomToast(R.string.secondDeleteToast);
                }
                deleteCount[0]++;
            }
        });

        //update account
        Button updateBtn = findViewById(R.id.btnUpdate);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(AccountDetails.this, UpdateUserActivity.class);
                intent.putExtra("id", userID);
                startActivity(intent);
            }
        });


        //
        final Switch passwordSwitch = findViewById(R.id.passwordSwitch);

        passwordSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordSwitch.getText().toString().equals("Show")){
//                    txtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    txtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passwordSwitch.setText(R.string.passwordHide);
                }
                else {
                    txtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passwordSwitch.setText(R.string.passwordShow);
                }
            }
        });
    }

    private void createCustomToast(int text) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast));
        TextView txtLoginToast = (TextView) layout.findViewById(R.id.txtCustomToast);
        txtLoginToast.setText(text);
        txtLoginToast.setGravity(Gravity.CENTER);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}
