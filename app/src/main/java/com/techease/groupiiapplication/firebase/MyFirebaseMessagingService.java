package com.techease.groupiiapplication.firebase;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.getSingleTrip.GetSingleTripResponse;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.ui.activity.ChatsActivity;
import com.techease.groupiiapplication.ui.activity.HomeActivity;
import com.techease.groupiiapplication.ui.activity.LoginSignUp.LoginActivity;
import com.techease.groupiiapplication.ui.activity.SplashActivity;
import com.techease.groupiiapplication.ui.activity.tripDetailScreen.TripDetailScreenActivity;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.StringHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    public static boolean isNewOrder = false;
    public static boolean isAcceptOffer = false;
    public static boolean isRejectOffer = false;
    public static boolean isDeliverOrder = false;
    public static boolean isReviewOrder = false;
    String msg, productName;
    private NotificationUtils notificationUtils;
    Intent intent = new Intent();
    private static int count = 0;

    private NotificationManager notificationManager;

    ArrayList<String> arrayListMessages = new ArrayList<>();


    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


        JSONObject jsonObject = new JSONObject(remoteMessage.getData());
        Log.d("zmanotificationdata", "  " + remoteMessage.getData());


        String strTitle = null;

//        try {
//
////            String strFromUser = jsonObject.getString("messageType");
////            String name = jsonObject.getString("name");
////            String toUser = jsonObject.getString("touser");
////            String picture = jsonObject.getString("picture");
////            String strFromUser = jsonObject.getString("messageType");
////            String strFromUser = jsonObject.getString("messageType");
////            String strFromUser = jsonObject.getString("messageType");
//
////            Intent intent = new Intent(getApplicationContext(), ChatsActivity.class);
////            Bundle bundle = new Bundle();
////            bundle.putString("title_name", jsonObject.getString("name"));
////            bundle.putString("tripId", "12");
////            bundle.putString("toUserId", jsonObject.getString("touser"));
////            bundle.putString("type", jsonObject.getString("user"));
////            bundle.putString("picture", jsonObject.getString("picture"));
////
////            strTitle = jsonObject.getString("messageType");
////            intent.putExtras(bundle);
////            getApplicationContext().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) getApplicationContext()).toBundle());
//
//
//            Log.d("zma", "chat");
////            Log.d("zmachat data",  "strFromUser" + strFromUser);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


        String title = "";
        String message = "";
        PendingIntent pendingIntent = null;


        if (remoteMessage.getData().size() > 0 && StringHelper.checkFirebase) {
            Log.e(TAG, "Received notification1");

//            Log.e(TAG, "Received notification1" + remoteMessage.getData());

            // Parse if twilio notification  or not
            if (StringHelper.isEmpty(remoteMessage.getData().get("n_type"))) {

//                pendingIntent =
//                        PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
//            } else {


                // General notification
                Intent chatActivityIntent = new Intent(getApplicationContext(), ChatsActivity.class);

                Intent intent = null;
                if (AppRepository.isLoggedIn(this)) {


                    try {

                        if (jsonObject.get("messageType").equals("chat")) {

                            Bundle bundle = new Bundle();
                            bundle.putString("title_name", jsonObject.getString("name"));
                            bundle.putString("toUserId", jsonObject.getString("fromuser"));
                            bundle.putString("type", "user");
//                            bundle.putString("picture", jsonObject.getString("picture"));
                            chatActivityIntent.putExtras(bundle);


                        }


                        if (jsonObject.getString("messageType").equals("group")) {

                            Bundle bundle = new Bundle();
                            bundle.putString("title_name", jsonObject.getString("title"));
                            bundle.putString("tripId", jsonObject.getString("tripid"));
                            bundle.putString("type", "group");
                            bundle.putString("picture", jsonObject.getString("picture"));
                            chatActivityIntent.putExtras(bundle);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("zmachatNotifiError", e.getMessage());
                    }
                } else {
                    intent = new Intent(this, LoginActivity.class);
                }
                pendingIntent =
                        PendingIntent.getActivity(this, 0, chatActivityIntent, PendingIntent.FLAG_ONE_SHOT);
            }


            if (remoteMessage.getNotification() != null) {
                title = remoteMessage.getNotification().getTitle();
                try {
                    message = jsonObject.getString("message");
                } catch (JSONException e) {
                    e.printStackTrace();
                    message = remoteMessage.getNotification().getBody();

                }

            }

            if (StringHelper.isEmpty(title) || StringHelper.isEmpty(message)) {
                return;
            }


            //You should use an actual ID instead, If not, messages are collapsed...
            int notificationId = new Random().nextInt(60000);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = new NotificationChannel(getString(R.string.default_notification_channel_id), "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);

                // Configure the notification channel.
                notificationChannel.setDescription("Channel description");
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(R.color.purple_500);
                notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);
            }

            if (StringHelper.isEmpty(remoteMessage.getData().get("n_type"))) {

                NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
                arrayListMessages.add(message);
                // Sets a title for the Inbox style big view
                inboxStyle.setBigContentTitle(title + ":");
                // Moves events into the big view
                for (int i = 0; i < arrayListMessages.size(); i++) {
                    inboxStyle.addLine(arrayListMessages.get(i));
                }
                NotificationCompat.Builder notificationBuilder =
                        new NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
                                .setAutoCancel(true)
                                .setSound(defaultSoundUri)
                                .setStyle(inboxStyle)
                                .setContentIntent(pendingIntent)
                                .setOnlyAlertOnce(false);
//        notificationBuilder.setStyle(inboxStyle);
                notificationBuilder.setSmallIcon(R.drawable.grouppii_logo);
                notificationBuilder.setColor(ContextCompat.getColor(getBaseContext(), R.color.purple_500));
                notificationManager.notify(0, notificationBuilder.build());
            } else {
                NotificationCompat.Builder notificationBuilder =
                        new NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
                                .setContentTitle(title)
                                .setAutoCancel(true)
                                .setSound(defaultSoundUri)
                                .setContentText(message)
                                .setContentIntent(pendingIntent)
                                .setOnlyAlertOnce(false);


                notificationBuilder.setSmallIcon(R.drawable.grouppii_logo);
                notificationBuilder.setColor(ContextCompat.getColor(getBaseContext(), R.color.purple_500));

                notificationManager.notify(notificationId, notificationBuilder.build());
            }

        }
    }


    private void sendNotification(String title, String messageBody) {
        Intent intent = new Intent(getApplicationContext(), ChatsActivity.class);
//you can use your launcher Activity insted of SplashActivity, But if the Activity you used here is not launcher Activty than its not work when App is in background.
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//Add Any key-value to pass extras to intent
        intent.putExtra("pushnotification", "yes");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//For Android Version Orio and greater than orio.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel mChannel = new NotificationChannel("Sesame", "Sesame", importance);
            mChannel.setDescription(messageBody);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            mNotifyManager.createNotificationChannel(mChannel);
        }
//For Android Version lower than oreo.
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "Seasame");
        mBuilder.setContentTitle(title)
                .setContentText(messageBody)
                .setSmallIcon(R.drawable.grouppii_logo)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.grouppii_logo))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setColor(Color.parseColor("#FFD600"))
                .setContentIntent(pendingIntent)
                .setChannelId("Sesame")
                .setPriority(NotificationCompat.PRIORITY_LOW);

        mNotifyManager.notify(count, mBuilder.build());
        count++;
    }


    private void GetTripById(String strTripID) {
        Call<GetSingleTripResponse> getSingleTripResponseCall = BaseNetworking.ApiInterface().getTripById("trips/getsingletrip/" + strTripID);
        getSingleTripResponseCall.enqueue(new Callback<GetSingleTripResponse>() {
            @Override
            public void onResponse(Call<GetSingleTripResponse> call, Response<GetSingleTripResponse> response) {
                if (response.isSuccessful()) {


                    Intent intent = new Intent(getApplicationContext(), TripDetailScreenActivity.class);
                    Bundle bundle = new Bundle();
                    AppRepository.mPutValue(getApplicationContext()).putString("tripIDForUpdation", String.valueOf(response.body().getData().getId())).commit();

                    bundle.putString("image", response.body().getData().getCoverimage());
                    bundle.putString("title", response.body().getData().getTitle());
                    bundle.putString("trip_type", response.body().getData().getStatus());
                    bundle.putString("start_date", response.body().getData().getFromdate());
                    bundle.putString("end_date", response.body().getData().getTodate());
                    bundle.putString("pay_date", response.body().getData().getPayDate());
                    bundle.putString("description", response.body().getData().getDescription());
                    bundle.putString("location", response.body().getData().getLocation());
                    bundle.putBoolean("is_createdby", false);
                    intent.putExtras(bundle);
                    startActivity(intent);


                }
            }

            @Override
            public void onFailure(Call<GetSingleTripResponse> call, Throwable t) {

            }
        });

    }
}