package com.example.geo_attendance;

public class RequestModel {
    String lat,lon,fname,lname,date,time,status,purpose,description,id;
    RequestModel()
    {

    }

    public RequestModel(String lat, String lon, String fname, String lname, String date, String time, String status, String purpose, String description, String id) {
        this.lat = lat;
        this.lon = lon;
        this.fname = fname;
        this.lname = lname;
        this.date = date;
        this.time = time;
        this.status = status;
        this.purpose = purpose;
        this.description = description;
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
