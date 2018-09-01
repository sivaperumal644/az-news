package com.example.siva.aznews;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by siva on 29/7/18.
 */

public class splashscreenActivity extends AppCompatActivity {
    private static int splash_timeout = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_splashscreen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent main = new Intent(splashscreenActivity.this,MainActivity.class);
                startActivity(main);
                finish();
            }
        },splash_timeout);
    }
}
