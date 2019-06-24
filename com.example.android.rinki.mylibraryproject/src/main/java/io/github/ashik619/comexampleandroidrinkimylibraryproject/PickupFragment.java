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
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by dilip on 3/5/19.
 */

public class PickupFragment extends Fragment {

    private final int FIVE_SECONDS = 5000;


    List<LatLng> pickupOnlyDataList;

    View view;
    int REQUEST_LOCATION = 1;
    MapView mMapView;
    ImageView imageView;
    TextView textView;
    DataList dataList;

    private GoogleMap googleMap;

    Polyline line;
    LatLng sydney;
    Polyline myPolyline;
    Handler handler;
    List<LatLng> pointsdecode = new ArrayList<LatLng>();


    List<LatLng> dashDataArray;
    LatLng userLatLng;
    Context context;
ProgressBar progressBar;
    String runnerLatitude=null;
    String runnerLongitude=null;
    boolean isRunnerRuning =false;

 /*   public PickupFragment newInstance(Context context, final DataList dataList) {
        PickupFragment fragment = new PickupFragment();
        Bundle args = new Bundle();
        this.context = context;
        this.dataList = dataList;
        if (dataList.getStatus().equals("Appointment Created")) {
            mMapView.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }if (dataList.getStatus().equals("Runner Assigned")) {
            mMapView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            userLatLng = getLocationFromAddress(context, dataList.getPickAddress());

            if(userLatLng!=null)
            {
                handler.postDelayed(new Runnable() {
                    public void run() {
                        getRunnerLocation(dataList,userLatLng,"1");
                        // this method will contain your almost-finished HTTP calls
                        handler.postDelayed(this, FIVE_SECONDS);
                    }
                }, FIVE_SECONDS);

            }

        } else if (dataList.getStatus().equals("Bike Picked For Service")) {
            mMapView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            userLatLng = getLocationFromAddress(context, dataList.getPickAddress());

        if(userLatLng!=null){
            getPolyline(dataList.getServiceCenterLat(),dataList.getServiceCenterLng(),userLatLng,"2");
            getRunnerLocation(dataList,userLatLng,"2");
        }
        } else if (dataList.getStatus().equals("Bike Picked For Delivery") || dataList.getStatus().equals("Bike Delivered") || dataList.getStatus().equals("Bike Service In Progress")) {
            mMapView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            userLatLng = getLocationFromAddress(context, dataList.getPickAddress());

        if(userLatLng!=null){
            getPolyline(dataList.getServiceCenterLat(),dataList.getServiceCenterLng(),userLatLng,"2");
        }
        }
        return fragment;
    }*/


public void setFragmentValues(Context context, final DataList dataList){

    if (dataList.getStatus().equals("Appointment Created")) {
        mMapView.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);
    }if (dataList.getStatus().equals("Runner Assigned")) {
        mMapView.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
        userLatLng = getLocationFromAddress(context, dataList.getPickAddress());
        isRunnerRuning =true;
        if(userLatLng!=null)
        {
            handler.postDelayed(new Runnable() {
                public void run() {
                    getRunnerLocation(dataList,userLatLng,"1");
                    // this method will contain your almost-finished HTTP calls
                    handler.postDelayed(this, FIVE_SECONDS);
                }
            }, FIVE_SECONDS);

        }

    } else if (dataList.getStatus().equals("Bike Picked For Service")) {
        mMapView.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
        userLatLng = getLocationFromAddress(context, dataList.getPickAddress());

        if(userLatLng!=null){
            getPolyline(dataList.getServiceCenterLat(),dataList.getServiceCenterLng(),userLatLng,"2");
            getRunnerLocation(dataList,userLatLng,"2");
        }
    } else if (dataList.getStatus().equals("Bike Picked For Delivery") || dataList.getStatus().equals("Bike Delivered") || dataList.getStatus().equals("Bike Service In Progress")) {
        mMapView.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
        userLatLng = getLocationFromAddress(context, dataList.getPickAddress());

        if(userLatLng!=null){
            getPolyline(dataList.getServiceCenterLat(),dataList.getServiceCenterLng(),userLatLng,"2");
        }
    }
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
        progressBar = (ProgressBar) view.findViewById(R.id.progress);

        mMapView.onCreate(savedInstanceState);
        dataList = getArguments().getParcelable("dataList");
        handler=new Handler();
        pickupOnlyDataList = new ArrayList<LatLng>();
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
                setFragmentValues(getActivity(),dataList);
              /*  LocationManager service = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                boolean enabledGPS = service
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);
                boolean enabledWiFi = service
                        .isProviderEnabled(LocationManager.NETWORK_PROVIDER);


                locationManager = (LocationManager) (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
*/
              /*  Criteria criteria = new Criteria();
                provider = locationManager.getBestProvider(criteria, false);
                Location location = locationManager.getLastKnownLocation(provider);

                double longitude = location.getLongitude();
                double latitude = location.getLatitude();

                LatLng latLng1 = new LatLng(latitude, longitude);*/

       /*         LatLng latLng1 = new LatLng(latitude, longitude);
                googleMap.addMarker(new MarkerOptions().position(latLng1).title("Runner1").snippet("Location"));*/
               /* if(googleMap!=null){
                    googleMap.clear();

                    googleMap.setMyLocationEnabled(false);
                    googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                }

                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(latitude, longitude))
                        .title("Runner Position")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.bike_google_map_cion)));
                CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng1).zoom(35).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));*/
/*

                double latCurrent = dashDataArray.get(0).latitude;
                double lngCurrent = dashDataArray.get(0).longitude;

                sydney = new LatLng(latCurrent, lngCurrent);
                googleMap.addMarker(new MarkerOptions().position(sydney).title("Customer").snippet("Location"));*/
                // Define the criteria how to select the locatioin provider -> use
                // default
            /*    locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        mCurrentLocation = location;
                        Double lat = mCurrentLocation.getLatitude();
                        Double lng = mCurrentLocation.getLongitude();
                        // LatLng latLng = new LatLng(lat, lng); //you already have this
                        //  drawLine(dashDataArray,userLatLng);
                        //  dashDataArray.add(latLng); //added

                        LatLng latLng1 = new LatLng(lat, lng);
               *//*         googleMap.addMarker(new MarkerOptions().position(latLng1).title("Runner").snippet("Location"));*//*
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
   *//*  String latitude = String.valueOf(lat);
       String lngitude = String.valueOf(lng);*//*
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
                }*/
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
/*

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
*/

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
        setFragmentValues(getActivity(),dataList);
     /*   if(googleMap!=null){
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
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);*/

    }



    @Override
    public void onResume() {

        mMapView.onResume();
        if(googleMap!=null){
            googleMap.clear();
        }

        setFragmentValues(getActivity(),dataList);
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
    /*    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
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
*/
        super.onResume();
    }

    public String generateHash(String id){
        String str = id+"LetsServiceAPIs";
        long hash = 0;
        for (int i = 0; i < str.length(); i++){
            char character = str.charAt(i);
            int ascii = (int) character;
            hash = ((hash * 8)-hash)+ascii;
        }
        return hash+"";
    }


    private void getRunnerLocation(DataList dataList, final LatLng latLng, final String isRunnerAvil){
        RequestQueue queue = null;

        queue = Volley.newRequestQueue(getActivity());
        progressBar.setVisibility(View.VISIBLE);

        String token =generateHash("3");
        final String headTokn =generateHeader("3");

       // String URL = "https://letsservice.co.in/heroPd/getRunnerCurrentLocation/"+dataList.getId()+"/3/"+token;
        String URL = "https://letsservice.co.in/heroPd/getRunnerCurrentLocation/"+dataList.getRunnerId()+"/3/"+token;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                       progressBar.setVisibility(View.GONE);
                        Log.e("Response", response.toString());
                        String responsemessage = null;

                        try {
                            String success = response.getString("success");
                             JSONObject jsonObject=response.getJSONObject("details");
                            if(success.equals("true")) {


                                  runnerLatitude =jsonObject.getString("runnerLatitude");
                                   runnerLongitude =jsonObject.getString("runnerLongitude");
                                 double lat = Double.parseDouble(runnerLatitude);
                                 double lng = Double.parseDouble(runnerLongitude);
                                if(isRunnerAvil.equals("2"))
                                {
                                    googleMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(lat, lng))
                                            .title("Runner Current Position")
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.bike_google_map_cion)));
                                }else {
                                    getPolyline(runnerLatitude,runnerLongitude,latLng,"1");
                                }


                            }


//8147868049
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR","error => "+error.toString());
                        progressBar.setVisibility(View.GONE);
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Header-Token", headTokn);
                return params;
            }
        };

        queue.add(jsObjRequest);
    }


    public String generateHeader(String id){
        String str = id+"LsSalesHeader";
        long hash = 0;
        for (int i = 0; i < str.length(); i++){
            char character = str.charAt(i);
            int ascii = (int) character;
            hash = ((hash * 8)-hash)+ascii;
        }
        return hash+"";
    }
    public void getPickupdata()
    {
        if(pickupOnlyDataList.size()>0){
             pickupOnlyDataList.clear();
            }

        RequestQueue queue = null;

        queue = Volley.newRequestQueue(context);
     //   progressBar.setVisibility(View.VISIBLE);
        String URL=null;

        String token =generateHash(dataList.getId());
        URL ="https://letsservicetech.in/appointmentWiseRunnerTrack/"+dataList.getId()+"/start/startPick/endPick/"+token;


        JsonArrayRequest jsObjRequest = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println(response);
                        //   progressBar.setVisibility(View.GONE);
                        //    Log.e("Response", response.toString());
                        String responsemessage = null;


                        String typeOfServ = null;
                        try {
                            //  JSONArray json =response.getJSONArray("");

                            for(int i=0;i<response.length();i++){

                                {
                                    JSONObject jsonObject = response.getJSONObject(i);


                                    String  startPickupLat = jsonObject.getString("lat");
                                    String  StartPickupLng = jsonObject.getString("lng");

                                    // String  startPickupLat1,StartPickupLng1,dropPickupLat1,dropPickupLng1;
                                  LatLng  latLng = new LatLng(Double.parseDouble(startPickupLat.trim()),Double.parseDouble(StartPickupLng.trim()));
                                    if (!pickupOnlyDataList.contains(latLng)){
                                        pickupOnlyDataList.add(latLng);
                                    }
                                }

                            }
                            if(pickupOnlyDataList.size()>0) {
                             //   drawLine(pickupOnlyDataList,"1");
                              //  progressBar.setVisibility(View.GONE);
                              //  pickupFragment.newInstance(pickupOnlyDataList, getApplicationContext(), dataList);

                            }

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }


                },


                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                     //   progressBar.setVisibility(View.GONE);
                       // pickupFragment.newInstance(pickupOnlyDataList,getApplicationContext(), dataList);
                    }
                });

        queue.add(jsObjRequest);


    }


    public void getPolyline(String latit1, String  longit1, LatLng latLng, final String isServiceCenter) {
     //   getAddressLatLng(latLng);
        String link6 = "https://maps.googleapis.com/maps/api/directions/json?origin=" +latit1+ "," +longit1+ "&destination=" + latLng.latitude + "," + latLng.longitude + "&mode=car&key=AIzaSyAyjEwKCLpCMA2CFFy0JDn2D9YP6d6kK64";
        Log.e("Link 67", link6);

progressBar.setVisibility(View.VISIBLE);
        final RequestQueue queue = Volley.newRequestQueue(getActivity());
        final JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, link6, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.e("Json Object", "response:=" + response);
                try {
                    progressBar.setVisibility(View.GONE);
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
                            //  textDistance.setText(distancefinal);
                        }
                        //    nametext.setText("Distance: "+distancefinal);

                        String overviewpoints = jsonObject.getString("overview_polyline");
                        Log.e("Overviewpoints", overviewpoints);
                        JSONObject jsonObjectpoints = new JSONObject(overviewpoints);
                        String points = jsonObjectpoints.getString("points");
                        Log.e("points", points);
                        pointsdecode = decodePolyLine(points);
                        drawLine(pointsdecode, isServiceCenter);
                    }
                } catch (Exception e) {
                    progressBar.setVisibility(View.GONE);
                    e.printStackTrace();
                }


// TODO Auto-generated method stub

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
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

            public void drawLine(List<LatLng> points,String isServiceCenter) {
                if (points == null) {
                    Log.e("Draw Line", "got null as parameters");
                    return;
                }
                if(googleMap!=null){
                    googleMap.clear();
                }
                int s = points.size()-1;
                double latCurrent = points.get(0).latitude;
                double lngCurrent = points.get(0).longitude;

                double latCurrent1 = points.get(s).latitude;
                double lngCurrent1 = points.get(s).longitude;
                sydney = new LatLng(latCurrent, lngCurrent);
                LatLng sydney1= new LatLng(latCurrent1, lngCurrent1);
                if(isServiceCenter.equals("1")){

                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(latCurrent, lngCurrent))
                            .title("Runner Current Position")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.bike_google_map_cion)));
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(15).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }else {
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(latCurrent, lngCurrent))
                            .title("Service Center")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.service_center_image)));
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney1).zoom(15).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }



              /*        googleMap.addMarker(new MarkerOptions().position(sydney1).title("Runner").snippet("Current Position"));
*/
                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(latCurrent1, lngCurrent1))
                        .title("Customer Location")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.home_icon_map)));


            line = googleMap.addPolyline(new PolylineOptions().width(3).color(Color.BLUE));
                line.setPoints(points);
              myPolyline = googleMap.addPolyline(new PolylineOptions().addAll(points));
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




