package com.pleiades.pleione.base64.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.pleiades.pleione.base64.Variables;
import com.pleiades.pleione.base64.ui.main.MainActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialize external input
        Intent intent = getIntent();
        if (intent.getAction().equals(Intent.ACTION_PROCESS_TEXT) && intent.hasExtra(Intent.EXTRA_PROCESS_TEXT))
            Variables.externalInput = intent.getStringExtra(Intent.EXTRA_PROCESS_TEXT);

        // start activity
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
