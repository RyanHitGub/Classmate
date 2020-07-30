package com.example.studymate.appActivities;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import com.example.studymate.R;
import com.example.studymate.login.SignupActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;

public class CourseSelectionActivityTest {

    @Rule
    public ActivityTestRule<CourseSelectionActivity> activityTestRule = new ActivityTestRule<>(CourseSelectionActivity.class);

    @Before
    public void setUp() throws Exception {
    }

    //Toolbar back button
    @Test
    public void testToolbarBackButton(){
        Espresso.onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());
    }

    //Button invalid input
    @Test
    public void testButtonInvalidInput(){
        Espresso.onView(withId(R.id.btnAddCourse)).perform(click());
    }

    //Button valid input
    @Test
    public void testButtonValidInput(){
        Espresso.onView(withId(R.id.spinnerSchoolOf)).perform(click());
        Espresso.onData(allOf(is(instanceOf(String.class)), is("Information Technology School"))).perform(click());
        Espresso.onView(withId(R.id.spinnerSchoolOf)).check(matches(withSpinnerText(containsString("Information Technology School"))));

        Espresso.onView(withId(R.id.spinnerIT_Programmes)).perform(click());
        Espresso.onData(allOf(is(instanceOf(String.class)), is("Bachelor of Information Technology (Level 7)"))).perform(click());
        Espresso.onView(withId(R.id.spinnerIT_Programmes)).check(matches(withSpinnerText(containsString("Bachelor of Information Technology (Level 7)"))));

        Espresso.onView(withId(R.id.spinnerIT_BIT_Year)).perform(click());
        Espresso.onData(allOf(is(instanceOf(String.class)), is("Year 2"))).perform(click());
        Espresso.onView(withId(R.id.spinnerIT_BIT_Year)).check(matches(withSpinnerText(containsString("Year 2"))));

        Espresso.onView(withId(R.id.spinnerIT_BIT_year2_Course)).perform(click());
        Espresso.onData(allOf(is(instanceOf(String.class)), is("SD6501 Mobile Application Development"))).perform(click());
        Espresso.onView(withId(R.id.spinnerIT_BIT_year2_Course)).check(matches(withSpinnerText(containsString("SD6501 Mobile Application Development"))));

        Espresso.onView(withId(R.id.btnAddCourse)).perform(click());
    }

    @After
    public void tearDown() throws Exception {
    }
}