package com.ptit.baobang.piospaapp.ui.activities.login;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.Customer;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.data.network.model_request.LoginRequest;
import com.ptit.baobang.piospaapp.error.Error;
import com.ptit.baobang.piospaapp.services.FCMUtils;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.SharedPreferenceUtils;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter extends BasePresenter implements ILoginPresenter {

    private ILoginView mILoginView;
    private Context mContext;

    /**
     * The contructor
     *
     * @param mContext
     * @param mILoginView
     * */
    LoginPresenter(Context mContext, ILoginView mILoginView) {
        super();
        this.mContext = mContext;
        this.mILoginView = mILoginView;
    }

    /**
     *
     * Method was executed when user clicked button Login
     *      - Check the username and the password are valid
     *      - Send a quest to server to check login information
     *
     * @param username
     * @param password
     * */

    @Override
    public void onClickLogin(String username, String password) {

        if (username.trim().length() == 0) {
            mILoginView.showMessage(
                    mContext.getString(R.string.message),
                    Error.ERROR_LOGIN_USR_EMPTY,
                    SweetAlertDialog.WARNING_TYPE);
            return;
        }
        if (password.trim().length() == 0) {
            mILoginView.showMessage(
                    mContext.getString(R.string.message),
                    Error.ERROR_LOGIN_PWD_EMPTY,
                    SweetAlertDialog.WARNING_TYPE);
            return;
        }

        LoginRequest request = new LoginRequest(username, password);
        mILoginView.showLoading(mContext.getString(R.string.login));
        mApiService.login(request).enqueue(new Callback<EndPoint<Customer>>() {
            @Override
            public void onResponse(@NonNull Call<EndPoint<Customer>> call, @NonNull Response<EndPoint<Customer>> response) {
                doResponse(response);
            }

            @Override
            public void onFailure(@NonNull Call<EndPoint<Customer>> call, @NonNull Throwable t) {
                doLoginFailed();
            }
        });
    }

    /**
     * The method was executed when the response of the request was sent by user is failed
     */
    private void doLoginFailed() {
        mILoginView.hideLoading(Error.ERROR_LOGIN_FAILED, false);
    }

    /**
     * Method was executed
     *
     * @param response data which client was received when the request is success
     */
    private void doResponse(Response<EndPoint<Customer>> response) {
        if (response.isSuccessful()) {
            if (response.body().getStatusCode() == AppConstants.SUCCESS_CODE) {
                mILoginView.hideLoading();
                Customer customer = response.body().getData();
                saveUserInfo(customer);
                registerNotify(customer);
                mILoginView.openMainActivity();
            } else {
                doLoginFailed();
            }
        }
    }

    /**
     * Method regist receice notify from firebase cloud message
     * The message will send by topic is customer's account
     *
     * @param customer
     */
    private void registerNotify(Customer customer) {
        FCMUtils.subscribeTopicFCM(mContext, customer.getAccount());
    }

    /**
     * Method will save customer infomation to local(SharedPreference)
     *
     * @param customer
     */
    private void saveUserInfo(Customer customer) {
        SharedPreferenceUtils.saveUser(mContext, customer);
        SharedPreferenceUtils.saveIsLogin(mContext, true);
    }


    /**
     * Method was executed when user click button register
     * Open Register activity to regist new account
     */
    @Override
    public void onClickRegister() {
        mILoginView.openRegisterActivity();
    }
}
