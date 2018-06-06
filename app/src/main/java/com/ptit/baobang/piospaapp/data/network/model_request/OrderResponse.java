package com.ptit.baobang.piospaapp.data.network.model_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ptit.baobang.piospaapp.data.model.BookingDetail;
import com.ptit.baobang.piospaapp.data.model.OrderProduct;

import java.util.List;

public class OrderResponse {
    @SerializedName("orderProducts")
    @Expose
    private List<OrderProduct> orderProducts = null;
    @SerializedName("bookingDetails")
    @Expose
    private List<BookingDetail> bookingDetails = null;

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public List<BookingDetail> getBookingDetails() {
        return bookingDetails;
    }

    public void setBookingDetails(List<BookingDetail> bookingDetails) {
        this.bookingDetails = bookingDetails;
    }
}
