package com.example.geo_attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Empmaps extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {
    private int FINE_LOCATION_CODE = 10001;
    private int BACKGROUND_LOCATION_CODE = 10002;
    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;
    private GeofencingClient geofencingClient;
    private int GeofenseRadious = 200;
    Double lat,lon;
    private GeofenceHelper geofenceHelper;
    private String GEOFENSEID = "SOMEID";
    Toolbar toolbar;
    LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geofencepage);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        lat = Double.valueOf(intent.getStringExtra("lat"));
        lon = Double.valueOf(intent.getStringExtra("lon"));
        latLng = new LatLng(lat, lon);
        Toast.makeText(getApplicationContext(),String.valueOf(lat)+":"+String.valueOf(lon),Toast.LENGTH_SHORT).show();
        geofencingClient = LocationServices.getGeofencingClient(this);
        geofenceHelper = new GeofenceHelper(this);
        toolbar = findViewById(R.id.details);
        setSupportActionBar(toolbar);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.employeemenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.empsign:
                enableuserlocation();
                createfense();
                Toast.makeText(getApplicationContext(), "Wait for entry notification", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_LOCATION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                mMap.setMyLocationEnabled(true);
            } else {

            }
        }
        if (requestCode == BACKGROUND_LOCATION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "You can now add geofence", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        enableuserlocation();
       // createfense();

    }




    public void createfense()
    {




                            enableuserlocation();
                            addGeofense(latLng,100);
                            mMap.setOnMapLongClickListener(Empmaps.this);
                            ///new here
                            if (Build.VERSION.SDK_INT >= 30) {

                                if (ContextCompat.checkSelfPermission(Empmaps.this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                                        == PackageManager.PERMISSION_GRANTED) {
                                    mMap.clear();
                                    addmarker(latLng);
                                    addcircle(latLng, 100);
                                    addGeofense(latLng, 100);
                                } else {
                                    if (ActivityCompat.shouldShowRequestPermissionRationale(Empmaps.this, Manifest.permission
                                            .ACCESS_BACKGROUND_LOCATION)) {
                                        ActivityCompat.requestPermissions(Empmaps.this, new String[]{Manifest.permission
                                                .ACCESS_BACKGROUND_LOCATION}, BACKGROUND_LOCATION_CODE);
                                    }
                                }
                            } else {
                                mMap.clear();
                                addmarker(latLng);
                                addcircle(latLng, 100);
                                addGeofense(latLng, 100);
                            }





    }

    private void enableuserlocation() {
        //check if user permission is enabled
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                //show a dialog box
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_CODE);

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_CODE);
            }


        }

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        if (Build.VERSION.SDK_INT >= 30) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.clear();
                addmarker(latLng);
                addcircle(latLng, 30);
                addGeofense(latLng, 30);
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                        .ACCESS_BACKGROUND_LOCATION)) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission
                            .ACCESS_BACKGROUND_LOCATION}, BACKGROUND_LOCATION_CODE);
                }
            }
        } else {
            mMap.clear();
            addmarker(latLng);
            addcircle(latLng, 100);
            addGeofense(latLng, 100);
        }
    }


    private void addGeofense(LatLng latLng, float radious) {
        Geofence geofence = geofenceHelper.getGeofence(GEOFENSEID, latLng, radious,
                Geofence.GEOFENCE_TRANSITION_ENTER |
                        Geofence.GEOFENCE_TRANSITION_DWELL |
                        Geofence.GEOFENCE_TRANSITION_EXIT);
        GeofencingRequest geofencingRequest = geofenceHelper.geofencingRequest(geofence);
        PendingIntent pendingIntent = geofenceHelper.getPendingIntent();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        geofencingClient.addGeofences(geofencingRequest, pendingIntent).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Log.d(TAG, "geofense added");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String errormessage = geofenceHelper.getErrorString(e);
                Log.d(TAG, "oNFAILURE " + errormessage);
            }
        });

    }

    private void addmarker(LatLng latLng)
    {
        MarkerOptions markerOptions = new MarkerOptions().position(latLng);
        mMap.addMarker(markerOptions);
    }

    private void addcircle(LatLng latLng,int radius)
    {
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(latLng);
        circleOptions.radius(100.0);
        circleOptions.strokeColor(Color.argb(255,255,0,0));
        circleOptions.fillColor(Color.argb(255,255,0,0));
        circleOptions.strokeWidth(4);
        mMap.addCircle(circleOptions);
    }
}