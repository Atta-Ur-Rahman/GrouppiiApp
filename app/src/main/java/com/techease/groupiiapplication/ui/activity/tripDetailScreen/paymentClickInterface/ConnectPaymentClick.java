package com.techease.groupiiapplication.ui.activity.tripDetailScreen.paymentClickInterface;

import java.util.ArrayList;
import java.util.List;

public class ConnectPaymentClick {
    private static boolean myBooleanClickPayment;
    private static List<ConnectionBooleanClickChangedListener> listeners = new ArrayList<ConnectionBooleanClickChangedListener>();

    public static boolean getMyBoolean() { return myBooleanClickPayment; }

    public static boolean getMyBooleanClickPayment() { return myBooleanClickPayment; }

    public static void setMyBoolean(boolean value) {
        myBooleanClickPayment = value;

        for (ConnectionBooleanClickChangedListener l : listeners) {
            l.OnMyBooleanClickChanged();
        }
    }

    public static void setMyBooleanClickPayment(boolean value) {
        myBooleanClickPayment = value;
        for (ConnectionBooleanClickChangedListener l : listeners) {
            l.OnMyBooleanClickChanged();
        }
    }

    public static void addClickListener(ConnectionBooleanClickChangedListener l) {
        listeners.add(l);
    }

}