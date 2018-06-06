package com.ptit.baobang.piospaapp.ui.activities.booking_info;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.ptit.baobang.piospaapp.data.cart.BookingItem;
import com.ptit.baobang.piospaapp.data.cart.Cart;
import com.ptit.baobang.piospaapp.data.cart.CartHelper;
import com.ptit.baobang.piospaapp.data.model.ServicePrice;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.AppConstants;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingInfoPresenter extends BasePresenter implements  IBookingInfoPresenter {

        private IBookingInfoView mView;

    BookingInfoPresenter(IBookingInfoView mView) {
        this.mView = mView;
    }

    @Override
    public void loadDate(Intent intent) {
        int servicePriceId = intent.getIntExtra(AppConstants.SERVICE_PRICE_ID, 0);
        mApiService.getServicePriceById(servicePriceId).enqueue(new Callback<EndPoint<ServicePrice>>() {
            @Override
            public void onResponse(@NonNull Call<EndPoint<ServicePrice>> call, @NonNull Response<EndPoint<ServicePrice>> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatusCode() == 200){
                        ServicePrice servicePrice = response.body().getData();
                        mView.attachData(servicePrice);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<EndPoint<ServicePrice>> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void clickAdd(String text) {
        int amount = Integer.parseInt(text);
        amount++;
        mView.setAmount(amount+"");
    }

    @Override
    public void clickRemove(String text) {
        int amount = Integer.parseInt(text);
        if(amount > 1)
             amount--;
        mView.setAmount(amount+"");
    }

    @Override
    public void clickConfirm(ServicePrice mServicePrice, Date mSelectedDate, String amount) {
        int numberCustomer = Integer.parseInt(amount);
        Cart cart = CartHelper.getCart();
        BookingItem bookingItem = new BookingItem(mServicePrice, mSelectedDate);
        cart.add(bookingItem, numberCustomer);
        mView.showMessage("Addedd");
    }

    @Override
    public void clickFloatButtonCart() {
        mView.openFramentCart();
    }

    @Override
    public void clickSelectDate() {
        mView.openDatePicker();
    }

    @Override
    public void clickSelectTime(Date date) {
        if(date == null){
            mView.showMessage("Vui lòng chọn ngày đặt hẹn");
            return;
        }
        mView.openTimePicker(date);
    }
}
