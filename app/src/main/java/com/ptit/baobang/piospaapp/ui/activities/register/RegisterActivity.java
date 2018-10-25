package com.ptit.baobang.piospaapp.ui.activities.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.dto.RegisterDTO;
import com.ptit.baobang.piospaapp.ui.base.BaseActivity;
import com.ptit.baobang.piospaapp.utils.KeyBundleConstant;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Màn hình đăng kí
 *
 * @version 1.0.1
 * @author BaoBang
 */
public class RegisterActivity extends BaseActivity<RegisterPresenter>
        implements IRegisterView {

    @BindView(R.id.txtFullName)
    EditText edtFullName;
    @BindView(R.id.txtEmail)
    EditText edtEmail;
    @BindView(R.id.txtPassword)
    EditText edtPassword;
    @BindView(R.id.txtRetypePassword)
    EditText edtRetypePassword;

    /**
     * 1. Hiển thị ban đầu
     *      1. Thực hiện khởi tạo màn hình ban đầu
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        addControls();
    }

    /**
     * 2. Xử lý đăng kí
     *      1. Nhấn nút đăng kí thì thực hiện xử lý.
     * 3. Xử lý bạn đã có tài khoản
     *      1. Nhấn bạn đã có tài khoản
     * @param view
     */
    @OnClick({R.id.btnRegister, R.id.txtBackToLogin})
    void onClick(View view) {
        switch (view.getId()) {
            // Sự kiện nhấn button đăng kí
            // 2. Xử lý đăng kí
            //      1. Nhấn nút đăng kí thì thực hiện xử lý.
            case R.id.btnRegister:
                RegisterDTO registerDTO  = new RegisterDTO();
                registerDTO.setFullName(edtFullName.getText().toString());
                registerDTO.setUserName(edtEmail.getText().toString());
                registerDTO.setPassword(edtPassword.getText().toString());
                registerDTO.setRetypePassword(edtRetypePassword.getText().toString());
                mPresenter.clickRegister(registerDTO);
                break;
            // Sự kiện nhấn textview bạn đã có tài khoản
            // 3. Xử lý bạn đã có tài khoản
            //       1. Nhấn bạn đã có tài khoản
            case R.id.txtBackToLogin:
                backToLoginActiviry("", "");
                break;
        }
    }

    private void addControls() {
        mPresenter = new RegisterPresenter(this, this);
    }

    /**
     * 3. Xử lý bạn đã có tài khoản
     *      2. Thực hiện chuyển về màn hình đăng nhập
     * @param username
     * @param password
     */
    @Override
    public void backToLoginActiviry(String username, String password) {
        Intent intent = new Intent();
        intent.putExtra(KeyBundleConstant.USERNAME, username);
        intent.putExtra(KeyBundleConstant.PASSWORD, password);
        setResult(RESULT_OK, intent);
        finish();
    }
}
