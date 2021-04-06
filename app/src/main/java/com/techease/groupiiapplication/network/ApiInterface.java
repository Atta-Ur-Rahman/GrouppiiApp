
package com.techease.groupiiapplication.network;

import com.techease.groupiiapplication.dataModel.addPhotoToGallery.AddPhotoToGalleryResponse;
import com.techease.groupiiapplication.dataModel.addTrip.AddTripResponse;
import com.techease.groupiiapplication.dataModel.addTripDay.AddTripDayResponse;
import com.techease.groupiiapplication.dataModel.addTripDetail.AddTripDetailResponse;
import com.techease.groupiiapplication.dataModel.createTrip.CreateTripResponse;
import com.techease.groupiiapplication.dataModel.forgot.ChangePasswordResponse;
import com.techease.groupiiapplication.dataModel.forgot.ForgotResponse;
import com.techease.groupiiapplication.dataModel.getAllTripDay.AllTripDayResponse;
import com.techease.groupiiapplication.dataModel.getGalleryPhoto.GetGalleryPhotoResponse;
import com.techease.groupiiapplication.dataModel.OgodaHotel.OgodaHotelResponse;
import com.techease.groupiiapplication.dataModel.ogodaHotelObject.MainHotelObject;
import com.techease.groupiiapplication.dataModel.login.LogInResponse;
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
    Call<ChangePasswordResponse> changePassword(
            @Field("password") String userPassword,
            @Field("token") String userToken);

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
            @Field("userid") int userId);



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


//
//    @Multipart
//    @POST("register")
//    Call<SignUpResponse> signUpWithOutImage(
//            @Part("name") RequestBody name,
//            @Part("email") RequestBody email,
//            @Part("phoneNumber") RequestBody phoneNumber,
//            @Part("password") RequestBody password,
//            @Part("confirmPassword") RequestBody confirmPassword,
//            @Part("deviceType") RequestBody deviceType,
//            @Part("deviceToken") RequestBody deviceToken,
//            @Part("latitude") RequestBody latitude,
//            @Part("longitude") RequestBody longitude);
//
//
//    @Multipart
//    @POST("update-user-profile-picture")
//    Call<UpdateProfileResponse> updateProfilePic(
//            @Part MultipartBody.Part photo,
//            @Part("profilePicture") RequestBody fileName);
//
//
//    @FormUrlEncoded
//    @POST("reset-password")
//    Call<ResetPasswordResponse> forgotPassword(@Field("email") String email);
//
//    @FormUrlEncoded
//    @POST("reset-password-verify")
//    Call<VerifyCodeResponse> verifyCode(@Field("email") String email,
//                                        @Field("code") String code);
//
//
//    @FormUrlEncoded
//    @POST("change-password")
//    Call<ChangePasswordResponse> changePassword(@Field("newPassword") String newPassword,
//                                                @Field("confirmPassword") String confirmPassword);
//
//    @GET("get-profile-detail")
//    Call<ProfileResponse> getProfile();
//
//
//    @GET("get-restaurents")
//    Call<NearRestaurantsResponse> getNearRestaurants(
//            @Query("latitude") String latitude,
//            @Query("longitude") String longitude);
//
//
//    @GET("restaurent-details?")
//    Call<RestaurantDetailResponse> getRestaurantDetail(
//            @Query("id") String restaurantId);
//
//
//    @FormUrlEncoded
//    @POST("vote")
//    Call<VoteResponse> vote(
//            @Field("restaurentId") String restaurantId);
//
//    @FormUrlEncoded
//    @POST("tast")
//    Call<TastResponse> tast(
//            @Field("restaurentId") String restaurantId);
//
//
//    @FormUrlEncoded
//    @POST("unTast")
//    Call<UnTastedResponse> unTast(
//            @Field("restaurentId") String restaurantId);
//
//
//    @GET("category-restaurents")
//    Call<CategoryResponse> getCategoryRestaurant();
//
//
//    @GET("my-votes")
//    Call<MyVotingResponse> getMyVotes();
//
//    @GET("top-tasted")
//    Call<TopTastedResponse> getTopTasted();

}
