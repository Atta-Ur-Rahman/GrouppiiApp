package com.techease.groupiiapplication.hotel.api;

public class ApiCalls {

    private static final String FIREBASE_KEY_ORDERS_DEV = "TestOrders";
    private static final String FIREBASE_KEY_RIDERS_DEV = "TestRiders";

    private static final String FIREBASE_KEY_RIDERS_LIVE = "Riders";
    private static final String FIREBASE_KEY_ORDERS_LIVE = "Orders";

    //        LIVE
    //        change splunk to release in app
    //        Staging

    private static final String BASE_URL_AZURE = "http://darewro.southeastasia.cloudapp.azure.com/api/";
    private static final String BASE_URL_IMAGE_LIVE = "http://darewro.southeastasia.cloudapp.azure.com/";
    public static String BASE_URL = BASE_URL_AZURE;
    public static String BASE_URL_IMAGE = BASE_URL_IMAGE_LIVE;
    public static final String FIREBASE_KEY_RIDERS = FIREBASE_KEY_RIDERS_LIVE;
    public static final String FIREBASE_KEY_ORDERS = FIREBASE_KEY_ORDERS_LIVE;

    //        TEST

//    private static final String BASE_URL_STAGING_VM = "http://52.148.84.55/api/";
//    private static final String BASE_URL_IMAGE_STAGING = "http://10.1.254.31:19000/";//this hasn't been provided by ali bhai.

//    public static  String BASE_URL = BASE_URL_STAGING_VM;
//    public static  String BASE_URL_IMAGE= BASE_URL_IMAGE_STAGING;
//    public static final String FIREBASE_KEY_ORDERS = FIREBASE_KEY_ORDERS_DEV;
//    public static final String FIREBASE_KEY_RIDERS = FIREBASE_KEY_RIDERS_DEV;

    //        DEV
    private static final String BASE_URL_DEV_VM = "http://10.10.201.21:19000/api/";//Ahmed Zeb Static VPN Laptop IP
    private static final String BASE_URL_IMAGE_DEV = "http://10.10.201.21:19000/";//Ahmed Zeb Static VPN Laptop IP
//    private static final String BASE_URL_DEV_VM = "http://192.168.1.100:19000/api/";//Ahmed Zeb Static Laptop IP
//    private static final String BASE_URL_IMAGE_DEV = "http://192.168.1.100:19000/";//Ahmed Zeb Static Laptop IP
//    private static final String BASE_URL_DEV_VM = "http://10.11.10.179:19000/api/";
//    private static final String BASE_URL_IMAGE_DEV = "http://10.11.10.179:19000/";


    /*DEV*/
    public static final String BASE_URL_DEV_AHMEDZEB_NGROK = "http://4c4ba361fc13.ngrok.io/api/";//Ahmed Zeb Static VPN Laptop IP
    private static final String BASE_URL_IMAGE_DEV_AHMEDZEB_NGROK = "http://4c4ba361fc13.ngrok.io/";//Ahmed Zeb Static VPN Laptop IP
//    public static String BASE_URL = BASE_URL_DEV_AHMEDZEB_NGROK;
//    public static String BASE_URL_IMAGE = BASE_URL_IMAGE_DEV_AHMEDZEB_NGROK;
//    public static final String FIREBASE_KEY_ORDERS = FIREBASE_KEY_ORDERS_DEV;
//    public static final String FIREBASE_KEY_RIDERS = FIREBASE_KEY_RIDERS_DEV;


    private static final String BASE_URL_DEV_VM_AHMAD_ZEB = "http://10.11.203.54:19000/api/";
//    private static final String BASE_URL = BASE_URL_DEV_VM_AHMAD_ZEB;
//    private static final String BASE_URL = BASE_URL_DEV_VM;
//    private static final String BASE_URL_IMAGE = BASE_URL_IMAGE_DEV;
//    public static final String FIREBASE_KEY_ORDERS = FIREBASE_KEY_ORDERS_DEV;
//    public static final String FIREBASE_KEY_RIDERS = FIREBASE_KEY_RIDERS_DEV;

    //change splunk to testing in app
    private static final String GET_TOKEN = "TOKEN";
    private static final String GET_PARTNERS = "partner/getpartners";
    private static final String GET_PRODUCTS = "Product/GetPartnerMenusAndProducts";
    private static final String POST_ORDER = "Composer/CreateNewOrder";

    private static final String DELIVERY_FEE_TIME = "Composer/GetEstimatedDeliveryFeeAndTime";
    //    private static final String DELIVERY_FEE_TIME = "Order/GetEstimatedDeliveryFeeAndTime";
    private static final String PAYMENT_METHODS = "Finance/GetPaymentMethods";
    private static final String ORDER_LISTING = "Composer/GetUserOrders";
    private static final String ORDER_DETAILS = "Composer/GetOrderDetails";
    private static final String SIGNUP = "User/CustomerSignUp";
    private static final String UPDATE_USER_PROFILE = "User/UpdateCustomerProfile";
    private static final String UPDATE_DEVICE_TOKEN = "User/UpdateDevice";
    private static final String GET_INVOICE = "Finance/GetInvoiceByOrderID";
    private static final String RATE_API = "Rating/RateEntity";
    private static final String VARIANT = "Product/GetProductWTRAttributes";
    private static final String FILE_UPLOAD = "user/SaveChatFile";


    public static String getVARIANT() {
        return BASE_URL + VARIANT;
    }


    public static String getUpdateDeviceToken() {
        return BASE_URL + UPDATE_DEVICE_TOKEN;
    }

    public static String getUpdateUserProfile() {
        return BASE_URL + UPDATE_USER_PROFILE;
    }

    public static String getDeliveryFeeTime() {
        return BASE_URL + DELIVERY_FEE_TIME;
    }

    public static String getGetToken() {
        return BASE_URL + GET_TOKEN;
    }

    public static String getPartners() {
        return BASE_URL + GET_PARTNERS;
    }

    public static String getProducts() {
        return BASE_URL + GET_PRODUCTS;
    }

    public static String getImageUrl(String url) {
        return BASE_URL_IMAGE + url;
    }

    public static String getPostOrder() {
        return BASE_URL + POST_ORDER;
    }

    public static String getPaymentMethods() {
        return BASE_URL + PAYMENT_METHODS;
    }

    public static String getOrderListing() {
        return BASE_URL + ORDER_LISTING;
    }

    public static String getOrderDetails() {
        return BASE_URL + ORDER_DETAILS;
    }

    public static String getSignUp() {
        return BASE_URL + SIGNUP;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static String getInvoice() {
        return BASE_URL + GET_INVOICE;
    }

    public static String rateApi() {
        return BASE_URL + RATE_API;
    }


    public static String getFileUpload() {
        return BASE_URL + FILE_UPLOAD;
    }
}
