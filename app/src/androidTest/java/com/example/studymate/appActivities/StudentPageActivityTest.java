package com.example.studymate.appActivities;

import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;
import com.example.studymate.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;

public class StudentPageActivityTest {

    @Rule
    public ActivityTestRule<StudentPageActivity> activityTestRule = new ActivityTestRule<>(StudentPageActivity.class);

    @Before
    public void setUp() throws Exception {

        activityTestRule.getActivity().userID = 2;
//        ArrayList<HashMap<String, String>> tempList = new ArrayList<>();
//        HashMap<String,String> enrolledCourse = new HashMap<>();
//        enrolledCourse.put("firstName", "ryan");
//        enrolledCourse.put("schoolName", "Weltec");
//        enrolledCourse.put("programmesITName", "Bachelor of Information Technology (Level 7)");
//        enrolledCourse.put("yearName", "Year 2");
//        enrolledCourse.put("courseName", "SD6501 Mobile Application Development");
//        tempList.add(enrolledCourse);
//
//        activityTestRule.getActivity().currentUserEnrolledCoursesList = tempList;

    }

    //Menu items working
    //Add course item
    @Test
    public void testMenuItemAddCourse(){
        onView(withId(R.id.action_add_course)).perform(click());
    }

    //Activity item
    @Test
    public void testMenuItemActivity(){
        Espresso.onView(withId(R.id.app_bar)).perform(click());
        Espresso.openContextualActionModeOverflowMenu();
        Espresso.onView(withText(R.string.account_info)).perform(click());
    }

    //Settings item
    @Test
    public void testMenuItemSettings(){
        Espresso.onView(withId(R.id.app_bar)).perform(click());
        Espresso.openContextualActionModeOverflowMenu();
        Espresso.onView(withText(R.string.settings_name)).perform(click());
    }

    @After
    public void tearDown() throws Exception {
    }
}