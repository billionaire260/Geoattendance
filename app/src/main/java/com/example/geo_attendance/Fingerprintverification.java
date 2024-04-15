package com.example.geo_attendance;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.app.KeyguardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.concurrent.Executor;
import java.util.concurrent.Executor;

public class Fingerprintverification extends AppCompatActivity {
    Button auth;
    Button pattern;
    public static int INTENT_AUTHENTICATE=10001;
    TextView status;

    private Executor executor;
    private   BiometricPrompt prompt;
    private BiometricPrompt.PromptInfo promptInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprintverification);
        auth = findViewById(R.id.auth);
        pattern = findViewById(R.id.pattern);
        status = findViewById(R.id.status);
        executor = ContextCompat.getMainExecutor(Fingerprintverification.this);
        prompt = new BiometricPrompt(Fingerprintverification.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                status.setText("Error" +errString);
              //  Toast.makeText(getApplicationContext(),"Failed"+errString ,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {


                super.onAuthenticationSucceeded(result);
                Intent intent = getIntent();
                status.setText("SUCCESS");
                Intent i = new Intent(getApplicationContext(),Empmaps.class);
                String latitude = intent.getStringExtra("lati");
                String longitude = intent.getStringExtra("loni");
                i.putExtra("lat",latitude);
                i.putExtra("lon",longitude);
                startActivity(i);


            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                status.setText("FAILED");
                Toast.makeText(getApplicationContext(),"FAILED" ,Toast.LENGTH_LONG).show();
            }
        });
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric")
                .setSubtitle("Sign in using fingerprint")
                .setNegativeButtonText("USER butoon text").build();




        auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                prompt.authenticate(promptInfo);
            }
        });
        pattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    KeyguardManager km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

                    if (km.isKeyguardSecure()) {
                        Intent authIntent = km.createConfirmDeviceCredentialIntent("Confirm Pattern","fingerprint not working?");
                        startActivityForResult(authIntent, INTENT_AUTHENTICATE);
                    }
                };
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_AUTHENTICATE) {
            if (resultCode == RESULT_OK) {
                Intent intent = getIntent();
                status.setText("SUCCESS");
                Intent i = new Intent(getApplicationContext(),Empmaps.class);
                String latitude = intent.getStringExtra("lati");
                String longitude = intent.getStringExtra("loni");
                i.putExtra("lat",latitude);
                i.putExtra("lon",longitude);
                startActivity(i);

            }
        }
    }
}