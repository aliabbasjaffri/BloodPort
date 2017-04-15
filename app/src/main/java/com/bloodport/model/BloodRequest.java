package com.bloodport.model;

/**
 * Created by aliabbasjaffri on 26/03/2017.
 */

public class BloodRequest
{
    private String name;
    private String bloodGroup;
    private String timeStamp;
    private String location;
    private String phoneNumber;
    private String city;

    public BloodRequest()
    {

    }

    public BloodRequest(String name, String bloodGroup, String timeStamp, String location, String phoneNumber, String city) {
        this.name = name;
        this.bloodGroup = bloodGroup;
        this.timeStamp = timeStamp;
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

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
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
}
