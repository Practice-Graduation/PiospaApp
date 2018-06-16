package com.ptit.baobang.piospaapp.ui.base;

import com.ptit.baobang.piospaapp.data.network.api.APIService;
import com.ptit.baobang.piospaapp.data.network.api.ApiUtils;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BasePresenter {
    protected APIService mApiService;
    private CompositeDisposable mDisposables;

    public BasePresenter() {
        mApiService = ApiUtils.getAPIService();
    }

    public APIService getmApiService(){
        return mApiService;
    }

    public void unSubscribeRequests(){
        if(mDisposables != null && !mDisposables.isDisposed()) {
            mDisposables.clear();
            mDisposables.dispose();
        }
    }

    public CompositeDisposable getCompositeDisposable(){
        if (mDisposables == null || mDisposables.isDisposed()) {
            mDisposables = new CompositeDisposable();
        }
        return mDisposables;
    }

}
