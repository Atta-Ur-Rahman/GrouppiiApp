package com.techease.groupiiapplication.network;

public class BaseNetworking {
    public static ApiInterface services;

    public static ApiInterface ApiInterface() {
        services = APIClient.getClient().create(ApiInterface.class);
        return services;
    }
    public static ApiInterface ApiInterface(String token) {
        services = APIClient.getClient(token).create(ApiInterface.class);
        return services;
    }
    public static ApiInterface ApiInterfaceForRegistration(String token) {
        services = APIClient.getClientForRegisteration(token).create(ApiInterface.class);
        return services;
    }

    public static ApiInterface ApiInterfaceForHotel() {
        services = APIClient.getClientForHotel().create(ApiInterface.class);
        return services;
    }
}