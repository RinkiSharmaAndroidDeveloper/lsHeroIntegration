package io.github.ashik619.comexampleandroidrinkimylibraryproject;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dilip on 3/5/19.
 */

public class DropFragment extends Fragment {
    Location mCurrentLocation;
    String mLastUpdateTime;
    private LocationManager locationManager;
    private String provider;
LocationListener locationListener;
    View view;
    private GoogleMap mMap;
    DataList dataList;
    int REQUEST_LOCATION = 1;
    MapView mMapView;
    TextView textView;
    Double lat=12.9121, lng=77.6446,lati=12.9698,longi=77.7500,lat1;
    private GoogleMap googleMap;
    Polyline polyline23;
    List<LatLng> pointsdecode = new ArrayList<LatLng>();
    int p = 0;
    List<LatLng> dashDataArray;
    Context context;
ImageView imageView;
    public DropFragment newInstance(List<LatLng> dashDataArray, Context context,DataList dataList) {
        DropFragment fragment = new DropFragment();
        Bundle args = new Bundle();
        this.dashDataArray=dashDataArray;
        this.context=context;
        this.dataList= dataList;
        //Bike Service In Progress
        if(dashDataArray!=null&&dashDataArray.size()>0&&dataList.getStatus().equals("Bike Delivered")){
            mMapView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            drawLine(dashDataArray);
            //
        }if(dataList.getStatus().equals("Bike Service In Progress")||dataList.getStatus().equals("Bike Picked For Service")||dataList.getStatus().equals("Bike Picked For Delivery")||dataList.getStatus().equals("Runner Assigned")||dataList.getStatus().equals("Appointment Created")){
            mMapView.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }if(dashDataArray==null&&dashDataArray.size()<0&& dataList.getStatus().equals("Bike Delivered")){
            mMapView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
        }
        return fragment;
    }

    public void drawLine(List<LatLng> points) {
        if (points == null) {
            Log.e("Draw Line", "got null as parameters");
            return;
        }
        int s =points.size()-1;
        double latCurrent = points.get(0).latitude;
        double lngCurrent = points.get(0).longitude;

        double latCurrent1 = points.get(s).latitude;
        double lngCurrent1 = points.get(s).longitude;
        LatLng sydney = new LatLng(latCurrent1, lngCurrent1);
       // googleMap.addMarker(new MarkerOptions().position(sydney).title("Dropped").snippet("Start Position"));                    LatLng sydney1= new LatLng(latCurrent1, lngCurrent1);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Customer Position").snippet("End Position"));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latCurrent1, lngCurrent1))
                .title("Customer Location")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.home_icon_map)));
     /*   CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(15).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        Polyline line = googleMap.addPolyline(new PolylineOptions().width(3).color(Color.BLUE));
        line.setPoints(points);*/

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.drop_fragment, container, false);

        mMapView = (MapView) view.findViewById(R.id.mapView);
        imageView = (ImageView) view.findViewById(R.id.image_view);
        textView = (TextView) view.findViewById(R.id.default_txt);
        mMapView.onCreate(savedInstanceState);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        } else {

        }
        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                googleMap.setMyLocationEnabled(false);


                LocationManager service = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                boolean enabledGPS = service
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);
                boolean enabledWiFi = service
                        .isProviderEnabled(LocationManager.NETWORK_PROVIDER);


                locationManager = (LocationManager) (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

                Criteria criteria = new Criteria();
                provider = locationManager.getBestProvider(criteria, false);
                Location location = locationManager.getLastKnownLocation(provider);

                double longitude = location.getLongitude();
                double latitude = location.getLatitude();

                LatLng latLng1 = new LatLng(latitude, longitude);

       /*         LatLng latLng1 = new LatLng(latitude, longitude);
                googleMap.addMarker(new MarkerOptions().position(latLng1).title("Runner1").snippet("Location"));*/
                if(googleMap!=null){
                    googleMap.clear();

                    googleMap.setMyLocationEnabled(false);
                    googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                }

                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(latitude, longitude))
                        .title("Runner Position")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.bike_google_map_cion)));
                CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng1).zoom(35).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
/*

                double latCurrent = dashDataArray.get(0).latitude;
                double lngCurrent = dashDataArray.get(0).longitude;

                sydney = new LatLng(latCurrent, lngCurrent);
                googleMap.addMarker(new MarkerOptions().position(sydney).title("Customer").snippet("Location"));*/
                // Define the criteria how to select the locatioin provider -> use
                // default
                locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        mCurrentLocation = location;
                        Double lat = mCurrentLocation.getLatitude();
                        Double lng = mCurrentLocation.getLongitude();
                        // LatLng latLng = new LatLng(lat, lng); //you already have this
                        //  drawLine(dashDataArray,userLatLng);
                        //  dashDataArray.add(latLng); //added

                        LatLng latLng1 = new LatLng(lat, lng);
               /*         googleMap.addMarker(new MarkerOptions().position(latLng1).title("Runner").snippet("Location"));*/
                        if(googleMap!=null){
                            googleMap.clear();

                        }
                        googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(lat, lng))
                                .title("Runner Position")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.bike_google_map_cion)));
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng1).zoom(35).build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        // redrawLine(lat, lng);
   /*  String latitude = String.valueOf(lat);
       String lngitude = String.valueOf(lng);*/
                        // Toast.makeText(context,latitude+","+lngitude,Toast.LENGTH_LONG).show();

                        // getPolyline(sydney,latitude,lngitude);
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                };


            }
        });

        return view;
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // We can now safely use the API we requested access to
/*             Location myLocation =
                     LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);*/
            } else {
                // Permission was denied or request was cancelled
            }
        }
    }


    @Override
    public void onStart() {
        if(googleMap!=null){
            googleMap.clear();
        }

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        } else {

        }
        if(locationListener!=null) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }

        super.onStart();

    }






    @Override
    public void onPause() {
        super.onPause();

        mMapView.onPause();
        if(googleMap!=null){
            googleMap.clear();
        }

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        } else {

        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

    }



    @Override
    public void onResume() {

        mMapView.onResume();
        if(googleMap!=null){
            googleMap.clear();
        }

        //isLocationEnabled();
 /*    *//*   if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }*/
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        } else {

        }
        if(locationListener!=null){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        }

        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
