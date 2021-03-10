package com.techease.groupiiapplication.hotel.apiHandler;

import android.app.Activity;


import com.techease.groupiiapplication.hotel.apiHandler.presenter.BaseResponseListener;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class ApiBaseResponseHandler {

    Activity activity;
    String calledApi;
    BaseResponseListener baseResponseListener;

    public ApiBaseResponseHandler(Activity activity, String calledApi, BaseResponseListener baseResponseListener) {
        this.activity = activity;
        this.calledApi = calledApi;
        this.baseResponseListener = baseResponseListener;
    }

    public abstract void setApiResponse(JSONObject jsonObject, String calledApi);

    public abstract void setApiResponse(JSONArray jsonArray, String calledApi);

    public abstract void setApiResponse(String jsonAsString, String calledApi);

    public abstract void setApiError(String calledApi);

    public abstract void setApiError(String errorMessage, String calledApi);

    public abstract void apiResponseWithoutObject(String calledApi);

}
