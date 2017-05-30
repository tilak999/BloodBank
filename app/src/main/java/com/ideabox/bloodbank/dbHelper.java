package com.ideabox.bloodbank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class dbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Doner.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE Doner (" +
                    "Id INTEGER PRIMARY KEY," +
                    "Name TEXT ,"+
                    "Email TEXT ,"+
                    "Phone TEXT ,"+
                    "Addr TEXT ,"+
                    "BloodGroup TEXT ,"+
                    "City TEXT ,"+
                    "Area TEXT"+
            " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS Doner";

    public dbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    //User defined methods Methods

    public static void insert(DonerData doner,Context ctx)
    {
        dbHelper dbhelper = new dbHelper(ctx);
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        Log.d("info", "insert: "+ doner.city+doner.area+doner.bloodgrp);

        ContentValues values = new ContentValues();
        values.put("Name", doner.full_name);
        values.put("Email", doner.email);
        values.put("Phone", doner.phone);
        values.put("Addr", doner.addr);
        values.put("BloodGroup", doner.bloodgrp);
        values.put("City", doner.city);
        values.put("Area", doner.area);

        db.insert("Doner", "id", values);
        db.close();
    }

    public static ArrayList<DonerData> getDonerList(Context ctx, String bloodgrp, String city)
    {
        ArrayList<DonerData> data = new  ArrayList<>();

        Log.d("info", "getDonerList: "+bloodgrp+city);

        dbHelper dbhelper = new dbHelper(ctx);
        SQLiteDatabase db = dbhelper.getReadableDatabase();

        String[] projection = {
                "ID",
                "Name",
                "City",
                "Area"
        };

        String where = "BloodGroup LIKE ? AND City LIKE ?";

        String[] values = {
                bloodgrp,
                city
        };

        String sortOrder = "Id DESC";

        Cursor c = db.query(
                "Doner",                                    // The table to query
                projection,                                 // The columns to return
                where,                                       // The columns for the WHERE clause
                values,                                       // The values for the WHERE clause
                null,                                       // don't group the rows
                null,                                       // don't filter by row groups
                sortOrder                                   // The sort order
        );

        if (c != null && c.moveToFirst()) {
            do {
                DonerData d = new DonerData();
                d.id = c.getInt(c.getColumnIndexOrThrow("Id"));
                d.full_name = c.getString(c.getColumnIndexOrThrow("Name"));
                d.city = c.getString(c.getColumnIndexOrThrow("City"));
                d.area = c.getString(c.getColumnIndexOrThrow("Area"));

                data.add(d);
            }
            while (c.moveToNext());
        }

        return data;
    }

    public static DonerData getDonerDetail(Context ctx,int Id)
    {
        dbHelper dbhelper = new dbHelper(ctx);
        SQLiteDatabase db = dbhelper.getReadableDatabase();

        DonerData d = new DonerData();

        String[] projection = {
                "Id",
                "Name",
                "Email",
                "Phone",
                "Addr",
                "BloodGroup",
                "City",
                "Area"
        };

        String where = "Id LIKE ?";

        String[] values = {Id+""};

        Cursor c = db.query(
                "Doner",                                    // The table to query
                projection,                                 // The columns to return
                where,                                       // The columns for the WHERE clause
                values,                                       // The values for the WHERE clause
                null,                                       // don't group the rows
                null,                                       // don't filter by row groups
                null                                   // The sort order
        );

        if (c != null && c.moveToFirst()) {

                d.full_name = c.getString(c.getColumnIndexOrThrow("Name"));
                d.bloodgrp = c.getString(c.getColumnIndexOrThrow("BloodGroup"));
                d.phone = c.getString(c.getColumnIndexOrThrow("Phone"));
                d.email = c.getString(c.getColumnIndexOrThrow("Email"));
                d.addr = c.getString(c.getColumnIndexOrThrow("Addr"));
                d.area = c.getString(c.getColumnIndexOrThrow("Area"));
                d.city = c.getString(c.getColumnIndexOrThrow("City"));
        }

        return d;
    }
}
