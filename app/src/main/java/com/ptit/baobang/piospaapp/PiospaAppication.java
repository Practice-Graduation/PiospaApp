package com.ptit.baobang.piospaapp;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.crashlytics.android.Crashlytics;
import com.ptit.baobang.piospaapp.utils.SharedPreferenceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class PiospaAppication extends Application {
    private static PiospaAppication instance = null;
    private String FOLDER = "/piospa";
    private String LOG = "/log";
    private String LOGFILE = "logcat";
    private String EXTERNAL = ".txt";
    private String LOGCAT_C = "logcat -c";
    private String LOGCAT_F = "logcat -d -f ";
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
        Fabric.with(this, new Crashlytics());
        setInstance(this);
        //init realm
        initRealm();
    }

    private void initRealm() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("piospa.realm").build();
        Realm.setDefaultConfiguration(config);

        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            if (!SharedPreferenceUtils.getFirstInit(this)) {
                realm.close();
                Realm.deleteRealm(config);
                Realm.setDefaultConfiguration(config);
                SharedPreferenceUtils.clearAll(this);
                SharedPreferenceUtils.saveFirstInit(this);
            }


        } catch (Exception e) {
            if (realm != null) {
                realm.close();
            }
            Realm.deleteRealm(config);
            Realm.setDefaultConfiguration(config);
            SharedPreferenceUtils.clearAll(this);
            e.printStackTrace();
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

}
