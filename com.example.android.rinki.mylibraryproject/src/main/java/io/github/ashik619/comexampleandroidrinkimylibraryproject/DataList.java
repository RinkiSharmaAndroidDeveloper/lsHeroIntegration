package io.github.ashik619.comexampleandroidrinkimylibraryproject;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by dilip on 5/4/19.
 */

public class DataList implements Parcelable {
    String name,mobile,bikeModel,bikeNo,pickAddress,locality,typeOfService,status,remarks,finalQuotation,lsAmount;

    public DataList(String name, String mobile, String bikeModel, String bikeNo, String pickAddress, String locality, String typeOfService, String status, String remarks, String finalQuotation, String lsAmount) {
        this.name = name;
        this.mobile = mobile;
        this.bikeModel = bikeModel;
        this.bikeNo = bikeNo;
        this.pickAddress = pickAddress;
        this.locality = locality;
        this.typeOfService = typeOfService;
        this.status = status;
        this.remarks = remarks;
        this.finalQuotation = finalQuotation;
        this.lsAmount = lsAmount;
    }

    protected DataList(Parcel in) {
        name = in.readString();
        mobile = in.readString();
        bikeModel = in.readString();
        bikeNo = in.readString();
        pickAddress = in.readString();
        locality = in.readString();
        typeOfService = in.readString();
        status = in.readString();
        remarks = in.readString();
        finalQuotation = in.readString();
        lsAmount = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(mobile);
        dest.writeString(bikeModel);
        dest.writeString(bikeNo);
        dest.writeString(pickAddress);
        dest.writeString(locality);
        dest.writeString(typeOfService);
        dest.writeString(status);
        dest.writeString(remarks);
        dest.writeString(finalQuotation);
        dest.writeString(lsAmount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DataList> CREATOR = new Creator<DataList>() {
        @Override
        public DataList createFromParcel(Parcel in) {
            return new DataList(in);
        }

        @Override
        public DataList[] newArray(int size) {
            return new DataList[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBikeModel() {
        return bikeModel;
    }

    public void setBikeModel(String bikeModel) {
        this.bikeModel = bikeModel;
    }

    public String getBikeNo() {
        return bikeNo;
    }

    public void setBikeNo(String bikeNo) {
        this.bikeNo = bikeNo;
    }

    public String getPickAddress() {
        return pickAddress;
    }

    public void setPickAddress(String pickAddress) {
        this.pickAddress = pickAddress;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getTypeOfService() {
        return typeOfService;
    }

    public void setTypeOfService(String typeOfService) {
        this.typeOfService = typeOfService;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getFinalQuotation() {
        return finalQuotation;
    }

    public void setFinalQuotation(String finalQuotation) {
        this.finalQuotation = finalQuotation;
    }

    public String getLsAmount() {
        return lsAmount;
    }

    public void setLsAmount(String lsAmount) {
        this.lsAmount = lsAmount;
    }
    /* [{"username":"Sanchit Agrawal","user_mobile":"7406557772","bikeModel":null,"bikeNo":"KA09BH2711","pickAddress":"hsr sector 7","locality":"HSR Layout 5th Sector Bengaluru Karnataka India","typeOfService":null,"status":"Appointment Cancelled","remarks":null,"final_quotation":null,"lsAmount":null}*/
}
