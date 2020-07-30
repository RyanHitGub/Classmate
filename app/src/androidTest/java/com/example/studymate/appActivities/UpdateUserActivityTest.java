package com.example.studymate.appActivities;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import com.example.studymate.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class UpdateUserActivityTest {

    @Rule
    public ActivityTestRule<UpdateUserActivity> activityTestRule = new ActivityTestRule<>(UpdateUserActivity.class);

    @Before
    public void setUp() throws Exception {
    }

    //Button invalid input
    @Test
    public void testButtonInvalidInput(){
        Espresso.onView(withId(R.id.btnUpdate)).perform(click());
    }

    //Correct password input update
    //Update fails, test does not have access to database to confirm input
    @Test
    public void testPasswordUpdated(){
        Espresso.onView(withId(R.id.txtPasswordOld)).perform(typeText("Password1!"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.txtPasswordNew)).perform(typeText("Password1!"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.txtPasswordConfirm)).perform(typeText("Password1!"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.btnUpdate)).perform(click());
    }

    //Error messages appearing with incorrect input
    //Update fails, test does not have access to database to confirm input
    @Test
    public void testInputErrorMessages(){
        Espresso.onView(withId(R.id.txtPasswordOld)).perform(typeText("password"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.txtPasswordNew)).perform(typeText("Password1!"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.txtPasswordConfirm)).perform(typeText("Password1!"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.btnUpdate)).perform(click());
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