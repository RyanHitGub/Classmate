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

public class AccountDetailsTest {

    @Rule
    public ActivityTestRule<AccountDetails> activityTestRule = new ActivityTestRule<>(AccountDetails.class);

    @Before
    public void setUp() throws Exception {
    }

    //Delete button twice for permanent delete
    @Test
    public void testButtonDelete(){
        Espresso.onView(withId(R.id.btnDelete)).perform(click());
        Espresso.onView(withId(R.id.btnDelete)).perform(click());
    }

    //Update button
    @Test
    public void testButtonUpdate(){
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