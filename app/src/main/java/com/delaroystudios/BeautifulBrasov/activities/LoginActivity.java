package com.delaroystudios.BeautifulBrasov.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.delaroystudios.BeautifulBrasov.R;
import com.delaroystudios.BeautifulBrasov.helper.InputValidation;
import com.delaroystudios.BeautifulBrasov.sql.DatabaseHelper;
import com.delaroystudios.BeautifulBrasov.utils.PreferenceUtils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = LoginActivity.this;

    private NestedScrollView nestedScrollView;

    /* the layouts of the TextInputEditTexts */
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;

    /* TextInputEditText field to enter the email address */
    private TextInputEditText textInputEditTextEmail;
    /* TextInputEditText field to enter the password */
    private TextInputEditText textInputEditTextPassword;

    /* Button to Login */
    private AppCompatButton appCompatButtonLogin;

    /* Text to go to the register page */
    private AppCompatTextView textViewLinkRegister;
    /* Text to go to forget password page */
    private AppCompatTextView textViewLinkForgotPassword;

    /* instance of the class InputValidation */
    private InputValidation inputValidation;
    /* instance of the class DatabaseHelper */
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        /* call the methods for login */
        initViews();
        initListeners();
        initObjects();
    }

    /**
     * method to find all the views from the login layout
     */
    private void initViews(){
        nestedScrollView = findViewById(R.id.nestedScrollView);

        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);

        textInputEditTextEmail = findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = findViewById(R.id.textInputEditTextPassword);

        appCompatButtonLogin =  findViewById(R.id.appCompatButtonLogin);

        textViewLinkRegister = findViewById(R.id.textViewLinkRegister);
        textViewLinkForgotPassword = findViewById(R.id.forgotPassword);

        /* create the PreferenceUtils object */
        PreferenceUtils utils = new PreferenceUtils();

        /* this check we'll verify if already logged in; if we previously logged in, than the email in the shared prefs is != null */
        /* and the app will start with the UserActivity, and NOT with the Login Activity */
        if (utils.getEmail(this) != null ){
            Intent intent = new Intent(LoginActivity.this, UsersActivity.class);
            startActivity(intent);
        }else{
            // do nothing
        }
    }

    /**
     * method to initialize the listeners
     */
    private void initListeners(){
        /* initialize the listener to the Login Button */
        appCompatButtonLogin.setOnClickListener(this);
        /* initialize the listener to the Register text */
        textViewLinkRegister.setOnClickListener(this);
        /* initialize the listener to the Forgot Password text */
        textViewLinkForgotPassword.setOnClickListener(this);
    }

    /**
     * method to create the objects for the classes that we'll use
     */
    private void initObjects(){
        /* create the dataBase object */
        databaseHelper = new DatabaseHelper(activity);
        /* create the InputValidation object */
        inputValidation = new InputValidation(activity);
    }

    /**
     * method to set the behavior in our activity
     * @param view
     */
    @Override
    public void onClick(View view){
        switch (view.getId()){
            /* if we press the Login Button we'll call the verifyFromSQLite() method */
            case R.id.appCompatButtonLogin:
                verifyFromSQLite();
                break;
                /* if we press the Register text we'll go to the Register Activity */
            case R.id.textViewLinkRegister:
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
            /* if we press the Forgot Password text we'll go to the Forgot Password Activity */
            case R.id.forgotPassword:
                Intent intent = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * this method  - checks all the TextInputEditText fields to be valid
     *              - save to Shared Preferences the email address and the password that the user's trying to login with
     *              - if the email address and the password correspond to one account, we'll land to the UsersActivity
     */
    private void verifyFromSQLite(){
        /* if the email address field is empty, we'll exit the method */
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        /* if the email address is not a valid email address, we'll exit the method */
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        /* if the password field is empty, we'll exit the method */
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_email))) {
            return;
        }
        /* get the data from the email address field and from the password field */
        String email = textInputEditTextEmail.getText().toString().trim();
        String password = textInputEditTextPassword.getText().toString().trim();

        /* we check the data - teh email address and the password - to see if we have a user registered with that data */
        if (databaseHelper.checkUser(email, password)) {
            /*if we do have an user with tis email amd password, we'll save to Shared Preferences that email and that password */
            PreferenceUtils.saveEmail(email, this);
            PreferenceUtils.savePassword(password, this);
            /* we'll go to the UserActivity  view */
            Intent accountsIntent = new Intent(activity, UsersActivity.class);
            /* send the email address that the user entered in the login form  with the intent, to be used in the  UsersActivity view */
            accountsIntent.putExtra("EMAIL", textInputEditTextEmail.getText().toString().trim());
            emptyInputEditText();
            startActivity(accountsIntent);
            finish();
        } else {
            /* if there is no account with that  specific email address and password, show the error */
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * method to empty all the EditTexts
     */
    private void emptyInputEditText(){
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }
}
