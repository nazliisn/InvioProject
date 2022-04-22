package com.example.catapp.view;

import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.catapp.R;
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
    ImageView imageView;
    TextView text_description;
    TextView text_origin;
    TextView text_name;
    Retrofit retrofit;
    String id;



    private String BASE_URL = "https://api.thecatapi.com/v1/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);

        id = getIntent().getStringExtra("id");


        imageView = findViewById(R.id.imageView);
        text_description = findViewById(R.id.text_description);
        text_origin = findViewById(R.id.origin);
        text_name = findViewById(R.id.text_name);


        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        loadData();

    }

    private void loadData() {

        final CatAPI catAPI = retrofit.create(CatAPI.class);
        Call<List<CatModel>> call = catAPI.getDetail(id);

        call.enqueue(new Callback<List<CatModel>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<CatModel>> call, Response<List<CatModel>> response) {
                if (response.isSuccessful()) {
                    List<CatModel> responseList = response.body();
                    responseList.forEach(change -> {

                                Glide.with(getApplicationContext()).load(change.getUrl()).into(imageView);
                                text_name.setText(change.getBreedData().get(0).getName());
                                text_description.setText(change.getBreedData().get(0).getDescription());
                                text_origin.setText(change.getBreedData().get(0).getOrigin());
                            }
                    );
                }
            }

            @Override
            public void onFailure(Call<List<CatModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


}