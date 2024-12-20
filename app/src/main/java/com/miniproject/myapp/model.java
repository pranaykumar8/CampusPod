package com.miniproject.myapp;


public class model {

    private String imageurl;

    public model(String imageurl) {
        this.imageurl = imageurl;
    }
    public model(){}

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getImageurl() {
        return imageurl;
    }
}
