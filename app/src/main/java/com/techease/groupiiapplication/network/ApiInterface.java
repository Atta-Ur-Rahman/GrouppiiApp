
package com.techease.groupiiapplication.network;

import com.techease.groupiiapplication.dataModel.addPhotoToGallery.AddPhotoToGalleryResponse;
import com.techease.groupiiapplication.dataModel.addTrip.AddTripResponse;
import com.techease.groupiiapplication.dataModel.addTripDay.AddTripDayResponse;
import com.techease.groupiiapplication.dataModel.addTripDetail.AddTripDetailResponse;
import com.techease.groupiiapplication.dataModel.contactUs.ContactUsData;
import com.techease.groupiiapplication.dataModel.contactUs.ContactUsResponse;
import com.techease.groupiiapplication.dataModel.createTrip.CreateTripResponse;
import com.techease.groupiiapplication.dataModel.forgot.ForgotResponse;
import com.techease.groupiiapplication.dataModel.genrelResetPassword.GeneralResetPassword;
import com.techease.groupiiapplication.dataModel.getAllTripDay.AllTripDayResponse;
import com.techease.groupiiapplication.dataModel.getGalleryPhoto.GetGalleryPhotoResponse;
import com.techease.groupiiapplication.dataModel.OgodaHotel.OgodaHotelResponse;
import com.techease.groupiiapplication.dataModel.getUserTrip.GetUserTripResponse;
import com.techease.groupiiapplication.dataModel.ogodaHotelObject.MainHotelObject;
import com.techease.groupiiapplication.dataModel.login.LogInResponse;
import com.techease.groupiiapplication.dataModel.publishTrip.PublishTripResponse;
import com.techease.groupiiapplication.dataModel.signUp.SignUpResponse;
import com.techease.groupiiapplication.dataModel.tripDelete.DeleteTripResponse;
import com.techease.groupiiapplication.dataModel.tripDetail.TripDetailResponse;
import com.techease.groupiiapplication.dataModel.updateProfilePicture.UpdateProfilePicResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface ApiInterface {

//    @FormUrlEncoded
//    @POST("login")
//    Call<LoginResponse> loginUser(
//            @Field("email") String userLogin,
//            @Field("password") String userPassword,
//            @Field("deviceToken") String deviceToken,
//            @Field("latitude") String latitude,
//            @Field("longitude") String longitude);


    @FormUrlEncoded
    @POST("users/signup")
    Call<SignUpResponse> signUp(
            @Field("name") String name,
            @Field("email") String userEmail,
            @Field("password") String userPassword,
            @Field("fcm_token") String fcmToken,
            @Field("device_type") String deviceType,
            @Field("usertype") String userType);


    @FormUrlEncoded
    @POST("users/login")
    Call<LogInResponse> login(
            @Field("email") String userEmail,
            @Field("password") String userPassword);


    @FormUrlEncoded
    @POST("users/forgotpassword")
    Call<ForgotResponse> forgotPassword(
            @Field("email") String userEmail);

    @FormUrlEncoded
    @POST("users/resetpassword")
    Call<ForgotResponse> resetPassword(
            @Field("password") String userPassword,
            @Field("token") String userToken);

    @FormUrlEncoded
    @POST("users/passwordreset")
    Call<GeneralResetPassword> changePassword(
            @Field("password") String userPassword,
            @Field("userid") Integer userID);

    @Multipart
    @POST("users/profilepicture")
    Call<UpdateProfilePicResponse> updateProfilePic(
            @Part("userid") RequestBody userID,
            @Part MultipartBody.Part photo,
            @Part("picture") RequestBody fileName);


    @FormUrlEncoded
    @POST("trips/createtrip")
    Call<CreateTripResponse> getTripID(
            @Field("userid") int userId);


    @FormUrlEncoded
    @POST("trips/getusertrips")
    Call<TripDetailResponse> getTripDetail(
            @Field("userid") int userId);


    @FormUrlEncoded
    @POST("trips/tripusers")
    Call<AddTripResponse> addTrip(
            @Field("name") String name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("share_cost") String shareCost,
            @Field("tripid") String tripId,
            @Field("userid") int userId);


    @Multipart
    @POST("trips/tripdetail")
    Call<AddTripDetailResponse> addTripDetail(
            @Part("tripid") RequestBody tripID,
            @Part("title") RequestBody title,
            @Part("description") RequestBody description,
            @Part("location") RequestBody location,
            @Part("startdate") RequestBody startDate,
            @Part("enddate") RequestBody endDate,
            @Part("paydate") RequestBody payByDate,
            @Part MultipartBody.Part photo,
            @Part("coverimage") RequestBody fileName);


    @FormUrlEncoded
    @POST("trips/deletetrip")
    Call<DeleteTripResponse> deleteTrip(
            @Field("tripid") String tripId);


    @POST("lt_v1")
    Call<OgodaHotelResponse> getAllHotel(@Body MainHotelObject mainObject);


    @FormUrlEncoded
    @POST("tripdays/add")
    Call<AddTripDayResponse> addTripDay(
            @Field("title") String title,
            @Field("description") String description,
            @Field("date") String date,
            @Field("time") String time,
            @Field("tripid") String tripId,
            @Field("userid") int userId,
            @Field("type") String type);


    @Multipart
    @POST("trips/photo")
    Call<AddPhotoToGalleryResponse> addPhotoToGallery(
            @Part("tripid") RequestBody tripID,
            @Part("title") RequestBody title,
            @Part("time") RequestBody time,
            @Part("date") RequestBody date,
            @Part MultipartBody.Part photo,
            @Part("photo") RequestBody fileName);


    @GET
    Call<AllTripDayResponse> getAllTripDayResponse(
            @Url String url);


    @FormUrlEncoded
    @POST("tripdays/getbydate")
    Call<AllTripDayResponse> getTripByDate(
            @Field("date") String date,
            @Field("tripid") String tripId);


    @GET
    Call<GetGalleryPhotoResponse> getAllGalleryPhoto(
            @Url String tripId);

    @GET
    Call<PublishTripResponse> publishTrip(
            @Url String url);

    @GET
    Call<GetUserTripResponse> getUserTrip(
            @Url String url);


    @FormUrlEncoded
    @POST("users/contactus")
    Call<ContactUsResponse> contactUs(
            @Field("contact") String message,
            @Field("userid") int userId);
}
