package com.techease.groupiiapplication.adapter.gallery;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.deleteGalleryImage.DeleteGalleryPhotoResponse;
import com.techease.groupiiapplication.dataModel.tripDetial.getGalleryPhoto.GalleryPhotoDataModel;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.ui.activity.tripDetailScreen.ImagePreviewActivity;
import com.techease.groupiiapplication.utils.DateUtills;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryPhotoAdapter extends RecyclerView.Adapter<GalleryPhotoAdapter.MyViewHolder> {

    private Context context;
    private List<GalleryPhotoDataModel> galleryPhotoDataModels;
    private int layout_id;
    String fileUri;
    private long mLastClickTime = 0;
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
        holder.tvHotelPrice.setText(DateUtills.getPhotoGalleryDateFormate(data.getDate() + "" + data.getTime()));

        holder.ivShareGalleryPhoto.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            shareImage(data.getFile());
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ImagePreviewActivity.class);
            intent.putExtra("file", data.getFile());
            context.startActivity(intent);
        });

        holder.ivDeleteGalleryPhoto.setOnClickListener(v -> {

            BottomSheetMaterialDialog mBottomSheetDialogd = new BottomSheetMaterialDialog.Builder((Activity) context)
                    .setTitle("Delete Photo?")
                    .setMessage("Are you sure you want to this photo?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialogInterface, which) -> {
                        ApiCallDeletePhoto(data.getId());
                        dialogInterface.dismiss();
                    })
                    .setNegativeButton("No", (dialogInterface, which) -> dialogInterface.dismiss())
                    .build();

            // Show Dialog
            mBottomSheetDialogd.show();
        });

    }

    private void ApiCallDeletePhoto(int id) {
        Call<DeleteGalleryPhotoResponse> deleteGalleryPhotoResponseCall = BaseNetworking.ApiInterface().deletePhotoFromGallery(id + "");
        deleteGalleryPhotoResponseCall.enqueue(new Callback<DeleteGalleryPhotoResponse>() {
            @Override
            public void onResponse(Call<DeleteGalleryPhotoResponse> call, Response<DeleteGalleryPhotoResponse> response) {
                if (response.isSuccessful()) {
                    Connect.setMyBoolean(true);
                    Toast.makeText(context, "Photo deleted successfully", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<DeleteGalleryPhotoResponse> call, Throwable t) {
                Toast.makeText(context, "photo" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return galleryPhotoDataModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tvTitle, tvHotelPrice;
        ImageView ivGalleryPhoto, ivShareGalleryPhoto, ivDeleteGalleryPhoto;

        MyViewHolder(View view) {
            super(view);
            ivGalleryPhoto = view.findViewById(R.id.ivGalleryPhoto);
            tvTitle = view.findViewById(R.id.tvGalleryPhotoTitle);
            tvHotelPrice = view.findViewById(R.id.tvGalleryPhotoDateAndTime);
            ivShareGalleryPhoto = view.findViewById(R.id.ivShareGalleryPhoto);
            ivDeleteGalleryPhoto = view.findViewById(R.id.ivDeleteGalleryPhoto);

        }
    }


    public void shareImage(String url) {

        Picasso.get().load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                String bitmapPath = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "palette", "share palette");
                Uri bitmapUri = Uri.parse(bitmapPath);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/png");
                intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
                context.startActivity(Intent.createChooser(intent, "Share"));
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