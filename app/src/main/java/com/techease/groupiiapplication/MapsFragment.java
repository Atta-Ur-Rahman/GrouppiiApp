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
import android.content.DialogInterface;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsFragment extends Fragment implements View.OnClickListener {

    public ArrayList<AddTripDataModel> userParticipaintsList = new ArrayList<>();
    public Dialog dialog;

    private List<Marker> markers = new ArrayList<>();
    ViewGroup infoWindow;
    MarkerOptions markerOptions;
    int globleCount = 0;
    GoogleMap googleMap;
    ArrayList<Integer> integerArrayList = new ArrayList<>();
    boolean firstTimeRunMap = false;
    @BindView(R.id.ivClose)
    ImageView ivClose;

    public static MapsFragment newInstance() {
        return new MapsFragment();
    }

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            markerOptions = new MarkerOptions();
            googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
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
        ButterKnife.bind(this, view);
        dialog = AlertUtils.createProgressDialog(getActivity());

        dialog.setCancelable(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                getActivity().finish();
            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return view;
    }

    private void ApiCallGetUserTrip(GoogleMap googleMap) {
        dialog.show();
        userParticipaintsList.clear();
        this.googleMap = googleMap;

        Call<AddTripResponse> addTripResponseCall = BaseNetworking.ApiInterface().getUserTrip("trips/gettrip/" + AppRepository.mTripIDForUpdation(getActivity()));
        addTripResponseCall.enqueue(new Callback<AddTripResponse>() {
            @Override
            public void onResponse(Call<AddTripResponse> call, Response<AddTripResponse> response) {
                if (response.isSuccessful()) {
//                    dialog.dismiss();
                    userParticipaintsList.addAll(response.body().getData());
                    for (int i = 0; i < userParticipaintsList.size(); i++) {
                        new GetImageFromUrl(dialog, getActivity(), userParticipaintsList, googleMap, markers, markerOptions, i).execute(userParticipaintsList.get(i).getPicture());
                    }

//                    Log.d("zmaimge", "map" + userParticipaintsList.get(0));

               /*     try {
//                        Log.d("zmaimge", "map" + userParticipaintsList.size());
                        do {
                            Log.d("zmaimge", "load");
//
//                            Picasso.get().load(userParticipaintsList.get(globleCount).getPicture()).into(new Target() {
//                                @Override
//                                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
////                                    Log.d("zmaimge", "loadBitmap");
////                                    if (!TextUtils.isEmpty(userParticipaintsList.get(globleCount).getLongitude())) {
//                                    Log.d("zmaimge", "" + globleCount+  bitmap);
//
//                                    globleCount++;

                            try {
//                                        Marker marker = googleMap.addMarker(markerOptions.position(new LatLng(Double.parseDouble(userParticipaintsList.get(globleCount).getLatitude()),
//                                                Double.parseDouble(userParticipaintsList.get(globleCount).getLongitude()))).
//                                                icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(getActivity(), bitmap))));
//                                        marker.setTitle(userParticipaintsList.get(globleCount).getName());
//                                        markers.add(marker);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

//                                    }
//                                }
//
//                                @Override
//                                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//
//                                }
//
//                                @Override
//                                public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//
//                                }
//                            });


                        } while (globleCount < userParticipaintsList.size());

//                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
//                        for (Marker marker : markers) {
//                            builder.include(marker.getPosition());
//                        }
//                        LatLngBounds bounds = builder.build();
//
//                        int padding = 0; // offset from edges of the map in pixels
//                        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
//                        googleMap.animateCamera(cu);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
*/
                }
            }

            @Override
            public void onFailure(Call<AddTripResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getActivity(), String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static Bitmap createCustomMarker(Context context, Bitmap image) {

        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_custom_marker, null);
        CircleImageView markerImage = (CircleImageView) marker.findViewById(R.id.user_dp);
        markerImage.setImageBitmap(image);
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

    }

    @Override
    public void onClick(View v) {


    }
}


class GetImageFromUrl extends AsyncTask<String, Void, Bitmap> {
    //    ImageView imageView;
    Bitmap bitmap;
    ArrayList<AddTripDataModel> userParticipaintsList;
    Context context;
    GoogleMap googleMap;
    int anIntPosition = 0;
    List<Marker> markers;
    MarkerOptions markerOptions;
    Dialog dialog;

    public GetImageFromUrl(Dialog dialog, Context context, ArrayList<AddTripDataModel> addTripDataModel, GoogleMap googleMap, List<Marker> markers, MarkerOptions markerOptions, int anIntPosition) {
        this.dialog = dialog;
        this.context = context;
        this.userParticipaintsList = addTripDataModel;
        this.googleMap = googleMap;
        this.markers = markers;
        this.markerOptions = markerOptions;
        this.anIntPosition = anIntPosition;


    }

    @Override
    protected Bitmap doInBackground(String... url) {


        String stringUrl = url[0];
        bitmap = null;
        InputStream inputStream;
        try {
            inputStream = new java.net.URL(stringUrl).openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
//        imageView.setImageBitmap(bitmap);
        Log.d("zma", "bitmap" + bitmap);

        dialog.dismiss();

        try {
            Marker marker = googleMap.addMarker(markerOptions.position(new LatLng(Double.parseDouble(userParticipaintsList.get(anIntPosition).getLatitude()),
                    Double.parseDouble(userParticipaintsList.get(anIntPosition).getLongitude()))).
                    icon(BitmapDescriptorFactory.fromBitmap(MapsFragment.createCustomMarker(context, bitmap))));
            marker.setTitle(userParticipaintsList.get(anIntPosition).getName());
            markers.add(marker);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 13));
//            Log.d("zmaName", userParticipaintsList.get(anIntPosition).getName() + "  " + anIntPosition);
        } catch (Exception e) {
            e.printStackTrace();

        }


        try {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (Marker marker : markers) {
                builder.include(marker.getPosition());
            }
            LatLngBounds bounds = builder.build();

            int padding = 0; // offset from edges of the map in pixels
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            googleMap.animateCamera(cu);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

/*
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


            do {
                Picasso.get().load(userParticipaintsList.get(globleCount).getPicture()).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Log.d("zmaimge", "loadBitmap");
                        Marker marker = googleMap.addMarker(markerOptions.position(new LatLng(Double.parseDouble(userParticipaintsList.get(globleCount).getLatitude()),
                                Double.parseDouble(userParticipaintsList.get(globleCount).getLongitude()))).
                                icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(getActivity(), bitmap))));
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


            return null;
        }

        @Override
        protected void onPostExecute(String args) {


        }
    }*/



