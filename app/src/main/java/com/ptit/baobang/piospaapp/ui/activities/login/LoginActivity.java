package com.ptit.baobang.piospaapp.ui.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.ptit.baobang.piospaapp.ui.activities.main.MainActivity;
import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.Customer;
import com.ptit.baobang.piospaapp.ui.activities.register.RegisterActivity;
import com.ptit.baobang.piospaapp.ui.base.BaseActivity;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.SharedPreferenceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements ILoginView {

    private LoginPresenter mLoginPresenter;


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
        mUnbinder = ButterKnife.bind(this);
        mLoginPresenter = new LoginPresenter(this);
        slideDownAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_down_animation);
        nestedScrollView.startAnimation(slideDownAnimation);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Customer customer = SharedPreferenceUtils.getUser(this);
        if(customer != null){
            openMainActivity();
        }
    }

    @OnClick({R.id.btnLogin, R.id.btnRegister})
    void onClick(View view){
        switch (view.getId()){
            case R.id.btnLogin:
                mLoginPresenter.onClickLogin(txtUsername.getText().toString(),
                        txtPassword.getText().toString());
                break;
            case R.id.btnRegister:
                mLoginPresenter.onClickRegister();
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
        startActivityForResult(intent, AppConstants.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AppConstants.REQUEST_CODE){
            if(resultCode == RESULT_OK){
                String userName = data.getStringExtra(AppConstants.USERNAME);
                String password = data.getStringExtra(AppConstants.PASSWORD);
                txtUsername.setText(userName);
                txtPassword.setText(password);
            }
        }
    }
}
