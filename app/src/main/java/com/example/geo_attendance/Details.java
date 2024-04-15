package com.example.geo_attendance;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Details extends AppCompatActivity {
    private static final int REQUEST_LOCATION = 1;
    TextView showlocationlatitude,showlocationlongitude,emkey;
    FirebaseAuth auth;
    DatabaseReference mydb;
    LocationManager locationManager;
    ProgressBar pro;
    Double lat,lon;
    String latitude, longitude;
    Button getlocation,createmap;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        pro = findViewById(R.id.progress);
        auth = FirebaseAuth.getInstance();
        showlocationlatitude= findViewById(R.id.locatedlatitude);
        showlocationlongitude = findViewById(R.id.locatedlongitude);
        emkey = findViewById(R.id.emkey);
        emkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clipData = android.content.ClipData.newPlainText("Text Label", emkey.getText().toString());
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(getApplicationContext(),"Copied from Clipboard!",Toast.LENGTH_SHORT).show();


            }
        });
        //check if permission is enabled
        getlocation = findViewById(R.id.locate);
        createmap = findViewById(R.id.createmap);
        toolbar = findViewById(R.id.toolbar1);
        getSupportActionBar();
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.employee);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Addnewemployees.class));
            }
        });

        getlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(Details.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);

                }
                else
                {
                    getCurrentlocation();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_LOCATION && grantResults.length>0)
        {
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                getCurrentlocation();
            }

        }
        else
        {
            Toast.makeText(getApplicationContext(),"permission denied",Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentlocation()

    {
        pro.setVisibility(View.VISIBLE);
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(30000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.getFusedLocationProviderClient(Details.this).
                requestLocationUpdates(locationRequest,new LocationCallback(){
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(Details.this).removeLocationUpdates(this);
                        if(locationResult !=null && locationResult.getLocations().size()>0 )
                        {
                            int latestlocationindex = locationResult.getLocations().size()-1;
                            lat = locationResult.getLocations().get(latestlocationindex).getLatitude();
                            lon = locationResult.getLocations().get(latestlocationindex).getLongitude();
                            showlocationlatitude.setText(String.valueOf(lat));
                            showlocationlongitude.setText(String.valueOf(lon));
                            emkey.setText(auth.getCurrentUser().getUid());
                        }
                        pro.setVisibility(View.GONE);
                    }

                },Looper.getMainLooper());

            createmap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mydb = FirebaseDatabase.getInstance().getReference("fenses").child(auth.getCurrentUser().getUid()).child(auth.getCurrentUser().getUid());
                    Models model = new Models(
                            auth.getCurrentUser().getUid(),
                            String.valueOf(lat),
                            String.valueOf(lon),
                            "seven",
                            "today"
                    );
                    mydb.setValue(model);
                    if(showlocationlatitude.getText().toString().isEmpty())
                    {
                        Toast.makeText(Details.this,"Please derive your details",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(showlocationlongitude.getText().toString().isEmpty())
                    {
                        Toast.makeText(Details.this,"Please derive your details",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(getApplicationContext(),"Details saved",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),Geofencepage.class);
                    intent.putExtra("latitude",showlocationlatitude.getText().toString());
                    intent.putExtra("longitude",showlocationlongitude.getText().toString());
                    startActivity(intent);
                }
            });
    }


}