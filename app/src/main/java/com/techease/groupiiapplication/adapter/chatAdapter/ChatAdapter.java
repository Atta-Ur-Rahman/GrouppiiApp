package com.techease.groupiiapplication.adapter.chatAdapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.chats.chat.ChatModel;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.DateUtills;
import com.techease.groupiiapplication.utils.EmojiEncoder;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private List<ChatModel> mMessages;
    private Context context;
    private Drawable mDeliveredIcon;
    private Drawable mSeenIcon;
    private String strTripId;

    public ChatAdapter(Context context, List<ChatModel> messages, String strTripId) {
        this.context = context;
        mMessages = messages;
        this.strTripId = strTripId;
    }

    @Override
    @NotNull
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        int layout;

        if (viewType == AppRepository.mUserID(context)) {
            layout = R.layout.custom_sender_layout;
        } else {
            layout = R.layout.custom_reciever_message;
        }

        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        ChatModel message = mMessages.get(position);
        viewHolder.setDate(String.valueOf(message.getDate()));
        viewHolder.setMessage(EmojiEncoder.decodeEmoji(message.messages), "0");
        viewHolder.tvDate.setText(message.getDate());


        Log.d("zma message sent", DateUtills.getChatDateFormate(message.getDate()));


        if (message.getDate() != null) {
            viewHolder.setDate(DateUtills.getChatDateFormate(message.getDate()));
        }
        if (message.getFromuserName() != null) {
            viewHolder.tvName.setText(message.getFromuserName());
        }


        Log.d("zma message sent", message.getIsSent());
        Log.d("zma message read", message.getIsRead());
        Log.d("zma message rec", "" + message.getRecieverID());
        Log.d("zma message user", "" + AppRepository.mUserID(context));

        if (AppRepository.mUserID(context) == message.recieverID) {

            viewHolder.ivSentMessage.setVisibility(View.VISIBLE);
            if (message.isRead.equals("1")) {
                viewHolder.ivSeenMessage.setVisibility(View.VISIBLE);
                viewHolder.ivSentAndReceiveMessage.setVisibility(View.GONE);
                viewHolder.ivSentMessage.setVisibility(View.GONE);


            } else if (message.isSent.equals("1")) {
                viewHolder.ivSeenMessage.setVisibility(View.GONE);
                viewHolder.ivSentAndReceiveMessage.setVisibility(View.VISIBLE);
                viewHolder.ivSentMessage.setVisibility(View.GONE);

            } else {
                viewHolder.ivSeenMessage.setVisibility(View.GONE);
                viewHolder.ivSentAndReceiveMessage.setVisibility(View.GONE);
                viewHolder.ivSentMessage.setVisibility(View.GONE);


            }


            Log.d("zma message", "read sho");


        }


    }


    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mMessages != null) {
            ChatModel object = mMessages.get(position);
            if (object != null) {
                return object.getRecieverID();
            }
        }
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvMessageView, tvDate, tvName;
        private ImageView ivSentAndReceiveMessage, ivSeenMessage, ivSentMessage;
        private ProgressBar progressBar;
        private FrameLayout messageLayout;

        private ViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvMessageView = itemView.findViewById(R.id.tvMessageBody);
            tvName = itemView.findViewById(R.id.tvName);
            ivSeenMessage = itemView.findViewById(R.id.ivSeenMessage);
            ivSentMessage = itemView.findViewById(R.id.ivSentMessages);
            ivSentAndReceiveMessage = itemView.findViewById(R.id.ivSentAndReceiveMessage);


//            ivSender = itemView.findViewById(R.id.iv_sender);
//            ivMessage = itemView.findViewById(R.id.message_image);
//            progressBar = itemView.findViewById(R.id.progress);
//            messageLayout = itemView.findViewById(R.id.message_layout);
        }

        public void setDate(String messageDate) {
            if (null == tvDate) return;

            String split[] = messageDate.split("T");
            String dateString = split[0];
            if (dateString != null && !dateString.isEmpty()) {
                tvDate.setText(dateString);
            }

        }

        private void setMessage(String message, String type) {
            if (null == tvMessageView) return;

            try {
                if (type.equals("image")) {
                    tvMessageView.setVisibility(View.GONE);
                    messageLayout.setVisibility(View.VISIBLE);

                    Glide.with(context)
                            .load(message)
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }
                            })
                            .into(ivSeenMessage);
                } else {
//                    mMessageView.setVisibility(View.VISIBLE);
//                    messageLayout.setVisibility(View.GONE);
                    tvMessageView.setText(message);
                }
            } catch (Exception e) {
                Log.d("error", "" + e.getMessage());
            }

        }

        public void setImage(String image) {
//            if (null == ivSender) return;
//            Glide.with(context)
//                    .load(image)
//                    .placeholder(R.color.grey)
//                    .into(ivSender);
        }

    }
}
