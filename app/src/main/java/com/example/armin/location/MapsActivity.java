package com.example.armin.location;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private String dlugosc;
    private String szerokosc;
    private LatLng koord;
    private Double lat;
    private Double lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Button butStart = (Button) findViewById(R.id.butStop);
        Button butKoniec = (Button) findViewById(R.id.butKoniec);
        butStart.setOnClickListener(this);
        butKoniec.setOnClickListener(this);
        mapFragment.getMapAsync(this);
        ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        Intent intent = getIntent();
        dlugosc = intent.getStringExtra("dlugosc");
        szerokosc = intent.getStringExtra("szerokosc");

        koord = new LatLng(Double.parseDouble(szerokosc), Double.parseDouble(dlugosc));

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        addMarker();

    }

    private void addMarker() {
        mMap.addMarker(new MarkerOptions().position(koord));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(koord)
                .zoom(10).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.butStop:
                GPStracker gpStracker = new GPStracker(getApplicationContext());
                Location location = gpStracker.getlocation();
                if( location != null) {
                    lat = location.getLatitude();
                    lon = location.getLongitude();
                }

                koord = new LatLng(lat, lon);
                addMarker();
                break;

            case R.id.butKoniec:
                Toast.makeText(this, "koniec", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}



/*
// Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

// Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                //makeUseOfNewLocation(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

// Register the listener with the Location Manager to receive location updates
       *//* if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        String LocationProvider = LocationManager.GPS_PROVIDER;
        Location LastKnowLocation = locationManager.getLastKnownLocation(LocationProvider);


        LatLng koord = new LatLng((int) (LastKnowLocation.getLatitude()), (int) LastKnowLocation.getLongitude());*//*



    }*/

