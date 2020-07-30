package com.example.studymate.appActivities;

import android.app.LauncherActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studymate.DbHandler;
import com.example.studymate.R;
import com.example.studymate.SettingsActivity;
import com.example.studymate.Student;
import com.example.studymate.login.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class StudentPageActivity extends AppCompatActivity {

    public static int courseID;
    int userID = MainActivity.userID;

    DbHandler dbHandler = new DbHandler(this);

    ArrayList<HashMap<String, String>> currentUserEnrolledCoursesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_page);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        //display the enrolled courses
        currentUserEnrolledCoursesList = dbHandler.GetCurrentUserEnrolledCourses(String.valueOf(userID));
        final ListView listView = findViewById(R.id.course_list);
        //list adapter transforms list to a readable format
        final ListAdapter adapter = new SimpleAdapter(StudentPageActivity.this, currentUserEnrolledCoursesList,
                R.layout.database_enrolled_course_list_row,
                new String[]{"firstName", "schoolName", "programmesITName", "yearName", "courseName"},
                new int[]{R.id.db_enrolledUser, R.id.db_enrolledSchool, R.id.db_enrolledProgramme,
                        R.id.db_enrolledYear, R.id.db_enrolledCourseName});
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = view.findViewById(R.id.db_enrolledCourseName);
                courseID = dbHandler.GetCurrentCourseID(String.valueOf(userID),
                                             textView.getText().toString());

//                Toast.makeText(StudentPageActivity.this, textView.getText().toString(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(StudentPageActivity.this, CourseDetails.class);
                startActivity(intent);

            }
        });
    }

    //needed to display toolbar options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Intent settings = new Intent(this, SettingsActivity.class);
                startActivity(settings);
                break;

            case R.id.action_add_course:
                Intent addCourse = new Intent(this, CourseSelectionActivity.class);
                startActivity(addCourse);
                break;

            case R.id.action_account_information:
                Intent account = new Intent(this, AccountDetails.class);
                startActivity(account);
                break;

            default:
        }
        return super.onOptionsItemSelected(item);
    }
}
