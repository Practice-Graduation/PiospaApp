package com.ptit.baobang.piospaapp.ui.fragments.fragment_booking;

import android.util.Log;

import com.ptit.baobang.piospaapp.data.model.BookingDetail;
import com.ptit.baobang.piospaapp.data.model.Room;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.data.network.firebase.BookingTimeFB;
import com.ptit.baobang.piospaapp.data.network.firebase.FirebaseUtils;
import com.ptit.baobang.piospaapp.data.network.model_request.BookingDetailRequest;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.CommonUtils;
import com.ptit.baobang.piospaapp.utils.DateTimeUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingFragmentPresenter extends BasePresenter implements IBookingFragmentPresenter {

    private IBookingFragmentView mView;

    public BookingFragmentPresenter(IBookingFragmentView mView) {
        this.mView = mView;
    }

    @Override
    public void loadData() {
        mApiService.getAllRoom().enqueue(new Callback<EndPoint<List<Room>>>() {
            @Override
            public void onResponse(Call<EndPoint<List<Room>>> call, Response<EndPoint<List<Room>>> response) {
                if (response.isSuccessful()) {
                    mView.updateRecycleViewRoom(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<EndPoint<List<Room>>> call, Throwable t) {
                mView.showMessage(t.getMessage());
            }
        });
    }

    @Override
    public void loadBookingTime(Room room, Date selectedDate) {


        BookingDetailRequest request = new BookingDetailRequest();
        request.setDate(DateTimeUtils.formatDate(selectedDate));

        mApiService.getBookingDetailOnDayOfRoom(request).enqueue(new Callback<EndPoint<List<BookingDetail>>>() {
            @Override
            public void onResponse(Call<EndPoint<List<BookingDetail>>> call, Response<EndPoint<List<BookingDetail>>> response) {
                if(response.isSuccessful()){
                    List<BookingDetail> details = response.body().getData();
                    if(details == null){
                        details = new ArrayList<>();
                    }
                    createBookingTimes(details, room, selectedDate);
                }
            }

            @Override
            public void onFailure(Call<EndPoint<List<BookingDetail>>> call, Throwable t) {

            }
        });




    }

    @Override
    public void clickBookingButton(int mServicePriceSelected, Date mSelectedDate, Room mRoomSelected, BookingTimeFB mTimeSelected) {
//        mView.openBookingInfoActivity(mServicePriceSelected, mSelectedDate, mRoomSelected, mTimeSelected);
    }

    private void createBookingTimes(List<BookingDetail> details, Room room, Date selectedDate) {
        Date timeStart = CommonUtils.getDateTime(selectedDate, room.getStartTime());
        Date timeEnd = CommonUtils.getDateTime(selectedDate, room.getEndTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timeStart);
        List<BookingTimeFB> times = new ArrayList<>();
        Log.e("TAg", details.size() + "");
        int index = 0;
        while (calendar.getTime().getTime() < timeEnd.getTime()) {

           BookingDetail detail = null;
           if(details.size() > 0){
               detail = details.get(0);
           }

           if(detail != null){
               Date timeBookingStart = CommonUtils.getDateTime(selectedDate,
                       detail.getTimeStart());
               if(calendar.getTime().getTime() >= timeBookingStart.getTime()){
                   String timeDurian = detail.getServicePrice().getService().getServiceTime().getTime();
                   BookingTimeFB bookingTimeFB = new BookingTimeFB();
                   bookingTimeFB.setServicePriceId(1);
                   bookingTimeFB.setTimeStart(DateTimeUtils.formatTime(calendar.getTime()));
                   calendar.add(Calendar.MINUTE, Integer.parseInt(timeDurian));
                   bookingTimeFB.setTimeEnd(DateTimeUtils.formatTime(calendar.getTime()));
                   bookingTimeFB.setBooking(true);
                   times.add(bookingTimeFB);
                   FirebaseUtils.addBookingTime(index++ +"",DateTimeUtils.formatDate(selectedDate), bookingTimeFB);
                   details.remove(0);
                   continue;
               }
           }

            BookingTimeFB bookingTimeFB = new BookingTimeFB();
            bookingTimeFB.setServicePriceId(1);
            bookingTimeFB.setTimeStart(DateTimeUtils.formatTime(calendar.getTime()));
            calendar.add(Calendar.MINUTE, 30);
            bookingTimeFB.setTimeEnd(DateTimeUtils.formatTime(calendar.getTime()));
            times.add(bookingTimeFB);
            FirebaseUtils.addBookingTime(index++ +"",DateTimeUtils.formatDate(selectedDate), bookingTimeFB);
        }
//        mView.updateRecycleViewTime(times);
    }
}
