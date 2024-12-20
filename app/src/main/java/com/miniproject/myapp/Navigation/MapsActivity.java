package  com.miniproject.myapp.Navigation;



import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.miniproject.myapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap gMap;
    SearchView searchView;
    String Venue;
    Button getCurrentLoc,getVenue;
    ImageButton currentlocation, zoomOut, zoomIn;
    FusedLocationProviderClient fClient;
    LatLng latLng1, latLng2, latLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
//        ImageButton back2events = (ImageButton) findViewById(R.id.back2events);
        fClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Venue = getIntent().getStringExtra("Venue");
        getVenue = findViewById(R.id.getVenue);
        getVenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVenue();
            }
        });
        getCurrentLoc =findViewById(R.id.getCurrentLoc);
        getCurrentLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentLocation();
            }
        });
//        getDirection = (Button) findViewById(R.id.getDirection);
//        getDirection.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String src = getAddress(latLng);
//                System.out.println(src);
//                String dest = getAddress(latLng1);
//                System.out.println(dest);
////                displayTrack(src,dest);
//            }
//        });
        zoomIn = (ImageButton) findViewById(R.id.zoomIn);
        zoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gMap.animateCamera(CameraUpdateFactory.zoomIn());
            }
        });
        zoomOut = (ImageButton) findViewById(R.id.zoomOut);
        zoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gMap.animateCamera(CameraUpdateFactory.zoomOut());
            }
        });
        currentlocation = (ImageButton) findViewById(R.id.currentlocation);
        currentlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation();
                } else {
                    ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                }
            }
        });


//        back2events.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), EventViewPage.class));
//                finish();
//            }
//        });
    }

//    private String getAddress(LatLng latLng) {
//        String address = "";
//        Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
//        try {
//                List<Address> addresses = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
//                if (address!=null){
//                    Address returnAddress = addresses.get(0);
//                    StringBuilder stringBuilder = new StringBuilder("");
//
//                    for (int i =0;i<=returnAddress.getMaxAddressLineIndex();i++){
//                        stringBuilder.append(returnAddress.getAddressLine(i)).append("\n");
//                    }
//                    address = stringBuilder.toString();
//                }
//                else{
//                    Toast.makeText(this, "address not found ", Toast.LENGTH_SHORT).show();
//
//                }
//        }catch(Exception e){
//            Toast.makeText(this, "cannot convet ", Toast.LENGTH_SHORT).show();
//        }
//        return address;
//    }

//    private void displayTrack(String source, String dest) {
//        try{
//            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/"+ source + "/"+ dest );
//            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
//            intent.setPackage("com.google.android.apps.maps");
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//        }catch(ActivityNotFoundException e){
//            Uri uri =Uri.parse("https://play.google.com/store/details?id=com.google.android.apps.maps");
//            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//        }
//    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
            getCurrentLocation();
        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(
                LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            fClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        latLng1 = new LatLng(location.getLatitude(), location.getLongitude());
                        gMap.addMarker(new MarkerOptions().position(latLng1).title("Your are here"));
                        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 18), 3000, null);
//                        tvLatitude.setText(String.valueOf(location.getLatitude()));
//                        tvlongitude.setText(String.valueOf(location.getLongitude()));
                    } else {
                        LocationRequest locationRequest = new LocationRequest()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(10000)
                                .setFastestInterval(1000)
                                .setNumUpdates(1);

                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                super.onLocationResult(locationResult);
                                Location location1 = locationResult.getLastLocation();
                                latLng2 = new LatLng(location1.getLatitude(), location1.getLongitude());
                                gMap.addMarker(new MarkerOptions().position(latLng2).title("Your are here"));

                                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng2, 18), 3000, null);

                            }
                        };
                        fClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                    }
                }
            });

        } else {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMapType(3);

        gMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.custommaps));
        getVenue();
        Toast.makeText(this, "tap on the marker to get directions", Toast.LENGTH_LONG).show();

    }


    private void getVenue() {
        Geocoder geocoder = new Geocoder(this);
        List<Address> addressList;
        try {
            addressList = geocoder.getFromLocationName(Venue.toString(), 1);
            if (addressList != null) {

                double latitude = addressList.get(0).getLatitude();
                double longitude = addressList.get(0).getLongitude();
                System.out.println("addresslst   :" + addressList);

                System.out.println("latitude:" + latitude + " " + "Longitude" + longitude);

                latLng = new LatLng(latitude, longitude);
                System.out.println("latlang" + latLng);
                gMap.addMarker(new MarkerOptions().position(latLng).title(Venue));

                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18), 3000, null);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


