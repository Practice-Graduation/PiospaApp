package com.ptit.baobang.piospaapp.ui.activities.login;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.Customer;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.data.network.model_request.LoginRequest;
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
    LoginPresenter(Context mContext, ILoginView mILoginView) {
        super();
        this.mContext = mContext;
        this.mILoginView = mILoginView;
    }

    @Override
    public void onClickLogin(String username, String password) {
        if (username.trim().length() == 0) {
            mILoginView.showMessage(mContext.getString(R.string.message), R.string.message_username_empty, SweetAlertDialog.WARNING_TYPE);
            return;
        }
        if (password.trim().length() == 0) {
            mILoginView.showMessage(mContext.getString(R.string.message), mContext.getString(R.string.message_pwd_empty), SweetAlertDialog.WARNING_TYPE);
            return;
        }
        LoginRequest request = new LoginRequest(username, password);
        mILoginView.showLoading(mContext.getString(R.string.login));

        mApiService.login(request).enqueue(new Callback<EndPoint<Customer>>() {
            @Override
            public void onResponse(@NonNull Call<EndPoint<Customer>> call, @NonNull Response<EndPoint<Customer>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatusCode() == AppConstants.SUCCESS_CODE) {
                        mILoginView.hideLoading();
                        Customer customer = response.body().getData();
                        SharedPreferenceUtils.saveUser(mContext, customer);
                        SharedPreferenceUtils.saveIsLogin(mContext, true);
                        FCMUtils.subscribeTopicFCM(mContext, customer.getAccount());
                        mILoginView.openMainActivity();

                    } else {
                        mILoginView.hideLoading(response.body().getMessage(), false);
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<EndPoint<Customer>> call, @NonNull Throwable t) {
                mILoginView.hideLoading(t.getMessage(), false);
            }
        });
    }



    @Override
    public void onClickRegister() {
        mILoginView.openRegisterActivity();
    }
}
