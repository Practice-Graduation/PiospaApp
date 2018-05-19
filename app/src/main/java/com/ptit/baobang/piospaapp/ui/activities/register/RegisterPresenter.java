package com.ptit.baobang.piospaapp.ui.activities.register;

import com.ptit.baobang.piospaapp.data.model.Customer;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresenter extends BasePresenter implements IRegisterPresenter{

    IRegisterView mView;

    public RegisterPresenter(IRegisterView mView) {
        this.mView = mView;
    }

    @Override
    public void clickBackLogin() {

    }

    @Override
    public void clickRegister(String fullName, String email, String password, String retypePassword) {
        if(fullName.trim().length() == 0){
            mView.showMessage("Nhập vào họ và tên");
            return;
        }
        if(email.trim().length() == 0){
            mView.showMessage("Nhập vào tên đăng nhập");
            return;
        }
        if(password.trim().length() == 0){
            mView.showMessage("Nhập vào mật khẩu");
            return;
        }
        if(retypePassword.trim().length() == 0){
            mView.showMessage("Nhập vào xác nhận mật khẩu");
            return;
        }
        if(!password.trim().equalsIgnoreCase(retypePassword.trim())){
            mView.showMessage("Mật không nhập không trùng nhau");
            return;
        }
        Customer customer = new Customer();
        customer.setAccount(email);
        customer.setPassword(password);
        customer.setFullname(fullName);

        mView.showLoading();
        mApiService.register(customer).enqueue(new Callback<EndPoint<Customer>>() {
            @Override
            public void onResponse(Call<EndPoint<Customer>> call, Response<EndPoint<Customer>> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatusCode() == 200){
                        mView.hideLoading();
                        mView.backToLoginActiviry(email, password);
                    }else{
                        mView.hideLoading();
                        mView.showMessage(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<EndPoint<Customer>> call, Throwable t) {
                mView.hideLoading();
                mView.showMessage(t.getMessage());
            }
        });
    }
}
