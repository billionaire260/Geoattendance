package com.example.geo_attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SignAttendance extends AppCompatActivity {
Button sign;
String check;
    String androidId;
    String empfirstnae,emplastname,epdate,eptime;
    EditText firstname,lastname;
TextInputLayout f,l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_attendance);
        sign = findViewById(R.id.btnsign);
        firstname = findViewById(R.id.fname);
        f = findViewById(R.id.fnameerrrorbox);
        l = findViewById(R.id.lnameerrrorbox);
        lastname = findViewById(R.id.lastname);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firstname.getText().toString().isEmpty()) {
                    f.setError("Required");
                    return;
                }
                if (lastname.getText().toString().isEmpty()) {
                    l.setError("Required");
                    return;
                }
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm");
                Date date = new Date();
                epdate = formatter.format(date);
                eptime = formatter2.format(date);

                // Modelupload model = new Modelupload(uri.toString(),nameposting.getText().toString(),formatter.format(date));
                androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                empfirstnae = firstname.getText().toString();
                emplastname = lastname.getText().toString();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("adata").child(epdate).child(androidId).child("id");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        check = snapshot.getValue(String.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                if(check==null)
                {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbzbom4TlveMTribwue1mGHzkV9W9sZxdK6nTroVDjgUdufrOk2C/exec", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Toast.makeText(SignAttendance.this, response, Toast.LENGTH_SHORT).show();


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }


                    }

                    ) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> params = new HashMap<>();
                            params.put("action", "additem");
                            params.put("imei", androidId);
                            params.put("fname", empfirstnae);
                            params.put("lname", emplastname);
                            params.put("datesign", epdate);
                            params.put("timesign", eptime);
                            return params;

                        }
                    };

                    int SocketTimeout = 50000;
                    RetryPolicy retryPolicy = new DefaultRetryPolicy(SocketTimeout, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    stringRequest.setRetryPolicy(retryPolicy);
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                    //push the details to database
                    DatabaseReference m = FirebaseDatabase.getInstance().getReference().child("adata").child(epdate).child(androidId);
                    Attendancemodel attendancemodel = new Attendancemodel(androidId, epdate);
                    m.setValue(attendancemodel);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Device has signed", Toast.LENGTH_SHORT).show();
                }
            }


        });
    }
    //send to google sheets


}