package com.techease.groupiiapplication.ui.activity.AddTrip;

import static com.techease.groupiiapplication.utils.Constants.IS_FROM_NEW_SET_SCREEN;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.textfield.TextInputLayout;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.tripDetail.CustomSpinnerAdapter;
import com.techease.groupiiapplication.dataModel.addTrips.addTrip.AddTripDataModel;
import com.techease.groupiiapplication.dataModel.addTrips.addTrip.AddTripResponse;
import com.techease.groupiiapplication.dataModel.addTrips.publishTrip.PublishTripResponse;
import com.techease.groupiiapplication.dataModel.tripDetial.addPaymentExpenses.AddPaymentResponse;
import com.techease.groupiiapplication.interfaceClass.AddPaymentCallBackListener;
import com.techease.groupiiapplication.interfaceClass.AddPaymentOnBackListener;
import com.techease.groupiiapplication.interfaceClass.AddPaymentOnSetpFourCallBackListener;
import com.techease.groupiiapplication.interfaceClass.ClickPartiallyPaidTripListener;
import com.techease.groupiiapplication.interfaceClass.ClickRecentTransactionListener;
import com.techease.groupiiapplication.interfaceClass.EditPaymentCallBackListener;
import com.techease.groupiiapplication.interfaceClass.backParticipantsCostsClickInterface.ConnectParticipantCostsBackClick;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.ui.activity.HomeActivity;
import com.techease.groupiiapplication.ui.fragment.payment.AddPaymentFragment;
import com.techease.groupiiapplication.ui.fragment.payment.ParticipantCostsTabsFragment;
import com.techease.groupiiapplication.ui.fragment.tripDetialScreen.PaymentsFragment;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.Connectivity;
import com.techease.groupiiapplication.utils.Constants;
import com.techease.groupiiapplication.utils.DateUtills;
import com.techease.groupiiapplication.utils.ProgressBarAnimation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewTripStepFourPaymentActivity extends AppCompatActivity implements View.OnClickListener, EditPaymentCallBackListener, ClickRecentTransactionListener, AddPaymentOnBackListener, AddPaymentOnSetpFourCallBackListener, ClickPartiallyPaidTripListener {


    @BindView(R.id.btnDone)
    TextView btnDone;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    Dialog dialog;

    @BindView(R.id.tvTripTitle)
    TextView tvTripTitle;
    @BindView(R.id.ivAddPayment)
    ImageView ivAddPayment;

    @BindView(R.id.ivBack)
    ImageView ivBack;

    @BindView(R.id.llBottomSheetBehaviorId)
    LinearLayout llBottomSheetAddPayment;
    BottomSheetBehavior addPaymentBottomSheetBehavior;


    String strTripID, strPaymentAmount;

    int userID;

    public ArrayList<AddTripDataModel> userList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip_step_four_payment);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        dialog = AlertUtils.createProgressDialog(this);
        showDialog(this, "Add Expenses");
        ProcessBarAnimation();
        tvTripTitle.setText(AppRepository.mTripTitleName(this));
        userID = AppRepository.mUserID(this);
        strTripID = AppRepository.mTripIDForUpdation(this);
        addPaymentBottomSheetBehavior = BottomSheetBehavior.from(llBottomSheetAddPayment);

        gotoPaymentFragment();


    }

    private void gotoPaymentFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.clContianer, PaymentsFragment.newInstance())
                .commitNow();
    }


    private void ProcessBarAnimation() {
        ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, 75, 100);
        anim.setDuration(1000);
        progressBar.startAnimation(anim);
    }

    private void ApiCallPublishTrip() {
        dialog.show();
        Call<PublishTripResponse> getGalleryPhotoResponseCall = BaseNetworking.ApiInterface().publishTrip("trips/publish/" + strTripID);
        getGalleryPhotoResponseCall.enqueue(new Callback<PublishTripResponse>() {
            @Override
            public void onResponse(Call<PublishTripResponse> call, Response<PublishTripResponse> response) {
                if (response.isSuccessful()) {
                    ApiCallForAddExpenses();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<PublishTripResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(NewTripStepFourPaymentActivity.this, String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @OnClick({R.id.ivAddPayment, R.id.ivBack, R.id.btnDone})
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.ivAddPayment:
                addPaymentBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                AppRepository.mPutValue(this).putBoolean("add_payment_on_step_four", true).commit();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerPayment, AddPaymentFragment.newInstance("0", "AddPayment"))
                        .commitNow();
                break;
            case R.id.btnDone:
                Intent mainIntent = new Intent(NewTripStepFourPaymentActivity.this, HomeActivity.class);
                NewTripStepFourPaymentActivity.this.startActivity(mainIntent, ActivityOptions.makeSceneTransitionAnimation(NewTripStepFourPaymentActivity.this).toBundle());
                NewTripStepFourPaymentActivity.this.finishAffinity();
                break;
        }
    }


    @Override
    public void onPaymentBack() {
        addPaymentBottomSheetBehavior.setHideable(true);
        addPaymentBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }


    @Override
    public void onAddPaymentOnSetpFourBack() {
        addPaymentBottomSheetBehavior.setHideable(true);
        addPaymentBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        gotoPaymentFragment();


    }

    @Override
    public void goClickRecentTransaction() {
        IS_FROM_NEW_SET_SCREEN = false;
        Fragment myFragment = new ParticipantCostsTabsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.clContianer, myFragment).addToBackStack("").commit();

    }

    @Override
    public void goClickPartiallyPaidTrip() {

    }


    public void showDialog(Activity activity, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialogbox_otp);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        EditText etAmount = dialog.findViewById(R.id.etAmountFragment);
        TextView text = (TextView) dialog.findViewById(R.id.txt_file_path);
        text.setText(msg);

        TextView dialogBtn_cancel = dialog.findViewById(R.id.btnDone);
        dialogBtn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strPaymentAmount = etAmount.getText().toString();
                double payment = Double.parseDouble(strPaymentAmount);
                if (!strPaymentAmount.isEmpty() && strPaymentAmount.length() > 1 && payment > 0) {
                    ApiCallPublishTrip();
                    dialog.dismiss();
                } else {
                    etAmount.setError("invalid amount");
                }

            }
        });

        dialog.show();
    }


    private void ApiCallForAddExpenses() {
        dialog.show();
        Call<AddPaymentResponse> addPaymentResponseCall = BaseNetworking.ApiInterface().addPayment(strTripID, userID,
                strPaymentAmount, "Taxi", "Total Trip Cost", DateUtills.getCurrentDate("yyyy-MM-dd"), "Description", "0", userID + "", "PaymentMethod", "1");
        addPaymentResponseCall.enqueue(new Callback<AddPaymentResponse>() {
            @Override
            public void onResponse(Call<AddPaymentResponse> call, Response<AddPaymentResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();

                    //add payment bottom sheet
                    addPaymentBottomSheetBehavior.setHideable(true);
                    addPaymentBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.clContianer, PaymentsFragment.newInstance())
                            .commitNow();

                } else {
                    dialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        Toast.makeText(NewTripStepFourPaymentActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddPaymentResponse> call, Throwable t) {
                Log.d("zma addpayment error", String.valueOf(t.getMessage()));
                dialog.dismiss();
                if (t.getMessage().equals("java.lang.IllegalStateException: Expected BEGIN_OBJECT but was STRING at line 1 column 52 path $.data")) {
                    Toast.makeText(NewTripStepFourPaymentActivity.this, "Payment amount incorrect", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(NewTripStepFourPaymentActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onEditPaymentCallBack() {
        addPaymentBottomSheetBehavior.setHideable(true);
        addPaymentBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }
}