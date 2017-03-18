package com.bloodport.model;

/**
 * Created by aliabbasjaffri on 18/03/2017.
 */

public class UserRegistration
{
    String name;
    String phoneNumber;
    Boolean gender;
    Boolean termsAgreement;

    public UserRegistration(String name, String phoneNumber, Boolean gender, Boolean termsAgreement) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.termsAgreement = termsAgreement;
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
}
