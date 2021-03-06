package com.apps.awesome.smartpipican;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, Listener {

    public static final String TAG = MapsActivity.class.getSimpleName();
    public static final int DEFAULT_ZOOM = 15;

    private final int LOCATION_REQUEST_CODE = 1;
    private final int NFC_REQUEST_CODE = 2;

    private ArrayList<String> selectedDogs;
    private NFCReadFragment mNfcReadFragment;
    private boolean isDialogDisplayed = false;
    private NfcAdapter mNfcAdapter;
    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        boolean location_permission = askPermission(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_REQUEST_CODE);
        askPermission(Manifest.permission.NFC, NFC_REQUEST_CODE);

        if (location_permission && hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            initMap();
            initNFC();
            createDogList();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu_main; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_scan) {
            MapsActivity.this.showReadFragment();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initNFC() {
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
    }

    private void showReadFragment() {
        mNfcReadFragment = (NFCReadFragment) getFragmentManager().findFragmentByTag(NFCReadFragment.TAG);
        if (mNfcReadFragment == null) {
            mNfcReadFragment = NFCReadFragment.newInstance(selectedDogs);
        }
        mNfcReadFragment.show(getFragmentManager(), NFCReadFragment.TAG);
    }

    @Override
    public void onDialogDisplayed() {
        isDialogDisplayed = true;
    }

    @Override
    public void onDialogDismissed() {
        isDialogDisplayed = false;
    }

    private void createDogList() {
        selectedDogs = new ArrayList<>();
        LinearLayout dogLayout = (LinearLayout) findViewById(R.id.dog_list);
        LinearLayout rexLayout = createDogLayout(DogSeeder.getRex());
        dogLayout.addView(rexLayout);
    }

    @NonNull
    private LinearLayout createDogLayout(final Dog dog) {
        ImageView dogImage = new ImageView(this);
        dogImage.setImageResource(dog.getDrawable());
        dogImage.setAdjustViewBounds(true);
        dogImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message;
                if (selectedDogs.contains(dog.getName())) {
                    message = "You've unselected ";
                    v.setBackground(null);
                    selectedDogs.remove(dog.getName());
                } else {
                    message = "You've succesfully selected ";
                    v.setBackground(getBaseContext().getDrawable(R.drawable.border));
                    selectedDogs.add(dog.getName());
                }
                Toast toast = Toast.makeText(getApplicationContext(),
                        message.concat(dog.getName()),
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        dogImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(getBaseContext(), DogProfileActivity.class);
                ViewGroup parent = (ViewGroup) v.getParent();
                for (int itemPos = 0; itemPos < parent.getChildCount(); itemPos++) {
                    View view = parent.getChildAt(itemPos);
                    if (view instanceof TextView) {
                        TextView nameView = (TextView) view; //Found it!
                        intent.putExtra("DOG_NAME", nameView.getText());
                        break;
                    }
                }
                startActivity(intent);
                return true;
            }
        });
        TextView dogName = new TextView(this);
        dogName.setText(dog.getName());
        dogName.setGravity(Gravity.CENTER);
        LinearLayout dogLayout = new LinearLayout(this);
        dogLayout.setOrientation(LinearLayout.VERTICAL);
        dogLayout.addView(dogName);
        dogLayout.addView(dogImage);
        dogLayout.setPadding(10, 10, 10, 10);
        return dogLayout;
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

    @Override
    protected void onNewIntent(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        Log.d(TAG, "onNewIntent: " + intent.getAction());

        if (tag != null) {
            Toast.makeText(this, getString(R.string.message_tag_detected), Toast.LENGTH_SHORT).show();
            Ndef ndef = Ndef.get(tag);

            if (isDialogDisplayed) {

                mNfcReadFragment = (NFCReadFragment) getFragmentManager().findFragmentByTag(NFCReadFragment.TAG);
                try {
                    mNfcReadFragment.onNfcDetected(ndef);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        IntentFilter techDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        IntentFilter[] nfcIntentFilter = new IntentFilter[]{techDetected, tagDetected, ndefDetected};

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        if (mNfcAdapter != null)
            mNfcAdapter.enableForegroundDispatch(this, pendingIntent, nfcIntentFilter, null);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mNfcAdapter != null)
            mNfcAdapter.disableForegroundDispatch(this);
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private boolean askPermission(String permission, int request_code) {
        if (!hasPermission(permission)) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, request_code);
            return false;
        } else return true;
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

        requestPipicans();
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

    private void requestPipicans() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://api.thingtia.cloud/data/pipicans/";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.v(TAG, "Response is: " + response);

                        try {
                            List<Pipican> pipicanList = new ArrayList<>();

                            JSONObject resp = new JSONObject(response);
                            JSONArray jsensors = resp.getJSONArray("sensors");
                            if (jsensors != null) {
                                int len = jsensors.length();
                                for (int i = 0; i < len; i++) {
                                    JSONObject jpipican = jsensors.getJSONObject(i);
                                    String pipicanName = jpipican.getString("sensor");
                                    Pipican pipican = PipicanProfileActivity.obtainPipicanFromName(pipicanName);

                                    JSONArray jArr = jpipican.getJSONArray("observations");
                                    JSONObject jObj = jArr.getJSONObject(0);
                                    String val = jObj.getString("value");
                                    String loc = jObj.getString("location");
                                    LatLng latLng = new LatLng(Double.valueOf(loc.split(",")[0]), Double.valueOf(loc.split(",")[1]));
                                    pipican.setLatLng(latLng);
                                    pipicanList.add(pipican);
                                    JSONObject jVal = new JSONObject(val);
                                    JSONArray jDogs = jVal.getJSONArray("dogs");
                                    List<Dog> doglist = new ArrayList<>();
                                    if (jDogs != null) {
                                        int len2 = jDogs.length();
                                        for (int i2 = 0; i2 < len2; i2++) {
                                            String dogName = jDogs.get(i2).toString();
                                            doglist.add(DogProfileActivity.obtainDogFromName(dogName));
                                        }
                                    }
                                    pipican.setDogs(new HashSet<>(doglist));

                                }
                                DogCompatibility dogCompatibility = new DogCompatibility();
                                for (Pipican pipican : pipicanList) {
                                    boolean compatible = true;
                                    for (Dog d : pipican.getDogs()) {
                                        if (d.getName().equals("Kira")) d = DogSeeder.getKira();
                                        if (d.getName().equals("Angus")) d = DogSeeder.getAngus();
                                        if (d.getName().equals("Rex")) d = DogSeeder.getRex();
                                        compatible &= dogCompatibility.isCompatible(DogSeeder.getRex(), d);
                                    }
                                    BitmapDescriptor compatibleMarker;
                                    if (compatible)
                                        compatibleMarker = BitmapDescriptorFactory.fromResource(R.drawable.marker);
                                    else
                                        compatibleMarker = BitmapDescriptorFactory.fromResource(R.drawable.marker_orange);
                                    mMap.addMarker(new MarkerOptions().position(pipican.getLatLng()).title(pipican.getTitle())
                                            .icon(compatibleMarker));
                                }
                            }
                            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                @Override
                                public void onInfoWindowClick(Marker marker) {
                                    Intent intent = new Intent(getBaseContext(), PipicanProfileActivity.class);
                                    intent.putExtra("PIPICAN_NAME", marker.getTitle());
                                    startActivity(intent);
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v(TAG, "Error is: " + error.getMessage());
            }
        })

        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("IDENTITY_KEY", "b0fbc84c2ecd88589a7cc11f999bb3cafad521c79b79abffb3ab1b480a56d6ef");
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private Location getCurrentLocation() throws SecurityException {
        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        return locationManager.getLastKnownLocation(locationManager
                .getBestProvider(criteria, false));
    }
}
