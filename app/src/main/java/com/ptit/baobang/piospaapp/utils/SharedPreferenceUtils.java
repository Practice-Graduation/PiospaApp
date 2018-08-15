package com.ptit.baobang.piospaapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.ptit.baobang.piospaapp.data.model.Customer;

public class SharedPreferenceUtils {

    private static final String CUSTOMER = "CUSTOMER";
    private static final String COUNT = "COUNT";
    private static final String APP_PROCESS_ID = "APP_PROCESS_ID";
    private static final String IS_UPDATE_ORDER = "IS_UPDATE_ORDER";
    private static final String FIRST_INIT = "FIRST_INIT";
    private static final String IS_LOGIN = "IS_LOGIN";

    public static void clearAll(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor myEditor = sharedPreferences.edit();
        myEditor.clear();
        myEditor.apply();;
    }

    public static void saveUser(Context context, Customer customer) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor myEditor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json;
        if (customer == null) {
            json = "";
        } else {
            json = gson.toJson(customer);
        }
        myEditor.putString(CUSTOMER, json);
        myEditor.apply();
    }

    public static Customer getUser(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(CUSTOMER, "");
        return gson.fromJson(json, Customer.class);
    }

    public static int getCount(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(COUNT, 0);
    }

    public static void saveCount(Context context, int count) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor myEditor = sharedPreferences.edit();
        myEditor.putInt(COUNT, count);
        myEditor.apply();
    }

    public static void saveCurrentProcessID(Context context) {
        int currentProcessID = android.os.Process.myPid();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor myEditor = sharedPreferences.edit();
        myEditor.putInt(APP_PROCESS_ID, currentProcessID);
        myEditor.apply();
    }

    public static int getProcessID(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(APP_PROCESS_ID, 0);
    }

    public static void saveIsUpdateOrder(Context context, boolean isUpdate){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor myEditor = sharedPreferences.edit();
        myEditor.putBoolean(IS_UPDATE_ORDER, isUpdate);
        myEditor.apply();
    }

    public static boolean getIsUpdateOrder(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(IS_UPDATE_ORDER, false);
    }

    public static void saveFirstInit(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor myEditor = sharedPreferences.edit();
        myEditor.putBoolean(FIRST_INIT, true);
        myEditor.apply();
    }

    public static boolean getFirstInit(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(FIRST_INIT, false);
    }

    public static void saveIsLogin(Context context, boolean isLogin){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor myEditor = sharedPreferences.edit();
        myEditor.putBoolean(IS_LOGIN, isLogin);
        myEditor.apply();
    }

    public static boolean getIsLogin(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }
}
