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
    private static final String TABLE_SEARCH = "search";

    private static final String COLUMN_FAVORITES_ID ="id";
    private static final String COLUMN_FAVORITES_PROTOCOLNO = "protocolNo";
    private static final String COLUMN_FAVORITES_TITLE = "title";
    private static final String COLUMN_FAVORITES_STITLE = "shortTitle";
    private static final String COLUMN_FAVORITES_STATUS = "status";
    private static final String COLUMN_FAVORITES_NAME = "name";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP =", ";

    private static final String SQL_CREATE_FAVORITE_ENTRIES =
                    "CREATE TABLE " + TABLE_FAVORITES + " ("
                    //these are the created columns
                    + BaseColumns._ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_FAVORITES_ID + TEXT_TYPE + COMMA_SEP
                    + COLUMN_FAVORITES_PROTOCOLNO + TEXT_TYPE + COMMA_SEP
                    + COLUMN_FAVORITES_TITLE + TEXT_TYPE + COMMA_SEP
                    + COLUMN_FAVORITES_STITLE + TEXT_TYPE + COMMA_SEP
                    + COLUMN_FAVORITES_STATUS + TEXT_TYPE + COMMA_SEP
                    + COLUMN_FAVORITES_NAME + TEXT_TYPE + " )";


    private static final String SQL_CREATE_SEARCH_ENTRIES =
                    "CREATE TABLE " + TABLE_SEARCH + " ("
                    //these are the created columns
                    + BaseColumns._ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_FAVORITES_ID + TEXT_TYPE + COMMA_SEP
                    + COLUMN_FAVORITES_PROTOCOLNO + TEXT_TYPE + COMMA_SEP
                    + COLUMN_FAVORITES_TITLE + TEXT_TYPE + COMMA_SEP
                    + COLUMN_FAVORITES_STITLE + TEXT_TYPE + COMMA_SEP
                    + COLUMN_FAVORITES_STATUS + TEXT_TYPE + COMMA_SEP
                    + COLUMN_FAVORITES_NAME + TEXT_TYPE + " )";


    private static final String DATABASE_NAME = "cctr.db";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_SEARCH;

    //context: provides access to application-specific resources and classes
    public sqliteDatabase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public sqliteDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        sqLiteDatabase.execSQL(SQL_CREATE_SEARCH_ENTRIES);
        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_ENTRIES);

    }

    @Override
    //calls onCreate to make an empty table
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    //not yet added to an ArrayList, pure strings, these strings are converted from numbers and stuff
    //from JSONobject "doInBackground"
    public void insertSearchData(String id, String protocol, String title, String sTitle, String status, String name){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //stores key value pairs. ContentValues data types is needed because the database requires its data type to be passed
        //these are the "initial values", this info will be there when it first opens
        ContentValues values = new ContentValues();

        values.put(COLUMN_FAVORITES_ID, id);
        values.put(COLUMN_FAVORITES_PROTOCOLNO, protocol);
        values.put(COLUMN_FAVORITES_TITLE, title);
        values.put(COLUMN_FAVORITES_STITLE, sTitle);
        values.put(COLUMN_FAVORITES_STATUS, status);
        values.put(COLUMN_FAVORITES_NAME, name);

        //inserts the data in the form of ContentValues to the table name TABLE_SEARCH
        //(inserted into TABLE_SEARCH, null, values found) = inserted into rows or columns?
        sqLiteDatabase.insert(TABLE_SEARCH, null, values);
    }

    public void clearSearchTable()   {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //everything in TABLE_SEARCH gets cleared
        //null,null = Columns,Rows?
        sqLiteDatabase.delete(TABLE_SEARCH, null,null);


    }
    public ArrayList fetchSearchData(){

        ArrayList<String> stringArrayList = new ArrayList<String>();
        String fetchdata = "select * FROM " + TABLE_SEARCH;

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(fetchdata, null);

        if(cursor.moveToFirst()){

            do{

                stringArrayList.add(
                        "ID: " + cursor.getString(0)
                                + "\nProtocol Number: " + cursor.getString(1)
                                + "\nTitle: " + cursor.getString(2)
                                + "\nShort Title: " + cursor.getString(3)
                                + "\nStatus: " + cursor.getString(4)
                                + "\nName: " + cursor.getString(5));

            } while(cursor.moveToNext());
        }

        return stringArrayList;
    }


    public void getSearchData(String id){

        SQLiteDatabase sqLiteDatabaseWrite = this.getWritableDatabase();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        ContentValues values = new ContentValues();

        String fetchdata = "select * FROM " + TABLE_SEARCH + "WHERE" + _ID + " = " +id;

        Cursor cursor = sqLiteDatabase.rawQuery(fetchdata, null);

        if(cursor.moveToFirst()){

            do{
                values.put(COLUMN_FAVORITES_ID, cursor.getString(0));
                values.put(COLUMN_FAVORITES_PROTOCOLNO, cursor.getString(1));
                values.put(COLUMN_FAVORITES_TITLE, cursor.getString(2));
                values.put(COLUMN_FAVORITES_STITLE, cursor.getString(3));
                values.put(COLUMN_FAVORITES_STATUS, cursor.getString(4));
                values.put(COLUMN_FAVORITES_NAME, cursor.getString(5));

            } while(cursor.moveToNext());
        }

        sqLiteDatabaseWrite.insert(TABLE_SEARCH, null, values);
    }

    public ArrayList fetchFavoritesData(){

        ArrayList<String> stringArrayList = new ArrayList<String>();
        String fetchdata = "select * from " + TABLE_FAVORITES;

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(fetchdata, null);

        if(cursor.moveToFirst()){
            do{
                stringArrayList.add("ID: " + cursor.getString(0)
                        + "\nProtocol Number: " + cursor.getString(1)
                        + "\nTitle: " + cursor.getString(2)
                        + "\nShort Title: " + cursor.getString(3)
                        + "\nStatus: " + cursor.getString(4)
                        + "\nName: " + cursor.getString(5));
            }
            while(cursor.moveToNext());
        }
        return stringArrayList;
    }

    public void insertFavoritesData(String id){

        SQLiteDatabase sqLiteDatabaseWrite = this.getWritableDatabase();
        SQLiteDatabase sqLiteDatabaseRead = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        String fetchdata = "select * from " + TABLE_SEARCH + " WHERE " + _ID + " = " + id;

        Cursor cursor = sqLiteDatabaseRead.rawQuery(fetchdata, null);

        if(cursor.moveToFirst()){

            do{

                values.put(COLUMN_FAVORITES_ID, cursor.getString(0));
                values.put(COLUMN_FAVORITES_PROTOCOLNO, cursor.getString(1));
                values.put(COLUMN_FAVORITES_TITLE, cursor.getString(2));
                values.put(COLUMN_FAVORITES_STITLE, cursor.getString(3));
                values.put(COLUMN_FAVORITES_STATUS, cursor.getString(4));
                values.put(COLUMN_FAVORITES_NAME, cursor.getString(5));

            } while(cursor.moveToNext());

        }

        sqLiteDatabaseWrite.insert(TABLE_FAVORITES, null, values);

    }

}
