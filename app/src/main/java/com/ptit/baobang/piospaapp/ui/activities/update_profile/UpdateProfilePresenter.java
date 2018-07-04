package com.ptit.baobang.piospaapp.ui.activities.update_profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.ptit.baobang.piospaapp.data.model.Customer;
import com.ptit.baobang.piospaapp.data.model.District;
import com.ptit.baobang.piospaapp.data.model.Province;
import com.ptit.baobang.piospaapp.data.model.Ward;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.ui.listener.CallBackChoosePhoto;
import com.ptit.baobang.piospaapp.ui.listener.CallBackConfirmDialog;
import com.ptit.baobang.piospaapp.ui.listener.CallBackUploadFireBase;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.CommonUtils;
import com.ptit.baobang.piospaapp.utils.DateTimeUtils;
import com.ptit.baobang.piospaapp.utils.FirebaseStoreUtils;
import com.ptit.baobang.piospaapp.utils.InputUtils;
import com.ptit.baobang.piospaapp.utils.SharedPreferenceUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UpdateProfilePresenter extends BasePresenter implements IUpdateProfilePresenter {

    private Context mContext;
    private IUpdateProfileView mView;

    public UpdateProfilePresenter(Context mContext, IUpdateProfileView mView) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @Override
    public void loadData(Context baseContext) {
        Customer customer = SharedPreferenceUtils.getUser(baseContext);

        String gender = customer.getGender();

        gender = gender.equalsIgnoreCase("male") ? "Nam" : "Nữ";
        String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, new Locale("vi", "VN"));
        try {
            calendar.setTime(sdf.parse(customer.getBirthday()));
        } catch (ParseException e) {
            sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", new Locale("vi", "VN"));
            try {
                calendar.setTime(sdf.parse(customer.getBirthday()));
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }

        mView.loadData(customer.getCustomerAvatar(),
                customer.getFullname(),
                customer.getPhone(),
                customer.getEmail(),
                DateTimeUtils.formatDate(calendar.getTime(), DateTimeUtils.DATE_PATTERN_DDMMYY),
                gender,
                customer.getProvince(),
                customer.getDistrict(),
                customer.getWard(),
                customer.getAddress());
    }

    @Override
    public void clickShowOrder() {
        mView.showOrderActivity();
    }

    @Override
    public void clickShowOrder(int i) {
        mView.showOrderActivity(i);
    }

    @Override
    public void logOut() {
        mView.logOut();
    }

    @Override
    public void clickUpdate() {
        Customer customer = SharedPreferenceUtils.getUser(mContext);
        mView.onClickUpdate(customer);
    }

    @Override
    public void clickProvine(Province mProvince) {
        mView.onClickProvince(mProvince);
    }

    @Override
    public void clickDistrict(District mDistrict) {
        mView.onClickDistrict(mDistrict);
    }

    @Override
    public void clickAddress(String s) {
        mView.onClickAddress(s);
    }

    @Override
    public void clickFullName(String s) {
        mView.onClickFullName(s);
    }

    @Override
    public void clickEmil(String s) {
        mView.onClickEmail(s);
    }

    @Override
    public void clickWard(Ward mWard) {
        mView.onClickWard(mWard);
    }

    @Override
    public void clickPhone(String s) {
        mView.onClickPhone(s);
    }

    @Override
    public void clickBirthday(String s) {
        mView.onClickBirthday(s);
    }

    @Override
    public void clickGender(String s) {
        mView.onClickGender(s);
    }

    @Override
    public void clickDone(Bitmap avatar, String fullName, String phone, String email, String birthday, String gender,
                          Province mProvince, District mDistrict, Ward mWard, String address) {
        Customer customer = SharedPreferenceUtils.getUser(mContext);
        if (!checkDataChange(avatar, customer, fullName, phone, email, birthday, gender, mProvince, mDistrict, mWard, address)) {
            mView.showMessage("Thông báo", "Không có dữ liệu được thay đổi", SweetAlertDialog.NORMAL_TYPE);
            return;
        }

        if(!InputUtils.isValidPhone(phone)){
            mView.showMessage("Thông báo", "Số điện thoại " + phone + " không đúng", SweetAlertDialog.WARNING_TYPE);
            return;
        }
        if(!InputUtils.isValidEmail(email)){
            mView.showMessage("Thông báo", "Email " + email + " không đúng", SweetAlertDialog.WARNING_TYPE);
            return;
        }

        mView.showConfirm("Thông báo", "Lưu thay đổi", "Ok", "Hủy", SweetAlertDialog.NORMAL_TYPE, new CallBackConfirmDialog() {
            @Override
            public void DiaglogPositive() {
                customer.setFullname(fullName);
                customer.setPhone(phone);
                customer.setEmail(email);

                String pattern = "dd/MM/yyy";

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat(pattern, new Locale("vi", "VN"));
                try {
                    calendar.setTime(sdf.parse(birthday));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                mView.showLoading("Upload...");
                customer.setBirthday(DateTimeUtils.formatDate(calendar.getTime(), DateTimeUtils.DATE_PATTERN_DDMMYYTHHMMSSSSSZ));
                customer.setGender(gender.equalsIgnoreCase("Nam") ? "male" : "female");
                customer.setProvince(mProvince);
                customer.setDistrict(mDistrict);
                customer.setWard(mWard);
                customer.setAddress(address);
                if (avatar == null) {
                    uploadWithoutAvatar(customer);
                    Log.e("Up profile Pre", "uploadWithoutAvatar");
                } else {
                    uploadWithAvatar(customer, avatar);
                    Log.e("Up profile Pre", "uploadWithAvatar");
                }
            }

            @Override
            public void DiaglogNegative() {

            }
        });
    }

    private void uploadWithoutAvatar(Customer customer) {
        getCompositeDisposable().add(mApiService.updateCustomer(customer.getCustomerId(), customer)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleUploadSuccess, this::handleUploadError));
    }

    private void uploadWithAvatar(Customer customer, Bitmap avatar) {
        FirebaseStoreUtils.uploadFile(avatar, new CallBackUploadFireBase() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                mView.hideLoading("Upload thất bại", false);
                if (exception != null) {
                    Log.e("Upload Pre", exception.getMessage());
                }
            }

            @Override
            public void onSuccess(String s) {
                if (s.trim().length() == 0) {
                    mView.hideLoading("Upload thất bại", false);
                    return;
                }

                customer.setCustomerAvatar(s);
                getCompositeDisposable().add(mApiService.updateCustomer(customer.getCustomerId(), customer)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io())
                        .subscribe(UpdateProfilePresenter.this::handleUploadSuccess, UpdateProfilePresenter.this::handleUploadError));

            }


        });
    }

    private void handleUploadSuccess(EndPoint<Customer> customerEndPoint) {

        if (customerEndPoint.getStatusCode() == 200) {
            SharedPreferenceUtils.saveUser(mContext, customerEndPoint.getData());
            mView.setNullAvatar();
            mView.hideLoading("Lưu thành công", true);
        } else {
            mView.hideLoading("Có lỗi xảy ra " + customerEndPoint.getMessage(), false);
        }

    }

    private void handleUploadError(Throwable throwable) {
        mView.hideLoading("Upload thất bại", false);
    }

    @Override
    public void clickUpdateAvatar(UpdateProfileActivity updateProfileActivity) {

        CommonUtils.openDialogChooseImage(updateProfileActivity, new CallBackChoosePhoto() {
            @Override
            public void onCamera() {
                if (checkCamPermission(updateProfileActivity))
                    cameraIntent(updateProfileActivity);
            }

            @Override
            public void onGallery() {
                if (checkGaleryPermission(updateProfileActivity))
                    galleryIntent(updateProfileActivity);
            }
        });
    }


    public void cameraIntent(Activity activity) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(intent, AppConstants.REQUEST_CAMERA_PIC);
    }

    public void galleryIntent(Activity activity) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        activity.startActivityForResult(Intent.createChooser(intent, "Select avatar..."), AppConstants.REQUEST_SELECT_FILE);
    }

    private boolean checkCamPermission(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.CAMERA}, AppConstants.REQUEST_CAMERA_PIC);
            return false;
        } else {
            return true;
        }
    }

    private boolean checkGaleryPermission(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, AppConstants.REQUEST_SELECT_FILE);
            return false;
        } else {
            return true;
        }
    }

    private boolean checkDataChange(Bitmap avatar, Customer customer, String fullName, String phone, String email, String birthday, String gender, Province mProvince, District mDistrict, Ward mWard, String address) {

        if (avatar != null) {
            return true;
        }

        if (!customer.getFullname().trim().equalsIgnoreCase(fullName.trim())) {
            return true;
        }

        if (!customer.getPhone().trim().equalsIgnoreCase(phone.trim())) {
            return true;
        }
        if (!customer.getEmail().trim().equalsIgnoreCase(email.trim())) {
            return true;
        }
        String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX";

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, new Locale("vi", "VN"));
        try {
            calendar.setTime(sdf.parse(customer.getBirthday()));
        } catch (ParseException e) {
            e.printStackTrace();
            sdf = new SimpleDateFormat(pattern, new Locale("vi", "VN"));
            try {
                calendar.setTime(sdf.parse(customer.getBirthday()));
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
        String cusBirth = DateTimeUtils.formatDate(calendar.getTime(), DateTimeUtils.DATE_PATTERN_DDMMYY);
        if (!cusBirth.trim().equalsIgnoreCase(birthday.trim())) {
            return true;
        }
        String cusGender = customer.getGender().equals("male") ? "Nam" : "Nữ";
        if (!cusGender.trim().equalsIgnoreCase(gender.trim())) {
            return true;
        }
        if (customer.getProvince().getProvinceid() != mProvince.getProvinceid()) {
            return true;
        }

        if (customer.getDistrict().getDistrictid() != mDistrict.getDistrictid()) {
            return true;
        }

        if (customer.getWard().getWardid() != mWard.getWardid()) {
            return true;
        }

        return !customer.getAddress().trim().equalsIgnoreCase(address.trim());

    }
}
