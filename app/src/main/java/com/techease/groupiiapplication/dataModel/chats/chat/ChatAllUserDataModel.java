package com.techease.groupiiapplication.dataModel.chats.chat;

public class ChatAllUserDataModel {
    String titleName, chatTime, chatType, message, tripId, toUser, createdAt, modifiedAt, picture, currentChatUserId;

    public ChatAllUserDataModel() {
    }

    public ChatAllUserDataModel(String titleName, String chatTime, String chatType, String message, String tripId, String toUser, String createdAt, String modifiedAt, String picture, String strCurrentChatUserId) {
        this.titleName = titleName;
        this.chatTime = chatTime;
        this.chatType = chatType;
        this.message = message;
        this.tripId = tripId;
        this.toUser = toUser;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.picture = picture;
        this.currentChatUserId = strCurrentChatUserId;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getChatTime() {
        return chatTime;
    }

    public void setChatTime(String chatTime) {
        this.chatTime = chatTime;
    }

    public String getChatType() {
        return chatType;
    }

    public void setChatType(String chatType) {
        this.chatType = chatType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCurrentChatUserId() {
        return currentChatUserId;
    }

    public void setCurrentChatUserId(String currentChatUserId) {
        this.currentChatUserId = currentChatUserId;
    }
}
