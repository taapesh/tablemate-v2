package com.aryamohades.tablemate.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Table implements Parcelable {
    private String table_id;
    private Server server;
    private Restaurant restaurant;
    private int size;
    private String table_number;
    private boolean active;
    private boolean requested;

    public String getId() {
        return table_id;
    }

    public Server getServer() {
        return server;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public int getSize() {
        return size;
    }

    public String getTableNumber() {
        return table_number;
    }

    public boolean isActive() {
        return active;
    }

    public boolean hasRequested() {
        return requested;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(size);
        out.writeInt(requested ? 1 : 0);
        out.writeString(table_number);
    }

    public static final Parcelable.Creator<Table> CREATOR
            = new Parcelable.Creator<Table>() {
        public Table createFromParcel(Parcel in) {
            return new Table(in);
        }

        public Table[] newArray(int size) {
            return new Table[size];
        }
    };

    private Table(Parcel in) {
        size = in.readInt();
        requested = in.readInt() == 1;
        table_number = in.readString();
    }
}
