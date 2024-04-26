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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Employeesignup extends Fragment {
    Button employeesignup;
    EditText empemail,emppassword;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase mydb;
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_employeesignup, container, false);
        employeesignup = view.findViewById(R.id.employeesignup);
        empemail = view.findViewById(R.id.empemail);
        emppassword = view.findViewById(R.id.emppassword);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(getContext());
        mydb = FirebaseDatabase.getInstance();
        employeesignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(empemail.getText().toString().isEmpty())
                {
                    empemail.setError("Required");
                    return;
                }
                if(emppassword.getText().toString().isEmpty())
                {
                    emppassword.setError("Required");
                    return;
                }
                progressDialog.setMessage("Processing");
                progressDialog.show();
                firebaseAuth.signInWithEmailAndPassword(
                        empemail.getText().toString(),
                        emppassword.getText().toString()
                ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            DatabaseReference myref = mydb.getReference().child("employee").child(firebaseAuth.getCurrentUser().getUid()).child("type");
                            myref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange( DataSnapshot snapshot) {
                                    if(snapshot.getValue().equals("employee"))
                                    {
                                        //startActivity(new Intent(getContext(),Codeholder.class));
                                        startActivity(new Intent(getContext(),Sharedfences.class));
                                        progressDialog.dismiss();


                                    }
                                    else
                                    {
                                        Toast.makeText(getContext(),"Incorrect Login",Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError error) {

                                }
                            });

                        }
                        else
                        {
                              Toast.makeText(getContext(),"Incorrect Login",Toast.LENGTH_SHORT).show();
                              progressDialog.dismiss();
                        }

                    }
                });


            }
        });



        return view;
    }
}