package com.project.ebus_drive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    private EditText mBusNoField;
    private EditText mDriverName;
    private Button mSendData;
    private DatabaseReference mDatabase;
    private FusedLocationProviderClient fusedLocationClient;
    private double mlatitude = 0.0, mlongitude = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            mlatitude=location.getLatitude();
                            mlongitude=location.getLongitude();

                            // Logic to handle location object
                        }
                    }
                });
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mSendData = findViewById(R.id.sendLoc);
        mBusNoField = findViewById(R.id.busno);
        mDriverName = findViewById(R.id.drivername);
        mSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = mBusNoField.getText().toString();
                String value1 = mDriverName.getText().toString();
                DatabaseReference mRef = mDatabase.child("Users");
                DatabaseReference mBusid = mRef.child(value);
                mBusid.child("Name").setValue(value1);
                mBusid.child("Latitude").setValue(mlatitude);
                mBusid.child("Longitude").setValue(mlongitude);

                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });

    }


}
