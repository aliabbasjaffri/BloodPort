package com.bloodport.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bloodport.R;
import com.bloodport.model.BloodRequestList;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by aliabbasjaffri on 26/03/2017.
 */

public class DashboardAdapter extends BaseAdapter
{
    private Context context;
    private List<BloodRequestList> listObjects;

    public DashboardAdapter(Context context, List<BloodRequestList> obj)
    {
        this.context = context;
        this.listObjects = obj;
    }

    @Override
    public int getCount() {
        return listObjects.size();
    }

    @Override
    public Object getItem(int position) {
        return listObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.row_dashboard_listview, null);
        TextView nameHolder = (TextView) view.findViewById(R.id.dashboardRowNameTextView);
        TextView bloodGroupHolder = (TextView) view.findViewById(R.id.dashboardRowBloodGroupTextView);
        TextView timePosterHolder = (TextView) view.findViewById(R.id.dashboardRowTimestampTextView);
        TextView locationHolder = (TextView) view.findViewById(R.id.dashboardRowLocationTextView);

        nameHolder.setText(listObjects.get(position).getName());
        bloodGroupHolder.setText(listObjects.get(position).getBloodGroup());
        timePosterHolder.setText(listObjects.get(position).getTimeStamp());
        locationHolder.setText(listObjects.get(position).getLocation());

        return view;
    }
}
