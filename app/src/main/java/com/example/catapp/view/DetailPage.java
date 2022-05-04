package com.example.catapp.view;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.catapp.R;
import com.example.catapp.databinding.ActivityDetailPageBinding;
import com.example.catapp.model.CatModel;
import com.example.catapp.service.CatAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailPage extends AppCompatActivity {
    ActivityDetailPageBinding binding;
    Retrofit retrofit;
    private String id;
    private String favStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        id = getIntent().getStringExtra("id");
        favStatus = getIntent().getStringExtra("favStatus");

        Gson gson = new GsonBuilder().setLenient().create();
        String BASE_URL = "https://api.thecatapi.com/v1/";
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        loadData();
    }

    private void loadData() {
        if (favStatus.equals("1")) {
            binding.favoriteButton.setBackgroundResource(R.drawable.favorite_press);
        } else {
            binding.favoriteButton.setBackgroundResource(R.drawable.favorite);
        }
        final CatAPI catAPI = retrofit.create(CatAPI.class);
        Call<List<CatModel>> call = catAPI.getDetail(id);

        call.enqueue(new Callback<List<CatModel>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NonNull Call<List<CatModel>> call, @NonNull Response<List<CatModel>> response) {
                if (response.isSuccessful()) {
                    List<CatModel> responseList = response.body();
                    assert responseList != null;
                    responseList.forEach(change -> {
                                Glide.with(getApplicationContext()).load(change.getUrl()).override(300, 200)
                                        .centerCrop().into(binding.imageView);
                                binding.textName.setText(change.getBreedData().get(0).getName());
                                binding.textDescription.setText(change.getBreedData().get(0).getDescription());
                                binding.origin.setText(change.getBreedData().get(0).getOrigin());
                            }
                    );
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<CatModel>> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
}