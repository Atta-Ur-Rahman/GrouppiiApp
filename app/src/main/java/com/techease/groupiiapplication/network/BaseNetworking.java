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

    public static ApiInterface ApiInterfaceForHotel(String token) {
        services = APIClient.getClientForHotel(token).create(ApiInterface.class);
        return services;
    }
}