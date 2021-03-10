package com.techease.groupiiapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.getGalleryPhoto.GalleryPhotoDataModel;

import java.util.List;

public class GalleryPhotoAdapter extends RecyclerView.Adapter<GalleryPhotoAdapter.MyViewHolder> {

    private Context context;
    private List<GalleryPhotoDataModel> galleryPhotoDataModels;
    private int layout_id;


    public GalleryPhotoAdapter(Context context, List<GalleryPhotoDataModel> galleryPhotoDataModels, int layout) {
        this.galleryPhotoDataModels = galleryPhotoDataModels;
        this.context = context;
        this.layout_id = layout;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layout_id, parent, false);
        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        GalleryPhotoDataModel data = galleryPhotoDataModels.get(position);
        Glide.with(context).load(data.getFile()).placeholder(R.drawable.progress_animation).into(holder.ivGalleryPhoto);
        holder.tvTitle.setText(data.getTitle());
        holder.tvHotelPrice.setText(data.getTime() + "," + data.getDate());


    }

    @Override
    public int getItemCount() {
        return galleryPhotoDataModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tvTitle, tvHotelPrice;
        ImageView ivGalleryPhoto;

        MyViewHolder(View view) {
            super(view);
            ivGalleryPhoto = view.findViewById(R.id.ivGalleryPhoto);
            tvTitle = view.findViewById(R.id.tvGalleryPhotoTitle);
            tvHotelPrice = view.findViewById(R.id.tvGalleryPhotoDateAndTime);

        }
    }
}