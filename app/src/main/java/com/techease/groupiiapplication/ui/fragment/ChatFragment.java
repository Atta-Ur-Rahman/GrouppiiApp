package com.techease.groupiiapplication.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.chatAdapter.ChatBoxAdapter;
import com.techease.groupiiapplication.dataModel.socketModel.Message;
import com.techease.groupiiapplication.socket.ChatApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.etChat)
    EditText etChat;
    @BindView(R.id.btnChat)
    Button btnChat;

    @BindView(R.id.rvMessagelist)
    RecyclerView rvMessageList;
    public List<Message> messageList;
    public ChatBoxAdapter chatBoxAdapter;

    boolean isConnected;
    String message;
    private Socket mSocket;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_activity, container, false);
        ButterKnife.bind(this, view);


        ChatApplication app = new ChatApplication();
        mSocket = app.getSocket();


        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.on("new message", onNewMessage);
        mSocket.connect();


        messageList = new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvMessageList.setLayoutManager(mLayoutManager);
        rvMessageList.setItemAnimator(new DefaultItemAnimator());


        return view;
    }


    private void scrollToBottom() {
        rvMessageList.scrollToPosition(chatBoxAdapter.getItemCount() - 1);
    }

    @OnClick({R.id.btnChat, R.id.etChat})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnChat:


                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("userid", "33");


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (mSocket.connected()) {
                    mSocket.emit("getusers", jsonObject);
                    mSocket.on("allusers", new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            Log.d("zma", "new emit");
                        }
                    });
                }

                if (mSocket.connected()) {
                    Toast.makeText(getActivity(), "socket connected", Toast.LENGTH_SHORT).show();
                }


                break;
            case R.id.etChat:

                break;
        }

    }


    private Emitter.Listener getOnNewMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

        }
    };
    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d("zma", "connected...");
            isConnected = true;


            // This doesn't run in the UI thread, so use:
            // .runOnUiThread if you want to do something in the UI

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


    protected Emitter.Listener getAlluser = new Emitter.Listener() {
        @Override
        public void call(Object... args) {


            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    JSONObject data = (JSONObject) args[0];

                    Log.d("zma", "alluser" + data);


                }
            });
        }
    };


    protected Emitter.Listener getSingleUser = args -> {
        Log.d("zma object", "single");

        try {
            JSONObject messageJson = new JSONObject(args[0].toString());
            Log.d("zma object", messageJson + "");
            message = messageJson.getString("allusers");
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    etChat.setText(message);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }


        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    };

    private Emitter.Listener onNewMessage = args -> {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject data = (JSONObject) args[0];
                String username;
                String message;
                try {
                    username = data.getString("id");
                    message = data.getString("message");
                } catch (JSONException e) {
                    return;
                }

                // add the message to view
            }
        });
    };


}












































































/* private Socket mSocket;

    {
        try {
            mSocket = IO.socket("http://grouppii.com:9000");
//            Toast.makeText(getActivity(), "connected", Toast.LENGTH_SHORT).show();

            Log.d("zma socket",String.valueOf(mSocket));



        } catch (URISyntaxException e) {
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSocket.on("new message", onNewMessage);
        mSocket.connect();


        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.off("new message", onNewMessage);
        mSocket.off("typing", onTyping);
        mSocket.off("stop typing", onStopTyping);

        Log.d("zma socket",String.valueOf(mSocket));
    }
    private void attemptSend() {
        String message = etChat.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            return;
        }

        etChat.setText("");
        mSocket.emit("new message", message);

//        mSocket.on()
    }





    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("TAG", "Error connecting");
                    Toast.makeText(getActivity().getApplicationContext(),"error connect", Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];

                    Log.d("zma jsonobject",String.valueOf(data));
                    String username;
                    String message;
                    try {
                        username = data.getString("username");
                        message = data.getString("message");

                        Message m = new Message(username,message);
                        messageList.add(m);

                        // add the new updated list to the dapter
                        chatBoxAdapter = new ChatBoxAdapter(messageList);

                        // notify the adapter to update the recycler view

                        chatBoxAdapter.notifyDataSetChanged();

                        //set the adapter for the recycler view

                        rvMessageList.setAdapter(chatBoxAdapter);

                    } catch (JSONException e) {
                        return;
                    }

                    Toast.makeText(getActivity(), String.valueOf(username+"  "+message), Toast.LENGTH_SHORT).show();
                    // add the message to view
//                    addMessage(username, message);
                }
            });
        }
    };


    private Emitter.Listener onTyping = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
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
                    addTyping(username);
                }
            });
        }
    };

    private Emitter.Listener onStopTyping = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
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
                    removeTyping(username);
                }
            });
        }
    };


    private void addTyping(String username) {
//        messageList.add(new Message.Builder(Message.TYPE_ACTION)
//                .username(username).build());
//        mAdapter.notifyItemInserted(mMessages.size() - 1);

        Toast.makeText(getActivity(), username, Toast.LENGTH_SHORT).show();
        scrollToBottom();
    }

    private void removeTyping(String username) {
        Toast.makeText(getActivity(), username, Toast.LENGTH_SHORT).show();
//        for (int i = messageList.size() - 1; i >= 0; i--) {
//            Message message = messageList.get(i);
//            if (message.getMessage() == Message.TYPE_ACTION && message.getNickname().equals(username)) {
//                messageList.remove(i);
//                chatBoxAdapter.notifyItemRemoved(i);
//            }
//        }
    }*/