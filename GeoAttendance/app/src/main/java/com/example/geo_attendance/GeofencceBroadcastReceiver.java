package com.example.geo_attendance;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
public class GeofencceBroadcastReceiver  extends BroadcastReceiver  {

    private static final String TAG  = "Messagesss" ;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving

        NotificationHelper notificationHelper = new NotificationHelper(context);

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if(geofencingEvent.hasError())
        {
            Log.d(TAG,"HAS ERROR");
            return;
        }
        int transitiontype = geofencingEvent.getGeofenceTransition();
        switch (transitiontype)
        {
            case Geofence
                    .GEOFENCE_TRANSITION_ENTER:
                Toast.makeText(context,"You have entered",Toast.LENGTH_SHORT).show();
                notificationHelper.sendHighPriorityNotification("Entered","please sign",SignAttendance.class);
                break;
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                Toast.makeText(context,"You have Exited",Toast.LENGTH_SHORT).show();
                notificationHelper.sendHighPriorityNotification("Exited","",Register.class);
                break;
            case Geofence.GEOFENCE_TRANSITION_DWELL:
                Toast.makeText(context,"You are dwelling",Toast.LENGTH_SHORT).show();
                notificationHelper.sendHighPriorityNotification("Dwell","Thankyou",Register.class);
                break;
        }
    }

}
