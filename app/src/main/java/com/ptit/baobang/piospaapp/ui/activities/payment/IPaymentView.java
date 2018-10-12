package com.ptit.baobang.piospaapp.ui.activities.payment;

import com.ptit.baobang.piospaapp.data.cart.CartProductItem;
import com.ptit.baobang.piospaapp.data.dto.CustomerInfoDTO;
import com.ptit.baobang.piospaapp.data.model.Customer;
import com.ptit.baobang.piospaapp.data.model.District;
import com.ptit.baobang.piospaapp.data.model.OrderDeliveryType;
import com.ptit.baobang.piospaapp.data.model.OrderPaymentType;
import com.ptit.baobang.piospaapp.data.model.Province;
import com.ptit.baobang.piospaapp.data.model.Tax;
import com.ptit.baobang.piospaapp.ui.base.BaseView;

import java.util.List;

public interface IPaymentView extends BaseView{
    void nextStep(int currentStep);

    void openDialogProvince();

    void openDialogDistrict(Province province);

    void openDialogWard(District mDistrict);

    void updateRVOrderDeliveryType(List<OrderDeliveryType> deliveryTypes);

    void updateRVOrderPaymentType(List<OrderPaymentType> paymentTypes);

    void showData(CustomerInfoDTO customerInfoDTO);

    void updateRVProduct(List<CartProductItem> mItems);

    void showDataForInput(Customer customer);

    void doneStep();

    void openOrderActivity();

    void updateUIPaymentInfo(String totalPrice, String ship, String payment);

    void setTax(Tax data);
}
