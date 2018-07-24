package com.ptit.baobang.piospaapp.ui.activities.update_profile;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.District;
import com.ptit.baobang.piospaapp.data.model.Province;
import com.ptit.baobang.piospaapp.data.model.Ward;
import com.ptit.baobang.piospaapp.ui.activities.login.LoginActivity;
import com.ptit.baobang.piospaapp.ui.base.BaseActivity;
import com.ptit.baobang.piospaapp.ui.dialogs.district.DistrictActivity;
import com.ptit.baobang.piospaapp.ui.dialogs.province.ProvinceActivity;
import com.ptit.baobang.piospaapp.ui.dialogs.ward.WardActivity;
import com.ptit.baobang.piospaapp.ui.listener.CallBackDialog;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.CommonUtils;
import com.ptit.baobang.piospaapp.utils.SharedPreferenceUtils;

import java.io.FileDescriptor;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class UpdateProfileActivity extends BaseActivity<UpdateProfilePresenter> implements IUpdateProfileView {

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

    private Bitmap avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        addControls();
        addEvents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_update_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.done:
                mPresenter.clickDone(
                        avatar,
                        txtFullName.getText().toString(),
                        txtPhone.getText().toString(),
                        txtEmail.getText().toString(),
                        txtBirthDay.getText().toString(),
                        txtGender.getText().toString(),
                        mProvince,
                        mDistrict,
                        mWard,
                        txtAddress.getText().toString()
                );
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @OnClick({R.id.imgAvatarBackground, R.id.imgAvatar, R.id.txtProvince, R.id.txtDistrict, R.id.txtWard, R.id.txtAddress,
            R.id.txtFullName, R.id.txtPhone, R.id.txtEmail, R.id.txtBirthDay, R.id.txtGender})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgAvatarBackground:
            case R.id.imgAvatar:
                mPresenter.clickUpdateAvatar(this);
                break;
            case R.id.txtProvince:
                mPresenter.clickProvine(mProvince);
                break;
            case R.id.txtDistrict:
                mPresenter.clickDistrict(mProvince, mDistrict);
                break;
            case R.id.txtWard:
                mPresenter.clickWard(mDistrict, mWard);
                break;
            case R.id.txtAddress:
                mPresenter.clickAddress(txtAddress.getText().toString());
                break;
            case R.id.txtFullName:
                mPresenter.clickFullName(txtFullName.getText().toString());
                break;
            case R.id.txtPhone:
                mPresenter.clickPhone(txtPhone.getText().toString());
                break;
            case R.id.txtEmail:
                mPresenter.clickEmil(txtEmail.getText().toString());
                break;
            case R.id.txtBirthDay:
                mPresenter.clickBirthday(txtBirthDay.getText().toString());
                break;
            case R.id.txtGender:
                mPresenter.clickGender(txtGender.getText().toString());
                break;
        }
    }


    private void addControls() {
        mPresenter = new UpdateProfilePresenter(this, this);
        setSuportToolbar();
    }

    private void setSuportToolbar() {
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitle("Cập Nhật Thông Tin Cá Nhân");
        centerToolbarTitle(mToolbar, 0);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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

        RequestOptions options = new RequestOptions().placeholder(R.drawable.paceholder).error(R.drawable.user);
        Bitmap error = BitmapFactory.decodeResource(getResources(), R.drawable.user);
        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), error);
        circularBitmapDrawable.setCircular(true);
        Glide.with(this).load(customerAvatar).apply(options).into(imgAvatarBackground);
        Glide.with(this).load(customerAvatar)
                .apply(RequestOptions.centerCropTransform().circleCrop().error(circularBitmapDrawable))
                .into(imgAvatar);

        txtFullName.setText(fullname);
        txtPhone.setText(phone);
        txtEmail.setText(email);
        txtBirthDay.setText(birthday);
        txtGender.setText(gender);
        mProvince = province;
        txtProvince.setText(province == null ? "" : province.getName());
        mDistrict = district;
        txtDistrict.setText(district == null ? "" : district.getName());
        mWard = ward;
        txtWard.setText(ward == null ? "" : ward.getName());
        txtAddress.setText(address);
    }

    @Override
    public void logOut() {
        SharedPreferenceUtils.saveUser(this, null);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClickProvince(Province mProvince) {
        Intent intent = new Intent(this, ProvinceActivity.class);
        intent.putExtra(AppConstants.PROVINCE, mProvince);
        startActivityForResult(intent, AppConstants.REQUEST_CODE_PROVINCE);
    }

    @Override
    public void onClickDistrict(District mDistrict) {
        Intent intent = new Intent(this, DistrictActivity.class);
        intent.putExtra(AppConstants.PROVINCE, mProvince);
        intent.putExtra(AppConstants.DISTRICT, mDistrict);
        startActivityForResult(intent, AppConstants.REQUEST_CODE_DISTRICT);
    }

    @Override
    public void onClickAddress(String s) {
        showEnterTextDialog("Nhập dịa chỉ", s, "Ok", "Hủy", new CallBackDialog() {
            @Override
            public void diaglogPositive(AlertDialog b, String s) {
                txtAddress.setText(s);
                b.dismiss();
            }

            @Override
            public void diaglogNegative() {

            }
        });
    }

    @Override
    public void onClickFullName(String s) {
        showEnterTextDialog("Nhập họ và tên", s, "Ok", "Hủy", new CallBackDialog() {
            @Override
            public void diaglogPositive(AlertDialog b, String s) {
                txtFullName.setText(s);
                b.dismiss();
            }

            @Override
            public void diaglogNegative() {

            }
        });
    }

    @Override
    public void onClickEmail(String s) {
        showEnterTextDialog("Nhập địa chỉ email", s, "Ok", "Hủy", new CallBackDialog() {
            @Override
            public void diaglogPositive(AlertDialog b, String s) {
                txtEmail.setText(s);
                b.dismiss();
            }

            @Override
            public void diaglogNegative() {

            }
        });
    }

    @Override
    public void onClickWard(Ward mWard) {
        Intent intent = new Intent(this, WardActivity.class);
        intent.putExtra(AppConstants.DISTRICT, mDistrict);
        intent.putExtra(AppConstants.WARD, mWard);
        startActivityForResult(intent, AppConstants.REQUEST_CODE_WARD);
    }

    @Override
    public void onClickPhone(String s) {
        showEnterTextDialog("Nhập số điện thoại", s, "Ok", "Hủy", new CallBackDialog() {
            @Override
            public void diaglogPositive(AlertDialog b, String s) {
                txtPhone.setText(s);
                b.dismiss();
            }

            @Override
            public void diaglogNegative() {

            }
        });
    }

    @Override
    public void onClickGender(String s) {
        boolean isMale = s.trim().toLowerCase().equalsIgnoreCase("nam");
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Chọn giới tính");

        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.gender_layout, null);

        TextView txtTitle = dialogView.findViewById(R.id.txtTitle);
        Button btnOk = dialogView.findViewById(R.id.btnOk);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        RadioButton rbMale = dialogView.findViewById(R.id.rbMale);
        RadioButton rbFeMale = dialogView.findViewById(R.id.rbFeMale);
        if (isMale) {
            rbMale.setChecked(true);
        } else {
            rbFeMale.setChecked(true);
        }
        btnOk.setText("Ok");
        btnCancel.setText("Hủy");

        dialogBuilder.setView(dialogView);
        AlertDialog b = dialogBuilder.create();

        btnOk.setOnClickListener(
                v -> {
                    if (rbMale.isChecked()) {
                        txtGender.setText("Nam");
                    } else {

                        txtGender.setText("Nữ");
                    }
                    b.dismiss();
                }
        );

        btnCancel.setOnClickListener(v -> {
            b.dismiss();
        });

        b.setCanceledOnTouchOutside(false);
        b.show();
    }

    @Override
    public void onClickBirthday(String s) {
        final Calendar calendar = Calendar.getInstance();
        if (!s.trim().isEmpty()) {

            SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.DATE_FORMAT, Locale.ENGLISH);

            try {
                Date date = sdf.parse(s.trim());
                calendar.setTime(date);// all done
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Calendar calendar1 = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (datePicker, year, monthOfYear, dayOfMonth) -> {
                    txtBirthDay.setText(new StringBuilder(CommonUtils.formatDateAndMonth(dayOfMonth) + "/" + CommonUtils.formatDateAndMonth((monthOfYear + 1)) + "/" + year));
                    calendar.set(Calendar.DATE, dayOfMonth);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.YEAR, year);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE));
        datePickerDialog.show();
    }

    @Override
    public void updateUIAvatar(Bitmap avatar) {
        RequestOptions options = new RequestOptions().placeholder(R.drawable.paceholder).error(R.drawable.error);
        if(avatar != null){
            Glide.with(this).load(avatar).apply(options).into(imgAvatarBackground);
            Glide.with(this).load(avatar)
                    .apply(RequestOptions.centerCropTransform().circleCrop())
                    .into(imgAvatar);
        }

    }

    @Override
    public void setNullAvatar() {
        avatar = null;
    }

    public static Bitmap getBitmapFromUri(Activity activity, Uri uri) throws IOException {

        ParcelFileDescriptor parcelFileDescriptor = activity
                .getContentResolver()
                .openFileDescriptor(uri, "r");

        FileDescriptor fileDescriptor = parcelFileDescriptor ==
                null ? null : parcelFileDescriptor.getFileDescriptor();

        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);

        if (parcelFileDescriptor != null) {
            parcelFileDescriptor.close();
        }

        return image;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case AppConstants.REQUEST_CAMERA_PIC:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED) {
                        mPresenter.cameraIntent(this);
                    }
                } else {
                    showMessage("Thông báo", "Ứng dụng không có quyền mở máy ảnh", SweetAlertDialog.WARNING_TYPE);
                }
                break;
            case AppConstants.REQUEST_SELECT_FILE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mPresenter.galleryIntent(this);
                } else {
                    showMessage("Thông báo", "Ứng dụng không có quyền truy cập bộ nhớ", SweetAlertDialog.WARNING_TYPE);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case AppConstants.REQUEST_CODE_PROVINCE:
                    Province province = (Province) data.getSerializableExtra(AppConstants.PROVINCE);
                    mProvince = province;
                    txtProvince.setText(province.getName());
                    txtDistrict.setText("");
                    txtWard.setText("");
                    mDistrict = null;
                    mWard = null;
                    txtDistrict.setClickable(true);
                    txtWard.setClickable(false);
                    break;
                case AppConstants.REQUEST_CODE_DISTRICT:
                    District district = (District) data.getSerializableExtra(AppConstants.DISTRICT);
                    mDistrict = district;
                    txtDistrict.setText(district.getName());
                    txtWard.setText("");
                    mWard = null;
                    txtWard.setClickable(true);
                    break;
                case AppConstants.REQUEST_CODE_WARD:
                    Ward ward = (Ward) data.getSerializableExtra(AppConstants.WARD);
                    txtWard.setText(ward.getName());
                    mWard = ward;
                    break;
                case AppConstants.REQUEST_CAMERA_PIC:
                    Bundle bundle = data.getExtras();

                    if (bundle != null) {
                        avatar = (Bitmap) bundle.get("data");
                        updateUIAvatar(avatar);
                    }else{
                        avatar = null;
                    }
                    break;
                case AppConstants.REQUEST_SELECT_FILE:
                    Uri selectedImageUri = data.getData();
                    try {
                        avatar = getBitmapFromUri(this, selectedImageUri);
                        updateUIAvatar(avatar);
                    } catch (IOException e) {
                        avatar = null;
                    }
                    break;
            }
        }
    }
}
