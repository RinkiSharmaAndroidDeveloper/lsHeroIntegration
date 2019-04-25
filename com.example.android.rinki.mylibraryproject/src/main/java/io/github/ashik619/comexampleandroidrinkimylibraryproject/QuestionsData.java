package io.github.ashik619.comexampleandroidrinkimylibraryproject;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dilip on 23/4/19.
 */

public class QuestionsData implements Parcelable {
    String Q1;

    public QuestionsData(String q1) {
        Q1 = q1;

    }

    protected QuestionsData(Parcel in) {
        Q1 = in.readString();
    }

    public static final Creator<QuestionsData> CREATOR = new Creator<QuestionsData>() {
        @Override
        public QuestionsData createFromParcel(Parcel in) {
            return new QuestionsData(in);
        }

        @Override
        public QuestionsData[] newArray(int size) {
            return new QuestionsData[size];
        }
    };

    public String getQ1() {
        return Q1;
    }

    public void setQ1(String q1) {
        Q1 = q1;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Q1);
    }
}
