package io.github.ashik619.comexampleandroidrinkimylibraryproject;

import android.Manifest;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;
import static com.google.android.gms.maps.model.JointType.ROUND;

/**
 * Created by dilip on 3/5/19.
 */

public class DropFragment extends Fragment {
    private final int FIVE_SECONDS = 10000;
    private static final long ANIMATION_TIME_PER_ROUTE = 3000;
    View view;
    LatLng sydney;
    DataList dataList;
    int REQUEST_LOCATION = 1;
    MapView mMapView;
    TextView textView;
    private boolean isFirstPosition = true;

    public LatLng startPosition;
    public LatLng endPosition;
    PolylineOptions polylineOptions;
     String distancefinal =null,duration=null;
    private GoogleMap googleMap;
    List<LatLng> polyLineList;
    float zoomLevel;
    private Polyline greyPolyLine;
    List<LatLng> pointsdecode = new ArrayList<LatLng>();

    Context context;
    ImageView imageView;
    ImageView navigationImage;
    LatLng userLatLng;
    Handler handler;
    Polyline line;
    Polyline myPolyline;
    AlertDialog alertDialog1;
    Marker marker;
    private int emission = 0;

    PicassoMarker marker1;
    private int index;
    private Marker carMarker;
    private int next;
    private float v;
    double lat,lng;

    public void setFragmentValues(Context context, final DataList dataList){

        if(dataList.getStatus().equals("Bike Delivered")){
            mMapView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            navigationImage.setVisibility(View.VISIBLE);
            userLatLng = getLocationFromAddress(context, dataList.getDropAddress());

            if(userLatLng!=null){
                getPolyline(dataList.getServiceCenterLat(),dataList.getServiceCenterLng(),userLatLng,"2");
            }

            //
        }if(dataList.getStatus().equals("Bike Service In Progress")||dataList.getStatus().equals("Bike Picked For Service")||dataList.getStatus().equals("Runner Assigned")||dataList.getStatus().equals("Appointment Created")){
            mMapView.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
            navigationImage.setVisibility(View.GONE);
        }if(dataList.getStatus().equals("Bike Picked For Delivery")){

            mMapView.setVisibility(View.VISIBLE);
            navigationImage.setVisibility(View.VISIBLE);
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

    private int getZoomLevel(double radius){
        double scale = radius / 500;
        return ((int) (16 - Math.log(scale) / Math.log(2)));
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
            if(address!=null&&address.size()>0&&address.get(0)!=null)
            {
                Address location = address.get(0);
                p1 = new LatLng(location.getLatitude(), location.getLongitude());
            }else{

                Toast.makeText(getActivity(),"Please give the valid drop address",Toast.LENGTH_LONG).show();
            }
        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }



    private void animateCarOnMap(final List<LatLng> latLngs, final float zoomLevel) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng latLng : latLngs) {
            builder.include(latLng);
        }
        LatLngBounds bounds = builder.build();
        CameraUpdate mCameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 2);
        //  googleMap.animateCamera(mCameraUpdate);

        if (emission == 1) {
            marker = googleMap.addMarker(new MarkerOptions().position(latLngs.get(0))
                    .flat(true)
                    .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(getActivity()))));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngs.get(0), zoomLevel));
        }
        marker.setPosition(latLngs.get(0));

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        // valueAnimator.setDuration(3000);
        //valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
              /*  v = valueAnimator.getAnimatedFraction();
                double lng = v * latLngs.get(1).longitude + (1 - v)
                        * latLngs.get(0).longitude;
                double lat = v * latLngs.get(1).latitude + (1 - v)
                        * latLngs.get(0).latitude;
                LatLng newPos = new LatLng(lat, lng);
                marker.remove();
                marker.setPosition(newPos);
                marker.setAnchor(1.5f, 0.5f);*/
                // marker.setRotation(getBearing(latLngs.get(0), newPos));
                marker.remove();
                marker = googleMap.addMarker(new MarkerOptions().position(latLngs.get(0))
                        .flat(true)
                        .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(getActivity()))));
               /* googleMap.animateCamera(CameraUpdateFactory.newCameraPosition
                        (new CameraPosition.Builder().target(latLngs.get(0))
                                .zoom(15.5f).build()));*/
                //   googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel));
                animateMarker(marker,latLngs.get(1),false);


            }
        });
        valueAnimator.start();
    }

    private Bitmap getMarkerBitmapFromView( Context context) {

        View customMarkerView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.marker_image, null);
        CircleImageView markerImageView = (CircleImageView) customMarkerView.findViewById(R.id.profile_image);


        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }

    public void animateMarker(final Marker marker, final LatLng toPosition,
                              final boolean hideMarker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = googleMap.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 5000;
        final Interpolator interpolator = new LinearInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 100);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.drop_fragment, container, false);

        mMapView = (MapView) view.findViewById(R.id.mapView);
        imageView = (ImageView) view.findViewById(R.id.image_view);
        textView = (TextView) view.findViewById(R.id.default_txt);
        navigationImage = (ImageView) view.findViewById(R.id.navigation);
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

        navigationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userLatLng!=null&&!(zoomLevel==0)){
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, zoomLevel));
                }

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

                                double lat = Double.parseDouble(runnerLatitude);
                                double lng = Double.parseDouble(runnerLongitude);

                                Log.d(TAG, runnerLatitude + "--" + runnerLatitude);
                                    if (isFirstPosition) {
                                        startPosition = new LatLng(lat, lng);
                                        carMarker = googleMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).icon(BitmapDescriptorFactory.fromResource(R.drawable.bike)));
/*
                                        carMarker = googleMap.addMarker(new MarkerOptions().position(startPosition).
                                                flat(true).icon(BitmapDescriptorFactory.fromResource(R.mipmap.new_car_small)));
                                        carMarker.setAnchor(0.5f, 0.5f);

                                *//*    googleMap.moveCamera(CameraUpdateFactory
                                            .newCameraPosition
                                                    (new CameraPosition.Builder()
                                                            .target(startPosition)
                                                            .zoom(0f)
                                                            .build()));
*/
                                        isFirstPosition = false;
                                        getPolyline(runnerLatitude,runnerLongitude,latLng,"1");

                                    } else {
                                        endPosition = new LatLng(lat, lng);
                                     /*   carMarker = googleMap.addMarker(new MarkerOptions().position(endPosition).icon(BitmapDescriptorFactory.fromResource(R.drawable.bike)));*/
                                        Log.d(TAG, startPosition.latitude + "--" + endPosition.latitude + "--Check --" + startPosition.longitude + "--" + endPosition.longitude);

                                        if ((startPosition.latitude != endPosition.latitude) || (startPosition.longitude != endPosition.longitude)) {
                                            getPolyline(runnerLatitude,runnerLongitude,latLng,"1");
                                            Log.e(TAG, "NOT SAME");
                                            //   startBikeAnimation(startPosition, endPosition);

                                        } else {

                                            Log.e(TAG, "SAMME");
                                        }
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
                            String durationObj = legsobj.getString("duration");
                            JSONObject jstextDistance = new JSONObject(distanceObj);
                            JSONObject jstextDuration = new JSONObject(durationObj);
                            distancefinal = jstextDistance.getString("text");
                            duration = jstextDuration.getString("text");
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
                       // drawLine(pointsdecode, isServiceCenter,duration,distancefinal);
                        staticPolyLine(points, isServiceCenter,duration,distancefinal);
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
    void staticPolyLine(String polyLine,String isServiceCenter,String duration,String distancefinal) {
        if(!(emission==0)){
            // carMarker.remove();
            googleMap.clear();
        }


        polyLineList =decodePolyLine(polyLine);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng latLng : polyLineList) {
            builder.include(latLng);
        }
        LatLngBounds bounds = builder.build();
        CameraUpdate mCameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 2);
        googleMap.animateCamera(mCameraUpdate);

        polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.BLACK);
        polylineOptions.width(5);
        polylineOptions.startCap(new SquareCap());
        polylineOptions.endCap(new SquareCap());
        polylineOptions.jointType(ROUND);
        polylineOptions.addAll(polyLineList);
        greyPolyLine = googleMap.addPolyline(polylineOptions);
        double latCurrent = polyLineList.get(0).latitude;
        double lngCurrent = polyLineList.get(0).longitude;
        int s = polyLineList.size()-1;

        double latCurrent1 = polyLineList.get(s).latitude;
        double lngCurrent1 = polyLineList.get(s).longitude;
        sydney = new LatLng(latCurrent, lngCurrent);
        LatLng sydney1= new LatLng(latCurrent1, lngCurrent1);

        Location locationA = new Location("point A");
        locationA.setLatitude(latCurrent);
        locationA.setLongitude(lngCurrent);

        Location locationB = new Location("point B");
        locationB.setLatitude(latCurrent1);
        locationB.setLongitude(lngCurrent1);
        double distance = locationA.distanceTo(locationB) ;



        double circleRad = distance*1000;//multiply by 1000 to make units in KM



        zoomLevel = getZoomLevel(circleRad);


        if(isServiceCenter.equals("1")) {
            carMarker = googleMap.addMarker(new MarkerOptions().position(polyLineList.get(0)).icon(BitmapDescriptorFactory.fromResource(R.drawable.bike)));
            emission++;
            if (endPosition != null) {

                startBikeAnimation(startPosition, endPosition);
            }
        }else {
            carMarker = googleMap.addMarker(new MarkerOptions().position(new LatLng(latCurrent, lngCurrent)).title("Service Center")
                    .flat(true)
                    .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(getActivity()))));

            //   CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney1).zoom(15).build();
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel));
        }



              /*        googleMap.addMarker(new MarkerOptions().position(sydney1).title("Runner").snippet("Current Position"));
*/

        if(isServiceCenter.equals("1")){

            if(duration!=null){

                marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(latCurrent1, lngCurrent1)).title("Customer Location")
                        .flat(true)
                        .icon(BitmapDescriptorFactory.fromBitmap(getCustomerMArker(duration,getActivity()))));
                // googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel));
            }

        }else {

            marker1 = new PicassoMarker(googleMap.addMarker(new MarkerOptions().position(new LatLng(latCurrent1, lngCurrent1)).title("Customer Location")));
            Picasso.with(getActivity()).load(R.drawable.home_icon_map).resize( 120,  120)
                    .into(marker1);
        }
        // startCarAnimation(polyLineList.get(0).latitude, polyLineList.get(0).longitude);

    }


    private void startBikeAnimation(final LatLng start, final LatLng end) {

        Log.i(TAG, "startBikeAnimation called...");

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(ANIMATION_TIME_PER_ROUTE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                //LogMe.i(TAG, "Car Animation Started...");
                v = valueAnimator.getAnimatedFraction();
                lng = v * end.longitude + (1 - v)
                        * start.longitude;
                lat = v * end.latitude + (1 - v)
                        * start.latitude;

                LatLng newPos = new LatLng(lat, lng);

                carMarker.setAnchor(0.5f, 0.5f);
                carMarker.setPosition(newPos);

                carMarker.setRotation(getBearing(start, end));

                // todo : Shihab > i can delay here
          /*      googleMap.moveCamera(CameraUpdateFactory
                        .newCameraPosition
                                (new CameraPosition.Builder()
                                        .target(newPos)
                                        .zoom(10.5f)
                                        .build()));*/

                startPosition = carMarker.getPosition();

            }

        });
        valueAnimator.start();
    }
    public static float getBearing(LatLng begin, LatLng end) {
        double lat = Math.abs(begin.latitude - end.latitude);
        double lng = Math.abs(begin.longitude - end.longitude);

        if (begin.latitude < end.latitude && begin.longitude < end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)));
        else if (begin.latitude >= end.latitude && begin.longitude < end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 90);
        else if (begin.latitude >= end.latitude && begin.longitude >= end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)) + 180);
        else if (begin.latitude < end.latitude && begin.longitude >= end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 270);
        return -1;
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

        super.onStart();

    }



    @Override
    public void onPause() {
        super.onPause();

        mMapView.onPause();


    }

    private Bitmap getCustomerMArker(String url, Context context) {

        View customMarkerView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.customer_marker, null);
        TextView textView = (TextView) customMarkerView.findViewById(R.id.error_message);
        if(url!=null){
            textView.setText(url);
        }


        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }

    @Override
    public void onResume() {

        mMapView.onResume();

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
