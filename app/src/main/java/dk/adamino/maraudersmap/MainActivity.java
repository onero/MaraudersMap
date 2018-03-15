package dk.adamino.maraudersmap;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private final LatLng LOCATION_SEATTLE = new LatLng(47.5843604,-122.1504054);

    private LatLng mUserLocation;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    @SuppressLint("MissingPermission")
    public void onClick_City(View v) {
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                            mUserLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(mUserLocation, 16);
                            mMap.animateCamera(update);
                            mMap.addMarker(new MarkerOptions().position(mUserLocation).title("Mischief Managed!"));

                        }
                    }
                });
    }
    public void onClick_Seattle(View v) {
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_SEATTLE, 16);
        mMap.animateCamera(update);
        mMap.addMarker(new MarkerOptions().position(LOCATION_SEATTLE).title("#SleeplessInSeattle"));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}
