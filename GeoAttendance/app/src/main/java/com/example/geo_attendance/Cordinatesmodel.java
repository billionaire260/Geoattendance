package com.example.geo_attendance;

public class Cordinatesmodel {
    public Cordinatesmodel()
    {

    }
    String latititude,longitude;

    public Cordinatesmodel(String latititude, String longitude) {
        this.latititude = latititude;
        this.longitude = longitude;
    }

    public String getLatititude() {
        return latititude;
    }

    public void setLatititude(String latititude) {
        this.latititude = latititude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
