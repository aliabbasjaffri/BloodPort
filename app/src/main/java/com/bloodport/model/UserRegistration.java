package com.bloodport.model;

/**
 * Created by aliabbasjaffri on 18/03/2017.
 */

public class UserRegistration
{
    private String name;
    private String phoneNumber;
    private Boolean gender;
    private Boolean termsAgreement;
    private String bloodGroup;

    public UserRegistration(String name, String phoneNumber, Boolean gender, Boolean termsAgreement, String bloodGroup) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.termsAgreement = termsAgreement;
        this.bloodGroup = bloodGroup;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Boolean getGender() {
        return gender;
    }

    public Boolean getTermsAgreement() {
        return termsAgreement;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public void setTermsAgreement(Boolean termsAgreement) {
        this.termsAgreement = termsAgreement;
    }


    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
}
