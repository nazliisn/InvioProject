package com.example.catapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.catapp.R;
import com.example.catapp.model.CatModel;
import com.example.catapp.view.DetailPage;
import com.example.catapp.view.FavoriteDB;

import java.io.IOException;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RowHolder> {
    private ArrayList<CatModel> catList;
    private Context context;
    private FavoriteDB favoriteDB;

    public RecyclerViewAdapter(ArrayList<CatModel> catList, Context context) {
        this.catList = catList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_layout, parent, false);
        favoriteDB = new FavoriteDB(context);

        SharedPreferences preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        boolean firstStart = preferences.getBoolean("firstStart", true);
        if (firstStart) {
            createTableOnFirstStart();
        }
        return new RowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.RowHolder holder, int position) {
        final CatModel catModel = catList.get(position);
        try {
            holder.bind(catModel);
            readCursorData(catModel, holder);
            holder.detailPage(catModel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return catList.size();
    }

    public void createTableOnFirstStart() {
        favoriteDB.insertEmpty();
        SharedPreferences preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

    @SuppressLint("Range")
    public void readCursorData(CatModel catModel, RecyclerView.ViewHolder viewHolder) {
        Cursor cursor = favoriteDB.readAllData(catModel.getId());
        SQLiteDatabase database = favoriteDB.getReadableDatabase();
        try {
            while (cursor.moveToNext()) {
                String item = cursor.getString(cursor.getColumnIndex(FavoriteDB.FAVSTATUS));
                catModel.setFavStatus(item);
                if (item != null && item.equals("1")) {
                    viewHolder.itemView.findViewById(R.id.favorite_button).setBackgroundResource(R.drawable.favorite_press);
                } else
                    viewHolder.itemView.findViewById(R.id.favorite_button).setBackgroundResource(R.drawable.favorite);

            }
        } finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
            database.close();
        }
    }

    public class RowHolder extends RecyclerView.ViewHolder {
        TextView textName;
        ImageView cat_avatar;
        ImageButton favoriteButton;

        public RowHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(CatModel catModel) throws IOException {
            textName = itemView.findViewById(R.id.text_name);
            cat_avatar = itemView.findViewById(R.id.cat_avatar);
            favoriteButton = itemView.findViewById(R.id.favorite_button);

            favoriteButton.setOnClickListener(view -> {
                if (catModel.getFavStatus() == null) {
                    catModel.setFavStatus("1");
                    favoriteDB.insertIntoTheDatabase(catModel.getName(), catModel.getImageData().getUrl(), catModel.getId(), catModel.getFavStatus());
                    favoriteButton.setBackgroundResource(R.drawable.favorite_press);
                } else {
                    catModel.setFavStatus(null);
                    favoriteDB.remove_fav(catModel.getId());
                    favoriteButton.setBackgroundResource(R.drawable.favorite);
                }
            });
            textName.setText(catModel.getName());
            Glide.with(context).load(catModel.getImageData().getUrl()).into(cat_avatar);
        }

        public void detailPage(CatModel catModel) {
            textName = itemView.findViewById(R.id.text_name);

            textName.setOnClickListener(view -> {
                Intent intent = new Intent(context, DetailPage.class);
                String id = catModel.getId();
                intent.putExtra("id", id);
                context.startActivity(intent);


            });

        }
    }
}




