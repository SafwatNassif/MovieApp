package com.example.m.movieapp;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.m.movieapp.Interface.InterfaceCallBack;

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

/**
 * Created by bishoy on 9/17/16.
 */
public class FetchReiview extends AsyncTask<Integer,Void,String[]> {

    private static Context context;
    private static String jsonString;
    private static String  Log_cat =FetchReiview.class.getSimpleName();
    public static InterfaceCallBack interfaceCallBack;
    private static ArrayList<String> reiviewList;
    public FetchReiview(Context context, InterfaceCallBack interfaceCallBack)
    {
        this.context = context;
       this.interfaceCallBack=interfaceCallBack;
       ;

    }
    @Override
    protected String[] doInBackground(Integer... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        jsonString = null;


        if(params.length == 0) {
            return null;
        }
        try {

            String BaseURI = "http://api.themoviedb.org/3/movie/" + params[0].toString() + "/reviews";
            String API_PARAM = "api_key";
            Uri builtUri = Uri.parse(BaseURI).buildUpon()
                    .appendQueryParameter(API_PARAM, BuildConfig.Movie_MAP_API_KEY).build();
            URL url = new URL(builtUri.toString());
            Log.i(Log_cat, "Uri id : " + builtUri.toString());


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
            Log.e(Log_cat,"error in connection ",e);
            return null;

        }
        finally {
            if(urlConnection !=null)
                urlConnection.disconnect();

            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(Log_cat,"error in closing reader",e);
                }

        }
        try {
            return  getReviwsDataFromJson(jsonString);

        }  catch (JSONException e){


        }

        return null;
    }



    private String[] getReviwsDataFromJson(String jsonString) throws  JSONException{

        String [] resultReviews ;
        final String  results ="results";
        final String content="content";

        JSONObject json = new JSONObject(jsonString);
        JSONArray results_from_json_string  = json.getJSONArray(results);

        resultReviews = new String[results_from_json_string.length()];
        for (int i=0;i<results_from_json_string.length();i++) {

            JSONObject jsonItem = results_from_json_string.getJSONObject(i);
            resultReviews[i] =jsonItem.getString(content);
        }




        reiviewList= new ArrayList<>(Arrays.asList(resultReviews));

        return resultReviews;
    }


    @Override
    protected void onPostExecute(String[] strings) {
        super.onPostExecute(strings);
        interfaceCallBack.SetReviews(reiviewList);
    }
}
