package com.aryamohades.tablemate.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Restaurant implements Parcelable {
    private String restaurant_id;
    private String name;
    private String address;

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getId() {
        return restaurant_id;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(restaurant_id);
        out.writeString(name);
        out.writeString(address);
    }

    public static final Parcelable.Creator<Restaurant> CREATOR
            = new Parcelable.Creator<Restaurant>() {
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    private Restaurant(Parcel in) {
        restaurant_id = in.readString();
        name = in.readString();
        address = in.readString();
    }
}
