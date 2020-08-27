package com.example.lattice;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback , GoogleMap.OnMapClickListener , View.OnClickListener {

    private GoogleMap mMap;
    private static int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1 ;
    boolean locationPermissionGranted;
    int DEFAULT_ZOOM = 17;
    private String TAG = "map";
    private LatLng defaultLocation = new LatLng(34.765 , 74.2546);
    FusedLocationProviderClient fusedLocationProviderClient;
    private Location lastKnownLocation; // Variable to store the last known location
    Marker currLocationMarker; // Current Location Marker to point where are you


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        ImageView aim = (ImageView) findViewById(R.id.aim);
        ImageView done = (ImageView) findViewById(R.id.done);
        aim.setOnClickListener(this);
        done.setOnClickListener(this);


        //Check the loaction permission
        checkLocaionPermission();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapClickListener(this);
        //Getting the current location of the device
        getDeviceLocation();
    }

    
    private void checkLocaionPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else{
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }
    
    
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        }
        getDeviceLocation();
    }


    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                if(currLocationMarker != null)
                    currLocationMarker.remove();
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLocation.getLatitude(),
                                                lastKnownLocation.getLongitude()), DEFAULT_ZOOM));

                                BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.marker);
                                MarkerOptions markerOptions = new MarkerOptions();
                                markerOptions.position(new LatLng(lastKnownLocation.getLatitude(),
                                        lastKnownLocation.getLongitude()));
                                markerOptions.title("YOU");
                                markerOptions.icon(icon);
                                currLocationMarker = mMap.addMarker(markerOptions);
                            }
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }


    @Override
    public void onMapClick(LatLng latLng) {
        if(currLocationMarker != null)
            currLocationMarker.remove();

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom( latLng , 17));
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.marker);
        MarkerOptions options = new MarkerOptions();
        options.position( latLng);
        options.title("YOU");
        options.icon(icon);
        currLocationMarker = mMap.addMarker(options);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.aim :
                getDeviceLocation();
                break;

            case R.id.done:
                Toast.makeText(this, "Your Location is recorded", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}