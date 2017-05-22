package com.bloodport.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bloodport.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by aliabbasjaffri on 21/05/2017.
 */

public class ForgetPasswordFragment extends Fragment
{
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    EditText email;

    public ForgetPasswordFragment()
    {
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forget_password, container, false);
        email = (EditText) view.findViewById(R.id.forgetPasswordFragmentEmailAddressEditText);
        Button sendRequest = (Button) view.findViewById(R.id.btnForgetPassword);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        email.getText().toString().trim();
        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                progressBar.setVisibility(View.VISIBLE);

                if (TextUtils.isEmpty(email.getText().toString().trim())) {
                    Toast.makeText(getActivity(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }

                auth.sendPasswordResetEmail(email.getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.INVISIBLE);
                                    getActivity().getSupportFragmentManager().popBackStack();

                                } else {
                                    Toast.makeText(getActivity(), "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        return view;
    }
}
