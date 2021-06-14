package com.techease.groupiiapplication.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
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
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.chatAdapter.ChatAdapter;
import com.techease.groupiiapplication.dataModel.chats.chat.ChatModel;
import com.techease.groupiiapplication.socket.ChatApplication;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.EmojiEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    private String strTripId, strToUserId, strUsername, strMessageType = "1", strChatType, strChatImageLink;
    private String message, toUser, fromUser, fromUserName, tripId, isSent, isRead, date, senderImage, type;
    int userID;

    boolean aBooleanShowKeyboardListener = true;


    private List<ChatModel> mMessages = new ArrayList();
    private ChatAdapter chatAdapter;
    private Socket mSocket;
    boolean aBooleanOneTimeHistoryLoad = true;

    Dialog dialog;

    private boolean mTyping = false;
    private Handler mTypingHandler = new Handler();

    private static int firstVisibleInListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        Objects.requireNonNull(getSupportActionBar()).hide();

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

        if (strChatType.equals("group")) {
            Glide.with(this).load(strChatImageLink).placeholder(R.drawable.group_image).into(ivChatImage);
        }
        if (strChatType.equals("user")) {
            Glide.with(this).load(strChatImageLink).placeholder(R.drawable.user).into(ivChatImage);
        }
        Log.d("zma trip id", strTripId);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvMessage.setLayoutManager(mLayoutManager);
        rvMessage.setItemAnimator(new DefaultItemAnimator());
        chatAdapter = new ChatAdapter(this, mMessages, strTripId);
        rvMessage.setAdapter(chatAdapter);
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
                if (etMessageView.getText().length() > 1) {
                    JSONObject object = new JSONObject();
                    try {


                        if (strChatType.equals("group")) {
                            object.put("userid", userID);
                            object.put("touser", strToUserId);
                            object.put("tripid", strTripId);
                        }
                        if (strChatType.equals("user")) {
                            object.put("userid", userID);
                            object.put("touser", strToUserId);
                            object.put("tripid", null);
                        }

                        object.put("message", EmojiEncoder.encodeEmoji(etMessageView.getText().toString()));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    tvSend.setEnabled(false);
                    mSocket.emit("sendmessage", object);
//                    addMessage(String.valueOf(userID), fromUser, fromUserName, message, date, senderImage, type, isSent);
                }
                break;
            case R.id.iv_send_file:
                break;


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


                                            Log.d("zma sender", "this is type " + toUser);
//                                            strToUserId.equals(fromUser) ||


//                                            if ( strToUserId.equals(toUser)) {
                                            addMessage(toUser, fromUser, fromUserName, message, date, senderImage, type, isSent, isRead);

//                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(ChatsActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                            etMessageView.setText("");
                            tvSend.setEnabled(true);
                            JSONObject jsonObject = (JSONObject) args[0];
                            try {
                                if (jsonObject != null) {
                                    toUser = jsonObject.getString("touser");
                                    fromUser = jsonObject.getString("fromuser");
//                                    fromUserName = jsonObject.getString("fromusername");
                                    message = jsonObject.getString("message");
                                    date = jsonObject.getString("created_at");
                                    tripId = jsonObject.getString("tripid");
                                    isSent = jsonObject.getString("is_sent");
                                    type = jsonObject.getString("type");
                                    isRead = jsonObject.getString("is_read");


                                    Log.d("zma message send sho", "" + jsonObject);

                                    if (strChatType.equals("user")) {
                                        if (toUser.equals("" + AppRepository.mUserID(ChatsActivity.this)) || (fromUser.equals("" + AppRepository.mUserID(ChatsActivity.this)))) {
                                            addMessage(toUser, fromUser, "", message, date, "senderImage", type, isSent, isRead);
                                        }
                                    }
                                    if (strChatType.equals("group")) {
                                        if (strTripId.equals(tripId)) {
                                            addMessage(toUser, fromUser, "", message, date, "senderImage", type, isSent, isRead);
                                        }
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    });
                }
            }
        }
    };


    private void addMessage(String from, String to, String fromUserName, String message, String date, String receiverImage, String type, String isSent, String isRead) {
        mMessages.add(new ChatModel(Integer.parseInt(from), Integer.parseInt(to), fromUserName, message, date, receiverImage, type, isSent, isRead));
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
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.on("getbacksendmessage", sendMessages);
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

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


}



























/*

    //recieving new messages from coach
    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {


            if (mSocket.connected()) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                try {
                                    JSONObject data = (JSONObject) args[0];
                                    sender = data.getString("fromuser");
                                    reciever = data.getString("touser");
                                    message = data.getString("message");
                                    date = data.getString("created_at");
                                    senderImage = data.getString("picture");
                                    type = data.getString("type");


                                    Log.d("type new", "this is type " + sender);

                                    //checking and counting new messages
//                                        if(strUserID.equals(sender)){
//                                            int countMessage=GeneralUtilities.getSharedPreferences(getActivity()).getInt("message_count",0);
//                                            countMessage++;
//                                            GeneralUtilities.putIntValueInEditor(getActivity(),"message_count",countMessage);
//                                        }

                                    //adding new message to list
                                    if (strToUserId.equals(reciever) || strToUserId.equals(sender)) {
                                        addMessage(sender, reciever, message, date, senderImage, type);
                                    }

//                                        removeTyping();

                                } catch (JSONException e) {
                                    Toast.makeText(ChatsActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();

                                }
                            }
                        });
                    }
                });
            }
        }

    };
*/


//        etMessageView.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (null == strUsername) return;
//                if (!mSocket.connected()) return;
//
//                if (!mTyping) {
//                    mTyping = true;
//                    JSONObject typingObject = new JSONObject();
//                    try {
//                        typingObject.put("username", strUsername);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    mSocket.emit("typing", typingObject);
//                }
//
//                mTypingHandler.removeCallbacks(onTypingTimeout);
//                mTypingHandler.postDelayed(onTypingTimeout, 600);
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                removeTyping();
//            }
//        });


//    private Emitter.Listener onTyping = new Emitter.Listener() {
//        @Override
//        public void call(final Object... args) {
//
//
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    JSONObject data = (JSONObject) args[0];
//                    String username;
//                    try {
//                        username = data.getString("username");
//                    } catch (JSONException e) {
//                        Log.d("TAG", e.getMessage());
//                        return;
//                    }
//
//                    if (username == null || username.equals("")) {
//                        removeTyping();
//                    } else {
//                        addTyping(username);
//                    }
//
//
//                }
//            });
//
//        }
//    };

//
//    private void addTyping(String username) {
//        if (username.equals(strUsername)) {
//            removeTyping();
//        } else {
//            layoutTyping.setVisibility(View.VISIBLE);
//            tvSenderName.setText(username);
//            scrollToBottom();
//        }
//
//    }
//
//
//    private void removeTyping() {
//        layoutTyping.setVisibility(View.GONE);
//    }
//
//    private Runnable onTypingTimeout = new Runnable() {
//        @Override
//        public void run() {
//            if (!mTyping) return;
//            mTyping = false;
//            mSocket.emit("stop typing");
//        }
//    };


