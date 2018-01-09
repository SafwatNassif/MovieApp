package com.example.m.movieapp.Fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.m.movieapp.Data.Operations;
import com.example.m.movieapp.GetMovieData;
import com.example.m.movieapp.Interface.InterfaceCallBack;
import com.example.m.movieapp.Activities.MainActivity;
import com.example.m.movieapp.Model.Movie;
import com.example.m.movieapp.Model.Video;
import com.example.m.movieapp.Adapter.MovieAdapter;
import com.example.m.movieapp.R;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private  static MovieAdapter arrayAdapter ;
    private  static ArrayList<Movie>  movieList;
    private  static String  sort_order;
    private  static GridView gridView;

    private ArrayList<Movie> movieArrayList ; //to store all Data


    public MainFragment() {
        this.movieArrayList = new ArrayList<Movie>();
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rooView =  inflater.inflate(R.layout.fragment_main, container, false);
        gridView = (GridView) rooView.findViewById(R.id.gridview);
        Actions();
        return rooView;

    }

    private void Actions() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((MainActivity)getActivity()).onItemClicked(movieArrayList.get(position));
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        updateMovie();

    }

    public void updateMovie() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sort_order = sharedPreferences.getString(getString(R.string.sorted_key), getString(R.string.default_value));//file strings
        movieArrayList = new ArrayList<Movie>();
        arrayAdapter = new MovieAdapter(getContext(), movieArrayList);
        if (sort_order.equals("favourite")) {
            getFavouriteMovie();
        } else {
            GetMovieData getMovieData = new GetMovieData(getContext(), new InterfaceCallBack() {
                @Override
                public void setData(ArrayList<Movie> movieArray) {
                    movieArrayList = movieArray;
                    arrayAdapter = new MovieAdapter(getContext(), movieArrayList);
                    gridView.setAdapter(arrayAdapter);

                }
                @Override
                public void SetVideo(ArrayList<Video> videos) {
                }

                @Override
                public void SetReviews(ArrayList<String> videos) {
                }
            });
            getMovieData.execute("" + 1, sort_order);
            Log.e(MainFragment.class.getSimpleName(), "sort order value: " + sort_order);
        }

    }



    private void getFavouriteMovie(){
        Movie [] movie ;

        Operations operation= new Operations();
        movie = operation.query(getContext());

        if(movie != null)
            arrayAdapter.clear();

        for(Movie md:movie)
            arrayAdapter.add(md);

        arrayAdapter.notifyDataSetChanged();

        gridView.setAdapter(arrayAdapter);
        movieArrayList= new ArrayList<>(Arrays.asList(movie));
    }

}
