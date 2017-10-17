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

/**
 * Parceble is a pipeline between activities to transit data
 * implement parcelable in a class means this class can be transit in this pipeline with
 * specific method to serialize and de-serialize data
 * protected education (Parcel in) use to de-serialize data
 * public void writeToParcel(Parcel parcel, int i) use to serialize data
 * */

public class Education implements Parcelable{
    public String id;
    public String school;
    public Date startDate;
    public Date endDate;
    public List<String> courseList;

    public Education() {
        // get a random and unique id for each education objext
        id = UUID.randomUUID().toString();
    }

    protected Education(Parcel in) {
        id = in.readString();
        school = in.readString();
        // also parcelable do not know how to de-serialize date
        startDate = DateUtil.stringToDate(in.readString());
        endDate = DateUtil.stringToDate(in.readString());
        courseList = in.createStringArrayList();
    }

    public static final Creator<Education> CREATOR = new Creator<Education>() {
        @Override
        public Education createFromParcel(Parcel in) {
            return new Education(in);
        }

        @Override
        public Education[] newArray(int size) {
            return new Education[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(school);
        // parcelable do not know how to Serialize date
        parcel.writeString(DateUtil.dateToString(startDate));
        parcel.writeString(DateUtil.dateToString(endDate));
        parcel.writeStringList(courseList);
    }
}
