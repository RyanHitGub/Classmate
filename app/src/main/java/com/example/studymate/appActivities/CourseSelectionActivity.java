package com.example.studymate.appActivities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studymate.DbHandler;
import com.example.studymate.R;
import com.example.studymate.login.MainActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseSelectionActivity extends AppCompatActivity {

    int userID = MainActivity.userID;

    DbHandler dbHandler = new DbHandler(this);

    TextView tempWelcome;

    boolean schoolValid = false;
    boolean programmeValid = false;
    boolean yearValid = false;
    boolean courseValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_selection);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        tempWelcome = findViewById(R.id.txt_tempWelcome);
        tempWelcome.setGravity(Gravity.CENTER);

        //Spinner references
        final Spinner schoolSpinner = findViewById(R.id.spinnerSchoolOf);
        final Spinner itProgrammeSpinner = findViewById(R.id.spinnerIT_Programmes);
        final Spinner yearSpinner = findViewById(R.id.spinnerIT_BIT_Year);
        final Spinner bitYear2CourseSpinner = findViewById(R.id.spinnerIT_BIT_year2_Course);

        //Custom spinners, add information to display
        createSpinner(R.id.spinnerSchoolOf, createSchoolList());
        createSpinner(R.id.spinnerIT_Programmes, createITProgrammeList());
        createSpinner(R.id.spinnerIT_BIT_Year, createYearList());
        createSpinner(R.id.spinnerIT_BIT_year2_Course, createITCourseList());

        Button btnAddCourse = findViewById(R.id.btnAddCourse);
        btnAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (schoolValid && programmeValid && yearValid && courseValid) {
                    //insert data into enrolled courses
                    dbHandler.insertEnrolledCourseDetails(userID,
                            dbHandler.GetCurrentSchoolID(schoolSpinner.getSelectedItem().toString()),
                            dbHandler.GetCurrentProgrammeID(itProgrammeSpinner.getSelectedItem().toString()),
                            dbHandler.GetCurrentYearID(yearSpinner.getSelectedItem().toString()),
                            dbHandler.GetCurrentBITYear2CourseID(bitYear2CourseSpinner.getSelectedItem().toString()));

                    accessStudentPageActivity();
                }
                else
                    createCustomToast();
            }
        });
    }

    private void createCustomToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast));

        TextView txtLoginToast = layout.findViewById(R.id.txtCustomToast);
        txtLoginToast.setText(R.string.courseSelection_Toast);
        txtLoginToast.setGravity(Gravity.CENTER);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }


    private void accessStudentPageActivity() {
        Intent intent = new Intent(CourseSelectionActivity.this, StudentPageActivity.class);
        startActivity(intent);
    }

    //convert database returned lists from an array list with hash map to array list
    //can now be used in create spinner method
    private ArrayList createSchoolList(){

        ArrayList<HashMap<String, String>> schoolsHashMap = dbHandler.GetSchools();
        ArrayList<String> schoolList = new ArrayList<>();
        schoolList.add("School");

        for (Map<String, String> school : schoolsHashMap){
            schoolList.add(school.get("schoolName"));
        }
        return schoolList;
    }

    private ArrayList createITProgrammeList(){

        ArrayList<HashMap<String, String>> programmesHashMap = dbHandler.GetITProgrammes();
        ArrayList<String> programmeList = new ArrayList<>();
        programmeList.add("Programme");

        for (Map<String, String> programme : programmesHashMap){
            programmeList.add(programme.get("programmesITName"));
        }
        return programmeList;
    }

    private ArrayList createYearList(){

        ArrayList<HashMap<String, String>> yearsHashMap = dbHandler.GetYears();
        ArrayList<String> yearList = new ArrayList<>();
        yearList.add("Year");

        for (Map<String, String> year : yearsHashMap){
            yearList.add(year.get("yearName"));
        }
        return yearList;
    }

    private ArrayList createITCourseList(){

        ArrayList<HashMap<String, String>> coursesHashMap = dbHandler.GetITCourses();
        ArrayList<String> courseList = new ArrayList<>();
        courseList.add("Course");

        for (Map<String, String> course : coursesHashMap){
            courseList.add(course.get("courseName"));
        }
        return courseList;
    }

    //Custom Spinner
    private void createSpinner(final int spinner, ArrayList options){

        Spinner spinnerTextView = findViewById(spinner);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_institution, options) {

            //Disable the first item of the spinner, it becomes the hint
            @Override
            public boolean isEnabled(int position){
                if (position != 0){
                    if (spinner == R.id.spinnerSchoolOf)
                        schoolValid = true;
                    else if (spinner == R.id.spinnerIT_Programmes)
                        programmeValid = true;
                    else if (spinner == R.id.spinnerIT_BIT_Year)
                        yearValid = true;
                    else
                        courseValid = true;
                    return true;
                }
                else return false;
            }
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent){
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                if (position == 0){
                    //Set hint colour to grey
                    textView.setTextColor(Color.GRAY);
                }
                else{
                    textView.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_institution);
        spinnerTextView.setAdapter(spinnerArrayAdapter);
        spinnerTextView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                //If user change the default selection
                //First item is disabled and used for hint
                if (position > 0){
                    //Selected Institute is Black
                    TextView textView = (TextView) view;
                    textView.setTextColor(Color.BLACK);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){}
        });
    }
}
