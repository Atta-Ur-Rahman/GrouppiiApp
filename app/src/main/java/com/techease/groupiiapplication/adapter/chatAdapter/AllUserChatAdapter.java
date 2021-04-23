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

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.chat.ChatAllUserDataModel;
import com.techease.groupiiapplication.dataModel.tripDetail.Active;
import com.techease.groupiiapplication.ui.activity.ChatsActivity;
import com.techease.groupiiapplication.ui.activity.TripDetailScreenActivity;

import java.util.ArrayList;
import java.util.List;


public class AllUserChatAdapter extends RecyclerView.Adapter<AllUserChatAdapter.MyViewHolder> implements Filterable {
    private List<ChatAllUserDataModel> chatAllUserDataModels;
    private List<ChatAllUserDataModel> chatAllUserDataModelsFilter;

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitleName, tvChatTime, tvChatType, tvMessage;
        ImageView ivChatImage;
        RelativeLayout rlGroupChat;


        public MyViewHolder(View view) {
            super(view);

            tvTitleName = view.findViewById(R.id.tvTitleName);
            tvChatTime = view.findViewById(R.id.tvChatTime);
            tvChatType = view.findViewById(R.id.tvChatType);
            tvMessage = view.findViewById(R.id.tvMessage);
            rlGroupChat = view.findViewById(R.id.rlGroupChat);


        }
    }

    public AllUserChatAdapter(Context context, List<ChatAllUserDataModel> chatAllUserDataModel) {
        this.chatAllUserDataModels = chatAllUserDataModel;
        this.chatAllUserDataModelsFilter=chatAllUserDataModel;
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


//        Log.d("zma tilte",chatAllUserDataModel.getTitleName());

        holder.rlGroupChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ChatsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title_name", chatAllUserDataModel.getTitleName());
                bundle.putString("tripId", chatAllUserDataModel.getTripId());
                bundle.putString("toUserId", chatAllUserDataModel.getToUser());

                intent.putExtras(bundle);
                context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context).toBundle());
            }
        });


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