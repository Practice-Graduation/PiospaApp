package com.ptit.baobang.piospaapp.services;

import android.content.Context;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.ptit.baobang.piospaapp.R;

public class FCMUtils {
    public static void subscribeTopicFCM(Context context, String account) {
        FirebaseMessaging.getInstance().subscribeToTopic(account)
                .addOnCompleteListener(task -> {
                    String msg = context.getString(R.string.msg_subscribed);
                    if (!task.isSuccessful()) {
                        msg = context.getString(R.string.msg_subscribe_failed);
                    }
                    Log.d("LoginPresenter", msg);
                });

    }

    public static void unsubscribeTopicFCM(Context context, String account) {

       try{
           FirebaseMessaging.getInstance().unsubscribeFromTopic(account).addOnCompleteListener(task -> {
               String msg = context.getString(R.string.msg_unsubscribed);
               if (!task.isSuccessful()) {
                   msg = context.getString(R.string.msg_unsubscribe_failed);
               }
               Log.d("LoginPresenter", msg);
           });
       }catch (Exception e){
            e.printStackTrace();
       }
    }
}
