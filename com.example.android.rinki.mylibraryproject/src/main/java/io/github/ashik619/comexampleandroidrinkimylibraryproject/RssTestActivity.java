package io.github.ashik619.comexampleandroidrinkimylibraryproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RssTestActivity extends AppCompatActivity {




String id;
    RecyclerView recyclerView;
    ProgressBar progress;
    List<DataList> listData;
    DataListAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_test);

        progress=findViewById(R.id.progress);
       Intent intent =getIntent();
      if(intent!=null){
            id=intent.getStringExtra("id");
                    getAuthenticateLogin(id);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        listData=new ArrayList<>();
        mAdapter = new DataListAdapter(listData);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(mAdapter);


    }


    public void getAuthenticateLogin(final String mobile)
    {

        RequestQueue queue = null;

        queue = Volley.newRequestQueue(this);
        progress.setVisibility(View.VISIBLE);
        String URL = Utils.Base_url+mobile;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        progress.setVisibility(View.GONE);
                        Log.e("Response", response.toString());
                        String responsemessage = null;
                        try {
                            String resposne_message = response.getString("sucess");
                            JSONArray jsonArray =response.getJSONArray("customer_details");


                            if(resposne_message.equals("true")) {
                                for (int i=0;i<jsonArray.length();i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String customerName1 = jsonObject.getString("username");
                                    String mobile1 = jsonObject.getString("user_mobile");
                                    String model = jsonObject.getString("bikeModel");
                                    String pickAddress1 = jsonObject.getString("pickAddress");
                                    String bikeNo = jsonObject.getString("bikeNo");
                                    String locality = jsonObject.getString("locality");
                                    String typeOfService = jsonObject.getString("typeOfService");
                                    String status = jsonObject.getString("status");
                                    String remarks = jsonObject.getString("remarks");
                                    String finalQuotation = jsonObject.getString("final_quotation");
                                    String lsAmount = jsonObject.getString("lsAmount");

                                    DataList dataList =new DataList(customerName1,mobile1,model,pickAddress1,bikeNo,locality,typeOfService,status,remarks,finalQuotation,lsAmount);
                                    listData.add(dataList);

                                }
mAdapter.notifyDataSetChanged();

                            }else{
                                Toast.makeText(RssTestActivity.this, "Detail fetch successfully", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.setVisibility(View.GONE);
                    }
                });

        queue.add(jsObjRequest);


    }
}
