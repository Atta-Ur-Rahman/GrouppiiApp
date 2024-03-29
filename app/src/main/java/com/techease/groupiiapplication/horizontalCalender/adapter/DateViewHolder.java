package com.techease.groupiiapplication.horizontalCalender.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.techease.groupiiapplication.R;

class DateViewHolder extends RecyclerView.ViewHolder {

    TextView textTop;
    TextView textMiddle;
    TextView textBottom;
    View selectionView;
    View layoutContent;
    RecyclerView eventsRecyclerView;

    DateViewHolder(View rootView) {
        super(rootView);
        textTop = rootView.findViewById(R.id.hc_text_top);
        textMiddle = rootView.findViewById(R.id.hc_text_middle);
        textBottom = rootView.findViewById(R.id.hc_text_bottom);
        layoutContent = rootView.findViewById(R.id.hc_layoutContent);
        selectionView = rootView.findViewById(R.id.hc_selector);
        eventsRecyclerView = rootView.findViewById(R.id.hc_events_recyclerView);
    }
}
