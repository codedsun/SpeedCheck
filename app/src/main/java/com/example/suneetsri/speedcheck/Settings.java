package com.example.suneetsri.speedcheck;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Settings extends AppCompatActivity {
    ToggleButton toggleButton;
    EditText speedLimit;
    EditText number1;
    EditText number2;
    EditText number3;
    Button saveChanges;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        toggleButton=findViewById(R.id.settingsToggleButton);
        speedLimit=findViewById(R.id.settingsSpeedLimit);
        number1=findViewById(R.id.settingNumber1);
        number2=findViewById(R.id.settingNumber2);
        number3=findViewById(R.id.settingNumber3);
        saveChanges=findViewById(R.id.settingsSaveButton);

        sharedPreferences = getSharedPreferences("SPEEDCHECK_APP", MODE_PRIVATE);
        String notification=sharedPreferences.getString("NOTIFICATION_ENABLED","T");
        toggleButton.setChecked(Boolean.parseBoolean(notification));
        String limit=sharedPreferences.getString("SPEED_LIMIT","60");
        speedLimit.setText(limit);
        number1.setText(sharedPreferences.getString("USER_NUMBER1","No Data Found"));
        number2.setText(sharedPreferences.getString("USER_NUMBER2","No Data Found"));
        number3.setText(sharedPreferences.getString("USER_NUMBER3","No Data Found"));
        number1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(number1.getText().toString().length()!=10)
               {
                   Toast.makeText(Settings.this, "Invalid Number", Toast.LENGTH_SHORT).show();
                   Log.e("SHARED ToAST", "onClick: "+number1.getText().toString() );
               }
               else
               {
                   /*Log.e("SHARED", "onClick: "+number1.getText().toString() );
                   sharedPreferences.edit().putString("USER_NUMBER1",number1.getText().toString());*/
               }
            }
        });
        number2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(number2.getText().toString().length()!=10)
                {
                    Toast.makeText(Settings.this, "Invalid Number", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    sharedPreferences.edit().putString("USER_NUMBER2",number2.getText().toString());
                }
            }
        });
        number3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(number3.getText().toString().length()!=10)
                {
                    Toast.makeText(Settings.this, "Invalid Number", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    sharedPreferences.edit().putString("USER_NUMBER3",number1.getText().toString());
                }
            }
        });
        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("SHARED", "onClick: "+number1.getText().toString() );
                sharedPreferences.edit().putString("USER_NUMBER1",number1.getText().toString());
                Log.e("HI ", "onClick: "+ sharedPreferences.edit().commit() );
                sharedPreferences.edit().commit();
                Toast.makeText(Settings.this, "Values Updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Settings.this,UserProfile.class));
                finish();
            }
        });



    }
}
