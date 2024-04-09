package com.example.geo_attendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OffOfficeRequest extends AppCompatActivity {
EditText firstname,lname,description,purpose;
Button request;
FirebaseAuth auth;
String epdate,eptime,eplat,eplon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_off_office_request);
        firstname = findViewById(R.id.offfname);
        lname = findViewById(R.id.offflname);
        request = findViewById(R.id.sendrequest);
        auth = FirebaseAuth.getInstance();
        description = findViewById(R.id.offplace);
        purpose = findViewById(R.id.offplace);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        epdate = formatter.format(date);
        eptime = formatter2.format(date);
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(firstname.getText().toString().isEmpty())
                {
                    firstname.setError("Required field");
                    return;
                }
                if(lname.getText().toString().isEmpty())
                {
                    lname.setError("Required");
                    return;
                }
                if(description.getText().toString().isEmpty())
                {
                    lname.setError("Required");
                    return;
                }
                if(purpose.getText().toString().isEmpty())
                {
                    purpose.setError("Required");
                    return;
                }

                DatabaseReference requests = FirebaseDatabase.getInstance().getReference().child("requests").child(auth.getCurrentUser().getUid());
                DatabaseReference requests2 = FirebaseDatabase.getInstance().getReference().child("perrequests").child(auth.getCurrentUser().getUid()).child(auth.getCurrentUser().getUid());
                FusedLocationProviderClient fusedLocationClient;
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


                    return;
                }
                fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location!=null) {
                            eplat = String.valueOf(location.getLatitude());
                            eplon = String.valueOf(location.getLongitude());
                            RequestModel requestModel = new RequestModel(
                                    eplat,
                                    eplon,
                                    firstname.getText().toString(),
                                    lname.getText().toString(),
                                    epdate,
                                    eptime,
                                    "Unapproved",
                                    purpose.getText().toString(),
                                    description.getText().toString(),
                                    auth.getCurrentUser().getUid()
                            );
                            requests.setValue(requestModel);
                            requests2.setValue(requestModel);
                            Toast.makeText(getApplicationContext(), "Request sent", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Retrieving details", Toast.LENGTH_SHORT).show();
                            return;
                        }


                    }
                });

            }
        });

    }
}