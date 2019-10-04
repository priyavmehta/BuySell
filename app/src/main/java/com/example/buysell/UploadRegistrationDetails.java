package com.example.priyavfireapp;

public class UploadRegistrationDetails {
    private String contactNo;
    private String password;
    private String email;
    private String name;

    public UploadRegistrationDetails(String contactNo, String password, String email, String name) {
        this.contactNo = contactNo;
        this.password = password;
        this.email = email;
        this.name = name;
    }
    public UploadRegistrationDetails() {

    }

    public String getContactNo() {
        return contactNo;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
