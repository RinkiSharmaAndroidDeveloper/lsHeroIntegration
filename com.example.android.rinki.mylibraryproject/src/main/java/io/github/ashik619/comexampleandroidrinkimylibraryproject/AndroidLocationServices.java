package io.github.ashik619.comexampleandroidrinkimylibraryproject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by dilip on 17/6/19.
 */

public class AndroidLocationServices  extends Service implements LocationListener{

    PowerManager.WakeLock wakeLock;
    String MODULE="AndroidLocationServices",TAG="";
    private LocationManager locationManager;
    double getLattitude=0,getLogitude=0;
    private static final long MIN_TIME_BW_UPDATES = 1; // 1 minute
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;


    public AndroidLocationServices() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
       // preferences=getSharedPreferences(GlobalConfig.PREF_NAME, MODE_PRIVATE);
        PowerManager pm = (PowerManager) getSystemService(this.POWER_SERVICE);

        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "DoNotSleep");

        // Toast.makeText(getApplicationContext(), "Service Created",
        // Toast.LENGTH_SHORT).show();

        Log.e("Google", "Service Created");

    }

    @Override
    @Deprecated
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);

        Log.e("Google", "Service Started");

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2500, 0, listener);
 /*       locationManager.requestLocationUpdates( LocationManager.NETWORK_PROVIDER,  MIN_TIME_BW_UPDATES,  MIN_DISTANCE_CHANGE_FOR_UPDATES, listener);*/


    }


/*
    public LocationListener listener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            // TODO Auto-generated method stub

            Log.e("Google", "Location Changed");
            location.setAccuracy(100);

            if (location == null)
                return;
            getLattitude=location.getLatitude();
            getLogitude=location.getLongitude();
            Toast.makeText(AndroidLocationServices.this,getLattitude+","+getLogitude,Toast.LENGTH_LONG).show();
            Log.e("latitude", getLattitude + "");
            Log.e("longitude", getLogitude + "");


        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub

        }
    };*/

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

//        wakeLock.release();

    }


    @Override
    public void onLocationChanged(Location location) {
        Double lat = location.getLatitude();
        Double lng = location.getLongitude();
        LatLng latLng = new LatLng(lat, lng); //you already have this
        //drawLine(dashDataArray,userLatLng);
        //  dashDataArray.add(latLng); //added

        // redrawLine(lat,lng);
        String latitude = String.valueOf(lat);
        String lngitude = String.valueOf(lng);
        Toast.makeText(AndroidLocationServices.this,latitude+","+lngitude,Toast.LENGTH_LONG).show();
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
}