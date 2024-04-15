package com.example.geo_attendance;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EmployeeViewholder extends RecyclerView.ViewHolder {
    TextView fname,surname,department,email;
    public EmployeeViewholder(View itemView) {
        super(itemView);
        fname = itemView.findViewById(R.id.samplefname);
        surname = itemView.findViewById(R.id.samplesurname);
        department = itemView.findViewById(R.id.sampledepartment);


    }
}
