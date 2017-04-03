package com.bloodport.fragment;

import com.bloodport.R;
import com.bloodport.model.UserRegistration;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.InputType;
import android.text.TextUtils;
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

/**
 * Created by aliabbasjaffri on 18/03/2017.
 */

public class RegistrationMainPageFragment extends Fragment
{
    View view;
    EditText completeName;
    EditText phoneNumber;
    EditText emailAddress;
    EditText password;
    RadioGroup genderGroup;
    CheckBox termsAndConditions;
    Button registerButton;
    Spinner bloodGroupSpinner;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private FirebaseAuth auth;
    DatabaseReference mDatabase;
    ProgressDialog progressDialog;
    UserRegistration newUser;

    public RegistrationMainPageFragment()
    {
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_registration, container, false);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = prefs.edit();

        emailAddress = (EditText) view.findViewById(R.id.registrationFragmentEmailAddressEditText);
        password = (EditText) view.findViewById(R.id.registrationFragmentPasswordEditText);
        completeName = (EditText) view.findViewById(R.id.registrationFragmentCompleteNameEditText);
        phoneNumber = (EditText) view.findViewById(R.id.registrationFragmentPhoneNumberEditText);
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
        String email = emailAddress.getText().toString().trim();
        String passwordText = password.getText().toString().trim();

        //phone = phone.replace("(" , "").replace(")","").replace("-","").replace(" ", "");

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getActivity(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(passwordText)) {
            Toast.makeText(getActivity(), "Enter password!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (passwordText.length() < 6) {
            Toast.makeText(getActivity(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getActivity(), "Enter complete name", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(phone) || phone.length() < 11) {
            Toast.makeText(getActivity(), "Enter correct phone number!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(gender)) {
            Toast.makeText(getActivity(), "Please enter gender!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!terms) {
            Toast.makeText(getActivity(), "Please enter gender!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void reviewDetails()
    {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.popup_confirm_details, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);

        TextView email = (TextView) promptView.findViewById(R.id.confirmPopupEmailTextView);
        TextView name = (TextView) promptView.findViewById(R.id.confirmPopupNameTextView);
        TextView mobileNumber = (TextView) promptView.findViewById(R.id.confirmPopupPhoneNumberTextView);
        TextView gender = (TextView) promptView.findViewById(R.id.confirmPopupGenderTextView);
        TextView bloodGroups = (TextView) promptView.findViewById(R.id.confirmPopupBloodGroupTextView);

        email.setText(emailAddress.getText().toString().trim());
        name.setText(completeName.getText().toString().trim());
        mobileNumber.setText(phoneNumber.getText().toString().trim());
        gender.setText(((RadioButton) view.findViewById(genderGroup.getCheckedRadioButtonId())).getText().toString());
        bloodGroups.setText(bloodGroupSpinner.getSelectedItem().toString());

        newUser = new UserRegistration(name.getText().toString().trim(),
                mobileNumber.getText().toString().trim(),
                gender.getText().toString().trim(),
                bloodGroups.getText().toString().trim(),
                email.getText().toString().trim());

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setMessage("Registering New User");
                        progressDialog.show();

                        registerUserOnFirebase(emailAddress.getText().toString().trim(), password.getText().toString().trim());
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

    private void registerUserOnFirebase(String emailAddress, String password)
    {
        auth.createUserWithEmailAndPassword(emailAddress, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (!task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            mDatabase.child("users").child(task.getResult().getUser().getUid()).setValue(newUser);

                            progressDialog.hide();

                            Toast.makeText(getActivity(), "You have successfully registered" , Toast.LENGTH_SHORT).show();

                            editor.putBoolean("skip_registration" , true).apply();
                            editor.putString("phoneNumber" , phoneNumber.getText().toString().trim()).apply();

                            getActivity().getSupportFragmentManager().popBackStack();
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.mainFragmentFrame,
                                            new LoginFragment(),
                                            LoginFragment.class.getSimpleName())
                                    .addToBackStack(LoginFragment.class.getSimpleName())
                                    .commit();
                        }
                    }
                });
    }
}
