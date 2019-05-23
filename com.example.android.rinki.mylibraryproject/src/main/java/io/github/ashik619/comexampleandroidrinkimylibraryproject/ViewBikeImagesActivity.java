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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewBikeImagesActivity extends Activity {
TextView typeOfSerice,fuel,damage,odometer,reciept,customervoice;
ImageView bike1,bike2,bike3,bike4,bike5,bike6,bike7,rcCopy,insuranceCopy,finalbill;
ProgressBar progressBar;
String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bike_images);
        typeOfSerice=findViewById(R.id.paid_txt);
        fuel=findViewById(R.id.fuel_txt);
        damage=findViewById(R.id.damage_txt);
        odometer=findViewById(R.id.odometer_txt);
        reciept=findViewById(R.id.reciept_txt);
        customervoice=findViewById(R.id.customer_txt);
        bike1=(ImageView)findViewById(R.id.bike1_img);
        bike2=findViewById(R.id.bike2_img);
        bike3=findViewById(R.id.bike3_img);
        bike4=findViewById(R.id.bike4_img);
        bike5=findViewById(R.id.bike5_img);
        bike6=findViewById(R.id.bike6_img);
        bike7=findViewById(R.id.bike7_img);
        rcCopy=findViewById(R.id.rc_copy_img);
        insuranceCopy=findViewById(R.id.insurance_img);
        finalbill=findViewById(R.id.final_bill_img);
        progressBar=findViewById(R.id.progress);
        Intent intent =getIntent();
        if(intent!=null){
            id=intent.getStringExtra("id");
            getData();
        }

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
    public void getData()
    {

        RequestQueue queue = null;

        queue = Volley.newRequestQueue(this);
        progressBar.setVisibility(View.VISIBLE);
     String token= generateHash(id);
        //String URL = Utils.Base_url+mobile;getAssigedAppointmentDetail/:aaptId/:token
        String URL ="http://letsservicetech.in/getAssigedAppointmentDetail/"+id+"/"+token;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        progressBar.setVisibility(View.GONE);
                        Log.e("Response", response.toString());
                        String responsemessage = null;


                        String typeOfServ = null;
                        try {
                            typeOfServ = response.getString("typeOfService");
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
                            typeOfSerice.setText(typeOfServ);
                            fuel.setText(fuel1+"/5");
                            odometer.setText(odometer1);
                            damage.setText(damage1);
                            reciept.setText(reciept1);
                            customervoice.setText(customer_voice);
                            Picasso.with(ViewBikeImagesActivity.this).load(bike11).error(R.drawable.not_found_image).into(bike1);
                            Picasso.with(ViewBikeImagesActivity.this).load(bike21).error(R.drawable.not_found_image).into(bike2);
                            Picasso.with(ViewBikeImagesActivity.this).load(bike31).error(R.drawable.not_found_image).into(bike3);

                            Picasso.with(ViewBikeImagesActivity.this).load(bike41).error(R.drawable.not_found_image).into(bike4);
                            Picasso.with(ViewBikeImagesActivity.this).load(bike51).error(R.drawable.not_found_image).into(bike5);
                            Picasso.with(ViewBikeImagesActivity.this).load(bike61).error(R.drawable.not_found_image).into(bike6);
                            Picasso.with(ViewBikeImagesActivity.this).load(bike71).error(R.drawable.not_found_image).into(bike7);
                            Picasso.with(ViewBikeImagesActivity.this).load(finalBillImg).error(R.drawable.not_found_image).into(finalbill);
                            Picasso.with(ViewBikeImagesActivity.this).load(insurance_img).error(R.drawable.not_found_image).into(insuranceCopy);
                            Picasso.with(ViewBikeImagesActivity.this).load(rc_copyImg).error(R.drawable.not_found_image).into(rcCopy);

                            Toast.makeText(getApplicationContext(), "Details found successfully", Toast.LENGTH_LONG).show();


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


    }
}
