package com.example.studymate.appActivities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateUserActivity extends AppCompatActivity {

    int userID = MainActivity.userID;

    DbHandler dbHandler = new DbHandler(this);

    boolean passwordValid = true;

    EditText passwordOldEditText;
    EditText passwordNewEditText;
    EditText passwordConfirmEditText;
    TextView passwordErrorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        passwordOldEditText = findViewById(R.id.txtPasswordOld);
        passwordNewEditText = findViewById(R.id.txtPasswordNew);
        passwordConfirmEditText = findViewById(R.id.txtPasswordConfirm);
        passwordErrorText = findViewById(R.id.txtPasswordError);
        passwordErrorText.setVisibility(View.INVISIBLE);
        passwordNewEditText.addTextChangedListener(passwordWatcher);

        String firstName = "";
        String lastName = "";
        String institution = "";
        String email = "";
        String username = "";
        String password = "";
        String id = "";

        ArrayList<HashMap<String, String>> currentUserList = dbHandler.GetCurrentUser(String.valueOf(userID));

        for (Map<String, String> item : currentUserList){
            firstName = item.get("firstName");
            lastName = item.get("lastName");
            institution = item.get("institution");
            email = item.get("emailAddress");
            username = item.get("username");
            password = item.get("password");
            id = item.get("id");
        }

        Button updateBtn = findViewById(R.id.btnUpdate);
        final String finalFirstName = firstName;
        final String finalLastName = lastName;
        final String finalInstitution = institution;
        final String finalEmail = email;
        final String finalUsername = username;
        final String finalPassword = password;
        final String finalId = id;

        //press update button
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Old password does not match database stored password
                if (!passwordOldEditText.getText().toString().equals(finalPassword)){
                    createCustomToast(R.string.passwordUpdateFail);
                }
                //New password is invalid or null
                else if (!passwordValid || passwordNewEditText.getText().toString().equals("")) {
                    createCustomToast(R.string.passwordUpdateFail2);
                }
                //Confirm password does not match new password
                else if (!passwordConfirmEditText.getText().toString().equals(passwordNewEditText.getText().toString())){
                    createCustomToast(R.string.passwordUpdateFail3);
                }
                //The new password is assigned to the user
                else{
                    dbHandler.UpdateUserDetails(finalFirstName, finalLastName, finalInstitution, finalEmail,
                            finalUsername, passwordConfirmEditText.getText().toString(), Integer.parseInt(finalId));
                    createCustomToast(R.string.updateComplete);
                    accessAccountDetails();
                }
            }
        });
    }

    private void accessAccountDetails(){
        Intent intent = new Intent(UpdateUserActivity.this, AccountDetails.class);
        intent.putExtra("id", userID);
        startActivity(intent);
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

    private final TextWatcher passwordWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            Pattern pattern;
            Matcher matcher;
            final String passwordRule = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*])(?=\\S+$).{8,16}$";
            pattern = Pattern.compile(passwordRule);
            matcher = pattern.matcher(s);
            if (s.length() == 0 || !matcher.matches()) {
                passwordErrorText.setVisibility(View.VISIBLE);
                passwordErrorText.setText(R.string.passwordError);
                passwordValid = false;

                if (passwordNewEditText.getText().toString().equals("")){
                    passwordErrorText.setVisibility(View.INVISIBLE);
                    passwordValid = true;
                }

                Toast toast = Toast.makeText(getApplicationContext(), "Valid input: 1 lowercase, 1 uppercase, 1 number, 1 special character, 8 min, 16 max", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 10);
                toast.show();
            }
            else{
                passwordErrorText.setVisibility(View.INVISIBLE);
                passwordValid = true;
            }
        }
    };
}
