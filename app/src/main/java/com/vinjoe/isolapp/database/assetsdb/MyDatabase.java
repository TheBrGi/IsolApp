package com.vinjoe.isolapp.database.assetsdb;

/**
 * Created by BrGi on 11/06/2015.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;


public class MyDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "Luoghi.db";
    private static final int DATABASE_VERSION = 1;
    SQLiteDatabase db = getReadableDatabase();

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgrade();
        // you can use an alternate constructor to specify a database location
        // (such as a folder on the sd card)
        // you must ensure that this folder is available and you have permission
        // to write to it
        //super(context, DATABASE_NAME, context.getExternalFilesDir(null).getAbsolutePath(), null, DATABASE_VERSION);

    }

    public String[] getTables() {
        Cursor c;
        try {
            c = db.rawQuery(
                    "select name from sqlite_master where type = 'table';",
                    null);
        } catch (Exception e) {
            return new String[0];
        }
        int cursorCount = c.getCount();
        String[] list = new String[cursorCount - 1];
        int i = 0;
        c.moveToFirst();
        //all tables less the last one(android_metadata):not needed
        while (i < cursorCount - 1) {
            list[i] = c.getString(c.getColumnIndex("name"));
            i++;
            c.moveToNext();
        }
        c.close();
        return list;
    }

    public Cursor getCategoria(String categoria) {


        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        qb.setTables(categoria);
        //Cursor c = qb.query(db, null, null, null,
        //        null, null, null);
        Cursor c;
        try {
            c = db.rawQuery("select rowid _id,* from [" + categoria + "]", null);
        } catch (Exception e) {
            return null;
        }
        c.moveToFirst();
        return c;

    }

}
