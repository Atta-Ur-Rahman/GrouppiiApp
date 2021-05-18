package com.techease.groupiiapplication.ui.activity.AddTrip;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.addTrip.AutoCompleteCitiesAdapter;
import com.techease.groupiiapplication.adapter.addTrip.HotelAdapter;
import com.techease.groupiiapplication.dataModel.OgodaHotel.HotelCityIdData;
import com.techease.groupiiapplication.dataModel.OgodaHotel.OgodaHotelResponse;
import com.techease.groupiiapplication.dataModel.OgodaHotel.Result;
import com.techease.groupiiapplication.dataModel.ogodaHotelObject.AreaDataObject;
import com.techease.groupiiapplication.dataModel.ogodaHotelObject.CriteriaDataObject;
import com.techease.groupiiapplication.dataModel.ogodaHotelObject.MainHotelObject;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.GeneralUtills;
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
    List<HotelCityIdData> hotelCityIdDataList = new ArrayList<>();

    AutoCompleteCitiesAdapter autoCompleteCitiesAdapter;
    int cityID = 2994;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip_step_three);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        initAdapter();
        try {
            apiCallGetTripDetail();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
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
}