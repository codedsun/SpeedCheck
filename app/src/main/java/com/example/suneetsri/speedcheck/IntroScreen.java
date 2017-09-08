package com.example.suneetsri.speedcheck;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntro2Fragment;

/**
 * Created by suneetsri on 8/9/17.
 */

public class IntroScreen extends AppIntro2 {
    @Override
    public void onDonePressed() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSlide(AppIntro2Fragment.newInstance("Welcome","Hello Welcome to Speed Check App !",R.drawable.roadsafetyone,Color.RED));
        addSlide(AppIntro2Fragment.newInstance("Info","This App uses a Service and monitors your data in background ",R.drawable.introscreentwo,Color.GREEN));
        askForPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.SEND_SMS,
                Manifest.permission.INTERNET,
                Manifest.permission.VIBRATE},2);


    }
}
