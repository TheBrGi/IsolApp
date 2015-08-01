package com.vinjoe.isolapp.database.standarddb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by BrGi on 11/06/2015.
 */
public class DbHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "BILLBOOK";

    public DbHelper(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String q = "CREATE TABLE " + DatabaseStrings.TBL_NAME +
                " ( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                DatabaseStrings.FIELD_SUBJECT + " TEXT," +
                DatabaseStrings.FIELD_TEXT + " TEXT," +
                DatabaseStrings.FIELD_DATE + " TEXT)";
        db.execSQL(q);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}

