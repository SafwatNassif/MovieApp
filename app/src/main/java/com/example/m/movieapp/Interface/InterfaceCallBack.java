package com.example.m.movieapp.Interface;

import com.example.m.movieapp.Model.Movie;
import com.example.m.movieapp.Model.Video;

import java.util.ArrayList;

/**
 * Created by m on 8/28/2016.
 */
public interface InterfaceCallBack {
    public void  setData (ArrayList<Movie> movieArray);
    public void  SetVideo(ArrayList<Video> videos);
    public void   SetReviews(ArrayList<String> videos);


}
