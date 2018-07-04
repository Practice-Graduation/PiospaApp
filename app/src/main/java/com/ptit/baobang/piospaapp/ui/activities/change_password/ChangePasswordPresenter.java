package com.ptit.baobang.piospaapp.ui.activities.change_password;

import android.content.Context;

import com.ptit.baobang.piospaapp.data.model.Customer;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.SharedPreferenceUtils;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChangePasswordPresenter extends BasePresenter implements IChangePasswordPresenter {
    private Context mContext;
    private IChangePasswordView mView;

    public ChangePasswordPresenter(Context mContext, IChangePasswordView mView) {
        this.mContext = mContext;
        this.mView = mView;

    }

    public void clickCancel() {
        mView.openCancelChangePassword();
    }

    @Override
    public void clickChangePassword(String oldPassword, String newPassword, String passwordConfirm) {
        if (oldPassword.trim().length() == 0) {
            mView.showMessage("Thông báo", "Vui lòng nhập vào mật khẩu cũ", SweetAlertDialog.WARNING_TYPE);
            return;
        }
        if (newPassword.trim().length() == 0) {
            mView.showMessage("Thông báo", "Vui lòng nhập vào mật khẩu mới", SweetAlertDialog.WARNING_TYPE);
            return;
        }
        if (passwordConfirm.trim().length() == 0) {
            mView.showMessage("Thông báo", "Vui lòng nhập vào xác nhận mật khẩu", SweetAlertDialog.WARNING_TYPE);
            return;
        }

        Customer customer = SharedPreferenceUtils.getUser(mContext);

        if (!customer.getPassword().trim().equals(oldPassword)) {
            mView.showMessage("Thông báo", "Mật khẩu cũ không đúng", SweetAlertDialog.WARNING_TYPE);
            return;
        }

        if (!newPassword.trim().equals(passwordConfirm.trim())) {
            mView.showMessage("Thông báo", "Mật khẩu xác nhận không giống mật khẩu mới", SweetAlertDialog.WARNING_TYPE);
            return;
        }

        customer.setPassword(newPassword);
        mView.showLoading("Đổi mật khẩu");
        getCompositeDisposable()
                .add(mApiService.updateCustomer(customer.getCustomerId(), customer)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io())
                        .subscribe(this::handleRespons, this::handleError));
    }

    private void handleError(Throwable throwable) {
        mView.hideLoading(throwable.getMessage(), false);
    }

    private void handleRespons(EndPoint<Customer> customerEndPoint) {
        if (customerEndPoint.getStatusCode() == 200) {
            SharedPreferenceUtils.saveUser(mContext, customerEndPoint.getData());
            mView.clearData();
            mView.hideLoading("Đổi mật khẩu thành công", true);
        } else {
            mView.hideLoading(customerEndPoint.getMessage(), false);
        }
    }
}
