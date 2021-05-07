package com.techease.groupiiapplication.adapter.gallery;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.getGalleryPhoto.GalleryPhotoDataModel;
import com.techease.groupiiapplication.ui.activity.tripDetailScreen.ImagePreviewActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class GalleryPhotoAdapter extends RecyclerView.Adapter<GalleryPhotoAdapter.MyViewHolder> {

    private Context context;
    private List<GalleryPhotoDataModel> galleryPhotoDataModels;
    private int layout_id;
    String fileUri;


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


        holder.ivShareGalleryPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareImage(data.getFile());
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ImagePreviewActivity.class);
                intent.putExtra("file", data.getFile());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return galleryPhotoDataModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tvTitle, tvHotelPrice;
        ImageView ivGalleryPhoto, ivShareGalleryPhoto;

        MyViewHolder(View view) {
            super(view);
            ivGalleryPhoto = view.findViewById(R.id.ivGalleryPhoto);
            tvTitle = view.findViewById(R.id.tvGalleryPhotoTitle);
            tvHotelPrice = view.findViewById(R.id.tvGalleryPhotoDateAndTime);
            ivShareGalleryPhoto = view.findViewById(R.id.ivShareGalleryPhoto);

        }
    }


    public void shareImage(String url) {

        Picasso.get().load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                try {
                    File mydir = new File(Environment.getExternalStorageDirectory() + "/Groupii");
                    if (!mydir.exists()) {
                        mydir.mkdirs();
                    }

                    fileUri = mydir.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".jpg";
                    FileOutputStream outputStream = new FileOutputStream(fileUri);

                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), BitmapFactory.decodeFile(fileUri), null, null));
                // use intent to share image
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/*");
                share.putExtra(Intent.EXTRA_STREAM, uri);
                context.startActivity(Intent.createChooser(share, "Share Image"));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });
    }
}