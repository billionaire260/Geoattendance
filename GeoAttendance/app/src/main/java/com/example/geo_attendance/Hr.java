package com.example.geo_attendance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Hr extends Fragment {
    TextView back;
    EditText username,password;
    Button register;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;


    public Hr() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_hr, container, false);
        back = view.findViewById(R.id.back);
        progressDialog = new ProgressDialog(getContext());
        username = view.findViewById(R.id.regusername);
        firebaseAuth = FirebaseAuth.getInstance();
        password = view.findViewById(R.id.regpassword);
        register = view.findViewById(R.id.create);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().isEmpty())
                {
                    username.setError("Required");
                    return;
                }
                if(password.getText().toString().isEmpty())
                {
                    password.setError("Required");
                    return;
                }
                progressDialog.setMessage("Processing");
                progressDialog.show();
                firebaseAuth.signInWithEmailAndPassword(username.getText().toString(),password.getText().toString()).addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    progressDialog.dismiss();
                                    startActivity(new Intent(getContext(),Hrmenu.class));
                                }
                                else
                                {
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(),"INCORRECT LOGIN DETAILS",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                );
            }
        });




        return  view;
    }
}