package io.github.ashik619.comexampleandroidrinkimylibraryproject;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LetsServiceHeroIntegrationActivity extends AppCompatActivity implements PaytmPaymentTransactionCallback {
    String id;
    RecyclerView recyclerView;
    ProgressBar progress;
    List<DataList> listData;
    List<AnswersData> answersDataList;
    TextView errorTextView;
    DataListAdapter mAdapter;
    private PaytmPGService Service;
    String orderId;
    String customerId;
    String userId=null,privilegedId=null,aaptId=null;
    boolean isFeedbackFilled=false;
    List<QuestionsData> questionsDataList;
    ImageView imageView;
  Dialog   dialog;
String headerToken;
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
        errorTextView = (TextView) findViewById(R.id.error_message);
        imageView = (ImageView) findViewById(R.id.up_icon);
        listData=new ArrayList<>();
        questionsDataList=new ArrayList<>();
        answersDataList=new ArrayList<>();
       // dialog = new Dialog(this);
        dialog = new Dialog(this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        mAdapter = new DataListAdapter(listData,getApplicationContext(),asyncResult_clickPayTm, viewJobCard,goOnMap );
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(mAdapter);
        isPermissionGranted();
        if (ContextCompat.checkSelfPermission(LetsServiceHeroIntegrationActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LetsServiceHeroIntegrationActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    AsyncResult<DataList> asyncResult_clickPayTm = new AsyncResult<DataList>()  {
        @Override
        public void success(DataList dataList) {

            getCheckSumKey(dataList);
        }
        @Override
        public void error(String error) {

        }
    };
 AsyncResult<DataList> goOnMap  = new AsyncResult<DataList>()  {
        @Override
        public void success(DataList dataList) {

         Intent intent =new Intent(LetsServiceHeroIntegrationActivity.this,MapActivity.class);
         intent.putExtra("dataList",dataList);
            startActivity(intent);
        }
        @Override
        public void error(String error) {

        }
    };

    AsyncResult<String > viewJobCard = new AsyncResult<String>()  {
        @Override
        public void success(String  apptId) {
            Intent i = new Intent(LetsServiceHeroIntegrationActivity.this, ViewBikeImagesActivity.class);
            i.putExtra("id",apptId);
          startActivity(i);
           // getCheckSumKey(dataList);
        }
        @Override
        public void error(String error) {

        }
    };


    public  boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                  //  call_action();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    private void initializePaytmPayment(String checksum,DataList dataList) {

        //getting paytm service
        PaytmPGService Service = PaytmPGService.getProductionService();


        String callbackUrl = Utils.CallBack_Url+orderId;
         userId=dataList.getUserId();
         privilegedId=dataList.getPrivilegeId();
         aaptId=dataList.getId();
        HashMap<String, String> paramMap = new HashMap<String,String>();
        paramMap.put( "MID" , Utils.MidKey);
        // Key in your staging and production MID available in your dashboard
        paramMap.put( "ORDER_ID" ,orderId);
        paramMap.put( "CUST_ID" , customerId);
        paramMap.put( "MOBILE_NO" , dataList.getMobileNo());
       // paramMap.put( "EMAIL" , dataList.getEmail());
        paramMap.put( "CHANNEL_ID" , "WEB");
        paramMap.put( "TXN_AMOUNT" , "1");
        paramMap.put( "WEBSITE" , "LetSerWEB");

        // This is the staging value. Production value is available in your dashboard
        paramMap.put( "INDUSTRY_TYPE_ID" , "Retail109");
        // This is the staging value. Production value is available in your dashboard
        paramMap.put( "CALLBACK_URL", callbackUrl);
        paramMap.put( "CHECKSUMHASH" , checksum);
        PaytmOrder Order = new PaytmOrder(paramMap);
        //intializing the paytm service
        Service.initialize(Order, null);

        //finally starting the payment transaction
        Service.startPaymentTransaction(this, true, true, this);

    }

        private String generateOrderId(String apptId){
           // String[] lsApptId = apptId.split("-");
            String timeStamp = String.valueOf(System.currentTimeMillis());
            String _orderId = "TRXN"+apptId+timeStamp.substring(4,timeStamp.length());
//        Log.e("ORDER",_orderId);
            return _orderId;
        }


    public void getAuthenticateLogin(String id)
    {

        RequestQueue queue = null;

        queue = Volley.newRequestQueue(this);
        progress.setVisibility(View.VISIBLE);
        if(listData!=null&&listData.size()>0)
        {
            listData.clear();
        }
        headerToken =generateHeader("503");
        //String URL = Utils.Base_url+mobile;
     String URL =Utils.Base_url+Utils.Fetch_List+id+"/503/14229915560960046";
  /*      String URL ="http://stagging.us-west-2.elasticbeanstalk.com/"+Utils.Fetch_List+"MERTYUI123456789"+"/503/14229915560960046";*/

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
                            if(resposne_message.equals("true")) {
                                JSONArray jsonArray =response.getJSONArray("appointment details");
                                for (int i=0;i<jsonArray.length();i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String customerName1 = jsonObject.getString("userName");
                                    String email = jsonObject.getString("useremail");
                                    String user_picture = jsonObject.getString("user_picture");
                                    String bikeBrandname = jsonObject.getString("bikeBrandname");
                                    String id = String.valueOf(jsonObject.getInt("_id"));
                                    String userId = String.valueOf(jsonObject.getInt("userId"));
                                    String createdBy_Id = String.valueOf(jsonObject.getInt("createdBy_Id"));
                                    String organicId = String.valueOf(jsonObject.getInt("organicId"));
                                    String pcId = String.valueOf(jsonObject.getInt("pcId"));
                                    String bikeBrand = jsonObject.getString("bikeBrand");
                                    String bikeModel = jsonObject.getString("bikeModel");
                                    String bikeNo = jsonObject.getString("bikeNo");
                                    String pickAddress = jsonObject.getString("pickAddress");
                                    String dropAddress = jsonObject.getString("dropAddress");
                                    String locality = jsonObject.getString("locality");
                                    String dateTime = jsonObject.getString("dateTime");
                                    String serviceTypeId = jsonObject.getString("serviceTypeId");
                                    String assistanceTypeId = jsonObject.getString("assistanceTypeId");
                                    String assistanceType= jsonObject.getString("assistanceType");
                                    String serviceCenter= jsonObject.getString("serviceCenter");
                                    String typeOfService = jsonObject.getString("typeOfService");
                                    String customerType = jsonObject.getString("customerType");
                                    String status = jsonObject.getString("status");
                                    String ACRId = jsonObject.getString("ACRId");
                                    String cancelRemarks = jsonObject.getString("cancelRemarks");
                                    String remarks = jsonObject.getString("remarks");
                                    String lsRemarks = jsonObject.getString("lsRemarks");
                                    String scStatus = jsonObject.getString("scStatus");
                                    String final_quotation = jsonObject.getString("final_quotation");
                                    String discounted_amount = jsonObject.getString("discounted_amount");
                                    String assistanceAmount = jsonObject.getString("assistanceAmount");
                                    String lsAmount = jsonObject.getString("lsAmount");
                                    String chassisNo = jsonObject.getString("chassisNo");
                                    String engineNO = jsonObject.getString("engineNO");
                                    String SPONO = jsonObject.getString("SPONO");
                                    String dealerCode = jsonObject.getString("dealerCode");
                                    String availHMSICredit = jsonObject.getString("availHMSICredit");
                                    String typeOfPD = jsonObject.getString("typeOfPD");
                                    String amcUser = jsonObject.getString("amcUser");
                                    String privilegeId = String.valueOf(jsonObject.getInt("privilegeId"));
                                    String cityId = String.valueOf(jsonObject.getInt("cityId"));
                                    String couponId =jsonObject.getString("couponId");
                                    String teleCallerTCID = String.valueOf(jsonObject.getInt("teleCallerTCID"));
                                    String CREID = jsonObject.getString("CREID");
                                    String activeStatus = jsonObject.getString("activeStatus");
                                    String rating = String.valueOf(jsonObject.getInt("rating"));
                                    String runnerId = String.valueOf(jsonObject.getInt("runnerId"));
                                    String runnerName = jsonObject.getString("runnername");
                                    String runnerPicture = jsonObject.getString("runnerPicture");
                                    String runnerMobile = String.valueOf(jsonObject.getInt("runnermobile"));
                                    String bookingNo = jsonObject.getString("bookingNo");
                                    String mobile = jsonObject.getString("user_mobile");
                                    String paymentStatus = jsonObject.getString("paymentStatus");

                                    DataList dataList =new  DataList(customerName1,email,user_picture,bikeBrandname, id, userId, createdBy_Id,  organicId,  pcId,  bikeBrand,  bikeModel,  bikeNo,  pickAddress,  dropAddress,  locality,  dateTime,  serviceTypeId,  assistanceTypeId,assistanceType, serviceCenter, typeOfService,  customerType,  status,  ACRId,  cancelRemarks,  lsRemarks,  scStatus,  final_quotation,  discounted_amount,  assistanceAmount,  lsAmount,  chassisNo,  engineNO,  SPONO,  dealerCode,  availHMSICredit,  typeOfPD,  amcUser, privilegeId, cityId,  couponId,  teleCallerTCID,  CREID,  activeStatus,rating,runnerId,  runnerName,runnerPicture,runnerMobile,bookingNo,mobile,paymentStatus);
                                    listData.add(dataList);

                                }
                                showErrorText(listData);
                                mAdapter.notifyDataSetChanged();

                                Toast.makeText(getApplicationContext(), "Details found successfully", Toast.LENGTH_LONG).show();
                            }else{
                                showErrorText(listData);
                                Toast.makeText(getApplicationContext(), "Details not found", Toast.LENGTH_LONG).show();
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
                        progress.setVisibility(View.GONE);
                    }
                }
        ) /*{
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Header-Token", headerToken);
                return params;
            }
        }*/;

        queue.add(jsObjRequest);


    }
    public void showErrorText(List<DataList> listData)
    {
        if(listData!=null && listData.size()>0)
        {

            errorTextView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }else{
            errorTextView.setVisibility(View.VISIBLE);
            errorTextView.setText("There is no data for the VIN "+id+"/ Invalid VIN.");
            recyclerView.setVisibility(View.GONE);
        }
        mAdapter.notifyDataSetChanged();
    }
    public void getCheckSumKey(final DataList dataList)
    {
        orderId =generateOrderId(dataList.getId());
        customerId =generateOrderId(dataList.getUserId());


        String callbackUrl = Utils.CallBack_Url+orderId;
        RequestQueue queue = null;

        queue = Volley.newRequestQueue(this);
        progress.setVisibility(View.VISIBLE);



    String token =generateHash(dataList.getUserId());
        String URL = Utils.getCheckSum_Url+dataList.getUserId()+"/"+token;
        JSONObject jsonObject =new JSONObject();
        try {
            jsonObject.put("ORDER_ID",orderId);
            jsonObject.put("CUST_ID",customerId);
            jsonObject.put("INDUSTRY_TYPE_ID","Retail109");
            jsonObject.put("CHANNEL_ID","WEB");
            jsonObject.put("WEBSITE","LetSerWEB");
        //   jsonObject.put("TXN_AMOUNT",dataList.getFinal_quotation()+dataList.getLsAmount());
            jsonObject.put("TXN_AMOUNT","1");
            jsonObject.put("CALLBACK_URL",callbackUrl);
            jsonObject.put("MID","LetSer76845485640281");

         jsonObject.put("MOBILE_NO",dataList.getMobileNo());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        progress.setVisibility(View.GONE);
                        Log.e("Response", response.toString());
                        String responsemessage = null;
                        try {
                            String checksum = response.getString("checksum");
                            if(checksum!=null) {
                                initializePaytmPayment(checksum,dataList);
                            }else{

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


    @Override
    public void onTransactionResponse(Bundle inResponse) {
     //   Toast.makeText(this, inResponse.toString(), Toast.LENGTH_LONG).show();
        parsePaytmResponse(inResponse);

    }
    private void parsePaytmResponse(Bundle bundle){

        String STATUS = bundle.getString("STATUS");

        if(STATUS.equals("TXN_SUCCESS")) {

            String MID = bundle.getString("MID");

            String ORDERID = bundle.getString("ORDERID");

            String TXNAMOUNT = bundle.getString("TXNAMOUNT");

            String CURRENCY = bundle.getString("CURRENCY");

            String TXNID = bundle.getString("TXNID");

            String BANKTXNID = bundle.getString("BANKTXNID");

            String RESPCODE = bundle.getString("RESPCODE");

            String RESPMSG = bundle.getString("RESPMSG");

            String TXNDATE = bundle.getString("TXNDATE");

            String GATEWAYNAME = bundle.getString("GATEWAYNAME");

            String BANKNAME = bundle.getString("BANKNAME");

            String PAYMENTMODE = bundle.getString("PAYMENTMODE");

            String CHECKSUMHASH = bundle.getString("CHECKSUMHASH");
            JSONObject jsonObject =new JSONObject();
            try {
                jsonObject.put("STATUS",STATUS);
                jsonObject.put("MID",MID);
                jsonObject.put("ORDERID",ORDERID);
                jsonObject.put("TXNAMOUNT",TXNAMOUNT);
                jsonObject.put("CURRENCY",CURRENCY);
                jsonObject.put("TXNID",TXNID);
                //jsonObject.put("TXN_AMOUNT",dataList.getFinal_quotation());
                jsonObject.put("BANKTXNID",BANKTXNID);
                jsonObject.put("RESPCODE",RESPCODE);
                jsonObject.put("RESPMSG",RESPMSG);

                jsonObject.put("TXNDATE",TXNDATE);
                jsonObject.put("GATEWAYNAME",GATEWAYNAME);
                jsonObject.put("BANKNAME",BANKNAME);
                jsonObject.put("PAYMENTMODE",PAYMENTMODE);
                jsonObject.put("CHECKSUMHASH",CHECKSUMHASH);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Toast.makeText(LetsServiceHeroIntegrationActivity.this, "Transaction has been successful", Toast.LENGTH_LONG).show();
            updatePaymentStatus(jsonObject,aaptId,userId);

        }else {

            String RESPMSG = bundle.getString("RESPMSG");
            Toast.makeText(this, RESPMSG, Toast.LENGTH_LONG).show();

        }

    }
    private void updatePaymentStatus(JSONObject jsonObject,String id,String custId){
        RequestQueue queue = null;

        queue = Volley.newRequestQueue(this);
        progress.setVisibility(View.VISIBLE);

        String URL = Utils.update_Payment_Status_Url+id+"&customer_order_id="+custId;
        Log.e("Url", URL);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        progress.setVisibility(View.GONE);
                        try {
                            String paymentMessage = response.getString("message");
                            if(paymentMessage.equals("Updated Succesfully")){
                                getFeedBackQuestion();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("Response", response.toString());
                        String responsemessage = null;

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

    private void getFeedBackQuestion(){
        RequestQueue queue = null;

        queue = Volley.newRequestQueue(this);
        progress.setVisibility(View.VISIBLE);

        String token =generateHash("3");
        final String headTokn =generateHash("3");
        String URL = Utils.Base_url+Utils.getFeedbackQuestions+"3/"+token;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        progress.setVisibility(View.GONE);
                        Log.e("Response", response.toString());
                        String responsemessage = null;

                        try {
                            String success = response.getString("success");

                            if(success.equals("true")) {
                                JSONArray questions = response.getJSONArray("message");
                           for(int i=0;i<questions.length();i++){
                               JSONObject jsonObject =questions.getJSONObject(i);
                               String question =jsonObject.getString("que");
                               QuestionsData  questionsData=new QuestionsData(question);
                               questionsDataList.add(questionsData);
                           }
                                showFeedback(questionsDataList,getApplicationContext());
                            }else{

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
                        progress.setVisibility(View.GONE);
                    }
                }
        ) /*{
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Header-Token", headTokn);
                return params;
            }
        }*/;

        queue.add(jsObjRequest);
    }


     private boolean submitFeedBackQuestion(List<AnswersData> answersDataList) {
         RequestQueue queue = null;

         queue = Volley.newRequestQueue(this);
         progress.setVisibility(View.VISIBLE);

         JSONArray array = new JSONArray();

         for (int i = 0; i < answersDataList.size(); i++) {
             JSONObject obj = new JSONObject();
             try {
                 obj.put("question", answersDataList.get(i).getQuestion());
                 obj.put("rating", answersDataList.get(i).getRating());
             } catch (JSONException e) {
                 e.printStackTrace();
             }
             array.put(obj);
         }
         JSONObject jsonObject = new JSONObject();
         try {
             jsonObject.put("userId", userId);
             jsonObject.put("apptId", aaptId);
             jsonObject.put("privilageId", privilegedId);
             jsonObject.put("feedback", array);
         } catch (JSONException e) {
             e.printStackTrace();
         }


         String token = generateHash("3");
         final String headTokn =generateHeader("3");
         String URL = Utils.Base_url + Utils.sendFeedBack + "3/" + token;

         JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject,
                 new Response.Listener<JSONObject>() {
                     @Override
                     public void onResponse(JSONObject response) {
                         System.out.println(response);
                         progress.setVisibility(View.GONE);
                         Log.e("Response", response.toString());
                         String responsemessage = null;

                         try {
                             String success = response.getString("success");
                             String message = response.getString("message");
                             if (success.equals("true")) {
                                 dialog.dismiss();
                                 getAuthenticateLogin(id);
                                 Toast.makeText(LetsServiceHeroIntegrationActivity.this, message, Toast.LENGTH_LONG).show();
                             } else {
                                 isFeedbackFilled = false;
                                 Toast.makeText(LetsServiceHeroIntegrationActivity.this, message, Toast.LENGTH_LONG).show();
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
                         progress.setVisibility(View.GONE);
                     }
                 }
         ) /*{
             @Override
             public Map<String, String> getHeaders() throws AuthFailureError {
                 Map<String, String>  params = new HashMap<String, String>();
                 params.put("Header-Token", headTokn);
                 return params;
             }
         }*/;
    /*



         */
         queue.add(jsObjRequest);
         return isFeedbackFilled;
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
     //LsSalesHeader

   /* @Override
    public void onStart()
    {
        super.onStart();

        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
*/
    private void showFeedback(final List<QuestionsData> questionsData, Context context){
RecyclerView recyclerViewDialog;
FeedbackAdapter feedbackAdapter;
    //final Dialog dialog = new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
    dialog.setContentView(R.layout.feedback_dialog);
    dialog.setTitle("Submit Feedback");
        recyclerViewDialog=(RecyclerView)dialog.findViewById(R.id.recycler_view_dialog);
        Button dialogButton = (Button) dialog.findViewById(R.id.submit);
        feedbackAdapter = new FeedbackAdapter(questionsData,context,asyncResult_clickPayTm1);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewDialog.setLayoutManager(mLayoutManager);

        recyclerViewDialog.setAdapter(feedbackAdapter);


        // if button is clicked, close the custom dialog
    dialogButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            if(answersDataList!=null&&answersDataList.size()==questionsData.size())
            {


                isFeedbackFilled =  submitFeedBackQuestion(answersDataList);

            }else{
                Toast.makeText(getApplicationContext(), "Please fill all the questions", Toast.LENGTH_LONG).show();

            }


        }
    });

    dialog.show();
}
    AsyncResult<AnswersData> asyncResult_clickPayTm1 = new AsyncResult<AnswersData>()  {
        @Override
        public void success(AnswersData dataList) {
            if(answersDataList.size()>0)
            {
                for(AnswersData d:answersDataList)
                {
                    if(d.getQuestion().equals(dataList.getQuestion()))
                    {
                        answersDataList.remove(d);
                        answersDataList.add(dataList);
                     return;
                    }
                }
            }
            answersDataList.add(dataList);

        }
        @Override
        public void error(String error) {

        }
    };


    @Override
    public void networkNotAvailable() {
        Toast.makeText(this, "Network Error", Toast.LENGTH_LONG).show();
    }

    @Override
    public void clientAuthenticationFailed(String inErrorMessage) {
        Toast.makeText(this, inErrorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void someUIErrorOccurred(String inErrorMessage) {
        Toast.makeText(this, inErrorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
        Toast.makeText(this, inErrorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressedCancelTransaction() {

    }

    @Override
    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
        Toast.makeText(this, inErrorMessage, Toast.LENGTH_LONG).show();
    }
}
