package com.techease.groupiiapplication.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.inputmethod.InputContentInfoCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.chatAdapter.ChatAdapter;
import com.techease.groupiiapplication.chat.view.chatEdittext.MyEditText;
import com.techease.groupiiapplication.chat.view.fileUploader.FileUploader;
import com.techease.groupiiapplication.dataModel.chats.chat.ChatModel;
import com.techease.groupiiapplication.interfaceClass.refreshChat.ConnectChatResfresh;
import com.techease.groupiiapplication.socket.ChatApplication;
import com.techease.groupiiapplication.ui.fragment.chat.AllUsersChatFragment;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.Constants;
import com.techease.groupiiapplication.utils.EmojiEncoder;
import com.techease.groupiiapplication.utils.StringHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatsActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean keyboardListenersAttached = false;
    private ViewGroup rootLayout;

    protected void onShowKeyboard(int keyboardHeight) {
    }

    protected void onHideKeyboard() {
    }


    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.ivMenu)
    ImageView ivMenu;
    @BindView(R.id.tvSend)
    TextView tvSend;
    @BindView(R.id.iv_send_file)
    ImageView ivSendFile;
    @BindView(R.id.ivGroup)
    CircleImageView ivChatImage;

    @BindView(R.id.etMessageView)
    EditText etMessageView;
    @BindView(R.id.rvMessages)
    RecyclerView rvMessage;

    @BindView(R.id.tv_typer_name)
    TextView tvSenderName;
    @BindView(R.id.typing_layout)
    RelativeLayout layoutTyping;

    @BindView(R.id.rlRootLayout)
    RelativeLayout rlRootLayout;
    boolean isConnected;

    MyEditText myEditText;


    private static final String TAG = "MainActivity";
    private static final String URL = "http://192.168.0.103:8090";

    private String strTripId, strCheckToUserID = "", strToUserId, strUsername, senderImage, strMessageType, strChatType, strChatImageLink;
    private String message, toUser, fromUser, fromUserName, tripId, isSent, isRead, date, currentChatUserId = "", type;
    int userID;

    boolean aBooleanShowKeyboardListener = true, aBooleanChaResfresh = true;


    private List<ChatModel> mMessages = new ArrayList();
    private ChatAdapter chatAdapter;
    private Socket mSocket;

    boolean aBooleanOneTimeHistoryLoad = true;

    Dialog dialog;

    private boolean mTyping = false;
    private Handler mTypingHandler = new Handler();

    private static int firstVisibleInListview;

    ArrayList<String> chatImageFiles = new ArrayList<>();
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        pDialog = new ProgressDialog(this);
        Objects.requireNonNull(getSupportActionBar()).hide();
        myEditText = findViewById(R.id.etMessageView);
        init();
        socketConnectivity();
        GetAllMessages();
        KeyBoardListener();


    }

    private void KeyBoardListener() {
//        etMessageView.requestFocus();
        rlRootLayout = findViewById(R.id.rlRootLayout);
        rlRootLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                rlRootLayout.getWindowVisibleDisplayFrame(r);
                int screenHeight = rlRootLayout.getRootView().getHeight();
                int keypadHeight = screenHeight - r.bottom;
                if (keypadHeight > screenHeight * 0.15) {
                    if (aBooleanShowKeyboardListener) {
                        aBooleanShowKeyboardListener = false;
                        scrollToBottom();
                    }
                } else {

                    aBooleanShowKeyboardListener = true;
                }
            }
        });


    }


    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        ButterKnife.bind(this);
        dialog = AlertUtils.createProgressDialog(this);
        Bundle bundle = getIntent().getExtras();

        tvUserName.setText(bundle.getString("title_name"));

        strTripId = bundle.getString("tripId");
        userID = AppRepository.mUserID(this);
        strToUserId = bundle.getString("toUserId");
        strChatType = bundle.getString("type");
        strChatImageLink = bundle.getString("picture");

        Log.d("zmamessagetypedd", strChatType);


        if (strChatType.equals("group")) {
            Glide.with(this).load(strChatImageLink).placeholder(R.drawable.group_image).into(ivChatImage);
        }
        if (strChatType.equals("user")) {
            Glide.with(this).load(strChatImageLink).placeholder(R.drawable.user).into(ivChatImage);
        }
//        Log.d("zma trip id", strTripId);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvMessage.setLayoutManager(mLayoutManager);
        rvMessage.setItemAnimator(new DefaultItemAnimator());
        chatAdapter = new ChatAdapter(this, mMessages, strTripId);
        rvMessage.setAdapter(chatAdapter);


        myEditText.setKeyBoardInputCallbackListener(new MyEditText.KeyBoardInputCallbackListener() {
            @Override
            public void onCommitContent(InputContentInfoCompat inputContentInfo,
                                        int flags, Bundle opts) {
                //you will get your gif/png/jpg here in opts bundle

//                Log.d("zmabundle", inputContentInfo.getContentUri() + "");

//                sendMessageFun("image", inputContentInfo.getContentUri()+"");
//                File file=new File(inputContentInfo.getContentUri().getPath());
//                chatImageFiles.add(String.valueOf(file));
//                uploadFiles();

                chatImageFiles.clear();
                chatImageFiles.add(createCopyAndReturnRealPath(ChatsActivity.this, inputContentInfo.getContentUri()));
                uploadFiles();


            }
        });
    }

    @Nullable
    public static String createCopyAndReturnRealPath(
            @NonNull Context context, @NonNull Uri uri) {
        final ContentResolver contentResolver = context.getContentResolver();
        if (contentResolver == null)
            return null;

        // Create file path inside app's data dir
        String filePath = context.getApplicationInfo().dataDir + File.separator + "temp_file";
        File file = new File(filePath);
        try {
            InputStream inputStream = contentResolver.openInputStream(uri);
            if (inputStream == null)
                return null;
            OutputStream outputStream = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0)
                outputStream.write(buf, 0, len);
            outputStream.close();
            inputStream.close();
        } catch (IOException ignore) {
            return null;
        }
        return file.getAbsolutePath();
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.ivBack, R.id.iv_send_file, R.id.tvSend})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.tvSend:
                sendMessageFun("text", EmojiEncoder.encodeEmoji(etMessageView.getText().toString()));
                break;
            case R.id.iv_send_file:

                chatImageFiles.clear();
                ArrayList<String> uris = new ArrayList<>();
                Options options = Options.init()
                        .setRequestCode(100)                                           //Request code for activity results
                        .setCount(5)                                                   //Number of images to restict selection count
                        .setFrontfacing(false)                                         //Front Facing camera on start
                        .setPreSelectedUrls(uris)                               //Pre selected Image Urls
                        .setSpanCount(5)                                               //Span count for gallery min 1 & max 5
                        .setMode(Options.Mode.All)                                     //Option to select only pictures or videos or both
                        .setVideoDurationLimitinSeconds(30)                            //Duration for video recording
                        .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)     //Orientaion
                        .setPath("/Grouppii/files");                                       //Custom Path For media Storage


                Pix.start(this, options);
                break;


        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            ArrayList<String> returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
            chatImageFiles.addAll(returnValue);
            uploadFiles();

        }
    }

    private void sendMessageFun(String messageType, String strMessage) {
        if (strMessage.length() > 1) {
            JSONObject object = new JSONObject();
            try {
                if (strChatType.equals("group")) {
                    object.put("userid", userID);
                    object.put("touser", null);
                    object.put("tripid", strTripId);

                }
                if (strChatType.equals("user")) {
                    object.put("userid", userID);
                    object.put("touser", strToUserId);
                    object.put("tripid", null);
                }
                object.put("type", messageType);
                object.put("message", strMessage);

//                Log.d("zmachat", "" + object);


            } catch (JSONException e) {
                e.printStackTrace();
//                Log.d("zmasendmessage", e.getMessage());
            }

            ivSendFile.setEnabled(false);
            tvSend.setEnabled(false);
            mSocket.emit("sendmessage", object);
        }
    }


    private void GetAllMessages() {
        dialog.show();
        JSONObject objectGetAllMessages = new JSONObject();
        try {
            if (strChatType.equals("group")) {
                objectGetAllMessages.put("userid", userID);
                objectGetAllMessages.put("touser", null);
                objectGetAllMessages.put("tripid", strTripId);
            }
            if (strChatType.equals("user")) {
                objectGetAllMessages.put("userid", userID);
                objectGetAllMessages.put("touser", strToUserId);
                objectGetAllMessages.put("tripid", null);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mSocket.emit("getAllMessages", objectGetAllMessages);
        mSocket.on("AllMessages", onChatHistory);

    }

    //getting chat history
    private Emitter.Listener onChatHistory = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            if (aBooleanOneTimeHistoryLoad) {
                aBooleanOneTimeHistoryLoad = false;
                if (mSocket.connected()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dialog.dismiss();
                                    JSONArray jsonArray = (JSONArray) args[0];
                                    Log.d("zma all messages", "" + jsonArray);

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        try {
                                            JSONObject data = jsonArray.getJSONObject(i);
                                            toUser = data.getString("touser");
                                            fromUser = data.getString("fromuser");
                                            if (strChatType.equals("group")) {
                                                fromUserName = data.getString("fromusername");
                                            }
                                            message = data.getString("message");
                                            date = data.getString("created_at");
//                                            senderImage = data.getString("picture");
                                            type = data.getString("type");
                                            isSent = data.getString("is_sent");
                                            isRead = data.getString("is_read");
                                            strMessageType = data.getString("type");


//                                            strToUserId.equals(fromUser) ||

//                                            if ( strToUserId.equals(toUser)) {
                                            addMessage(toUser, fromUser, fromUserName, message, date, senderImage, type, isSent, isRead, strMessageType);

//                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
//                                            Toast.makeText(ChatsActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                        }
                    });
                } else {
                    Log.d("zma socket", "not connected");
                }
            }
        }
    };
    protected Emitter.Listener sendMessages = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            if (ChatsActivity.this != null) {
                if (mSocket.connected()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Log.d("zma", "message send sho");

                            JSONObject jsonObject = (JSONObject) args[0];
                            try {
                                if (jsonObject != null) {
                                    toUser = jsonObject.getString("touser");
                                    fromUser = jsonObject.getString("fromuser");
                                    message = jsonObject.getString("message");
                                    date = jsonObject.getString("created_at");
                                    tripId = jsonObject.getString("tripid");
                                    isSent = jsonObject.getString("is_sent");

                                    isRead = jsonObject.getString("is_read");
                                    strMessageType = jsonObject.getString("type");
                                    currentChatUserId = jsonObject.getString("id");
                                    Log.d("zmatestingIDs", "type" + strChatType + "FromUserID = " + fromUser + "  and   " + "ToUserID = " + toUser + "   and  " + "AppUserID = " + AppRepository.mUserID(ChatsActivity.this) + "  and  CurrentChatID " + Constants.currentUserChatId);

//                                    Log.d("zma message send sho", "" + jsonObject);
                                    if (strChatType.equals("user") && !toUser.equals("null")) {
//                                        Log.d("zmatestingIDs", "FromUserID = " + fromUser + "  and   " + "ToUserID = " + toUser + "   and  " + "AppUserID = " + AppRepository.mUserID(ChatsActivity.this)+"  and  CurrentChatID "+Constants.currentUserChatId);
                                        if (fromUser.equals("" + AppRepository.mUserID(ChatsActivity.this)) || (fromUser.equals(Constants.currentUserChatId))) {
                                            addMessage(toUser, fromUser, "", message, date, "senderImage", type, isSent, isRead, strMessageType);
                                        }
                                    }
                                    if (strChatType.equals("group")) {

//                                        Log.d("zmatestingIDs", "FromUserID = " + fromUser + "  and   " + "ToUserID = " + toUser + "   and  " + "AppUserID = " + AppRepository.mUserID(ChatsActivity.this) + "  and  CurrentChatID " + Constants.currentUserChatId);
                                        if (strTripId.equals(tripId)) {
                                            addMessage(toUser, fromUser, "", message, date, "senderImage", type, isSent, isRead, strMessageType);
                                        }
                                    }
                                    if (fromUser.equals(String.valueOf(AppRepository.mUserID(ChatsActivity.this)))) {
                                        etMessageView.setText("");
                                        tvSend.setEnabled(true);
                                        ivSendFile.setEnabled(true);
                                    }
                                    dialog.dismiss();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            if (aBooleanChaResfresh) {
                                aBooleanChaResfresh = false;
                                try {
                                    AllUsersChatFragment.aBooleanRefreshSocket = false;
                                    ConnectChatResfresh.setMyBoolean(true);
                                } catch (Exception e) {
                                    e.printStackTrace();
//                                    Log.d("zmaerror",e.getMessage());
                                    AllUsersChatFragment.aBooleanRefreshSocket = true;
                                }
                            }

                        }
                    });
                }
            }
        }
    };

    private void addMessage(String from, String to, String fromUserName, String message, String date, String receiverImage, String userType, String isSent, String isRead, String messageType) {
        mMessages.add(new ChatModel(AppRepository.mUserID(this), Integer.parseInt(to), fromUserName, message, date, receiverImage, userType, isSent, isRead, messageType));
        chatAdapter.notifyItemInserted(mMessages.size() - 1);
        scrollToBottom();

    }

    private void scrollToBottom() {
        rvMessage.scrollToPosition(chatAdapter.getItemCount() - 1);
    }


    private void socketConnectivity() {

        ChatApplication app = new ChatApplication();
        mSocket = app.getSocket();
        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on("getbacksendmessage", sendMessages);
        mSocket.on("start", arg0 -> Toast.makeText(ChatsActivity.this, "start image sending", Toast.LENGTH_SHORT).show());
        mSocket.connect();

//        mSocket.on("typing", onTyping);
    }


    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d("zma", "connected...");
            isConnected = true;


        }
    };
    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d("zma", "Error connecting...");
            isConnected = false;

        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d("zma", "dis connecting...");
            isConnected = false;

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mSocket.connected();
        aBooleanOneTimeHistoryLoad = true;
        aBooleanChaResfresh = true;

        StringHelper.checkFirebase = false;


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
        StringHelper.checkFirebase = true;
        if (keyboardListenersAttached) {
            rootLayout.getViewTreeObserver().removeGlobalOnLayoutListener(keyboardLayoutListener);
        }

    }


    private ViewTreeObserver.OnGlobalLayoutListener keyboardLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            int heightDiff = rootLayout.getRootView().getHeight() - rootLayout.getHeight();
            int contentViewTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();

            LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(ChatsActivity.this);

            if (heightDiff <= contentViewTop) {
                onHideKeyboard();

                Intent intent = new Intent("KeyboardWillHide");
                broadcastManager.sendBroadcast(intent);
            } else {
                int keyboardHeight = heightDiff - contentViewTop;
                onShowKeyboard(keyboardHeight);

                Intent intent = new Intent("KeyboardWillShow");
                intent.putExtra("KeyboardHeight", keyboardHeight);
                broadcastManager.sendBroadcast(intent);
            }
        }
    };


    protected void attachKeyboardListeners() {
        if (keyboardListenersAttached) {
            return;
        }
        rootLayout = (ViewGroup) findViewById(R.id.rlRootLayout);
        rootLayout.getViewTreeObserver().addOnGlobalLayoutListener(keyboardLayoutListener);
        keyboardListenersAttached = true;
    }


    public void uploadFiles() {
        File[] filesToUpload = new File[chatImageFiles.size()];
        for (int i = 0; i < chatImageFiles.size(); i++) {
            filesToUpload[i] = new File(chatImageFiles.get(i));
        }
        showProgress("Uploading media ...");
        FileUploader fileUploader = new FileUploader();
        fileUploader.uploadFiles("/", "images", filesToUpload, new FileUploader.FileUploaderCallback() {
            @Override
            public void onError() {
                hideProgress();
            }

            @Override
            public void onFinish(String[] responses) {
                hideProgress();

                for (int i = 0; i < responses.length; i++) {
                    String strImageUrl = responses[i];
                    Log.e("RESPONSE " + i, responses[i]);
                    strImageUrl = strImageUrl.replace("[", "");
                    strImageUrl = strImageUrl.replace("]", "");
                    sendMessageFun("image", strImageUrl);
                }
            }

            @Override
            public void onProgressUpdate(int currentpercent, int totalpercent, int filenumber) {
                updateProgress(totalpercent, "Uploading images " + filenumber, "");
                Log.e("Progress Status", currentpercent + " " + totalpercent + " " + filenumber);
            }
        });
    }

    public void updateProgress(int val, String title, String msg) {
        pDialog.setTitle(title);
        pDialog.setMessage(msg);
        pDialog.setProgress(val);
    }

    public void showProgress(String str) {
        try {
            pDialog.setCancelable(false);
            pDialog.setTitle("Please wait");
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.setMax(100); // Progress Dialog Max Value
            pDialog.setMessage(str);
            if (pDialog.isShowing())
                pDialog.dismiss();
            pDialog.show();
        } catch (Exception e) {

        }
    }

    public void hideProgress() {
        try {
            if (pDialog.isShowing())
                pDialog.dismiss();
        } catch (Exception e) {

        }

    }


}

