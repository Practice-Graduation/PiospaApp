package com.ptit.baobang.piospaapp.ui.activities.update_profile;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.dto.CustomerProfileDTO;
import com.ptit.baobang.piospaapp.data.model.Customer;
import com.ptit.baobang.piospaapp.data.model.District;
import com.ptit.baobang.piospaapp.data.model.Province;
import com.ptit.baobang.piospaapp.data.model.Ward;
import com.ptit.baobang.piospaapp.data.network.api.APIService;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.error.Error;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.ui.listener.CallBackChoosePhoto;
import com.ptit.baobang.piospaapp.ui.listener.CallBackConfirmDialog;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.CommonUtils;
import com.ptit.baobang.piospaapp.utils.DateTimeUtils;
import com.ptit.baobang.piospaapp.utils.InputUtils;
import com.ptit.baobang.piospaapp.utils.SharedPreferenceUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Presenter màn hình cập nhật thông tin người dùng
 *
 * @version 1.0.1
 * @author BaoBang
 */
public class UpdateProfilePresenter extends BasePresenter
        implements IUpdateProfilePresenter {

    private static final String TAG = "UpdateProfilePresenter";
    private Context mContext;
    private IUpdateProfileView mView;
    private Customer mCustomer;

    UpdateProfilePresenter(Context mContext, IUpdateProfileView mView) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @Override
    public void loadData(Context baseContext) {
        Customer customer = SharedPreferenceUtils.getUser(baseContext);
        mView.loadData(customer);
    }


    /**
     * 6. Tỉnh/Thành phố
     *      2. Thực hiện chuyển sang màn hình Tỉnh/Thành phố
     * @param mProvince Tỉnh/Thành phố hiện tại được chọn
     */
    @Override
    public void clickProvince(Province mProvince) {
        mView.onClickProvince(mProvince);
    }

    /**
     * 7. Quận/Huyện
     *      2.Check hạng mục
     *      3. Thực hiện chuyển sang màn hình Quận/Huyện
     * @param mProvince Tỉnh/Thành phố hiện tại được chọn
     * @param mDistrict Quận/Huyện hiện tại được chọn
     */
    @Override
    public void clickDistrict(Province mProvince, District mDistrict) {
        if (mProvince == null) {
            mView.showWarningMessage(Error.ERROR_UPDATE_PROFILE_NOT_CHOOSE_PROVINCE);
            return;
        }
        mView.onClickDistrict(mDistrict);
    }

    /**
     * 8. Phường/Xã
     *      2.Check hạng mục
     *      3. Thực hiện chuyển sang màn hình Phường/Xã
     * @param mDistrict Quận/Huyện hiện tại được chọn
     * @param mWard Phường/Xã hiện tại được chon
     */
    @Override
    public void clickWard(District mDistrict, Ward mWard) {
        if (mDistrict == null) {
            mView.showWarningMessage(Error.ERROR_UPDATE_PROFILE_NOT_CHOOSE_DISTRICT);
            return;
        }
        mView.onClickWard(mWard);
    }


    /**
     * 4. Ngày sinh
     *      a. Người dùng nhấn [Ngày sinh], hiển thị DatePickerDialog
     * @param birthday Ngày sinh hiện tại
     */
    @Override
    public void clickBirthday(String birthday) {
        mView.onClickBirthday(birthday);
    }

    /**
     * 5. Giới tính
     * @param gender giới tính hiện tại được chọn
     */
    @Override
    public void clickGender(String gender) {
        mView.onClickGender(gender);
    }

    /**
     * 3. Xử lý cập nhật thông tin người dùng
     *      2. Xử lý check
     *         a. Check dữ liệu thay đổi
     *         b. Check hạng mục
     * @param customerProfileDTO DTO
     */
    @Override
    public void clickDone(CustomerProfileDTO customerProfileDTO) {
        Customer customer = SharedPreferenceUtils.getUser(mContext);
        // a. Check dữ liệu thay đổi
        if (!checkDataChange(customer, customerProfileDTO)) {
            mView.showMessage(R.string.message_data_not_change);
            return;
        }
        // b. Check hạng mục
        if(customerProfileDTO.getFullName().trim().equals("")){
            mView.showErrorMessage(Error.ERROR_UPDATE_PROFILE_NAME_EMPTY);
            return;
        }
        if (InputUtils.isPhoneValid(customerProfileDTO.getPhone())) {
            mView.showErrorMessage(Error.ERROR_UPDATE_PROFILE_PHONE_INVALID);
            return;
        }
        if (!InputUtils.isValidEmail(customerProfileDTO.getEmail())) {
            mView.showErrorMessage(Error.ERROR_EMAIL_INVALID);
            return;
        }
        if (!checkAddressValid(customerProfileDTO))
            return;
        mView.showConfirm(mContext.getString(R.string.save_change),
                new CallBackConfirmDialog() {
            @Override
            public void DiaglogPositive() {
                updateData(customer, customerProfileDTO);
            }
            @Override
            public void DiaglogNegative() {

            }
        });
    }

    /**
     * 3. Xử lý cập nhật thông tin người dùng
     *      2. Xử lý check
     *         b. Check hạng mục
     *              4. Quận/Huyện
     *              5. Phường/Xã
     *              6. Địa chỉ cụ thể
     * @param customerProfileDTO Thông tin người dùng thay đổi
     * @return boolean
     */
    private boolean checkAddressValid(CustomerProfileDTO customerProfileDTO) {
        if(customerProfileDTO.getProvince() != null){
            if(customerProfileDTO.getDistrict() == null){
                mView.showErrorMessage(Error.ERROR_DISTRICT_EMPTY);
                return false;
            }
            if(customerProfileDTO.getWard() == null){
                mView.showErrorMessage(Error.ERROR_WARD_EMPTY);
                return false;
            }
            if(customerProfileDTO.getAddress().isEmpty()){
                mView.showErrorMessage(Error.ERROR_ADDRESS_EMPTY);
                return false;
            }
        }
        return true;
    }

    /**
     * 3. Gửi yêu cầu cập nhật tài khoản
     *      TH1: Trường hợp người dùng thay đổi avatar, gọi api upload ảnh sau đó gọi api cập nhật thông tin
     *      Th2: Trường hợp người dùng không thay đổi avatar, thì chỉ gọi api cập nhật thông tin
     * @param customer Thông tin người dùng hiện tại
     * @param customerProfileDTO Thông tin người dùng thay đổi
     */
    private void updateData(Customer customer, CustomerProfileDTO customerProfileDTO) {
        customer.setFullName(customerProfileDTO.getFullName());
        customer.setPhone(customerProfileDTO.getPhone());
        customer.setEmail(customerProfileDTO.getEmail());
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(
                DateTimeUtils.DATE_PATTERN_DDMMYY,
                DateTimeUtils.getLocale());
        try {
            calendar.setTime(sdf.parse(customerProfileDTO.getBirthday()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mView.showLoading(mContext.getString(R.string.uploading));
        String birthDay = DateTimeUtils.formatDate(
                calendar.getTime(),
                DateTimeUtils.DATE_PATTERN_DDMMYYTHHMMSSSSSZ);
        customer.setBirthday(birthDay);
        customer.setGender(CommonUtils.getGenderCode(mContext, customerProfileDTO.getGender()));
        customer.setWard(customerProfileDTO.getWard());
        customer.setAddress(customerProfileDTO.getAddress());
        if (customerProfileDTO.getAvatar() == null) {
            // Th2: Trường hợp người dùng không thay đổi avatar, thì chỉ gọi api cập nhật thông tin
            uploadWithoutAvatar(customer);
        } else {
            // TH1: Trường hợp người dùng thay đổi avatar, gọi api upload ảnh sau đó gọi api cập nhật thông tin
            uploadWithAvatar(customer, customerProfileDTO.getAvatar());
        }
    }

    /**
     * 3. Gửi yêu cầu cập nhật tài khoản
     *      Th2: Trường hợp người dùng không thay đổi avatar, thì chỉ gọi api cập nhật thông tin
     * @param customer Thông tin người dùng
     */
    private void uploadWithoutAvatar(Customer customer) {
        getCompositeDisposable().add(mApiService.updateCustomer(customer.getCustomerId(), customer)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleUploadSuccess, this::handleUploadError));
    }

    /**
     * 3. Gửi yêu cầu cập nhật tài khoản
     *      // TH1: Trường hợp người dùng thay đổi avatar, gọi api upload ảnh sau đó gọi api cập nhật thông tin
     * @param customer Thông tin người dùng
     * @param avatar Hình đại diện
     */
    private void uploadWithAvatar(Customer customer, Bitmap avatar) {
        mCustomer = customer;
        try {
            //create a file to write bitmap data
            File file = new File(mContext.getCacheDir(), AppConstants.DEFAULT_FILE_NAME);
           if(file.createNewFile()){
               Bitmap bitmap = CommonUtils.resizeImage(avatar);//Convert bitmap to byte array
               ByteArrayOutputStream bos = new ByteArrayOutputStream();
               bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
               byte[] bitmapdata = bos.toByteArray();
               FileOutputStream fos = new FileOutputStream(file);  //write the bytes in file
               fos.write(bitmapdata);
               fos.flush();
               fos.close();
               RequestBody requestFile =
                       RequestBody.create(MediaType.parse(APIService.MULTIPART_FORM_DATA), file);
               RequestBody data = new MultipartBody.Builder()
                       .setType(MultipartBody.FORM)
                       .addFormDataPart(APIService.PARAM_UPLOAD_AVATAR, file.getName(), requestFile)
                       .build();
               getCompositeDisposable().add(mApiService.uploadImage(data)// c. Api upload ảnh
                       .subscribeOn(Schedulers.computation())
                       .observeOn(AndroidSchedulers.mainThread())
                       .unsubscribeOn(Schedulers.io())
                       .subscribe(this::handleResponseUpload, this::handleUploadError));
           }
        } catch (IOException e) {
            mView.hideLoading(e.getMessage(), false);
        }
    }

    /**
     * d. Check response api upload ảnh
     *  2. Response succeeded
     * @param stringEndPoint EndPoint
     */
    private void handleResponseUpload(EndPoint<String> stringEndPoint) {
        if (stringEndPoint.getStatusCode() == AppConstants.SUCCESS_CODE) {
            mCustomer.setCustomerAvatar(stringEndPoint.getData());
            getCompositeDisposable()
                    .add(mApiService.updateCustomer(mCustomer.getCustomerId(), mCustomer)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io())
                    .subscribe(
                            UpdateProfilePresenter.this::handleUploadSuccess,
                            UpdateProfilePresenter.this::handleUploadError));
        } else {
            mView.hideLoading(stringEndPoint.getMessage(), false);
        }
    }


    /**
     * b. Check response api cập nhật thông tin tài khoản
     *       2. Response succeeded
     *  4. Lưu thông tin người dùng vào Sharepreferences
     * @param customerEndPoint EndPoint
     */
    private void handleUploadSuccess(EndPoint<Customer> customerEndPoint) {
        // 2. Response succeeded
        if (customerEndPoint.getStatusCode() == AppConstants.SUCCESS_CODE) {
            // 4. Lưu thông tin người dùng vào Sharepreferences
            SharedPreferenceUtils.saveUser(mContext, customerEndPoint.getData());
            mView.setNullAvatar();
            mView.loadAvatar(customerEndPoint.getData().getCustomerAvatar());
            // 5. Thông báo cập nhật thành công
            mView.hideLoading(mContext.getString(R.string.save_successed), true);
        } else {
            mView.hideLoading(customerEndPoint.getMessage(), false);
        }

    }

    /**
     *
     *  b. Check response api cập nhật thông tin tài khoản
     *      1. Response error
     *  d. Check response api upload ảnh
     *     1. Response error
     * @param throwable Throwable
     */
    private void handleUploadError(Throwable throwable) {
        Log.e(TAG, throwable.getMessage());
        mView.hideLoading(Error.ERROR_UPDATE_PROFILE_FAILED, false);
    }

    /**
     * 2. Cập nhật thông tin người dùng
     *      2. Hình lớn + 3. Hình nhỏ
     *          1. Hiển thị ban đầu
     *          2. Xử lý chọn máy ảnh
     *          3. Xử lý chọn bộ sưu tập
     * @param updateProfileActivity Màn hình cập nhật thông tin
     */
    @Override
    public void clickUpdateAvatar(UpdateProfileActivity updateProfileActivity) {

        CommonUtils.openDialogChooseImage(updateProfileActivity, new CallBackChoosePhoto() {
            @Override
            public void onCamera() {
                if (mView.checkCamPermission())
                    mView.cameraIntent();
            }

            @Override
            public void onGallery() {
                if (mView.checkGalleryPermission())
                    mView.galleryIntent();
            }
        });
    }

    /**
     * Kiểm tra hai chuỗi có giống nhau hay không
     * @param text1 chuỗi thứ nhất
     * @param text2 chuỗi thứ hai
     * @return boolean
     */
    private boolean isTextEqual(String text1, String text2) {
        text1 = text1.trim();
        text2 = text2.trim();
        return !text1.equalsIgnoreCase(text2);
    }

    /**
     *   3. Xử lý cập nhật thông tin người dùng
     *      2. Xử lý check
     *         a. Check dữ liệu thay đổi
     * @param customer Customer
     * @param customerProfileDTO CustomerProfileDTO
     * @return boolean
     */
    private boolean checkDataChange(Customer customer, CustomerProfileDTO customerProfileDTO) {
        String customerGender = CommonUtils.getGenderText(mContext, customer.getGender());
        String customerBirthday = DateTimeUtils.getBirthday(customer.getBirthday());
        return customerProfileDTO.getAvatar() != null || // Hình đại diện
                isTextEqual(customer.getFullName(), customerProfileDTO.getFullName()) || // Họ và tên
                isTextEqual(customer.getPhone(), customerProfileDTO.getPhone()) || // Số điện thoại
                isTextEqual(customer.getEmail(), customerProfileDTO.getEmail()) || // Email
                isTextEqual(customerBirthday, customerProfileDTO.getBirthday()) || // Ngày sinh
                isTextEqual(customerGender, customerProfileDTO.getGender()) || // giới tính
                checkAddressChange(customer, customerProfileDTO) || // Địa chỉ
                isTextEqual(customer.getAddress(), customerProfileDTO.getAddress()); // địa chỉ cụ thể

    }

    /**
     * 3. Xử lý cập nhật thông tin người dùng
     *      2. Xử lý check
     *       a. Check dữ liệu thay đổi
     * @param customer Thông tin người dùng
     * @param customerProfileDTO Thông tin người dùng thay đổi
     * @return boolean
     */
    private boolean checkAddressChange(Customer customer, CustomerProfileDTO customerProfileDTO) {
        if(customer.getWard() == null) {
            return customerProfileDTO.getProvince() != null ||
                    customerProfileDTO.getDistrict() != null ||
                    customer.getWard() != null;
        }else {
            Ward customerWard = customer.getWard();
            District customerDistrict = customerWard.getDistrict();
            return !customerDistrict.getProvince().equals(customerProfileDTO.getProvince()) ||
                    !customerDistrict.equals(customerProfileDTO.getDistrict()) ||
                    !customerWard.equals(customerProfileDTO.getWard());
        }
    }
}
