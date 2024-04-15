package com.example.geo_attendance;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyrequestViewholder extends RecyclerView.ViewHolder {
    TextView mytime,myplace,mydate,mystatus;
    Button mysign;

    public MyrequestViewholder(@NonNull View itemView) {
        super(itemView);
        mytime = itemView.findViewById(R.id.mytime);
        mydate = itemView.findViewById(R.id.mydate);
        mystatus = itemView.findViewById(R.id.mystatus);
        myplace = itemView.findViewById(R.id.myplace);
        mysign = itemView.findViewById(R.id.mysign);
    }
}
