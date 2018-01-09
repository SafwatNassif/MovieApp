package com.example.m.movieapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.m.movieapp.Data.Contract;
import com.example.m.movieapp.Data.Operations;
import com.example.m.movieapp.Fragments.DetailsFragment;
import com.example.m.movieapp.Interface.onItemClick;
import com.example.m.movieapp.Model.Movie;
import com.example.m.movieapp.R;
import com.example.m.movieapp.Setting;

public class MainActivity extends AppCompatActivity  implements onItemClick {

   private Boolean land =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if( (findViewById(R.id.land_phone) != null )||(findViewById(R.id.land_tablet)!=null)){
            land=true;
        }else{
            land=false;
        }

    }


    @Override
    public void onItemClicked(Movie movie) {
        if (movie!=null) {

            Operations operations = new Operations();
            if (land) {
                Bundle args= new Bundle();
                args.putString(Contract.favourite.COLUMN_OVERVIEW,movie.overview);
                args.putString(Contract.favourite.COLUMN_TITLE,movie.title);
                args.putString(Contract.favourite.COLUMN_RELEASED_DATE, movie.releaseDate);
                args.putDouble(Contract.favourite.COLUMN_VOTE_AVERAGE, movie.voteAverage);
                args.putInt(Contract.favourite.COLUMN_ID, movie.id);
                args.putString(Contract.favourite.COLUMN_POSTER_PATH, movie.posterPath);
                args.putBoolean("toggleButton", operations.rowQueryIsFound(this, movie.id));
                args.putBoolean("testTwoPane",land);
                DetailsFragment fragmentDetails = new DetailsFragment();
                fragmentDetails.setArguments(args);


                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.details_land, fragmentDetails).commit();

            } else {

                Intent intent = new Intent(this, DetailActivity.class);
                intent.putExtra(Contract.favourite.COLUMN_POSTER_PATH, movie.posterPath);
                intent.putExtra(Contract.favourite.COLUMN_TITLE, movie.title);
                intent.putExtra(Contract.favourite.COLUMN_OVERVIEW, movie.overview);
                intent.putExtra(Contract.favourite.COLUMN_RELEASED_DATE, movie.releaseDate);
                intent.putExtra(Contract.favourite.COLUMN_VOTE_AVERAGE, movie.voteAverage);
                intent.putExtra(Contract.favourite.COLUMN_ID, movie.id);
                intent.putExtra("toggleButton", operations.rowQueryIsFound(this, movie.id));
                startActivity(intent);

            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.setting_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(R.id.setting == id){
            Intent  intent = new Intent(this,Setting.class);
            startActivity(intent);
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }
}
