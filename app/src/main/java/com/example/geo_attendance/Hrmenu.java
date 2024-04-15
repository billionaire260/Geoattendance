package com.example.geo_attendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class Hrmenu extends AppCompatActivity {

    CardView fences, employees,webv,emprequest,notes,out;
    Double lat,lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hrmenu);
        webv = findViewById(R.id.webv);
        fences = findViewById(R.id.create);
        employees = findViewById(R.id.manage);
        out = findViewById(R.id.out);
        notes = findViewById(R.id.mynotifications);
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),OutAttendance.class));
            }
        });
        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "No pending notifications", Toast.LENGTH_SHORT).show();
            }
        });
        emprequest = findViewById(R.id.emprequests);
        emprequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),EmployeesAwayrequests.class));
            }
        });
        webv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Viewweb.class));
            }
        });
        fences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //generate params here
                LocationRequest locationRequest = new LocationRequest();
                locationRequest.setInterval(10000);
                locationRequest.setFastestInterval(3000);
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

                if (ActivityCompat.checkSelfPermission(Hrmenu.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Hrmenu.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions

                    return;
                }
                LocationServices.getFusedLocationProviderClient(Hrmenu.this)
                        .requestLocationUpdates(locationRequest, new LocationCallback() {

                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                super.onLocationResult(locationResult);
                                LocationServices.getFusedLocationProviderClient(getApplicationContext())
                                        .removeLocationUpdates(this);
                                if (locationResult != null && locationResult.getLocations().size() > 0) {
                                    int latestlocIndex = locationResult.getLocations().size() - 1;
                                    lat = locationResult.getLocations().get(latestlocIndex).getLatitude();
                                     lon = locationResult.getLocations().get(latestlocIndex).getLongitude();


                                    Location location = new Location("providerNA");
                                    location.setLongitude(lat);
                                    location.setLatitude(lon);


                                }
                            }
                        }, Looper.getMainLooper());

                if(lat!=null && lon!=null)
                {
                    Intent i = new Intent(getApplicationContext(),Geofencepage.class);
                    i.putExtra("latitude",String.valueOf(lat));
                            i.putExtra("longitude",String.valueOf(lon));
                            startActivity(i);
                            finish();

                }
                //startActivity(new Intent(getApplicationContext(),Geofencepage.class));
                //finish();
                else
                {
                    return;
                }
            }
        });
        employees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Addnewemployees.class));
                finish();
            }
        });
    }
    private void getCurrentLocation() {



    }

}