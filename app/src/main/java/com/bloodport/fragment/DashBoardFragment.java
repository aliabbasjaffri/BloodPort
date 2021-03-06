package com.bloodport.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.TextView;
import android.widget.Toast;
import com.bloodport.R;
import com.bloodport.adapter.DashboardAdapter;
import com.bloodport.model.BloodRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by aliabbasjaffri on 22/03/2017.
 */

public class DashBoardFragment extends Fragment
{
    private FirebaseDatabase mDatabase;
    private FirebaseAuth auth;
    private List<BloodRequest> requests = new ArrayList<>();
    private SharedPreferences prefs;
    private DashboardAdapter adapter;
    private DatabaseReference ref;
    private Query queryRef;
    private EditText name;
    private EditText mobileNumber;
    private Spinner bloodGroupSpinner;
    private Spinner citiesSpinner;
    private EditText address;
    private RadioGroup contactGroup;
    private RadioButton contactButton;
    private boolean isFromRequestFragment;
    private Menu menu;

    public static DashBoardFragment newInstance(boolean isFromRequestFragment) {
        DashBoardFragment dashBoardFragment = new DashBoardFragment();
        Bundle args = new Bundle();
        args.putBoolean("isFromRequestFragment",isFromRequestFragment);
        dashBoardFragment.setArguments(args);
        return dashBoardFragment;
    }

    public DashBoardFragment()
    {
        mDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        isFromRequestFragment = getArguments().getBoolean("isFromRequestFragment");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        this.menu = menu;
        getActivity().getMenuInflater().inflate(R.menu.main_menu, menu);
        showMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menuItemAdd:
                addNewRequest();
                return true;
            case R.id.menuLogOut:
                signOut();
                break;
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

        if(isFromRequestFragment)
            queryRef = mDatabase.getReference().child("requests").orderByChild("timeStamp");
        else
            queryRef = mDatabase.getReference().child("donors").orderByChild("timeStamp");

        queryRef.keepSynced(true);
        queryRef.addChildEventListener(childEventListener);

        adapter = new DashboardAdapter(getActivity(), requests, isFromRequestFragment);
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
            requests.add(0 , dataSnapshot.getValue(BloodRequest.class));
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

        TextView title = (TextView) promptView.findViewById(R.id.newRequestTitleTextView);

        if(isFromRequestFragment) {
            ref = mDatabase.getReference("requests/").orderByChild("timeStamp").getRef();
            title.setText(getActivity().getString(R.string.popup_request_title, "Blood"));
        }
        else {
            ref = mDatabase.getReference("donors/").orderByChild("timeStamp").getRef();
            title.setText(getActivity().getString(R.string.popup_request_title, "Donor" ));
        }

        name = (EditText) promptView.findViewById(R.id.popupRequestNameEditText);

        mobileNumber = (EditText) promptView.findViewById(R.id.popupRequestPhoneNumberEditText);
        mobileNumber.setText(prefs.getString("phoneNumber", ""));

        bloodGroupSpinner = (Spinner) promptView.findViewById(R.id.popupRequestBloodGroupSpinner);
        citiesSpinner = (Spinner) promptView.findViewById(R.id.popupRequestCitySpinner);
        address = (EditText) promptView.findViewById(R.id.popupRequestAddressEditText);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Post Request", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String request_user_name = name.getText().toString().trim();
                        String mobile = mobileNumber.getText().toString().trim();
                        String bloodGroup = bloodGroupSpinner.getSelectedItem().toString();
                        String city = citiesSpinner.getSelectedItem().toString();
                        String completeAddress = address.getText().toString().trim();

                        if (TextUtils.isEmpty(request_user_name)) {
                            Toast.makeText(getActivity(), "Enter complete name!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (TextUtils.isEmpty(mobile)) {
                            Toast.makeText(getActivity(), "Enter mobile no.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (TextUtils.isEmpty(completeAddress)) {
                            Toast.makeText(getActivity(), "Enter complete address!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        ref.push().setValue(new BloodRequest(request_user_name,
                                bloodGroup,
                                new SimpleDateFormat("HH:mm", Locale.US).format(Calendar.getInstance().getTime()),
                                new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Calendar.getInstance().getTime()),
                                completeAddress,
                                mobile,
                                city));
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

        //TODO: Must add dynamic permissions. Remove explicit permissions from manifest file.
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

    public void signOut() {
        auth.signOut();

        if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 1)
            while(getActivity().getSupportFragmentManager().getBackStackEntryCount() != 1)
                getActivity().getSupportFragmentManager().popBackStack();

        showMenu(false);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainFragmentFrame,
                        new LoginFragment(),
                        LoginFragment.class.getSimpleName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    @Override
    public void onStop() {
        super.onStop();
        queryRef.removeEventListener(childEventListener);
    }

    public void showMenu(boolean showMenu)
    {
        if(menu == null)
            return;
        menu.setGroupVisible(R.id.mainMenuGroup, showMenu);
    }
}
