package com.ptit.baobang.piospaapp.ui.activities.main;

import android.content.Context;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.Customer;
import com.ptit.baobang.piospaapp.data.model.Product;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.SharedPreferenceUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter extends BasePresenter implements IMainPresenter {

    private IMainView mView;
    private Context mContext;

    MainPresenter(Context mContext, IMainView mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void clickNavProfile() {
        mView.openProfileActivity();
    }

    @Override
    public void clickNavOrder() {
        mView.openOrderActivity();
    }

    @Override
    public void clickNavChangePassword() {
        mView.openChangePasswordActivity();
    }

    @Override
    public void clickNavLogout() {
        mView.logOut();
    }

    @Override
    public void loadUserInfo(Context context) {
        Customer customer = SharedPreferenceUtils.getUser(context);
        mView.loadUserInfo(customer.getCustomerAvatar(), customer.getFullname(), customer.getEmail());
    }

    @Override
    public void clickScanBarcode() {
        mView.openScanBarcodeActivity();
    }

    @Override
    public void getProductByCode(String returnString) {
        if(returnString == null || returnString.length() == 0){
            return;
        }
        mView.showLoading(mContext.getString(R.string.scan_qrcode));
        getCompositeDisposable().add(mApiService.getProductByCode(returnString)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handlerResponse, this::handleError));
    }

    private void handleError(Throwable throwable) {
        mView.hideLoading(throwable.getMessage(), false);
    }

    private void handlerResponse(EndPoint<Product> productEndPoint) {
        if(productEndPoint == null){
            mView.hideLoading(mContext.getString(R.string.scan_qrcode_failed), false);
            return;
        }
        if (productEndPoint.getStatusCode() == 200){
            if(productEndPoint.getData() != null){
                mView.hideLoading();
                mView.openProductDetail(productEndPoint.getData());
                return;
            }
        }
        mView.hideLoading(mContext.getString(R.string.message_respons_scan_qrcode_failed), false);
        return;
    }

}
