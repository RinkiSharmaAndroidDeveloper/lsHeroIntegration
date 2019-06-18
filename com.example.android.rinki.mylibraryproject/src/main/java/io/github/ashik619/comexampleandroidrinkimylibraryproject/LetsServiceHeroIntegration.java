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
        Intent i = new Intent(context.getApplicationContext(), StausActivity.class);
        i.putExtra("id",vinNumber);
        i.putExtra("bookingDate",bookingDate);
        i.putExtra("bookingSlot",bookingSlot);
        context.getApplicationContext().startActivity(i);

    }


}
