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
        progressBar = findViewById(R.id.progress);
        backIcon = findViewById(R.id.up_icon);

        pickupOnlyDataList = new ArrayList<LatLng>();
        dropOnlyDataList = new ArrayList<LatLng>();
        pickupFragment = new PickupFragment();
        dropFragment = new DropFragment();
        Intent intent = getIntent();
        dataList = intent.getParcelableExtra("dataList");

        Bundle bundleBroad = new Bundle();

        bundleBroad.putParcelable("dataList",dataList);
        FragmentManager managerBroad = getFragmentManager();
        FragmentTransaction transactionBroad = managerBroad.beginTransaction();
        transactionBroad.replace(R.id.pick_up_map, pickupFragment, null);
       // pickupFragment.newInstance(getApplicationContext(), dataList);
        pickupFragment.setArguments(bundleBroad);
        transactionBroad.commit();


        if(!(dataList.getStatus().equals("Runner Assigned"))){
            Bundle bundleBroad1 = new Bundle();
            dropFragment.setArguments(bundleBroad);
            FragmentManager managerBroad1 = getFragmentManager();
            FragmentTransaction transactionBroad1 = managerBroad.beginTransaction();
            transactionBroad.replace(R.id.drop_map, dropFragment, null);
            bundleBroad.putParcelable("dataList",dataList);
            pickupFragment.setArguments(bundleBroad);
            transactionBroad1.commit();
            //dropFragment.newInstance(getApplicationContext(), dataList);
        }



        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

    public void getPickupdata()
    {


        RequestQueue queue = null;

        queue = Volley.newRequestQueue(this);
        progressBar.setVisibility(View.VISIBLE);
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
                                     latLng = new LatLng(Double.parseDouble(startPickupLat.trim()),Double.parseDouble(StartPickupLng.trim()));
                                    if (!pickupOnlyDataList.contains(latLng)){
                                        pickupOnlyDataList.add(latLng);
                                    }
                                }

                                }
                     if(pickupOnlyDataList.size()>0) {
                         progressBar.setVisibility(View.GONE);
                      //   pickupFragment.newInstance(pickupOnlyDataList, getApplicationContext(), dataList);

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
                      //  pickupFragment.newInstance(pickupOnlyDataList,getApplicationContext(), dataList);
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

        String token =generateHash(dataList.getId());
        URL ="https://letsservicetech.in/appointmentWiseRunnerTrack/"+dataList.getId()+"/start/startDrop/dropEnd/"+token;



        JsonArrayRequest jsObjRequest = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println(response);
                        String responsemessage = null;
                        progressBar.setVisibility(View.GONE);

                        String typeOfServ = null;
                 try {
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
                      //   dropFragment.newInstance(dropOnlyDataList,getApplicationContext(), dataList);
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
                  //      dropFragment.newInstance(dropOnlyDataList,getApplicationContext(), dataList);
                    }
                });

        queue.add(jsObjRequest);


    }
}
