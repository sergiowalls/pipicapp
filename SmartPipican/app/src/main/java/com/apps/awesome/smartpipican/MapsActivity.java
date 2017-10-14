package com.apps.awesome.smartpipican;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashSet;
import java.util.Set;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static final int DEFAULT_ZOOM = 15;
    private GoogleMap mMap;
    private final int LOCATION_REQUEST_CODE = 1;
    private final int NFC_REQUEST_CODE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        askPermission(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_REQUEST_CODE);
        askPermission(Manifest.permission.NFC, NFC_REQUEST_CODE);

        if (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) initMap();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initMap();
                } else {
                    finish();
                }
            }
        }
    }

    private void initMap() {
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void askPermission(String permission, int request_code) {
        if (!hasPermission(permission)) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, request_code);
        }
    }

    private boolean hasPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) throws SecurityException {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);

        mMap.moveCamera(getCameraUpdate(getCurrentLocation(), DEFAULT_ZOOM));

        Set<Pipican> pipicans = getPipicans();
        for (Pipican pipican : pipicans)
            mMap.addMarker(new MarkerOptions().position(pipican.getLatLng()).title(pipican.getTitle())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

    }

    @NonNull
    private CameraUpdate getCameraUpdate(Location location, float zoom) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        return CameraUpdateFactory.newLatLngZoom(latLng, zoom);
    }

    private Set<Pipican> getPipicans() {
        Set<Pipican> pipicans = new HashSet<>();
        pipicans.add(new Pipican(new LatLng(10, 10), "Pipican 1"));
        pipicans.add(new Pipican(new LatLng(20, 10), "Pipican 2"));
        pipicans.add(new Pipican(new LatLng(30, 10), "Pipican 3"));
        return pipicans;
    }

    private Location getCurrentLocation() throws SecurityException {
        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        return locationManager.getLastKnownLocation(locationManager
                .getBestProvider(criteria, false));
    }
}
