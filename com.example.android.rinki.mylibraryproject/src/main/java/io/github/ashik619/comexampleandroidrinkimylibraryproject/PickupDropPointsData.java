package io.github.ashik619.comexampleandroidrinkimylibraryproject;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dilip on 3/5/19.
 */

public class PickupDropPointsData implements Parcelable {
    String  startPickupLat,StartPickupLng,dropPickupLat,dropPickupLng;
    String  startPickupLat1,StartPickupLng1,dropPickupLat1,dropPickupLng1;

    public PickupDropPointsData(String startPickupLat, String startPickupLng, String dropPickupLat, String dropPickupLng, String startPickupLat1, String startPickupLng1, String dropPickupLat1, String dropPickupLng1) {
        this.startPickupLat = startPickupLat;
        StartPickupLng = startPickupLng;
        this.dropPickupLat = dropPickupLat;
        this.dropPickupLng = dropPickupLng;
        this.startPickupLat1 = startPickupLat1;
        StartPickupLng1 = startPickupLng1;
        this.dropPickupLat1 = dropPickupLat1;
        this.dropPickupLng1 = dropPickupLng1;
    }

    protected PickupDropPointsData(Parcel in) {
        startPickupLat = in.readString();
        StartPickupLng = in.readString();
        dropPickupLat = in.readString();
        dropPickupLng = in.readString();
        startPickupLat1 = in.readString();
        StartPickupLng1 = in.readString();
        dropPickupLat1 = in.readString();
        dropPickupLng1 = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(startPickupLat);
        dest.writeString(StartPickupLng);
        dest.writeString(dropPickupLat);
        dest.writeString(dropPickupLng);
        dest.writeString(startPickupLat1);
        dest.writeString(StartPickupLng1);
        dest.writeString(dropPickupLat1);
        dest.writeString(dropPickupLng1);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PickupDropPointsData> CREATOR = new Creator<PickupDropPointsData>() {
        @Override
        public PickupDropPointsData createFromParcel(Parcel in) {
            return new PickupDropPointsData(in);
        }

        @Override
        public PickupDropPointsData[] newArray(int size) {
            return new PickupDropPointsData[size];
        }
    };

    public String getStartPickupLat() {
        return startPickupLat;
    }

    public void setStartPickupLat(String startPickupLat) {
        this.startPickupLat = startPickupLat;
    }

    public String getStartPickupLng() {
        return StartPickupLng;
    }

    public void setStartPickupLng(String startPickupLng) {
        StartPickupLng = startPickupLng;
    }

    public String getDropPickupLat() {
        return dropPickupLat;
    }

    public void setDropPickupLat(String dropPickupLat) {
        this.dropPickupLat = dropPickupLat;
    }

    public String getDropPickupLng() {
        return dropPickupLng;
    }

    public void setDropPickupLng(String dropPickupLng) {
        this.dropPickupLng = dropPickupLng;
    }

    public String getStartPickupLat1() {
        return startPickupLat1;
    }

    public void setStartPickupLat1(String startPickupLat1) {
        this.startPickupLat1 = startPickupLat1;
    }

    public String getStartPickupLng1() {
        return StartPickupLng1;
    }

    public void setStartPickupLng1(String startPickupLng1) {
        StartPickupLng1 = startPickupLng1;
    }

    public String getDropPickupLat1() {
        return dropPickupLat1;
    }

    public void setDropPickupLat1(String dropPickupLat1) {
        this.dropPickupLat1 = dropPickupLat1;
    }

    public String getDropPickupLng1() {
        return dropPickupLng1;
    }

    public void setDropPickupLng1(String dropPickupLng1) {
        this.dropPickupLng1 = dropPickupLng1;
    }
}
