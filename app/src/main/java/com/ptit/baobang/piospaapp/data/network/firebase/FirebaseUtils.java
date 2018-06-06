package com.ptit.baobang.piospaapp.data.network.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtils {

    private static String BOOKING_TIME_REF = "booking";

    private static FirebaseDatabase database = null;

    public static FirebaseDatabase getDatabase() {
        if(database == null){
            database = FirebaseDatabase.getInstance();
        }
        return database;
    }

    public static DatabaseReference getBookingTimeRef(){
        return  getDatabase().getReference(BOOKING_TIME_REF);
    }

    public static void addBookingTime(String key, String date, BookingTimeFB bookingTimeFB){
        bookingTimeFB.setId(key);
        getBookingTimeRef().child(date).child(key).setValue(bookingTimeFB);
    }

}
