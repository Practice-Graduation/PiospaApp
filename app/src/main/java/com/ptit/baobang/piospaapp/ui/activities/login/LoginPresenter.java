package com.ptit.baobang.piospaapp.ui.activities.login;

import com.ptit.baobang.piospaapp.data.model.Customer;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.data.network.model_request.LoginRequest;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.SharedPreferenceUtils;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter implements ILoginPresenter {

    private ILoginView mILoginView;

    LoginPresenter(ILoginView mILoginView) {
        super();
        this.mILoginView = mILoginView;
    }

    @Override
    public void onClickLogin(String username, String password) {
        if (username.trim().length() == 0) {
            mILoginView.showMessage("Thông báo", "Vui lòng nhập vào tên đăng nhập", SweetAlertDialog.WARNING_TYPE);
            return;
        }
        if (password.trim().length() == 0) {
            mILoginView.showMessage("Thông báo", "Vui lòng nhập vào mật khẩu", SweetAlertDialog.WARNING_TYPE);
            return;
        }
        LoginRequest request = new LoginRequest(username, password);
        mILoginView.showLoading("Đăng nhập");

        getCompositeDisposable().add(
                mApiService.login(request)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io())
                        .subscribe(this::handleResponseLogin, this::handleError));

//        mApiService.login(request).enqueue(new Callback<EndPoint<Customer>>() {
//            @Override
//            public void onResponse(@NonNull Call<EndPoint<Customer>> call, @NonNull Response<EndPoint<Customer>> response) {
//                if (response.isSuccessful()) {
//                    if (response.body().getStatusCode() == 200) {
//                        mILoginView.hideLoading();
//                        Customer customer = response.body().getData();
//                        SharedPreferenceUtils.saveUser(mILoginView.getBaseContext(), customer);
//                        mILoginView.openMainActivity();
//
//                    } else {
//                        mILoginView.hideLoading(response.body().getMessage(), false);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<EndPoint<Customer>> call, @NonNull Throwable t) {
//                mILoginView.hideLoading(t.getMessage(), false);
//            }
//        });
    }

    private void handleError(Throwable throwable) {
        mILoginView.hideLoading(throwable.getMessage(), false);
    }

    private void handleResponseLogin(EndPoint<Customer> customerEndPoint) {
        if (customerEndPoint.getStatusCode() == 200) {
            mILoginView.hideLoading();
            Customer customer = customerEndPoint.getData();
            SharedPreferenceUtils.saveUser(mILoginView.getBaseContext(), customer);
            mILoginView.openMainActivity();

        } else {
            mILoginView.hideLoading(customerEndPoint.getMessage(), false);
        }
    }

    @Override
    public void onClickRegister() {
        mILoginView.openRegisterActivity();
    }
}
