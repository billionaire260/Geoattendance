package com.example.geo_attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sharedfences extends AppCompatActivity {
RecyclerView recyclerView;
FirebaseDatabase mydatabase;
FirebaseRecyclerAdapter<Models,Shareddetailsviewholder> adapter;
Toolbar tools;
FirebaseRecyclerOptions<Models> options ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharedfences);
        recyclerView = findViewById(R.id.detailsdata);
        mydatabase = FirebaseDatabase.getInstance();
        tools = findViewById(R.id.mytool);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Sharedfences.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        setSupportActionBar(tools);
        fetch();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.empmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.myrs:
                startActivity(new Intent(getApplicationContext(),Myrequests.class));
                return true;
                default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void fetch()
    {
        DatabaseReference myref = mydatabase.getReference().child("new");
        options = new FirebaseRecyclerOptions.Builder<Models>().setQuery(myref, Models.class).build();
        adapter = new FirebaseRecyclerAdapter<Models, Shareddetailsviewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Shareddetailsviewholder holder, int position, @NonNull Models model) {
                holder.latitude.setText(model.getLatitude());
                holder.longitude.setText(model.getLongitude());
                holder.proc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Sharedfences.this,Fingerprintverification.class);
                        intent.putExtra("lati" ,model.getLatitude());
                        intent.putExtra("loni" ,model.getLongitude());
                        startActivity(intent);

                    }
                });
                holder.req.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(),OffOfficeRequest.class));
                    }
                });

            }

            @NonNull
            @Override
            public Shareddetailsviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.samplefensedetails,parent,false);
                return  new Shareddetailsviewholder(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }
}