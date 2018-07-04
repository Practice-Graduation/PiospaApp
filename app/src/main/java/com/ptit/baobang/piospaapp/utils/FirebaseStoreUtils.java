package com.ptit.baobang.piospaapp.utils;

import android.graphics.Bitmap;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ptit.baobang.piospaapp.ui.listener.CallBackUploadFireBase;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class FirebaseStoreUtils {
    private static String FIREBASE_STORE_PATH = "avatar/";

    public static void uploadFile(Bitmap bitmap, CallBackUploadFireBase callBack) {
       try {
           Calendar calendar = Calendar.getInstance();
           FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
           StorageReference mStorageReference = firebaseStorage
                   .getReference(FIREBASE_STORE_PATH + calendar.getTimeInMillis() + ".png");

           ByteArrayOutputStream baos = new ByteArrayOutputStream();
           bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
           byte[] data = baos.toByteArray();


           UploadTask uploadTask = mStorageReference.putBytes(data);
           uploadTask
                   .continueWithTask(task -> mStorageReference.getDownloadUrl())
                   .addOnCompleteListener(task -> {
                       if (task.isSuccessful()) {
                           callBack.onSuccess(task.getResult().toString());
                       } else {
                           callBack.onFailure(task.getException());
                       }
                   })
           ;
       }catch (Exception e){
           callBack.onFailure(e);
       }
    }

}
