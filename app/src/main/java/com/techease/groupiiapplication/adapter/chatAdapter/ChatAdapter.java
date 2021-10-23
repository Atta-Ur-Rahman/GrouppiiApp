package com.techease.groupiiapplication.adapter.chatAdapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.chat.VideoPlayerActivity;
import com.techease.groupiiapplication.chat.images.ChatImagePreviewActivity;
import com.techease.groupiiapplication.dataModel.chats.chat.ChatModel;
import com.techease.groupiiapplication.ui.activity.tripDetailScreen.ImagePreviewActivity;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.DateUtills;
import com.techease.groupiiapplication.utils.EmojiEncoder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private List<ChatModel> mMessages;
    private Context context;
    private Drawable mDeliveredIcon;
    private Drawable mSeenIcon;
    private String strTripId;
    public static String imageBase64;


    ArrayList<String> chatImages = new ArrayList<>();

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
        viewHolder.setMessage(message.getMessages(), message.getMessageType());
        viewHolder.tvDate.setText(message.getDate());


//        Log.d("zm")


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


        viewHolder.ivMessageImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String extension = message.getMessages().substring(message.getMessages().lastIndexOf("."));
                Log.d("zmaexitern", extension);

                if (extension.equals(".mp4")) {
                    Intent intent = new Intent(context, VideoPlayerActivity.class);
                    intent.putExtra("file", message.getMessages());
                    intent.putExtra("images", chatImages);
                    context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context).toBundle());

                } else {
                    Intent intent = new Intent(context, ChatImagePreviewActivity.class);
                    intent.putExtra("file", message.getMessages());
                    intent.putExtra("images", chatImages);
                    context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context).toBundle());

                }

            }
        });


        for (int i = 0; i < mMessages.size(); i++) {
            if (mMessages.get(i).getMessageType().equals("image")) {
                chatImages.add(mMessages.get(i).getMessages());
            }
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
        private ImageView ivSentAndReceiveMessage, ivSeenMessage, ivSentMessage, ivMessageImage, ivVideo;
        private ProgressBar progressBar;
        private FrameLayout messageLayout;

        private ViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvMessageView = itemView.findViewById(R.id.tvMessageBody);
            tvName = itemView.findViewById(R.id.tvName);
            ivSeenMessage = itemView.findViewById(R.id.ivSeenMessage);
            ivSentMessage = itemView.findViewById(R.id.ivSentMessages);
            messageLayout = itemView.findViewById(R.id.message_layout);
            ivMessageImage = itemView.findViewById(R.id.message_image);
            progressBar = itemView.findViewById(R.id.progress);
            ivVideo = itemView.findViewById(R.id.video_image);
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
                    Log.d("zmaimageinadapter", message);

                    tvMessageView.setVisibility(View.GONE);
                    messageLayout.setVisibility(View.VISIBLE);
//                    chatImages.add(message);
                    String extension = message.substring(message.lastIndexOf("."));

                    if (extension.equals(".mp4")) {
                        ivVideo.setVisibility(View.VISIBLE);
                    } else {
                        ivVideo.setVisibility(View.GONE);

                    }

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
                            .into(ivMessageImage);


                } else {
                    tvMessageView.setVisibility(View.VISIBLE);
                    messageLayout.setVisibility(View.GONE);
                    tvMessageView.setText(EmojiEncoder.decodeEmoji(message));
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
