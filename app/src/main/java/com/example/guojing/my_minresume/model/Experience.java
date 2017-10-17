package com.example.guojing.my_minresume.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;

import com.example.guojing.my_minresume.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by AmazingLu on 8/24/17.
 */

public class Experience implements Parcelable{

    public String id;
    public String company;
    public Date startDate;
    public Date endDate;
    public List<String> workList;

    public Experience() {
        id = UUID.randomUUID().toString();
    }

    protected Experience(Parcel in) {
        id = in.readString();
        company = in.readString();
        startDate = DateUtil.stringToDate(in.readString());
        endDate = DateUtil.stringToDate(in.readString());
        workList = in.createStringArrayList();
    }

    public static final Creator<Experience> CREATOR = new Creator<Experience>() {
        @Override
        public Experience createFromParcel(Parcel in) {
            return new Experience(in);
        }

        @Override
        public Experience[] newArray(int size) {
            return new Experience[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(company);
        parcel.writeString(DateUtil.dateToString(startDate));
        parcel.writeString(DateUtil.dateToString(endDate));
        parcel.writeStringList(workList);
    }
}
