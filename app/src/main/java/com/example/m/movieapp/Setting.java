package com.example.m.movieapp;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.MenuItem;

/**
 * Created by bishoy on 9/16/16.
 */
public class Setting extends PreferenceActivity implements Preference.OnPreferenceChangeListener   {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref);
        Preference preference = findPreference(getString(R.string.sorted_key));
        preference.setOnPreferenceChangeListener(this);
        onPreferenceChange(preference, PreferenceManager.getDefaultSharedPreferences(preference.getContext()).getString(
                preference.getKey(),""));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.setting) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String value = newValue.toString();

        if(preference instanceof ListPreference){
            ListPreference listPreference = (ListPreference) preference;
            int prefindex = listPreference.findIndexOfValue(value);
            if(prefindex >= 0){
                preference.setSummary(listPreference.getEntries()[prefindex]);

            }else{
                preference.setSummary(value);


            }

        }
        return true;
    }
}
