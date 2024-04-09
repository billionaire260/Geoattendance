package com.example.geo_attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Myrequests extends AppCompatActivity {
RecyclerView personalrequests;
FirebaseAuth mauth;
    FirebaseRecyclerAdapter<RequestModel,MyrequestViewholder> adapter;
    FirebaseRecyclerOptions<RequestModel> options ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myrequests);
        personalrequests = findViewById(R.id.personalrequests);
        mauth = FirebaseAuth.getInstance();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        personalrequests.setLayoutManager(linearLayoutManager);
        fetch();
    }

    public void fetch() {
        DatabaseReference myref= FirebaseDatabase.getInstance().getReference().child("perrequests").child(mauth.getCurrentUser().getUid());
        options = new FirebaseRecyclerOptions.Builder<RequestModel>().setQuery(myref, RequestModel.class).build();
        adapter = new FirebaseRecyclerAdapter<RequestModel, MyrequestViewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyrequestViewholder holder, int position, @NonNull RequestModel model) {
                holder.mydate.setText("made on Date " +": "+model.getDate());
                holder.mytime.setText("request made at " +":" +model.getTime());
                holder.myplace.setText("Requesting to sign from " +model.getDescription());
                holder.mystatus.setText("Status of the request is "+model.getStatus());


                    holder.mysign.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(model.getStatus()!=null) {
                                if (model.getStatus().equals("Approved")) {
                                    Intent intent = new Intent(Myrequests.this, Fingerprintverification.class);
                                    intent.putExtra("lati", model.getLat());
                                    intent.putExtra("loni", model.getLon());
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Contact HR for approval", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                return;
                            }
                        }

                    });
                }


            @NonNull
            @Override
            public MyrequestViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.samplemyrequest,parent,false);
                return new MyrequestViewholder(v);
            }
        };
        adapter.startListening();
        personalrequests.setAdapter(adapter);
    }
}