package com.ptit.baobang.piospaapp.ui.activities.change_password;

import android.content.Context;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.Customer;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.InputUtils;
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
            mView.showMessage(mContext.getString(R.string.message), R.string.message_old_password_empty, SweetAlertDialog.WARNING_TYPE);
            return;
        }
        if (newPassword.trim().length() == 0) {
            mView.showMessage(mContext.getString(R.string.message), R.string.message_new_pwd_empty, SweetAlertDialog.WARNING_TYPE);
            return;
        }

        if(!InputUtils.isValidPassword(newPassword.trim())){
            mView.showMessage(mContext.getString(R.string.message), R.string.message_pwd_invalid, SweetAlertDialog.WARNING_TYPE);
            return;
        }

        if (passwordConfirm.trim().length() == 0) {
            mView.showMessage(mContext.getString(R.string.message), R.string.message_pwd_comfirm_empty, SweetAlertDialog.WARNING_TYPE);
            return;
        }

        Customer customer = SharedPreferenceUtils.getUser(mContext);

        if (!customer.getPassword().trim().equals(oldPassword)) {
            mView.showMessage(mContext.getString(R.string.message), R.string.message_old_pwd_invlid, SweetAlertDialog.WARNING_TYPE);
            return;
        }

        if (!newPassword.trim().equals(passwordConfirm.trim())) {
            mView.showMessage(mContext.getString(R.string.message), R.string.message_pwd_and_pwd_comfirm_not_same, SweetAlertDialog.WARNING_TYPE);
            return;
        }

        customer.setPassword(newPassword);
        mView.showLoading(mContext.getString(R.string.change_password));
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
            mView.hideLoading(mContext.getString(R.string.message_change_pwd_success), true);
        } else {
            mView.hideLoading(customerEndPoint.getMessage(), false);
        }
    }
}
