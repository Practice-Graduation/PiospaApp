package com.ptit.baobang.piospaapp.ui.activities.booking_info;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.cart.BookingItem;
import com.ptit.baobang.piospaapp.data.cart.Cart;
import com.ptit.baobang.piospaapp.data.cart.CartHelper;
import com.ptit.baobang.piospaapp.data.model.Room;
import com.ptit.baobang.piospaapp.data.model.ServicePrice;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.data.network.model_request.RoomBody;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.DateTimeUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    public void clickConfirm(ServicePrice mServicePrice, Date mSelectedDate, String mTimeSelected, String amount, Room mRoomSelected) {
        if (mSelectedDate == null) {
            mView.showMessage(mContext.getString(R.string.message), R.string.message_booking_date_empty, SweetAlertDialog.WARNING_TYPE);
            return;
        }
        if (mTimeSelected.trim().length() == 0) {
            mView.showMessage(mContext.getString(R.string.message), R.string.message_time_booking_empty, SweetAlertDialog.WARNING_TYPE);
            return;
        }
        if(mRoomSelected == null){
            mView.showMessage(mContext.getString(R.string.message), "Vui lòng chọn phòng", SweetAlertDialog.WARNING_TYPE);
            return;
        }
        int numberCustomer = Integer.parseInt(amount);
        Cart cart = CartHelper.getCart();
        BookingItem bookingItem = new BookingItem(mServicePrice, mSelectedDate, mRoomSelected);
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
        Calendar calendar = Calendar.getInstance();
        if(isSameDate(calendar.getTime(), date) &&calendar.get(Calendar.HOUR_OF_DAY) >= 22){
            mView.showMessage(mContext.getString(R.string.message), "Trung tâm đã đóng cửa, vui lòng chọn ngày khác", SweetAlertDialog.WARNING_TYPE);
            return;
        }
        mView.openTimePicker(date);
    }

    @Override
    public void clickRoom(Date mSelectedDate, String mTimeSelected, Room mRoomSelected) {
        if(mSelectedDate == null || mTimeSelected == null){
            mView.showMessage(mContext.getString(R.string.message),"Vui lòng chọn ngày giờ đặt hẹn", SweetAlertDialog.WARNING_TYPE);
            return;
        }

        RoomBody roomBody = new RoomBody(DateTimeUtils.formatDate(mSelectedDate, DateTimeUtils.DATE_PATTERN_DDMMYY), mTimeSelected);

        getCompositeDisposable().add(mApiService.getRoom(roomBody)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handlerResponseRoom, this::handlerErorr));
    }

    private void handlerResponseRoom(EndPoint<List<Room>> listEndPoint) {
        if(listEndPoint.getData().size() == 0){
            mView.showMessage(mContext.getString(R.string.message),"Phòng đầy, vui lòng chọn thời gian khác.", SweetAlertDialog.WARNING_TYPE);
            return;
        }
        mView.openRoom(listEndPoint.getData());
    }

    private void handlerErorr(Throwable throwable) {
        mView.showMessage(mContext.getString(R.string.message),throwable.getMessage(), SweetAlertDialog.ERROR_TYPE);
    }

    private boolean isSameDate(Date time, Date date) {
        String dateSystem = DateTimeUtils.formatDate(time, DateTimeUtils.DATE_PATTERN_DDMMYY);
        String dateSelected = DateTimeUtils.formatDate(date, DateTimeUtils.DATE_PATTERN_DDMMYY);
        return dateSelected.equals(dateSystem);
    }
}
