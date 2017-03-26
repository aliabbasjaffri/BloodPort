package com.bloodport.fragment;

import com.bloodport.R;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by aliabbasjaffri on 18/03/2017.
 */

public class RegistrationMainpageFragment extends Fragment
{
    View view;
    EditText completeName;
    EditText phoneNumber;
    RadioGroup genderGroup;
    CheckBox termsAndConditions;
    Button registerButton;
    Spinner bloodGroupSpinner;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    public RegistrationMainpageFragment()
    {

    }

    public static RegistrationMainpageFragment newInstance() {
        return new RegistrationMainpageFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_registration, container, false);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = prefs.edit();

        completeName = (EditText) view.findViewById(R.id.registrationFragmentCompleteNameEditText);

        phoneNumber = (EditText) view.findViewById(R.id.registrationFragmentPhoneNumberEditText);
        phoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        phoneNumber.setInputType(InputType.TYPE_CLASS_PHONE);

        genderGroup = (RadioGroup) view.findViewById(R.id.registrationFragmentGenderRadioButton);
        termsAndConditions = (CheckBox) view.findViewById(R.id.termsAndConditionsCheckBox);
        registerButton = (Button) view.findViewById(R.id.registrationButton);
        bloodGroupSpinner = (Spinner) view.findViewById(R.id.registrationFragmentBloodGroupSpinner);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (validate())
                {
                    reviewDetails();
                }
                else
                {
                    editor.putBoolean("skip_registration" , false).apply();
                }
            }
        });
        return view;
    }

    private boolean validate()
    {
        String name = completeName.getText().toString().trim();
        String phone = phoneNumber.getText().toString().trim();
        boolean terms = termsAndConditions.isEnabled();
        String gender = ((RadioButton) view.findViewById(genderGroup.getCheckedRadioButtonId())).getText().toString();
        String bloodGroup = bloodGroupSpinner.getSelectedItem().toString();
        phone = phone.replace("(" , "").replace(")","").replace("-","").replace(" ", "");

        if (!phone.equals("") && phone.length() < 10)
        {
            Toast.makeText(getActivity(), "Please enter complete phone number" , Toast.LENGTH_SHORT).show();
            phoneNumber.setText("");
            return false;
        }

        return !(name.equals("") || gender.equals("") || bloodGroup.equals("") || terms == false);
    }

    private void reviewDetails()
    {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.popup_confirm_details, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);

        TextView name = (TextView) promptView.findViewById(R.id.confirmPopupNameTextView);
        TextView mobileNumber = (TextView) promptView.findViewById(R.id.confirmPopupPhoneNumberTextView);
        TextView gender = (TextView) promptView.findViewById(R.id.confirmPopupGenderTextView);
        TextView bloodGroups = (TextView) promptView.findViewById(R.id.confirmPopupBloodGroupTextView);

        name.setText(completeName.getText().toString().trim());
        mobileNumber.setText(phoneNumber.getText().toString().trim());
        gender.setText(((RadioButton) view.findViewById(genderGroup.getCheckedRadioButtonId())).getText().toString());
        bloodGroups.setText(bloodGroupSpinner.getSelectedItem().toString());

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        editor.putBoolean("skip_registration" , true).apply();
                        Toast.makeText(getActivity(), "You have successfully registered" , Toast.LENGTH_SHORT).show();
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
}
