package com.bloodport.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.bloodport.R;
import com.bloodport.fragment.LoginFragment;
import com.bloodport.fragment.MainFragmentPager;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity
{
    private FirebaseAuth auth;

    public MainActivity() {
        auth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null && getSupportFragmentManager().findFragmentById(R.id.mainFragmentFrame) == null)
        {
            if (auth.getCurrentUser() == null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.mainFragmentFrame,
                                new LoginFragment(),
                                LoginFragment.class.getSimpleName())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            } else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.mainFragmentFrame,
                                new MainFragmentPager(),
                                MainFragmentPager.class.getSimpleName())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        if (getFragmentManager().getBackStackEntryCount() > 1)
            getFragmentManager().popBackStackImmediate();
        else super.onBackPressed();
    }
}
