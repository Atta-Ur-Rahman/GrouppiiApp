//package com.techease.groupiiapplication.hotel.requests;
//
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.util.Log;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.DefaultRetryPolicy;
//import com.android.volley.NetworkError;
//import com.android.volley.NoConnectionError;
//import com.android.volley.ParseError;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.ServerError;
//import com.android.volley.TimeoutError;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;
//import com.google.gson.Gson;
//import com.techease.groupiiapplication.hotel.Interface.InitApi;
//import com.techease.groupiiapplication.hotel.api.ApiBaseResponseHandler;
//import com.techease.groupiiapplication.hotel.api.ApiCalls;
//import com.techease.groupiiapplication.hotel.api.ApiHandler;
//import com.techease.groupiiapplication.hotel.api.BaseResponseHandler;
//import com.techease.groupiiapplication.hotel.api.ResponseHandler;
//import com.techease.groupiiapplication.utils.AppUtils;
//import com.techease.groupiiapplication.utils.Logger;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static com.techease.groupiiapplication.utils.AppWideVariables.REQUEST_TIME_OUT;
//
//
//public class JsonObjectRequestCall extends ApiHandler {
//
//    private BaseResponseHandler responseHandler;
//    private ProgressDialog progressDialog = null;
//    private Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
//        @Override
//        public void onResponse(JSONObject response) {
//
//            try {
//                responseJson = response;
//                ResponseHandler rh;
//                if (isAlternate) {
//                    rh = new ResponseHandler(activity, calledApi, alternatebody, header, apiBaseResponseHandler);
//                } else {
//                    rh = new ResponseHandler(activity, calledApi, body, header, apiBaseResponseHandler);
//                }
//                responseHandler = rh;
//                responseHandler.setResponse(responseJson, response.toString());
//                responseHandler.handleResponse();
//
//                if (progressDialog != null && progressDialog.isShowing())
//                    progressDialog.dismiss();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    };
//
//    private Response.ErrorListener responseErrorListener = new Response.ErrorListener() {
//
//        @Override
//        public void onErrorResponse(VolleyError error) {
//            Log.i("Server volley error = ", error.toString());
//            if (progressDialog != null && progressDialog.isShowing())
//                progressDialog.dismiss();
//
//
//            if (error != null && error.networkResponse != null && error.networkResponse.statusCode == 401) {
//
//                final InitApi initApi = new InitApi() {
//
//                    @Override
//                    public HashMap<String, String> getBody() {
//                        HashMap<String, String> body = new HashMap<>();
////                        User user = App.getInstance().getDatabaseInstance().getUser();
////                        if (user == null) {
////                            body.put("UserName", "darewaro");//local
//                            body.put("UserName", "apiuser");//live
//                            body.put("Password", "Lmkt@12345");
//                            AppUtils.USERID = "";
////                        } else {
////                            body.put("UserName", user.getUserName());
////                            body.put("Password", "darewrouser");
////                            AppUtils.USERID = user.getId();
////                        }
//                        body.put("SecretKey", "nYA26svXRlbJRccDrxFxqOXztBKXMoZD");
//                        return body;
//                    }
//
//                    @Override
//                    public HashMap<String, String> getHeader() {
//                        HashMap<String, String> headers = new HashMap<>();
////                        headers.put("Content-Type", "application/x-www-form-urlencoded");
////                        headers.put("Content-Type", "application/json");
////                        headers.put("Accept", "application/json");
//                        return headers;
//                    }
//                };
//
////                Logger.LogFullString(Logger.INFO, "body = ", initApi.getBody().toString());
////                Logger.LogFullString(Logger.INFO, "Header = ", initApi.getHeader().toString());
//
//                JsonObjectRequest jsonRequestToken = new JsonObjectRequest(Request.Method.POST, ApiCalls.getGetToken(), new JSONObject(initApi.getBody()), new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
////                        Log.i("ResponseJson = ", response.toString());
//                        if (response.has("returnCode")) {
//                            try {
//                                if (response.getString("returnCode").equalsIgnoreCase("200")) {
//                                    String token = new JSONObject(response.getString("response")).getString("access_token");
////                                    SharedPreferenceHelper.saveString(SharedPreferenceHelper.TOKEN, token, activity);
//                                    if (response.has("user_object")) {
////                                        User user = new Gson().fromJson(String.valueOf(new JSONObject(response.getString("user_object"))), new TypeToken<User>() {
////                                        }.getType());
////                                        user.setToken(token);
////                                        user.setMsisdn(user.getMsisdn().replaceAll(" ",""));
////                                        App.getInstance().getDatabaseInstance().updateUser(user);
////
////                                        AppUtils.USERID = user.getId();
//                                    }
//                                    if (!calledApi.equalsIgnoreCase(ApiCalls.getGetToken())) {
//                                        header.put("Authorization", "Bearer " + token);
//                                    }
////                                    sendData();
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                        ResponseHandler rh;
//                        if (isAlternate) {
//                            rh = new ResponseHandler(activity, calledApi, alternatebody, header, apiBaseResponseHandler);
//                        } else {
//                            rh = new ResponseHandler(activity, calledApi, body, header, apiBaseResponseHandler);
//                        }
//                        responseHandler = rh;
//                        responseHandler.errorResponse();
//
//                    }
//                }) {
//
//                    /*     @Override
//                         public String getBodyContentType() {
//                             return "application/x-www-form-urlencoded; charset=UTF-8";
//                         }     */
//
//                    @Override
//                    public Map<String, String> getHeaders() throws AuthFailureError {
//                        return initApi.getHeader();
//                    }
//
//                };
//
//                if (jsonRequestToken != null) {
//                    jsonRequestToken.setRetryPolicy(new DefaultRetryPolicy((int) REQUEST_TIME_OUT, 0/* DefaultRetryPolicy.DEFAULT_MAX_RETRIES*/, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//                    RequestQueue requestQueue = Volley.newRequestQueue(activity.getApplicationContext());
//                    requestQueue.add(jsonRequestToken);
//                }
//            } else {
//                String errorMessage = error.getMessage();
//                if (error instanceof NetworkError) {
//                } else if (error instanceof ServerError) {
////                    errorMessage = "Internal Server Error!";//also contains 404
//                    errorMessage = "No Response From Server. Please Try Again Later!";
////                    Toast.makeText(activity, error.getMessage(), Toast.LENGTH_SHORT).show();
//                } else if (error instanceof AuthFailureError) {
//                    errorMessage = "Authentication Failure! ";
////                    Toast.makeText(activity, error.getMessage(), Toast.LENGTH_SHORT).show();
//                } else if (error instanceof ParseError) {
//                    errorMessage = "Parsing Error! ";
////                    Toast.makeText(activity, error.getMessage(), Toast.LENGTH_SHORT).show();
//                } else if (error instanceof NoConnectionError) {
//                    errorMessage = "Internet Connection Issue! ";
////                    Toast.makeText(activity, "Check your internet connection! ", Toast.LENGTH_SHORT).show();
//                } else if (error instanceof TimeoutError) {
//                    errorMessage = "Request Timeout! ";
////                    Toast.makeText(activity, error.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//
////            Toast.makeText(activity, "Check your internet connection!", Toast.LENGTH_SHORT).show();
//
//                ResponseHandler rh;
//                if (isAlternate) {
//                    rh = new ResponseHandler(activity, calledApi, alternatebody, header, apiBaseResponseHandler);
//                } else {
//                    rh = new ResponseHandler(activity, calledApi, body, header, apiBaseResponseHandler);
//                }
//                responseHandler = rh;
//                responseHandler.errorResponse(errorMessage);
//            }
////            Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
//        }
//    };
//
//    public JsonObjectRequestCall(Map<String, String> header, Map<String, String> body, String calledApi, int method, Activity activity, ApiBaseResponseHandler apiBaseResponseHandler) {
//        super(header, body, calledApi, method, activity, apiBaseResponseHandler);
//        this.header = header;
//        this.body = body;
//        this.calledApi = calledApi;
//        this.method = method;
//        this.activity = activity;
//        this.apiBaseResponseHandler = apiBaseResponseHandler;
//
//        if (body != null)
//            Logger.LogFullString(Logger.INFO, "body = ", body.toString());
//    }
//
//    public JsonObjectRequestCall(Map<String, String> header, JSONObject body, String calledApi, int method, Activity activity, boolean isAlternate, ApiBaseResponseHandler apiBaseResponseHandler) {
//        super(header, body, calledApi, method, activity, isAlternate, apiBaseResponseHandler);
//        this.header = header;
//        this.alternatebody = body;
//        this.calledApi = calledApi;
//        this.method = method;
//        this.activity = activity;
//        this.isAlternate = isAlternate;
//        this.apiBaseResponseHandler = apiBaseResponseHandler;
//
//        if (alternatebody != null)
//            Logger.LogFullString(Logger.INFO, "alternateBody = ", alternatebody.toString());
//    }}
