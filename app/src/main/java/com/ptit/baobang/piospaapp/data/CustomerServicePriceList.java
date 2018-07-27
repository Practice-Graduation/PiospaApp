package com.ptit.baobang.piospaapp.data;

import com.ptit.baobang.piospaapp.data.model.ServicePrice;

import java.util.List;

public class CustomerServicePriceList {
    private List<ServicePrice> data;

    public CustomerServicePriceList(List<ServicePrice> data) {
        this.data = data;
    }

    public CustomerServicePriceList() {
    }

    public List<ServicePrice> getData() {
        return data;
    }

    public void setData(List<ServicePrice> data) {
        this.data = data;
    }
}
