package com.techease.groupiiapplication.adapter.addTrip;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.addTrips.OgodaHotel.Result;
import com.techease.groupiiapplication.ui.activity.WebViewActivity;
import com.thefinestartist.finestwebview.FinestWebView;
import com.thefinestartist.finestwebview.listeners.WebViewListener;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.MyViewHolder> {

    private Context context;
    private List<Result> hotelDataModels;


    public HotelAdapter(Context context, List<Result> hotelDataModelList) {
        this.hotelDataModels = hotelDataModelList;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_trip_detail_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Result data = hotelDataModels.get(position);
        Glide.with(context).load(data.getImageURL()).into(holder.ivImage);
        holder.tvTitle.setText(data.getHotelName());
        holder.tvRoom.setText(data.getDailyRate() + "");
        holder.tvHotelPrice.setText(data.getDailyRate() + " " + data.getCurrency());
        holder.tvReview.setText(data.getStarRating() + " / 5.0");
        holder.tvRecommended.setText("Recommended for " + data.getDiscountPercentage() + " of guests");


        holder.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = data.getLandingURL();

//
                Intent intent = new Intent(context, WebViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", url);
                intent.putExtras(bundle);
                context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context).toBundle());

//                try {
//                    Uri uri = Uri.parse("googlechrome://navigate?url=" + url);
//                    Intent i = new Intent(Intent.ACTION_VIEW, uri);
//                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(i);
//                } catch (ActivityNotFoundException e) {
//                    // Chrome is probably not installed
//                }
//
//                new FinestWebView.Builder(context)
//                        .titleDefault("Groupii App")
//                        .theme(R.style.FinestWebViewTheme)
//                        .showUrl(false)
//                        .addWebViewListener(new WebViewListener() {
//                            @Override
//                            public void onPageFinished(String url) {
//                                super.onPageFinished(url);
//
//                            }
//                        })
//                        .show(data.getLandingURL());
            }
        });

//            Log.d("zma hotel pic", data.getImageURL());

//
//        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
//        List<Address> addresses = null;
//        try {
//            addresses = geocoder.getFromLocation(data.getLatitude(), data.getLongitude(), 1);
//            String cityName = addresses.get(0).getAddressLine(0);
//            String stateName = addresses.get(0).getAddressLine(1);
//            String countryName = addresses.get(0).getAddressLine(2);
//
//            Log.d("zma loc", stateName + "  " + cityName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }

    @Override
    public int getItemCount() {
        return hotelDataModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tvTitle, tvRoom, tvDescription, tvReview, tvHotelPrice, tvRecommended;
        ImageView ivImage;

        MyViewHolder(View view) {
            super(view);
            ivImage = view.findViewById(R.id.ivHotelImages);
            tvTitle = view.findViewById(R.id.tvTripTitle);
            tvRoom = view.findViewById(R.id.tvRoom);
            tvHotelPrice = view.findViewById(R.id.tvHotelPrice);
            tvReview = view.findViewById(R.id.tvReview);
            tvRecommended = view.findViewById(R.id.tvRecommended);

        }
    }
}