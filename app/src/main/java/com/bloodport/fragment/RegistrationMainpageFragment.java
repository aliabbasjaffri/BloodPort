package com.bloodport.fragment;

import com.bloodport.R;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by aliabbasjaffri on 18/03/2017.
 */

public class RegistrationMainpageFragment extends Fragment
{
    View view;
    EditText completeName;
    EditText phoneNumber;
    RadioGroup genderGroup;
    RadioButton genderButton;
    CheckBox termsAndConditions;
    Button registerButton;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    public RegistrationMainpageFragment()
    {
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = prefs.edit();
    }

    public static RegistrationMainpageFragment newInstance() {
        return new RegistrationMainpageFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_registration, container, false);

        completeName = (EditText) view.findViewById(R.id.registrationFragmentCompleteNameEditText);
        phoneNumber = (EditText) view.findViewById(R.id.registrationFragmentPhoneNumberEditText);
        genderGroup = (RadioGroup) view.findViewById(R.id.registrationFragmentGenderRadioButton);
        termsAndConditions = (CheckBox) view.findViewById(R.id.termsAndConditionsCheckBox);
        registerButton = (Button) view.findViewById(R.id.registrationButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (validate())
                {

                    editor.putBoolean("skip_registration" , true).commit();
                }
                else
                {
                    editor.putBoolean("skip_registration" , false).commit();
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

        return !(name.equals("") || phone.equals("") || gender.equals("") || terms == false);
    }

}
