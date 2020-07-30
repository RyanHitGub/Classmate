package com.example.studymate.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studymate.DbHandler;
import com.example.studymate.R;
import com.example.studymate.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    DbHandler dbHandler = new DbHandler(this);

    EditText editText;
    Button button;

    EditText fNameEditText;
    TextView fNameErrorText;
    EditText lNameEditText;
    TextView lNameErrorText;
    EditText emailEditText;
    EditText usernameEditText;
    EditText passwordEditText;
    TextView passwordErrorText;

    boolean fNameValid = false;
    boolean lNameValid = false;
    boolean passwordValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

//        createSpinner();

        button = findViewById(R.id.btnSignUp);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fNameValid && lNameValid && passwordValid) {
                    createNewUser();
                    accessMainActivity();
                }
                else{
                    createCustomToast();
                }
            }
        });

        fNameEditText = findViewById(R.id.txtFirstName);
        fNameErrorText = findViewById(R.id.txtFNameError);
        fNameErrorText.setVisibility(View.INVISIBLE);
        fNameEditText.addTextChangedListener(fNameWatcher);

        lNameEditText = findViewById(R.id.txtLastName);
        lNameErrorText = findViewById(R.id.txtLNameError);
        lNameErrorText.setVisibility(View.INVISIBLE);
        lNameEditText.addTextChangedListener(lNameWatcher);

        emailEditText = findViewById(R.id.txtEmail);

        usernameEditText = findViewById(R.id.txtUsername);
        usernameEditText.addTextChangedListener(usernameWatcher);

        passwordEditText = findViewById(R.id.txtPassword);
        passwordErrorText = findViewById(R.id.txtPasswordError);
        passwordErrorText.setVisibility(View.INVISIBLE);
        passwordEditText.addTextChangedListener(passwordWatcher);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
    }

    private void createCustomToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast));

        TextView txtLoginToast = layout.findViewById(R.id.txtCustomToast);
        txtLoginToast.setText(R.string.signUpToast);
        txtLoginToast.setGravity(Gravity.CENTER);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    //first name check input
    private final TextWatcher fNameWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 0 || s.toString().matches(".*[^a-zA-Z ].*")) {
                fNameErrorText.setVisibility(View.VISIBLE);
                fNameErrorText.setText(R.string.fNameError);
                fNameValid = false;

                Toast toast = Toast.makeText(getApplicationContext(), "Valid input: letters", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 10);
                toast.show();
            }
            else{
                fNameErrorText.setVisibility(View.INVISIBLE);
                fNameValid = true;

                usernameEditText.setText(fNameEditText.getText().toString().toLowerCase() +
                        "." +
                        lNameEditText.getText().toString().toLowerCase() +
                        dbHandler.CheckUsernameExists(fNameEditText.getText().toString().toLowerCase(),
                                lNameEditText.getText().toString().toLowerCase()));
            }
        }
    };

    //last name check input
    private final TextWatcher lNameWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 0 || lNameEditText.getText().toString().matches(".*[^a-zA-Z].*")) {
                lNameErrorText.setVisibility(View.VISIBLE);
                lNameErrorText.setText(R.string.lNameError);
                lNameValid = false;

                Toast toast = Toast.makeText(getApplicationContext(), "Valid input: letters", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 10);
                toast.show();
            }
            else{
                lNameErrorText.setVisibility(View.INVISIBLE);
                lNameValid = true;

                usernameEditText.setText(fNameEditText.getText().toString().toLowerCase() +
                                         "." +
                                         lNameEditText.getText().toString().toLowerCase() +
                                         dbHandler.CheckUsernameExists(fNameEditText.getText().toString().toLowerCase(),
                                                                       lNameEditText.getText().toString().toLowerCase()));
            }
        }
    };

    //username check input
    private final TextWatcher usernameWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            emailEditText.setText(usernameEditText.getText().toString() + "@student.weltec.ac.nz");
        }
    };

    //password check input
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

    private void createNewUser() {
        editText = findViewById(R.id.txtFirstName);
        String firstName = editText.getText().toString().toLowerCase();
        editText = findViewById(R.id.txtLastName);
        String lastName = editText.getText().toString().toLowerCase();
        editText = findViewById(R.id.txtInstitute);
        String institute = editText.getText().toString().toLowerCase();
        editText = findViewById(R.id.txtEmail);
        String email = editText.getText().toString().toLowerCase();
        editText = findViewById(R.id.txtUsername);
        String username = editText.getText().toString().toLowerCase();
        editText = findViewById(R.id.txtPassword);
        String password = editText.getText().toString();

        //database code
        //insert information to database
        dbHandler.insertUserDetails(firstName, lastName,
                                    Integer.parseInt(dbHandler.CheckUsernameExists(firstName, lastName)),
                                    institute, email, username, password);
    }

    private void accessMainActivity() {
        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
