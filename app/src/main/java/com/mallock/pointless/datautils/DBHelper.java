package com.mallock.pointless.datautils;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mallock on 02-10-2016.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "myDatabase";
    public static final String TABLE_NAME = "users";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_HOBBY = "hobby";
    public static final String COLUMN_PASSWORD = "password";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+
                "( "+COLUMN_USERNAME+" varchar(20) primary key, "
                +COLUMN_NAME+" varchar(20), "
                +COLUMN_PHONE+" varchar(20), "
                +COLUMN_HOBBY+" varchar(20), "
                +COLUMN_PASSWORD+" varchar(20) "
                +");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
