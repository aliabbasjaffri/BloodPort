package com.bloodport.fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.bloodport.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by aliabbasjaffri on 02/04/2017.
 */

public class LoginFragment extends Fragment
{
    EditText emailAddress;
    EditText password;
    Button registration;
    Button login;
    TextView forgetPassword;
    FirebaseAuth auth;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;

    public LoginFragment()
    {
        auth = FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        emailAddress = (EditText) view.findViewById(R.id.loginFragmentEmailAddressEditText);
        password = (EditText) view.findViewById(R.id.loginFragmentPasswordEditText);
        registration = (Button) view.findViewById(R.id.loginFragmentRegisterButton);
        login = (Button) view.findViewById(R.id.loginFragmentLoginButton);
        forgetPassword = (TextView) view.findViewById(R.id.loginFragmentForgetPassword);

        progressDialog = new ProgressDialog(getActivity());
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = prefs.edit();

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.mainFragmentFrame,
                                new RegistrationMainPageFragment(),
                                RegistrationMainPageFragment.class.getSimpleName())
                        .addToBackStack(RegistrationMainPageFragment.class.getSimpleName())
                        .setTransition(FragmentTransaction.TRANSIT_EXIT_MASK)
                        .commit();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                progressDialog.setMessage("Loggin In..");
                progressDialog.show();

                String email = emailAddress.getText().toString();
                final String passwordText = password.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getActivity(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(passwordText)) {
                    Toast.makeText(getActivity(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //authenticate user
                auth.signInWithEmailAndPassword(email, passwordText)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (!task.isSuccessful())
                                {
                                    if (passwordText.length() < 6)
                                        password.setError(getString(R.string.login_fragment_minimum_password_error));
                                    else
                                        Toast.makeText(getActivity(), getString(R.string.login_fragment_auth_failed_error), Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    progressDialog.hide();
                                    editor.putBoolean("skip_registration" , true).apply();
                                    getActivity().getSupportFragmentManager().popBackStack();
                                    getActivity().getSupportFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.mainFragmentFrame,
                                                    new DashBoardFragment(),
                                                    DashBoardFragment.class.getSimpleName())
                                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                            .commit();
                                }
                            }
                        });
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Why the fuck did you forget it!", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}
