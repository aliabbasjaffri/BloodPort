package com.bloodport.model;

/**
 * Created by aliabbasjaffri on 26/03/2017.
 */

public class BloodRequestList
{
    private String name;
    private String bloodGroup;
    private String timeStamp;
    private String location;

    public BloodRequestList(String name, String bloodGroup, String timeStamp, String location) {
        this.name = name;
        this.bloodGroup = bloodGroup;
        this.timeStamp = timeStamp;
        this.location = location;
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
}
