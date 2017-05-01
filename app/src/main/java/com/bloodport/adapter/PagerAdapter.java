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

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0: return "Blood Request";
            case 1: return "Blood Donors";
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
