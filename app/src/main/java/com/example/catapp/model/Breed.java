package com.example.catapp.model;

import com.google.gson.annotations.SerializedName;

public class Breed {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("origin")
    private String origin;
    @SerializedName("reference_image_id")
    private String reference_image_id;

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
