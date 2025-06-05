package com.example.userinfoapp;
public class User {
    private String userName;
    private String password;
    private String phoneNumber;
    private String address;
    private String description;

    public User(String userName, String password, String phoneNumber, String address) {
        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.description = "";
    }

    public User() {}

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
