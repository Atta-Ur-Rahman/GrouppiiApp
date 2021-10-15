package com.techease.groupiiapplication.dataModel.chats.chat;

public class ChatModel {

    public String fromuserName, messages, date, senderImage, userType, isSent, isRead, messageType;
    public int senderID, recieverID;


    public ChatModel(int senderID, int recieverID, String fromuserName, String messages, String date, String senderImage, String userType, String isSent, String isRead, String messageType) {
        this.fromuserName = fromuserName;
        this.messages = messages;
        this.date = date;
        this.senderImage = senderImage;
        this.userType = userType;
        this.senderID = senderID;
        this.recieverID = recieverID;
        this.isSent = isSent;
        this.isRead = isRead;
        this.messageType = messageType;

    }

    public ChatModel() {
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getIsSent() {
        return isSent;
    }

    public void setIsSent(String isSent) {
        this.isSent = isSent;
    }

    public String getFromuserName() {
        return fromuserName;
    }

    public void setFromuserName(String fromuserName) {
        this.fromuserName = fromuserName;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public int getRecieverID() {
        return recieverID;
    }

    public void setRecieverID(int recieverID) {
        this.recieverID = recieverID;
    }

    public String getSenderImage() {
        return senderImage;
    }

    public void setSenderImage(String senderImage) {
        this.senderImage = senderImage;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    //-------------

}
