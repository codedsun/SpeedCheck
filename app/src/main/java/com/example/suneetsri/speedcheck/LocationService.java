package com.example.suneetsri.speedcheck;

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.List;

/**
 * Created by suneetsri on 7/9/17.
 */

public class LocationService extends IntentService implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    GoogleApiClient googleApiClient;
    Intent i=new Intent("NOW");
    float speed;
    NotificationManager notificationManager;
    NotificationCompat.Builder builder;
    Notification notificationCompat;
    SharedPreferences sharedPreferences;



    FusedLocationProviderApi fusedLocationProviderApi;
    LocationRequest locationRequest;


    public LocationService()
    {
        super("Location");


    }

    @Override
    public void onDestroy() {
        googleApiClient.disconnect();

    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        fusedLocationProviderApi = LocationServices.FusedLocationApi;
        googleApiClient = new GoogleApiClient.Builder(this).
                addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        sharedPreferences=getSharedPreferences("SPEEDCHECK_APP",MODE_PRIVATE);
        Log.e("", "onStart:sas " );
        notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        googleApiClient.connect();
        builder=new
                NotificationCompat.Builder(this)
                .setAutoCancel(false)
                .setContentText("You are Offline")
                .setSmallIcon(R.drawable.cast_ic_notification_small_icon)
                .setContentTitle("Speed")
                .setOngoing(true);





    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public LocationService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        ActivityManager am = (ActivityManager) this .getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        ComponentName componentInfo = taskInfo.get(0).topActivity;
        Log.e("TAG", "CURRENT Activity ::" + taskInfo.get(0).topActivity.getClassName()+"   Package Name :  "+componentInfo.getPackageName());

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

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {


        i.putExtra("loc",Float.toString(location.getSpeed()));
       // speed=location.getSpeed();

        speed= Float.parseFloat(sharedPreferences.getString("SPEED_LIMIT","50"));
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);
        if(speed<=10.0)
        {
            notificationCompat = builder.build();


            notificationManager.notify(1,notificationCompat);
          /*  Intent i=new Intent(this,MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);*/
        }
        else if ((speed <=50.0)&&(speed>10.0)) {
            builder.setContentText("You are Driving");
            builder.setSubText("Drive Safe and Follow the rules of Road Ministry");


            notificationCompat = builder.build();
            notificationManager.notify(1,notificationCompat);
            Intent i=new Intent(this,OverlayScreen.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);

        }
        else if(speed>=60)
        {


        }
        else {
        }

        Log.e("SAS", "onLocationChanged: "+location.getAltitude() );

    }
}
