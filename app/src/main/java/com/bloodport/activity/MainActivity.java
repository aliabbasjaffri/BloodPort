package com.bloodport.activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bloodport.R;
import com.bloodport.fragment.DashBoardFragment;
import com.bloodport.fragment.LoginFragment;
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
                            new LoginFragment(),
                            LoginFragment.class.getSimpleName())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }
        else
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.mainFragmentFrame,
                            new DashBoardFragment(),
                            DashBoardFragment.class.getSimpleName())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }
    }

    @Override
    public void onBackPressed()
    {
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStackImmediate();
        } else {
            super.onBackPressed();
        }
    }
}
