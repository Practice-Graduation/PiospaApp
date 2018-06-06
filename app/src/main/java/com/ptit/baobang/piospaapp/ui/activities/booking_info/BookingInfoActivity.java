package com.ptit.baobang.piospaapp.ui.activities.booking_info;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.ServicePrice;
import com.ptit.baobang.piospaapp.ui.dialogs.booking_time.BookingTimeActivity;
import com.ptit.baobang.piospaapp.ui.activities.main.MainActivity;
import com.ptit.baobang.piospaapp.ui.base.BaseActivity;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.CommonUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BookingInfoActivity extends BaseActivity implements IBookingInfoView{

    private BookingInfoPresenter mPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.fbGoToCart)
    FloatingActionButton fbGoToCart;

    @BindView(R.id.txtServiceName)
    TextView txtServiceName;
    @BindView(R.id.txtPrice)
    TextView txtPriceAndTime;
    @BindView(R.id.txtDate)
    TextView txtDate;
    @BindView(R.id.txtTimeStart)
    TextView txtTimeStart;
    @BindView(R.id.txtAmount)
    TextView txtAmount;
    @BindView(R.id.btnAdd)
    ImageView btnAdd;
    @BindView(R.id.btnRemove)
    ImageView btnRemove;

    private ServicePrice mServicePrice;
    private Date mSelectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_info);
        addControls();
        addEvents();
    }

    private void addEvents() {

    }

    @OnClick({R.id.btnAdd, R.id.btnRemove,
            R.id.btnComfirm, R.id.fbGoToCart,
            R.id.txtDate, R.id.txtTimeStart})
    void onClick(View view){
        switch (view.getId()){
            case R.id.fbGoToCart:
                mPresenter.clickFloatButtonCart();
                break;
            case R.id.btnAdd:
                mPresenter.clickAdd(txtAmount.getText().toString());
                break;
            case R.id.btnRemove:
                mPresenter.clickRemove(txtAmount.getText().toString());
                break;
            case R.id.btnComfirm:
                mPresenter.clickConfirm(mServicePrice, mSelectedDate, txtAmount.getText().toString());
                break;
            case R.id.txtDate:
                mPresenter.clickSelectDate();
                break;
            case R.id.txtTimeStart:
                mPresenter.clickSelectTime(mSelectedDate);
                break;
        }
    }

    private void addControls() {
        ButterKnife.bind(this);
        mPresenter = new BookingInfoPresenter(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mPresenter.loadDate(getIntent());
    }

    @Override
    public void attachData(ServicePrice servicePrice) {
        mServicePrice = servicePrice;
        RequestOptions options = new RequestOptions()
                .error(R.drawable.error)
                .placeholder(R.drawable.paceholder);

        String image = "", name = "", time = "";
        if(servicePrice.getService() != null){
            image = servicePrice.getService().getImage();
            name = servicePrice.getService().getServiceName();
            time = servicePrice.getService().getServiceTime().getTime() + "";
        }else if(servicePrice.getServicePackage() != null){
            image = servicePrice.getServicePackage().getImage();
            name = servicePrice.getServicePackage().getServicePackageName();
        }

        Glide.with(this).load(image)
                .apply(options)
                .into(img);

        txtServiceName.setText(name);
        String s = getString(R.string.price) + CommonUtils.formatToCurrency(servicePrice.getRetailPrice()) + " - " + time + " phút";
        txtPriceAndTime.setText(s);
//        s = "Ngày: " + DateTimeUtils.formatDateDDMMYYYY(mSelectedDate);
//        txtDate.setText(s);
//        s = "Từ: " + mSelectedTime.getTimeStart();
//        txtTimeStart.setText(s);
//        s = "Đến: " ;
////        Calendar calendar = Calendar.getInstance();
////        Date temp = CommonUtils.getDateTimeHHMM(mSelectedDate, mSelectedTime.getTimeStart());
////        calendar.setTime(temp);
//////        calendar.add(Calendar.MINUTE, Integer.parseInt(servicePrice.getService().getServiceTime().getTime()));
////        s += DateTimeUtils.formatTime(calendar.getTime());
//////        txtTimeEnd.setText(s);
        txtAmount.setText("1");
//        Customer customer = SharedPreferenceUtils.getUser(this);
//        txtCustomerName.setText(customer.getFullname());
//        txtPhone.setText(customer.getPhone());
//        txtEmail.setText(customer.getEmail());
    }

    @Override
    public void setAmount(String s) {
        txtAmount.setText(s);
    }

    @Override
    public void openFramentCart() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(AppConstants.FRAGMENT, AppConstants.CART_INDEX);
        startActivity(intent);
    }

    @Override
    public void openDatePicker() {
        final Calendar calendar = Calendar.getInstance();

        if (!txtDate.getText().toString().trim().isEmpty()) {

            SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.DATE_FORMAT, Locale.ENGLISH);

            try {
                Date date = sdf.parse(txtDate.getText().toString().trim());
                calendar.setTime(date);// all done
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (datePicker, year, monthOfYear, dayOfMonth) -> {
                    txtDate.setText(new StringBuilder(getString(R.string.date_booking) + ": " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year));
                    calendar.set(Calendar.DATE, dayOfMonth);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.YEAR, year);
                    mSelectedDate = calendar.getTime();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE));
        datePickerDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AppConstants.REQUEST_CODE){
            if(resultCode == RESULT_OK){
                String timeStart = data.getStringExtra(AppConstants.TIME_SELECRED);
                txtTimeStart.setText(new StringBuilder(getString(R.string.time_booking) + ": " + timeStart));
                mSelectedDate = CommonUtils.getDateTime(mSelectedDate, timeStart);
            }
        }
    }

    @Override
    public void openTimePicker(Date date ) {
        Intent intent = new Intent(this, BookingTimeActivity.class);
        intent.putExtra(AppConstants.DATE_SELECTED, date);
        intent.putExtra(AppConstants.SERVICE_PRICE_ID, mServicePrice);
        startActivityForResult(intent, AppConstants.REQUEST_CODE);
    }

    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return super.onNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return (super.onOptionsItemSelected(item));
    }
}
