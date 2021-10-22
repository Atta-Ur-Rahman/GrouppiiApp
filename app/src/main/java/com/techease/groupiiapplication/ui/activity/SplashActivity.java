
package com.techease.groupiiapplication.ui.activity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.ui.activity.LoginSignUp.LoginActivity;
import com.techease.groupiiapplication.ui.activity.LoginSignUp.SignUpActivity;
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
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SplashActivity extends AppCompatActivity {

    private Socket mSocket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        Uri uri = getIntent().getData();
        String strUsername = "";
        if (uri != null) {
            strUsername = uri.getQueryParameter("tripid");
//            Toast.makeText(this, strUsername, Toast.LENGTH_SHORT).show();
        }
        else {

            // Your app will pop up even if http://www.myurl.com/sso is clicked, so better to handle null uri
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
        new Handler().postDelayed(new Runnable() {
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
        }, 000);

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

    }


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


}