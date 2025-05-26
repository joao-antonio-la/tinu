package com.example.tinu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.ViewHolder> {
    private List<String> items;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String itemName);
    }

    public CarouselAdapter(List<String> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemText;

        public ViewHolder(View view) {
            super(view);
            itemText = view.findViewById(R.id.itemText);
        }

        public void bind(String itemName, OnItemClickListener listener) {
            itemText.setText(itemName);
            itemView.setOnClickListener(v -> listener.onItemClick(itemName));
        }
    }

    @NonNull
    @Override
    public CarouselAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.carousel_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarouselAdapter.ViewHolder holder, int position) {
        holder.bind(items.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
