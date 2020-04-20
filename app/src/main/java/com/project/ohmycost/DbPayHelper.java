package com.project.ohmycost;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbPayHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBPayHelper";
    private static final String TABLE_NAME = "list_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "day";
    private static final String COL3 = "month";
    private static final String COL4 = "year";
    private static final String COL5 = "type";
    private static final String COL6 = "amount";

    public DbPayHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT, " + COL3 + " TEXT, "  + COL4 + " TEXT, "  + COL5 + " TEXT, " + COL6 + " TEXT);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String day,String month,String year,String type,String amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL2, day);
        values.put(COL3, month);
        values.put(COL4, year);
        values.put(COL5, type);
        values.put(COL6, amount);

        Log.d(TAG, "addData: Adding " +day+ ", "+month+ ", "+year
                + ", "+type+" and "+amount+ " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, values);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //type+amount from date
    public Cursor getData(String day){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + "," + COL5 + "," + COL6 + " FROM " + TABLE_NAME + " WHERE " + COL2+ " = '" + day + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getAmount(String day){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + "," + COL6 + " FROM " + TABLE_NAME + " WHERE " + COL2 + " = '" + day + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getTotalMonth(String month){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + "," + COL6 + " FROM " +
                TABLE_NAME + " WHERE " + COL3+ " = '" + month + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getType(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL5 + " FROM " +TABLE_NAME ;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getItemID(String day,String amount){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME + " WHERE " +
                COL6 + " = '" + amount + "'"+ " AND " + COL2 + " = '" + day + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }


    public void updateName(String newName, int id, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL6 +
                " = '" + newName + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL6 + " = '" + oldName + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }


    public void deleteName(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + id + "'" +
                " AND " + COL6 + " = '" + name + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }
}

