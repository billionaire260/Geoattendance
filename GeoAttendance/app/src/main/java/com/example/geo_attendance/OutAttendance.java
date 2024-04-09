package com.example.geo_attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class OutAttendance extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_attendance);
        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadUrl("https://docs.google.com/spreadsheets/d/1AktOYVbte-sHHJDscyVW08pLj_0hYWm-Zodyzbs51ug/edit#gid=0");
    }
}