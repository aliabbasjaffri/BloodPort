package com.bloodport.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bloodport.fragment.DashBoardFragment;

/**
 * Created by aliabbasjaffri on 01/05/2017.
 */

public class PagerAdapter extends FragmentPagerAdapter
{
    private String [] titles = {"Blood Request" , "Blood Donors"};

    public PagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return DashBoardFragment.newInstance(true);
            case 1:
                return DashBoardFragment.newInstance(false);
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return 2;
    }
}
