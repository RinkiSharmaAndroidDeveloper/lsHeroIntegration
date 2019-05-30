package io.github.ashik619.comexampleandroidrinkimylibraryproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dilip on 3/4/19.
 */

public class LetsServiceHeroIntegration {

    String vinNumber,bookingSlot,bookingDate,googleApi;
    Context context;
    ProgressDialog progressBar;
    public LetsServiceHeroIntegration(Context context, String vinNumber) {
            this.context = context;
            this.vinNumber = vinNumber;
            this.googleApi = googleApi;
            Intent i = new Intent(context.getApplicationContext(), LetsServiceHeroIntegrationActivity.class);
            i.putExtra("id",vinNumber);

            context.getApplicationContext().startActivity(i);
    }

    public LetsServiceHeroIntegration() {

    }

    public void LetsServiceHeroCreateAppointment(Context context,String vinNumber, String bookingDate,String bookingSlot) {
            this.context = context;
            this.vinNumber = vinNumber;
            this.bookingDate = bookingDate;
            this.bookingSlot = bookingSlot;
            progressBar =new ProgressDialog(context,R.style.AppCompatAlertDialogStyle);
        createAppintment(vinNumber,bookingDate,bookingSlot);
    }


    private void createAppintment(String vinNumber, String bookingDate,String bookingSlot) {
        RequestQueue queue = null;

        queue = Volley.newRequestQueue(context);
        progressBar.setCancelable(false);
        progressBar.setMessage("Creating appointment...");
        progressBar.show();

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
                        progressBar.hide();
                        Log.e("Response", response.toString());
                        String responsemessage = null;

                        try {
                            String success = response.getString("success");
                            String message = response.getString("message");
                            if (success.equals("true")) {
                                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
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
                        progressBar.hide();
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
