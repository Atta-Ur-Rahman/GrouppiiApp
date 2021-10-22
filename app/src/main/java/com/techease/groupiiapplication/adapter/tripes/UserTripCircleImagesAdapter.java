package com.techease.groupiiapplication.adapter.tripes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.getAllTrip.User;
import com.techease.groupiiapplication.utils.AppRepository;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserTripCircleImagesAdapter extends RecyclerView.Adapter<UserTripCircleImagesAdapter.ViewHolder> {

    private final Context context;
    private final List<User> arrayList;
    int anIntImage = 1;

    public UserTripCircleImagesAdapter(Context context, List<User> imagesArray) {
        this.context = context;
        arrayList = imagesArray;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View imageLayout = LayoutInflater.from(context).inflate(R.layout.custom_circle_layout, parent, false);
        return new ViewHolder(imageLayout);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(AppRepository.mUserProfileImage(context)).placeholder(R.drawable.user).into(holder.imageView);

        try {
            Glide.with(context).load(arrayList.get(position).getPicture()).placeholder(R.drawable.user).into(holder.imageView);
            Log.d("zma array", String.valueOf(arrayList.get(position).getTripid()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (arrayList.size() == 1) {
            anIntImage = 1;
            if (position == 1) {
                holder.relativeLayout.setVisibility(View.VISIBLE);
                holder.tvCount.setText(arrayList.size() + "+");
            } else {
                holder.relativeLayout.setVisibility(View.GONE);
            }
        }
        if (arrayList.size() == 2) {
            anIntImage = 2;

            if (position == 2) {
                holder.relativeLayout.setVisibility(View.VISIBLE);
                holder.tvCount.setText(arrayList.size() + "+");
            } else {
                holder.relativeLayout.setVisibility(View.GONE);
            }
        }
        if (arrayList.size() >= 3) {
            anIntImage = 3;
            if (position >= 3) {
                holder.relativeLayout.setVisibility(View.VISIBLE);
                holder.tvCount.setText(arrayList.size() + "+");
            } else {
                holder.relativeLayout.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return anIntImage;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout relativeLayout;
        TextView tvCount;
        CircleImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.relative);
            tvCount = itemView.findViewById(R.id.tvCount);
            imageView = itemView.findViewById(R.id.profile_image);
        }
    }
}