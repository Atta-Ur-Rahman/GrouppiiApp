package com.techease.groupiiapplication.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.tripDetail.User;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private Context context;
    private List<User> arrayList;
    int anIntImage;

    public DataAdapter(Context context, List<User> imagesArray) {
        this.context = context;
        arrayList = imagesArray;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View imageLayout = LayoutInflater.from(context).inflate(R.layout.custom_circle_layout, parent, false);
        return new ViewHolder(imageLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        User user=arrayList.get(position);
//        Picasso.get().load((Uri) user.getPicture()).placeholder(R.drawable.image_thumbnail).into(holder.imageView);


//
        if (position == 3) {
            holder.relativeLayout.setVisibility(View.VISIBLE);
            holder.tvCount.setText(String.valueOf(arrayList.size()+4)+"+");
        }else {
            holder.relativeLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout relativeLayout;
        TextView tvCount;
        CircleImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.relative);
            tvCount = itemView.findViewById(R.id.tvCount);
            imageView = itemView
                    .findViewById(R.id.profile_image);


        }
    }
}