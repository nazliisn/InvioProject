package com.example.catapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catapp.R;
import com.example.catapp.adapter.RecyclerViewAdapter;
import com.example.catapp.model.CatModel;
import com.example.catapp.service.CatAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    ArrayList<CatModel> catModels;
    Retrofit retrofit;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;

    private String BASE_URL = "https://api.thecatapi.com/v1/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);

        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        SearchView searchView = root.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerViewAdapter.getFilter().filter(newText);
                return true;
            }
        });
        loadData();
        return root;
    }

    private void loadData() {
        final CatAPI catAPI = retrofit.create(CatAPI.class);
        Call<List<CatModel>> call = catAPI.getData();

        call.enqueue(new Callback<List<CatModel>>() {
            @Override
            public void onResponse(Call<List<CatModel>> call, Response<List<CatModel>> response) {
                if (response.isSuccessful()) {
                    List<CatModel> responseList = response.body();
                    catModels = new ArrayList<>(responseList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerViewAdapter = new RecyclerViewAdapter(catModels, getActivity());
                    recyclerView.setAdapter(recyclerViewAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<CatModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}