package io.github.ashik619.comexampleandroidrinkimylibraryproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;


public class RssTestActivity extends AppCompatActivity {



    TextView mobile,name;
    TextView address;
    TextView PickupArea;

    TextView appoinmentStatus;

    TextView dealer_name;


    TextView dealerCode;

    TextView dealerCityState;

    TextView bikeBookedModel;

    TextView appointment_date;


    TextView exShowroomPrice;

    TextView bookingAmount;
String id;

    ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_test);
        name=findViewById(R.id.name);
        mobile=findViewById(R.id.mobile);
        address=findViewById(R.id.address);
        PickupArea=findViewById(R.id.pickup_area);
        appoinmentStatus=findViewById(R.id.appoinment_status);
        dealer_name=findViewById(R.id.dealer_name);
        dealerCityState=findViewById(R.id.dealer_city_state);
        bikeBookedModel=findViewById(R.id.model);
        dealerCode=findViewById(R.id.dealer_code);
        appointment_date=findViewById(R.id.appointment_date);
        exShowroomPrice=findViewById(R.id.exShowroomPrice);
        appointment_date=findViewById(R.id.appointment_date);
        bookingAmount=findViewById(R.id.bookingAmount);
        progress=findViewById(R.id.progress);
       Intent intent =getIntent();
      if(intent!=null){
            id=intent.getStringExtra("id");
                    getAuthenticateLogin(id);
        }



    }


    public void getAuthenticateLogin(String id)
    {

        RequestQueue queue = null;

        queue = Volley.newRequestQueue(this);
        progress.setVisibility(View.VISIBLE);
//social_id, identity_id, social_profileid
      /*  String URL = "http://letsservicetech.co.in/lsVehicle/newVehicleEnquiryDetailsForRunner/"+"71"+"/38/38/2023934918896593";
        ///lsVehicle/newVehicleEnquirycustomerDetailsForRunner/71/38/38/2023934918896593*/

        String URL = "http://stagging.us-west-2.elasticbeanstalk.com/lsVehicle/newVehicleEnquirycustomerDetailsForRunner/"+id+"/38/38/2023934918896593";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        progress.setVisibility(View.GONE);
                        Log.e("Response", response.toString());
                        String responsemessage = null;
                        try {
                            String resposne_message = response.getString("success");
                          //  String res = response.getString("message");
                            JSONArray jsonArray =response.getJSONArray("customer_details");
                       /*     {"vdid":56,"customerName":"Manish Kumar","mobile":"9664383698","address":"229, badodiya basti, station road, Jaipur 302006","hgpMartOrderNumber":null,"hcOrderNumber":null,"orderDate":null,"orderTime":null,"skuCode":null,"exShowroomPrice":null,"bookingAmount":null,"bikeModel":"HF Deluxe","bikeColor":null,"showroomPrice":54000,"insuranceAmount":3480,"rtoAmount":9870,"accessoryAmount":1250,"message":null,"dealerCode":"KA010005","dealerName":"Akar Automobile","dealerState":"KARNATAKA","dealerCity":"BANGALORE","DocumentAndCash":{"adid":15,"runnerId":38,"documentId":1,"pickAddress":"Hsr sector 7","pickLocality":"Hsr sector 7","apptStatus":"Document Collection Completed","createdDate":"2019-04-02 15:43:07","runnerName":"Aditya","runnerMobile":"7406557772","appointmentDate":"03-Apr-2019","appointmentTime":"12:00 PM","documentName":"Document and Cash","status":"true"},"Delivery":{},"dcStatus":"true","deliveryStatus":"false"}]*/

                            if(resposne_message.equals("True")) {
                           JSONObject jsonObject =jsonArray.getJSONObject(0);
                                String customerName1 = jsonObject.getString("customerName");
                                String mobile1= jsonObject.getString("mobile");
                                String model = jsonObject.getString("bikeModel");
                                String pickAddress1 = jsonObject.getString("address");
                             //   String pickLocality1 = jsonObject.getString("pickLocality");
                                String dealerCode1 = jsonObject.getString("dealerCode");
                                String dealerName1 = jsonObject.getString("dealerName");
                                String dealerState1 = jsonObject.getString("dealerState");
                                String dealerCity1 = jsonObject.getString("dealerCity");
                                String bikeColor1 = jsonObject.getString("bikeColor");
                                String hcOrderNumber1 = jsonObject.getString("hcOrderNumber");
                                String hgpMartOrderNumber1 = jsonObject.getString("hgpMartOrderNumber");
                                String exShowroomPrice1 = jsonObject.getString("exShowroomPrice");
                                String bookingAmount1 = jsonObject.getString("bookingAmount");
                                String showroomPrice1 = jsonObject.getString("showroomPrice");
                                String appointmentDate1 = jsonObject.getString("orderDate");
                                String appointmentTime1 = jsonObject.getString("orderTime");
                                mobile.setText(mobile1);
                                name.setText(customerName1);
                                address.setText(pickAddress1);
                                PickupArea.setText(pickAddress1);
                                appoinmentStatus.setText(hcOrderNumber1);
                                dealer_name.setText(dealerName1);
                                dealerCode.setText(dealerCode1);
                                dealer_name.setText(dealerName1);
                                dealerCityState.setText(dealerState1+","+dealerCity1);
                                bikeBookedModel.setText(model);
                                appointment_date.setText(appointmentDate1+", "+appointmentTime1);
                                exShowroomPrice.setText(showroomPrice1);
                                bookingAmount.setText(bookingAmount1);



                            }else{
                                Toast.makeText(RssTestActivity.this, "Success", Toast.LENGTH_SHORT).show();
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
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("headerToken", "355007973");
                return params;
            }
        };
        queue.add(jsObjRequest);


    }
}
