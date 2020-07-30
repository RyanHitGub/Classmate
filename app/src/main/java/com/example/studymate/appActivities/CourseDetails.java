package com.example.studymate.appActivities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studymate.DbHandler;
import com.example.studymate.R;
import com.example.studymate.login.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class CourseDetails extends AppCompatActivity {

    int userID = MainActivity.userID;
    int courseID = StudentPageActivity.courseID;

    DbHandler dbHandler = new DbHandler(this);

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //display the enrolled courses
        ArrayList<HashMap<String, String>> currentUserEnrolledCoursesList =
                dbHandler.GetCurrentUserEnrolledCourse(String.valueOf(userID), String.valueOf(courseID));
        ListView listView = findViewById(R.id.course_list);
        //list adapter transforms list to a readable format
        ListAdapter adapter = new SimpleAdapter(CourseDetails.this, currentUserEnrolledCoursesList,
                R.layout.database_enrolled_course_list_row,
                new String[]{"firstName", "schoolName", "programmesITName", "yearName", "courseName"},
                new int[]{R.id.db_enrolledUser, R.id.db_enrolledSchool, R.id.db_enrolledProgramme,
                        R.id.db_enrolledYear, R.id.db_enrolledCourseName});
        listView.setAdapter(adapter);

        final int[] deleteCount = {1};
        Button btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteCount[0] == 1) {
                    createCustomToast(R.string.firstRemoveToast);
                }
                if (deleteCount[0] == 2) {
                    dbHandler.DeleteEnrolledCourse(courseID);
                    intent = new Intent(CourseDetails.this, StudentPageActivity.class);
                    startActivity(intent);
                    createCustomToast(R.string.secondRemoveToast);
                }
                deleteCount[0]++;
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
