package com.example.studymate.login;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import com.example.studymate.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class SignupActivityTest {

    @Rule
    public ActivityTestRule<SignupActivity> activityTestRule = new ActivityTestRule<>(SignupActivity.class);

    @Before
    public void setUp() throws Exception {
    }

    //Button invalid input
    @Test
    public void testButtonInvalidInput(){
        Espresso.onView(withId(R.id.btnSignUp)).perform(click());
    }

    //Error messages appearing with incorrect input
    @Test
    public void testInputErrorMessages(){
        Espresso.onView(withId(R.id.txtFirstName)).perform(typeText("ry4n"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.txtLastName)).perform(typeText("sm1th"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.txtPassword)).perform(typeText("password"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.btnSignUp)).perform(click());
    }

    //Button valid input
    @Test
    public void testButtonValidInput(){
        Espresso.onView(withId(R.id.txtFirstName)).perform(typeText("ryan"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.txtLastName)).perform(typeText("smith"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.txtPassword)).perform(typeText("Password1!"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.btnSignUp)).perform(click());
    }

    @After
    public void tearDown() throws Exception {
    }
}