package com.ptit.baobang.piospaapp.ui.activities.booking_info;

import android.content.Intent;

import com.ptit.baobang.piospaapp.data.cart.BookingItem;
import com.ptit.baobang.piospaapp.data.cart.Cart;
import com.ptit.baobang.piospaapp.data.cart.CartHelper;
import com.ptit.baobang.piospaapp.data.model.Room;
import com.ptit.baobang.piospaapp.data.model.ServicePackage;
import com.ptit.baobang.piospaapp.data.model.ServicePrice;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.data.network.firebase.BookingTimeFB;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.AppConstants;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingInfoPresenter extends BasePresenter implements  IBookingInfoPresenter {

        private IBookingInfoView mView;

    public BookingInfoPresenter(IBookingInfoView mView) {
        this.mView = mView;
    }

    @Override
    public void loadDate(Intent intent) {
        int servicePriceId = intent.getIntExtra(AppConstants.SERVICE_PRICE_ID, 0);
        Date selectedDate = (Date) intent.getSerializableExtra(AppConstants.DATE_SELECTED);
        Room selectedRoom = (Room) intent.getSerializableExtra(AppConstants.ROOM_SELECTED);
        BookingTimeFB selectedTime = (BookingTimeFB) intent.getSerializableExtra(AppConstants.TIME_SELECRED);
        mApiService.getServicePriceById(servicePriceId).enqueue(new Callback<EndPoint<ServicePrice>>() {
            @Override
            public void onResponse(Call<EndPoint<ServicePrice>> call, Response<EndPoint<ServicePrice>> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatusCode() == 200){
                        ServicePrice servicePrice = response.body().getData();
                        mView.attachData(servicePrice, selectedDate, selectedRoom, selectedTime);
                    }
                }
            }

            @Override
            public void onFailure(Call<EndPoint<ServicePrice>> call, Throwable t) {

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
    public String sumTotalTimeOfServicePackage(ServicePackage servicePackage) {
//        List<Service> services = servicePackage.get
        return null;
    }

    @Override
    public void clickConfirm(ServicePrice mServicePrice, Date mSelectedDate, Room mSelectedRoom, BookingTimeFB mSelectedTime, String amount, String customerName, String phone, String email, String note) {
        if(customerName == null || customerName.length() == 0){
            mView.showMessage("Vui lòng nhập vào họ và tên");
            return;
        }
        if(phone == null || phone.length() == 0){
            mView.showMessage("Vui lòng nhập vào số điện thoại liên hệ");
            return;
        }
        if(email == null || email.length() == 0){
            mView.showMessage("Vui lòng nhập vào địa chỉ email");
            return;
        }
        int numberCustomer = Integer.parseInt(amount);
        Cart cart = CartHelper.getCart();
        BookingItem bookingItem = new BookingItem(mServicePrice, mSelectedDate, mSelectedRoom, mSelectedTime);
        cart.add(bookingItem, numberCustomer);
        mView.showMessage("Addedd");
    }

    @Override
    public void clickFloatButtonCart() {
        mView.openFramentCart();
    }
}
