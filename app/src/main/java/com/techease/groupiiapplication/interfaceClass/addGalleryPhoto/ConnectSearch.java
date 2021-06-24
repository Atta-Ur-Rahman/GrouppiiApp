package com.techease.groupiiapplication.interfaceClass.addGalleryPhoto;

import java.util.ArrayList;
import java.util.List;

public class ConnectSearch {
    private static String mySearch;
    private static List<ConnectionSearchChangedListener> listeners = new ArrayList<ConnectionSearchChangedListener>();

    public static String getMySearch() {
        return mySearch;
    }

    public static void setMySearch(String value) {
        mySearch = value;
        for (ConnectionSearchChangedListener l : listeners) {
            l.OnMySearching();
        }
    }

    public static void addGalleryPhotoListener(ConnectionSearchChangedListener l) {
        listeners.add(l);
    }
}