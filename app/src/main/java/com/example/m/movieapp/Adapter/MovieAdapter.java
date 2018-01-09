package com.example.m.movieapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.m.movieapp.Model.Movie;
import com.example.m.movieapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by m on 8/28/2016.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {


    private ImageView imageView;
    private Context context;

    public  MovieAdapter(Context context,ArrayList<Movie> data){
        super(context, R.layout.grid_item,data);
        this.context=context;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;
        if(item == null){
            item= LayoutInflater.from(getContext()).inflate(R.layout.grid_item, parent, false);
        }

        Movie md = getItem(position);
        imageView = (ImageView) item.findViewById(R.id.gridItem);
        // textView =(TextView) item.findViewById(R.id.Text_view_id);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w500"+md.posterPath).into(imageView);
        //textView.setText(md.title);
        return item;

    }

}