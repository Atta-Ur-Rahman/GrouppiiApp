package com.techease.groupiiapplication.firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.ui.activity.HomeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.logging.Logger;


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

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
//        try {
            Map<String, String> data = remoteMessage.getData();
//            String myCustomKey = data.get("notificationType");


//
//            JSONObject jsonObject = new JSONObject());
            Log.d("zmachat data",data+"");

//            int orderId = jsonObject.getInt("orderId");
//            msg = jsonObject.getString("notificationType");
//            int userID = jsonObject.getInt("userId");
//            productName = jsonObject.getString("productName");
//            if (jsonObject.has("price")) {
//                String price = jsonObject.getString("price");
//                if (price != null) {
//                    SharedPrefUtils.getEditor(this).putString("price", price).commit();
//                } else {
//                    SharedPrefUtils.getEditor(this).putString("price", "NA").commit();
//                }
//            }
//            SharedPrefUtils.getEditor(this).putInt("orderId", orderId).commit();
//            SharedPrefUtils.getEditor(this).putString("productName", productName).commit();
//            SharedPrefUtils.getEditor(this).putInt("userId", userID).commit();
//
//            SharedPrefUtils.getEditor(this).putInt("productID", jsonObject.getInt("productId")).commit();
//            productName="Product: "+productName;
//            Log.d("zma notification msg", msg);
//            String msg = remoteMessage.getData().get("custom");
//            if (msg.equals("newOrder")) {
//                isNewOrder = true;
//                isAcceptOffer = true;
//                isRejectOffer = false;
//                isDeliverOrder = false;
//                isReviewOrder = false;
//                SharedPrefUtils.getEditor(this).putBoolean("newOrder", true).commit();
//                SharedPrefUtils.getEditor(this).putBoolean("acceptOrder", false).commit();
//                SharedPrefUtils.getEditor(this).putBoolean("rejectOrder", false).commit();
//                SharedPrefUtils.getEditor(this).putBoolean("orderDelivered", false).commit();
//                SharedPrefUtils.getEditor(this).putBoolean("reviewOrder", false).commit();
//                intent = new Intent(this, BottomNavigationActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                msg = "Waoow! You have a new order";
//            } else if (msg.equals("acceptOrder")) {
//                isNewOrder = false;
//                isAcceptOffer = true;
//                isRejectOffer = false;
//                isDeliverOrder = false;
//                isReviewOrder = false;
//                SharedPrefUtils.getEditor(this).putBoolean("newOrder", false).commit();
//                SharedPrefUtils.getEditor(this).putBoolean("acceptOrder", true).commit();
//                SharedPrefUtils.getEditor(this).putBoolean("rejectOrder", false).commit();
//                SharedPrefUtils.getEditor(this).putBoolean("orderDelivered", false).commit();
//                SharedPrefUtils.getEditor(this).putBoolean("reviewOrder", false).commit();
//                msg = "Congratulation! Your order has been accepted";
//                startActivity(new Intent(this, ProductDetailActivity.class));
//
//            } else if (msg.equals("rejectOrder")) {
//                isAcceptOffer = false;
//                isNewOrder = false;
//                isRejectOffer = true;
//                isDeliverOrder = false;
//                isReviewOrder = false;
//                SharedPrefUtils.getEditor(this).putBoolean("newOrder", false).commit();
//                SharedPrefUtils.getEditor(this).putBoolean("acceptOrder", false).commit();
//                SharedPrefUtils.getEditor(this).putBoolean("rejectOrder", true).commit();
//                SharedPrefUtils.getEditor(this).putBoolean("orderDelivered", false).commit();
//                SharedPrefUtils.getEditor(this).putBoolean("reviewOrder", false).commit();
//                 intent = new Intent(this, BottomNavigationActivity.class);
//                msg = "Oops! Your order has been rejected";
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            } else if (msg.equals("deliverOrder")) {
//                isAcceptOffer = false;
//                isNewOrder = false;
//                isRejectOffer = false;
//                isDeliverOrder = true;
//                isReviewOrder = false;
//                SharedPrefUtils.getEditor(this).putBoolean("newOrder", false).commit();
//                SharedPrefUtils.getEditor(this).putBoolean("acceptOrder", false).commit();
//                SharedPrefUtils.getEditor(this).putBoolean("rejectOrder", false).commit();
//                SharedPrefUtils.getEditor(this).putBoolean("orderDelivered", true).commit();
//                SharedPrefUtils.getEditor(this).putBoolean("reviewOrder", false).commit();
//                intent = new Intent(this, BottomNavigationActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                msg = "Your order has been delivered!";
//            } else if (msg.equals("reviewOrder")) {
//                isAcceptOffer = false;
//                isNewOrder = false;
//                isRejectOffer = false;
//                isDeliverOrder = false;
//                isReviewOrder = true;
//                SharedPrefUtils.getEditor(this).putBoolean("newOrder", false).commit();
//                SharedPrefUtils.getEditor(this).putBoolean("acceptOrder", false).commit();
//                SharedPrefUtils.getEditor(this).putBoolean("rejectOrder", false).commit();
//                SharedPrefUtils.getEditor(this).putBoolean("orderDelivered", false).commit();
//                SharedPrefUtils.getEditor(this).putBoolean("reviewOrder", true).commit();
//
//
//                SharedPrefUtils.getEditor(this).putString("stars", jsonObject.getString("stars")).commit();
//                SharedPrefUtils.getEditor(this).putString("reviewerProfilePic", jsonObject.getString("userprofilePicture")).commit();
//                SharedPrefUtils.getEditor(this).putString("reviewerName", jsonObject.getString("userName")).commit();
//                SharedPrefUtils.getEditor(this).putString("reviewComment", jsonObject.getString("review")).commit();
//                SharedPrefUtils.getEditor(this).putString("reviewProductName", jsonObject.getString("productName")).commit();
//                intent = new Intent(this, BottomNavigationActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
//        String channelId = "Default";
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle("Soxs")
//                .setContentText(remoteMessage.getNotification().getBody()).setAutoCancel(true).setContentIntent(pendingIntent);
//
//        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT
                        | PendingIntent.FLAG_ONE_SHOT);
        String channelId = "Default";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.grouppii_logo)
                .setContentTitle(msg)
                .setContentText(productName).setAutoCancel(true).setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        handleNotification(remoteMessage.getNotification().getBody());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());
    }


    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        } else {
            // If the app is in background, firebase itself handles the notification
        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        try {
            JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");
            boolean isBackground = data.getBoolean("is_background");
            String imageUrl = data.getString("image");
            String timestamp = data.getString("timestamp");
            JSONObject payload = data.getJSONObject("payload");

            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            Log.e(TAG, "isBackground: " + isBackground);
            Log.e(TAG, "payload: " + payload.toString());
            Log.e(TAG, "imageUrl: " + imageUrl);
            Log.e(TAG, "timestamp: " + timestamp);


            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
            } else {
                // app is in background, show the notification in notification tray
                Intent resultIntent = new Intent(getApplicationContext(), HomeActivity.class);
                resultIntent.putExtra("message", message);

                // check for image attachment
                if (TextUtils.isEmpty(imageUrl)) {
                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                } else {
                    // image is present, show notification with image
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }

    private void showSmallNotification(NotificationCompat.Builder mBuilder, int icon, String title, String message, String timeStamp, PendingIntent resultPendingIntent, Uri alarmSound) {

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        inboxStyle.addLine(message);

        Notification notification;
        notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentIntent(resultPendingIntent)
                .setSound(alarmSound)
                .setStyle(inboxStyle)
                .setWhen(getTimeMilliSec(timeStamp))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), icon))
                .setContentText(message)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Config.NOTIFICATION_ID, notification);
    }

    private long getTimeMilliSec(String timeStamp) {
        return 0;
    }


}