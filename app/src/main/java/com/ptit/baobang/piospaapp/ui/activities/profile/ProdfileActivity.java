package com.ptit.baobang.piospaapp.ui.activities.profile;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.District;
import com.ptit.baobang.piospaapp.data.model.Province;
import com.ptit.baobang.piospaapp.data.model.Ward;
import com.ptit.baobang.piospaapp.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProdfileActivity extends BaseActivity<ProfilePresenter> implements IProfileView {

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

    private void addControls() {
        mPresenter = new ProfilePresenter(this);
    }

    private void addEvents() {
        mPresenter.loadData(getApplicationContext());
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
}
