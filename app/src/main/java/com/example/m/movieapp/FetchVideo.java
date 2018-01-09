package com.example.m.movieapp;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.example.m.movieapp.Interface.InterfaceCallBack;
import com.example.m.movieapp.Model.Video;

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
public class FetchVideo extends AsyncTask<Integer ,Void,Video[]> {

    private final String Log_cat= FetchVideo.class.getSimpleName();
    private InterfaceCallBack interfaceCallBack;
    private static Context context;
    private static  String jsonString;
    private static ArrayList<Video> videos;

    public  FetchVideo(Context c,InterfaceCallBack interfaceCallBack){
        this.context =c;
        this.interfaceCallBack=interfaceCallBack;

    }
    @Override
    protected Video[] doInBackground(Integer... params) {


        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        jsonString = null;


        if(params.length == 0) {
            return null;
        }
        try {

            String BaseURI = "http://api.themoviedb.org/3/movie/" + params[0].toString() + "/videos";
            String API_PARAM = "api_key";
            Uri builtUri = Uri.parse(BaseURI).buildUpon()
                    .appendQueryParameter(API_PARAM, BuildConfig.Movie_MAP_API_KEY).build();
            URL url = new URL(builtUri.toString());
            Log.i(Log_cat,"Uri id : "+builtUri.toString());


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
            return  getVideoDataFromJson(jsonString);

        }  catch (JSONException e){


        }
        return null;
    }

    private Video[] getVideoDataFromJson(String jsonString) throws  JSONException{
        Video[]  videoDatas  ;
        final String  results ="results";
        final String ID="id";
        final String KEY="key";
        final String NAME="name";
        final String SITE="site";
        final String TYPE="type";
        final String SIZE="size";

        JSONObject json = new JSONObject(jsonString);
        JSONArray results_from_json_string  = json.getJSONArray(results);
        videoDatas = new Video[results_from_json_string.length()];
        for (int i=0;i<results_from_json_string.length();i++) {

            JSONObject jsonItem = results_from_json_string.getJSONObject(i);
            videoDatas[i] = new Video();

            videoDatas[i].Id = jsonItem.getString(ID);
            videoDatas[i].key = jsonItem.getString(KEY);
            videoDatas[i].Name = jsonItem.getString(NAME);
            videoDatas[i].site = jsonItem.getString(SITE);
            videoDatas[i].type = jsonItem.getString(TYPE);
            videoDatas[i].size = jsonItem.getInt(SIZE);

        }

        videos= new ArrayList<Video>(Arrays.asList(videoDatas));

        return  videoDatas;
    }

    @Override
    protected void onPostExecute(Video[] video) {
        super.onPostExecute(video);
        interfaceCallBack.SetVideo(videos);
    }
}
