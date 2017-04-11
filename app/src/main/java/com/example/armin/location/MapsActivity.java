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

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private String dlugosc;
    private String szerokosc;
    private LatLng koord, StartP, StopP;
    private Double lat;
    private Double lon;
    private Double StartLat, StartLon, StopLat, StopLon;
    private Date czasS;

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
        StartP = new LatLng(Double.parseDouble(szerokosc), Double.parseDouble(dlugosc));
        czasS = new Date();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        addMarker(StartP);
    }

    private void addMarker(LatLng pozycja) {
        mMap.addMarker(new MarkerOptions().position(pozycja));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(pozycja)
                .zoom(10).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.butStop:
                GPStracker gpStracker = new GPStracker(getApplicationContext());
                Location location = gpStracker.getlocation();
                if (location != null) {
                    //StopP = new LatLng(location.getLatitude(), location.getLongitude());
                    StopP = new LatLng(51.301146, 19.383130);
                }
                addMarker(StopP);
                ObliczanieOdl(StartP, StopP);
                break;

            case R.id.butKoniec:
               int ss,mm,hh;

                long czasF = (new Date().getTime() - czasS.getTime());

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(czasF);

                TimeZone UTC = TimeZone.getTimeZone("UTC");

                Date date = new Date(czasF);
                DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                formatter.setTimeZone(UTC);
                String dateFormatted = formatter.format(date);


                Toast.makeText(this, dateFormatted, Toast.LENGTH_SHORT).show();


               break;
        }
    }

    public double ObliczanieOdl(LatLng StartP, LatLng StopP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = StopP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = StopP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Toast.makeText(getApplicationContext(), "Radius Value "  + valueResult + "   KM  " + kmInDec
                + " Meter   " + meter, Toast.LENGTH_LONG).show();

        return Radius * c;
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

