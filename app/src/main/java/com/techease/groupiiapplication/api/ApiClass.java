package com.techease.groupiiapplication.api;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.techease.groupiiapplication.dataModel.addTrips.tripDelete.DeleteTripResponse;
import com.techease.groupiiapplication.dataModel.getSingleTrip.GetSingleTripResponse;
import com.techease.groupiiapplication.dataModel.tripDetial.deleteTripUser.DeleteTripUserResponse;
import com.techease.groupiiapplication.interfaceClass.refreshChat.ConnectChatResfresh;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.ui.activity.HomeActivity;
import com.techease.groupiiapplication.ui.activity.tripDetailScreen.TripDetailScreenActivity;
import com.techease.groupiiapplication.ui.fragment.chat.AllUsersChatFragment;
import com.techease.groupiiapplication.ui.fragment.tripes.TripFragment;
import com.techease.groupiiapplication.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiClass {


    public static void ApiCallForDeleteTripUser(TripUserDeleteCallback tripUserDeleteCallback, String strTripId, String strUserID) {
        Call<DeleteTripUserResponse> deleteTripUserResponseCall = BaseNetworking.ApiInterface().deleteTripUser(strTripId, strUserID);
        deleteTripUserResponseCall.enqueue(new Callback<DeleteTripUserResponse>() {
            @Override
            public void onResponse(Call<DeleteTripUserResponse> call, Response<DeleteTripUserResponse> response) {
                if (response.isSuccessful()) {
                    tripUserDeleteCallback.onResponse(true);
                } else {
                    tripUserDeleteCallback.onResponse(false);

                }
            }

            @Override
            public void onFailure(Call<DeleteTripUserResponse> call, Throwable t) {

                tripUserDeleteCallback.onResponse(false);
            }
        });
    }


    public static void apiCallForTripDelete(Context context, ApiCallback callback, String tripId) {

//        Toast.makeText(context, tripId, Toast.LENGTH_SHORT).show();
        Call<DeleteTripResponse> deleteTripResponseCall = BaseNetworking.ApiInterface().deleteTrip(tripId);
        deleteTripResponseCall.enqueue(new Callback<DeleteTripResponse>() {
            @Override
            public void onResponse(Call<DeleteTripResponse> call, Response<DeleteTripResponse> response) {

                if (response.isSuccessful()) {
                    Log.d("zma trip", "delete sho");
                    Constants.aBooleanRefreshAllTripApi = true;
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


    public static void GetTripById(String strTripID, Context context, Dialog dialog, TripEditedCallback tripEditedCallback) {
        dialog.show();
        Call<GetSingleTripResponse> getSingleTripResponseCall = BaseNetworking.ApiInterface().getTripById("trips/getsingletrip/" + strTripID);
        getSingleTripResponseCall.enqueue(new Callback<GetSingleTripResponse>() {
            @Override
            public void onResponse(Call<GetSingleTripResponse> call, Response<GetSingleTripResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(context, "Trip updated successfully", Toast.LENGTH_SHORT).show();


//                    Intent intent = new Intent(context, TripDetailScreenActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("image", response.body().getData().getCoverimage());
//                    bundle.putString("title", response.body().getData().getTitle());
//                    bundle.putString("trip_type", response.body().getData().getStatus());
//                    bundle.putString("start_date", response.body().getData().getFromdate());
//                    bundle.putString("end_date", response.body().getData().getTodate());
//                    bundle.putString("pay_date", response.body().getData().getPayDate());
//                    bundle.putString("description", response.body().getData().getDescription());
//                    bundle.putString("location", response.body().getData().getLocation());
//                    bundle.putBoolean("is_createdby", false);
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);

                    tripEditedCallback.onTripEdited(response);

                } else {
                    dialog.dismiss();
                    Toast.makeText(context, "some went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetSingleTripResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}
