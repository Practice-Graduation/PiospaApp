package com.ptit.baobang.piospaapp.ui.activities.payment;

import android.content.Context;

import com.ptit.baobang.piospaapp.data.model.District;
import com.ptit.baobang.piospaapp.data.model.OrderDeliveryType;
import com.ptit.baobang.piospaapp.data.model.OrderPaymentType;
import com.ptit.baobang.piospaapp.data.model.Province;
import com.ptit.baobang.piospaapp.data.model.Ward;

public interface IPaymentPresenter {
    void clickButtonNext(int currentStep, String name, String phone, Province mProvince, District mDistrict, Ward mWard, String address, OrderDeliveryType mDeliveryType, OrderPaymentType mPaymentType);

    void clickTextViewProvince();

    void clickTextViewDistrict(Province province);

    void clickTextViewWard(District mDistrict);

    void loadDeliveryType();

    void loadPaymentType();

    void attachData(String name, String phone, Province mProvince, District mDistrict, Ward mWard, String address, OrderDeliveryType mDeliveryType, OrderPaymentType mPaymentType);

    void loadCartItem();

    void attachDataForInput(Context baseContext);
}
