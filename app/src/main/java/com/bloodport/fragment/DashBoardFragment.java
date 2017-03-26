package com.bloodport.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.bloodport.R;
import com.bloodport.adapter.DashboardAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by aliabbasjaffri on 22/03/2017.
 */

public class DashBoardFragment extends Fragment
{
    private ListView listView;
    private DatabaseReference mDatabase;

    public DashBoardFragment()
    {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        listView = (ListView)view.findViewById(R.id.dashboardFragmentListView);
        //DashboardAdapter adapter = new DashboardAdapter(getActivity(), );
        //listView.setAdapter(adapter);
        //listView.invalidate();

        return view;
    }
}
