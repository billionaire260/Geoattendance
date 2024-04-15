package com.example.geo_attendance;

public class Mods {
    public Mods()
    {

    }
    String latitude,longitude,radious;

    public Mods(String latitude, String longitude, String radious) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.radious = radious;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getRadious() {
        return radious;
    }

    public void setRadious(String radious) {
        this.radious = radious;
    }
}
