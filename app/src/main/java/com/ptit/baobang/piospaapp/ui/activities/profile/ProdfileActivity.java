package com.ptit.baobang.piospaapp.ui.activities.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.Customer;
import com.ptit.baobang.piospaapp.data.model.District;
import com.ptit.baobang.piospaapp.data.model.Province;
import com.ptit.baobang.piospaapp.data.model.Ward;
import com.ptit.baobang.piospaapp.ui.activities.change_password.ChangePasswordActivity;
import com.ptit.baobang.piospaapp.ui.activities.login.LoginActivity;
import com.ptit.baobang.piospaapp.ui.activities.order.OrderActivity;
import com.ptit.baobang.piospaapp.ui.activities.update_profile.UpdateProfileActivity;
import com.ptit.baobang.piospaapp.ui.base.BaseActivity;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.SharedPreferenceUtils;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class ProdfileActivity extends BaseActivity<ProfilePresenter> implements IProfileView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.imgAvatarBackground)
    ImageView imgAvatarBackground;

    @BindView(R.id.imgAvatar)
    ImageView imgAvatar;

    @BindView(R.id.txtFullName)
    TextView txtFullName;

    @BindView(R.id.txtPhone)
    TextView txtPhone;

    @BindView(R.id.txtEmail)
    TextView txtEmail;

    @BindView(R.id.txtBirthDay)
    TextView txtBirthDay;

    @BindView(R.id.txtGender)
    TextView txtGender;

    @BindView(R.id.txtProvince)
    TextView txtProvince;

    @BindView(R.id.txtDistrict)
    TextView txtDistrict;

    @BindView(R.id.txtWard)
    TextView txtWard;

    @BindView(R.id.txtAddress)
    TextView txtAddress;

    private Province mProvince;
    private District mDistrict;
    private Ward mWard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prodfile);

        addControls();
        addEvents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.update:
                mPresenter.clickUpdate();
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadData(getBaseContext());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @OnClick({R.id.lbShowOrder, R.id.layout_not_payment, R.id.layout_payment, R.id.layout_payment_cancle, R.id.lbLogOut, R.id.lbChangePassword})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.lbShowOrder:
                mPresenter.clickShowOrder();
                break;
            case R.id.layout_not_payment:
                mPresenter.clickShowOrder(0);
                break;
            case R.id.layout_payment:
                mPresenter.clickShowOrder(1);
                break;
            case R.id.layout_payment_cancle:
                mPresenter.clickShowOrder(2);
                break;
            case R.id.lbLogOut:
                mPresenter.logOut();
                break;
            case R.id.lbChangePassword:
                mPresenter.clickChangePassword();
                break;
        }
    }


    private void addControls() {
        mPresenter = new ProfilePresenter(this,this);
        setSuportToolbar();
        mPresenter.loadData(getApplicationContext());
    }

    private void setSuportToolbar() {
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitle("Thông Tin Cá Nhân");
        centerToolbarTitle(mToolbar, 0);
    }

    private void addEvents() {
    }

    @Override
    public void loadData(String customerAvatar, String fullname,
                         String phone, String email,
                         String birthday, String gender,
                         Province province, District district,
                         Ward ward, String address) {

        RequestOptions options = new RequestOptions().placeholder(R.drawable.paceholder).error(R.drawable.error);

        Glide.with(this).load(customerAvatar).apply(options).into(imgAvatarBackground);
        Glide.with(this).load(customerAvatar)
                .apply(RequestOptions.centerCropTransform().circleCrop())
                .into(imgAvatar);

        txtFullName.setText(fullname);
        txtPhone.setText(phone);
        txtEmail.setText(email);
        txtBirthDay.setText(birthday);
        txtGender.setText(gender);
        mProvince = province;
        txtProvince.setText(province.getName());
        mDistrict = district;
        txtDistrict.setText(district.getName());
        mWard = ward;
        txtWard.setText(ward.getName());
        txtAddress.setText(address);
    }

    @Override
    public void showOrderActivity() {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }

    @Override
    public void showOrderActivity(int i) {
        Intent intent = new Intent(this, OrderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.ORDER_FRAGMENT_INDEX, i);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void logOut() {
        SharedPreferenceUtils.saveUser(this, null);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClickUpdate(Customer customer) {
        Intent intent = new Intent(this, UpdateProfileActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstants.CUSTOMER, customer);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void openChangePasswordActivity() {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        startActivity(intent);
    }

}
