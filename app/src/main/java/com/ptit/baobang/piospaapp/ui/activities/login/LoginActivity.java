package com.ptit.baobang.piospaapp.ui.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.ui.activities.main.MainActivity;
import com.ptit.baobang.piospaapp.ui.activities.register.RegisterActivity;
import com.ptit.baobang.piospaapp.ui.base.BaseActivity;
import com.ptit.baobang.piospaapp.utils.KeyBundleConstant;
import com.ptit.baobang.piospaapp.utils.RequestCodeConstant;
import com.ptit.baobang.piospaapp.utils.SharedPreferenceUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Màn hình đăng nhập
 *
 * @version 1.0.1
 * @author BaoBang
 */
public class LoginActivity extends BaseActivity<LoginPresenter>
        implements ILoginView {

    @BindView(R.id.edtUserName)
    EditText edtUsername;

    @BindView(R.id.edtPassword)
    EditText edtPassword;
    /***
     * 2. Hiển thị ban đầu
     *    1. Thực hiện khởi tạo màn hình ban đầu
     * Khởi tạo màn hình
     *
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mPresenter = new LoginPresenter(this, this);
    }


    /**
     * 1. Kiểm tra đăng nhập
     *    a. Kiểm tra SharedPreferences
     *    b. Xử lý kiểm tra đăng nhập
     * */
    @Override
    protected void onStart() {
        super.onStart();
        // a. Kiểm tra SharedPreferences
        boolean isLogin = SharedPreferenceUtils.getIsLogin(this);
        // b. Xử lý kiểm tra đăng nhập
        if (isLogin) {
            openMainActivity();
        }
    }

    /**
     * 3. Xử lý đăng nhập
     *      1. Nhấn nút đăng nhập thì thực hiện xử lý đăng nhập
     * 4. Xử lý đăng kí
     *      1. Nhấn nút đăng kí
     * **/
    @OnClick({R.id.btnLogin, R.id.btnRegister})
    void onClick(View view) {
        switch (view.getId()) {
            // Sự kiện nhấn button đăng nhập
            // 3. Xử lý đăng nhập
            //    1. Nhấn nút đăng nhập thì thực hiện xử lý đăng nhập
            case R.id.btnLogin:
                mPresenter.onClickLogin(
                        edtUsername.getText().toString(),
                        edtPassword.getText().toString());
                break;
             // Sự kiện nhấn button đăng kí
             // 4. Xử lý đăng kí
             //     1. Nhấn nút đăng kí
            case R.id.btnRegister:
                openRegisterActivity();
                break;
        }
    }

    /**
     * 3. Xử lý đăng nhập
     *    2. Đăng nhập thành công thì di chuyển sang màn hình chính
     * **/
    @Override
    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 4. Xử lý đăng kí
     *    2. Thực hiện di chuyển sang màn hình đăng kí
     * **/
    @Override
    public void openRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
            startActivityForResult(intent, RequestCodeConstant.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCodeConstant.REQUEST_CODE && resultCode == RESULT_OK) {
            doDataResponse(data);
        }
    }

    /**
     * Method get username and password was registed by user
     * And show in Login form
     *
     * @param data Intent
     */

    private void doDataResponse(Intent data) {
        String userName = data.getStringExtra(KeyBundleConstant.USERNAME);
        String password = data.getStringExtra(KeyBundleConstant.PASSWORD);
        edtUsername.setText(userName);
        edtPassword.setText(password);
    }
}
