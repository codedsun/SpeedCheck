package com.example.suneetsri.speedcheck;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText name;      //Field for Saving User Name
    private EditText econtact1;   //Field for Emergency contact 1
    private EditText econtact2;     //Field for Emergency contact 2
    private EditText econtact3;     //Field for Emergency contact 3
    private Button nextButton;      //Next Button
    private EditText speedLimit;
    private String username,cont1,cont2,cont3;
    private SharedPreferences sharedPreferences;
    private final String TAG="SPEEDCHECK_APP";      //Tag used for sharedPreferences

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i1=new Intent(this,LocationService.class);
        startService(i1);
        econtact1=(EditText)findViewById(R.id.econtact1);
        name=(EditText)findViewById(R.id.username);
        speedLimit=findViewById(R.id.speedLimit);
        econtact2=(EditText)findViewById(R.id.econtact2);
        econtact3=(EditText)findViewById(R.id.econtact3);
        nextButton=(Button)findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username=name.getText().toString();
                cont1=econtact1.getText().toString();
                cont2=econtact2.getText().toString();
                cont3=econtact3.getText().toString();
                sharedPreferences = getSharedPreferences(TAG,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("USER_NAME",username);
                editor.putString("USER_NUMBER1",cont1);
                editor.putString("USER_NUMBER2",cont2);
                editor.putString("USER_NUMBER3",cont3);
                editor.putString("SPEED_LIMIT",speedLimit.getText().toString());
                editor.commit();
                startActivity(new Intent(MainActivity.this,UserProfile.class));
                finish();
            }
        });

        sharedPreferences=getSharedPreferences(TAG,MODE_PRIVATE);
        username = sharedPreferences.getString("USER_NAME","No name defined");
        if(username!="No name defined"){
            Intent i = new Intent(MainActivity.this,UserProfile.class);
            i.putExtra("name",username);
            startActivity(i);
            finish();
        }
    }
}
