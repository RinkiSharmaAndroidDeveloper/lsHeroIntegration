package io.github.ashik619.comexampleandroidrinkimylibraryproject;

import android.Manifest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;


import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by dilip on 5/4/19.
 */

public class DataListAdapter extends RecyclerView.Adapter<DataListAdapter.MyViewHolder> {

    private List<DataList> moviesList;
    Context context;
    AsyncResult<DataList> asyncResult_clickPayTm;
    AsyncResult<DataList> feedBackAsyncTask;
    AsyncResult<String> viewJobCard;
    int steps = 3;
    AsyncResult<DataList> goOnMap;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Username, userRating, bookingNumber, vinNumber, currentStatus, bikeBookedModel, bikeNumber, appointCreatated1, runnerAssigned2, bikePickupedup3, textView4, textView5, textView6, date, lsAmount;
        Button otpBtn, PaymentButton,feedback;
        CircleImageView userImg, PhoneBtn;
        LinearLayout spinner, spiiner1;
        LinearLayout linearLayout;
      LinearLayout google_map_image;
      ImageView Dot1,Dot2,Dot3,Dot4,Dot5,Dot6;
      View viewDot1,viewDot2,viewDot3,viewDot4;
        public MyViewHolder(View view) {
            super(view);
            Username = (TextView) view.findViewById(R.id.name);
            userRating = (TextView) view.findViewById(R.id.rating);
            bookingNumber = (TextView) view.findViewById(R.id.booking_txt);
            vinNumber = (TextView) view.findViewById(R.id.registration_number);
            lsAmount = (TextView) view.findViewById(R.id.ls_amount);
            currentStatus = (TextView) view.findViewById(R.id.current_status_number);
            otpBtn = (Button) view.findViewById(R.id.otp_txt);
            google_map_image = (LinearLayout) view.findViewById(R.id.google_map);
            PaymentButton = (Button) view.findViewById(R.id.payment_btn);
            feedback = (Button) view.findViewById(R.id.feedBackbutton);
            userImg = (CircleImageView) view.findViewById(R.id.profile_image);
            PhoneBtn = (CircleImageView) view.findViewById(R.id.bs_call_customer);
            spinner = (LinearLayout) view.findViewById(R.id.breadcrumbs);
            spiiner1 = (LinearLayout) view.findViewById(R.id.breadcrumbs1);
            linearLayout = (LinearLayout) view.findViewById(R.id.view_photos);

            bikeBookedModel = (TextView) view.findViewById(R.id.model_txt);
            date = (TextView) view.findViewById(R.id.date);
            bikeNumber = (TextView) view.findViewById(R.id.bike_number_txt);
            appointCreatated1 = (TextView) view.findViewById(R.id.txt1);
            runnerAssigned2 = (TextView) view.findViewById(R.id.txt2);
            bikePickupedup3 = (TextView) view.findViewById(R.id.txt3);
            textView4 = (TextView) view.findViewById(R.id.txt4);
            textView5 = (TextView) view.findViewById(R.id.txt5);
            textView6 = (TextView) view.findViewById(R.id.txt6);
            Dot1 = (ImageView) view.findViewById(R.id.dot1);
            Dot2 = (ImageView) view.findViewById(R.id.dot2);
            Dot3 = (ImageView) view.findViewById(R.id.dot3);
            Dot4 = (ImageView) view.findViewById(R.id.dot4);
            Dot5 = (ImageView) view.findViewById(R.id.dot5);
            Dot6 = (ImageView) view.findViewById(R.id.dot6);

            viewDot1 = (View) view.findViewById(R.id.line1);
            viewDot2 = (View) view.findViewById(R.id.line2);
            viewDot3 = (View) view.findViewById(R.id.line3);
            viewDot4 = (View) view.findViewById(R.id.line4);
        }
    }


    public DataListAdapter(List<DataList> moviesList, Context context, AsyncResult<DataList> asyncResult_clickPayTm, AsyncResult<String> viewJobCard,AsyncResult<DataList> goOnMap,AsyncResult<DataList> feedBackAsyncTask ) {
        this.moviesList = moviesList;
        this.context = context;
        this.asyncResult_clickPayTm = asyncResult_clickPayTm;
        this.viewJobCard = viewJobCard;
        this.goOnMap=goOnMap;
        this.feedBackAsyncTask=feedBackAsyncTask;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_list_data, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final DataList movie = moviesList.get(position);

        if (movie.getRunnerName() != null) {
            holder.Username.setText(movie.getRunnerName());
        }
        holder.feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((movie.getPaymentStatus().equals("paid"))) {
                    feedBackAsyncTask.success(moviesList.get(position));

                   // Toast.makeText(context, "Feedback already exist", Toast.LENGTH_LONG).show();
                }
            }
        });
        holder.PhoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (movie.getRunnerMobile().length() < 10) {
                    Toast.makeText(context, "Invalid Number", Toast.LENGTH_LONG).show();
                    return;
                }
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Intent intentCall = new Intent(Intent.ACTION_CALL);

                intentCall.setData(Uri.parse("tel:" + movie.getRunnerMobile()));
                context.startActivity(intentCall);

            }
        });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewJobCard.success(moviesList.get(position).getId());


            }
        });
        if (!TextUtils.isEmpty(movie.getBookingNo())) {
            holder.bookingNumber.setText(movie.getBookingNo());
        }
        if (movie.getPaymentStatus().equals("paid")) {

            holder.PaymentButton.setVisibility(View.GONE);
            holder.feedback.setVisibility(View.VISIBLE);

        }else{
            if(movie.getFinal_quotation().equals("0")){
                holder.PaymentButton.setBackgroundResource(R.drawable.disable_btn);
            }else{
                holder.PaymentButton.setBackgroundResource(R.drawable.background_btn);
            }

            holder.PaymentButton.setVisibility(View.VISIBLE);
            holder.feedback.setVisibility(View.GONE);
        }

      /*  if (movie.getFeedbackStatus().equals("true")||(moviesList.get(position).getStatus().equals("Appointment Cancelled"))) {
            holder.feedback.setBackgroundResource(R.drawable.disable_btn);

        }else{
            holder.feedback.setBackgroundResource(R.drawable.background_btn);
        }
*/
        holder.PaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (movie.getFinal_quotation().equals("0")) {

                    Toast.makeText(context, "Payment detail is not updated", Toast.LENGTH_LONG).show();
                } else if (movie.getPaymentStatus().equals("paid")) {
                    Toast.makeText(context, "Payment already done", Toast.LENGTH_LONG).show();
                } else {
                    asyncResult_clickPayTm.success(moviesList.get(position));
                }
            }
        });

        holder.google_map_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!moviesList.get(position).getStatus().equals("Appointment Cancelled")&&(!moviesList.get(position).getStatus().equals("Bike Delivered"))) {
                    goOnMap.success(moviesList.get(position));
                } else if(moviesList.get(position).getStatus().equals("Bike Delivered")){
                    Toast.makeText(context, "Appointment has been delivered", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(context, "Appointment Cancelled", Toast.LENGTH_LONG).show();
                }
            }
        });

        if (movie.getChassisNo() != null) {
            holder.vinNumber.setText(movie.getChassisNo());
        }
        if (movie.getLsAmount() != null) {
            holder.lsAmount.setText(movie.getLsAmount() + ".00");
        }

        if (movie.getFinal_quotation() != null) {
            holder.currentStatus.setText(movie.getFinal_quotation() + ".00");
        }
        if (movie.getBikeNo() != null) {
            holder.bikeNumber.setText(movie.getBikeNo());
        }
        if (movie.getDateTime() != null) {
            holder.date.setText(movie.getDateTime());
        }
        if (movie.getBikeBrandname() != null) {
            holder.bikeBookedModel.setText(movie.getBikeBrandname() + " " + movie.getBikeModel());
        }
        if (movie.getRunnerPicture() != null) {
            Picasso.with(context).load(movie.getRunnerPicture()).into(holder.userImg);
        }


        holder.userRating.setText(movie.getRating());


        if (moviesList.get(position).getStatus().equals("Appointment Created")) {
            //holder.spinner.setCurrentStep(1);
  /*  ImageView Dot1,Dot2,Dot3,Dot4,Dot5,Dot6;
    View viewDot1,viewDot2,viewDot3,viewDot4;*/

            holder.appointCreatated1.setVisibility(View.VISIBLE);
            holder.appointCreatated1.setText("Appointment Created");
            holder.spiiner1.setVisibility(View.GONE);
            holder.spinner.setVisibility(View.VISIBLE);
            holder.runnerAssigned2.setVisibility(View.GONE);
            holder.bikePickupedup3.setVisibility(View.GONE);
            holder.textView4.setVisibility(View.GONE);
            holder.textView5.setVisibility(View.GONE);
            holder.textView6.setVisibility(View.GONE);

            holder.Dot1.setBackgroundResource(R.drawable.green_circle);
            holder.Dot2.setBackgroundResource(R.drawable.red_circle);
            holder.Dot3.setBackgroundResource(R.drawable.red_circle);
            holder.Dot4.setBackgroundResource(R.drawable.red_circle);
            holder.Dot5.setBackgroundResource(R.drawable.red_circle);
            holder.Dot6.setBackgroundResource(R.drawable.red_circle);
            holder.viewDot1.setBackgroundColor(context.getResources().getColor(R.color.heroColorPrimary));
            holder.viewDot2.setBackgroundColor(context.getResources().getColor(R.color.heroColorPrimary));
            holder.viewDot3.setBackgroundColor(context.getResources().getColor(R.color.heroColorPrimary));
            holder.viewDot4.setBackgroundColor(context.getResources().getColor(R.color.heroColorPrimary));


        } else if (moviesList.get(position).getStatus().equals("Runner Assigned")) {
            holder.spiiner1.setVisibility(View.GONE);
            holder.spinner.setVisibility(View.VISIBLE);
            //  holder.spinner.setCurrentStep(2);
            holder.appointCreatated1.setText("Appointment Created");
            holder.appointCreatated1.setVisibility(View.VISIBLE);
            holder.runnerAssigned2.setVisibility(View.VISIBLE);
            holder.bikePickupedup3.setVisibility(View.GONE);
            holder.textView4.setVisibility(View.GONE);
            holder.textView5.setVisibility(View.GONE);
            holder.textView6.setVisibility(View.GONE);

            holder.Dot1.setBackgroundResource(R.drawable.green_circle);
            holder.Dot2.setBackgroundResource(R.drawable.green_circle);
            holder.Dot3.setBackgroundResource(R.drawable.red_circle);
            holder.Dot4.setBackgroundResource(R.drawable.red_circle);
            holder.Dot5.setBackgroundResource(R.drawable.red_circle);
            holder.Dot6.setBackgroundResource(R.drawable.red_circle);
            holder.viewDot1.setBackgroundColor(context.getResources().getColor(R.color.greenColor));
            holder.viewDot2.setBackgroundColor(context.getResources().getColor(R.color.heroColorPrimary));
            holder.viewDot3.setBackgroundColor(context.getResources().getColor(R.color.heroColorPrimary));
            holder.viewDot4.setBackgroundColor(context.getResources().getColor(R.color.heroColorPrimary));

        } else if (moviesList.get(position).getStatus().equals("Bike Picked For Service")) {
            holder.spinner.setVisibility(View.VISIBLE);
            holder.spiiner1.setVisibility(View.GONE);
            holder.appointCreatated1.setText("Appointment Created");
            //  holder.spinner.setCurrentStep(3);
            holder.appointCreatated1.setVisibility(View.VISIBLE);
            holder.runnerAssigned2.setVisibility(View.VISIBLE);
            holder.bikePickupedup3.setVisibility(View.VISIBLE);
            holder.textView4.setVisibility(View.GONE);
            holder.textView5.setVisibility(View.GONE);
            holder.textView6.setVisibility(View.GONE);
            holder.Dot1.setBackgroundResource(R.drawable.green_circle);
            holder.Dot2.setBackgroundResource(R.drawable.green_circle);
            holder.Dot3.setBackgroundResource(R.drawable.green_circle);
            holder.Dot4.setBackgroundResource(R.drawable.red_circle);
            holder.Dot5.setBackgroundResource(R.drawable.red_circle);
            holder.Dot6.setBackgroundResource(R.drawable.red_circle);
            holder.viewDot1.setBackgroundColor(context.getResources().getColor(R.color.greenColor));
            holder.viewDot2.setBackgroundColor(context.getResources().getColor(R.color.greenColor));
            holder.viewDot3.setBackgroundColor(context.getResources().getColor(R.color.heroColorPrimary));
            holder.viewDot4.setBackgroundColor(context.getResources().getColor(R.color.heroColorPrimary));
        } else if (moviesList.get(position).getStatus().equals("Bike Service In Progress")) {
            holder.spiiner1.setVisibility(View.VISIBLE);
            holder.spinner.setVisibility(View.VISIBLE);
            // holder.spiiner1.setCurrentStep(1);
            //holder.spinner.setCurrentStep(3);
            holder.appointCreatated1.setVisibility(View.VISIBLE);
            holder.appointCreatated1.setText("Appointment Created");
            holder.runnerAssigned2.setVisibility(View.VISIBLE);
            holder.bikePickupedup3.setVisibility(View.VISIBLE);
            holder.textView4.setVisibility(View.VISIBLE);
            holder.textView5.setVisibility(View.GONE);
            holder.textView6.setVisibility(View.GONE);
            holder.Dot1.setBackgroundResource(R.drawable.green_circle);
            holder.Dot2.setBackgroundResource(R.drawable.green_circle);
            holder.Dot3.setBackgroundResource(R.drawable.green_circle);
            holder.Dot4.setBackgroundResource(R.drawable.green_circle);
            holder.Dot5.setBackgroundResource(R.drawable.red_circle);
            holder.Dot6.setBackgroundResource(R.drawable.red_circle);
            holder.viewDot1.setBackgroundColor(context.getResources().getColor(R.color.greenColor));
            holder.viewDot2.setBackgroundColor(context.getResources().getColor(R.color.greenColor));
            holder.viewDot3.setBackgroundColor(context.getResources().getColor(R.color.heroColorPrimary));
            holder.viewDot4.setBackgroundColor(context.getResources().getColor(R.color.heroColorPrimary));
        } else if (moviesList.get(position).getStatus().equals("Bike Picked For Delivery")) {
            //  holder.spinner.setCurrentStep(3);
            // holder.spiiner1.setCurrentStep(2);
            holder.spiiner1.setVisibility(View.VISIBLE);
            holder.spinner.setVisibility(View.VISIBLE);
            holder.appointCreatated1.setVisibility(View.VISIBLE);
            holder.appointCreatated1.setText("Appointment Created");
            holder.runnerAssigned2.setVisibility(View.VISIBLE);
            holder.bikePickupedup3.setVisibility(View.VISIBLE);
            holder.textView4.setVisibility(View.VISIBLE);
            holder.textView5.setVisibility(View.VISIBLE);
            holder.textView6.setVisibility(View.GONE);
            holder.Dot1.setBackgroundResource(R.drawable.green_circle);
            holder.Dot2.setBackgroundResource(R.drawable.green_circle);
            holder.Dot3.setBackgroundResource(R.drawable.green_circle);
            holder.Dot4.setBackgroundResource(R.drawable.green_circle);
            holder.Dot5.setBackgroundResource(R.drawable.green_circle);
            holder.Dot6.setBackgroundResource(R.drawable.red_circle);
            holder.viewDot1.setBackgroundColor(context.getResources().getColor(R.color.greenColor));
            holder.viewDot2.setBackgroundColor(context.getResources().getColor(R.color.greenColor));
            holder.viewDot3.setBackgroundColor(context.getResources().getColor(R.color.greenColor));
            holder.viewDot4.setBackgroundColor(context.getResources().getColor(R.color.heroColorPrimary));
        } else if (moviesList.get(position).getStatus().equals("Bike Delivered")) {
        /*    holder.spinner.setCurrentStep(3);
            holder.spiiner1.setCurrentStep(3);*/
            holder.spinner.setVisibility(View.VISIBLE);
            holder.spiiner1.setVisibility(View.VISIBLE);
            holder.appointCreatated1.setVisibility(View.VISIBLE);
            holder.appointCreatated1.setText("Appointment Created");
            holder.runnerAssigned2.setVisibility(View.VISIBLE);
            holder.bikePickupedup3.setVisibility(View.VISIBLE);
            holder.textView4.setVisibility(View.VISIBLE);
            holder.textView5.setVisibility(View.VISIBLE);
            holder.textView6.setVisibility(View.VISIBLE);
            holder.Dot1.setBackgroundResource(R.drawable.green_circle);
            holder.Dot2.setBackgroundResource(R.drawable.green_circle);
            holder.Dot3.setBackgroundResource(R.drawable.green_circle);
            holder.Dot4.setBackgroundResource(R.drawable.green_circle);
            holder.Dot5.setBackgroundResource(R.drawable.green_circle);
            holder.Dot6.setBackgroundResource(R.drawable.green_circle);
            holder.viewDot1.setBackgroundColor(context.getResources().getColor(R.color.greenColor));
            holder.viewDot2.setBackgroundColor(context.getResources().getColor(R.color.greenColor));
            holder.viewDot3.setBackgroundColor(context.getResources().getColor(R.color.greenColor));
            holder.viewDot4.setBackgroundColor(context.getResources().getColor(R.color.greenColor));
        }
        else if (moviesList.get(position).getStatus().equals("Appointment Cancelled")) {
            holder.feedback.setVisibility(View.GONE);
            holder.PaymentButton.setVisibility(View.GONE);
            holder.spinner.setVisibility(View.GONE);
            holder.spiiner1.setVisibility(View.GONE);
            holder.appointCreatated1.setVisibility(View.VISIBLE);
            holder.appointCreatated1.setText("Appointment Cancelled");
            holder.runnerAssigned2.setVisibility(View.GONE);
            holder.bikePickupedup3.setVisibility(View.GONE);
            holder.textView4.setVisibility(View.GONE);
            holder.textView5.setVisibility(View.GONE);
            holder.textView6.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}