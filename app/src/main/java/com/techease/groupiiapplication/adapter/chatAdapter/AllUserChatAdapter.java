package com.techease.groupiiapplication.adapter.chatAdapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.chats.chat.ChatAllUserDataModel;
import com.techease.groupiiapplication.ui.activity.ChatsActivity;
import com.techease.groupiiapplication.utils.Constants;
import com.techease.groupiiapplication.utils.DateUtills;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class AllUserChatAdapter extends RecyclerView.Adapter<AllUserChatAdapter.MyViewHolder> implements Filterable {
    private List<ChatAllUserDataModel> chatAllUserDataModels;
    private List<ChatAllUserDataModel> chatAllUserDataModelsFilter;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitleName, tvChatTime, tvChatType, tvMessage;
        ImageView ivGroupChatImage;
        RelativeLayout rlGroupChat;

        public MyViewHolder(View view) {
            super(view);

            tvTitleName = view.findViewById(R.id.tvTitleName);
            tvChatTime = view.findViewById(R.id.tvChatTime);
            tvChatType = view.findViewById(R.id.tvChatType);
            tvMessage = view.findViewById(R.id.tvMessage);
            rlGroupChat = view.findViewById(R.id.rlGroupChat);
            ivGroupChatImage = view.findViewById(R.id.ivGroupChatImage);
        }
    }

    public AllUserChatAdapter(Context context, List<ChatAllUserDataModel> chatAllUserDataModel) {
        this.chatAllUserDataModels = chatAllUserDataModel;
        this.chatAllUserDataModelsFilter = chatAllUserDataModel;
        this.context = context;

    }

    @Override
    public int getItemCount() {
        return chatAllUserDataModelsFilter.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_user_chat_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ChatAllUserDataModel chatAllUserDataModel = chatAllUserDataModelsFilter.get(position);
        holder.tvTitleName.setText(chatAllUserDataModel.getTitleName());
        holder.tvMessage.setText(chatAllUserDataModel.getMessage());


        if (!chatAllUserDataModel.getCreatedAt().equals("null")) {

//            holder.tvChatTime.setText(chatAllUserDataModel.getCreatedAt());

            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:m:ss.SSS'Z'");
            try {
                Date d = f.parse(chatAllUserDataModel.getCreatedAt());
                long milliseconds = d.getTime();
                holder.tvChatTime.setText(DateUtills.getTimeAgo(milliseconds));

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        Glide.with(context).load(chatAllUserDataModel.getPicture()).placeholder(R.drawable.user).into(holder.ivGroupChatImage);

//        if (chatAllUserDataModel.getChatType().equals("group")) {
//            Glide.with(context).load(chatAllUserDataModel.getPicture()).placeholder(R.drawable.group_image).into(holder.ivGroupChatImage);
//        }
//        if (chatAllUserDataModel.getMessage().equals("user")) {
//            Glide.with(context).load(chatAllUserDataModel.getPicture()).placeholder(R.drawable.user).into(holder.ivGroupChatImage);
//        }
        holder.rlGroupChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ChatsActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("title_name", chatAllUserDataModel.getTitleName());
                bundle.putString("tripId", chatAllUserDataModel.getTripId());
                bundle.putString("toUserId", chatAllUserDataModel.getToUser());
                bundle.putString("type", chatAllUserDataModel.getMessage());
                bundle.putString("picture", chatAllUserDataModel.getPicture());

                Log.d("zmamessagetype", chatAllUserDataModel.getToUser());

                Constants.currentUserChatId = chatAllUserDataModel.getToUser();

                intent.putExtras(bundle);
                context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context).toBundle());
            }
        });


    }


    public void clearApplications() {
        int size = this.chatAllUserDataModels.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                chatAllUserDataModelsFilter.remove(0);
            }
            this.notifyItemRangeRemoved(0, size);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    chatAllUserDataModelsFilter = chatAllUserDataModels;
                } else {
                    List<ChatAllUserDataModel> filteredList = new ArrayList<>();
                    for (ChatAllUserDataModel row : chatAllUserDataModels) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTitleName().toLowerCase().contains(charString.toLowerCase()) || row.getTitleName().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    chatAllUserDataModelsFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = chatAllUserDataModelsFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                chatAllUserDataModelsFilter = (ArrayList<ChatAllUserDataModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}