package com.example.m.movieapp.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.m.movieapp.Adapter.VideoAdapter;
import com.example.m.movieapp.Data.Contract;
import com.example.m.movieapp.Data.Operations;
import com.example.m.movieapp.FetchReiview;
import com.example.m.movieapp.FetchVideo;
import com.example.m.movieapp.Interface.InterfaceCallBack;
import com.example.m.movieapp.Model.Movie;
import com.example.m.movieapp.Model.Video;
import com.example.m.movieapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    private View rootView;
    private Bundle argument;
    private ImageView imageDetails;
    private TextView title,overView,relaseDate,vote_average;
    private Movie movie;
    private FloatingActionButton favourite;
    private ListView vedeolist,ReiviewList;
    private Boolean testFavourite;

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //the compiler enter this method before onCreateView
        //and  i used this method to get data from  DetailsActivity that i passed to
        // DetailsFragment by used detailsFragment.setArguments(args)

        argument = getArguments();

        if (argument!=null){
        //get data from argument and insert it into movie
              movie = new Movie();
              movie.setPosterPath(argument.getString(Contract.favourite.COLUMN_POSTER_PATH));
              movie.setOverview(argument.getString(Contract.favourite.COLUMN_OVERVIEW));
              movie.setTitle(argument.getString(Contract.favourite.COLUMN_TITLE));
              movie.setReleaseDate(argument.getString(Contract.favourite.COLUMN_RELEASED_DATE));
              movie.setId(argument.getInt(Contract.favourite.COLUMN_ID,0));
              movie.setVoteAverage(argument.getDouble(Contract.favourite.COLUMN_VOTE_AVERAGE));
              testFavourite=argument.getBoolean("toggleButton");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_details, container, false);
        imageDetails=(ImageView)rootView.findViewById(R.id.ImageViewDetails);
        overView=(TextView)rootView.findViewById(R.id.OverView);
        title=(TextView)rootView.findViewById(R.id.titleToolBar);
        favourite=(FloatingActionButton)rootView.findViewById(R.id.favourit);
        vedeolist=(ListView)rootView.findViewById(R.id.list_view_for_vedios);
        ReiviewList=(ListView)rootView.findViewById(R.id.list_view_for_reviews);
        relaseDate=(TextView)rootView.findViewById(R.id.releasDate);
        vote_average=(TextView)rootView.findViewById(R.id.vote_average);

        setValues();
        Action();
        Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w780"+movie.getPosterPath()).placeholder(R.drawable.progress_animation).into(imageDetails);
        getvideo();

        return rootView;
    }

    private void getvideo() {
        FetchVideo fetchVideo = new FetchVideo(getContext(), new InterfaceCallBack() {
            @Override
            public void setData(ArrayList<Movie> movieArray) {

            }

            @Override
            public void SetVideo(ArrayList<Video> videos) {
                VideoAdapter videoAdapter = new VideoAdapter(getContext(),videos,movie.getPosterPath());
                vedeolist.setAdapter(videoAdapter);
                setListViewHeightBasedOnChildren(vedeolist);
            }

            @Override
            public void SetReviews(ArrayList<String> videos) {

            }

        });
        FetchReiview fetchReiview = new FetchReiview(getContext(), new InterfaceCallBack() {
            @Override
            public void setData(ArrayList<Movie> movieArray) {

            }

            @Override
            public void SetVideo(ArrayList<Video> videos) {

            }

            @Override
            public void SetReviews(ArrayList<String> videos) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.layout.item_of_review,R.id.text_view_for_reviews
                        ,videos);
                ReiviewList.setAdapter(adapter);
                setListViewHeightBasedOnChildren(ReiviewList);
            }
        });

        fetchVideo.execute(movie.getId());
        fetchReiview.execute(movie.getId());
    }



    private void Action() {
        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (testFavourite){
                    favourite.setImageResource(R.drawable.off);
                    Operations operation = new Operations();
                    operation.delete(getContext(), movie.id);
                    Toast.makeText(getContext(),movie.title+" deleted from your favourite",Toast.LENGTH_LONG).show();
                     testFavourite=false;
                }else {
                    favourite.setImageResource(R.drawable.on);
                    Operations operation = new Operations();
                    operation.insert(movie, getContext());
                    Toast.makeText(getContext(), movie.title + " added to your favourite", Toast.LENGTH_LONG).show();
                    testFavourite=true;
                }
            }
        });
    }

    private void setValues() {
        title.setText(movie.getTitle());
        overView.setText(movie.getOverview());
        relaseDate.setText(movie.getReleaseDate());

        vote_average.setText(""+movie.getVoteAverage()+"/10");


        if (testFavourite){
            favourite.setImageResource(R.drawable.on);
            testFavourite=true;
        }else {
            favourite.setImageResource(R.drawable.off);
            testFavourite=false;
        }
    }

    //handel  what will be doing if user press backButton in ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home://BackButton
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
