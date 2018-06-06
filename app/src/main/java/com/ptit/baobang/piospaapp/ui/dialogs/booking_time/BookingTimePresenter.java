package com.ptit.baobang.piospaapp.ui.dialogs.booking_time;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.ptit.baobang.piospaapp.data.model.BookingDetail;
import com.ptit.baobang.piospaapp.data.model.ServicePrice;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.data.network.model_request.BookingDetailRequest;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.CommonUtils;
import com.ptit.baobang.piospaapp.utils.DateTimeUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingTimePresenter extends BasePresenter implements IBookingTimePresenter {
    private IBookingTimeView mView;

    public BookingTimePresenter(IBookingTimeView mView) {
        this.mView = mView;
    }
    @Override
    public void loadData(ServicePrice servicePrice, Date date) {

        BookingDetailRequest request = new BookingDetailRequest();
        request.setDate(DateTimeUtils.formatDate(date));

        mApiService.getBookingDetailOnDayOfRoom(request).enqueue(new Callback<EndPoint<List<BookingDetail>>>() {
            @Override
            public void onResponse(@NonNull Call<EndPoint<List<BookingDetail>>> call, @NonNull Response<EndPoint<List<BookingDetail>>> response) {
                if(response.isSuccessful()){
                    List<BookingDetail> details = response.body().getData();
                    if(details == null){
                        details = new ArrayList<>();
                    }
                    createBookingTimes(details,servicePrice , date);
                    mView.stopShimmerAnimation();
                }
            }

            @Override
            public void onFailure(Call<EndPoint<List<BookingDetail>>> call, Throwable t) {

            }
        });
    }

    private void createBookingTimes(List<BookingDetail> details, ServicePrice servicePrice, Date selectedDate) {
        Calendar calendar = Calendar.getInstance();

        Date timeEnd = CommonUtils.getDateTime(selectedDate, AppConstants.END_TIME);
        calendar.setTime(timeEnd);
        calendar.add(Calendar.MINUTE, - getTimeService(servicePrice));
        timeEnd = calendar.getTime();
        Date timeStart = CommonUtils.getDateTime(selectedDate, AppConstants.START_TIME);
        Date currentTime = new Date();
        if(currentTime.getTime() > timeStart.getTime()){
            timeStart = currentTime;
        }
        calendar.setTime(timeStart);
        if(calendar.get(Calendar.MINUTE) > 30){
            calendar.set(Calendar.MINUTE, 0);
            calendar.add(Calendar.MINUTE, 60);
        }else{
            calendar.set(Calendar.MINUTE, 30);
        }

        List<String> times = new ArrayList<>();
        BookingDetail detail;
        while (calendar.getTime().getTime() <= timeEnd.getTime()) {
            detail = null;
            if(details.size() > 0){
                detail = details.get(0);
            }

            if(detail != null){
                Date timeBookingStart = CommonUtils.getDateTime(selectedDate,
                        detail.getTimeStart());
                if(calendar.getTime().getTime() >= timeBookingStart.getTime()){
                    String timeDurian = detail.getServicePrice().getService().getServiceTime().getTime();
                    times.add(DateTimeUtils.formatTime(calendar.getTime()));
                    calendar.add(Calendar.MINUTE, Integer.parseInt(timeDurian));
                    details.remove(0);
                    continue;
                }
            }
            times.add(DateTimeUtils.formatTime(calendar.getTime()));
            calendar.add(Calendar.MINUTE, 30);
        }
        mView.updateRecycleViewTime(times);
    }

    private int getTimeService(ServicePrice servicePrice) {

        int time = 0;
        if(servicePrice.getService() != null){
            time = Integer.parseInt(servicePrice.getService().getServiceTime().getTime());
        }else if(servicePrice.getServicePackage() != null){
            time = servicePrice.getServicePackage().getTime();
        }

        return time;
    }

    @Override
    public Date getSelectedDateFromIntent(Intent intent) {
        return (Date) intent.getSerializableExtra(AppConstants.DATE_SELECTED);
    }

    @Override
    public ServicePrice getServicePriceFromIntent(Intent intent) {
        return (ServicePrice) intent.getSerializableExtra(AppConstants.SERVICE_PRICE_ID);
    }

    @Override
    public void clickBooking(String time) {
        mView.backToBookingActivity(time);
    }
}
