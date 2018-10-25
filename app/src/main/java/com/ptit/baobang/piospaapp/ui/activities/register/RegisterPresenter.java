package com.ptit.baobang.piospaapp.ui.activities.register;

import android.content.Context;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.dto.RegisterDTO;
import com.ptit.baobang.piospaapp.data.model.Customer;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.error.Error;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.InputUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Presenter màn hình đăng kí
 *
 * @version 1.0.1
 * @author BaoBang
 */
public class RegisterPresenter extends BasePresenter
        implements IRegisterPresenter {

    private IRegisterView mView;
    private Context mContext;

    RegisterPresenter(Context mContext, IRegisterView mView) {

        this.mContext = mContext;
        this.mView = mView;
    }

    /**
     * 2. Xử lý đăng kí
     *      2. Xử lý check
     *          a. Check hạng mục
     *      3. Gửi yêu cầu đăng kí tài khoản
     * @param registerDTO
     */
    @Override
    public void clickRegister(RegisterDTO registerDTO) {
        //2. Xử lý check
        //  a. Check hạng mục
        if (!isInputValid(registerDTO)) return;
        Customer customer = new Customer();
        customer.setAccount(registerDTO.getUserName());
        customer.setPassword(registerDTO.getPassword());
        customer.setFullName(registerDTO.getPassword());

        mView.showLoading(mContext.getString(R.string.register));
        // 3. Gửi yêu cầu đăng kí tài khoản
        getCompositeDisposable().add(
                mApiService.register(customer)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io())
                        .subscribe(this::handleResponse, this::handleError)
        );
    }

    /**
     * Kiểm tra xem input có rỗng hay không
     * Nếu rỗng thì hiện thị thông báo
     *
     * @param input String
     * @param error Error
     * @return boolean
     */
    private boolean isInputEmpty(String input, Error error) {
        if (input.trim().length() == 0) {
            mView.showErrorMessage(error);
            return true;
        }
        return false;
    }

    /**
     * Kiểm tra hợp lệ các input nhập vào
     * @param registerDTO
     * @return
     */
    private boolean isInputValid(RegisterDTO registerDTO) {
        if (isInputEmpty(registerDTO.getFullName(), Error.ERROR_REGISTER_NAME_EMPTY)) {
            return false;
        }
        if (InputUtils.isUsernameValid(registerDTO.getUserName())) {
            mView.showWarningMessage( Error.ERROR_REGISTER_USR_INVALID);
            return false;
        }
        if (InputUtils.isPasswordValid(registerDTO.getPassword().trim())) {
            mView.showWarningMessage(Error.ERROR_REGISTER_PWD_INVALID);
            return false;
        }
        if (!registerDTO.getPassword().trim()
                .equalsIgnoreCase(registerDTO.getRetypePassword().trim())) {
            mView.showWarningMessage(Error.ERROR_REGISTER_PWD_COMFIRM_NOT_SAME);
            return false;
        }
        return true;
    }

    /**
     * 4. Check response
     *      1. Response error
     * @param throwable Throwable
     */
    private void handleError(Throwable throwable) {
        mView.hideLoading(Error.ERROR_REGISTER_FAILED, false);
    }

    /**
     * 4. Check response
     *      2. Response succeeded
     * @param customerEndPoint EndPoint
     */
    private void handleResponse(EndPoint<Customer> customerEndPoint) {
        if (customerEndPoint.getStatusCode() == AppConstants.SUCCESS_CODE) {
            mView.hideLoading();
            mView.backToLoginActiviry(
                    customerEndPoint.getData().getAccount(),
                    customerEndPoint.getData().getPassword());
        } else {
            mView.hideLoading(customerEndPoint.getMessage(), false);
        }
    }
}
