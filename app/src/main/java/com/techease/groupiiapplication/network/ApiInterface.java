
package com.techease.groupiiapplication.network;

import com.techease.groupiiapplication.dataModel.CommonResponse;
import com.techease.groupiiapplication.dataModel.addCriditModel.AddCreditCardResponse;
import com.techease.groupiiapplication.dataModel.deleteGalleryImage.DeleteGalleryPhotoResponse;
import com.techease.groupiiapplication.dataModel.deleteRsvp.DeleteRsvpResponse;
import com.techease.groupiiapplication.dataModel.getReserveModel.GetAllReserveResponse;
import com.techease.groupiiapplication.dataModel.getSingleTrip.GetSingleTripResponse;
import com.techease.groupiiapplication.dataModel.newLogin.LoginResponse;
import com.techease.groupiiapplication.dataModel.notifications.GetNotificationsResponse;
import com.techease.groupiiapplication.dataModel.payments.getPaymentsExpenses.GetPaymentExpensesResponse;
import com.techease.groupiiapplication.dataModel.reserveModel.AddReserveResponse;
import com.techease.groupiiapplication.dataModel.tripDetial.addPaymentExpenses.AddPaymentResponse;
import com.techease.groupiiapplication.dataModel.tripDetial.addPhotoToGallery.AddPhotoToGalleryResponse;
import com.techease.groupiiapplication.dataModel.addTrips.addTrip.AddTripResponse;
import com.techease.groupiiapplication.dataModel.tripDetial.addTripDay.AddTripDayResponse;
import com.techease.groupiiapplication.dataModel.tripDetial.addTripDetail.AddTripDetailResponse;
import com.techease.groupiiapplication.dataModel.profile.contactUs.ContactUsResponse;
import com.techease.groupiiapplication.dataModel.addTrips.createTrip.CreateTripResponse;
import com.techease.groupiiapplication.dataModel.loginSignup.forgot.ForgotResponse;
import com.techease.groupiiapplication.dataModel.loginSignup.genrelResetPassword.GeneralResetPassword;
import com.techease.groupiiapplication.dataModel.tripDetial.deleteTripUser.DeleteTripUserResponse;
import com.techease.groupiiapplication.dataModel.tripDetial.getAllTripDay.AllTripDayResponse;
import com.techease.groupiiapplication.dataModel.tripDetial.getGalleryPhoto.GetGalleryPhotoResponse;
import com.techease.groupiiapplication.dataModel.addTrips.OgodaHotel.OgodaHotelResponse;
import com.techease.groupiiapplication.dataModel.addTrips.ogodaHotelObject.MainHotelObject;
import com.techease.groupiiapplication.dataModel.addTrips.publishTrip.PublishTripResponse;
import com.techease.groupiiapplication.dataModel.loginSignup.signUp.SignUpResponse;
import com.techease.groupiiapplication.dataModel.addTrips.tripDelete.DeleteTripResponse;
import com.techease.groupiiapplication.dataModel.getAllTrip.TripDetailResponse;
import com.techease.groupiiapplication.dataModel.profile.updateProfilePicture.UpdateProfilePicResponse;
import com.techease.groupiiapplication.dataModel.updateUserProfile.UpdateUserProfileResponse;

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
import retrofit2.http.Path;
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
            @Field("phone") String phoneNumber,
            @Field("device_type") String deviceType,
            @Field("usertype") String userType,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude);


    @FormUrlEncoded
    @POST("users/login")
    Call<LoginResponse> login(
            @Field("email") String userEmail,
            @Field("password") String userPassword,
            @Field("token") String fcmToken);


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
    Call<AddTripResponse> addTripWithGmailAndPhone(
            @Field("name") String name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("share_cost") String shareCost,
            @Field("tripid") String tripId,
            @Field("userid") int userId);

    @FormUrlEncoded
    @POST("trips/tripusers")
    Call<AddTripResponse> addTripWithPhone(
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("share_cost") String shareCost,
            @Field("tripid") String tripId,
            @Field("userid") int userId);

    @FormUrlEncoded
    @POST("trips/tripusers")
    Call<AddTripResponse> addTripWithGmail(
            @Field("name") String name,
            @Field("email") String email,
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


    @Multipart
    @POST("trips/tripdetail")
    Call<AddTripDetailResponse> addTripDetailWithOutImage(
            @Part("tripid") RequestBody tripID,
            @Part("title") RequestBody title,
            @Part("description") RequestBody description,
            @Part("location") RequestBody location,
            @Part("startdate") RequestBody startDate,
            @Part("enddate") RequestBody endDate,
            @Part("paydate") RequestBody payByDate);


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

    @FormUrlEncoded
    @POST("tripdays/update")
    Call<AddTripDayResponse> updateTripDay(
            @Field("id") String id,
            @Field("title") String title,
            @Field("description") String description,
            @Field("date") String date,
            @Field("time") String time,
            @Field("tripid") String tripId,
            @Field("userid") int userId,
            @Field("type") String type);


    @GET("tripdays/delete/{dayPlanID}")
    Call<DeleteRsvpResponse> deleteDayPlan(
            @Path("dayPlanID") String dayPlanID);


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
    Call<AddTripResponse> getUserTrip(
            @Url String url);


    @GET
    Call<GetSingleTripResponse> getTripById(
            @Url String id);


    @GET
    Call<GetNotificationsResponse> getAllNotification(
            @Url String url);


    @FormUrlEncoded
    @POST("users/contactus")
    Call<ContactUsResponse> contactUs(
            @Field("contact") String message,
            @Field("userid") int userId);

    @FormUrlEncoded
    @POST("trips/expenses")
    Call<AddPaymentResponse> addPayment(
            @Field("tripid") String tripId,
            @Field("userid") int userId,
            @Field("amount") String amount,
            @Field("type_image") String type_image,
            @Field("name") String name,
            @Field("date") String date,
            @Field("short_desc") String short_desc,
            @Field("is_personal") String is_personal,
            @Field("paid_by") String paid_by,
            @Field("source") String source,
            @Field("paid") String paid);


    @FormUrlEncoded
    @POST("trips/uodateexpenses")
    Call<AddPaymentResponse> editPayment(
            @Field("payment_id") String payment_id,
            @Field("tripid") String tripId,
            @Field("userid") int userId,
            @Field("amount") String amount,
            @Field("type_image") String type_image,
            @Field("name") String name,
            @Field("date") String date,
            @Field("short_desc") String short_desc,
            @Field("is_personal") String is_personal,
            @Field("paid_by") String paid_by,
            @Field("source") String source,
            @Field("paid") String paid);


    @FormUrlEncoded
    @POST("trips/getexpenses")
    Call<GetPaymentExpensesResponse> getPaymentExpenses(
            @Field("tripid") String tripId,
            @Field("userid") int userId);


    @FormUrlEncoded
    @POST("trips/deleteuser")
    Call<DeleteTripUserResponse> deleteTripUser(
            @Field("tripid") String tripId,
            @Field("userid") String userId);


    @FormUrlEncoded
    @POST("users/updateprofile")
    Call<UpdateUserProfileResponse> updateProfile(
            @Field("userid") String userid,
            @Field("name") String name,
            @Field("email") String userEmail,
            @Field("description") String description,
            @Field("phone") String phone);


    @FormUrlEncoded
    @POST("users/updateprofile")
    Call<UpdateUserProfileResponse> updateProfileLatLng(
            @Field("userid") String userid,
            @Field("name") String name,
            @Field("email") String userEmail,
            @Field("description") String description,
            @Field("phone") String phone,
            @Field("latitude") String latitude,
            @Field("longitude") String langitude);

    @FormUrlEncoded
    @POST("payment/addcard")
    Call<AddCreditCardResponse> addCreditCard(
            @Field("name") String name,
            @Field("cardnumber") String cardNumber,
            @Field("month") String month,
            @Field("year") String year,
            @Field("cvc") String cvc,
            @Field("stripeid") String stripId);

    @GET("trips/photo/{photo_ID}")
    Call<DeleteGalleryPhotoResponse> deletePhotoFromGallery(
            @Path("photo_ID") String photoId);


    @Multipart
    @POST("reserve/add")
    Call<AddReserveResponse> addReserveDetail(
            @Part("tripid") RequestBody tripID,
            @Part("confirmation_code") RequestBody tripConfirmation,
            @Part("title") RequestBody title,
            @Part("address") RequestBody address,
            @Part("fromdate") RequestBody startDate,
            @Part("todate") RequestBody endDate,
            @Part("is_done") RequestBody isDone,
            @Part MultipartBody.Part photo,
            @Part("image") RequestBody fileName);


    @Multipart
    @POST("reserve/update")
    Call<AddReserveResponse> editReserveDetail(
            @Part("id") RequestBody rsvpID,
            @Part("confirmation_code") RequestBody tripConfirmation,
            @Part("tripid") RequestBody tripID,
            @Part("title") RequestBody title,
            @Part("address") RequestBody address,
            @Part("fromdate") RequestBody startDate,
            @Part("todate") RequestBody endDate,
            @Part("is_done") RequestBody isDone,
            @Part MultipartBody.Part photo,
            @Part("image") RequestBody fileName);

    @Multipart
    @POST("reserve/update")
    Call<AddReserveResponse> editReserveWithImageDetail(
            @Part("id") RequestBody rsvpID,
            @Part("confirmation_code") RequestBody tripConfirmation,
            @Part("tripid") RequestBody tripID,
            @Part("title") RequestBody title,
            @Part("address") RequestBody address,
            @Part("fromdate") RequestBody startDate,
            @Part("todate") RequestBody endDate,
            @Part("is_done") RequestBody isDone);


    @GET("reserve/getall/{tripID}")
    Call<GetAllReserveResponse> getAllReserve(
            @Path("tripID") String tripID);


    @GET("reserve/delete/{RsvpID}")
    Call<DeleteRsvpResponse> deleteReserve(
            @Path("RsvpID") String rsvpID);


    @FormUrlEncoded
    @POST("trips/addusertotrip")
    Call<CommonResponse> addUserToTrip(
            @Field("userid") String userId,
            @Field("tripid") String tripId);
}
