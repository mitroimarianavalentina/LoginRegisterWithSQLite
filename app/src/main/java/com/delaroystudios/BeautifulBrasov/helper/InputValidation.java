package com.delaroystudios.BeautifulBrasov.helper;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public class InputValidation {

    /* variable for the context */
    private Context context;

    /* the constructor of the class that will update the context of the activity we'll be in every time we make an object */
    public InputValidation(Context context){
        this.context = context;
    }

    /**
     * method to verify any of our EditTexts if are empty
     * @param textInputEditText - the edit text where the user will input something
     * @param textInputLayout - the layout of the edit text. We'll use the layout only to throw an error on it, if the editText won't pass the validation
     * @param message - the error message that we'll throw on the Input Layout
     * @return true - if the validation is correct, and false otherwise
     */
    public boolean isInputEditTextFilled(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String message){
        /* variable to hold the text filled in by the user in the TextInputEditText */
        String value = textInputEditText.getText().toString().trim();
        /* if the TextInputEditText is empty */
        if (value.isEmpty()){
            /* we'll throw an error on the TextInputLayout */
            textInputLayout.setError(message);
            /* call the method to hide the keyboard */
            hideKeyboardFrom(textInputEditText);
            /* return false, because the field DID NOT passed the validation */
            return false;
        } else {
            /* if the field passed the validation, we'll throw no error, and the method will return true */
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    /**
     * method used to check if the email address is a valid email address
     * @param textInputEditText - the edit text where the user will input the email address
     * @param textInputLayout - the layout of the email address edit text. We'll use the layout only to throw an error on it, if the editText won't pass the validation
     * @param message - the error message that we'll throw on the Input Layout
     * @return true - if the email address is a valid one, and false otherwise
     */
    public boolean isInputEditTextEmail(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String message){
        /* variable to hold the email address filled in by the user in the email TextInputEditText */
        String value = textInputEditText.getText().toString().trim();
        /* if the TextInputEditText is empty  OR the form of the email address is not valid*/
        if (value.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(value).matches()){
            /* we'll throw an error on the TextInputLayout */
            textInputLayout.setError(message);
            /* call the method to hide the keyboard */
            hideKeyboardFrom(textInputEditText);
            /* return false, because the email address DID NOT passed the validation */
            return false;
        }else {
            /* if the email address passed the validation, we'll throw no error, and the method will return true */
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    /**
     * method to chech if the password and confirm password are a match
     * @param textInputEditText1 - the edit text where the user will input the password
     * @param textInputEditText2 - the edit text where the user will input confirm  password
     * @param textInputLayout - the layout of the password edit text. We'll use the layout only to throw an error on it, if the editText won't pass the validation
     * @param message - the error message that we'll throw on the Input Layout
     * @return true - if the two password are a match, and false otherwise
     */
    public boolean isInputEditTextMatches(TextInputEditText textInputEditText1, TextInputEditText textInputEditText2, TextInputLayout textInputLayout, String message ){
        /* variable to hold the password filled in by the user in the password TextInputEditText */
        String value1 = textInputEditText1.getText().toString().trim();
        /* variable to hold confirm email address filled in by the user in the confirm email address TextInputEditText */
        String value2 = textInputEditText2.getText().toString().trim();
        /* if the two password won't match */
        if (!value1.contentEquals(value2)){
            /* we'll throw an error on the TextInputLayout */
            textInputLayout.setError(message);
            /* call the method to hide the keyboard */
            hideKeyboardFrom(textInputEditText2);
            return false;
        }else {
            /* if the two password are a match, we'll throw no error, and the method will return true */
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    /**
     * method to hide the keyboard
     * @param view
     */
    private void hideKeyboardFrom(View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
