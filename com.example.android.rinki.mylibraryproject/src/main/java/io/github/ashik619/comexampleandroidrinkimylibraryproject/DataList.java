package io.github.ashik619.comexampleandroidrinkimylibraryproject;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by dilip on 5/4/19.
 */

public class DataList implements Parcelable{


    String customerName1, email, user_picture, bikeBrandname, id, userId, createdBy_Id, organicId, pcId, bikeBrand, bikeModel, bikeNo, pickAddress, dropAddress, locality, dateTime, serviceTypeId, assistanceTypeId, assistanceType, serviceCenter, typeOfService, customerType, status, ACRId, cancelRemarks, lsRemarks, scStatus, final_quotation, discounted_amount, assistanceAmount, lsAmount, chassisNo, engineNO, SPONO, dealerCode, availHMSICredit, typeOfPD, amcUser, privilegeId, cityId, couponId, teleCallerTCID, CREID, activeStatus, rating, runnerId, runnerName, runnerPicture, runnerMobile, bookingNo, mobileNo, paymentStatus;

    public DataList(String customerName1, String email, String user_picture, String bikeBrandname, String id, String userId, String createdBy_Id, String organicId, String pcId, String bikeBrand, String bikeModel, String bikeNo, String pickAddress, String dropAddress, String locality, String dateTime, String serviceTypeId, String assistanceTypeId, String assistanceType, String serviceCenter, String typeOfService, String customerType, String status, String ACRId, String cancelRemarks, String lsRemarks, String scStatus, String final_quotation, String discounted_amount, String assistanceAmount, String lsAmount, String chassisNo, String engineNO, String SPONO, String dealerCode, String availHMSICredit, String typeOfPD, String amcUser, String privilegeId, String cityId, String couponId, String teleCallerTCID, String CREID, String activeStatus, String rating, String runnerId, String runnerName, String runnerPicture, String runnerMobile, String bookingNo, String mobileNo, String paymentStatus) {
        this.customerName1 = customerName1;
        this.email = email;
        this.user_picture = user_picture;
        this.bikeBrandname = bikeBrandname;
        this.id = id;
        this.userId = userId;
        this.createdBy_Id = createdBy_Id;
        this.organicId = organicId;
        this.pcId = pcId;
        this.bikeBrand = bikeBrand;
        this.bikeModel = bikeModel;
        this.bikeNo = bikeNo;
        this.pickAddress = pickAddress;
        this.dropAddress = dropAddress;
        this.locality = locality;
        this.dateTime = dateTime;
        this.serviceTypeId = serviceTypeId;
        this.assistanceTypeId = assistanceTypeId;
        this.assistanceType = assistanceType;
        this.serviceCenter = serviceCenter;
        this.typeOfService = typeOfService;
        this.customerType = customerType;
        this.status = status;
        this.ACRId = ACRId;
        this.cancelRemarks = cancelRemarks;
        this.lsRemarks = lsRemarks;
        this.scStatus = scStatus;
        this.final_quotation = final_quotation;
        this.discounted_amount = discounted_amount;
        this.assistanceAmount = assistanceAmount;
        this.lsAmount = lsAmount;
        this.chassisNo = chassisNo;
        this.engineNO = engineNO;
        this.SPONO = SPONO;
        this.dealerCode = dealerCode;
        this.availHMSICredit = availHMSICredit;
        this.typeOfPD = typeOfPD;
        this.amcUser = amcUser;
        this.privilegeId = privilegeId;
        this.cityId = cityId;
        this.couponId = couponId;
        this.teleCallerTCID = teleCallerTCID;
        this.CREID = CREID;
        this.activeStatus = activeStatus;
        this.rating = rating;
        this.runnerId = runnerId;
        this.runnerName = runnerName;
        this.runnerPicture = runnerPicture;
        this.runnerMobile = runnerMobile;
        this.bookingNo = bookingNo;
        this.mobileNo = mobileNo;
        this.paymentStatus = paymentStatus;
    }

    protected DataList(Parcel in) {
        customerName1 = in.readString();
        email = in.readString();
        user_picture = in.readString();
        bikeBrandname = in.readString();
        id = in.readString();
        userId = in.readString();
        createdBy_Id = in.readString();
        organicId = in.readString();
        pcId = in.readString();
        bikeBrand = in.readString();
        bikeModel = in.readString();
        bikeNo = in.readString();
        pickAddress = in.readString();
        dropAddress = in.readString();
        locality = in.readString();
        dateTime = in.readString();
        serviceTypeId = in.readString();
        assistanceTypeId = in.readString();
        assistanceType = in.readString();
        serviceCenter = in.readString();
        typeOfService = in.readString();
        customerType = in.readString();
        status = in.readString();
        ACRId = in.readString();
        cancelRemarks = in.readString();
        lsRemarks = in.readString();
        scStatus = in.readString();
        final_quotation = in.readString();
        discounted_amount = in.readString();
        assistanceAmount = in.readString();
        lsAmount = in.readString();
        chassisNo = in.readString();
        engineNO = in.readString();
        SPONO = in.readString();
        dealerCode = in.readString();
        availHMSICredit = in.readString();
        typeOfPD = in.readString();
        amcUser = in.readString();
        privilegeId = in.readString();
        cityId = in.readString();
        couponId = in.readString();
        teleCallerTCID = in.readString();
        CREID = in.readString();
        activeStatus = in.readString();
        rating = in.readString();
        runnerId = in.readString();
        runnerName = in.readString();
        runnerPicture = in.readString();
        runnerMobile = in.readString();
        bookingNo = in.readString();
        mobileNo = in.readString();
        paymentStatus = in.readString();
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

    public String getCustomerName1() {
        return customerName1;
    }

    public void setCustomerName1(String customerName1) {
        this.customerName1 = customerName1;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_picture() {
        return user_picture;
    }

    public void setUser_picture(String user_picture) {
        this.user_picture = user_picture;
    }

    public String getBikeBrandname() {
        return bikeBrandname;
    }

    public void setBikeBrandname(String bikeBrandname) {
        this.bikeBrandname = bikeBrandname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreatedBy_Id() {
        return createdBy_Id;
    }

    public void setCreatedBy_Id(String createdBy_Id) {
        this.createdBy_Id = createdBy_Id;
    }

    public String getOrganicId() {
        return organicId;
    }

    public void setOrganicId(String organicId) {
        this.organicId = organicId;
    }

    public String getPcId() {
        return pcId;
    }

    public void setPcId(String pcId) {
        this.pcId = pcId;
    }

    public String getBikeBrand() {
        return bikeBrand;
    }

    public void setBikeBrand(String bikeBrand) {
        this.bikeBrand = bikeBrand;
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

    public String getDropAddress() {
        return dropAddress;
    }

    public void setDropAddress(String dropAddress) {
        this.dropAddress = dropAddress;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getAssistanceTypeId() {
        return assistanceTypeId;
    }

    public void setAssistanceTypeId(String assistanceTypeId) {
        this.assistanceTypeId = assistanceTypeId;
    }

    public String getAssistanceType() {
        return assistanceType;
    }

    public void setAssistanceType(String assistanceType) {
        this.assistanceType = assistanceType;
    }

    public String getServiceCenter() {
        return serviceCenter;
    }

    public void setServiceCenter(String serviceCenter) {
        this.serviceCenter = serviceCenter;
    }

    public String getTypeOfService() {
        return typeOfService;
    }

    public void setTypeOfService(String typeOfService) {
        this.typeOfService = typeOfService;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getACRId() {
        return ACRId;
    }

    public void setACRId(String ACRId) {
        this.ACRId = ACRId;
    }

    public String getCancelRemarks() {
        return cancelRemarks;
    }

    public void setCancelRemarks(String cancelRemarks) {
        this.cancelRemarks = cancelRemarks;
    }

    public String getLsRemarks() {
        return lsRemarks;
    }

    public void setLsRemarks(String lsRemarks) {
        this.lsRemarks = lsRemarks;
    }

    public String getScStatus() {
        return scStatus;
    }

    public void setScStatus(String scStatus) {
        this.scStatus = scStatus;
    }

    public String getFinal_quotation() {
        return final_quotation;
    }

    public void setFinal_quotation(String final_quotation) {
        this.final_quotation = final_quotation;
    }

    public String getDiscounted_amount() {
        return discounted_amount;
    }

    public void setDiscounted_amount(String discounted_amount) {
        this.discounted_amount = discounted_amount;
    }

    public String getAssistanceAmount() {
        return assistanceAmount;
    }

    public void setAssistanceAmount(String assistanceAmount) {
        this.assistanceAmount = assistanceAmount;
    }

    public String getLsAmount() {
        return lsAmount;
    }

    public void setLsAmount(String lsAmount) {
        this.lsAmount = lsAmount;
    }

    public String getChassisNo() {
        return chassisNo;
    }

    public void setChassisNo(String chassisNo) {
        this.chassisNo = chassisNo;
    }

    public String getEngineNO() {
        return engineNO;
    }

    public void setEngineNO(String engineNO) {
        this.engineNO = engineNO;
    }

    public String getSPONO() {
        return SPONO;
    }

    public void setSPONO(String SPONO) {
        this.SPONO = SPONO;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    public String getAvailHMSICredit() {
        return availHMSICredit;
    }

    public void setAvailHMSICredit(String availHMSICredit) {
        this.availHMSICredit = availHMSICredit;
    }

    public String getTypeOfPD() {
        return typeOfPD;
    }

    public void setTypeOfPD(String typeOfPD) {
        this.typeOfPD = typeOfPD;
    }

    public String getAmcUser() {
        return amcUser;
    }

    public void setAmcUser(String amcUser) {
        this.amcUser = amcUser;
    }

    public String getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(String privilegeId) {
        this.privilegeId = privilegeId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getTeleCallerTCID() {
        return teleCallerTCID;
    }

    public void setTeleCallerTCID(String teleCallerTCID) {
        this.teleCallerTCID = teleCallerTCID;
    }

    public String getCREID() {
        return CREID;
    }

    public void setCREID(String CREID) {
        this.CREID = CREID;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRunnerId() {
        return runnerId;
    }

    public void setRunnerId(String runnerId) {
        this.runnerId = runnerId;
    }

    public String getRunnerName() {
        return runnerName;
    }

    public void setRunnerName(String runnerName) {
        this.runnerName = runnerName;
    }

    public String getRunnerPicture() {
        return runnerPicture;
    }

    public void setRunnerPicture(String runnerPicture) {
        this.runnerPicture = runnerPicture;
    }

    public String getRunnerMobile() {
        return runnerMobile;
    }

    public void setRunnerMobile(String runnerMobile) {
        this.runnerMobile = runnerMobile;
    }

    public String getBookingNo() {
        return bookingNo;
    }

    public void setBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(customerName1);
        dest.writeString(email);
        dest.writeString(user_picture);
        dest.writeString(bikeBrandname);
        dest.writeString(id);
        dest.writeString(userId);
        dest.writeString(createdBy_Id);
        dest.writeString(organicId);
        dest.writeString(pcId);
        dest.writeString(bikeBrand);
        dest.writeString(bikeModel);
        dest.writeString(bikeNo);
        dest.writeString(pickAddress);
        dest.writeString(dropAddress);
        dest.writeString(locality);
        dest.writeString(dateTime);
        dest.writeString(serviceTypeId);
        dest.writeString(assistanceTypeId);
        dest.writeString(assistanceType);
        dest.writeString(serviceCenter);
        dest.writeString(typeOfService);
        dest.writeString(customerType);
        dest.writeString(status);
        dest.writeString(ACRId);
        dest.writeString(cancelRemarks);
        dest.writeString(lsRemarks);
        dest.writeString(scStatus);
        dest.writeString(final_quotation);
        dest.writeString(discounted_amount);
        dest.writeString(assistanceAmount);
        dest.writeString(lsAmount);
        dest.writeString(chassisNo);
        dest.writeString(engineNO);
        dest.writeString(SPONO);
        dest.writeString(dealerCode);
        dest.writeString(availHMSICredit);
        dest.writeString(typeOfPD);
        dest.writeString(amcUser);
        dest.writeString(privilegeId);
        dest.writeString(cityId);
        dest.writeString(couponId);
        dest.writeString(teleCallerTCID);
        dest.writeString(CREID);
        dest.writeString(activeStatus);
        dest.writeString(rating);
        dest.writeString(runnerId);
        dest.writeString(runnerName);
        dest.writeString(runnerPicture);
        dest.writeString(runnerMobile);
        dest.writeString(bookingNo);
        dest.writeString(mobileNo);
        dest.writeString(paymentStatus);
    }
}