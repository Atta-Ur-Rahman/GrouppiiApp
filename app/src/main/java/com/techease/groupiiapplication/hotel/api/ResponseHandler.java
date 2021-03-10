//package com.techease.groupiiapplication.hotel.api;
//
//
//import android.app.Activity;
//import android.text.TextUtils;
//
//import com.lmkt.darewro.customer.data.api.handlers.ApiBaseResponseHandler;
//import com.lmkt.darewro.customer.view.utils.AppUtils;
//import com.lmkt.darewro.customer.view.utils.Logger;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.Map;
//
///**
// * Created by Ahmad Abbas Zaidi on 3/21/2017.
// */
//
//public class ResponseHandler extends BaseResponseHandler {
//
//    private static final String OK = "200";
//    private static final String ERROR = "500";
//    private static final String BAD_REQUEST = "400";
//
//    public ResponseHandler(Activity activity, String calledApi, Map<String, String> body, Map<String, String> headers, ApiBaseResponseHandler apiBaseResponseHandler) {
//        super(activity, calledApi, body, headers, apiBaseResponseHandler);
//    }
//
//    public ResponseHandler(Activity activity, String calledApi, JSONObject alternateBody, Map<String, String> headers, ApiBaseResponseHandler apiBaseResponseHandler) {
//        super(activity, calledApi, alternateBody, headers, apiBaseResponseHandler);
//    }
//
//    @Override
//    public void errorResponse() {
//        apiBaseResponseHandler.setApiError(calledApi);
//    }
//
//    @Override
//    public void setResponse(JSONObject responseJson, String responseString) {
//        this.responseJson = responseJson;
//        this.responseString = responseString;
//    }
//
//    @Override
//    public void handleResponse() {
//
//        //apiResponseListener.setApiResponse(responseJson, calledApi);
//        try {
//            Logger.LogFullString(Logger.INFO, "ResponseJson = ", calledApi + " " + responseJson);
//            if (responseJson.has("returnCode")) {
//                String status = responseJson.getString("returnCode");
//                if (status.equalsIgnoreCase(OK)) {
//                    String errorMessage = responseJson.getString("message");
//                    if (errorMessage != null && !TextUtils.isEmpty(errorMessage) && !errorMessage.equalsIgnoreCase("null"))
//                        AppUtils.makeNotification(errorMessage, activity);
//                    if (responseJson.has("response") && responseJson.get("response") != null) {
//                        if (responseJson.get("response") instanceof JSONObject) {
//                            JSONObject result = responseJson.getJSONObject("response");
//                            apiBaseResponseHandler.setApiResponse(result, calledApi);
//                        } else if (responseJson.get("response") instanceof JSONArray) {
//                            JSONArray result = responseJson.getJSONArray("response");
//                            apiBaseResponseHandler.setApiResponse(result, calledApi);
//                        } else if (responseJson.get("response") instanceof String) {
//                            String result = responseJson.getString("response");
//                            apiBaseResponseHandler.setApiResponse(result, calledApi);
//                        } else {
//                            apiBaseResponseHandler.setApiResponse(responseJson, calledApi);
//                        }
//                    } else {
//                        apiBaseResponseHandler.apiResponseWithoutObject(calledApi);
//                    }
//
//                } else {
//                    String errorMessage = responseJson.getString("message");
//                    apiBaseResponseHandler.setApiError(errorMessage, calledApi);
//                    if (errorMessage != null && !TextUtils.isEmpty(errorMessage) && !errorMessage.equalsIgnoreCase("null"))
//                        AppUtils.makeNotification(errorMessage, activity);
////                    Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show();
//                }/*else if (status.equalsIgnoreCase(BAD_REQUEST)) {
//                    String errorMessage = responseJson.getString("message");
//                    apiBaseResponseHandler.setApiError(errorMessage, calledApi);
//                    AppUtils.makeNotification(errorMessage, activity);
////                    Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show();
//                } else if (status.equalsIgnoreCase(ERROR)) {
//                    String errorMessage = responseJson.getString("message");
//                    apiBaseResponseHandler.setApiError(errorMessage, calledApi);
//                    AppUtils.makeNotification(errorMessage, activity);
////                    Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show();
//                }*/
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void errorResponse(String error) {
//        apiBaseResponseHandler.setApiError(error, calledApi);
//    }
//}
