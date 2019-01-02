package com.delaroystudios.BeautifulBrasov.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Toast;

import com.delaroystudios.BeautifulBrasov.R;
import com.delaroystudios.BeautifulBrasov.helper.InputValidation;
import com.delaroystudios.BeautifulBrasov.sql.DatabaseHelper;

public class ForgotPassword extends AppCompatActivity {

    /* TextInputEditText field to enter the email address */
    private TextInputEditText textInputEditTextEmail;
    /* the layout of the email TextInputEditText */
    private TextInputLayout textInputLayoutEmail;

    /* Button to confirm the email address associated with the account whose password I forgot */
    private AppCompatButton appCompatButtonConfirm;

    /* instance of the class InputValidation */
    private InputValidation inputValidation;
    /* instance of the class DatabaseHelper */
    private DatabaseHelper databaseHelper;

    private NestedScrollView nestedScrollView;

    //  textInputLayout.setError(message);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);


        /* find all the views from the layout */
        textInputEditTextEmail = findViewById(R.id.textInputEditTextEmail);
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail);
        appCompatButtonConfirm = findViewById(R.id.appCompatButtonConfirm);
        nestedScrollView = findViewById(R.id.nestedScrollView);

        /* create the dataBase object */
        databaseHelper = new DatabaseHelper(this);
        /* create the InputValidation object */
        inputValidation = new InputValidation(this);

        /* set the title of the layout */
        setTitle("Recover password");
        appCompatButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            /* when clicking on the Confirm Button, we'll call verifyFromSQLite() method */
            public void onClick(View view) {
                verifyFromSQLite();
            }
        });

    }

    /**
     * this method -
     */
    private void verifyFromSQLite(){

        /* if teh email address field is empty, we'll throw an error to warn the user */
        if (textInputEditTextEmail.getText().toString().isEmpty()){
            textInputLayoutEmail.setError("Please fill your email");
            return;
        }


        /* if this email address it is indeed associated with an account */
        if (databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim())) {
            /* we''l go to the Confirm Password layout */
            Intent accountsIntent = new Intent(this, ConfirmPassword.class);
            /* send the email address that the user entered in the Confirm Password form  with the intent, to be used in the  UsersActivity view */
            accountsIntent.putExtra("EMAIL", textInputEditTextEmail.getText().toString().trim());
            emptyInputEditText();
            startActivity(accountsIntent);
        } else {
            /* if teh email address entered in the Confirm Password field is not associated with an account, we'll get a Snackbar */
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * method to empty the email address field
     */
    private void emptyInputEditText(){
        textInputEditTextEmail.setText("");
    }
}
