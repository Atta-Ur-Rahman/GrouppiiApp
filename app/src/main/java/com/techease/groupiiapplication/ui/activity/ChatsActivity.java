package com.techease.groupiiapplication.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.chatAdapter.ChatAdapter;
import com.techease.groupiiapplication.dataModel.chat.ChatModel;
import com.techease.groupiiapplication.socket.ChatApplication;
import com.techease.groupiiapplication.utils.AppRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatsActivity extends AppCompatActivity implements View.OnClickListener {


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

    @BindView(R.id.etMessageView)
    EditText etMessageView;
    @BindView(R.id.rvMessages)
    RecyclerView rvMessage;


    @BindView(R.id.tv_typer_name)
    TextView tvSenderName;
    @BindView(R.id.typing_layout)
    RelativeLayout layoutTyping;


    boolean isConnected;

    private String strTripId, strToUserId, strUsername, strMessageType = "1";
    private String message, sender, reciever, date, senderImage, type;
    int userID;


    private List<ChatModel> mMessages = new ArrayList();
    private ChatAdapter chatAdapter;
    private Socket mSocket;
    boolean aBooleanOneTimeHistoryLoad = true;

    private boolean mTyping = false;
    private Handler mTypingHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        Objects.requireNonNull(getSupportActionBar()).hide();

        init();
        socketConnectivity();
        GetAllMessages();


        etMessageView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (null == strUsername) return;
                if (!mSocket.connected()) return;

                if (!mTyping) {
                    mTyping = true;
                    JSONObject typingObject = new JSONObject();
                    try {
                        typingObject.put("username", strUsername);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mSocket.emit("typing", typingObject);
                }

                mTypingHandler.removeCallbacks(onTypingTimeout);
                mTypingHandler.postDelayed(onTypingTimeout, 600);
            }

            @Override
            public void afterTextChanged(Editable s) {
                removeTyping();
            }
        });
    }


    private Emitter.Listener onTyping = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    try {
                        username = data.getString("username");
                    } catch (JSONException e) {
                        Log.d("TAG", e.getMessage());
                        return;
                    }

                    if (username == null || username.equals("")) {
                        removeTyping();
                    } else {
                        addTyping(username);
                    }


                }
            });

        }
    };


    private void addTyping(String username) {
        if (username.equals(strUsername)) {
            removeTyping();
        } else {
            layoutTyping.setVisibility(View.VISIBLE);
            tvSenderName.setText(username);
            scrollToBottom();
        }

    }


    private void removeTyping() {
        layoutTyping.setVisibility(View.GONE);
    }

    private Runnable onTypingTimeout = new Runnable() {
        @Override
        public void run() {
            if (!mTyping) return;
            mTyping = false;
            mSocket.emit("stop typing");
        }
    };


    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        ButterKnife.bind(this);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Bundle bundle = getIntent().getExtras();
        tvUserName.setText(bundle.getString("title_name"));

        strTripId = bundle.getString("tripId");
        userID = AppRepository.mUserID(this);
        strToUserId = bundle.getString("toUserId");

//        Log.d("zma trip id", strTripId);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvMessage.setLayoutManager(mLayoutManager);
        rvMessage.setItemAnimator(new DefaultItemAnimator());
        chatAdapter = new ChatAdapter(this, mMessages);
        rvMessage.setAdapter(chatAdapter);

    }


    private void GetAllMessages() {

        JSONObject objectGetAllMessages = new JSONObject();
        try {

            objectGetAllMessages.put("userid", userID);
            objectGetAllMessages.put("touser", null);
            objectGetAllMessages.put("tripid", strTripId);


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
                                    JSONArray jsonArray = (JSONArray) args[0];
                                    Log.d("zma all messages", "" + jsonArray);

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        try {
                                            JSONObject data = jsonArray.getJSONObject(i);
                                            sender = data.getString("touser");
                                            reciever = data.getString("fromuser");
                                            message = data.getString("message");
                                            date = data.getString("created_at");
                                            senderImage = data.getString("picture");
                                            type = data.getString("type");

                                            if (strToUserId.equals(reciever) || strToUserId.equals(sender)) {
                                                addMessage(sender, reciever, message, date, senderImage, type);

                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(ChatsActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }

                                        Log.d("type", "this is type " + sender);
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
                        object.put("userid", userID);
                        object.put("touser", strToUserId);
                        object.put("tripid", strTripId);
                        object.put("message", etMessageView.getText().toString().trim());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    mSocket.emit("sendmessage", object);
                }
                break;
            case R.id.iv_send_file:
                break;


        }
    }

    protected Emitter.Listener sendMessages = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            if (ChatsActivity.this != null) {
                if (mSocket.connected()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            etMessageView.setText("");
                            JSONObject jsonObject = (JSONObject) args[0];
                            try {
                                if (jsonObject != null) {
                                    String sender = jsonObject.getString("touser");
                                    String reciever = jsonObject.getString("fromuser");
                                    String message = jsonObject.getString("message");
                                    String date = jsonObject.getString("created_at");
                                    String tripId=jsonObject.getString("tripid");
//                            senderImage = jsonObject.getString("picture");
                                    String type = jsonObject.getString("type");

                                    Log.d("zma message send sho", "" + jsonObject);


                                    if (strTripId.equals(tripId)) {
                                        addMessage(sender, reciever, message, date, "senderImage", type);

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


    private void addMessage(String from, String to, String message, String date, String receiverImage, String type) {
        mMessages.add(new ChatModel(Integer.parseInt(from), Integer.parseInt(to), message, date, receiverImage, type));
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

        mSocket.on("typing", onTyping);
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
        aBooleanOneTimeHistoryLoad = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
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


