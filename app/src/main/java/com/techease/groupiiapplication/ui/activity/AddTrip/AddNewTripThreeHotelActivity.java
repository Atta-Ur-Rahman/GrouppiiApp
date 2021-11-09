package com.techease.groupiiapplication.ui.activity.AddTrip;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.addTrip.AutoCompleteCitiesAdapter;
import com.techease.groupiiapplication.adapter.addTrip.HotelAdapter;
import com.techease.groupiiapplication.dataModel.addTrips.OgodaHotel.HotelCityIdData;
import com.techease.groupiiapplication.dataModel.addTrips.OgodaHotel.OgodaHotelResponse;
import com.techease.groupiiapplication.dataModel.addTrips.OgodaHotel.Result;
import com.techease.groupiiapplication.dataModel.addTrips.ogodaHotelObject.AreaDataObject;
import com.techease.groupiiapplication.dataModel.addTrips.ogodaHotelObject.CriteriaDataObject;
import com.techease.groupiiapplication.dataModel.addTrips.ogodaHotelObject.MainHotelObject;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.ui.activity.HomeActivity;
import com.techease.groupiiapplication.ui.activity.WebViewActivity;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.Constants;
import com.techease.groupiiapplication.utils.KeyBoardUtils;
import com.techease.groupiiapplication.utils.ProgressBarAnimation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewTripThreeHotelActivity extends AppCompatActivity implements View.OnClickListener {


    String webViewUrl;
    private static final String HTML = "<!DOCTYPE html><html><body><a href='tel:867-5309'>Click here to call!</a></body></html>";
    private static final String TEL_PREFIX = "tel:";

    LinearLayoutManager linearLayoutManager;
    HotelAdapter hotelAdapter;
    List<Result> hotelDataModels = new ArrayList<>();
    @BindView(R.id.rvTripDetail)
    RecyclerView rvTripDetail;
    @BindView(R.id.btnReverse)
    Button btnReverse;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    Dialog dialog;
    @BindView(R.id.autocompleteCity)
    AutoCompleteTextView autoCompleteTextView;

    @BindView(R.id.tvTripTitle)
    TextView tvTripTitle;
    List<HotelCityIdData> hotelCityIdDataList = new ArrayList<>();

    AutoCompleteCitiesAdapter autoCompleteCitiesAdapter;
    int cityID = 2994;

    @BindView(R.id.webView)
    WebView webView;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_back_no_internet_connection)
    TextView tvNoInternetConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip_step_three);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        cityID = AppRepository.mCityId(this);
        tvTripTitle.setText(AppRepository.mCityName(this));
        initAdapter();
//        try {
//            apiCallGetTripDetail();
//        } catch (JSONException | IOException e) {
//            e.printStackTrace();
//        }
        ProcessBarAnimation();


        getCityIdes();


        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int itemPosition, long id) {

                KeyBoardUtils.closeKeyboard(AddNewTripThreeHotelActivity.this);
                KeyBoardUtils.hideKeyboard(AddNewTripThreeHotelActivity.this);

                cityID = hotelCityIdDataList.get(itemPosition).getCityId();
                try {
                    apiCallGetTripDetail();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });


        webViewUrl = "http://104.131.66.116";

        if (webViewUrl.contains("S.browser_fallback_url=")) {
            String last = webViewUrl.substring(webViewUrl.lastIndexOf("http") + 4);
            Log.d("zmaurl", "http" + last);
            webViewUrl = "http" + last;
        }

        dialog.show();

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.setWebViewClient(new HelloWebViewClient());
        progressBar.setVisibility(View.VISIBLE);
        webView.loadData(HTML, "text/html", "utf-8");
        webView.loadUrl(webViewUrl);


        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //This is the filter
                if (event.getAction() != KeyEvent.ACTION_DOWN)
                    return true;

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {

                        onBackPressed();
                    }
                    return true;
                }
                return false;
            }
        });


        webView.setWebChromeClient(new WebChromeClient() {

            public void onProgressChanged(WebView webView1, int newProgress) {

                Log.d("progress", String.valueOf(newProgress));

//                progressBar.setProgress(newProgress);

//
                if (newProgress == 100) {
                    progressBar.setProgress(0);
                    dialog.dismiss();
                }
            }
        });


    }


    private class HelloWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            progressBar.setProgress(10);

            if (url.contains("S.browser_fallback_url=")) {
                String last = url.substring(url.lastIndexOf("http") + 4);
                Log.d("zmaurl", "http" + last);
                url = "http" + last;
            }
            Log.d("zma url", url);

            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (url.contains("S.browser_fallback_url=")) {
                String last = url.substring(url.lastIndexOf("http") + 4);
                Log.d("zmaurl", "http" + last);
                url = "http" + last;

                webView.loadUrl(url);
            }
            final Uri uri = Uri.parse(url);
            final String scheme = uri.getScheme();

            if (scheme != null) {
                final Intent externalSchemeIntent;
                switch (scheme) {
                    case "tel":
                        externalSchemeIntent = new Intent(Intent.ACTION_DIAL, uri);
                        break;
                    case "sms":
                    case "mailto":
                        externalSchemeIntent = new Intent(Intent.ACTION_SENDTO, uri);
                        break;
                    case "whatsapp":
                        externalSchemeIntent = new Intent(Intent.ACTION_SENDTO, uri);
                        externalSchemeIntent.setPackage("com.whatsapp");
                        break;
                    default:
                        externalSchemeIntent = null;
                        break;
                }

                if (externalSchemeIntent != null) {
                    externalSchemeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(externalSchemeIntent);
                    // cancel the original request
                    return true;
                }
            }

            if (url.startsWith(TEL_PREFIX)) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                return true;
            } else if (url.endsWith(".mp4")) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
                view.getContext().startActivity(intent);
                return true;
            } else {
                return super.shouldOverrideUrlLoading(view, url);

            }


        }

        @Override
        public void onPageFinished(WebView view, String url) {

//            progressBar.setProgress(0);
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError
                error) {
            super.onReceivedError(view, request, error);

//            progressBar.setProgress(0);
        }


    }


    private void getCityIdes() {


        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray("cities");
            ArrayList<HashMap<String, String>> formList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m_li;


            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
//                Log.d("Details-->", jo_inside.getString("city"));
                String city_id = jo_inside.getString("city_id");
                String cityName = jo_inside.getString("city");

                HotelCityIdData hotelCityIdData = new HotelCityIdData();
                hotelCityIdData.setCityId(Integer.parseInt(city_id));
                hotelCityIdData.setCityName(cityName);
                hotelCityIdDataList.add(hotelCityIdData);


//                //Add your values in your `ArrayList` as below:
//                m_li = new HashMap<String, String>();
//                m_li.put("city id", city_id);
//                m_li.put("city name", cityName);
//
//                formList.add(m_li);
            }


            autoCompleteCitiesAdapter = new AutoCompleteCitiesAdapter(this, hotelCityIdDataList);
            autoCompleteTextView.setAdapter(autoCompleteCitiesAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = null;
            try {
                is = getAssets().open("hotel_json.json");
            } catch (IOException e) {
                e.printStackTrace();
            }
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void ProcessBarAnimation() {
        ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, 50, 75);
        anim.setDuration(1000);
        progressBar.startAnimation(anim);
    }

    private void apiCallGetTripDetail() throws JSONException, IOException {
        dialog.show();
        hotelDataModels.clear();

        MainHotelObject mainObject = new MainHotelObject();
        AreaDataObject areaDataObject = new AreaDataObject();
        CriteriaDataObject criteriaDataObject = new CriteriaDataObject();

        areaDataObject.setId(26023);
        areaDataObject.setCityId(229);

        criteriaDataObject.setCityId(cityID);
        criteriaDataObject.setCheckInDate("2021-10-14");
        criteriaDataObject.setCheckOutDate("2021-10-15");

        mainObject.setCriteriaDataObject(criteriaDataObject);


        Call<OgodaHotelResponse> ogodaHotelResponseCall = BaseNetworking.ApiInterfaceForHotel().getAllHotel(mainObject);
        ogodaHotelResponseCall.enqueue(new Callback<OgodaHotelResponse>() {
            @Override
            public void onResponse(Call<OgodaHotelResponse> call, Response<OgodaHotelResponse> response) {

                if (response.isSuccessful()) {
                    hotelDataModels.addAll(response.body().getResults());
                    hotelAdapter.notifyDataSetChanged();
                }
                dialog.dismiss();

            }

            @Override
            public void onFailure(Call<OgodaHotelResponse> call, Throwable t) {
            }

        });
    }

    private void initAdapter() {

        linearLayoutManager = new LinearLayoutManager(this);
        dialog = AlertUtils.createProgressDialog(this);
        hotelAdapter = new HotelAdapter(this, hotelDataModels);
        rvTripDetail.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rvTripDetail.setAdapter(hotelAdapter);
        rvTripDetail.setNestedScrollingEnabled(true);
//        Collections.reverse(tripDetailDataModels);
    }

    @OnClick({R.id.ivBack, R.id.btnReverse})
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btnReverse:
//                finish();
                startActivity(new Intent(AddNewTripThreeHotelActivity.this, NewTripStepFourPaymentActivity.class), ActivityOptions.makeSceneTransitionAnimation(AddNewTripThreeHotelActivity.this).toBundle());

//                startActivity(new Intent(this, NewTripStepFourPaymentActivity.class));
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Constants.aBooleanAddedTripApi) {
            finish();
        }
    }
}