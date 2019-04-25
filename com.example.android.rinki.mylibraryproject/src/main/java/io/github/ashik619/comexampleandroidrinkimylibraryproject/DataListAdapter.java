package io.github.ashik619.comexampleandroidrinkimylibraryproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.victoralbertos.breadcumbs_view.BreadcrumbsView;

/**
 * Created by dilip on 5/4/19.
 */

public class DataListAdapter extends RecyclerView.Adapter<DataListAdapter.MyViewHolder> {

    private List<DataList> moviesList;
    Context context;
    AsyncResult<DataList> asyncResult_clickPayTm;
    AsyncResult<String > viewJobCard;
int steps=3;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Username, userRating, bookingNumber, vinNumber,currentStatus,bikeBookedModel,bikeNumber,appointCreatated1,runnerAssigned2,bikePickupedup3,textView4,textView5,textView6,date,lsAmount;
        Button otpBtn,PaymentButton;
        CircleImageView userImg,PhoneBtn;
        BreadcrumbsView spinner,spiiner1;
        LinearLayout linearLayout;
        public MyViewHolder(View view) {
            super(view);
            Username=(TextView) view.findViewById(R.id.name);
            userRating=(TextView) view.findViewById(R.id.rating);
            bookingNumber=(TextView) view.findViewById(R.id.booking_txt);
            vinNumber=(TextView) view.findViewById(R.id.registration_number);
            lsAmount=(TextView) view.findViewById(R.id.ls_amount);
            currentStatus=(TextView) view.findViewById(R.id.current_status_number);
            otpBtn=(Button) view.findViewById(R.id.otp_txt);
            PaymentButton=(Button) view.findViewById(R.id.payment_btn);
            userImg=(CircleImageView) view.findViewById(R.id.profile_image);
            PhoneBtn=(CircleImageView) view.findViewById(R.id.bs_call_customer);
            spinner=(BreadcrumbsView) view.findViewById(R.id.breadcrumbs);
            spiiner1=(BreadcrumbsView) view.findViewById(R.id.breadcrumbs1);
            linearLayout=(LinearLayout) view.findViewById(R.id.view_photos);

            bikeBookedModel=(TextView) view.findViewById(R.id.model_txt);
            date=(TextView) view.findViewById(R.id.date);
            bikeNumber=(TextView) view.findViewById(R.id.bike_number_txt);
            appointCreatated1=(TextView) view.findViewById(R.id.txt1);
            runnerAssigned2=(TextView) view.findViewById(R.id.txt2);
            bikePickupedup3=(TextView) view.findViewById(R.id.txt3);
            textView4=(TextView) view.findViewById(R.id.txt4);
            textView5=(TextView) view.findViewById(R.id.txt5);
            textView6=(TextView) view.findViewById(R.id.txt6);
        }
    }


    public DataListAdapter(List<DataList> moviesList, Context context, AsyncResult<DataList> asyncResult_clickPayTm,  AsyncResult<String > viewJobCard) {
        this.moviesList = moviesList;
        this.context = context;
        this.asyncResult_clickPayTm=asyncResult_clickPayTm;
        this.viewJobCard=viewJobCard;
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

       if(movie.getRunnerName()!=null){
           holder.Username.setText(movie.getRunnerName());
       }

holder.linearLayout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        viewJobCard.success(moviesList.get(position).getId());
    }
});
        if(!TextUtils.isEmpty(movie.getBookingNo())){
            holder.bookingNumber.setText(movie.getBookingNo());
        }
        if(movie.getFinal_quotation().equals("0")||movie.getPaymentStatus().equals("paid"))
        {
            holder.PaymentButton.setBackgroundResource(R.drawable.disable_btn);

        }
  holder.PaymentButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          if(movie.getFinal_quotation().equals("0"))
          {
              Toast.makeText(context, "Payment detail is not updated", Toast.LENGTH_LONG).show();
          }else if(movie.getPaymentStatus().equals("paid")){
              Toast.makeText(context, "Payment already done", Toast.LENGTH_LONG).show();
          }else
              {
                  asyncResult_clickPayTm.success(moviesList.get(position));
              }


      }
  });


if(movie.getChassisNo()!=null){
    holder.vinNumber.setText(movie.getChassisNo());
}
if(movie.getLsAmount()!=null){
    holder.lsAmount.setText(movie.getLsAmount()+".00");
}

     if(movie.getFinal_quotation()!=null){
            holder.currentStatus.setText(movie.getFinal_quotation()+".00");
        }
if(movie.getBikeNo()!=null){
    holder.bikeNumber.setText(movie.getBikeNo());
}
if(movie.getDateTime()!=null){
    holder.date.setText(movie.getDateTime());
}
if(movie.getBikeBrandname()!=null){
    holder.bikeBookedModel.setText(movie.getBikeBrandname()+" "+movie.getBikeModel());
}
    if(movie.getRunnerPicture()!=null){
        Picasso.with(context).load(movie.getRunnerPicture()).into(holder.userImg);
    }


     holder.userRating.setText(movie.getRating());


if(moviesList.get(position).getStatus().equals("Appointment Created"))
{
            holder.spinner.setCurrentStep(1);
            holder.appointCreatated1.setVisibility(View.VISIBLE);
            holder.spiiner1.setVisibility(View.GONE);
            holder.runnerAssigned2.setVisibility(View.GONE);
            holder.bikePickupedup3.setVisibility(View.GONE);
            holder.textView4.setVisibility(View.GONE);
            holder.textView5.setVisibility(View.GONE);
            holder.textView6.setVisibility(View.GONE);

}
        if(moviesList.get(position).getStatus().equals("Runner Assigned")){
            holder.spiiner1.setVisibility(View.GONE);

            holder.spinner.setCurrentStep(2);
            holder.appointCreatated1.setVisibility(View.VISIBLE);
            holder.runnerAssigned2.setVisibility(View.VISIBLE);
            holder.bikePickupedup3.setVisibility(View.GONE);
            holder.textView4.setVisibility(View.GONE);
            holder.textView5.setVisibility(View.GONE);
            holder.textView6.setVisibility(View.GONE);

        }

        if(moviesList.get(position).getStatus().equals("Bike Picked For Service")){

            holder.spiiner1.setVisibility(View.GONE);

            holder.spinner.setCurrentStep(3);
            holder.appointCreatated1.setVisibility(View.VISIBLE);
            holder.runnerAssigned2.setVisibility(View.VISIBLE);
            holder.bikePickupedup3.setVisibility(View.VISIBLE);
            holder.textView4.setVisibility(View.GONE);
            holder.textView5.setVisibility(View.GONE);
            holder.textView6.setVisibility(View.GONE);
        }

        if(moviesList.get(position).getStatus().equals("Bike Service In Progress")){
            holder.spiiner1.setVisibility(View.VISIBLE);
            holder.spiiner1.setCurrentStep(1);
            holder.spinner.setCurrentStep(3);
            holder.appointCreatated1.setVisibility(View.VISIBLE);
            holder.runnerAssigned2.setVisibility(View.VISIBLE);
            holder.bikePickupedup3.setVisibility(View.VISIBLE);
            holder.textView4.setVisibility(View.VISIBLE);
            holder.textView5.setVisibility(View.GONE);
            holder.textView6.setVisibility(View.GONE);
        }
        if(moviesList.get(position).getStatus().equals("Bike Picked For Delivery")){
            holder.spinner.setCurrentStep(3);
            holder.spiiner1.setCurrentStep(2);
            holder.spiiner1.setVisibility(View.VISIBLE);
            holder.appointCreatated1.setVisibility(View.VISIBLE);
            holder.runnerAssigned2.setVisibility(View.VISIBLE);
            holder.bikePickupedup3.setVisibility(View.VISIBLE);
            holder.textView4.setVisibility(View.VISIBLE);
            holder.textView5.setVisibility(View.VISIBLE);
            holder.textView6.setVisibility(View.GONE);
        }

        if(moviesList.get(position).getStatus().equals("Bike Delivered")){
            holder.spinner.setCurrentStep(3);
            holder.spiiner1.setCurrentStep(3);
            holder.spiiner1.setVisibility(View.VISIBLE);
            holder.appointCreatated1.setVisibility(View.VISIBLE);
            holder.runnerAssigned2.setVisibility(View.VISIBLE);
            holder.bikePickupedup3.setVisibility(View.VISIBLE);
            holder.textView4.setVisibility(View.VISIBLE);
            holder.textView5.setVisibility(View.VISIBLE);
            holder.textView6.setVisibility(View.VISIBLE);
        }

        if(moviesList.get(position).getStatus().equals("Appointment Cancelled")){

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