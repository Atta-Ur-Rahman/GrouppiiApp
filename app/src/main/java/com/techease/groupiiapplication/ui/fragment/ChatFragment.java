package com.techease.groupiiapplication.ui.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.ActiveTripAdapter;
import com.techease.groupiiapplication.adapter.chatAdapter.AllUserChatAdapter;
import com.techease.groupiiapplication.dataModel.chat.ChatAllUserDataModel;
import com.techease.groupiiapplication.dataModel.socketModel.Message;
import com.techease.groupiiapplication.socket.ChatApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import static com.techease.groupiiapplication.ui.fragment.TripFragment.activeList;

public class ChatFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.etChat)
    EditText etChat;


    @BindView(R.id.searchViewChat)
    SearchView searchView;


    @BindView(R.id.rvMessagelist)
    RecyclerView rvMessageList;
    public List<Message> messageList;
    public AllUserChatAdapter allUserChatAdapter;

    boolean isConnected;
    String id, strMessage, strTitleName;
    private Socket mSocket;
    JSONObject jsonObjectGetAllUsers = new JSONObject();
    LinearLayoutManager linearLayoutManager;
    private List<ChatAllUserDataModel> chatAllUserDataModels = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_activity, container, false);
        ButterKnife.bind(this, view);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        allUserChatAdapter = new AllUserChatAdapter(getActivity(),chatAllUserDataModels);
        rvMessageList.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        rvMessageList.setAdapter(allUserChatAdapter);


        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Search here...");

        ChatApplication app = new ChatApplication();
        mSocket = app.getSocket();

         jsonObjectGetAllUsers = new JSONObject();
        try {
            jsonObjectGetAllUsers.put("userid", "33");


        } catch (JSONException e) {
            e.printStackTrace();
        }


        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.connect();



        messageList = new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvMessageList.setLayoutManager(mLayoutManager);
        rvMessageList.setItemAnimator(new DefaultItemAnimator());



        JSONObject object = new JSONObject();
        try {

            object.put("userid", 33);
            object.put("touser", null);
            object.put("tripid", 40);
            object.put("message", "testing message second ");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        mSocket.emit("sendmessage", object);
        mSocket.on("getbacksendmessage", getSingleMessages);


        JSONObject objectGetAll = new JSONObject();
        try {

            object.put("userid", 33);
            object.put("touser", 33);
            object.put("tripid", 38);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        mSocket.emit("getAllMessages", objectGetAll);
        mSocket.on("AllMessages", getAllMessages);

        return view;
    }


    //sending message to coach
    private void attemptMessageSend(String message, String type) {
        if (mSocket.connected()) {

            JSONObject object = new JSONObject();
            try {

                object.put("userid", 33);
                object.put("touser", null);
                object.put("tripid", 38);
                object.put("message", message);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            mSocket.emit("sendmessage", object);
            mSocket.on("getbacksendmessage", getSingleMessages);
        }

    }


    private void addMessage(String from, String to, String message, String date, String receiverImage, String type) {
//        mMessages.add(new ChatModel(Integer.parseInt(from), Integer.parseInt(to), message, date, receiverImage, type));
//        chatAdapter.notifyItemInserted(mMessages.size() - 1);
//        scrollToBottom();
    }


    private void scrollToBottom() {
        rvMessageList.scrollToPosition(allUserChatAdapter.getItemCount() - 1);
    }

    @OnClick({R.id.etChat})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnChat:


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


            mSocket.emit("getusers", jsonObjectGetAllUsers);
            mSocket.on("allusers", getAllUsers);

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


    protected Emitter.Listener getAllUsers = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            chatAllUserDataModels.clear();
            ChatAllUserDataModel chatAllUserDataModel = new ChatAllUserDataModel();
            if (mSocket.connected()) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        JSONObject jsonObject = (JSONObject) args[0];
                        try {
                            JSONArray jsonArray = jsonObject.getJSONArray("groups");


                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject c = jsonArray.getJSONObject(i);
                                String strTitle = c.getString("group_title");
                                String message = c.getString("latest_message");

//                                JSONArray jsonGroupUsers = jsonObject.getJSONArray("group_users");




                                chatAllUserDataModel.setTitleName(strTitle);
                                chatAllUserDataModel.setMessage(message);

                                chatAllUserDataModels.add(chatAllUserDataModel);
                                allUserChatAdapter.notifyDataSetChanged();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                        Log.d("zma all user", jsonObject + "");


                    }
                });
            }
        }
    };


    protected Emitter.Listener getSingleMessages = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            if (mSocket.connected()) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        JSONObject jsonObject = (JSONObject) args[0];


                        Log.d("zma single user", "" + jsonObject);


                    }
                });
            }
        }
    };


    protected Emitter.Listener getAllMessages = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            if (mSocket.connected()) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        JSONObject jsonArray = (JSONObject) args[0];


                        Log.d("zma all messag", "" + jsonArray);


                    }
                });
            }
        }
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