package com.example.catapp.service;

import com.example.catapp.model.CatModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CatAPI {
    //https://api.thecatapi.com/v1/

    @GET("breeds")
    Call<List<CatModel>> getData();

    //https://api.thecatapi.com/v1/images/search?breed_id=acur
    @GET("images/search")
    Call<List<CatModel>> getDetail(@Query("breed_id") String q);


}
