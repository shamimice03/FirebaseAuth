package com.example.shamim.nstubds;

/**
 * Created by Shamim on 12-Mar-18.
 */

import java.sql.Timestamp;
import java.util.Date;

public class BloodPost {

    public BloodPost() {
    }



    public String bloodGroup;
    public String description;
    public String firstName;
    public String lastName;
    public String location;
    public String phoneNumber;
    public String unit;
    public String userId;
    public String userName;
    public Date timestamp;


    public BloodPost(String bloodGroup, String description, String firstName, String lastName, String location, String phoneNumber, String unit, String userId, String userName, Date timestamp) {
        this.bloodGroup = bloodGroup;
        this.description = description;
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.unit = unit;
        this.userId = userId;
        this.userName = userName;
        this.timestamp = timestamp;
    }




    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }





}
