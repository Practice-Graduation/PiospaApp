package com.ptit.baobang.piospaapp.ui.activities.login;

import android.support.annotation.NonNull;

import com.ptit.baobang.piospaapp.data.model.Customer;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.data.network.model_request.LoginRequest;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.SharedPreferenceUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter extends BasePresenter implements ILoginPresenter {

    private ILoginView mILoginView;

    public LoginPresenter(ILoginView mILoginView) {
        this.mILoginView = mILoginView;
    }

    @Override
    public void onClickLogin(String username, String password) {
        if (username.trim().length() == 0) {
            mILoginView.showMessage("Vui lòng nhập vào tên đăng nhập");
            return;
        }
        if (password.trim().length() == 0) {
            mILoginView.showMessage("Vui lòng nhập vào mật khẩu");
            return;
        }
        LoginRequest request = new LoginRequest(username, password);
        mILoginView.showLoading();
        mApiService.login(request).enqueue(new Callback<EndPoint<Customer>>() {
            @Override
            public void onResponse(@NonNull Call<EndPoint<Customer>> call, @NonNull Response<EndPoint<Customer>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatusCode() == 200) {
                        mILoginView.hideLoading();
                        Customer customer = response.body().getData();
                        SharedPreferenceUtils.saveUser(mILoginView.getBaseContext(), customer);
                        mILoginView.openMainActivity();

                    } else {
                        mILoginView.hideLoading();
                        mILoginView.showMessage(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<EndPoint<Customer>> call, @NonNull Throwable t) {
                mILoginView.hideLoading();
                mILoginView.showMessage(t.getMessage());
            }
        });
    }

    @Override
    public void onClickRegister() {
        mILoginView.openRegisterActivity();
    }
}
