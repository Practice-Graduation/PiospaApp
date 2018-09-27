package com.ptit.baobang.piospaapp.data.network.api;

public class ApiUtils {
    private ApiUtils() {}
    private static final String BASE_URL = "http://192.168.43.84:8080/";

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
