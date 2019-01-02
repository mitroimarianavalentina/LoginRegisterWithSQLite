package com.delaroystudios.BeautifulBrasov.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.delaroystudios.BeautifulBrasov.model.User;

public class DatabaseHelper  extends SQLiteOpenHelper{

    /**
     * the name of the database
     */
    private static final String DATABASE_NAME = "UserDetails.db";
    /**
     * the database version, witch need to pe updated in case, fo example, we add a new column
     */
    private static final int DATABASE_VERSION = 1;
    /**
     * Name of database table for user
     */
    public static final String TABLE_NAME = "User";

    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    /**
     * define the constant that will create the user table
     */
    private static final String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT,"
            + COLUMN_USER_PASSWORD + " TEXT" + ")";

    /**
     * defines the constant of what table should be deleted
     */
    private String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    /**
     * constructor of the class
     */
    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This method will create our table = User
     *
     * @param sqLiteDatabase - is an SQLite Database object, representing my database = "UserDetails.db"
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
    }

    /**
     * this method deletes the table once it get modified and creates it all over again
     *
     * @param sqLiteDatabase - is an SQLite Database object, representing my database = "UserDetails.db"
     * @param oldVersion     - the number of the old version of the database
     * @param newVersion     - the number of the old version of the database
     */
    @Override
    public  void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion){
        sqLiteDatabase.execSQL(SQL_DELETE_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void addUser(User user){
        /* make the database writable */
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        /* ContentValues is a name value pair, used to insert or update values into database tables */
        ContentValues values = new ContentValues();
        /* add the data to the database */
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        /* the insert method will return -1 if the data was not added successfully to the database */
        sqLiteDatabase.insert(TABLE_NAME, null, values);
        /* close the data base */
        sqLiteDatabase.close();
    }

    /**
     * method to update the password
     * @param email - the password that corresponds to this email address will be updated
     * @param password - the new password that will be saved in tha data base
     */
    public void updatePassword(String email, String password){
        /* make the database writable */
        SQLiteDatabase db = this.getWritableDatabase();
        /* ContentValues is a name value pair, used to insert or update values into database tables */
        ContentValues values = new ContentValues();
        /* add the new password into the database */
        values.put(COLUMN_USER_PASSWORD, password);
        /* update the data base with the new password */
        db.update(TABLE_NAME, values, COLUMN_USER_EMAIL+" = ?",new String[] { email });
        /* close the data base */
        db.close();
    }

    /**
     * method used to check if there is in the data base an user registered with that email address
     * @param email - the email address that we are searching for in the data base
     * @return true - if we find that email address already registered, and false otherwise
     */
    public boolean checkUser(String email){
        /* the column that we are interested in when making the search */
        String[] columns = {
                COLUMN_USER_ID
        };
        /* make the database writable */
        SQLiteDatabase db = this.getWritableDatabase();
        /* Filter results WHERE "email" is what the user enters in the field */
        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = { email };

        /* this cursor will get the row where the column email address contains the email address filled in by the user, IF IT EXISTS */
        Cursor cursor = db.query(TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        /* variable to count if the cursor has found what we were looking for */
        int cursorCount = cursor.getCount();
        /* close the cursor */
        cursor.close();
        /* close the data base */
        db.close();

        /* if we have counted at least 1 entry for that specific email address, it will return true */
        /* else, it will return false, witch means that there is no account made with that specific email address, and can be created one */
        return cursorCount > 0;

    }

    /**
     * overload the method used to check if there is in the data base an user registered with that email address and that specific password
     * @param email - the email address that we are searching for in the data base
     * @param password - the password that we are searching for in the data base, needs to specifically correspond to that email address
     * @return true - if we find that email address already registered, and false otherwise
     */
    public boolean checkUser(String email, String password){
        /* the column that we are interested in when making the search */
        String[] columns = {
                COLUMN_USER_ID
        };
        /* make the database writable */
        SQLiteDatabase db = this.getWritableDatabase();
        /* Filter results WHERE "email" is what the user enters in the field and the password that corresponds to it is the one that the user has typed in */
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " =?";
        String[] selectionArgs = { email, password };

        /* this cursor will get the row that we are interested in, IF IT EXISTS */
        Cursor cursor = db.query(TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        /* variable to count if the cursor has found what we were looking for */
        int cursorCount = cursor.getCount();
        /* close the cursor */
        cursor.close();
        /* close the data base */
        db.close();

        /* if we have counted at least 1 entry for that specific email address and password, it will return true  */
        /* else, it will return false, witch means that there is no account made with that specific email address and password, and can be created one */
        return cursorCount > 0;
    }
}
