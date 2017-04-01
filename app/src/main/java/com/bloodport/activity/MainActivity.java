package com.bloodport.activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bloodport.R;
import com.bloodport.fragment.DashBoardFragment;
import com.bloodport.fragment.RegistrationMainPageFragment;

public class MainActivity extends AppCompatActivity
{
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean skip = prefs.getBoolean("skip_registration" , false);

        if(!skip) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.mainFragmentFrame,
                            RegistrationMainPageFragment.newInstance(),
                            RegistrationMainPageFragment.class.getSimpleName())
                    .addToBackStack(RegistrationMainPageFragment.class.getSimpleName())
                    .commit();
        }
        else
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.mainFragmentFrame,
                            DashBoardFragment.newInstance(),
                            DashBoardFragment.class.getSimpleName())
                    .addToBackStack(DashBoardFragment.class.getSimpleName())
                    .commit();
        }
    }
}
