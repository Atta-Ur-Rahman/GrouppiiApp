package com.techease.groupiiapplication.ui.activity.Map;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.tripDetail.User;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class LocationMapViewFragment extends Fragment {


    Dialog dialog;
    List<User> nearByRestaurents = new ArrayList<>();
    private List<Marker> markers = new ArrayList<>();

    ViewGroup infoWindow;
    CircleImageView ivNotaryProfile;

    TextView tvRestaurantName, tvLaksaName;
    ImageView ivUserImage, ivLaksaImage, ivTasted;
    MarkerOptions markerOptions;



    public static LocationMapViewFragment newInstance() {
        return new LocationMapViewFragment();
    }


    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            markerOptions = new MarkerOptions();


            Marker darwinMarker = googleMap.addMarker(markerOptions.position(new LatLng(12.4245309,130.86125)).
                    icon(BitmapDescriptorFactory.fromResource(R.drawable.location)).
                    snippet("Darwin"));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(darwinMarker.getPosition(), 12));
            markers.add(darwinMarker);

            initData(googleMap);


            infoWindow = (ViewGroup) getActivity().getLayoutInflater().inflate(R.layout.marker_user_info_layout, null);
            ivUserImage = infoWindow.findViewById(R.id.civUserLocation);
//            ivLaksaImage = infoWindow.findViewById(R.id.iv_laksa_image);
//            tvRestaurantName = infoWindow.findViewById(R.id.tv_restaurant_name);
//            tvLaksaName = infoWindow.findViewById(R.id.tv_laksa_name);
//            ivTasted = infoWindow.findViewById(R.id.ivTasted);

            googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public View getInfoWindow(Marker marker) {

                    Log.d("zma marker", String.valueOf(marker.getPosition() + "  " + marker.getId()));
                    if (marker.getId().equals("m0")) {
                    } else {
//                        NearByRestaurent model = (NearByRestaurent) marker.getTag();
//                        Picasso.get().load(model.getLaksaImageLink()).into(ivLaksaImage, new MarkerCallback(marker));
//                    Picasso.get().load(model.getRestaurentImageLink()).into(ivRestaurantImage, new MarkerCallback(marker));

//                        if (!model.getCanTaste()) {
//                            ivTasted.setImageDrawable(getActivity().getDrawable(R.drawable.red_selected_spoon));
//                        }

//                        tvRestaurantName.setText(model.getRestaurentName());
//                        tvLaksaName.setText(model.getLaksaName());
//                        ImageView ivTasted = infoWindow.findViewById(R.id.ivTasted);
//                    ivTasted.setOnTouchListener((v, event) -> {
//                        ApiCallForTast(String.valueOf(model.getId()));
//                        return false;
//                    });

                        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                            @Override
                            public void onInfoWindowClick(Marker marker) {
//                                NearByRestaurent nearByRestaurent = (NearByRestaurent) marker.getTag();
//                                AppRepository.mPutValue(getActivity()).putString("restaurant_id", String.valueOf(nearByRestaurent.getId())).commit();
//                                startActivity(new Intent(getActivity(), DishDetailActivity.class));
                            }
                        });
                    }

                    return infoWindow;
                }

                @Override
                public View getInfoContents(Marker marker) {

                    return null;
                }
            });

        }
    };


    private void initData(GoogleMap googleMap) {
////        DialogBuilder.dialogBuilder(getActivity(), "");
//        Call<NearRestaurantsResponse> NearResaurantsResponseCall = BaseNetworking.ApiInterface(AppRepository.mAPIToken(getActivity())).getNearRestaurants(AppRepository.mLat(getActivity()), AppRepository.mLng(getActivity()));
//        NearResaurantsResponseCall.enqueue(new Callback<NearRestaurantsResponse>() {
//            @Override
//            public void onResponse(Call<NearRestaurantsResponse> call, Response<NearRestaurantsResponse> response) {
//
//                Log.d("zma Resturants", String.valueOf(response.message()));
//                if (response.isSuccessful()) {
//                    nearByRestaurents.addAll(response.body().getData().getNearByRestaurents());
//                    Log.d("zma data", String.valueOf(nearByRestaurents));
//
//                    for (int i = 0; i < nearByRestaurents.size(); i++) {
//                        Marker marker = googleMap.addMarker(markerOptions.position(new LatLng(Double.parseDouble(nearByRestaurents.get(i).getLatitude()),
//                                Double.parseDouble(nearByRestaurents.get(i).getLongitude()))).
//                                icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin)).
//                                snippet(nearByRestaurents.get(i).getRestaurentName()));
//                        marker.setTag(nearByRestaurents.get(i));
////                        marker.showInfoWindow();
//
//                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 13));
//                        markers.add(marker);
//
//                    }
//
//                } else {
//                    try {
//                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
//                        Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<NearRestaurantsResponse> call, Throwable t) {
//
//            }
//        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_map_view, container, false);


        return view;
    }
}