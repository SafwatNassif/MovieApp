package com.example.m.movieapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.m.movieapp.Model.Video;
import com.example.m.movieapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by bishoy on 9/17/16.
 */
public class VideoAdapter extends ArrayAdapter<Video> {

    private  Context context ;
    private ImageView imageButton;
    private TextView textView;
    private String poster_pth;

    public VideoAdapter(Context context, ArrayList<Video> datas, String poster_path) {
        super(context, R.layout.item_of_video,datas);
        this.context=context;
        this.poster_pth=poster_path;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView ;
        if (item == null){

            item= LayoutInflater.from(context).inflate(R.layout.item_of_video,parent,false);
        }

        imageButton = (ImageView) item.findViewById(R.id.image_button_for_video);
        textView =(TextView)item.findViewById(R.id.text_view_for_video);

        Picasso.with(context).load("http://image.tmdb.org/t/p/w92"+poster_pth).into(imageButton);

        final Video videoData = getItem(position);
        textView.setText(videoData.Name);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v="+videoData.key));
                context.startActivity(intent);
            }
        });


        return item;
    }

}
