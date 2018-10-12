package com.ptit.baobang.piospaapp.ui.activities.payment;

import android.content.Context;

import com.ptit.baobang.piospaapp.data.dto.CustomerInfoDTO;
import com.ptit.baobang.piospaapp.data.model.District;
import com.ptit.baobang.piospaapp.data.model.Province;
import com.shuhart.stepview.StepView;

public interface IPaymentPresenter {
    void clickButtonNext(StepView stepView, CustomerInfoDTO customerInfoDTO);

    void clickTextViewProvince();

    void clickTextViewDistrict(Province province);

    void clickTextViewWard(District district);

    void loadDeliveryType();

    void loadPaymentType();


    void loadCartItem();

    void attachDataForInput(Context baseContext);

    void loadTax();

}
