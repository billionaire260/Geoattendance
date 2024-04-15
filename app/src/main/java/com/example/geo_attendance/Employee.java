package com.example.geo_attendance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Employee extends Fragment {

    FirebaseDatabase mydb;
    String lat,longitude;
    EditText code;
    TextView genlat;
    TextView genlong;
    Button proceed;

    public Employee() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_employee, container, false);
            //employee logic

        mydb = FirebaseDatabase.getInstance();
        code = view.findViewById(R.id.employeecode);
        genlat = view.findViewById(R.id.genlat);
        genlong = view.findViewById(R.id.genlong);

        proceed = view.findViewById(R.id.proceed);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(code.getText().toString().isEmpty())
                {
                    code.setError("Required");
                    return;
                }
                DatabaseReference myref = FirebaseDatabase.getInstance().getReference("fenses").child(code.getText().toString());
                FirebaseRecyclerAdapter<Cordinatesmodel,CoordinatesVieholder> adapter;
                FirebaseRecyclerOptions<Cordinatesmodel> options ;




            }
        });











            return  view;
    }
}