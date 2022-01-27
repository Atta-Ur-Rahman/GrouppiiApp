
package com.techease.groupiiapplication.ui.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.api.ApiCallback;
import com.techease.groupiiapplication.dataModel.CommonResponse;
import com.techease.groupiiapplication.dataModel.addTrips.addTrip.AddTripResponse;
import com.techease.groupiiapplication.dataModel.getSingleTrip.GetSingleTripResponse;
import com.techease.groupiiapplication.interfaceClass.refreshChat.ConnectChatResfresh;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.ui.activity.LoginSignUp.LoginActivity;
import com.techease.groupiiapplication.ui.activity.LoginSignUp.SignUpActivity;
import com.techease.groupiiapplication.ui.activity.tripDetailScreen.TripDetailScreenActivity;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.AppRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {


    boolean aBooleanIsNotification = false;

    String shareTripId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        Uri uri = getIntent().getData();
        if (uri != null) {
            aBooleanIsNotification = true;
            shareTripId = uri.getQueryParameter("tripid");

            if (AppRepository.isLoggedIn(this)) {
                ApiCallAddUserToTrip(shareTripId);
            } else {
                AppRepository.mPutValue(this).putString("shareTripId", shareTripId).commit();
                startActivity(new Intent(this, LoginActivity.class));
            }
        }
        try {
            Bundle intent = getIntent().getExtras();
            if (getIntent().getExtras() != null) {
                if (AppRepository.isLoggedIn(this)) {
                    Intent chatActivityIntent = new Intent(SplashActivity.this, ChatsActivity.class);
                    if (intent.getString("messageType").equals("chat")) {
                        aBooleanIsNotification = true;

                        startActivity(new Intent(SplashActivity.this, HomeActivity.class));

                        Bundle bundle = new Bundle();
                        bundle.putString("title_name", intent.getString("name"));
                        bundle.putString("toUserId", intent.getString("fromuser"));
                        bundle.putString("type", "user");
                        bundle.putString("picture", intent.getString("picture"));
                        chatActivityIntent.putExtras(bundle);
                        SplashActivity.this.startActivity(chatActivityIntent);
                        SplashActivity.this.finish();

                    }

                    if (intent.getString("messageType").equals("group")) {
                        aBooleanIsNotification = true;

                        startActivity(new Intent(SplashActivity.this, HomeActivity.class));

                        Bundle bundle = new Bundle();
                        bundle.putString("title_name", intent.getString("title"));
                        bundle.putString("toUserId", intent.getString("fromuser"));
                        bundle.putString("tripId", intent.getString("tripid"));
                        bundle.putString("type", "group");
                        bundle.putString("picture", intent.getString("picture"));
                        chatActivityIntent.putExtras(bundle);
                        SplashActivity.this.startActivity(chatActivityIntent);
                        SplashActivity.this.finish();

                    }


                    if (intent.getString("messageType").equals("trip")) {
                        GetTripById(intent.getString("tripid"));
                    }
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }
            }

        } catch (Exception p) {
            p.printStackTrace();

        }

//        Geocoder gcd = new Geocoder(this, Locale.getDefault());
//        List<Address> addresses = null;
//        try {
//            addresses = gcd.getFromLocation(33.6970677,72.969148, 1);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (addresses.size() > 0) {
//            Toast.makeText(this, addresses.get(0).getCountryCode(), Toast.LENGTH_SHORT).show();
//            System.out.println(addresses.get(0).getLocality());
//        }
//        else {
//            // do your stuff
//        }
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        if (!aBooleanIsNotification) {
            new Handler().
                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (AppRepository.isLoggedIn(SplashActivity.this)) {

                                Intent mainIntent = new Intent(SplashActivity.this, HomeActivity.class);
                                SplashActivity.this.startActivity(mainIntent);
                                SplashActivity.this.finish();
                            } else {
                                Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                                SplashActivity.this.startActivity(mainIntent);
                                SplashActivity.this.finish();
                            }
                            finish();

                        }
                    }, 1000);

        }
    }

    private void ApiCallAddUserToTrip(String shareTripId) {
        Call<CommonResponse> addUserToTrip = BaseNetworking.ApiInterface().addUserToTrip("" + AppRepository.mUserID(this), shareTripId);
        addUserToTrip.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SplashActivity.this, "You Added to this trip", Toast.LENGTH_SHORT).show();
                    GetTripById(shareTripId);
                } else {
                    Toast.makeText(SplashActivity.this, "Some went wrong please try again", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Toast.makeText(SplashActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void GetTripById(String strTripID) {
        Call<GetSingleTripResponse> getSingleTripResponseCall = BaseNetworking.ApiInterface().getTripById("trips/getsingletrip/" + strTripID);
        getSingleTripResponseCall.enqueue(new Callback<GetSingleTripResponse>() {
            @Override
            public void onResponse(Call<GetSingleTripResponse> call, Response<GetSingleTripResponse> response) {
                if (response.isSuccessful()) {
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));


                    Intent intent = new Intent(SplashActivity.this, TripDetailScreenActivity.class);
                    Bundle bundle = new Bundle();
                    AppRepository.mPutValue(SplashActivity.this).putString("tripIDForUpdation", String.valueOf(response.body().getData().getId())).commit();

                    bundle.putString("image", response.body().getData().getCoverimage());
                    bundle.putString("title", response.body().getData().getTitle());
                    bundle.putString("trip_type", response.body().getData().getStatus());
                    bundle.putString("start_date", response.body().getData().getFromdate());
                    bundle.putString("end_date", response.body().getData().getTodate());
                    bundle.putString("pay_date", response.body().getData().getPayDate());
                    bundle.putString("description", response.body().getData().getDescription());
                    bundle.putString("location", response.body().getData().getLocation());
                    bundle.putBoolean("is_createdby", false);
                    intent.putExtras(bundle);
                    SplashActivity.this.startActivity(intent);
                    SplashActivity.this.finish();

                }
            }

            @Override
            public void onFailure(Call<GetSingleTripResponse> call, Throwable t) {

            }
        });
    }


    private void invitationDialog() {
        BottomSheetMaterialDialog mBottomSheetDialogd = new BottomSheetMaterialDialog.Builder(this)
                .setTitle("Invitation Trip?")
                .setMessage("Are you sure want to cancel this trip?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialogInterface, which) -> {
                    ApiCallback apiCallback = success -> {
                        SplashActivity.this.finish();
                        ConnectChatResfresh.setMyBoolean(true);
                        Toast.makeText(SplashActivity.this, "Trip cancel successfully", Toast.LENGTH_SHORT).show();
                        return false;
                    };
                    dialogInterface.dismiss();
                })
                .setNegativeButton("No", (dialogInterface, which) -> dialogInterface.dismiss())
                .build();

        // Show Dialog
        mBottomSheetDialogd.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


//        try {
//            mSocket = IO.socket("http://104.131.66.116:9000/");
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//
//
//        mSocket.on(Socket.EVENT_CONNECT, onConnect);
//        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
//        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
//        mSocket.connect();

//    private Emitter.Listener getOnNewMessage = new Emitter.Listener() {
//        @Override
//        public void call(Object... args) {
//
//        }
//    };
//    private Emitter.Listener onConnect = new Emitter.Listener() {
//        @Override
//        public void call(Object... args) {
////            Log.d("zma", "connected...");
//            // This doesn't run in the UI thread, so use:
//            // .runOnUiThread if you want to do something in the UI
//
//        }
//    };
//
//    private Emitter.Listener onConnectError = new Emitter.Listener() {
//        @Override
//        public void call(Object... args) {
////            Log.d("zma", "Error connecting...");
//        }
//    };
//
//    private Emitter.Listener onDisconnect = new Emitter.Listener() {
//        @Override
//        public void call(Object... args) {
////            Log.d("zma", "dis connecting...");
//        }
//    };
