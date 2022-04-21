package com.example.catapp.model;

import com.google.gson.annotations.SerializedName;

public class CatModel {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    private String FavStatus = null;

    @SerializedName("image")
    private ImageData imageData;

    public CatModel(String name, String id, String image) {
        this.name = name;
        this.id = id;
        this.imageData = new ImageData(image);

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFavStatus() {
        return FavStatus;
    }

    public void setFavStatus(String favStatus) {
        this.FavStatus = favStatus;
    }

    public ImageData getImageData() {
        return imageData;
    }

    public void setImageData(ImageData imageData) {
        this.imageData = imageData;
    }
}
