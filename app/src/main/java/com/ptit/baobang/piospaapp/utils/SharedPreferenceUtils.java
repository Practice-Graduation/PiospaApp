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
    public static void saveUser(Context context, Customer customer){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor myEditor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(customer);
        myEditor.putString("CUSTOMER", json);
        myEditor.apply();
    }

    public static Customer getUser(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json =  sharedPreferences.getString("CUSTOMER", "");
        return gson.fromJson(json, Customer.class);
    }

    public static int getCount(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(COUNT, 0);
    }

    public static void saveCount(Context context, int count){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor myEditor = sharedPreferences.edit();
        myEditor.putInt(COUNT, count);
        myEditor.apply();
    }

    public static void saveCurrentProcessID(Context context){
        int currentProcessID = android.os.Process.myPid();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor myEditor = sharedPreferences.edit();
        myEditor.putInt(APP_PROCESS_ID, currentProcessID);
        myEditor.apply();
    }

    public static int getProcessID(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(APP_PROCESS_ID, 0);
    }
}
