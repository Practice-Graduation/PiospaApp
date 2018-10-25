package com.ptit.baobang.piospaapp.ui.activities.login;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.Customer;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.data.network.model_request.LoginRequest;
import com.ptit.baobang.piospaapp.error.Error;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.InputUtils;
import com.ptit.baobang.piospaapp.utils.SharedPreferenceUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Presenter màn hình đăng nhập
 *
 * @version 1.0.1
 * @author BaoBang
 */
public class LoginPresenter extends BasePresenter
        implements ILoginPresenter {

    private ILoginView mILoginView;
    private Context mContext;

    /**
     * The contructor
     *
     * @param mContext Context
     * @param mILoginView ILoginVie
     * */
    LoginPresenter(Context mContext, ILoginView mILoginView) {
        super();
        this.mContext = mContext;
        this.mILoginView = mILoginView;
    }

    /**
     * 3. Xử lý đăng nhập
     *      1. Nhấn nút đăng nhập thì thực hiện xử lý.
     *      2. Xử lý check
     * Method was executed when user clicked button Login
     *      - Check the username and the password are valid
     *      - Send a quest to server to check login information
     *
     * @param username String
     * @param password String
     * */

    @Override
    public void onClickLogin(String username, String password) {
        // a. Check hạng mục
        //      1. Tên đăng nhập
        if (InputUtils.isUsernameValid(username)) {
            mILoginView.showWarningMessage(Error.ERROR_LOGIN_USR_NOT_VALID);
            return;
        }
        // a. Check hạng mục
        //      2. Mật khẩu
        if (InputUtils.isPasswordValid(password)) {
            mILoginView.showWarningMessage(Error.ERROR_LOGIN_PWD_NOT_VALID);
            return;
        }
        LoginRequest request = new LoginRequest(username, password);
        mILoginView.showLoading(mContext.getString(R.string.login));
        // b. Gửi yêu cầu đăng nhập
        mApiService.login(request).enqueue(new Callback<EndPoint<Customer>>() {
            @Override
            public void onResponse(
                    @NonNull Call<EndPoint<Customer>> call,
                    @NonNull Response<EndPoint<Customer>> response) {
                doResponse(response);
            }
            @Override
            public void onFailure(
                    @NonNull Call<EndPoint<Customer>> call,
                    @NonNull Throwable t) {
                doLoginFailed();
            }
        });
    }

    /**
     * 4. Check response
     *      1. Response error
     * The method was executed when the response of the request was sent by user is failed
     */
    private void doLoginFailed() {
        mILoginView.hideLoading(Error.ERROR_LOGIN_FAILED, false);
    }

    /**
     * 4. Check response
     *      2. Response succeeded
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
                mILoginView.openMainActivity();
            } else {
                mILoginView.hideLoading(response.body().getMessage(), false);
            }
        }
    }
    /**
     * 5. Lưu thông tin đăng nhập vào SharedPreferences  và di chuyển sang màn hình chính
     * Method will save customer infomation to local(SharedPreference)
     *
     * @param customer Customer
     */
    private void saveUserInfo(Customer customer) {
        SharedPreferenceUtils.saveUser(mContext, customer);
        SharedPreferenceUtils.saveIsLogin(mContext, true);
    }
}
