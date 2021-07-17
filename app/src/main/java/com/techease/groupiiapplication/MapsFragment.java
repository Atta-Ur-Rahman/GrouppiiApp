package com.techease.groupiiapplication;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.media.MediaMetadataCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.techease.groupiiapplication.adapter.tripDetail.CustomSpinnerAdapter;
import com.techease.groupiiapplication.dataModel.addTrips.addTrip.AddTripDataModel;
import com.techease.groupiiapplication.dataModel.addTrips.addTrip.AddTripResponse;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.ui.activity.tripDetailScreen.TripDetailScreenActivity;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.AppRepository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsFragment extends Fragment {

    public ArrayList<AddTripDataModel> userParticipaintsList = new ArrayList<>();
    public Dialog dialog;

    private List<Marker> markers = new ArrayList<>();
    ViewGroup infoWindow;
    MarkerOptions markerOptions;
    int globleCount = 0;

    GoogleMap googleMap;


    boolean firstTimeRunMap = false;


    public static MapsFragment newInstance() {
        return new MapsFragment();
    }

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            markerOptions = new MarkerOptions();
            LatLng sydney = new LatLng(-34, 151);
            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Groupii"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            ApiCallGetUserTrip(googleMap);

        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        dialog = AlertUtils.createProgressDialog(getActivity());

        return view;
    }


    private void ApiCallGetUserTrip(GoogleMap googleMap) {
        dialog.show();
        userParticipaintsList.clear();
        this.googleMap = googleMap;

        Call<AddTripResponse> addTripResponseCall = BaseNetworking.ApiInterface().getUserTrip("trips/gettrip/" + AppRepository.mTripId(getActivity()));
        addTripResponseCall.enqueue(new Callback<AddTripResponse>() {
            @Override
            public void onResponse(Call<AddTripResponse> call, Response<AddTripResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    userParticipaintsList.addAll(response.body().getData());


                    new ImportImages(getActivity()).execute("");

/*

                    try {
                        Log.d("zmaimge", "map" + userParticipaintsList.size());
                        do {
                            Log.d("zmaimge", "load");

                            Picasso.get().load(userParticipaintsList.get(globleCount).getPicture()).into(new Target() {
                                @Override
                                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                    Log.d("zmaimge", "loadBitmap");

                                    try {

                                        if (!TextUtils.isEmpty(userParticipaintsList.get(globleCount).getLongitude())) {

                                            Marker marker = googleMap.addMarker(markerOptions.position(new LatLng(Double.parseDouble(userParticipaintsList.get(globleCount).getLatitude()),
                                                    Double.parseDouble(userParticipaintsList.get(globleCount).getLongitude()))).
                                                    icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(getActivity(), userParticipaintsList.get(globleCount).getPicture(), bitmap))));
//                                snippet(userParticipaintsList.get(i).getName()));
                                            marker.setTitle(userParticipaintsList.get(globleCount).getName());
                                            markers.add(marker);


                                            //                                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 13));


                                        }
                                        globleCount++;


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                }

                                @Override
                                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                                }

                                @Override
                                public void onPrepareLoad(Drawable placeHolderDrawable) {
                                }
                            });

                        } while (globleCount < userParticipaintsList.size());

                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        for (Marker marker : markers) {
                            builder.include(marker.getPosition());
                        }
                        LatLngBounds bounds = builder.build();

                        int padding = 0; // offset from edges of the map in pixels
                        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                        googleMap.animateCamera(cu);
*/









/*
                        for (int i = 0; i < userParticipaintsList.size(); i++) {
                            Marker marker = googleMap.addMarker(markerOptions.position(new LatLng(Double.parseDouble(userParticipaintsList.get(i).getLatitude()),
                                    Double.parseDouble(userParticipaintsList.get(i).getLongitude()))).
                                    icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(getActivity(), userParticipaintsList.get(i).getPicture()))));
//                                snippet(userParticipaintsList.get(i).getName()));
                            marker.setTitle(userParticipaintsList.get(i).getName());
                            marker.showInfoWindow();
                            markers.add(marker);
                            markers.add(marker);
//                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 13));
                        }
                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        for (Marker marker : markers) {
                            builder.include(marker.getPosition());
                        }
                        LatLngBounds bounds = builder.build();

                        int padding = 0; // offset from edges of the map in pixels
                        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                        googleMap.animateCamera(cu);*/

//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }

                }
            }

            @Override
            public void onFailure(Call<AddTripResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getActivity(), String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static Bitmap createCustomMarker(Context context, String imag_url, Bitmap image) {

        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_custom_marker, null);
        CircleImageView markerImage = (CircleImageView) marker.findViewById(R.id.user_dp);
//        markerImage.setImageResource(R.drawable.user);


//        Glide.with(context).load(R.drawable.emoji_26d1).placeholder(R.drawable.user).into(markerImage);
//        Log.d("zmaimge", imag_url);

        markerImage.setImageBitmap(image);
//        TextView txt_name = (TextView)marker.findViewById(R.id.name);
//        txt_name.setText(_name);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        marker.setLayoutParams(new ViewGroup.LayoutParams(80, 80));
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        marker.draw(canvas);

        return bitmap;
    }

    @Override
    public void onResume() {
        super.onResume();
        globleCount = 0;

    }


    @SuppressLint("StaticFieldLeak")
    private class ImportImages extends AsyncTask<String, String, String> {
        private final Context context;

        public ImportImages(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

//            pDialog.show();
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub


            try {

                do {

                    Picasso.get().load(userParticipaintsList.get(globleCount).getPicture()).into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            Log.d("zmaimge", "loadBitmap");


                            Marker marker = googleMap.addMarker(markerOptions.position(new LatLng(Double.parseDouble(userParticipaintsList.get(globleCount).getLatitude()),
                                    Double.parseDouble(userParticipaintsList.get(globleCount).getLongitude()))).
                                    icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(getActivity(), userParticipaintsList.get(globleCount).getPicture(), bitmap))));
//                                snippet(userParticipaintsList.get(i).getName()));
                            marker.setTitle(userParticipaintsList.get(globleCount).getName());
                            marker.showInfoWindow();
                            markers.add(marker);

                            globleCount++;


                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                        }
                    });

                } while (globleCount < userParticipaintsList.size());


                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (Marker marker : markers) {
                    builder.include(marker.getPosition());
                }
                LatLngBounds bounds = builder.build();

                int padding = 0; // offset from edges of the map in pixels
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                googleMap.animateCamera(cu);

//                                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 13));


            } catch (Exception e) {
                e.printStackTrace();
            }


//
//            int j = 0;
//            do {
//                j++;
//
//
//            } while (j < userParticipaintsList.size());


            return null;
        }

        @Override
        protected void onPostExecute(String args) {



        }
    }


}
