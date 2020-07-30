package com.example.studymate.appActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.studymate.DbHandler;
import com.example.studymate.R;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        DbHandler dbHandler = new DbHandler(this);

        ArrayList<HashMap<String, String>> userList = dbHandler.GetUsers();

        ListView listView = findViewById(R.id.user_list);
        ListAdapter adapter = new SimpleAdapter(AdminPageActivity.this, userList,
                R.layout.database_list_row,
                new String[]{"id", "firstName", "lastName", "institution", "emailAddress", "username", "password"},
                new int[]{R.id.db_userId, R.id.db_firstName, R.id.db_lastName, R.id.db_institution, R.id.db_emailAddress, R.id.db_username, R.id.db_password});
        listView.setAdapter(adapter);
    }
}
