package io.github.ashik619.comexampleandroidrinkimylibraryproject;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by dilip on 3/5/19.
 */

public class PickupFragment extends Fragment {

    private static final String TAG = "LocationActivity";
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;


    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    String mLastUpdateTime;
    private LocationManager locationManager;
    private String provider;
    View view;
    private GoogleMap mMap;
    int REQUEST_LOCATION = 1;
    MapView mMapView;
    ImageView imageView;
    TextView textView;
    DataList dataList;
    Double lat = 12.9121, lng = 77.6446, lati = 12.9698, longi = 77.7500, lat1;
    private GoogleMap googleMap;
    Polyline polyline23;
    Polyline line;
    LatLng sydney;
    Polyline myPolyline;
    List<LatLng> pointsdecode = new ArrayList<LatLng>();
    int p = 0;
    LocationListener locationListener;

    List<LatLng> dashDataArray;
    LatLng userLatLng;
    Context context;

    protected void createLocationRequest() {
        /*mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);*/
    }

    public PickupFragment newInstance(List<LatLng> dashDataArray, Context context, DataList dataList) {
        PickupFragment fragment = new PickupFragment();
        Bundle args = new Bundle();
        this.dashDataArray = dashDataArray;
        this.context = context;
        this.dataList = dataList;
        if (dashDataArray != null && dashDataArray.size() < 0 && dataList.getStatus().equals("Runner Assigned") || dataList.getStatus().equals("Appointment Created")) {
            mMapView.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        } else if (dashDataArray != null && dashDataArray.size() > 0 && dataList.getStatus().equals("Bike Picked For Service") || dataList.getStatus().equals("Bike Picked For Delivery") || dataList.getStatus().equals("Bike Delivered") || dataList.getStatus().equals("Bike Service In Progress")) {
            mMapView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            userLatLng = getLocationFromAddress(context, dataList.getLocality());


          drawLine(dashDataArray, userLatLng);
        } else if (dashDataArray != null && dashDataArray.size() < 0 && dataList.getStatus().equals("Bike Picked For Service") || dataList.getStatus().equals("Bike Service In Progress") || dataList.getStatus().equals("Bike Picked For Delivery") || dataList.getStatus().equals("Bike Delivered")) {
            mMapView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);

        }

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.pickup_fragment, container, false);
       /* SupportMapFragment mapFragment = (SupportMapFragment) view.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);*/

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
            // permission has been granted, continue as usual
            /*Location myLocation =
                    LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);*/
        }
        mMapView.onResume(); // needed to get the map to display immediately

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

                // Initialize the location fields
                if (location != null) {
                    //  onLocationChanged(location);
                } else {

                    //do something
                }
                // For dropping a marker at a point on the Map
        /*        LatLng sydney = new LatLng(lati, longi);
                googleMap.addMarker(new MarkerOptions().position(sydney).title("Start Pickup").snippet("Whitefield"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
*/




/*







        //polyLine

        googleMap.setOnMarkerClickListener(
                new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {

                        */
/*  googleMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(lati, longi))
                                    .title("Start Pickup")
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_radius)));*//*

                        //    googleMap.getUiSettings().setMapToolbarEnabled(true);*//*

                            String link6 = "https://maps.googleapis.com/maps/api/directions/json?origin=" + lat + "," + lng + "&destination=" + lati + "," + longi + "&mode=car&key=AIzaSyAyjEwKCLpCMA2CFFy0JDn2D9YP6d6kK64";
                            Log.e("Link 67", link6);
                            RequestQueue queue = Volley.newRequestQueue(getContext());
                            final JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, link6, null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.e("Json Object", "response:=" + response);
                                    try {
                                        String routes = response.getString("routes");

                                        JSONArray jsonArray = new JSONArray(routes);
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                                            String bounds = jsonObject.getString("bounds");
                                            String legspoint = jsonObject.getString("legs");
                                            JSONArray legsArr = new JSONArray(legspoint);
                                            for (int j = 0; j < legsArr.length(); j++) {

                                                JSONObject legsobj = legsArr.getJSONObject(j);
                                                String distanceObj = legsobj.getString("distance");
                                                JSONObject jstextDistance = new JSONObject(distanceObj);
                                                String distancefinal = jstextDistance.getString("text");
                                                Log.e("Distance Meter", distancefinal);
                                                //   nametext.setText("TAKE ME HERE "+distancefinal);
                                            }

                                            String overviewpoints = jsonObject.getString("overview_polyline");
                                            Log.e("Overviewpoints", overviewpoints);
                                            JSONObject jsonObjectpoints = new JSONObject(overviewpoints);
                                            String points = jsonObjectpoints.getString("points");
                                            Log.e("points", points);
                                            pointsdecode = decodePolyLine(points);


                                            JSONObject jsonObject1 = new JSONObject(bounds);
                                            String northeast = jsonObject1.getString("northeast");
                                            JSONObject jsonObject2 = new JSONObject(northeast);
                                            lat1 = jsonObject2.getDouble("lat");
                                            double long1 = jsonObject2.getDouble("lng");
                                            LatLng sydney2 = new LatLng(lat1, long1);


                                            String southwest = jsonObject1.getString("southwest");
                                            JSONObject jsonObject3 = new JSONObject(southwest);

                                        }

                                        Log.e("Pointdecode", pointsdecode.toString() + pointsdecode.size());
                                        PolylineOptions polylineOptions = new PolylineOptions().
                                                geodesic(true).
                                                color(Color.BLUE).
                                                width(10);
                                        for (; p < pointsdecode.size(); p++) {
//
         //
//
//
                                            PolylineOptions polylineOptions2 = polylineOptions.add(pointsdecode.get(p));
                                            polyline23 = googleMap.addPolyline(polylineOptions);
//
                                        }
                                        LatLng sydney = new LatLng(lat, lng);
                                        googleMap.addMarker(new MarkerOptions().position(sydney).title("Drop Pickup").snippet("HSR Layout"));
//googleMap.addMarker(new MarkerOptions().position(pointsdecode.get(p)).title("Drop Pickup"));
//                    polylineOptions.visible(false);
//                    Polyline line = mMap.addPolyline(new PolylineOptions()
//                            .add(new LatLng(location.getLatitude(), location.getLongitude()),
//                                    new LatLng(this.destinationLatitude, this.destinationLongitude))
//                            .width(1)
//                            .color(Color.DKGRAY)
                                        polyline23.remove();

                                        Log.e("Pointdecode Clear", pointsdecode.toString() + pointsdecode.size());


                                        googleMap.addPolyline(polylineOptions);

                                        Log.e("routes", routes);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


// TODO Auto-generated method stub

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
// TODO Auto-generated method stub
                                }
                            });
                            queue.add(jsObjRequest);


                        return false;
                    }

                });
            }

*/
            }
        });
        return view;
    }

    /*   @Override
       public void onActivityCreated(@Nullable Bundle savedInstanceState) {
           super.onActivityCreated(savedInstanceState);

           // Obtain the SupportMapFragment and get notified when the map is ready to be used.
           if(getActivity()!=null) {
               SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()
                       .findFragmentById(R.id.map);
               if (mapFragment != null) {
                   mapFragment.getMapAsync(this);
               }
           }
       }*/


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
    /*    if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
            Log.d(TAG, "Location update resumed .....................");
        }*/
    }


   /* @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Firing onLocationChanged..............................................");
        mCurrentLocation = location;

        double latCurrent1 = mCurrentLocation.getLatitude();
        double lngCurrent1 = mCurrentLocation.getLongitude();
        LatLng latLng1 = new LatLng(latCurrent1, lngCurrent1);
        googleMap.addMarker(new MarkerOptions().position(latLng1).title("Runner").snippet("Location"));
        //  mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
     *//*   updateUI();*//*
    }
*/



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



    public void getPolyline(LatLng latLng,String latit1,String  longit1) {
     //   getAddressLatLng(latLng);
        String link6 = "https://maps.googleapis.com/maps/api/directions/json?origin=" +latit1+ "," +longit1+ "&destination=" + latLng.latitude + "," + latLng.longitude + "&mode=car&key=AIzaSyAyjEwKCLpCMA2CFFy0JDn2D9YP6d6kK64";
        Log.e("Link 67", link6);


        RequestQueue queue = Volley.newRequestQueue(getActivity());
        final JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, link6, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Json Object", "response:=" + response);
                try {
                    String routes = response.getString("routes");

                    JSONArray jsonArray = new JSONArray(routes);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String bounds = jsonObject.getString("bounds");
                        String legspoint = jsonObject.getString("legs");
                        JSONArray legsArr = new JSONArray(legspoint);
                        for (int j = 0; j < legsArr.length(); j++) {

                            JSONObject legsobj = legsArr.getJSONObject(j);
                            String distanceObj = legsobj.getString("distance");
                            JSONObject jstextDistance = new JSONObject(distanceObj);
                           String distancefinal= jstextDistance.getString("text");
                            Log.e("Distance Meter", distancefinal);
                            //  textDistance.setText(distancefinal);
                        }
                    //    nametext.setText("Distance: "+distancefinal);

                        String overviewpoints = jsonObject.getString("overview_polyline");
                        Log.e("Overviewpoints", overviewpoints);
                        JSONObject jsonObjectpoints = new JSONObject(overviewpoints);
                        String points = jsonObjectpoints.getString("points");
                        Log.e("points", points);
                        pointsdecode = decodePolyLine(points);
                        pointsdecode = decodePolyLine(points);


                        JSONObject jsonObject1 = new JSONObject(bounds);
                        String northeast = jsonObject1.getString("northeast");
                        JSONObject jsonObject2 = new JSONObject(northeast);
                        lat1 = jsonObject2.getDouble("lat");
                        double long1 = jsonObject2.getDouble("lng");
                        LatLng sydney2 = new LatLng(lat1, long1);


                        String southwest = jsonObject1.getString("southwest");
                        JSONObject jsonObject3 = new JSONObject(southwest);

                    }


                    Log.e("Pointdecode", pointsdecode.toString() + pointsdecode.size());
                    PolylineOptions polylineOptions = new PolylineOptions().
                            geodesic(true).
                            color(Color.BLUE).
                            width(10);
                    for (int j = 0; j < pointsdecode.size(); j++) {
//
////                        mMap.addMarker(new MarkerOptions().position(pointsdecode.get(j)).title("Place B"));
//
//
                        PolylineOptions polylineOptions2 = polylineOptions.add(pointsdecode.get(j));
                        polyline23 = googleMap.addPolyline(polylineOptions);
//
                    }
//
//                    polylineOptions.visible(false);
//                    Polyline line = mMap.addPolyline(new PolylineOptions()
//                            .add(new LatLng(location.getLatitude(), location.getLongitude()),
//                                    new LatLng(this.destinationLatitude, this.destinationLongitude))
//                            .width(1)
//                            .color(Color.DKGRAY)
                    polyline23.remove();

                    Log.e("Pointdecode Clear", pointsdecode.toString() + pointsdecode.size());


                    googleMap.addPolyline(polylineOptions);

                    Log.e("routes", routes);
                } catch (Exception e) {
                    e.printStackTrace();
                }


// TODO Auto-generated method stub

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
// TODO Auto-generated method stub
            }
        });
        queue.add(jsObjRequest);
  //      getAddressLatLng(latLng);


    }

 /*   public void getAddressLatLng(final LatLng latLng) {
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                String placeName = strReturnedAddress.toString();

                markerAddress.setText(placeName);

                saveButton.setVisibility(View.VISIBLE);
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("Lat", String.valueOf(lati));
                        editor.putString("Lng", String.valueOf(longi));
                        editor.commit();
                        Toast.makeText(getActivity().getApplicationContext(), "Your location has been saved", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/


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

            public void drawLine(List<LatLng> points,LatLng userLatLng) {
                if (points == null) {
                    Log.e("Draw Line", "got null as parameters");
                    return;
                }
                int s = points.size()-1;
                double latCurrent = points.get(0).latitude;
                double lngCurrent = points.get(0).longitude;

                double latCurrent1 = points.get(s).latitude;
                double lngCurrent1 = points.get(s).longitude;
                 sydney = new LatLng(latCurrent, lngCurrent);
                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(latCurrent, lngCurrent))
                        .title("Customer Location")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.home_icon_map)));
             //   googleMap.addMarker(new MarkerOptions().position(sydney).title("Customer").snippet("Location"));          /*          LatLng sydney1= new LatLng(latCurrent1, lngCurrent1);
               // googleMap.addMarker(new MarkerOptions().position(sydney1).title("Runner").snippet("Current Position"));
          /*      CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(15).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));*/
              /* line = googleMap.addPolyline(new PolylineOptions().width(3).color(Color.BLUE));
                line.setPoints(points);
              myPolyline = googleMap.addPolyline(new PolylineOptions().addAll(points))*/;
            }

    private List<LatLng> decodePolyLine(final String poly) {

        int len = poly.length();
        int index = 0;
        List<LatLng> decoded = new ArrayList<LatLng>();
        int lat = 0;
        int lng = 0;

        while (index < len) {
            int b;
            int shift = 0;
            int result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            decoded.add(new LatLng(
                    lat / 100000d, lng / 100000d
            ));
        }

        return decoded;
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


    public LatLng getLocationFromAddress(Context context,String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }
/*
  @Override
    public void onLocationChanged(Location location) {

      mCurrentLocation = location;
        Double lat = mCurrentLocation.getLatitude();
        Double lng = mCurrentLocation.getLongitude();
       // LatLng latLng = new LatLng(lat, lng); //you already have this
      //  drawLine(dashDataArray,userLatLng);
      //  dashDataArray.add(latLng); //added

      LatLng latLng1 = new LatLng(lat, lng);
      googleMap.addMarker(new MarkerOptions().position(latLng1).title("Runner1").snippet("Location"));
       redrawLine(lat,lng);
   *//*  String latitude = String.valueOf(lat);
       String lngitude = String.valueOf(lng);*//*
  // Toast.makeText(context,latitude+","+lngitude,Toast.LENGTH_LONG).show();

       // getPolyline(sydney,latitude,lngitude);
    }*/

    private void redrawLine(Double lat,Double lngi){

       // googleMap.clear();  //clears all Markers and Polylines
        if(dashDataArray!=null &&dashDataArray.size()>0){
            int s = dashDataArray.size()-1;
            PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
            for (int i = s; i>0; i--) {
                LatLng point = dashDataArray.get(i);
                options.add(point);
            }

            if(myPolyline != null) {
                myPolyline.remove();
            }
            googleMap.clear();

            googleMap.clear();
            LatLng runnerCurrent = new LatLng(lat, lngi);
            googleMap.addMarker(new MarkerOptions().position(runnerCurrent).title("Runner").snippet("Current location"));
            CameraPosition cameraPosition = new CameraPosition.Builder().target(runnerCurrent).zoom(15).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            line = googleMap.addPolyline(new PolylineOptions().width(3).color(Color.BLUE));

            //add Marker in current position
            line = googleMap.addPolyline(options);
        }
      ; //add Polyline
    }





}




