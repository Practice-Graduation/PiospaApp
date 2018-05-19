package com.ptit.baobang.piospaapp.data.network.api;

public class ApiUtils {
    private ApiUtils() {}
    private static final String BASE_URL = "https://piospa.herokuapp.com/";

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
