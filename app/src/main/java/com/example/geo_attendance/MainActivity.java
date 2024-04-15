package com.example.geo_attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
TextView have;
Button login;
Toolbar toolbar;
FirebaseAuth firebaseAuth;
ProgressDialog progressDialog;
EditText username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.maintool);
        toolbar.setTitle("LOGIN HERE");
        login = findViewById(R.id.login);
        firebaseAuth = FirebaseAuth.getInstance();
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        progressDialog = new ProgressDialog(this);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_west_24);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Firstactivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
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
                firebaseAuth.signInWithEmailAndPassword(username.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(getApplicationContext(),"Logging you in",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),Details.class));
                                    progressDialog.dismiss();

                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),"Try again later",Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }
                        });
            }
        });


    }
}