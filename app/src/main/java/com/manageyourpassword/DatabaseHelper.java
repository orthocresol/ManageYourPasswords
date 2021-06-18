package com.manageyourpassword;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(@Nullable Context context) {
        super(context, "Database.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE USERS (ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, PASSWORD TEXT)";
        db.execSQL(createTableStatement);
    }

    public boolean register(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("USERNAME", username);
        cv.put("PASSWORD", password);

        //email check

        long insert = db.insert("USERS", null, cv);
        db.close();
        if (insert == -1)
            return false;
        else return true;
    }
    public boolean delete(String tableName, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + tableName + " WHERE ID = " + id;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            return true;
        }
        else {
            return false;
        }

    }
    public Boolean authenticate(String username, String password){
        String query = "SELECT * FROM USERS WHERE USERNAME = \"" + username + "\"" + " AND PASSWORD = \"" + password + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            return true;
        } else {
            return false;
        }
    }

    public void createTableForUser(String name){
        String createTableStatement = "CREATE TABLE " + name + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, USERNAME TEXT, PASSWORD TEXT, URL TEXT)";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(createTableStatement);
    }

    public boolean insert(String tableName, Item item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        
        cv.put("NAME", item.getName());
        cv.put("USERNAME", item.getUsername());
        cv.put("PASSWORD", item.getPassword());
        cv.put("URL", item.getUrl());
        
        long insert = db.insert(tableName, null, cv);
        db.close();
        if (insert == -1){
            return false;
        }
        else {
            return true;
        }
    }
    public ArrayList<Item> getEveryone(String tableName){
        ArrayList<Item> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + tableName;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String username = cursor.getString(2);
                String password = cursor.getString(3);
                String url = cursor.getString(4);

                Item item = new Item(id, name, username, password, url);
                returnList.add(item);
            } while(cursor.moveToNext());
        }
        else {
            //do nothing
        }
        cursor.close();
        db.close();
        return returnList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
