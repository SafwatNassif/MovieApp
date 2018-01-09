package com.example.m.movieapp.Activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.m.movieapp.Fragments.DetailsFragment;
import com.example.m.movieapp.R;

public class DetailActivity extends AppCompatActivity {

    private Bundle args;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        settingActionBar();

        //get data that sent from MainActivity  by  Intent
        args= getIntent().getExtras();


        if (args != null){
            DetailsFragment detailsFragment = new DetailsFragment(); // create Object from DetailsFragment
            detailsFragment.setArguments(args);// put data that sent from MainActivity by Intent
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detailfragment,detailsFragment).commit();// put DetailsFragment on DetailsActivity
        }else{
        //if the data that sent from MainActivity  by Intent is empty show message "there is no  data Passed "
        Toast.makeText(getApplicationContext(),"there is no data passed ",Toast.LENGTH_LONG).show();
        }

    }

    private void settingActionBar() {
        actionBar=getSupportActionBar();// initialization action bar
        actionBar.setTitle(getResources().getString(R.string.detail_activity));//set ActionBar title specific value
        actionBar.setDisplayHomeAsUpEnabled(true); // this used to  show arrow of back  button

    }


    // this used to handle what will be doing  when user pressed any button in action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: // home == back arrow
                // app icon in action bar clicked; go home
                finish();// to  destroy activity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed() { //this used when user press back button in fragmentDetails
        finish();
        super.onBackPressed();
    }
}
