package com.ptit.baobang.piospaapp.ui.activities.main;

import android.content.Context;

import com.ptit.baobang.piospaapp.data.model.Customer;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.SharedPreferenceUtils;

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
        mView.loadUserInfo(customer.getCustomerAvatar(), customer.getFullName(), customer.getEmail());
    }

}
