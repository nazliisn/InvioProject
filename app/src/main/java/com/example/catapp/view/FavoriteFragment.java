package com.example.catapp.view;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catapp.R;
import com.example.catapp.adapter.FavoriteAdapter;
import com.example.catapp.model.CatModel;

import java.util.ArrayList;
import java.util.List;


public class FavoriteFragment extends Fragment {
    RecyclerView recyclerView;
    FavoriteDB favoriteDB;
    FavoriteAdapter favoriteAdapter;
    private List<CatModel> catModels = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favorite, container, false);

        favoriteDB = new FavoriteDB(getActivity());
        recyclerView = root.findViewById(R.id.recyclerView_favorite);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadData();
        return root;
    }
    @SuppressLint("Range")
    private void loadData() {
        if (catModels != null) {
            catModels.clear();
        }
        SQLiteDatabase sqLiteDatabase = favoriteDB.getReadableDatabase();
        Cursor cursor = favoriteDB.selectALLFavoriteList();
        try {
            while (cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndex(FavoriteDB.ITEM_TITLE));
                String id = cursor.getString(cursor.getColumnIndex(FavoriteDB.KEY_ID));
                String image = cursor.getString(cursor.getColumnIndex(FavoriteDB.ITEM_IMAGE));
                CatModel favoriteItem = new CatModel(title, id, image);

                catModels.add(favoriteItem);
            }
        } finally {
            if (cursor != null && cursor.isClosed()) {
                cursor.close();
            }
            sqLiteDatabase.close();
        }
        favoriteAdapter = new FavoriteAdapter((ArrayList<CatModel>) catModels, getActivity());
        recyclerView.setAdapter(favoriteAdapter);
    }
}