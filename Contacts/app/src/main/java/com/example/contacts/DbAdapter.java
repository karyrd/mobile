package com.example.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DbAdapter {

    static DbHelper dbHelper;
    public  DbAdapter(Context context) {
        dbHelper = new DbHelper(context);
    }

    public boolean insertData(String name, String email, String location,
                           String phone, String social_network) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DbHelper.NAME, name);
            contentValues.put(DbHelper.EMAIL, email);
            contentValues.put(DbHelper.LOCATION, location);
            contentValues.put(DbHelper.PHONE, phone);
            contentValues.put(DbHelper.SOCIAL_NETWORKS, social_network);
            db.insert(DbHelper.TABLE_NAME, null, contentValues);
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }

    public static ArrayList<ContactClass> getData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<ContactClass> contactsList = new ArrayList<>();
        String[] columns = { DbHelper.ID, DbHelper.NAME, DbHelper.EMAIL,
                            DbHelper.LOCATION, DbHelper.PHONE, DbHelper.SOCIAL_NETWORKS };
        Cursor cursor =db.query(DbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(DbHelper.ID));
            String name = cursor.getString(cursor.getColumnIndex(DbHelper.NAME));
            String email = cursor.getString(cursor.getColumnIndex(DbHelper.EMAIL));
            String location = cursor.getString(cursor.getColumnIndex(DbHelper.LOCATION));
            String phone = cursor.getString(cursor.getColumnIndex(DbHelper.PHONE));
            String social_network = cursor.getString(cursor.getColumnIndex(DbHelper.SOCIAL_NETWORKS));
            buffer.append(id + ";" + name + ";" + email + ";" + location +
                    ";" + phone + ";" + social_network + "\n");
            contactsList.add(new ContactClass(id, name, email, location, phone, social_network));
        }

        return contactsList;
    }

    public int delete(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] whereArgs = {String.valueOf(id)};

        int count = db.delete(DbHelper.TABLE_NAME ,DbHelper.ID + " = ?", whereArgs);
        return count;
    }

    public boolean update(long id, String name, String email, String location,
                      String phone, String social_network) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DbHelper.NAME, name);
            contentValues.put(dbHelper.EMAIL, email);
            contentValues.put(dbHelper.LOCATION, location);
            contentValues.put(dbHelper.PHONE, phone);
            contentValues.put(dbHelper.SOCIAL_NETWORKS, social_network);
            String[] whereArgs = {String.valueOf(id)};
            db.update(DbHelper.TABLE_NAME, contentValues, DbHelper.ID + " = ?", whereArgs);
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }

    ////////////////////////////////////////////////////////////////

    static class DbHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "myDatabase";    // Database Name
        private static final String TABLE_NAME = "myTable";   // Table Name
        private static final int DATABASE_Version = 1;   // Database Version

        private static final String ID = "_id";     // Column I (Primary Key)
        private static final String NAME = "name";    //Column II
        private static final String EMAIL = "email";    // Column III
        private static final String LOCATION = "location";    // Column IV
        private static final String PHONE = "phone";    // Column V
        private static final String SOCIAL_NETWORKS = "social_networks";    // Column VI

        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+ ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255) ,"+ EMAIL+" VARCHAR(225)," +
                LOCATION + " VARCHAR(225), " + PHONE +" VARCHAR(255) ," + SOCIAL_NETWORKS + " VARCHAR(255)" + ")";
        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
        private Context context;

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                Log.d("error", "CREATE TABLE ERROR: " + e.toString());
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }
            catch (Exception e) {
                Log.d("error", "UPGRADE TABLE ERROR: " +  e.toString());
            }
        }
    }
}
