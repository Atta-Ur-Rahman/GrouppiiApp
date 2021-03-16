package com.techease.groupiiapplication.dataModel.chat;

public class ChatModel {

    public String messages, date, senderImage, messageType;
    public int senderID, recieverID;

    public ChatModel(int senderID, int recieverID, String messages, String date, String senderImage, String messageType) {
        this.messages = messages;
        this.date = date;
        this.senderID = senderID;
        this.recieverID = recieverID;
        this.senderImage = senderImage;
        this.messageType = messageType;
    }

    public ChatModel() {
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

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    //-------------

}
