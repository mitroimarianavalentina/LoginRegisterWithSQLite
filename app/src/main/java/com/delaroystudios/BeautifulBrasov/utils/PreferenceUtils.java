package com.delaroystudios.BeautifulBrasov.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUtils {

    /* empty constructor of the class */
    public PreferenceUtils(){

    }

    /**
     * method to save the email in Shared Preferences
     * @param email - the email address that will be saved in Shared Preferences
     * @param context - the context where we'll be using this method
     */
    public static void saveEmail(String email, Context context) {
        /* create an object of the Shared Preferences */
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        /* prepare the Shared Preferences to be written */
        SharedPreferences.Editor prefsEditor = prefs.edit();
        /* save the email in Shared Preferences using the constant KEY_EMAIL */
        prefsEditor.putString(Constants.KEY_EMAIL, email);
        /* apply the new changes in the Shared Preferences */
        prefsEditor.apply();
    }

    /**
     * method to save the email in Shared Preferences
     * @param password - the password that will be saved in Shared Preferences
     * @param context - the context where we'll be using this method
     */
    public static void savePassword(String password, Context context) {
        /* create an object of the Shared Preferences */
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        /* prepare the Shared Preferences to be written */
        SharedPreferences.Editor prefsEditor = prefs.edit();
        /* save the password in Shared Preferences using the constant KEY_PASSWORD */
        prefsEditor.putString(Constants.KEY_PASSWORD, password);
        /* apply the new changes in the Shared Preferences */
        prefsEditor.apply();
    }

    /**
     * method to get the email address from Shared Preferences
     * @param context - the context where we'll be using this method
     * @return the info that's in the constant KEY_EMAIL, if nothing is there, then will return the default value - null -
     */
    public static String getEmail(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_EMAIL, null);
    }

    /* this method is never used */
//    public static String getPassword(Context context) {
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
//        return prefs.getString(Constants.KEY_PASSWORD, null);
//    }

    // ***********************************************************************************************//

//    public static boolean saveEmail(String email, Context context) {
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences.Editor prefsEditor = prefs.edit();
//        prefsEditor.putString(Constants.KEY_EMAIL, email);
//        prefsEditor.apply();
//        return true;
//    }

//    public static String getEmail(Context context) {
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
//        return prefs.getString(Constants.KEY_EMAIL, null);
//    }

//    public static boolean savePassword(String password, Context context) {
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences.Editor prefsEditor = prefs.edit();
//        prefsEditor.putString(Constants.KEY_PASSWORD, password);
//        prefsEditor.apply();
//        return true;
//    }

//    public static String getPassword(Context context) {
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
//        return prefs.getString(Constants.KEY_PASSWORD, null);
//    }
}
