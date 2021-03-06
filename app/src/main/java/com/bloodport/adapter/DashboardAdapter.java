package com.bloodport.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bloodport.R;
import com.bloodport.model.BloodRequest;

import java.util.List;

/**
 * Created by aliabbasjaffri on 26/03/2017.
 */

public class DashboardAdapter extends BaseAdapter
{
    private Context context;
    private List<BloodRequest> listObjects;
    private boolean isFromRequestFragment;
    int[] color_arr={Color.BLUE,Color.CYAN,Color.DKGRAY,Color.GREEN, Color.RED};

    public DashboardAdapter(Context context, List<BloodRequest> obj, boolean isFromRequestFragment)
    {
        this.context = context;
        this.listObjects = obj;
        this.isFromRequestFragment = isFromRequestFragment;
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
        View view = convertView;

        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_dashboard_listview, null);
        }

        TextView nameHolder = (TextView) view.findViewById(R.id.dashboardRowNameTextView);
        TextView bloodGroupHolder = (TextView) view.findViewById(R.id.dashboardRowBloodGroupTextView);
        TextView timePosterHolder = (TextView) view.findViewById(R.id.dashboardRowTimestampTextView);
        TextView locationHolder = (TextView) view.findViewById(R.id.dashboardRowLocationTextView);

        nameHolder.setText(listObjects.get(position).getName());
        bloodGroupHolder.setText(listObjects.get(position).getBloodGroup());
        timePosterHolder.setText(listObjects.get(position).getDate() + " " + listObjects.get(position).getTime());
        locationHolder.setText(listObjects.get(position).getLocation());

        //if(isFromRequestFragment)
        //    view.setBackgroundColor(color_arr[position % 5]);

        return view;
    }
}
