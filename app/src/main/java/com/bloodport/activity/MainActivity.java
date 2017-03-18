package com.bloodport.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bloodport.R;
import com.bloodport.fragment.RegistrationMainpageFragment;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mainFragmentFrame,
                RegistrationMainpageFragment.newInstance(),
                RegistrationMainpageFragment.class.getSimpleName())
                .commit();
    }
}
