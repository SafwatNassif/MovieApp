package com.example.m.movieapp.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bishoy on 9/16/16.
 */
public class MovieDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1 ;
    static  final String DATABASE_NAME = "movies.db";

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_FOR_CREATE_FAVOURITE_TABLE= "CREATE TABLE "+ Contract.favourite.TABLE_NAME+"(" +
                Contract.favourite.COLUMN_ID +" INTEGER PRIMARY KEY ,"+
                Contract.favourite.COLUMN_TITLE+" TEXT NOT NULL,"+
                Contract.favourite.COLUMN_OVERVIEW+" TEXT NOT NULL,"+
                Contract.favourite.COLUMN_POSTER_PATH+" TEXT NOT NULL,"+
                Contract.favourite.COLUMN_RELEASED_DATE+" TEXT NOT NULL,"+
                Contract.favourite.COLUMN_VOTE_AVERAGE+" TEXT NOT NULL"+");";


        db.execSQL(SQL_FOR_CREATE_FAVOURITE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST "+Contract.favourite.TABLE_NAME);
        onCreate(db);
    }
}
