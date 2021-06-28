package com.techease.groupiiapplication.ui.activity.AddTrip;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.textfield.TextInputLayout;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.tripDetail.CustomSpinnerAdapter;
import com.techease.groupiiapplication.dataModel.addTrips.addTrip.AddTripDataModel;
import com.techease.groupiiapplication.dataModel.addTrips.addTrip.AddTripResponse;
import com.techease.groupiiapplication.dataModel.addTrips.publishTrip.PublishTripResponse;
import com.techease.groupiiapplication.dataModel.tripDetial.addPaymentExpenses.AddPaymentResponse;
import com.techease.groupiiapplication.dataModel.tripDetial.getPaymentExpenses.GetPaymentExpensesResponse;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.ui.activity.HomeActivity;
import com.techease.groupiiapplication.ui.activity.LoginSignUp.LoginActivity;
import com.techease.groupiiapplication.ui.activity.tripDetailScreen.TripDetailScreenActivity;
import com.techease.groupiiapplication.ui.activity.tripDetailScreen.getExpenditureExpensesListener.ConnectExpenditures;
import com.techease.groupiiapplication.ui.fragment.tripes.TripFragment;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.Connectivity;
import com.techease.groupiiapplication.utils.DateUtills;
import com.techease.groupiiapplication.utils.KeyBoardUtils;
import com.techease.groupiiapplication.utils.ProgressBarAnimation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.tankery.lib.circularseekbar.CircularSeekBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewTripStepFourPaymentActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {


    Dialog addActivityTypeDialog;

    @BindView(R.id.csPayment)
    CircularSeekBar circularSeekBar;
    @BindView(R.id.btnDone)
    Button btnDone;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    Dialog dialog;

    @BindView(R.id.ivAddPayment)
    ImageView ivAddPayment;


    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvPartiallyPaid)
    TextView tvPartiallyPaid;
    @BindView(R.id.tvPaidNumber)
    TextView tvPaidNumber;


    @BindView(R.id.tvPercentage)
    TextView tvPartiallyPaidPercentage;


    boolean valid;
    ImageView ivPaymentBack;

    @BindView(R.id.llBottomSheetBehaviorId)
    LinearLayout llBottomSheetAddPayment;
    LinearLayout llPaymentMethod;
    BottomSheetBehavior addPaymentBottomSheetBehavior;

    LinearLayout llTaxi, llBus, llReserv, llFlight;


    @BindView(R.id.spUserName)
    Spinner spUserName;

    String strTitle, strPhoto, strPaymentTitle, strPaymentDate, strPaymentAmount, strPaymentShortDescription, strPaymentMethod = "VISA", strPaymentUser;


    TextInputLayout tillPaymentTitle, tillPaymentDate, tillPaymentAmount, tillShortDescription;
    EditText etPaymentTitle, etPaymentDate, etPaymentAmount, etShortDescription;
    String strIsPersonal = "0", strActivityType, strPersonal = "1";
    ImageView ivAddPaymentBack, ivType;
    ImageView ivVisa, ivMasterCard, ivAmericanCard, ivJcb;
    TextView tvAddPayment;
    SwitchCompat switchCompatGroupActivity, swAddGroupPayment;

    public ArrayList<AddTripDataModel> userList = new ArrayList<>();

    CustomSpinnerAdapter customSpinnerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip_step_four_payment);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        dialog = AlertUtils.createProgressDialog(this);
        circularSeekBar.setEnabled(false);
        ProcessBarAnimation();


        addPaymentBottomSheetBehavior = BottomSheetBehavior.from(llBottomSheetAddPayment);

        addPaymentBottomSheet();

        userList = NewTripStepOneInviteFriendActivity.addTripDataModels;
        customSpinnerAdapter = new CustomSpinnerAdapter(getApplicationContext(), userList);
        spUserName.setAdapter(customSpinnerAdapter);
        ApiCallGetUserTrip();


        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiCallPublishTrip();
            }
        });


    }


    private void ProcessBarAnimation() {
        ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, 75, 100);
        anim.setDuration(1000);
        progressBar.startAnimation(anim);
    }

    private void ApiCallPublishTrip() {
        dialog.show();
        Call<PublishTripResponse> getGalleryPhotoResponseCall = BaseNetworking.ApiInterface().publishTrip("trips/publish/" + AppRepository.mTripId(this));
        getGalleryPhotoResponseCall.enqueue(new Callback<PublishTripResponse>() {
            @Override
            public void onResponse(Call<PublishTripResponse> call, Response<PublishTripResponse> response) {
                if (response.isSuccessful()) {
                    TripFragment.aBooleanRefreshAllTripApi = true;
                    Intent mainIntent = new Intent(NewTripStepFourPaymentActivity.this, HomeActivity.class);
                    NewTripStepFourPaymentActivity.this.startActivity(mainIntent,ActivityOptions.makeSceneTransitionAnimation(NewTripStepFourPaymentActivity.this).toBundle());
                    NewTripStepFourPaymentActivity.this.finishAffinity();
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

    private void addPaymentBottomSheet() {
        //add payment
        ivAddPaymentBack = llBottomSheetAddPayment.findViewById(R.id.ivAddPaymentBack);
        ivType = llBottomSheetAddPayment.findViewById(R.id.ivType);
        llPaymentMethod = llBottomSheetAddPayment.findViewById(R.id.llPaymentMethod);


        tillPaymentTitle = llBottomSheetAddPayment.findViewById(R.id.tilPaymentTitle);
        tillPaymentDate = llBottomSheetAddPayment.findViewById(R.id.tillAddPaymentDate);
        tillPaymentAmount = llBottomSheetAddPayment.findViewById(R.id.tillAmount);
        tillShortDescription = llBottomSheetAddPayment.findViewById(R.id.tilAddPaymentShortDescription);

        etPaymentTitle = llBottomSheetAddPayment.findViewById(R.id.etPaymentTitle);
        etPaymentDate = llBottomSheetAddPayment.findViewById(R.id.etAddPaymentDate);
        etPaymentAmount = llBottomSheetAddPayment.findViewById(R.id.etAmount);
        etShortDescription = llBottomSheetAddPayment.findViewById(R.id.etAddPaymentShortDescription);

        tvAddPayment = llBottomSheetAddPayment.findViewById(R.id.tvPaymentAdd);
        swAddGroupPayment = llBottomSheetAddPayment.findViewById(R.id.swAddGroupPayment);

//        spUserName = llBottomSheetAddPayment.findViewById(R.id.spUserName);
        spUserName.setOnItemSelectedListener(this);
        ivVisa = llBottomSheetAddPayment.findViewById(R.id.ivVisa);
        ivMasterCard = llBottomSheetAddPayment.findViewById(R.id.ivMastercard);
        ivJcb = llBottomSheetAddPayment.findViewById(R.id.ivJcb);
        ivAmericanCard = llBottomSheetAddPayment.findViewById(R.id.ivAmericanExpress);


        strIsPersonal = "1";
        swAddGroupPayment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    strIsPersonal = "0";
                } else {
                    strIsPersonal = "1";

                }
            }
        });


        ivAddPaymentBack.setOnClickListener(this);
        tvAddPayment.setOnClickListener(this);
        etPaymentDate.setOnClickListener(this);
//        etPaymentUser.setOnClickListener(this);
        llPaymentMethod.setOnClickListener(this);
        ivType.setOnClickListener(this);


        ivVisa.setOnClickListener(this);
        ivMasterCard.setOnClickListener(this);
        ivJcb.setOnClickListener(this);
        ivAmericanCard.setOnClickListener(this);


        Log.d("zma usr", "" + TripFragment.userList);


    }


    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
//        Toast.makeText(getApplicationContext(), countryNames[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @OnClick({R.id.ivAddPayment, R.id.ivBack})
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.ivAddPayment:
                addPaymentBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.ivAddPaymentBack:
                addPaymentBottomSheetBehavior.setHideable(true);
                addPaymentBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                break;
            case R.id.tvPaymentAdd:
                if (isValidAddPayment()) {
                    ApiCallForAddPayment();
                }
                break;

            case R.id.ivType:
                activityTripTypeDialog();
                break;
            case R.id.llTaxi:
                ivType.setImageResource(R.mipmap.taxi_wheel);
                strActivityType = "Taxi";
                addActivityTypeDialog.dismiss();
                break;
            case R.id.llFlight:
                ivType.setImageResource(R.mipmap.flight);
                strActivityType = "Flight";
                addActivityTypeDialog.dismiss();
                break;
            case R.id.llBus:
                ivType.setImageResource(R.mipmap.transfer);
                strActivityType = "Bus";
                addActivityTypeDialog.dismiss();

                break;
            case R.id.llReserv:
                ivType.setImageResource(R.mipmap.reserv_selected);
                strActivityType = "hotel";
                addActivityTypeDialog.dismiss();
                break;
            case R.id.ivVisa:
                HighliteImage(ivVisa, ivMasterCard, ivJcb, ivAmericanCard);
                strPaymentMethod = "Visa";

                break;
            case R.id.ivMastercard:
                HighliteImage(ivMasterCard, ivVisa, ivJcb, ivAmericanCard);
                strPaymentMethod = "Mastercard";

                break;
            case R.id.ivJcb:
                HighliteImage(ivJcb, ivMasterCard, ivVisa, ivAmericanCard);
                strPaymentMethod = "JCB";

                break;
            case R.id.ivAmericanExpress:
                HighliteImage(ivAmericanCard, ivMasterCard, ivJcb, ivVisa);
                strPaymentMethod = "American Express";
                break;
            case R.id.etAddPaymentDate:
                DateUtills.GetDatePickerDialog(etPaymentDate, this);
                break;
        }
    }

    private void HighliteImage(ImageView iv1, ImageView iv2, ImageView iv3, ImageView iv4) {
        iv1.getDrawable().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
        iv2.getDrawable().setColorFilter(0x00000000, PorterDuff.Mode.SRC_ATOP);
        iv3.getDrawable().setColorFilter(0x00000000, PorterDuff.Mode.SRC_ATOP);
        iv4.getDrawable().setColorFilter(0x00000000, PorterDuff.Mode.SRC_ATOP);

    }


    void activityTripTypeDialog() {
        addActivityTypeDialog = new Dialog(this);
        addActivityTypeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        addActivityTypeDialog.setCancelable(true);
        addActivityTypeDialog.setContentView(R.layout.custom_activity_type_layout);
        llTaxi = addActivityTypeDialog.findViewById(R.id.llTaxi);
        llBus = addActivityTypeDialog.findViewById(R.id.llBus);
        llFlight = addActivityTypeDialog.findViewById(R.id.llFlight);
        llReserv = addActivityTypeDialog.findViewById(R.id.llReserv);

        llTaxi.setOnClickListener(this);
        llBus.setOnClickListener(this);
        llFlight.setOnClickListener(this);
        llReserv.setOnClickListener(this);

        addActivityTypeDialog.show();
        AlertUtils.doKeepDialog(addActivityTypeDialog);
        addActivityTypeDialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        addActivityTypeDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });

    }


    private void ApiCallForAddPayment() {

        dialog.show();
        Call<AddPaymentResponse> addPaymentResponseCall = BaseNetworking.ApiInterface().addPayment(AppRepository.mTripId(this), AppRepository.mUserID(this),
                strPaymentAmount, strActivityType, strPaymentTitle, strPaymentDate, strPaymentShortDescription, strIsPersonal, AppRepository.mUserID(this), strPaymentMethod);
        addPaymentResponseCall.enqueue(new Callback<AddPaymentResponse>() {
            @Override
            public void onResponse(Call<AddPaymentResponse> call, Response<AddPaymentResponse> response) {

                Log.d("zma addpayment", String.valueOf(response));
                if (response.isSuccessful()) {
                    dialog.dismiss();

                    //add payment bottom sheet
                    addPaymentBottomSheetBehavior.setHideable(true);
                    addPaymentBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    etPaymentTitle.setText("");
                    etPaymentDate.setText("");
                    etPaymentAmount.setText("");
                    etShortDescription.setText("");

                    KeyBoardUtils.hideKeyboard(NewTripStepFourPaymentActivity.this);
                    KeyBoardUtils.closeKeyboard(NewTripStepFourPaymentActivity.this);

                    getPaymentExpenses();
                }
            }

            @Override
            public void onFailure(Call<AddPaymentResponse> call, Throwable t) {
                Log.d("zma addpayment error", String.valueOf(t.getMessage()));

                Toast.makeText(NewTripStepFourPaymentActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void getPaymentExpenses() {
        dialog.show();
        Call<GetPaymentExpensesResponse> getPaymentExpensesResponseCall = BaseNetworking.ApiInterface().getPaymentExpenses(AppRepository.mTripId(this), AppRepository.mUserID(this));
        getPaymentExpensesResponseCall.enqueue(new Callback<GetPaymentExpensesResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<GetPaymentExpensesResponse> call, Response<GetPaymentExpensesResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    tvPartiallyPaidPercentage.setText(response.body().getData().getPaidPercent() + "%");
                    tvPartiallyPaid.setText(response.body().getData().getFullyPaidUsers() + "/" + response.body().getData().getTotalUsers());
                    circularSeekBar.setProgress(response.body().getData().getPaidPercent());
                    tvPaidNumber.setText("" + response.body().getData().getFullyPaidUsers());
                    ConnectExpenditures.setMyBooleanListener(true);

                } else {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<GetPaymentExpensesResponse> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

    @SuppressLint("ResourceType")
    private boolean isValidAddPayment() {
        valid = true;
        strPaymentTitle = etPaymentTitle.getText().toString();
        strPaymentDate = etPaymentDate.getText().toString();
        strPaymentAmount = etPaymentAmount.getText().toString();
        strPaymentShortDescription = etShortDescription.getText().toString();

        if (strPaymentTitle.isEmpty()) {
            tillPaymentTitle.setErrorEnabled(true);
            tillPaymentTitle.setError(getString(R.string.plesase_write_your_title));
            valid = false;
        } else {
            tillPaymentTitle.setError(null);
        }
        if (strPaymentShortDescription.isEmpty()) {
            valid = false;
            tillShortDescription.setErrorEnabled(true);
            tillShortDescription.setError(getString(R.string.plesase_write_your_description));

        } else {
            tillShortDescription.setError(null);
            tillShortDescription.setErrorEnabled(false);
        }


        if (strPaymentDate.isEmpty()) {
            valid = false;
            tillPaymentDate.setErrorEnabled(true);
            tillPaymentDate.setError(getString(R.string.plesase_write_date));

        } else {
            tillPaymentDate.setError(null);
            tillPaymentDate.setErrorEnabled(false);
        }
        if (strPaymentAmount.isEmpty()) {
            valid = false;
            tillPaymentAmount.setErrorEnabled(true);
            tillPaymentAmount.setError(getString(R.string.plesase_write_payment_amount));

        } else {
            tillPaymentAmount.setError(null);
            tillPaymentAmount.setErrorEnabled(false);
        }
        if (!Connectivity.isConnected(this)) {
            valid = false;
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }


        return valid;
    }


    private void ApiCallGetUserTrip() {
        userList.clear();
        try {
            AddTripDataModel addTripDataModel = new AddTripDataModel();
            addTripDataModel.setEmail(AppRepository.mEmail(NewTripStepFourPaymentActivity.this));
            addTripDataModel.setTripid(Long.valueOf(AppRepository.mTripId(this)));
            addTripDataModel.setUserid((long) AppRepository.mUserID(this));
            addTripDataModel.setName(AppRepository.mUserName(NewTripStepFourPaymentActivity.this));
            userList.add(addTripDataModel);
        }catch (Exception e) {
            e.printStackTrace();
        }

        dialog.show();
        Call<AddTripResponse> getGalleryPhotoResponseCall = BaseNetworking.ApiInterface().getUserTrip("trips/gettrip/" + AppRepository.mTripId(this));
        getGalleryPhotoResponseCall.enqueue(new Callback<AddTripResponse>() {
            @Override
            public void onResponse(Call<AddTripResponse> call, Response<AddTripResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    userList.addAll(response.body().getData());
                    customSpinnerAdapter.notifyDataSetChanged();

                    Log.d("zmauser", "" + userList);

                }
            }

            @Override
            public void onFailure(Call<AddTripResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(NewTripStepFourPaymentActivity.this, String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }

}