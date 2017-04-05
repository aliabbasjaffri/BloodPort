package com.bloodport.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
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
    SharedPreferences prefs;
    DashboardAdapter adapter;
    DatabaseReference ref;
    Query queryRef;
    EditText name;
    EditText mobileNumber;
    Spinner bloodGroupSpinner;
    EditText address;
    RadioGroup contactGroup;
    RadioButton contactButton;


    public DashBoardFragment()
    {
        mDatabase = FirebaseDatabase.getInstance();
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

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        queryRef = mDatabase.getReference().child("requests").orderByChild("timeStamp");
        queryRef.addChildEventListener(childEventListener);

        adapter = new DashboardAdapter(getActivity(), requests);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                contactPerson(requests.get(position));
            }
        });

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

                        if (TextUtils.isEmpty(request_user_name)) {
                            Toast.makeText(getActivity(), "Enter complete name!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (TextUtils.isEmpty(mobile)) {
                            Toast.makeText(getActivity(), "Enter mobile no.!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (TextUtils.isEmpty(completeAddress)) {
                            Toast.makeText(getActivity(), "Enter complete address!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        ref.push().setValue((new BloodRequest(request_user_name,
                                bloodGroup,
                                new SimpleDateFormat("yyyy.MM.dd.HH.mm", Locale.ENGLISH).format(new Date()),
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

    private void contactPerson(final BloodRequest obj)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        final View promptView = layoutInflater.inflate(R.layout.popup_phonecall_sms, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);

        contactGroup = (RadioGroup) promptView.findViewById(R.id.popupContactSelectRadioGroup);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Make Request", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        contactButton = (RadioButton) promptView.findViewById(contactGroup.getCheckedRadioButtonId());
                        if(contactButton.getText().toString().contains("SMS"))
                        {
                            startActivity( new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + obj.getPhoneNumber()))
                                    .putExtra( "sms_body", getString(R.string.text_message) + prefs.getString("phoneNumber","")));
                        }
                        else
                        {
                            startActivityForResult(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + obj.getPhoneNumber())), 1);
                        }
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
