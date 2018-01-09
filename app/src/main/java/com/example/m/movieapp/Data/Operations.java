package com.example.m.movieapp.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.m.movieapp.Model.Movie;

/**
 * Created by bishoy on 9/16/16.
 */
public class Operations  {
    public void insert(Movie movieData , Context context){

        MovieDBHelper dbHelper = new MovieDBHelper(context);
        SQLiteDatabase db =  dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Contract.favourite.COLUMN_ID,movieData.id);
        values.put(Contract.favourite.COLUMN_TITLE,movieData.title);
        values.put(Contract.favourite.COLUMN_OVERVIEW,movieData.overview);
        values.put(Contract.favourite.COLUMN_POSTER_PATH,movieData.posterPath);
        values.put(Contract.favourite.COLUMN_RELEASED_DATE,movieData.releaseDate);
        values.put(Contract.favourite.COLUMN_VOTE_AVERAGE, movieData.getVoteAverage());

        db.insert(Contract.favourite.TABLE_NAME, null, values);

        Log.e(Operations.class.getSimpleName(), movieData.title + " has add");

        db.close();
    }


    public  Movie[] query(Context context){

        MovieDBHelper dbHelper = new MovieDBHelper(context);
        Movie [] movieData;
        int index =0;
        String query ="SELECT * FROM " +Contract.favourite.TABLE_NAME;
        SQLiteDatabase  db =  dbHelper.getReadableDatabase();
        Cursor cursor= db.rawQuery(query,null);
        movieData= new Movie[cursor.getCount()];
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){

            movieData[index]= new Movie();
            if(cursor.getString(cursor.getColumnIndex(Contract.favourite.COLUMN_ID))!=null){
                movieData[index].overview = cursor.getString(cursor.getColumnIndex(Contract.favourite.COLUMN_OVERVIEW));
                movieData[index].title = cursor.getString(cursor.getColumnIndex(Contract.favourite.COLUMN_TITLE));
                movieData[index].id = cursor.getInt(cursor.getColumnIndex(Contract.favourite.COLUMN_ID));
                movieData[index].releaseDate = cursor.getString(cursor.getColumnIndex(Contract.favourite.COLUMN_RELEASED_DATE));
                movieData[index].posterPath = cursor.getString(cursor.getColumnIndex(Contract.favourite.COLUMN_POSTER_PATH));
                movieData[index].voteAverage = cursor.getDouble(cursor.getColumnIndex(Contract.favourite.COLUMN_VOTE_AVERAGE));

            }
            cursor.moveToNext();
            index++;

        }
        db.close();
        return movieData;
    }


    public  void  delete(Context c,int  index){

        MovieDBHelper dbHelper = new MovieDBHelper(c);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String  condition = Contract.favourite.COLUMN_ID +" == "+ Integer.toString(index);
        db.execSQL("DELETE FROM " + Contract.favourite.TABLE_NAME + " WHERE " + condition);

        db.close();

    }


    public boolean rowQueryIsFound(Context context,int index){
        boolean row =false;
        MovieDBHelper dbHelper = new MovieDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String query ="SELECT "+Contract.favourite.COLUMN_ID+" FROM " +Contract.favourite.TABLE_NAME
                +" WHERE " + Contract.favourite.COLUMN_ID + " == " +Integer.toString(index);

        Cursor cursor= db.rawQuery(query, null);

        if (cursor.getCount()>0)
            row=true ;

        return row;

    }



}
