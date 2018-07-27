package com.ptit.baobang.piospaapp.ui.activities.register;

import android.content.Context;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.Customer;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.InputUtils;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RegisterPresenter extends BasePresenter implements IRegisterPresenter {

    private IRegisterView mView;
    private Context mContext;

    public RegisterPresenter(Context mContext, IRegisterView mView) {

        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void clickBackLogin() {

    }

    @Override
    public void clickRegister(String fullName, String email, String password, String retypePassword) {
        if (fullName.trim().length() == 0) {
            mView.showMessage(mContext.getString(R.string.message), R.string.message_fullname_empty, SweetAlertDialog.WARNING_TYPE);
            return;
        }
        if (email.trim().length() == 0) {
            mView.showMessage(mContext.getString(R.string.message), R.string.message_username_empty, SweetAlertDialog.WARNING_TYPE);
            return;
        }

        if (!InputUtils.isValidUsername(email)) {
            mView.showMessage(mContext.getString(R.string.message), R.string.message_username_invalid, SweetAlertDialog.WARNING_TYPE);
            return;
        }

        if (password.trim().length() == 0) {
            mView.showMessage(mContext.getString(R.string.message), R.string.message_pwd_empty, SweetAlertDialog.WARNING_TYPE);
            return;
        }

        if (!InputUtils.isValidPassword(password.trim())) {
            mView.showMessage(mContext.getString(R.string.message), R.string.message_pwd_invalid, SweetAlertDialog.WARNING_TYPE);
            return;
        }

        if (retypePassword.trim().length() == 0) {
            mView.showMessage(mContext.getString(R.string.message), R.string.message_pwd_comfirm_empty, SweetAlertDialog.WARNING_TYPE);
            return;
        }
        if (!password.trim().equalsIgnoreCase(retypePassword.trim())) {
            mView.showMessage(mContext.getString(R.string.message), R.string.message_pwd_and_pwd_comfirm_not_same, SweetAlertDialog.WARNING_TYPE);
            return;
        }
        Customer customer = new Customer();
        customer.setAccount(email);
        customer.setPassword(password);
        customer.setFullname(fullName);

        mView.showLoading(mContext.getString(R.string.register));

        getCompositeDisposable().add(
                mApiService.register(customer)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io())
                        .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleError(Throwable throwable) {
        mView.hideLoading(throwable.getMessage(), false);
    }

    private void handleResponse(EndPoint<Customer> customerEndPoint) {
        if (customerEndPoint.getStatusCode() == 200) {
            mView.hideLoading();
            mView.backToLoginActiviry(
                    customerEndPoint.getData().getAccount(),
                    customerEndPoint.getData().getPassword());
        } else {
            mView.hideLoading(customerEndPoint.getMessage(), false);
        }
    }
}
