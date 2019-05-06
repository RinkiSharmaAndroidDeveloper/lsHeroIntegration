package io.github.ashik619.comexampleandroidrinkimylibraryproject;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
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

    View view;
    private GoogleMap mMap;
    int REQUEST_LOCATION = 1;
    MapView mMapView;
    Double lat=12.9121, lng=77.6446,lati=12.9698,longi=77.7500,lat1;
    private GoogleMap googleMap;
    Polyline polyline23;
    List<LatLng> pointsdecode = new ArrayList<LatLng>();
    int p = 0;
    List<LatLng> dashDataArray;
    Context context;
ImageView imageView;
    public DropFragment newInstance(List<LatLng> dashDataArray, Context context) {
        DropFragment fragment = new DropFragment();
        Bundle args = new Bundle();
        this.dashDataArray=dashDataArray;
        this.context=context;
        if(dashDataArray!=null&&dashDataArray.size()>0){
            mMapView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
            drawLine(dashDataArray);
        }else{
            mMapView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
        }
        return fragment;
    }

    public void drawLine(List<LatLng> points) {
        if (points == null) {
            Log.e("Draw Line", "got null as parameters");
            return;
        }
        double latCurrent = points.get(0).latitude;
        double lngCurrent = points.get(0).longitude;
        LatLng sydney = new LatLng(latCurrent, lngCurrent);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Dropped").snippet("Start Position"));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(15).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        Polyline line = googleMap.addPolyline(new PolylineOptions().width(3).color(Color.BLUE));
        line.setPoints(points);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.drop_fragment, container, false);
       /* SupportMapFragment mapFragment = (SupportMapFragment) view.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);*/

        mMapView = (MapView) view.findViewById(R.id.mapView);
        imageView = (ImageView) view.findViewById(R.id.image_view);
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
                googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map
            /*    LatLng sydney = new LatLng(lati, longi);
                googleMap.addMarker(new MarkerOptions().position(sydney).title("Start Drop").snippet("Whitefield"));

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

                        *//*  googleMap.addMarker(new MarkerOptions()
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
                                            googleMap.addMarker(new MarkerOptions().position(sydney).title("Dropped").snippet("HSR Layout"));
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

                        });*/
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
  /*  @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
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
