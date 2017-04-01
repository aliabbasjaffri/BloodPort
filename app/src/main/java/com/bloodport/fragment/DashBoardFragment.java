package com.bloodport.fragment;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import com.bloodport.R;
import com.bloodport.adapter.DashboardAdapter;
import com.bloodport.model.BloodRequest;
import com.google.firebase.database.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by aliabbasjaffri on 22/03/2017.
 */

public class DashBoardFragment extends Fragment
{
    private FirebaseDatabase mDatabase;
    List<BloodRequest> requests = new ArrayList<>();
    private static DashBoardFragment instance;
    SharedPreferences prefs;
    DashboardAdapter adapter;
    DatabaseReference ref;
    Query queryRef;
    EditText name;
    EditText mobileNumber;
    Spinner bloodGroupSpinner;
    EditText address;


    public DashBoardFragment()
    {
        mDatabase = FirebaseDatabase.getInstance();

    }

    public static DashBoardFragment newInstance()
    {
        return instance == null ? instance = new DashBoardFragment() : instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menuItemAdd:
                addNewRequest();
                return true;
            default:
                break;
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ListView listView = (ListView) view.findViewById(R.id.dashboardFragmentListView);

        queryRef = mDatabase.getReference().child("requests").orderByChild("timeStamp");
        queryRef.addChildEventListener(childEventListener);

        adapter = new DashboardAdapter(getActivity(), requests);
        listView.setAdapter(adapter);

        return view;
    }

    ChildEventListener childEventListener  = new ChildEventListener() {
        @Override
        public void onChildAdded (DataSnapshot dataSnapshot, String previousChild)
        {
            requests.add(dataSnapshot.getValue(BloodRequest.class));
            adapter.notifyDataSetChanged();
        }

        public void onChildRemoved(DataSnapshot snapshot) {

        }

        public void onChildChanged(DataSnapshot snapshot, String previousChild) {

        }

        public void onChildMoved(DataSnapshot snapshot, String previousChild) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private void addNewRequest()
    {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.popup_new_request, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        ref = mDatabase.getReference("requests/");

        name = (EditText) promptView.findViewById(R.id.popupRequestNameEditText);

        mobileNumber = (EditText) promptView.findViewById(R.id.popupRequestPhoneNumberEditText);
        mobileNumber.setText(prefs.getString("phoneNumber", ""));

        bloodGroupSpinner = (Spinner) promptView.findViewById(R.id.popupRequestBloodGroupSpinner);
        address = (EditText) promptView.findViewById(R.id.popupRequestAddressEditText);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Post Request", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String request_user_name = name.getText().toString().trim();
                        String mobile = mobileNumber.getText().toString().trim();
                        String bloodGroup = bloodGroupSpinner.getSelectedItem().toString();
                        String completeAddress = address.getText().toString().trim();

                        ref.push().setValue((new BloodRequest(request_user_name,
                                bloodGroup,
                                new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss", Locale.ENGLISH).format(new Date()),
                                completeAddress,
                                mobile)));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    public void onStop() {
        super.onStop();
        queryRef.removeEventListener(childEventListener);
    }
}
