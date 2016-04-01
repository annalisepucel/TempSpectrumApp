package com.vidyo.vidyosample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class WhoRU extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_who_ru);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Functionality for "Login as Administrator" button
        //Button staffBtn = (Button) findViewById(R.id.adminlogin);

        Button CallPatient = (Button) findViewById(R.id.adminlogin);

        // When the patient plan button is clicked, change the activity to the patient plan class
        CallPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveToPatientCarePlan = new Intent(WhoRU.this, VidyoSampleActivity.class);
                startActivity(moveToPatientCarePlan);
            }
        });


        //Functionality for "Login as Patient" button
        Button patientBtn = (Button) findViewById(R.id.patientlogin);
        patientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveToPatientLogin = new Intent(WhoRU.this, LoginActivity.class);

                startActivity(moveToPatientLogin);

            }
        });
    }

}
