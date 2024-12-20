package com.miniproject.myapp;

public class UserProfile {
    public String getFullname() {
        return fullname;
    }

    public String getCollegeid() {
        return collegeid;
    }

    public String getProfile() {
        return profile;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String fullname,collegeid,profile,email,password;
    public UserProfile(){

    }

    public UserProfile(String fullname, String collegeid,String profile,String email,String password) {
        this.fullname = fullname;
        this.collegeid = collegeid;
        this.profile = profile;
        this.email = email;
        this.password =password;
    }
}
