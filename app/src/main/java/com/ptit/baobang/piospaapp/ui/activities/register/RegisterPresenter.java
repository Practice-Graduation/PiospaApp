package com.ptit.baobang.piospaapp.ui.activities.register;

import com.ptit.baobang.piospaapp.data.model.Customer;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.InputUtils;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
            mView.showMessage("Thông báo", "Nhập vào họ và tên", SweetAlertDialog.WARNING_TYPE);
            return;
        }
        if(email.trim().length() == 0){
            mView.showMessage("Thông báo","Nhập vào tên đăng nhập", SweetAlertDialog.WARNING_TYPE);
            return;
        }

        if(!InputUtils.isValidUsername(email)){
            mView.showMessage("Thông báo","Tài khoản ít nhất 5 kí tự a-zA-Z0-9._-", SweetAlertDialog.WARNING_TYPE);
            return;
        }

        if(password.trim().length() == 0){
            mView.showMessage("Thông báo","Nhập vào mật khẩu", SweetAlertDialog.WARNING_TYPE);
            return;
        }

        if(!InputUtils.isValidPassword(password.trim())){
            mView.showMessage("Thông báo","Mật khẩu ít nhất 5 kí tự", SweetAlertDialog.WARNING_TYPE);
            return;
        }

        if(retypePassword.trim().length() == 0){
            mView.showMessage("Thông báo","Nhập vào xác nhận mật khẩu", SweetAlertDialog.WARNING_TYPE);
            return;
        }
        if(!password.trim().equalsIgnoreCase(retypePassword.trim())){
            mView.showMessage("Thông báo","Mật không nhập không trùng nhau", SweetAlertDialog.WARNING_TYPE);
            return;
        }
        Customer customer = new Customer();
        customer.setAccount(email);
        customer.setPassword(password);
        customer.setFullname(fullName);

        mView.showLoading("Đăng kí");

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
        if(customerEndPoint.getStatusCode() == 200){
            mView.hideLoading();
            mView.backToLoginActiviry(
                    customerEndPoint.getData().getAccount(),
                    customerEndPoint.getData().getPassword());
        }else{
            mView.hideLoading(customerEndPoint.getMessage(), false);
        }
    }
}
