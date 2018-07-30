package com.ptit.baobang.piospaapp.services;

import com.ptit.baobang.piospaapp.data.model.Order;

public interface OrderCallBack {
    void onSuccess(Order data);
    void onFailed(String error);
}
