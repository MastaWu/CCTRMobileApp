package com.example.workstation.cctrmobileapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;

/**
 * Created by Workstation on 11/4/2014.
 */
public class sqliteDatabase extends SQLiteOpenHelper implements BaseColumns{

    private static final String TABLE_FAVORITES = "favorites";
    private static final String COLUMN_FAVORITES_ID ="id";
    private static final String COLUMN_FAVORITES_PROTOCOLNO = "protocolNo";
    private static final String COLUMN_FAVORITES_TITLE = "title";
    private static final String COLUMN_FAVORITES_STITLE = "shortTitle";
    private static final String COLUMN_FAVORITES_STATUS = "status";
    private static final String COLUMN_FAVORITES_NAME = "name";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP =", ";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_FAVORITES + " (" +
            BaseColumns._ID + " INTEGER PRIMARY KEY," +
            COLUMN_FAVORITES_ID + TEXT_TYPE + COMMA_SEP +
            COLUMN_FAVORITES_PROTOCOLNO + TEXT_TYPE + COMMA_SEP +
            COLUMN_FAVORITES_TITLE + TEXT_TYPE + COMMA_SEP +
            COLUMN_FAVORITES_STITLE + TEXT_TYPE + COMMA_SEP +
            COLUMN_FAVORITES_STATUS + TEXT_TYPE + COMMA_SEP +
            COLUMN_FAVORITES_NAME + TEXT_TYPE + " )";
    private static final String DATABASE_NAME = "cctr.db";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_FAVORITES;


    public sqliteDatabase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public sqliteDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);

/*        DatabaseUtils.InsertHelper ih = new DatabaseUtils.InsertHelper(sqLiteDatabase, TABLE_FAVORITES);

        final int favorites_id_ind = ih.getColumnIndex("id");
        final int favorites_protocolNo_ind = ih.getColumnIndex("protocolNo");
        final int favorites_title_ind = ih.getColumnIndex("title");
        final int favorites_sTitle_ind = ih.getColumnIndex("shortTitle");
        final int favorites_status_ind = ih.getColumnIndex("status");
        final int favorites_name_ind = ih.getColumnIndex("name"); */

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);

    }

    public void insertData(String id, String protocol, String title, String sTitle, String status, String name){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_FAVORITES_ID, id);
        values.put(COLUMN_FAVORITES_PROTOCOLNO, protocol);
        values.put(COLUMN_FAVORITES_TITLE, title);
        values.put(COLUMN_FAVORITES_STITLE, sTitle);
        values.put(COLUMN_FAVORITES_STATUS, status);
        values.put(COLUMN_FAVORITES_NAME, name);

        sqLiteDatabase.insert(TABLE_FAVORITES, null, values);

    }

    public ArrayList fetchData(){

        ArrayList<String> stringArrayList = new ArrayList<String>();
        String fetchdata = "select * from " + TABLE_FAVORITES;

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(fetchdata, null);

        if(cursor.moveToFirst()){

            do{

                stringArrayList.add(cursor.getString(0));
                stringArrayList.add(cursor.getString(1));
                stringArrayList.add(cursor.getString(2));
                stringArrayList.add(cursor.getString(3));
                stringArrayList.add(cursor.getString(4));
                stringArrayList.add(cursor.getString(5));

            } while(cursor.moveToNext());

        }

        return stringArrayList;
    }

    public void fillDatabase (String Json){

        SQLiteDatabase databaseInstance = getReadableDatabase();

    }

}
