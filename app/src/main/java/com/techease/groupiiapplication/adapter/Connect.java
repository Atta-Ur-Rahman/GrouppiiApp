package com.techease.groupiiapplication.adapter;

import java.util.ArrayList;
import java.util.List;

public class Connect {
    private static boolean myBoolean;
    private static List<ConnectionBooleanChangedListener> listeners = new ArrayList<ConnectionBooleanChangedListener>();

    public static boolean getMyBoolean() { return myBoolean; }

    public static void setMyBoolean(boolean value) {
        myBoolean = value;

        for (ConnectionBooleanChangedListener l : listeners) {
            l.OnMyBooleanChanged();
        }
    }

    public static void addGalleryPhotoListener(ConnectionBooleanChangedListener l) {
        listeners.add(l);
    }
}