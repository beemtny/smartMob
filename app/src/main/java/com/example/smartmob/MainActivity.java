package com.example.smartmob;

import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.os.Build;
//import android.Manifest;
//import android.provider.Settings;
//import androidx.core.content.ContextCompat;
//import android.support.v4.app.ActivityCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    static final int MY_PERMISSIONS_MANAGE_WRITE_SETTINGS = 100 ;
    static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 69;

    private boolean mLocationPermission = false;
    private boolean mSettingPermission = true;

    private String randomPin(){
        Random rand = new Random();
        int randNum = rand.nextInt(100000);
        String pin = String.format("%06d", randNum);
        return pin;
    }

    protected void onJoinNewGroupBtn(){
        Intent intent = new Intent(this, JoinGroupActivity.class);
        startActivity(intent);
    }

    protected void onCreateGroupBtn(){
        Intent intent = new Intent(this, CreateGroupActivity.class);
        String pin = randomPin();
        intent.putExtra("pin",pin);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_and_create);

//        settingPermission();
//        locationsPermission();

        final Button button = findViewById(R.id.joinNewGroupButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onJoinNewGroupBtn();
            }
        });

        final Button create_group_button = findViewById(R.id.createGroupButton);
        create_group_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onCreateGroupBtn();
            }
        });
    }

//    private void settingPermission() {
//        mSettingPermission = true;
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (!Settings.System.canWrite(getApplicationContext())) {
//                mSettingPermission = false;
//                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:" + getPackageName()));
//                startActivityForResult(intent, MY_PERMISSIONS_MANAGE_WRITE_SETTINGS);
//            }
//        }
//    }


//    private void locationsPermission(){
//        mLocationPermission = true;
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            mLocationPermission = false;
//            // Permission is not granted
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
//
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//
//            } else {
//
//                // No explanation needed; request the permission
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
//                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
//
//                // MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION is an
//                // app-defined int constant. The callback method gets the
//                // result of the request.
//            }
//        }
//    }
}
