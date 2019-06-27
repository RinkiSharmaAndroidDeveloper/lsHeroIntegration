package io.github.ashik619.comexampleandroidrinkimylibraryproject;

import android.Manifest;
import android.content.Context;
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

import com.android.volley.AuthFailureError;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dilip on 3/5/19.
 */

public class DropFragment extends Fragment {
    private final int FIVE_SECONDS = 5000;
    View view;

    DataList dataList;
    int REQUEST_LOCATION = 1;
    MapView mMapView;
    TextView textView;

    private GoogleMap googleMap;

    List<LatLng> pointsdecode = new ArrayList<LatLng>();

    Context context;
    ImageView imageView;
    LatLng userLatLng;
    Handler handler;
    Polyline line;
    Polyline myPolyline;


    public void setFragmentValues(Context context, final DataList dataList){

        if(dataList.getStatus().equals("Bike Delivered")){
            mMapView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            userLatLng = getLocationFromAddress(context, dataList.getDropAddress());

            if(userLatLng!=null){
                getPolyline(dataList.getServiceCenterLat(),dataList.getServiceCenterLng(),userLatLng,"2");
            }

            //
        }if(dataList.getStatus().equals("Bike Service In Progress")||dataList.getStatus().equals("Bike Picked For Service")||dataList.getStatus().equals("Runner Assigned")||dataList.getStatus().equals("Appointment Created")){
            mMapView.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }if(dataList.getStatus().equals("Bike Picked For Delivery")){

            mMapView.setVisibility(View.VISIBLE);
            userLatLng = getLocationFromAddress(context, dataList.getDropAddress());
            // imageView.setVisibility(View.VISIBLE);
            if(userLatLng!=null)
            {
                handler.postDelayed(new Runnable() {
                    public void run() {
                        getRunnerLocation(dataList,userLatLng);
                        // this method will contain your almost-finished HTTP calls
                        handler.postDelayed(this, FIVE_SECONDS);
                    }
                }, FIVE_SECONDS);

            }
            textView.setVisibility(View.GONE);
        }
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
      LatLng  sydney = new LatLng(latCurrent, lngCurrent);
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.drop_fragment, container, false);

        mMapView = (MapView) view.findViewById(R.id.mapView);
        imageView = (ImageView) view.findViewById(R.id.image_view);
        textView = (TextView) view.findViewById(R.id.default_txt);
        mMapView.onCreate(savedInstanceState);
        dataList = getArguments().getParcelable("dataList");
        handler=new Handler();
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

                setFragmentValues(getActivity(),dataList);


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

    private void getRunnerLocation(DataList dataList, final LatLng latLng){
        RequestQueue queue = null;
        if(getActivity()!=null) {
            queue = Volley.newRequestQueue(getActivity());
        }else{
            return;
        }
        // progress.setVisibility(View.VISIBLE);

        String token =generateHash("3");
        final String headTokn =generateHeader("3");
        String URL = "https://letsservice.co.in/heroPd/getRunnerCurrentLocation/"+dataList.getRunnerId()+"/3/"+token;
       // String URL = "https://letsservice.co.in/heroPd/getRunnerCurrentLocation/"+"816"+"/3/"+token;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        //    progress.setVisibility(View.GONE);
                        Log.e("Response", response.toString());
                        String responsemessage = null;

                        try {
                            String success = response.getString("success");
                            JSONObject jsonObject=response.getJSONObject("details");
                            if(success.equals("true")) {


                                String runnerLatitude =jsonObject.getString("runnerLatitude");
                                String runnerLongitude =jsonObject.getString("runnerLongitude");

                                getPolyline(runnerLatitude,runnerLongitude,latLng,"1");


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
                        //progress.setVisibility(View.GONE);
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

    public void getPolyline(String latit1, String  longit1, LatLng latLng, final String isServiceCenter) {
        //   getAddressLatLng(latLng);
        String link6 = "https://maps.googleapis.com/maps/api/directions/json?origin=" +latit1+ "," +longit1+ "&destination=" + latLng.latitude + "," + latLng.longitude + "&mode=car&key=AIzaSyAyjEwKCLpCMA2CFFy0JDn2D9YP6d6kK64";
        Log.e("Link 67", link6);

if(getActivity()==null){
    return;
}
        final RequestQueue queue = Volley.newRequestQueue(getActivity());
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
    public void onStart() {
        if(googleMap!=null){
            googleMap.clear();
        }

        super.onStart();

    }






    @Override
    public void onPause() {
        super.onPause();

        mMapView.onPause();
        if (googleMap != null) {
            googleMap.clear();
        }

    }



    @Override
    public void onResume() {

        mMapView.onResume();
        if(googleMap!=null){
            googleMap.clear();
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
