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
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.dto.CustomerProfileDTO;
import com.ptit.baobang.piospaapp.data.model.Customer;
import com.ptit.baobang.piospaapp.data.model.District;
import com.ptit.baobang.piospaapp.data.model.Province;
import com.ptit.baobang.piospaapp.data.model.Ward;
import com.ptit.baobang.piospaapp.ui.base.BaseActivity;
import com.ptit.baobang.piospaapp.ui.dialogs.district.DistrictActivity;
import com.ptit.baobang.piospaapp.ui.dialogs.province.ProvinceActivity;
import com.ptit.baobang.piospaapp.ui.dialogs.ward.WardActivity;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.CommonUtils;
import com.ptit.baobang.piospaapp.utils.DateTimeUtils;
import com.ptit.baobang.piospaapp.utils.KeyBundleConstant;
import com.ptit.baobang.piospaapp.utils.RequestCodeConstant;

import java.io.FileDescriptor;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Màn hình cập nhật thông tin người dùng
 *
 * @version 1.0.1
 * @author BaoBang
 */
public class UpdateProfileActivity extends BaseActivity<UpdateProfilePresenter>
        implements IUpdateProfileView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.imgAvatarBackground)
    ImageView imgAvatarBackground;

    @BindView(R.id.imgAvatar)
    ImageView imgAvatar; // hình đai diện tròn

    @BindView(R.id.edtFullName)
    EditText edtFullName;

    @BindView(R.id.edtPhone)
    EditText edtPhone;

    @BindView(R.id.edtEmail)
    EditText edtEmail;

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

    @BindView(R.id.edtAddress)
    EditText edtAddress;

    CustomerProfileDTO customerProfileDTO; // Lưu dữu thông tin người dùng thay đổi

    /**
     * 1. Hiển thị ban đầu
     * 1. Lấy thông tin người dùng
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        mPresenter = new UpdateProfilePresenter(this, this);
        setSuportToolbar();
        // 1. Lấy thông tin người dùng
        mPresenter.loadData(getApplicationContext());
    }

    /**
     * 2. Cập nhật thông tin người dùng
     *      2. Hình lớn + 3. Hình nhỏ
     *      4. Ngày sinh
     *      5. Giới tính
     *      6. Tỉnh/Thành phố
     *      7. Quận/Huyện
     *      8. Phường/Xã
     *  3. Xử lý cập nhật thông tin người dùng
     *
     * @param view view được click
     */
    @OnClick({R.id.imgAvatarBackground,
            R.id.imgAvatar,
            R.id.txtProvince,
            R.id.txtDistrict,
            R.id.txtWard,
            R.id.txtBirthDay,
            R.id.txtGender,
            R.id.btnUpdate})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgAvatarBackground: // 2. Hình lớn + 3. Hình nhỏ
            case R.id.imgAvatar:           //
                mPresenter.clickUpdateAvatar(this);
                break;
            case R.id.txtProvince: // 6. Tỉnh/Thành phố
                mPresenter.clickProvince(customerProfileDTO.getProvince());
                break;
            case R.id.txtDistrict: // 7. Quận/Huyện
                mPresenter.clickDistrict(customerProfileDTO.getProvince(),
                        customerProfileDTO.getDistrict());
                break;
            case R.id.txtWard: // 8. Phường/Xã
                mPresenter.clickWard(customerProfileDTO.getDistrict(),
                        customerProfileDTO.getWard());
                break;
            case R.id.txtBirthDay: // 4. Ngày sinh
                customerProfileDTO.setBirthday(txtBirthDay.getText().toString());
                mPresenter.clickBirthday(customerProfileDTO.getBirthday());
                break;
            case R.id.txtGender: // 5. Giới tính
                customerProfileDTO.setGender(txtGender.getText().toString());
                mPresenter.clickGender(customerProfileDTO.getGender());
                break;
            // 3. Xử lý cập nhật thông tin người dùng
            //      1. Nhấn nút cập nhật thì xử lý cập nhật
            case R.id.btnUpdate:
                setDataToDTO();
                mPresenter.clickDone(customerProfileDTO);
                break;
        }
    }

    /**
     *  3. Xử lý cập nhật thông tin người dùng
     */
    private void setDataToDTO() {
        customerProfileDTO.setFullName(edtFullName.getText().toString());
        customerProfileDTO.setPhone(edtPhone.getText().toString());
        customerProfileDTO.setEmail(edtEmail.getText().toString());
        customerProfileDTO.setBirthday(txtBirthDay.getText().toString());
        customerProfileDTO.setGender(txtGender.getText().toString());
        customerProfileDTO.setAddress(edtAddress.getText().toString());

    }

    /**
     * 1. Hiển thị ban đầu
     * Phương thức sử dụng để cài đặt toolbar
     */
    private void setSuportToolbar() {
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitle(R.string.update_profile);
        centerToolbarTitle(mToolbar, 0);
    }

    /**
     * Phương thúc sử dụng khi click vào button back home
     *
     * @return boolean
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * 1. Hiển thị ban đầu
     * 2. Thực hiện khởi tạo màn hình ban đầu
     *
     * @param customer Thông tin người dùng
     */
    @Override
    public void loadData(Customer customer) {
        customerProfileDTO = getCustomerDTO(customer);

        CommonUtils.loadAvatar(imgAvatarBackground, customer.getCustomerAvatar());
        CommonUtils.loadRoundAvatar(imgAvatar, customer.getCustomerAvatar());

        edtFullName.setText(customer.getFullName());
        edtPhone.setText(customer.getPhone());
        edtEmail.setText(customer.getEmail());
        txtBirthDay.setText(DateTimeUtils.getBirthday(customer.getBirthday()));
        txtGender.setText(CommonUtils.getGenderText(this, customer.getGender()));


        if (customer.getWard() != null) {
            District district = customer.getWard().getDistrict();
            Province province = district.getProvince();
            txtProvince.setText(province.getName());
            txtDistrict.setText(district.getName());
            txtWard.setText(customer.getWard().getName());
        } else {
            txtProvince.setText("");
            txtDistrict.setText("");
            txtWard.setText("");
        }
        edtAddress.setText(customer.getAddress());
    }

    /**
     * @param customer Thông tin người dùng
     * @return CustomerProfileDTO
     */
    private CustomerProfileDTO getCustomerDTO(Customer customer) {
        CustomerProfileDTO customerProfileDTO = new CustomerProfileDTO();
        customerProfileDTO.setFullName(customer.getFullName());
        customerProfileDTO.setPhone(customer.getPhone());
        customerProfileDTO.setEmail(customer.getEmail());
        customerProfileDTO.setBirthday(customer.getBirthday());
        customerProfileDTO.setGender(customer.getGender());
        Ward ward = customer.getWard();
        if (ward != null) {
            customerProfileDTO.setWard(ward);
            customerProfileDTO.setDistrict(ward.getDistrict());
            customerProfileDTO.setProvince(ward.getDistrict().getProvince());
        }
        return customerProfileDTO;
    }

    /**
     * 6. Tỉnh/Thành phố
     *      2. Thực hiện chuyển sang màn hình Tỉnh/Thành phố
     *         a. Đăng kí sự kiện lắng nghe kết quả trả về
     * @param mProvince Tỉnh/Thành phố hiện tại được chọn
     */
    @Override
    public void onClickProvince(Province mProvince) {
        // 2. Thực hiện chuyển sang màn hình Tỉnh/Thành phố
        //      a. Đăng kí sự kiện lắng nghe kết quả trả về
        Intent intent = new Intent(this, ProvinceActivity.class);
        intent.putExtra(KeyBundleConstant.PROVINCE, mProvince);
        startActivityForResult(intent, RequestCodeConstant.REQUEST_CODE_PROVINCE);
    }

    /**
     * 7. Quận/Huyện
     *      3. Thực hiện chuyển sang màn hình Quận/Huyện
     *          a. Đăng kí sự kiện lắng nghe kết quả trả về
     * @param mDistrict Quận/Huyện hiện tại được chọn
     */
    @Override
    public void onClickDistrict(District mDistrict) {
        // 3. Thực hiện chuyển sang màn hình Quận/Huyện
        //      a. Đăng kí sự kiện lắng nghe kết quả trả về
        Intent intent = new Intent(this, DistrictActivity.class);
        intent.putExtra(KeyBundleConstant.PROVINCE, customerProfileDTO.getProvince());
        intent.putExtra(KeyBundleConstant.DISTRICT, mDistrict);
        startActivityForResult(intent, RequestCodeConstant.REQUEST_CODE_DISTRICT);
    }

    /**
     * 8. Phường/Xã
     *      3. Thực hiện chuyển sang màn hình Phường/Xã
     *          a. Đăng kí sự kiện lắng nghe kết quả trả về
     * @param mWard Phường/Xã hiện tại được chọn
     */
    @Override
    public void onClickWard(Ward mWard) {
        // 3. Thực hiện chuyển sang màn hình Phường/Xã
        //      a. Đăng kí sự kiện lắng nghe kết quả trả về
        Intent intent = new Intent(this, WardActivity.class);
        intent.putExtra(KeyBundleConstant.DISTRICT, customerProfileDTO.getDistrict());
        intent.putExtra(KeyBundleConstant.WARD, mWard);
        startActivityForResult(intent, RequestCodeConstant.REQUEST_CODE_WARD);
    }

    /**
     * 5. Giới tính
     *      1. Hiển thị ban đầu
     *      2. Xử lý chọn [Hủy]
     *      3. Xư lý chọn Đồng ý
     * @param gender Giới tính hiện tại được chọn
     */
    @Override
    public void onClickGender(String gender) {
        boolean isMale = gender.trim().toLowerCase().equalsIgnoreCase(getString(R.string.text_male));
        // 1. Hiển thị ban đầu
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle(R.string.choose_gender);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.gender_layout, null, false);

        Button btnOk = dialogView.findViewById(R.id.btnOk);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        RadioButton rbMale = dialogView.findViewById(R.id.rbMale);
        RadioButton rbFeMale = dialogView.findViewById(R.id.rbFeMale);
        rbMale.setChecked(isMale);
        rbFeMale.setChecked(!isMale);
        btnOk.setText(getString(R.string.ok));
        btnCancel.setText(getString(R.string.cancel));

        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();

        //3. Xư lý chọn Đồng ý
        btnOk.setOnClickListener(v -> doClickOkInGenderDialog(alertDialog, rbMale));
        // 2. Xử lý chọn [Hủy]
        btnCancel.setOnClickListener(v -> alertDialog.dismiss());

        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    /**
     * 5. Giới tính
     *      3. Xư lý chọn Đồng ý
     * @param alertDialog dialog gender
     * @param rbMale radio button male
     */
    private void doClickOkInGenderDialog(AlertDialog alertDialog, RadioButton rbMale) {
        if (rbMale.isChecked()) {
            txtGender.setText(R.string.text_male);
        } else {
            txtGender.setText(R.string.text_female);
        }
        alertDialog.dismiss();
    }

    /**
     * 4. Ngày sinh
     *      a. Người dùng nhấn [Ngày sinh], hiển thị DatePickerDialog
     *      b. Xử lý chọn ngày sinh
     * @param birthday Ngày sinh hiện tại được chọn
     */
    @Override
    public void onClickBirthday(String birthday) {
        // Hiển thị [Ngày sinh] lên DatePickerDialog
        final Calendar calendar = Calendar.getInstance();
        if (!birthday.trim().isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat(
                    AppConstants.DATE_FORMAT,
                    DateTimeUtils.getLocale());
            try {
                Date date = sdf.parse(birthday.trim());
                calendar.setTime(date);// all done
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        calendar.add(Calendar.DATE, -1);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                // Nhấn chọn OK thì cập nhật lại giao diện với ngày được chọn
                (datePicker, year, monthOfYear, dayOfMonth) -> {
                    String newBirthday = CommonUtils.formatDate(dayOfMonth, monthOfYear + 1, year);
                    txtBirthDay.setText(newBirthday);
                    customerProfileDTO.setBirthday(newBirthday);
                    calendar.set(Calendar.DATE, dayOfMonth);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.YEAR, year);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE));
        datePickerDialog.show();
    }

    /**
     * Cập nhật hình đại diện khi chọn hình từ bộ sưu tập
     * @param avatar Hình đại điện hiện tại được chọn
     */
    @Override
    public void updateUIAvatar(Bitmap avatar) {
        CommonUtils.loadAvatar(imgAvatarBackground, avatar);
        CommonUtils.loadRoundAvatar(imgAvatar, avatar);
    }

    /**
     * Cập nhật lại avatar khi upload thành công
     */
    @Override
    public void setNullAvatar() {
        customerProfileDTO.setAvatar(null);
    }

    /**
     * Cập nhật hình đại diện khi chụp mới
     * @param customerAvatar Hình đại diện hiện tại được chọn
     */
    @Override
    public void loadAvatar(String customerAvatar) {
        CommonUtils.loadAvatar(imgAvatarBackground, customerAvatar);
        CommonUtils.loadRoundAvatar(imgAvatar, customerAvatar);
    }

    /**
     * 2. Xử lý chọn máy ảnh
     * Mở màn hình chụp ảnh
     */
    @Override
    public void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, RequestCodeConstant.REQUEST_CAMERA_PIC);
    }

    /**
     * 3. Xử lý chọn bộ sưu tập
     * Mở màn hình bộ sưu tập
     */
    @Override
    public void galleryIntent() {
        Intent intent = new Intent();
        intent.setType(AppConstants.IMAGE_PATH);
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        getString(R.string.select_avatar)),
                RequestCodeConstant.REQUEST_SELECT_FILE);
    }

    /**
     * 2. Xử lý chọn máy ảnh
     *      b. Check quyền máy ảnh
     * Kiểm tra quyền máy ảnh
     * @return boolean
     */
    @Override
    public boolean checkCamPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(
                    this, new String[]{
                            android.Manifest.permission.CAMERA},
                    RequestCodeConstant.REQUEST_CAMERA_PIC);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 3. Xử lý chọn bộ sưu tập
     *      b. Check quyền truy cập bộ sưu tập
     * Kiểm quyền truy cập bộ nhớ
     * @return boolean
     */
    @Override
    public boolean checkGalleryPermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    RequestCodeConstant.REQUEST_SELECT_FILE);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 3. Xử lý chọn bộ sưu tập
     * @param activity Màn hình hiện tại
     * @param uri Uri của ảnh
     * @return Hình lấy từ Uri
     * @throws IOException Lỗi truy xuất file
     */
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

    /**
     * 2. Xử lý chọn máy ảnh
     *      c. Lắng nghe kết quả cấp quyền
     *  3. Xử lý chọn bộ sưu tập
     *      c. Lắng nghe kết quả cấp quyền
     * @param requestCode Mã code yêu cầu sử dụng quyền
     * @param permissions Danh sách quyền yêu câu
     * @param grantResults Danh sách quyền được cấp
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case RequestCodeConstant.REQUEST_CAMERA_PIC:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED) {
                        cameraIntent();
                    }
                } else {
                    showMessage(getString(R.string.message), getString(R.string.require_camera_permission), SweetAlertDialog.WARNING_TYPE);
                }
                break;
            case RequestCodeConstant.REQUEST_SELECT_FILE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    galleryIntent();
                } else {
                    showMessage(getString(R.string.message), getString(R.string.require_store_permission), SweetAlertDialog.WARNING_TYPE);
                }
                break;
        }
    }

    /**
     * 2. Xử lý chọn máy ảnh
     *     d. - Đăng kí sự kiện lắng nghe kết quả trả về
     * 3. Xử lý chọn bộ sưu tập
     *     d. - Xử lý lắng nghe kết quả trả về
     * 5. Tỉnh/Thành phố
     *      2. Thực hiện chuyển sang màn hình Tỉnh/Thành phố
     *          b. Xử lý lắng nghe kết quả trả về
     * 6. Quận/Huyện
     *      3. Thực hiện chuyển sang màn hình Quận/Huyện
     *           b. Xử lý lắng nghe kết quả trả về
     * 7. Phường/Xã
     *      3. Thực hiện chuyển sang màn hình Phường/Xã
     *          b. Xử lý lắng nghe kết quả trả về
     * @param requestCode Mã đăng kí lắng nghe sự kiện dữ liệu trả về
     * @param resultCode Mã kết quả trả về thành công
     * @param data Dữ liệu trả về
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //  5. Tỉnh/Thành phố
                //      b. Xử lý lắng nghe kết quả trả về
                case RequestCodeConstant.REQUEST_CODE_PROVINCE:
                    provinceResponse(data);
                    break;
                //  6. Quận/Huyện
                //      b. Xử lý lắng nghe kết quả trả về
                case RequestCodeConstant.REQUEST_CODE_DISTRICT:
                    districtResponse(data);
                    break;
                //  7. Phường/Xã
                //      b. Xử lý lắng nghe kết quả trả về
                case RequestCodeConstant.REQUEST_CODE_WARD:
                    wardResponse(data);
                    break;
                 // 2. Xử lý chọn máy ảnh
                //      d. - Đăng kí sự kiện lắng nghe kết quả trả về
                case RequestCodeConstant.REQUEST_CAMERA_PIC:
                    takePictureResponse(data);
                    break;
                // 3. Xử lý chọn bộ sưu tập
                //      d. - Xử lý lắng nghe kết quả trả về
                case RequestCodeConstant.REQUEST_SELECT_FILE:
                    selectPictureResponse(data);
                    break;
            }
        }
    }

    /**
     * 3. Xử lý chọn bộ sưu tập
     * Xử lý lấy dữ liệu từ chọn hình từ bộ sưu tập
     * @param data Màn hình lưu dữ liệu
     */
    private void selectPictureResponse(Intent data) {
        Uri selectedImageUri = data.getData();
        try {
            customerProfileDTO.setAvatar(getBitmapFromUri(this, selectedImageUri));
            updateUIAvatar(customerProfileDTO.getAvatar());
        } catch (IOException e) {
            customerProfileDTO.setAvatar(null);
        }
    }

    /**
     * 2. Xử lý chọn máy ảnh
     * Xử lý lấy dữ liệu sau khi chụp ảnh
     * @param data Màn hình lưu dữ liệu
     */
    private void takePictureResponse(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            customerProfileDTO.setAvatar((Bitmap) bundle.get("data"));
            updateUIAvatar(customerProfileDTO.getAvatar());
        } else {
            customerProfileDTO.setAvatar(null);
        }
    }

    /**
     * 7. Phường/Xã
     * Xử lý lấy dữ liệu phường/xã trả về
     * @param data Màn hình lưu dữ liệu
     */
    private void wardResponse(Intent data) {
        Ward ward = (Ward) data.getSerializableExtra(KeyBundleConstant.WARD);
        txtWard.setText(ward.getName());
        customerProfileDTO.setWard(ward);
    }

    /**
     * 6. Quận/Huyện
     * Xử lý lấy dữ liệu quận/huyện trả về
     * @param data Màn hình lưu dữ liệu
     */
    private void districtResponse(Intent data) {
        District district =
                (District) data.getSerializableExtra(KeyBundleConstant.DISTRICT);
        customerProfileDTO.setDistrict(district);
        txtDistrict.setText(district.getName());
        txtWard.setText("");
        customerProfileDTO.setWard(null);
        txtWard.setClickable(true);
    }

    /**
     * 5. Tỉnh/Thành phố
     * Xử lý lấy dữ liệu tỉnh/thành phố trả về
     * @param data Màn hình lưu dữ liệu
     */
    private void provinceResponse(Intent data) {
        Province province =
                (Province) data.getSerializableExtra(KeyBundleConstant.PROVINCE);
        customerProfileDTO.setProvince(province);
        txtProvince.setText(province.getName());
        txtDistrict.setText("");
        txtWard.setText("");
        customerProfileDTO.setDistrict(null);
        customerProfileDTO.setWard(null);
        txtDistrict.setClickable(true);
        txtWard.setClickable(false);
    }
}
