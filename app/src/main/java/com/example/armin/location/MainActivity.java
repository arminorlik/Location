package com.example.armin.location;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button butStart = (Button) findViewById(R.id.butStart);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123 );
        butStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GPStracker gpStracker = new GPStracker(getApplicationContext());
                Location location = gpStracker.getlocation();
                if( location != null){
                    Double lat = location.getLatitude();
                    Double lon = location.getLongitude();
                    //Toast.makeText(getApplicationContext(), "LAT: " + lat + " \n LON: " + lon, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    intent.putExtra("dlugosc", lon.toString());
                    intent.putExtra("szerokosc", lat.toString());
                    startActivity(intent);


                }
            }
        });


    }
}
