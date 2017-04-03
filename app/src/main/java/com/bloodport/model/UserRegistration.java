package com.bloodport.model;

/**
 * Created by aliabbasjaffri on 18/03/2017.
 */

public class UserRegistration
{
    private String email;
    private String name;
    private String phoneNumber;
    private String gender;
    private String bloodGroup;

    public UserRegistration(String name, String phoneNumber, String gender, String bloodGroup, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
