package com.example.m.movieapp;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.m.movieapp.Interface.InterfaceCallBack;
import com.example.m.movieapp.Model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

//make connection and get jason data covvert jason to array list of movie
//set data to interface

/**
 * Created by m on 8/28/2016.
 */
public class GetMovieData extends AsyncTask<String ,Void,Void >
{
    private InterfaceCallBack interfaceCallBack;
    private ArrayList<Movie> movieArrayList;
    private final String LOG_CAT= GetMovieData.class.getSimpleName();
    private String jsonString ;
    private Context context;
    public GetMovieData(Context context,InterfaceCallBack interfaceCallBack){

        this.interfaceCallBack=interfaceCallBack;
        this.context=context;


    }


    @Override
    protected Void doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        jsonString= null ;


        try {

            final  String PAGE_PARAM= "page";
            String baseUrl= "http://api.themoviedb.org/3/discover/movie?";
            String API_PARAM = "api_key";
            String Sort_order ="sort_by";
            Uri builtUri = Uri.parse(baseUrl).buildUpon()
                    .appendQueryParameter(PAGE_PARAM,params[0])
                    .appendQueryParameter(Sort_order,params[1])
                    .appendQueryParameter(API_PARAM,BuildConfig.Movie_MAP_API_KEY)
                    .build();

            URL url= new URL(builtUri.toString());

            Log.v(LOG_CAT, "Built URI " + builtUri.toString());


            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if(inputStream == null)
                return  null ;

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line ;
            while((line = reader.readLine())!=null){

                buffer.append(line+"\n");
            }

            if(buffer.length() ==0)
                return  null;



            jsonString = buffer.toString();


        }catch (IOException e){

            Log.e(LOG_CAT,"error in connection ",e);
            return  null;
        }
        finally {
            if(urlConnection !=null)
                urlConnection.disconnect();

            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(LOG_CAT,"error in closing reader",e);
                }

        }
        try {
            getMovieDataFromJson(jsonString) ;
        } catch (JSONException e) {
           Log.e(LOG_CAT,"error in json " + e.toString() ) ;
        }


        return null;
    }

    private Void getMovieDataFromJson(String jsonString) throws JSONException {

        final String MOVIE_RESULT = "results";
        final String POSTER_PATH="poster_path";
        final String MOVIE_TITLE="title";
        final String MOVIE_COUNT="vote_count";
        final String MOVIE_ID="id";
        final String MOVIE_AVERAGE="vote_average";
        final String MOVIE_OVERVIEW="overview";
        final String MOVIE_ORIGINAL_LANG="original_language";
        final String MOVIE_RELEASE_DATE="release_date";
        final String MOVIE_POPULARITY="popularity";

        Movie[] movieData ;
        JSONObject Json = new JSONObject(jsonString);
        JSONArray result = Json.getJSONArray(MOVIE_RESULT);

        movieData = new Movie[result.length()];


        for (int i = 0; i <result.length(); i++) {

            JSONObject jsonContainer = result.getJSONObject(i);
            movieData[i] =new Movie();
            movieData[i].posterPath = jsonContainer.getString(POSTER_PATH);
            movieData[i].title = jsonContainer.getString(MOVIE_TITLE);
            movieData[i].voteCount = jsonContainer.getDouble(MOVIE_COUNT);
            movieData[i].id=jsonContainer.getInt(MOVIE_ID);
            movieData[i].lang=jsonContainer.getString(MOVIE_ORIGINAL_LANG);
            movieData[i].overview=jsonContainer.getString(MOVIE_OVERVIEW);
            movieData[i].popularity=jsonContainer.getDouble(MOVIE_POPULARITY);
            movieData[i].releaseDate=jsonContainer.getString(MOVIE_RELEASE_DATE);
            movieData[i].voteAverage=jsonContainer.getDouble(MOVIE_AVERAGE);

        }

        movieArrayList = new ArrayList<>(Arrays.asList(movieData));
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        interfaceCallBack.setData(movieArrayList);
    }
}
