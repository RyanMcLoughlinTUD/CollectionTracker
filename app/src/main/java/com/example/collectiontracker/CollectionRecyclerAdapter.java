package com.example.collectiontracker;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.example.collectiontracker.CollectionModel;

public class CollectionRecyclerAdapter extends RecyclerView.Adapter<CollectionRecyclerAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    private List<CollectionModel> collectionList;

    public CollectionRecyclerAdapter(List<CollectionModel> collectionList, OnItemClickListener listener) {
        this.collectionList = collectionList;
        this.onItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collection, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CollectionModel collection = collectionList.get(position);
        holder.collectionNameTextView.setText(collection.getCollectionName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(collection);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return collectionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView collectionNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            collectionNameTextView = itemView.findViewById(R.id.collectionNameTextView);
        }
    }
    public interface OnItemClickListener {
        void onItemClick(CollectionModel collection);
    }
}