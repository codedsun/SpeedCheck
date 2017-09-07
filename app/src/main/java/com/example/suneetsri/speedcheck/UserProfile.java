package com.example.suneetsri.speedcheck;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class UserProfile extends AppCompatActivity {
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Intent i = getIntent();
        name=i.getStringExtra("name");
        Toast.makeText(this,name,Toast.LENGTH_SHORT).show();
    }
}
