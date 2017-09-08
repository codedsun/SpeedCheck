package com.example.suneetsri.speedcheck;

import android.app.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by suneetsri on 7/9/17.
 */

public class Location extends Service implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    FusedLocationProviderApi fusedLocationProviderApi;
    GoogleApiClient googleApiClient;
    LocationRequest locationRequest;
    Intent i = new Intent("NOW");
    float speed;
    float speedLimit;
    NotificationManager notificationManager;
    NotificationCompat.Builder builder;
    Notification notificationCompat;
    SharedPreferences sharedPreferences;
    Vibrator vibrator;
    MediaPlayer mp;

    @Override
    public void onStart(Intent intent, int startId) {
        googleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        sharedPreferences = getSharedPreferences("SPEEDCHECK_APP", MODE_PRIVATE);
        Log.e("", "onStart:sas ");
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        googleApiClient.connect();
        builder = new
                NotificationCompat.Builder(this)
                .setAutoCancel(false)
                .setContentText("You are Offline")
                .setSmallIcon(R.drawable.cast_ic_notification_small_icon)
                .setContentTitle("Speed")
                .setOngoing(true);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        mp=MediaPlayer.create(getApplicationContext(),R.raw.alarm);

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(getApplicationContext(), "Connection Suspended", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(), "Connection Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
       speedLimit= Float.parseFloat(sharedPreferences.getString("SPEED_LIMIT","0"));
       // speed=location.getSpeed();
        speed=0;

        Application.user.setCurrentSpeed(speed);


        //LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);
        if(speed<=10.0)
        {
            notificationCompat = builder.build();


            notificationManager.notify(1,notificationCompat);
          /*  Intent i=new Intent(this,MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);*/
        }
        else if ((speed <speedLimit)&&(speed>10.0)) {
            builder.setContentText("You are Driving");
            builder.setSubText("Drive Safe and Follow the rules of Road Ministry");

            notificationCompat = builder.build();
            notificationManager.notify(1,notificationCompat);

            if(Application.user.getLoopCondition()==0)
            {
                Intent i=new Intent(this,UserProfile.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                vibrator.vibrate(new long[]{1,2,3,4,5,6,7,8},1);
                Application.user.setLoopCondition(1);

            }

        }
        else if(speed>=speedLimit)
        {

            if(Application.user.getLoopConditionOverlay()==0) {
                Intent i = new Intent(this, OverlayScreen.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                mp.start();
                Application.user.setLoopConditionOverlay(1);
            }
        }
        else {
        }

        Log.e("SAS", "onLocationChanged: "+location.getAltitude() );

    }
}
