package com.ptit.baobang.piospaapp.ui.activities.booking_info;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.cart.BookingItem;
import com.ptit.baobang.piospaapp.data.cart.Cart;
import com.ptit.baobang.piospaapp.data.cart.CartHelper;
import com.ptit.baobang.piospaapp.data.model.ServicePrice;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.AppConstants;

import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BookingInfoPresenter extends BasePresenter implements IBookingInfoPresenter {

    private Context mContext;
    private IBookingInfoView mView;

    BookingInfoPresenter(Context mContext, IBookingInfoView mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void loadDataFromBunble(Intent intent) {

        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            ServicePrice servicePrice = (ServicePrice) bundle.getSerializable(AppConstants.SERVICE_PRICE_ID);
            mView.attachData(servicePrice);
        }

    }

    @Override
    public void clickAdd(String text) {
        int amount = Integer.parseInt(text);
        amount++;
        mView.setAmount(amount + "");
    }

    @Override
    public void clickRemove(String text) {
        int amount = Integer.parseInt(text);
        if (amount > 1)
            amount--;
        mView.setAmount(amount + "");
    }

    @Override
    public void clickConfirm(ServicePrice mServicePrice, Date mSelectedDate, String mTimeSelected, String amount) {
        if (mSelectedDate == null) {
            mView.showMessage(mContext.getString(R.string.message), R.string.message_booking_date_empty, SweetAlertDialog.WARNING_TYPE);
            return;
        }
        if (mTimeSelected.trim().length() == 0) {
            mView.showMessage(mContext.getString(R.string.message), R.string.message_time_booking_empty, SweetAlertDialog.WARNING_TYPE);
            return;
        }
        int numberCustomer = Integer.parseInt(amount);
        Cart cart = CartHelper.getCart();
        BookingItem bookingItem = new BookingItem(mServicePrice, mSelectedDate);
        cart.add(bookingItem, numberCustomer);
        mView.showMessage(mContext.getString(R.string.message), mContext.getString(R.string.added) + " " + mServicePrice + " " + mContext.getString(R.string._in_cart), SweetAlertDialog.SUCCESS_TYPE);
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
        if (date == null) {
            mView.showMessage(mContext.getString(R.string.message), R.string.message_booking_date_empty, SweetAlertDialog.WARNING_TYPE);
            return;
        }
        mView.openTimePicker(date);
    }
}
