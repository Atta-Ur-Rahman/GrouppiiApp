package com.techease.groupiiapplication.adapter.tripDetail;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.tripDetail.User;

import java.util.ArrayList;
import java.util.List;

public class CustomSpinnerAdapter extends BaseAdapter {
    Context context;

    LayoutInflater inflter;

    List<User> userArrayList;

    public CustomSpinnerAdapter(Context applicationContext, List<User> userArrayList) {
        this.context = applicationContext;
        this.userArrayList = userArrayList;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return userArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_user_sppinner_layout, null);
        ImageView icon = view.findViewById(R.id.ivUser);
        TextView names = view.findViewById(R.id.tvUserName);

//        Log.d("zma text",userArrayList.get(i).getName());

        Glide.with(context).load(userArrayList.get(i).getPicture()).into(icon);
        names.setText(userArrayList.get(i).getName());
        return view;
    }
}