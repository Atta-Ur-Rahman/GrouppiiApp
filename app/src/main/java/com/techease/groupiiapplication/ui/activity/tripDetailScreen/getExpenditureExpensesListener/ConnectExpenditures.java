package com.techease.groupiiapplication.ui.activity.tripDetailScreen.getExpenditureExpensesListener;

import java.util.ArrayList;
import java.util.List;

public class ConnectExpenditures {
    private static boolean myBooleanClickPayment;
    private static List<ConnectionBooleanExpendituresListener> listeners = new ArrayList<ConnectionBooleanExpendituresListener>();

    public static boolean getMyBoolean() { return myBooleanClickPayment; }

    public static boolean getMyBooleanClickPayment() { return myBooleanClickPayment; }

    public static void setMyBooleanLister(boolean value) {
        myBooleanClickPayment = value;

        for (ConnectionBooleanExpendituresListener l : listeners) {
            l.OnMyBooleanListener();
        }
    }

    public static void setMyBooleanListener(boolean value) {
        myBooleanClickPayment = value;
        for (ConnectionBooleanExpendituresListener l : listeners) {
            l.OnMyBooleanListener();
        }
    }

    public static void addClickListener(ConnectionBooleanExpendituresListener l) {
        listeners.add(l);
    }

}