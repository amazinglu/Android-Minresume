package com.example.guojing.my_minresume.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.guojing.my_minresume.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by AmazingLu on 8/24/17.
 */

public class Project implements Parcelable{

    public String id;
    public String projectName;
    public Date startDate;
    public Date endDate;
    public List<String> targetList;

    public Project() {
        id = UUID.randomUUID().toString();
    }

    protected Project(Parcel in) {
        id = in.readString();
        projectName = in.readString();
        startDate = DateUtil.stringToDate(in.readString());
        endDate = DateUtil.stringToDate(in.readString());
        targetList = in.createStringArrayList();
    }

    public static final Creator<Project> CREATOR = new Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel in) {
            return new Project(in);
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(projectName);
        parcel.writeString(DateUtil.dateToString(startDate));
        parcel.writeString(DateUtil.dateToString(endDate));
        parcel.writeStringList(targetList);
    }
}
