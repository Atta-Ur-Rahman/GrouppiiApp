package com.techease.groupiiapplication.interfaceClass.refreshChat;

import com.techease.groupiiapplication.adapter.gallery.ConnectionBooleanChangedListener;

import java.util.ArrayList;
import java.util.List;

public class ConnectChatResfresh {
    private static boolean myBoolean;
    private static boolean myBooleanClickPayment;
    private static List<ConnectionBooleanChangedListener> listeners = new ArrayList<ConnectionBooleanChangedListener>();

    public static boolean getMyBoolean() {
        return myBoolean;
    }

    public static boolean getMyBooleanClickPayment() {
        return myBooleanClickPayment;
    }

    public static void setMyBoolean(boolean value) {
        myBoolean = value;

        for (ConnectionBooleanChangedListener l : listeners) {
            l.OnMyBooleanChanged();
        }
    }

    public static void setMyBooleanClickPayment(boolean value) {
        myBooleanClickPayment = value;
        for (ConnectionBooleanChangedListener l : listeners) {
            l.OnMyBooleanChanged();
        }
    }

    public static void addUserToChat(ConnectionBooleanChangedListener l) {
        listeners.add(l);
    }

}