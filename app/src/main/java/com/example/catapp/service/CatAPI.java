package com.example.catapp.service;

import com.example.catapp.model.CatModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CatAPI {
    //https://api.thecatapi.com/v1/

    @GET("breeds")
    Call<List<CatModel>> getData();
}
