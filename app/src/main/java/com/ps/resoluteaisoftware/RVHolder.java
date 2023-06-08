package com.ps.resoluteaisoftware;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ps.resoluteaisoftware.databinding.RvlayoutBinding;

public class RVHolder extends RecyclerView.ViewHolder {

    RvlayoutBinding rvlayoutBinding;
    public RVHolder(RvlayoutBinding r) {
        super(r.getRoot());
        rvlayoutBinding=r;


    }
}
