package com.example.mylocations;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DatabaseAdapter {

    myDbHelper myhelper;

    public DatabaseAdapter(Context context) {
        myhelper = new myDbHelper(context);
    }

    public long insertData(String address, String latitude, String longitude) {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.ADDRESS, address);
        contentValues.put(myDbHelper.LATITUDE, latitude);
        contentValues.put(myDbHelper.LONGITUDE, longitude);
        long id = dbb.insert(myDbHelper.TABLE_NAME, null, contentValues);
        return id;
    }

    public String getData() {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID, myDbHelper.ADDRESS, myDbHelper.LATITUDE, myDbHelper.LONGITUDE};
        Cursor cursor = db.query(myDbHelper.TABLE_NAME, columns, null, null, null, null, null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int cid = cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex(myDbHelper.ADDRESS));
            @SuppressLint("Range") String latitude = cursor.getString(cursor.getColumnIndex(myDbHelper.LATITUDE));
            @SuppressLint("Range") String longitude = cursor.getString(cursor.getColumnIndex(myDbHelper.LONGITUDE));
            buffer.append(cid + "   " + address + "   " + latitude + "  " + longitude + " \n");
        }
        return buffer.toString();
    }

    public int delete(String address) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs = {address};

        int count = db.delete(myDbHelper.TABLE_NAME, myDbHelper.ADDRESS + " = ?", whereArgs);
        return count;
    }

    public int updateAddress(String oldAddress, String newAddress) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.ADDRESS, newAddress);
        String[] whereArgs = {oldAddress};
        int count = db.update(myDbHelper.TABLE_NAME, contentValues, myDbHelper.ADDRESS + " = ?", whereArgs);
        return count;
    }

    static class myDbHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "myDatabase";    // Database Name
        private static final String TABLE_NAME = "myLocations";   // Table Name
        private static final int DATABASE_Version = 1;    // Database Version
        private static final String UID = "_id";     // Column I (Primary Key)
        private static final String ADDRESS = "Address";    //Column II
        private static final String LONGITUDE = "Longitude";    // Column III
        private static final String LATITUDE = "Latitude"; // Column IV
        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ADDRESS + " VARCHAR(255) ," + LONGITUDE + " VARCHAR(225) , " + LATITUDE + " VARCHAR(255));";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        private Context context;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context = context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                Message.message(context, "" + e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Message.message(context, "OnUpgrade");
                db.execSQL(DROP_TABLE);
                onCreate(db);
            } catch (Exception e) {
                Message.message(context, "" + e);
            }
        }
    }
}
