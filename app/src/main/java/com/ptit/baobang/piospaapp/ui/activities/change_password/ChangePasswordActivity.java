package com.ptit.baobang.piospaapp.ui.activities.change_password;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.ui.base.BaseActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class ChangePasswordActivity extends BaseActivity<ChangePasswordPresenter> implements IChangePasswordView {

    @BindView(R.id.root)
    ConstraintLayout root;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.txtOldPassword)
    EditText txtOldPassword;
    @BindView(R.id.txtNewPassword)
    EditText txtNewPassword;
    @BindView(R.id.txtPasswordConfirm)
    EditText txtPasswordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        hideKeyboardOutside(root);
        addControls();
    }


    @OnClick({R.id.btnChangePassword, R.id.btnCancel})
    void onClick(View view){
        switch (view.getId()){
            case R.id.btnChangePassword:
                mPresenter.clickChangePassword(
                        txtOldPassword.getText().toString(),
                        txtNewPassword.getText().toString(),
                        txtPasswordConfirm.getText().toString());
                break;
            case R.id.btnCancel:
                mPresenter.clickCancel();
                break;
        }
    }

    private void addControls() {
        mPresenter = new ChangePasswordPresenter(this, this);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitle("Đổi Mật Khẩu");
        centerToolbarTitle(mToolbar, 20);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return (super.onOptionsItemSelected(item));
    }

    @Override
    public void openCancelChangePassword() {
        onBackPressed();
    }

    @Override
    public void clearData() {
        txtOldPassword.setText("");
        txtNewPassword.setText("");
        txtPasswordConfirm.setText("");
    }
}
