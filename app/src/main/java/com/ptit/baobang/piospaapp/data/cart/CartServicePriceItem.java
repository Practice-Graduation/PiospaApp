package com.ptit.baobang.piospaapp.data.cart;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.math.BigDecimal;

public class CartServicePriceItem implements Serializable, Comparable<CartServicePriceItem> {
    private BookingItem bookingItem;
    private int numberCustomer;

    public CartServicePriceItem(BookingItem bookingItem, int numberCustomer) {
        this.bookingItem = bookingItem;
        this.numberCustomer = numberCustomer;
    }

    public CartServicePriceItem() {
    }

    public BookingItem getBookingItem() {
        return bookingItem;
    }

    public void setBookingItem(BookingItem bookingItem) {
        this.bookingItem = bookingItem;
    }

    public int getNumberCustomer() {
        return numberCustomer;
    }

    public void setNumberCustomer(int numberCustomer) {
        this.numberCustomer = numberCustomer;
    }

    @Override
    public int compareTo(@NonNull CartServicePriceItem o) {
        return bookingItem.getServicePrice().getServicePriceId() == o.getBookingItem().getServicePrice().getServicePriceId() ? 0 : 1;
    }

    public BigDecimal getTotalItem() {
        return BigDecimal.valueOf(bookingItem.getServicePrice().getAllPrice() * numberCustomer);
    }
}
