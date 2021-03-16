package com.techease.groupiiapplication.hotel.apiHandler.presenter;

public interface ResponseListenerSignup extends BaseResponseListener{
    public void onSuccess(String calledApi, String id, String code, String token);
    public void onError(String calledApi, String message);
}
