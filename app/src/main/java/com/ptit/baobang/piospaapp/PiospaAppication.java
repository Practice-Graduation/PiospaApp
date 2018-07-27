package com.ptit.baobang.piospaapp;

import android.app.Application;
import android.content.Context;

import com.ptit.baobang.piospaapp.utils.SharedPreferenceUtils;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class PiospaAppication extends Application {
    private static PiospaAppication instance = null;
    //private static Context context;
    private static final Object instanceLock = new Object();

    private void setInstance(PiospaAppication application) {
        synchronized (instanceLock) {
            instance = application;
        }
    }

    public static PiospaAppication getInstance() {
        return instance;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
        //init realm
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("piospa.realm").build();
        Realm.setDefaultConfiguration(config);

        try{
            Realm realm = Realm.getDefaultInstance();
            realm.close();
        }catch (Exception e){
            Realm.deleteRealm(config);
            Realm.setDefaultConfiguration(config);
            SharedPreferenceUtils.saveUser(this, null);
            e.printStackTrace();
        }
    }

}
