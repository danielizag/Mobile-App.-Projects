package com.dan2you.dizzy.seniorproject.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dizzy on 4/1/2017.
 */

public class Job {

    private String job_Id = "";
    private String details = "";
    private String company = "";
    private String street = "";
    private String city = "";
    private String state = "";
    private String complete = "";

    public String getAddress(){
        String address = street + ", " + city + ", " + state;
        return address;
    }

    public String getClientDetails(){
        String details = job_Id + " " + company;
        return details;
    }

    public String getJob_Id() {
        return job_Id;
    }

    public void setJob_Id(String job_Id) {
        this.job_Id = job_Id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }
}


// if you want to make this class parcelable
//    public Job(Parcel parcel){
//        this.job_Number = parcel.readString();
//        this.job_Name = parcel.readString();
//        this.comp  = parcel.readString();
//        this.loc = parcel.readString();
//    }
//
//    public int describeContents(){
//        return 0;
//    }
//
//    public void writeToParcel(Parcel parcel, int i){
//        parcel.writeString(this.job_Number);
//        parcel.writeString(this.job_Name);
//        parcel.writeString(this.comp);
//        parcel.writeString(this.loc);
//    }
//
//    public static final Creator<Job> CREATOR = new Creator<Job>(){
//        @Override
//        public Job createFromParcel(Parcel parcel){
//            return new Job(parcel);
//        }
//        @Override
//        public Job[] newArray(int i){
//            return new Job[i];
//        }
//    };