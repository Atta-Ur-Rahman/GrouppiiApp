package com.techease.groupiiapplication.api;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.techease.groupiiapplication.dataModel.tripDelete.DeleteTripResponse;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.ui.fragment.tripes.TripFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiClass {

    public static ApiCallback callback;


    public static void apiCallForTripDelete(Context context, ApiCallback callback, String tripId) {

        Toast.makeText(context, tripId, Toast.LENGTH_SHORT).show();
        Call<DeleteTripResponse> deleteTripResponseCall = BaseNetworking.ApiInterface().deleteTrip(tripId);
        deleteTripResponseCall.enqueue(new Callback<DeleteTripResponse>() {
            @Override
            public void onResponse(Call<DeleteTripResponse> call, Response<DeleteTripResponse> response) {

                if (response.isSuccessful()) {
                    Log.d("zma trip", "delete sho");
                    TripFragment.aBooleanRefreshAllTripApi = true;
                    callback.onResponse(true);


                } else {
                    Log.d("zma trip", String.valueOf(response));

                    callback.onResponse(false);

                }
            }

            @Override
            public void onFailure(Call<DeleteTripResponse> call, Throwable t) {

                callback.onResponse(false);
                Log.d("zma trip", "delete " + t.getMessage());

            }
        });
    }


}
