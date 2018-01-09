package com.example.m.movieapp.Data;

import android.provider.BaseColumns;

/**
 * Created by bishoy on 9/16/16.
 */
public class Contract  {

    public static final class favourite implements BaseColumns {

        public static final String TABLE_NAME = "favourite";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER_PATH = "posterPath";
        public static final String COLUMN_VOTE_AVERAGE= "vote_average";
        public static final String COLUMN_RELEASED_DATE = "releasedDate";
        public static final String COLUMN_OVERVIEW = "overview";

    }
}
