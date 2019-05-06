package io.github.ashik619.comexampleandroidrinkimylibraryproject;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dilip on 6/5/19.
 */

public class PickupOnlyData implements Parcelable {
String latitude,langitude;

    public PickupOnlyData(String latitude, String langitude) {
        this.latitude = latitude;
        this.langitude = langitude;
    }

    protected PickupOnlyData(Parcel in) {
        latitude = in.readString();
        langitude = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(latitude);
        dest.writeString(langitude);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PickupOnlyData> CREATOR = new Creator<PickupOnlyData>() {
        @Override
        public PickupOnlyData createFromParcel(Parcel in) {
            return new PickupOnlyData(in);
        }

        @Override
        public PickupOnlyData[] newArray(int size) {
            return new PickupOnlyData[size];
        }
    };

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLangitude() {
        return langitude;
    }

    public void setLangitude(String langitude) {
        this.langitude = langitude;
    }
}
