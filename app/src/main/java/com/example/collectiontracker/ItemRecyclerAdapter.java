package com.example.collectiontracker;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.ViewHolder> {
    private List<ItemModel> itemList;

    public ItemRecyclerAdapter(List<ItemModel> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemModel item = itemList.get(position);
        holder.textViewItemName.setText(item.getItemName());
        holder.textViewItemDescription.setText(item.getItemDescription());
        holder.AmountItem.setText("Amount in stock: "+item.getItemAmount());

        Bitmap photo = item.getItemImage();
        if (photo != null) {
            Glide.with(holder.imageViewItemPhoto.getContext())
                    .load(photo)
                    .into(holder.imageViewItemPhoto);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewItemName;
        TextView textViewItemDescription;
        TextView AmountItem;
        ImageView imageViewItemPhoto;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewItemName = itemView.findViewById(R.id.textViewItemName);
            textViewItemDescription = itemView.findViewById(R.id.textViewItemDescription);
            imageViewItemPhoto = itemView.findViewById(R.id.imageViewItemPhoto);
            AmountItem = itemView.findViewById(R.id.AmountView);
        }
    }
}
