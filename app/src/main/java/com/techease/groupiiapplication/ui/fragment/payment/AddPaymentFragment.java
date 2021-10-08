package com.techease.groupiiapplication.ui.fragment.payment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.tripDetail.CustomSpinnerAdapter;
import com.techease.groupiiapplication.dataModel.addTrips.addTrip.AddTripDataModel;
import com.techease.groupiiapplication.dataModel.addTrips.addTrip.AddTripResponse;
import com.techease.groupiiapplication.dataModel.tripDetial.addPaymentExpenses.AddPaymentResponse;
import com.techease.groupiiapplication.interfaceClass.AddPaymentCallBackListener;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.ui.activity.tripDetailScreen.TripDetailScreenActivity;
import com.techease.groupiiapplication.ui.fragment.tripDetialScreen.PaymentsFragment;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.Connectivity;
import com.techease.groupiiapplication.utils.DateUtills;
import com.techease.groupiiapplication.utils.KeyBoardUtils;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPaymentFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {


    @BindView(R.id.spUserName)
    Spinner spUserName;
    Dialog addActivityTypeDialog;
    Dialog dialog;
    boolean valid;
    private AddPaymentCallBackListener callBackListener;
    String strPaymentTitle, strPaymentDate, strPaymentAmount, strPaymentShortDescription, strPaymentMethod = "VISA", strPaymentUser;
    TextInputLayout tillPaymentTitle, tillPaymentDate, tillPaymentAmount, tillShortDescription;
    EditText etPaymentTitle, etPaymentDate, etPaymentAmount, etShortDescription;
    String strIsPersonal = "0", strActivityType, strPersonal = "1";
    ImageView ivAddPaymentBack, ivType;
    ImageView ivVisa, ivMasterCard, ivAmericanCard, ivJcb;
    TextView tvAddPayment;
    SwitchCompat swAddGroupPayment;
    LinearLayout llPaymentMethod;
    LinearLayout llTaxi, llBus, llReserv, llFlight;

    public ArrayList<AddTripDataModel> userList = new ArrayList<>();
    CustomSpinnerAdapter customSpinnerAdapter;
    public static AddPaymentFragment newInstance() {
        AddPaymentFragment fragment = new AddPaymentFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_add_payment, container, false);
        ButterKnife.bind(this, parentView);
        dialog = AlertUtils.createProgressDialog(getActivity());
        if (getActivity() instanceof AddPaymentCallBackListener)
            callBackListener = (AddPaymentCallBackListener) getActivity();


        customSpinnerAdapter = new CustomSpinnerAdapter(getActivity(), userList);
        spUserName.setAdapter(customSpinnerAdapter);

        ivAddPaymentBack = parentView.findViewById(R.id.ivAddPaymentBack);
        ivType = parentView.findViewById(R.id.ivType);
        llPaymentMethod = parentView.findViewById(R.id.llPaymentMethod);


        tillPaymentTitle = parentView.findViewById(R.id.tilPaymentTitle);
        tillPaymentDate = parentView.findViewById(R.id.tillAddPaymentDate);
        tillPaymentAmount = parentView.findViewById(R.id.tillAmount);
        tillShortDescription = parentView.findViewById(R.id.tilAddPaymentShortDescription);

        etPaymentTitle = parentView.findViewById(R.id.etPaymentTitle);
        etPaymentDate = parentView.findViewById(R.id.etAddPaymentDate);
        etPaymentAmount = parentView.findViewById(R.id.etAmount);
        etShortDescription = parentView.findViewById(R.id.etAddPaymentShortDescription);

        tvAddPayment = parentView.findViewById(R.id.tvPaymentAdd);
        swAddGroupPayment = parentView.findViewById(R.id.swAddGroupPayment);


//        spUserName = parentView.findViewById(R.id.spUserName);
        spUserName.setOnItemSelectedListener(this);
        ivVisa = parentView.findViewById(R.id.ivVisa);
        ivMasterCard = parentView.findViewById(R.id.ivMastercard);
        ivJcb = parentView.findViewById(R.id.ivJcb);
        ivAmericanCard = parentView.findViewById(R.id.ivAmericanExpress);

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


        GetUserTrip();

        return parentView;
    }

    private void getUser() {

        Call<AddTripResponse> getGalleryPhotoResponseCall = BaseNetworking.ApiInterface().getUserTrip("trips/gallery/" + AppRepository.mTripId(getActivity()));
        getGalleryPhotoResponseCall.enqueue(new Callback<AddTripResponse>() {
            @Override
            public void onResponse(Call<AddTripResponse> call, Response<AddTripResponse> response) {
                if (response.isSuccessful()) {
                    userList.addAll(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<AddTripResponse> call, Throwable t) {

            }
        });

    }


    @OnClick({R.id.tvPaymentAdd, R.id.ivAddPaymentBack, R.id.ivType, R.id.ivVisa, R.id.ivMastercard, R.id.ivJcb, R.id.ivAmericanExpress, R.id.etAddPaymentDate,})
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tvPaymentAdd:
                if (isValidAddPayment()) {
                    ApiCallForAddPayment();
                }
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
                DateUtills.GetDatePickerDialog(etPaymentDate, getActivity());
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

        }

    }

    private void HighliteImage(ImageView iv1, ImageView iv2, ImageView iv3, ImageView iv4) {
        iv1.getDrawable().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
        iv2.getDrawable().setColorFilter(0x00000000, PorterDuff.Mode.SRC_ATOP);
        iv3.getDrawable().setColorFilter(0x00000000, PorterDuff.Mode.SRC_ATOP);
        iv4.getDrawable().setColorFilter(0x00000000, PorterDuff.Mode.SRC_ATOP);

    }


    void activityTripTypeDialog() {
        addActivityTypeDialog = new Dialog(getActivity());
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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        strPaymentUser = String.valueOf(userList.get(position).getUserid());


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void ApiCallForAddPayment() {

        String strPaid;
        if (AddPaymentsTabsFragment.anIntViewPagerPosition == 0) {
            strPaid = "0";
        } else {
            strPaid = "1";

        }

        dialog.show();
        Call<AddPaymentResponse> addPaymentResponseCall = BaseNetworking.ApiInterface().addPayment(AppRepository.mTripIDForUpdation(getActivity()), AppRepository.mUserID(getActivity()),
                strPaymentAmount, strActivityType, strPaymentTitle, strPaymentDate, strPaymentShortDescription, strIsPersonal, strPaymentUser, strPaymentMethod, strPaid);
        addPaymentResponseCall.enqueue(new Callback<AddPaymentResponse>() {
            @Override
            public void onResponse(Call<AddPaymentResponse> call, Response<AddPaymentResponse> response) {

                Log.d("zma addpayment", String.valueOf(response));
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    etPaymentTitle.setText("");
                    etPaymentDate.setText("");
                    etPaymentAmount.setText("");
                    etShortDescription.setText("");

                    callBackListener.onPaymentAdddCallBack();

                    PaymentsFragment paymentsFragment = new PaymentsFragment();
                    paymentsFragment.getPaymentExpenses();

                    KeyBoardUtils.hideKeyboard(Objects.requireNonNull(getActivity()));
                    KeyBoardUtils.closeKeyboard(getActivity());

                } else {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<AddPaymentResponse> call, Throwable t) {
                Log.d("zma addpayment error", String.valueOf(t.getMessage()));
                dialog.dismiss();
                if (t.getMessage().equals("java.lang.IllegalStateException: Expected BEGIN_OBJECT but was STRING at line 1 column 52 path $.data")) {
                    Toast.makeText(getActivity(), "Payment amount incorrect", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
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
        if (!Connectivity.isConnected(getActivity())) {
            valid = false;
            Toast.makeText(getActivity(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }


        return valid;
    }

    private void GetUserTrip() {
        userList.clear();
        userList.addAll(TripDetailScreenActivity.paymentUserParticipaintsList);
        customSpinnerAdapter.notifyDataSetChanged();

    }
}