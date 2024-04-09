package com.example.geo_attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Codeholder extends AppCompatActivity {
    FirebaseDatabase mydb;
    String lat,longitude;
    EditText code;
    TextView genlat;
    TextView genlong;
    Button proceed;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codeholder);
        mydb = FirebaseDatabase.getInstance();
        code = findViewById(R.id.employeecode);
        genlat = findViewById(R.id.genlat);
        genlong = findViewById(R.id.genlong);
        progressDialog = new ProgressDialog(Codeholder.this);
        proceed = findViewById(R.id.proceed);
        sharedPreferences = getSharedPreferences("code", Context.MODE_PRIVATE);

        // implemement a shared preference in this case



        //
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(code.getText().toString().isEmpty())
                {
                    code.setError("Required");
                    return;
                }
                //fetch using the viewholders
                SharedPreferences.Editor  editor= sharedPreferences.edit();
                editor.putString("usercode",code.getText().toString());
                editor.commit();



                //fetch using the viewholders
                progressDialog.setMessage("Searching details");
                DatabaseReference myref = mydb.getReference("fenses").child(code.getText().toString()).child(code.getText().toString()).child("latitude");
                DatabaseReference myref2 = mydb.getReference("fenses").child(code.getText().toString()).child(code.getText().toString()).child("longitude");
                if(myref.toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(myref2.toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog.show();
                myref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot snapshot) {
                        // Toast.makeText(getApplicationContext(),snapshot.getValue(String.class),Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getApplicationContext(),snapshot.getValue(String.class),Toast.LENGTH_SHORT).show();
                        lat = snapshot.getValue(String.class);


                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }


                });


                myref2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {


                            //Toast.makeText(getApplicationContext(),snapshot.getValue(String.class),Toast.LENGTH_SHORT).show();
                            longitude = snapshot.getValue(String.class);
                            progressDialog.dismiss();

                        {

                        }


                    }

                    @Override
                    public void onCancelled( DatabaseError error) {
                          progressDialog.dismiss();
                    }
                });

                if(lat==null && longitude == null)
                {
                    Toast.makeText(getApplicationContext(),"No such field parameters",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    return;
                }

                else {
                    Intent intent = new Intent(Codeholder.this,Geofencepage.class);
                    intent.putExtra("latitude",lat);
                    intent.putExtra("longitude",longitude);
                    startActivity(intent);
                    progressDialog.dismiss();
                }


            }
        });










    }
}