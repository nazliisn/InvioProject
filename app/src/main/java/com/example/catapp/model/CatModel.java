package com.example.catapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CatModel {
    @SerializedName("breeds")
    private ArrayList<Breed> breedData = new ArrayList();
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    private String FavStatus = "0";
    @SerializedName("image")
    private ImageData imageData;
    @SerializedName("url")
    private String url;
    @SerializedName("description")
    private String description;
    @SerializedName("origin")
    private String origin;
    @SerializedName("reference_image_id")
    private String reference_image_id;

    public CatModel(String name, String id, String image) {
        this.name = name;
        this.id = id;
        this.imageData = new ImageData(image);

    }

    public ArrayList<Breed> getBreedData() {
        return breedData;
    }

    public void setBreedData(ArrayList<Breed> breedData) {
        this.breedData = breedData;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getReference_image_id() {
        return reference_image_id;
    }

    public void setReference_image_id(String reference_image_id) {
        this.reference_image_id = reference_image_id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }


}
