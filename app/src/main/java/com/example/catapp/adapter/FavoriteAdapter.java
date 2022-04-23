package com.example.catapp.adapter;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.RowHolder> {

    private ArrayList<CatModel> catList;
    private Context context;
    private FavoriteDB favoriteDB;

    public FavoriteAdapter(ArrayList<CatModel> catList, Context context) {
        this.catList = catList;
        this.context = context;
    }

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        favoriteDB = new FavoriteDB(context);
        return new RowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {

        holder.textName.setText(catList.get(position).getName());
        holder.favoriteButton.setBackgroundResource(R.drawable.favorite_press);
        Glide.with(context).load(catList.get(position).getImageData().getUrl()).into(holder.cat_avatar);
    }

    @Override
    public int getItemCount() {
        return catList.size();
    }

    private void removeItem(int position) {
        catList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, catList.size());
    }

    public class RowHolder extends RecyclerView.ViewHolder {
        TextView textName;
        ImageView cat_avatar;
        ImageButton favoriteButton;

        public RowHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.text_name);
            cat_avatar = itemView.findViewById(R.id.cat_avatar);
            favoriteButton = itemView.findViewById(R.id.favorite_button);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailPage.class);
                    int position = getAdapterPosition();
                    String id = catList.get(position).getId();
                    String favStatus = catList.get(position).getFavStatus();

                    if (favStatus == null) {
                        favStatus = "0";
                    }
                    intent.putExtra("id", id);
                    intent.putExtra("favStatus", favStatus);
                    context.startActivity(intent);
                }
            });

            favoriteButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final CatModel favoriteItem = catList.get(position);
                    favoriteButton.setBackgroundResource(R.drawable.favorite);

                    favoriteDB.remove_fav(favoriteItem.getId());
                    removeItem(position);
                }
            });
        }
    }
}
