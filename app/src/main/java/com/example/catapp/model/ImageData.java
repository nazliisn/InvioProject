package com.example.catapp.model;

import com.google.gson.annotations.SerializedName;

public class ImageData {


    @SerializedName("id")
    private String id;
  /*  @SerializedName("width")
    public int width;*/

    @SerializedName("url")
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
