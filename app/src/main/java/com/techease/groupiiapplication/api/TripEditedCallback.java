package com.techease.groupiiapplication.api;

import com.techease.groupiiapplication.dataModel.getSingleTrip.GetSingleTripResponse;

import retrofit2.Response;

public interface TripEditedCallback {
    boolean onTripEdited( Response<GetSingleTripResponse> response);
}

