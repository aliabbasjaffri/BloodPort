package com.bloodport.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bloodport.R;
import com.bloodport.adapter.PagerAdapter;

/**
 * Created by aliabbasjaffri on 01/05/2017.
 */

public class MainFragmentPager extends Fragment
{
    public MainFragmentPager() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_main_fragment_pager, container, false);
        ViewPager vpPager = (ViewPager) view.findViewById(R.id.vpPager);
        vpPager.setAdapter(new PagerAdapter(getActivity().getSupportFragmentManager()));
        return view;
    }
}
