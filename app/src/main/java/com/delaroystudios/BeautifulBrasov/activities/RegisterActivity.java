package com.delaroystudios.BeautifulBrasov.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.delaroystudios.BeautifulBrasov.R;
import com.delaroystudios.BeautifulBrasov.helper.InputValidation;
import com.delaroystudios.BeautifulBrasov.model.User;
import com.delaroystudios.BeautifulBrasov.sql.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = RegisterActivity.this;

    private NestedScrollView nestedScrollView;

    /* the layouts of the TextInputEditTexts */
    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;

    /* TextInputEditText field to enter the username */
    private TextInputEditText textInputEditTextName;
    /* TextInputEditText field to enter the email address */
    private TextInputEditText textInputEditTextEmail;
    /* TextInputEditText field to enter the password */
    private TextInputEditText textInputEditTextPassword;
    /* TextInputEditText field to confirm password */
    private TextInputEditText textInputEditTextConfirmPassword;

    /* Button to register the new user */
    private AppCompatButton appCompatButtonRegister;
    /* Text to go to the login page */
    private AppCompatTextView appCompatTextViewLoginLink;

    /* instance of the class InputValidation */
    private InputValidation inputValidation;
    /* instance of the class DatabaseHelper */
    private DatabaseHelper databaseHelper;
    /* instance of the class User */
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        /* call the methods for registration */
        initViews();
        initListeners();
        initObjects();
    }

    /**
     * method to find all the views from the register layout
     */
    private void initViews(){
        nestedScrollView = findViewById(R.id.nestedScrollView);

        textInputLayoutName = findViewById(R.id.textInputLayoutName);
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);
        textInputLayoutConfirmPassword = findViewById(R.id.textInputLayoutConfirmPassword);

        textInputEditTextName = findViewById(R.id.textInputEditTextName);
        textInputEditTextEmail = findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = findViewById(R.id.textInputEditTextPassword);
        textInputEditTextConfirmPassword = findViewById(R.id.textInputEditTextConfirmPassword);

        appCompatButtonRegister = findViewById(R.id.appCompatButtonRegister);

        appCompatTextViewLoginLink = findViewById(R.id.appCompatTextViewLoginLink);
    }

    /**
     * method to initialize the listeners
     */
    private void initListeners(){
        /* initialize the listener to the Register Button */
        appCompatButtonRegister.setOnClickListener(this);
        /* initialize the listener to the login page */
        appCompatTextViewLoginLink.setOnClickListener(this);
    }

    /**
     * method to create the objects for the classes that we'll use
     */
    private void initObjects(){
        /* create the InputValidation object */
        inputValidation = new InputValidation(activity);
        /* create the dataBase object */
        databaseHelper = new DatabaseHelper(activity);
        /* create the User object */
        user = new User();
    }

    /**
     * method to set the behavior in our activity
     * @param view
     */
    @Override
    public void onClick(View view){
        switch (view.getId()){
            /* if Register Button is pressed, we'll call the postDataToSQLite() method */
            case R.id.appCompatButtonRegister:
                postDataToSQLite();
                break;
            /* if the Login Text is pressed, we'll destroy the register activity, and we'll go to login page by default */
            case R.id.appCompatTextViewLoginLink:
                finish();
                break;
        }
    }

    /**
     * this method  - checks all the TextInputEditText fields to be valid
     *              - adds the new user to the data base
     *              - if we are indeed a new user, we'll land to the Login Activity
     */
    private void postDataToSQLite(){
        /* if the name field is empty, we'll exit the method */
        if (!inputValidation.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, getString(R.string.error_message_name))) {
            return;
        }
        /* if the email address field is empty, we'll exit the method */
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        /* if the email address is not a valid email address, we'll exit the method */
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        /* if the password field is empty, we'll exit the method */
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }
        /* if the two passwords won't match, we'll exit the method */
        if (!inputValidation.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfirmPassword,
                textInputLayoutConfirmPassword, getString(R.string.error_password_match))) {
            return;
        }

        /* if in the database we don't have a specific email address registered yet */
        if (!databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim())) {

            /* we set the name of the user with the value that he entered in the user name field */
            user.setName(textInputEditTextName.getText().toString().trim());
            /* we set the email address of the user with the value that he entered in the email address field */
            user.setEmail(textInputEditTextEmail.getText().toString().trim());
            /* we set the password of the user with the value that teh user entered in the password field */
            user.setPassword(textInputEditTextPassword.getText().toString().trim());

            /* add to the data base the new user */
            databaseHelper.addUser(user);

            /* show the Snack Bar with the success message that record saved successfully */
            Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();
            /* make the intent to go from Registration activity to Login Activity */
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();

        }
        /* if in the data base we already have an user registered with that specific email address */
            else {
                // show the Snack Bar with an error message that record already exists
                Snackbar.make(nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * method to empty all the EditTexts
     */
    private void emptyInputEditText(){
        textInputEditTextName.setText(null);
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextConfirmPassword.setText(null);
    }

}
