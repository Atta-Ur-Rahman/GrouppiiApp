package com.techease.groupiiapplication.hotel.apiHandler.presenter;

public interface BaseResponseListener {
    public void onSuccess(String calledApi);
    public void onError(String calledApi);
}
