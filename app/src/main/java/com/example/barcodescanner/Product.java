package com.example.barcodescanner;

public class Product {
    private String name;
    private String imgURL;

    public Product(String name, String imgURL) {
        this.name = name;
        this.imgURL = imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgURL() {
        return imgURL;
    }

    public String getName() {
        return name;
    }
}
