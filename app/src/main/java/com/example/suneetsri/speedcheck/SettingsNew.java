package com.example.suneetsri.speedcheck;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * Created by suneetsri on 8/9/17.
 */

public class SettingsNew extends AppCompatActivity {
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
        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!number1.getText().toString().isEmpty()&&!number2.getText().toString().isEmpty()&&!number3.getText().toString().isEmpty()){
                    //Save the changes
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("SPEED_LIMIT",speedLimit.getText().toString());
                    if(toggleButton.isChecked()){
                        editor.putString("NOTIFICATION_ENABLED","T");
                    }
                    else{
                        editor.putString("NOTIFICATION_ENABLED","F");
                    }
                    editor.putString("USER_NUMBER1",number1.getText().toString());
                    editor.putString("USER_NUMBER2",number2.getText().toString());
                    editor.putString("USER_NUMBER3",number3.getText().toString());
                    editor.apply();
                    Toast.makeText(SettingsNew.this, "Values Updated", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(SettingsNew.this,"All Fields Requered!", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}