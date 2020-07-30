package com.example.studymate.login;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import com.example.studymate.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

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
import static org.junit.Assert.*;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
    }
    //UNIT TESTS

    //Invalid login
    @Test
    public void testInvalidInput(){
        Espresso.onView(withId(R.id.btnLogin)).perform(click());
    }

    //Valid student login
    @Test
    public void testValidInput(){
        Espresso.onView(withId(R.id.editUsername)).perform(typeText("ryan.smith001"));
        Espresso.onView(withId(R.id.editPassword)).perform(typeText("Password1!"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.btnLogin)).perform(click());
    }

    //Valid administrator login
    @Test
    public void testValidAdminInput(){
        Espresso.onView(withId(R.id.editUsername)).perform(typeText("admin"));
        Espresso.onView(withId(R.id.editPassword)).perform(typeText("Administrator1!"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.btnLogin)).perform(click());
    }

    //Signup Link
    @Test
    public void testSignupLink(){
        Espresso.onView(withId(R.id.txtSignup)).perform(click());
    }

    //INTEGRATION TESTING

    //Selecting an enrolled in course
    //Integration test: requires login, student page, database, course enrollment
    @Test
    public void testSelectEnrolledCourse(){
        Espresso.onView(withId(R.id.editUsername)).perform(typeText("ryan.smith001"));
        Espresso.onView(withId(R.id.editPassword)).perform(typeText("Password1!"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.btnLogin)).perform(click());

        onView(withId(R.id.action_add_course)).perform(click());

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

        Espresso.onData(anything()).inAdapterView(withId(R.id.course_list)).atPosition(0).perform(click());
    }

    //Removing an enrolled in course
    //Requires running testSelectEnrolledCourse() first
    @Test
    public void testRemovingEnrolledCourse(){
        Espresso.onView(withId(R.id.editUsername)).perform(typeText("ryan.smith001"));
        Espresso.onView(withId(R.id.editPassword)).perform(typeText("Password1!"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.btnLogin)).perform(click());

        Espresso.onData(anything()).inAdapterView(withId(R.id.course_list)).atPosition(0).perform(click());

        Espresso.onView(withId(R.id.btnDelete)).perform(click());
        Espresso.onView(withId(R.id.btnDelete)).perform(click());
    }

    //Update user account password
    @Test
    public void testUpdateUserAccount(){
        Espresso.onView(withId(R.id.editUsername)).perform(typeText("ryan.smith001"));
        Espresso.onView(withId(R.id.editPassword)).perform(typeText("Password1!"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.btnLogin)).perform(click());

        Espresso.onView(withId(R.id.app_bar)).perform(click());
        Espresso.openContextualActionModeOverflowMenu();
        Espresso.onView(withText(R.string.account_info)).perform(click());

        Espresso.onView(withId(R.id.btnUpdate)).perform(click());

        Espresso.onView(withId(R.id.txtPasswordOld)).perform(typeText("Password1!"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.txtPasswordNew)).perform(typeText("Password1!"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.txtPasswordConfirm)).perform(typeText("Password1!"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.btnUpdate)).perform(click());
    }

    //User Deletes their account
    //Make a temporary user account to delete
    @Test
    public void testUserDeleteAccount(){
        Espresso.onView(withId(R.id.txtSignup)).perform(click());

        Espresso.onView(withId(R.id.txtFirstName)).perform(typeText("Temp"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.txtLastName)).perform(typeText("User"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.txtPassword)).perform(typeText("Password1!"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.btnSignUp)).perform(click());

        Espresso.onView(withId(R.id.editUsername)).perform(typeText("temp.user001"));
        Espresso.onView(withId(R.id.editPassword)).perform(typeText("Password1!"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.btnLogin)).perform(click());

        Espresso.onView(withId(R.id.app_bar)).perform(click());
        Espresso.openContextualActionModeOverflowMenu();
        Espresso.onView(withText(R.string.account_info)).perform(click());

        Espresso.onView(withId(R.id.btnDelete)).perform(click());
        Espresso.onView(withId(R.id.btnDelete)).perform(click());
    }

    @After
    public void tearDown() throws Exception {
    }
}