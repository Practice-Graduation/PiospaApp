package com.ptit.baobang.piospaapp.ui.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

public class LoginActivity extends BaseActivity<LoginPresenter> implements ILoginView {


    @BindView(R.id.root)
    ConstraintLayout root;

    @BindView(R.id.login_layout)
    NestedScrollView nestedScrollView;

    @BindView(R.id.txtUsername)
    EditText txtUsername;

    @BindView(R.id.txtPassword)
    EditText txtPassword;

    Animation slideDownAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPresenter = new LoginPresenter(this, this);
        hideKeyboardOutside(root);

        slideDownAnimation = AnimationUtils.loadAnimation(
                getApplicationContext(),
                R.anim.slide_down_animation);
        nestedScrollView.startAnimation(slideDownAnimation);
    }

    @Override
    protected void onStart() {
        super.onStart();
        boolean isLogin = SharedPreferenceUtils.getIsLogin(this);
        if(isLogin){
            openMainActivity();
        }
    }

    @OnClick({R.id.btnLogin, R.id.btnRegister})
    void onClick(View view){
        switch (view.getId()){
            case R.id.btnLogin:
                mPresenter.onClickLogin(
                        txtUsername.getText().toString(),
                        txtPassword.getText().toString());
                break;
            case R.id.btnRegister:
                mPresenter.onClickRegister();
                break;

        }
    }

    @Override
    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void openRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivityForResult(intent, RequestCodeConstant.REQUEST_CODE);
    }

    @Override
    public String getUserName() {
        return txtUsername.getText().toString();
    }

    @Override
    public String getPassword() {
        return txtPassword.getText().toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RequestCodeConstant.REQUEST_CODE){
            if(resultCode == RESULT_OK){
                doDataResponse(data);
            }
        }
    }

    /**
     * Method get username and password was registed by user
     * And show in Login form
     * @param data
     * */

    private void doDataResponse(Intent data) {
        String userName = data.getStringExtra(KeyBundleConstant.USERNAME);
        String password = data.getStringExtra(KeyBundleConstant.PASSWORD);
        txtUsername.setText(userName);
        txtPassword.setText(password);
    }
}
