package com.ptit.baobang.piospaapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.ptit.baobang.piospaapp.data.model.Customer;

public class SharedPreferenceUtils {

    private static final String CUSTOMER = "CUSTOMER";

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
}
