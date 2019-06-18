package io.github.ashik619.comexampleandroidrinkimylibraryproject;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StausActivity extends Activity {
ImageView apptImage,backIcon;
ProgressBar progressBar;
TextView textView;
String id,bookingDate,bookingSlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staus);

        apptImage =findViewById(R.id.appt_icon);
        backIcon =findViewById(R.id.back_icon);
        progressBar =findViewById(R.id.progress);
        textView =findViewById(R.id.message);
        Intent intent =getIntent();
        if(intent!=null){
            id=intent.getStringExtra("id");
            bookingDate=intent.getStringExtra("bookingDate");
            bookingSlot=intent.getStringExtra("bookingSlot");

            createAppintment(id,bookingDate,bookingSlot);
        }
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void createAppintment(String vinNumber, String bookingDate,String bookingSlot) {
        RequestQueue queue = null;

        queue = Volley.newRequestQueue(this);

        progressBar.setVisibility(View.VISIBLE);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("chassisNo",vinNumber);
            jsonObject.put("bookingDate",bookingDate);
            jsonObject.put("slotValue",bookingSlot);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String token = generateHash("3");
        final String headTokn =generateHeader("3");
        String URL = Utils.Base_url + Utils.createAppt + "3/" + token;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject,
                new Response.Listener<JSONObject>()
                {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        progressBar.setVisibility(View.GONE);
                        Log.e("Response", response.toString());
                        String responsemessage = null;

                        try {
                            String success = response.getString("success");
                            String message = response.getString("message");
                            if (success.equals("true")) {
                                textView.setVisibility(View.VISIBLE);
                                textView.setText(message);
                                apptImage.setVisibility(View.VISIBLE);
                             apptImage.setBackgroundResource(R.drawable.transaction_pass);
                             //   Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            } else {
                                textView.setVisibility(View.VISIBLE);
                             apptImage.setBackgroundResource(R.drawable.transaction_failed);
                                textView.setText(message);
                                apptImage.setVisibility(View.VISIBLE);
                             //   Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            }


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
                Map<String, String> params = new HashMap<String, String>();
                params.put("Header-Token", headTokn);
                return params;
            }
        };
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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
}
