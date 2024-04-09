package com.example.geo_attendance;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RequestsViewholder extends RecyclerView.ViewHolder {
    TextView fname,lname,purpose,place,date,time,status;
    Button approve;
    public RequestsViewholder(@NonNull View itemView) {
        super(itemView);
        fname = itemView.findViewById(R.id.reqfname);
        lname = itemView.findViewById(R.id.reqlname);
        purpose = itemView.findViewById(R.id.reqlpurpose);
        place = itemView.findViewById(R.id.reqplace);
        date = itemView.findViewById(R.id.reqdate);
        time = itemView.findViewById(R.id.reqtime);
        status = itemView.findViewById(R.id.reqstatus);
        approve = itemView.findViewById(R.id.accepreq);

    }
}
