package com.example.studymate.appActivities;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import com.example.studymate.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class CourseDetailsTest {

    @Rule
    public ActivityTestRule<CourseDetails> activityTestRule = new ActivityTestRule<>(CourseDetails.class);

    @Before
    public void setUp() throws Exception {
    }

    //Delete button twice for permanent delete
    @Test
    public void testButtonDelete(){
        Espresso.onView(withId(R.id.btnDelete)).perform(click());
        Espresso.onView(withId(R.id.btnDelete)).perform(click());
    }

    //Toolbar back button
    @Test
    public void testToolbarBackButton(){
        Espresso.onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());
    }

    @After
    public void tearDown() throws Exception {
    }
}