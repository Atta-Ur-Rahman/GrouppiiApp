package com.techease.groupiiapplication.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.chatAdapter.ChatAdapter;
import com.techease.groupiiapplication.dataModel.chat.ChatModel;
import com.techease.groupiiapplication.socket.ChatApplication;

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


    boolean isConnected;


    private List<ChatModel> mMessages = new ArrayList();
    private ChatAdapter chatAdapter;
    private Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        Objects.requireNonNull(getSupportActionBar()).hide();
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        tvUserName.setText(bundle.getString("title_name"));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvMessage.setLayoutManager(mLayoutManager);
        rvMessage.setItemAnimator(new DefaultItemAnimator());
        chatAdapter = new ChatAdapter(this, mMessages);
        rvMessage.setAdapter(chatAdapter);


        ChatApplication app = new ChatApplication();
        mSocket = app.getSocket();
        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.connect();


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


    @SuppressLint("NonConstantResourceId")

    @OnClick({R.id.ivBack, R.id.iv_send_file, R.id.tvSend})
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.tvSend:


                if (!etMessageView.getText().equals("")) {


                    JSONObject object = new JSONObject();
                    try {

                        object.put("userid", 33);
                        object.put("touser", null);
                        object.put("tripid", 40);
                        object.put("message", etMessageView.getText().toString().trim());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    mSocket.emit("sendmessage", object);
                    mSocket.on("getbacksendmessage", sendMessages);


                    ChatModel chatModel=new ChatModel();

                    chatModel.setMessages(etMessageView.getText().toString());

//                        mMessages.add(new ChatModel(33, 22, etMessageView.getText().toString(), "date", "receiverImage", "type"));
                    mMessages.add(chatModel);
                    chatAdapter.notifyItemInserted(mMessages.size() - 1);
                    scrollToBottom();


                }

                break;

            case R.id.iv_send_file:

                break;
        }
    }


    protected Emitter.Listener sendMessages = new Emitter.Listener() {
        @Override
        public void call(Object... args) {





            if (mSocket.connected()) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        etMessageView.setText("");
                        JSONObject jsonObject = (JSONObject) args[0];


                        Log.d("zma single user", "" + jsonObject);


                    }
                });
            }
        }
    };

    private void scrollToBottom() {
        rvMessage.scrollToPosition(chatAdapter.getItemCount() - 1);
    }

}