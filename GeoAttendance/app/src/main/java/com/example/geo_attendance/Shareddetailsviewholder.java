package com.example.geo_attendance;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Shareddetailsviewholder extends RecyclerView.ViewHolder {
    TextView latitude,longitude;
    Button proc,req;
    public Shareddetailsviewholder(@NonNull View itemView) {
        super(itemView);
        latitude = itemView.findViewById(R.id.samplelatitude);
        longitude = itemView.findViewById(R.id.samplelongitude);
        proc = itemView.findViewById(R.id.proc);
        req = itemView.findViewById(R.id.offreq);
    }
}
