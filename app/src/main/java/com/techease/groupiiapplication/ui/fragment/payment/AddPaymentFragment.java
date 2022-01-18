package com.techease.groupiiapplication.ui.fragment.payment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.tripDetail.CustomSpinnerAdapter;
import com.techease.groupiiapplication.dataModel.addTrips.addTrip.AddTripDataModel;
import com.techease.groupiiapplication.dataModel.addTrips.addTrip.AddTripResponse;
import com.techease.groupiiapplication.dataModel.payments.getPaymentsExpenses.GetPaymentExpensesResponse;
import com.techease.groupiiapplication.dataModel.payments.getPaymentsExpenses.RecentTransaction;
import com.techease.groupiiapplication.dataModel.tripDetial.addPaymentExpenses.AddPaymentResponse;
import com.techease.groupiiapplication.interfaceClass.AddPaymentCallBackListener;
import com.techease.groupiiapplication.interfaceClass.AddPaymentOnBackListener;
import com.techease.groupiiapplication.interfaceClass.AddPaymentOnSetpFourCallBackListener;
import com.techease.groupiiapplication.interfaceClass.EditPaymentCallBackListener;
import com.techease.groupiiapplication.interfaceClass.backParticipantsCostsClickInterface.ConnectParticipantCostsBackClick;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.ui.activity.tripDetailScreen.TripDetailScreenActivity;
import com.techease.groupiiapplication.ui.fragment.tripDetialScreen.PaymentsFragment;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.Connectivity;
import com.techease.groupiiapplication.utils.DateUtills;
import com.techease.groupiiapplication.utils.KeyBoardUtils;

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

public class AddPaymentFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    @BindView(R.id.spUserNameFragment)
    Spinner spUserName;
    Dialog addActivityTypeDialog;
    Dialog dialog;
    boolean valid;
    String strPaymentTitle, strPaymentDate, strPaymentAmount, strPaymentShortDescription, strPaymentMethod = "VISA", strPaymentUser;
    TextInputLayout tillPaymentTitle, tillPaymentDate, tillPaymentAmount, tillShortDescription;
    EditText etPaymentTitle, etPaymentDate, etPaymentAmount, etShortDescription;
    String strIsPersonal = "0", strActivityType, strPaid = "0";
    ImageView ivAddPaymentBack, ivType;
    ImageView ivVisa, ivMasterCard, ivAmericanCard, ivJcb;
    TextView tvAddPayment;
    SwitchCompat swAddGroupPayment;
    LinearLayout llPaymentMethod;
    LinearLayout llTaxi, llBus, llReserv, llFlight;

    @BindView(R.id.rlAddActivity)
    RelativeLayout rlAddActivity;
    @BindView(R.id.vMinusIcon)
    View ivMinusIcon;

    private AddPaymentOnBackListener addPaymentOnBackListener;
    private AddPaymentOnSetpFourCallBackListener addPaymentOnSetpFourCallBackListener;


    public ArrayList<AddTripDataModel> userList = new ArrayList<>();
    CustomSpinnerAdapter customSpinnerAdapter;
    String strEditID = "0", strEditType = "null";

    public static AddPaymentFragment newInstance(String strEditPaymentID, String strEditPaymentType) {
        AddPaymentFragment fragment = new AddPaymentFragment();
        fragment.strEditType = strEditPaymentType;
        fragment.strEditID = strEditPaymentID;

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_add_payment, container, false);
        ButterKnife.bind(this, parentView);
        dialog = AlertUtils.createProgressDialog(getActivity());

        if (strEditType.equals("RecentTransaction")) {
            rlAddActivity.setVisibility(View.GONE);
            ivMinusIcon.setVisibility(View.GONE);
        }

        if (getActivity() instanceof AddPaymentOnBackListener)
            addPaymentOnBackListener = (AddPaymentOnBackListener) getActivity();

        if (getActivity() instanceof AddPaymentOnSetpFourCallBackListener)
            addPaymentOnSetpFourCallBackListener = (AddPaymentOnSetpFourCallBackListener) getActivity();


        customSpinnerAdapter = new CustomSpinnerAdapter(getActivity(), userList);
        spUserName.setAdapter(customSpinnerAdapter);

        ivAddPaymentBack = parentView.findViewById(R.id.ivAddPaymentBack);
        ivType = parentView.findViewById(R.id.ivTypeFragment);
        llPaymentMethod = parentView.findViewById(R.id.llPaymentMethodFragment);


        tillPaymentTitle = parentView.findViewById(R.id.tilPaymentTitle);
        tillPaymentDate = parentView.findViewById(R.id.tillAddPaymentDate);
        tillPaymentAmount = parentView.findViewById(R.id.tillAmount);
        tillShortDescription = parentView.findViewById(R.id.tilAddPaymentShortDescription);

        etPaymentTitle = parentView.findViewById(R.id.etPaymentTitleFragment);
        etPaymentDate = parentView.findViewById(R.id.etAddPaymentDateFragment);
        etPaymentAmount = parentView.findViewById(R.id.etAmountFragment);
        etShortDescription = parentView.findViewById(R.id.etAddPaymentShortDescriptionFragment);

        tvAddPayment = parentView.findViewById(R.id.tvPaymentAddFragment);
        swAddGroupPayment = parentView.findViewById(R.id.swAddGroupPaymentFragment);

//        spUserName = parentView.findViewById(R.id.spUserName);
        spUserName.setOnItemSelectedListener(this);
        ivVisa = parentView.findViewById(R.id.ivVisaFragment);
        ivMasterCard = parentView.findViewById(R.id.ivMastercardFragment);
        ivJcb = parentView.findViewById(R.id.ivJcbFragment);
        ivAmericanCard = parentView.findViewById(R.id.ivAmericanExpressFragment);
        swAddGroupPayment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    strIsPersonal = "0";
                    swAddGroupPayment.setText(getResources().getString(R.string.group));
                } else {
                    strIsPersonal = "1";
                    swAddGroupPayment.setText(getResources().getString(R.string.personal));
                }
            }
        });

        GetUserTrip();
        if (AppRepository.addPaymentOnStepFour(getActivity())) {
            ApiCallGetUserTrip();
        }
        try {
            if (strEditType.equals("RecentTransaction")) {
                getPaymentForEditExpenses();
            }
        } catch (Exception ignored) {

        }
        return parentView;
    }


    @OnClick({R.id.tvPaymentAddFragment, R.id.ivAddPaymentBack, R.id.ivTypeFragment, R.id.ivVisaFragment, R.id.ivMastercardFragment, R.id.ivJcbFragment, R.id.ivAmericanExpressFragment, R.id.etAddPaymentDateFragment,})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ivAddPaymentBack:
                addPaymentOnBackListener.onPaymentBack();
                break;
            case R.id.tvPaymentAddFragment:
                if (isValidAddPayment()) {
                    if (strEditType.equals("RecentTransaction")) {
                        ApiCallForEditPayment();
                    } else {
                        ApiCallForAddPayment();
                    }
                }
                break;
            case R.id.ivVisaFragment:
                HighliteImage(ivVisa, ivMasterCard, ivJcb, ivAmericanCard);
                strPaymentMethod = "Visa";

                break;
            case R.id.ivMastercardFragment:
                HighliteImage(ivMasterCard, ivVisa, ivJcb, ivAmericanCard);
                strPaymentMethod = "Mastercard";

                break;
            case R.id.ivJcbFragment:
                HighliteImage(ivJcb, ivMasterCard, ivVisa, ivAmericanCard);
                strPaymentMethod = "JCB";

                break;
            case R.id.ivAmericanExpressFragment:
                HighliteImage(ivAmericanCard, ivMasterCard, ivJcb, ivVisa);
                strPaymentMethod = "American Express";
                break;
            case R.id.etAddPaymentDateFragment:
                DateUtills.GetDatePickerDialog(etPaymentDate, getActivity());
                break;

            case R.id.ivTypeFragment:
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
        dialog.show();
        Call<AddPaymentResponse> addPaymentResponseCall = BaseNetworking.ApiInterface().addPayment(AppRepository.mTripIDForUpdation(getActivity()), AppRepository.mUserID(getActivity()),
                strPaymentAmount, strActivityType, strPaymentTitle, strPaymentDate, strPaymentShortDescription, strIsPersonal, strPaymentUser, strPaymentMethod, "0");
        addPaymentResponseCall.enqueue(new Callback<AddPaymentResponse>() {
            @Override
            public void onResponse(Call<AddPaymentResponse> call, Response<AddPaymentResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();

                    if (strEditType.equals("RecentTransaction")) {
                        addPaymentOnSetpFourCallBackListener.onAddPaymentOnSetpFourBack();
                    } else {
                        ConnectParticipantCostsBackClick.setMyBoolean(true);
                        PaymentsFragment paymentsFragment = new PaymentsFragment();
                        paymentsFragment.getPaymentExpenses();
                    }


                } else {
                    dialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getActivity(), "Payment amount incorrect", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void ApiCallForEditPayment() {
        dialog.show();
        Call<AddPaymentResponse> addPaymentResponseCall = BaseNetworking.ApiInterface().editPayment(strEditID, AppRepository.mTripIDForUpdation(getActivity()), AppRepository.mUserID(getActivity()), strPaymentAmount, strActivityType, strPaymentTitle, strPaymentDate, strPaymentShortDescription, strIsPersonal, strPaymentUser, strPaymentMethod, strPaid);//0 for paid payment 1 for expenses
        addPaymentResponseCall.enqueue(new Callback<AddPaymentResponse>() {
            @Override
            public void onResponse(Call<AddPaymentResponse> call, Response<AddPaymentResponse> response) {

                if (response.isSuccessful()) {
                    dialog.dismiss();
                    etPaymentTitle.setText("");
                    etPaymentDate.setText("");
                    etPaymentAmount.setText("");
                    etShortDescription.setText("");


                    ConnectParticipantCostsBackClick.setMyBoolean(true);
                    PaymentsFragment paymentsFragment = new PaymentsFragment();
                    paymentsFragment.getPaymentExpenses();
                    requireActivity().onBackPressed();


                } else {
                    dialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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


    private void getPaymentForEditExpenses() {

        dialog.show();
        Call<GetPaymentExpensesResponse> getPaymentExpensesResponseCall = BaseNetworking.ApiInterface().getPaymentExpenses(AppRepository.mTripIDForUpdation(getActivity()), AppRepository.mUserID(getActivity()));
        getPaymentExpensesResponseCall.enqueue(new Callback<GetPaymentExpensesResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<GetPaymentExpensesResponse> call, Response<GetPaymentExpensesResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();

                    for (RecentTransaction recentTransaction : response.body().getData().getRecentTransaction()) {

                        if (recentTransaction.getId() == Integer.parseInt(strEditID)) {
                            Log.d("zmaresponse" + strEditID, String.valueOf(response.body().getData().getRecentTransaction().get(0).getId()));
                            etPaymentTitle.setText(recentTransaction.getName());
                            etPaymentDate.setText(DateUtills.getEditDateFormate(recentTransaction.getDate()));
                            etPaymentAmount.setText(recentTransaction.getAmount() + "");
                            etShortDescription.setText(recentTransaction.getShortDesc());
                            strIsPersonal = recentTransaction.getIsPersonal() + "";
                            strPaymentUser = recentTransaction.getUserid() + "";
                            strPaid = recentTransaction.getPaid() + "";

                            tvAddPayment.setText("Update");
                            if (recentTransaction.getIsPersonal() == 0) {
                                swAddGroupPayment.setChecked(true);
                            }

                            if (recentTransaction.getTypeImage() != null) {
                                switch (recentTransaction.getTypeImage()) {
                                    case "taxi":
                                        Glide.with(requireActivity()).load(R.mipmap.taxi_wheel).into(ivType);
                                        break;
                                    case "bus":
                                        Glide.with(requireActivity()).load(R.mipmap.transfer).into(ivType);
                                        break;
                                    case "hotel":
                                        Glide.with(requireActivity()).load(R.mipmap.reserv_selected).into(ivType);
                                        break;
                                    case "Flight":
                                        Glide.with(requireActivity()).load(R.mipmap.flight).into(ivType);
                                        break;
                                }

                            }


                        }
                    }

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


    private void ApiCallGetUserTrip() {
        dialog.show();
        userList.clear();
        try {
            AddTripDataModel addTripDataModel = new AddTripDataModel();
            addTripDataModel.setEmail(AppRepository.mEmail(getActivity()));
            addTripDataModel.setTripid(Long.valueOf(AppRepository.mTripIDForUpdation(getActivity())));
            addTripDataModel.setUserid((long) AppRepository.mUserID(getActivity()));
            addTripDataModel.setName(AppRepository.mUserName(getActivity()));
            addTripDataModel.setPicture(AppRepository.mUserProfileImage(getActivity()));
            addTripDataModel.setLatitude(AppRepository.mLat(getActivity()));
            addTripDataModel.setLongitude(AppRepository.mLng(getActivity()));
            userList.add(addTripDataModel);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Call<AddTripResponse> addTripResponseCall = BaseNetworking.ApiInterface().getUserTrip("trips/gettrip/" + AppRepository.mTripIDForUpdation(getActivity()));
        addTripResponseCall.enqueue(new Callback<AddTripResponse>() {
            @Override
            public void onResponse(Call<AddTripResponse> call, Response<AddTripResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    userList.addAll(response.body().getData());

                    CustomSpinnerAdapter customAdapter = new CustomSpinnerAdapter(getActivity(), userList);
                    spUserName.setAdapter(customAdapter);


                }
            }

            @Override
            public void onFailure(Call<AddTripResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getActivity(), String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }


}