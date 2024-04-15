package com.example.geo_attendance;

public class Models {
    String id,latitude,longitude,radious,date;
    public Models()
    {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Models(String id, String latitude, String longitude, String radious, String date) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radious = radious;
        this.date = date;
    }
}
