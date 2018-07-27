package com.ptit.baobang.piospaapp.data.cart;

import android.support.annotation.NonNull;

import com.ptit.baobang.piospaapp.data.model.ServicePrice;
import com.ptit.baobang.piospaapp.utils.DateTimeUtils;

import java.io.Serializable;
import java.util.Date;

public class BookingItem implements Serializable, Comparable<BookingItem> {
    private ServicePrice servicePrice;
    private Date selectedDateTime;

    public BookingItem(ServicePrice servicePrice, Date selectedDate) {
        this.servicePrice = servicePrice;
        this.selectedDateTime = selectedDate;
    }


    public ServicePrice getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(ServicePrice servicePrice) {
        this.servicePrice = servicePrice;
    }

    public Date getSelectedDate() {
        return selectedDateTime;
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDateTime = selectedDate;
    }

    @Override
    public int compareTo(@NonNull BookingItem o) {

        boolean isSameService = servicePrice.getServicePriceId() == o.getServicePrice().getServicePriceId();

        boolean isSameDateTime = DateTimeUtils.formatDate(selectedDateTime, DateTimeUtils.DATETIME_PATTERN)
                .equals(DateTimeUtils.formatDate(o.getSelectedDate(), DateTimeUtils.DATETIME_PATTERN));

        if (isSameService && isSameDateTime) {
            return 0;
        }

        return 1;
    }
}
