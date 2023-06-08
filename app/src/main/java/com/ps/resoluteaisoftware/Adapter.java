package com.ps.resoluteaisoftware;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ps.resoluteaisoftware.databinding.RvlayoutBinding;

import java.util.ArrayList;


public class Adapter extends RecyclerView.Adapter<RVHolder> {
    ArrayList<Model> arraylist;

    public Adapter(ArrayList<Model> arrayList) {
        arraylist=arrayList;
    }

    @NonNull
    @Override
    public RVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       return new RVHolder(RvlayoutBinding.inflate(LayoutInflater.from(parent.getContext())));

    }

    @Override
    public void onBindViewHolder(@NonNull RVHolder holder, int position) {
        holder.rvlayoutBinding.forresulttv.setText(arraylist.get(position).getContents());
        holder.rvlayoutBinding.forformettv.setText(arraylist.get(position).getFormatName());
    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }
}
