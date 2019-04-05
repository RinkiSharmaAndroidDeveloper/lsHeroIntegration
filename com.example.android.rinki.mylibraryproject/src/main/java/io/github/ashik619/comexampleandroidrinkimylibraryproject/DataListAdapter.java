package io.github.ashik619.comexampleandroidrinkimylibraryproject;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dilip on 5/4/19.
 */

public class DataListAdapter extends RecyclerView.Adapter<DataListAdapter.MyViewHolder> {

    private List<DataList> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mobile,name, address, PickupArea, appoinmentStatus,dealerCityState,bikeBookedModel,remarks,bookingAmount,finalQuotation,service;

        public MyViewHolder(View view) {
            super(view);
            name=(TextView) view.findViewById(R.id.name);
            mobile=(TextView) view.findViewById(R.id.mobile);
            address=(TextView) view.findViewById(R.id.address);
            PickupArea=(TextView) view.findViewById(R.id.pickup_area);
            appoinmentStatus=(TextView) view.findViewById(R.id.dealer_code);
            service=(TextView) view.findViewById(R.id.dealer_name);

            bikeBookedModel=(TextView) view.findViewById(R.id.model);
            remarks=(TextView) view.findViewById(R.id.remarks);
            finalQuotation=(TextView) view.findViewById(R.id.appointment_date);
            bookingAmount=(TextView) view.findViewById(R.id.bookingAmount);
        }
    }


    public DataListAdapter(List<DataList> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_data, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DataList movie = moviesList.get(position);

       holder.mobile.setText(movie.getMobile());
       holder.name.setText(movie.getName());
       holder.address.setText(movie.getPickAddress());
       holder.PickupArea.setText(movie.getLocality());
       holder.appoinmentStatus.setText(movie.getStatus());

       holder.bikeBookedModel.setText(movie.getBikeModel());
       holder.remarks.setText(movie.getRemarks());
       holder.bookingAmount.setText(movie.getLsAmount());
       holder.finalQuotation.setText(movie.getFinalQuotation());
       holder.service.setText(movie.getTypeOfService());

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}