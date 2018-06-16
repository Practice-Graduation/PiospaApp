package com.ptit.baobang.piospaapp.ui.activities.order_detail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ptit.baobang.piospaapp.data.cart.BookingItem;
import com.ptit.baobang.piospaapp.data.cart.CartProductItem;
import com.ptit.baobang.piospaapp.data.cart.CartServicePriceItem;
import com.ptit.baobang.piospaapp.data.model.BookingDetail;
import com.ptit.baobang.piospaapp.data.model.Order;
import com.ptit.baobang.piospaapp.data.model.OrderProduct;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.data.network.model_request.OrderResponse;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.CommonUtils;
import com.ptit.baobang.piospaapp.utils.DateTimeUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OrderDetailPresenter extends BasePresenter implements IOrderDetailPresenter {

    private IOrderDetailView mView;

    private Order mOrder;

    public OrderDetailPresenter(IOrderDetailView mView) {
        this.mView = mView;
    }

    @Override
    public void getDate(Intent intent) {
        Bundle bundle = intent.getExtras();
        mOrder = (Order) bundle.getSerializable(AppConstants.ORDER);
        mView.showLoading("Tải dữ liệu");
        getCompositeDisposable().add(mApiService.getProductAndBookingDetail(mOrder.getOrderId())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handlerResponse, this::handlerErorr));


    }

    private void handlerErorr(Throwable throwable) {
        mView.hideLoading(throwable.getMessage(), false);
    }

    private void handlerResponse(EndPoint<OrderResponse> listEndPoint) {

        List<CartProductItem> productItems = getCartProductFromOrderProduct(listEndPoint.getData().getOrderProducts());
        List<CartServicePriceItem> priceItems = getServicePriceFromBookingDetail(listEndPoint.getData().getBookingDetails());
        String[] address = mOrder.getAddressDelivery().split(",");

        mView.setView(mOrder.getCode(),
                mOrder.getCreatedAt(),
                mOrder.getOrderStatus().getOrderStatusName(),
                mOrder.getFullName(), address[0], address[1],
                address[2], address[3], mOrder.getPhone(),
                productItems,
                priceItems,
                mOrder.getOrderDeliveryType().getOrderDeliveryTypeName(),
                mOrder.getOrderPaymentType().getOrderPaymentTypeName(),
                mOrder.getOrderPaymentType().getOrderPaymentTypeDescription(),
                CommonUtils.formatToCurrency(mOrder.getTotal()),
                CommonUtils.formatToCurrency(mOrder.getOrderDeliveryType().getPrice()),
                CommonUtils.formatToCurrency(mOrder.getSubTotal()));

        mView.hideLoading();
    }

    private List<CartServicePriceItem> getServicePriceFromBookingDetail(List<BookingDetail> bookingDetails) {
        List<CartServicePriceItem> priceItems = new ArrayList<>();
        for (BookingDetail bookingDetail : bookingDetails) {
            Log.e("TAG", bookingDetail.getDateBooking());
            Date date = DateTimeUtils.getDateFromString(bookingDetail.getDateBooking(), bookingDetail.getTimeStart());
            BookingItem bookingItem = new BookingItem(bookingDetail.getServicePrice(), date);
            priceItems.add(new CartServicePriceItem(bookingItem, bookingDetail.getNumber()));
        }
        return priceItems;
    }

    private List<CartProductItem> getCartProductFromOrderProduct(List<OrderProduct> orderProducts) {
        List<CartProductItem> productItems = new ArrayList<>();
        for (OrderProduct orderProduct : orderProducts) {
            productItems.add(new CartProductItem(orderProduct.getProduct(), orderProduct.getNumber()));
        }
        return productItems;
    }
}
