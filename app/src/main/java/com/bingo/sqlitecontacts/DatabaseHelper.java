package com.bingo.sqlitecontacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by RajeshRaman on 27-Sep-17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static  final String DATABASE_NAME = "CONTACTS.DB";
    public  static final String TABLE_NAME = "CONTACT_TABLE";
    public static  final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "PHONE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);  // database created
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating the table
        String query = "CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT , NAME TEXT , PHONE TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // if table is already present
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        onCreate(db);
    }

    public boolean insertData(String name,String phone){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,phone);

        long result =  db.insert(TABLE_NAME,null,contentValues);

        if(result==-1)
            return false;
        else
            return true;
    }

    // returning the full contents of table

    public Cursor viewAllContacts(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from " + TABLE_NAME;
        Cursor ret = db.rawQuery(query,null);

        return ret;
    }

    //update the contacts using primary key id

    public boolean updateContacts(String id,String name,String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,phone);

        db.update(TABLE_NAME,contentValues,"ID=?",new String[]{ id});

        return true;
    }

    public int deleteContact(String id){
        SQLiteDatabase db = this.getWritableDatabase();

        int no_Of_rows_effected = db.delete(TABLE_NAME,"ID=?",new String[]{id});

        return no_Of_rows_effected;

    }

}
