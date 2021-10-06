package com.techease.groupiiapplication.interfaceClass.participantsCostsClickInterface;

import java.util.ArrayList;
import java.util.List;

public class ConnectParticipantCostsClick {
    private static boolean myBooleanClickPayment;
    private static List<ParticipantCostsClickChangedListener> listeners = new ArrayList<ParticipantCostsClickChangedListener>();

    public static boolean getMyBoolean() { return myBooleanClickPayment; }

    public static boolean getMyBooleanClickPayment() { return myBooleanClickPayment; }

    public static void setMyBoolean(boolean value) {
        myBooleanClickPayment = value;

        for (ParticipantCostsClickChangedListener l : listeners) {
            l.OnMyBooleanClickChanged();
        }
    }

    public static void setMyBooleanClickPayment(boolean value) {
        myBooleanClickPayment = value;
        for (ParticipantCostsClickChangedListener l : listeners) {
            l.OnMyBooleanClickChanged();
        }
    }

    public static void addClickListener(ParticipantCostsClickChangedListener l) {
        listeners.add(l);
    }

}