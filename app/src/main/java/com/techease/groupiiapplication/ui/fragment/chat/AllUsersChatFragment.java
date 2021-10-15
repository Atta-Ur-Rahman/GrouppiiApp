package com.techease.groupiiapplication.ui.fragment.chat;

import static me.everything.android.ui.overscroll.IOverScrollState.STATE_DRAG_START_SIDE;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.chatAdapter.AllUserChatAdapter;
import com.techease.groupiiapplication.adapter.gallery.ConnectionBooleanChangedListener;
import com.techease.groupiiapplication.dataModel.chats.chat.ChatAllUserDataModel;
import com.techease.groupiiapplication.interfaceClass.refreshChat.ConnectChatResfresh;
import com.techease.groupiiapplication.socket.ChatApplication;
import com.techease.groupiiapplication.utils.AppRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import me.everything.android.ui.overscroll.IOverScrollDecor;
import me.everything.android.ui.overscroll.IOverScrollStateListener;
import me.everything.android.ui.overscroll.IOverScrollUpdateListener;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import me.everything.android.ui.overscroll.VerticalOverScrollBounceEffectDecorator;
import me.everything.android.ui.overscroll.adapters.RecyclerViewOverScrollDecorAdapter;

public class AllUsersChatFragment extends Fragment implements View.OnClickListener {

    private static final int STATE_DRAG_END_SIDE = 1;
    @BindView(R.id.etChat)
    EditText etChat;
    @BindView(R.id.searchViewChat)
    SearchView searchView;
    @BindView(R.id.rvMessagelist)
    RecyclerView rvMessageList;
    @BindView(R.id.ivEdit)
    ImageView ivEdit;
    private IOverScrollDecor mVertOverScrollEffect;
    private List<ChatAllUserDataModel> chatAllUserDataModels = new ArrayList<>();
    public AllUserChatAdapter allUserChatAdapter;

    boolean isConnected;
    String strUserID = "", strMessage, strGroupType = "", strDateAndTime = "", strTitleName = "", strGroupChatPicture = "", strTripID = "", strToUserId = "";
    private Socket mSocket;
    JSONObject jsonObjectGetAllUsers = new JSONObject();
    LinearLayoutManager linearLayoutManager;
    public static boolean aBooleanRefreshSocket = false;
    int baseUserID;
    private int STATE_BOUNCE_BACK;

    @BindView(R.id.tvNoUserFound)
    TextView tvNoUserFound;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_all_users, container, false);
        ButterKnife.bind(this, view);

        baseUserID = AppRepository.mUserID(getActivity());

        init();
        socketConnectivity();
        getAllUserFun();
        userSearch();

        ConnectChatResfresh.addUserToChat(new ConnectionBooleanChangedListener() {
            @Override
            public void OnMyBooleanChanged() {

                getAllUserFun();
                if (aBooleanRefreshSocket) {
                    aBooleanRefreshSocket = false;
                    scrollTotop();
                }
            }
        });


        return view;
    }

    private void userSearch() {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryString) {
                allUserChatAdapter.getFilter().filter(queryString);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String queryString) {
                allUserChatAdapter.getFilter().filter(queryString);
                return false;
            }
        });
    }


    private void socketConnectivity() {
        ChatApplication app = new ChatApplication();
        mSocket = app.getSocket();
        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on("allusers_" + AppRepository.mUserID(getActivity()), getAllUsers);
        mSocket.connect();
    }

    private void init() {

        linearLayoutManager = new LinearLayoutManager(getActivity());
        allUserChatAdapter = new AllUserChatAdapter(getActivity(), chatAllUserDataModels);
        rvMessageList.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        rvMessageList.setItemAnimator(new DefaultItemAnimator());
        rvMessageList.setAdapter(allUserChatAdapter);

        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Search here...");

/*
        // Set-up of recycler-view's native item swiping.
        ItemTouchHelper.Callback itemTouchHelperCallback = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(0, ItemTouchHelper.RIGHT);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setTitle("Item swiping is supported!")
                        .setMessage("Recycler-view's native item swiping and the over-scrolling effect can co-exist! But, to get them to work WELL -- please apply the effect using the dedicated helper method!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                allUserChatAdapter.notifyDataSetChanged();
                            }
                        })
                        .create();
                dialog.show();
            }
        };*/

        // Apply over-scroll in 'advanced form' - i.e. create an instance manually.
        mVertOverScrollEffect = new VerticalOverScrollBounceEffectDecorator(new RecyclerViewOverScrollDecorAdapter(rvMessageList));

        // Over-scroll listeners are applied here via the mVertOverScrollEffect explicitly.
        mVertOverScrollEffect.setOverScrollUpdateListener(new IOverScrollUpdateListener() {
            @Override
            public void onOverScrollUpdate(IOverScrollDecor decor, int state, float offset) {
//                mVertScrollMeasure.setText(String.valueOf((int) offset));
            }
        });
        mVertOverScrollEffect.setOverScrollStateListener(new IOverScrollStateListener() {
            private final int mDragColorTop = getResources().getColor(android.R.color.holo_red_light);
            private final int mBounceBackColorTop = getResources().getColor(android.R.color.holo_orange_dark);
            private final int mDragColorBottom = getResources().getColor(android.R.color.holo_purple);
            private final int mBounceBackColorBottom = getResources().getColor(android.R.color.holo_blue_light);
//            private final int mClearColor = mHorizScrollMeasure.getCurrentTextColor();

            @Override
            public void onOverScrollStateChange(IOverScrollDecor decor, int oldState, int newState) {
                if (newState == STATE_DRAG_START_SIDE) {
//                    mVertScrollMeasure.setTextColor(mDragColorTop);
                } else if (newState == STATE_DRAG_END_SIDE) {
//                    mVertScrollMeasure.setTextColor(mDragColorBottom);
                } else if (newState == STATE_BOUNCE_BACK) {
//                    mVertScrollMeasure.setTextColor(oldState == STATE_DRAG_START_SIDE ? mBounceBackColorTop : mBounceBackColorBottom);
                } else {
//                    mVertScrollMeasure.setTextColor(mClearColor);
                }
            }

        });
    }


    private void getAllUserFun() {
        allUserChatAdapter.clearApplications();
        jsonObjectGetAllUsers = new JSONObject();
        try {
            jsonObjectGetAllUsers.put("userid", baseUserID);
            mSocket.emit("getusers", jsonObjectGetAllUsers);
            Log.d("zmajsaonarray", "zma call");

        } catch (JSONException e) {
            e.printStackTrace();
        }


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


    protected Emitter.Listener getAllUsers = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            if (mSocket.connected()) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("zmajsaonarray", "event call");
                        allUserChatAdapter.clearApplications();


                        try {
                            String data = args[0].toString();
                            JSONArray jsonArray = new JSONArray(data);

                            Log.d("zmajsonarray", "Chat" + jsonArray);


                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject c = jsonArray.getJSONObject(i);
                                strGroupType = c.getString("group_type");
                                strTripID = c.getString("tripid");
                                strToUserId = c.getString("touser");

                                if (strGroupType.equals("group")) {
                                    strUserID = "";
                                    strDateAndTime = "null";
                                    strTitleName = c.getString("group_title");
                                    JSONArray jsonGroupUsers = c.getJSONArray("group_users");
                                    for (int ii = 0; ii < jsonGroupUsers.length(); ii++) {
                                        JSONObject groupUser = jsonGroupUsers.getJSONObject(ii);
                                        strGroupChatPicture = groupUser.getString("picture");
                                    }
                                } else {
                                    strTitleName = c.getString("name");
                                    strGroupChatPicture = c.getString("picture");
                                    strUserID = c.getString("userid");
                                    strDateAndTime = c.getString("created_at");
                                }

                                //check condition if user id and user
                                if (!strTitleName.equals("")) {
                                    if (strGroupType.equals("group")) {
                                        if (strToUserId.equals(String.valueOf(AppRepository.mUserID(getActivity())))) {
                                            addUserToList(strTitleName, "1223", "text", strGroupType, strTripID, strToUserId, strDateAndTime, "modfa", strGroupChatPicture);
                                        }
                                    } else {
                                        if (strUserID.equals(String.valueOf(AppRepository.mUserID(getActivity())))) {
                                            addUserToList(strTitleName, "1223", "text", strGroupType, strTripID, strToUserId, strDateAndTime, "modfa", strGroupChatPicture);
                                        }
                                    }
                                }
                            }

                        } catch (
                                JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    };


    private void addUserToList(String titleName, String chatTime, String chatType, String message, String tripId, String toUser, String createdAt, String modifiedAt, String picture) {

        chatAllUserDataModels.add(new ChatAllUserDataModel(titleName, chatTime, chatType, message, tripId, toUser, createdAt, modifiedAt, picture));
        allUserChatAdapter.notifyItemInserted(chatAllUserDataModels.size() - 1);
        allUserChatAdapter.notifyDataSetChanged();


        if (chatAllUserDataModels.size() == 0) {
            tvNoUserFound.setVisibility(View.VISIBLE);
        } else {
            tvNoUserFound.setVisibility(View.GONE);
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (aBooleanRefreshSocket) {
            aBooleanRefreshSocket = false;
            getAllUserFun();
        }
    }


    private void scrollTotop() {
        rvMessageList.smoothScrollToPosition(0);
    }

    @OnClick({R.id.ivEdit})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivEdit:
                getAllUserFun();
                break;
        }
    }
}












































































/*
























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











 private Socket mSocket;

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


//      dialog.dismiss();
                     /*   JSONObject jsonObject = (JSONObject) args[0];
                        Log.d("zma all user jsonObject", jsonObject + "");

                        try {
                            JSONArray jsonArray = jsonObject.getJSONArray("groups");

                            Log.d("zma all user jsonArray", jsonArray + "");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject c = jsonArray.getJSONObject(i);

                                String title = c.getString("group_title");
                                String strChatType = c.getString("chat_type");
                                String tripId = c.getString("tripid");
                                toUserId=c.getString("touser");
//                                toUserId = c.getString("touser");
//                                strGroupChatPicture = c.getString("picture");
////                               String fromUserId=c.getString("fromuser");
                                JSONArray jsonGroupUsers = c.getJSONArray("group_users");
                                for (int ii = 0; ii < jsonGroupUsers.length(); ii++) {
                                    JSONObject groupUser = jsonGroupUsers.getJSONObject(ii);
//                                    toUserId = groupUser.getString("touser");
                                    strGroupChatPicture = groupUser.getString("picture");
                                }

                                Log.d("zma chat type", strChatType);

                                //check condition if user id and user
                                if (toUserId.equals(String.valueOf(AppRepository.mUserID(getActivity())))) {
                                    addUserToList(title, "1223", "text", strChatType, tripId, toUserId, "date", "modfa", strGroupChatPicture);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
*/

                      /*  try {
                            JSONArray jsonArray = jsonObject.getJSONArray("single");

//                            Log.d("zma single json id", "" + jsonArray);


                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject c = jsonArray.getJSONObject(i);
                                String title = c.getString("name");
                                String message = c.getString("group_type");
                                String tripId = c.getString("tripid");
                                String strId = c.getString("id");
                                String toUserId = c.getString("touser");
                                String userId = c.getString("userid");

//                                Log.d("zma touser single id", userId);


                                String strGroupChatPicture = c.getString("picture");

                                //check condition if user id and user
                                if (userId.equals(String.valueOf(AppRepository.mUserID(getActivity())))) {
                                    addUserToList(title, "1223", "text", message, tripId, toUserId, "date", "modfa", strGroupChatPicture);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/