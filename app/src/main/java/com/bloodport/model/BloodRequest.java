package com.bloodport.model;

/**
 * Created by aliabbasjaffri on 26/03/2017.
 */

public class BloodRequest
{
    private String name;
    private String bloodGroup;
    private String time;
    private String date;
    private String location;
    private String phoneNumber;
    private String city;

    public BloodRequest()
    {

    }

    public BloodRequest(String name, String bloodGroup, String time, String date, String location, String phoneNumber, String city) {
        this.name = name;
        this.bloodGroup = bloodGroup;
        this.time = time;
        this.date = date;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
