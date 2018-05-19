package com.ptit.baobang.piospaapp.ui.activities.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.ui.base.BaseActivity;
import com.ptit.baobang.piospaapp.utils.AppConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements IRegisterView{

    private RegisterPresenter mPresenter;

    @BindView(R.id.txtFullName)
    EditText txtFullName;
    @BindView(R.id.txtEmail)
    EditText txtEmail;
    @BindView(R.id.txtPassword)
    EditText txtPassword;
    @BindView(R.id.txtRetypePassword)
    EditText txtRetypePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        addControls();
    }

    @OnClick({R.id.btnRegister, R.id.txtBackToLogin})
    void onClick(View view){
        switch (view.getId()){
            case R.id.btnRegister:
                mPresenter.clickRegister(txtFullName.getText().toString(),
                        txtEmail.getText().toString(),
                        txtPassword.getText().toString(),
                        txtRetypePassword.getText().toString());
                break;
            case R.id.txtBackToLogin:
                backToLoginActiviry("","");
                break;
        }
    }

    private void addControls() {
        mUnbinder = ButterKnife.bind(this);
        mPresenter = new RegisterPresenter(this);
    }

    @Override
    public void backToLoginActiviry(String email, String password) {
        Intent intent = new Intent();
        intent.putExtra(AppConstants.USERNAME, email);
        intent.putExtra(AppConstants.PASSWORD, password);
        setResult(RESULT_OK, intent);
        finish();
    }
}
