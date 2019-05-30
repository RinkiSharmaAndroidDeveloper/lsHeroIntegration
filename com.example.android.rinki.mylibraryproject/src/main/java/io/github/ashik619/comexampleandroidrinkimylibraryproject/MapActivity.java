package io.github.ashik619.comexampleandroidrinkimylibraryproject;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends Activity {
    DataList dataList;
    ProgressBar progressBar;
    List<LatLng> pickupOnlyDataList;

    List<LatLng> dropOnlyDataList;
    PickupOnlyData pickupOnlyData;
    LatLng latLng;
    PickupFragment pickupFragment;
    DropFragment dropFragment;
    ImageView backIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        progressBar=findViewById(R.id.progress);
        backIcon=findViewById(R.id.up_icon);

        pickupOnlyDataList =new ArrayList<LatLng>();
        dropOnlyDataList =new ArrayList<LatLng>();
        pickupFragment = new PickupFragment();
        dropFragment = new DropFragment();
        Intent intent =getIntent();
        dataList =intent.getParcelableExtra("dataList");
       // getData();
        getPickupdata();
        getDropdata();

        Bundle bundleBroad = new Bundle();
        pickupFragment.setArguments(bundleBroad);
        FragmentManager managerBroad = getFragmentManager();
        FragmentTransaction transactionBroad = managerBroad.beginTransaction();
        transactionBroad.replace(R.id.pick_up_map, pickupFragment, null);
       // pickupFragment.newInstance(click);
    //    transactionBroad.addToBackStack(null);
        transactionBroad.commit();


        Bundle bundleBroad1 = new Bundle();
        dropFragment.setArguments(bundleBroad);
        FragmentManager managerBroad1 = getFragmentManager();
        FragmentTransaction transactionBroad1 = managerBroad.beginTransaction();
        transactionBroad.replace(R.id.drop_map, dropFragment, null);

       // pickupFragment.newInstance(click);
       // transactionBroad1.addToBackStack(null);
        transactionBroad1.commit();

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
  /*      getSupportFragmentManager().beginTransaction()
                .add(R.id.pick_up_map, new PickupFragment()).commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.drop_map, new DropFragment()).commit();*/
    }
  /*  public void getData()
    {

        RequestQueue queue = null;

        queue = Volley.newRequestQueue(this);
        progressBar.setVisibility(View.VISIBLE);
        String URL=null;
       // String token= generateHash(id);
       if(dataList.getAssistanceType().equals("Pickup and Drop")){
             URL ="https://letsservicetech.in/getAppointmentWiseSourceDestinationLocation/139932/4599239666965021700";
        }
     *//*   if(dataList.getAssistanceType().equals("Pick Only")){
             URL ="https://letsservicetech.in/appointmentWiseRunnerTrack/139932/start/startPick/endPick/4599239666965021700";
        }
        if(dataList.getAssistanceType().equals("Drop Only")){
             URL ="https://letsservicetech.in/appointmentWiseRunnerTrack/139932/start/startDrop/dropEnd/4599239666965021700";
        }*//*
        //String URL = Utils.Base_url+mobile;getAssigedAppointmentDetail/:aaptId/:token


        JsonArrayRequest jsObjRequest = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println(response);
                        progressBar.setVisibility(View.GONE);
                    //    Log.e("Response", response.toString());
                        String responsemessage = null;


                        String typeOfServ = null;
                 try {
                          //  JSONArray json =response.getJSONArray("");

                            for(int i=0;i<response.length();i++){
                                JSONArray jsonArray1 =response.getJSONArray(i);
                                for(int j=0;j<jsonArray1.length();j++)
                                {
                                    JSONObject jsonObject = jsonArray1.getJSONObject(i);
                                    if(i==0&&j==0){

                                       String  startPickupLat = jsonObject.getString("lat");
                                       String  StartPickupLng = jsonObject.getString("lng");

                                       // String  startPickupLat1,StartPickupLng1,dropPickupLat1,dropPickupLng1;
                                    }
                                    if(i==0&&j==1){
                                        String  dropPickupLat = jsonObject.getString("lat");
                                        String  dropPickupLng = jsonObject.getString("lng");

                                    }

                                    if(i==1&&j==0){

                                       String  startPickupLat1 = jsonObject.getString("lat");
                                       String  StartPickupLng1 = jsonObject.getString("lng");

                                       // String  startPickupLat1,StartPickupLng1,dropPickupLat1,dropPickupLng1;
                                    }
                                    if(i==1&&j==1){
                                        String  dropPickupLat1 = jsonObject.getString("lat");
                                        String  dropPickupLng1 = jsonObject.getString("lng");

                                    }
                                }
                            }
                    *//*      typeOfServ = response.getString("typeOfService");
                            String fuel1 = response.getString("fuel");
                            String odometer1 = response.getString("odometer");
                            String damage1 = response.getString("damageDescription");
                            String reciept1 = response.getString("receipt");
                            String customer_voice = response.getString("customerVoiceId");
                            String insurance_img = response.getString("insuranceCopy");
                            String rc_copyImg = response.getString("rcCopy");
                            String finalBillImg = response.getString("finalBill");
                            String bike11 = response.getString("bikePhoto1");
                            String bike21 = response.getString("bikePhoto2");
                            String bike31 = response.getString("bikePhoto3");
                            String bike41 = response.getString("bikePhoto4");
                            String bike51 = response.getString("bikePhoto5");
                            String bike61 = response.getString("bikePhoto6");
                            String bike71 = response.getString("bikePhoto7");
*//*


                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }


                },


                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                    }
                });

        queue.add(jsObjRequest);


    }*/
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

    public void getPickupdata()
    {


        RequestQueue queue = null;

        queue = Volley.newRequestQueue(this);
        progressBar.setVisibility(View.VISIBLE);
        String URL=null;
       // String token= generateHash(id);
      /* if(dataList.getAssistanceType().equals("Pickup and Drop")){
             URL ="https://letsservicetech.in/getAppointmentWiseSourceDestinationLocation/139932/4599239666965021700";
        }*/
      String token =generateHash(dataList.getId());
        URL ="https://letsservicetech.in/appointmentWiseRunnerTrack/"+dataList.getId()+"/start/startPick/endPick/"+token;

  /*  if(dataList.getAssistanceType().equals("Pick Only")){
             URL ="https://letsservicetech.in/appointmentWiseRunnerTrack/139932/start/startPick/endPick/4599239666965021700";
        }*/
       /* if(dataList.getAssistanceType().equals("Drop Only")){
             URL ="https://letsservicetech.in/appointmentWiseRunnerTrack/139932/start/startDrop/dropEnd/4599239666965021700";
        }*/
        //String URL = Utils.Base_url+mobile;getAssigedAppointmentDetail/:aaptId/:token


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
                                     latLng = new LatLng(Double.parseDouble(startPickupLat.trim()),Double.parseDouble(StartPickupLng.trim()));
                                    if (!pickupOnlyDataList.contains(latLng)){
                                        pickupOnlyDataList.add(latLng);
                                    }
                                }

                                }
                     if(pickupOnlyDataList.size()>0) {
                         progressBar.setVisibility(View.GONE);
                         pickupFragment.newInstance(pickupOnlyDataList, getApplicationContext());

                     }

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }


                },


                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        pickupFragment.newInstance(pickupOnlyDataList,getApplicationContext());
                    }
                });

        queue.add(jsObjRequest);


    }


    public void getDropdata()
    {


        RequestQueue queue = null;

        queue = Volley.newRequestQueue(this);
        progressBar.setVisibility(View.VISIBLE);
        String URL=null;
       // String token= generateHash(id);
      /* if(dataList.getAssistanceType().equals("Pickup and Drop")){
             URL ="https://letsservicetech.in/getAppointmentWiseSourceDestinationLocation/139932/4599239666965021700";
        }*/
        String token =generateHash(dataList.getId());
        URL ="https://letsservicetech.in/appointmentWiseRunnerTrack/"+dataList.getId()+"/start/startDrop/dropEnd/"+token;

  /*  if(dataList.getAssistanceType().equals("Pick Only")){
             URL ="https://letsservicetech.in/appointmentWiseRunnerTrack/139932/start/startPick/endPick/4599239666965021700";
        }*/
       /* if(dataList.getAssistanceType().equals("Drop Only")){
             URL ="https://letsservicetech.in/appointmentWiseRunnerTrack/139932/start/startDrop/dropEnd/4599239666965021700";
        }*/
        //String URL = Utils.Base_url+mobile;getAssigedAppointmentDetail/:aaptId/:token


        JsonArrayRequest jsObjRequest = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println(response);
                       // progressBar.setVisibility(View.GONE);
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
                                     latLng = new LatLng(Double.parseDouble(startPickupLat.trim()),Double.parseDouble(StartPickupLng.trim()));
                                    if (!dropOnlyDataList.contains(latLng)){
                                        dropOnlyDataList.add(latLng);
                                    }
                                }


                                }
                     if(dropOnlyDataList.size()>0)
                     {
                         dropFragment.newInstance(dropOnlyDataList,getApplicationContext());
                         progressBar.setVisibility(View.GONE);
                     }


                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }


                },


                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        dropFragment.newInstance(dropOnlyDataList,getApplicationContext());
                    }
                });

        queue.add(jsObjRequest);


    }
}
