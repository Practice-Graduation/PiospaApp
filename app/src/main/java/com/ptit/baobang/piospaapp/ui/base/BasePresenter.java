package com.ptit.baobang.piospaapp.ui.base;

import com.ptit.baobang.piospaapp.data.model.Room;
import com.ptit.baobang.piospaapp.data.network.api.APIService;
import com.ptit.baobang.piospaapp.data.network.api.ApiUtils;
import com.ptit.baobang.piospaapp.data.network.firebase.BookingTimeFB;

import java.util.Date;

public abstract class BasePresenter {
    protected APIService mApiService;

    public BasePresenter() {
        mApiService = ApiUtils.getAPIService();
    }

    public APIService getmApiService(){
        return mApiService;
    }

}
