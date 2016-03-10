package com.aryamohades.tablemate.model;

import android.os.Parcelable;
import android.os.Parcel;

public class Table implements Parcelable {
    public static final String EXTRA_NAME = "TABLE";

    private String table_id;
    private int size;
    private String restaurant_name;
    private String restaurant_addr;
    private String table_number;
    private String server_id;
    private String server_name;
    private boolean requested;

    public Table(String name, String addr, String number) {
        restaurant_addr = addr;
        restaurant_name = name;
        table_number = number;
    }

    public String getRestaurantName() {
        return restaurant_name;
    }

    public String getRestaurantAddr() {
       return restaurant_addr;
    }

    public int getTableSize() {
        return size;
    }

    public String getServerName() {
        return server_name;
    }

    public String getServerId() {
        return server_id;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(table_id);
        out.writeInt(size);
        out.writeString(restaurant_name);
        out.writeString(restaurant_addr);
        out.writeString(table_number);
        out.writeInt(requested ? 1 : 0);
        out.writeString(server_name);
        out.writeString(server_id);
    }

    public static final Parcelable.Creator<Table> CREATOR = new Parcelable.Creator<Table>() {
        public Table createFromParcel(Parcel in) {
            return new Table(in);
        }

        public Table[] newArray(int size) {
            return new Table[size];
        }
    };

    private Table(Parcel in) {
        table_id = in.readString();
        size = in.readInt();
        restaurant_name = in.readString();
        restaurant_addr = in.readString();
        table_number = in.readString();
        requested = in.readInt() != 0;
        server_name = in.readString();
        server_id = in.readString();
    }

    public int describeContents() {
        return 0;
    }
}
