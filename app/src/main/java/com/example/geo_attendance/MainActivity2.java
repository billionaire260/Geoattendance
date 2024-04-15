package com.example.geo_attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Thread background = new Thread()
        {
            public void  run()
            {
                try {
                    sleep(6*1000);
                    Intent m = new Intent(getBaseContext(),Firstactivity.class);
                    startActivity(m);
                    finish();

                }
                catch (Exception e)
                {

                }

            }
        };
        background.start();
    }

}