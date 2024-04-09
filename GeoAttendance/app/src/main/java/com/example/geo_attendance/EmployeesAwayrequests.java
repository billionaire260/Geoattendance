package com.example.geo_attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EmployeesAwayrequests extends AppCompatActivity {
RecyclerView allrequests;
    FirebaseRecyclerAdapter<RequestModel,RequestsViewholder> adapter;
    FirebaseRecyclerOptions<RequestModel> options ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees_awayrequests);
        allrequests = findViewById(R.id.allrequests);
        LinearLayoutManager l = new LinearLayoutManager(getApplicationContext());
        allrequests.setLayoutManager(l);
        fetch();
    }
    private void fetch()
    {
        DatabaseReference myref= FirebaseDatabase.getInstance().getReference().child("requests");
        DatabaseReference myref2= FirebaseDatabase.getInstance().getReference().child("perrequests");
        options = new FirebaseRecyclerOptions.Builder<RequestModel>().setQuery(myref, RequestModel.class).build();
        adapter = new FirebaseRecyclerAdapter<RequestModel, RequestsViewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RequestsViewholder holder, int position, @NonNull RequestModel model) {
              holder.fname.setText("Employee first Name  " +model.getFname());
              holder.lname.setText("Employee Last Name "+model.getLname());
              holder.purpose.setText("Request Purpose " +model.getPurpose());
              holder.place.setText("Request to "+ model.getDescription());
              holder.date.setText("On Date "+model.getDate());
              holder.time.setText("at " +model.getTime());
              holder.status.setText("Status " +model.getStatus());
              holder.approve.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      myref.child(model.getId()).child("status").setValue("Approved");
                      myref2.child(model.getId()).child(model.getId()).child("status").setValue("Approved");
                      Toast.makeText(getApplicationContext(), "You Approved Request", Toast.LENGTH_SHORT).show();
                  }
              });
            }

            @NonNull
            @Override
            public RequestsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.samplerequests,parent,false);
                return  new RequestsViewholder(view);
            }
        };
        adapter.startListening();
        allrequests.setAdapter(adapter);
    }
}