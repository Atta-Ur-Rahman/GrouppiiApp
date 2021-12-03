package com.techease.groupiiapplication.api;

import com.techease.groupiiapplication.dataModel.addTrips.addTrip.AddTripResponse;
import com.techease.groupiiapplication.dataModel.getSingleTrip.GetSingleTripResponse;

import retrofit2.Response;

public interface GetTripUserCallback {
    boolean onGetTripUser(Response<AddTripResponse> response);
}

