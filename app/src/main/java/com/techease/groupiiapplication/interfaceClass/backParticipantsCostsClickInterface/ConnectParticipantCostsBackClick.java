package com.techease.groupiiapplication.interfaceClass.backParticipantsCostsClickInterface;

import java.util.ArrayList;
import java.util.List;

public class ConnectParticipantCostsBackClick {
    private static boolean myBooleanClickPayment;
    private static List<ParticipantCostsBackClickChangedListener> listeners = new ArrayList<ParticipantCostsBackClickChangedListener>();

    public static boolean getMyBoolean() { return myBooleanClickPayment; }

    public static boolean getMyBooleanClickPayment() { return myBooleanClickPayment; }

    public static void setMyBoolean(boolean value) {
        myBooleanClickPayment = value;

        for (ParticipantCostsBackClickChangedListener l : listeners) {
            l.OnMyBooleanClickChanged();
        }
    }

    public static void setMyBooleanClickPayment(boolean value) {
        myBooleanClickPayment = value;
        for (ParticipantCostsBackClickChangedListener l : listeners) {
            l.OnMyBooleanClickChanged();
        }
    }

    public static void addClickListener(ParticipantCostsBackClickChangedListener l) {
        listeners.add(l);
    }

}