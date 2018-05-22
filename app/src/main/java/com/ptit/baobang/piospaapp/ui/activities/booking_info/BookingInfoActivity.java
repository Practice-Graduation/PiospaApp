package com.ptit.baobang.piospaapp.ui.activities.booking_info;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ptit.baobang.piospaapp.MainActivity;
import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.Customer;
import com.ptit.baobang.piospaapp.data.model.Room;
import com.ptit.baobang.piospaapp.data.model.ServicePrice;
import com.ptit.baobang.piospaapp.data.network.firebase.BookingTimeFB;
import com.ptit.baobang.piospaapp.ui.base.BaseActivity;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.CommonUtils;
import com.ptit.baobang.piospaapp.utils.DateTimeUtils;
import com.ptit.baobang.piospaapp.utils.SharedPreferenceUtils;

import java.util.Calendar;
import java.util.Date;

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
    @BindView(R.id.txtRoom)
    TextView txtRoom;
    @BindView(R.id.txtTimeStart)
    TextView txtTimeStart;
    @BindView(R.id.txtTimeEnd)
    TextView txtTimeEnd;
    @BindView(R.id.txtAmount)
    TextView txtAmount;
    @BindView(R.id.btnAdd)
    ImageView btnAdd;
    @BindView(R.id.btnRemove)
    ImageView btnRemove;

    @BindView(R.id.txtCustomerName)
    EditText txtCustomerName;
    @BindView(R.id.txtPhone)
    EditText txtPhone;
    @BindView(R.id.txtEmail)
    EditText txtEmail;
    @BindView(R.id.txtNote)
    EditText txtNote;

    private ServicePrice mServicePrice;
    private Date mSelectedDate;
    private Room mSelectedRoom;
    private BookingTimeFB mSelectedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_info);
        addControls();
        addEvents();
    }

    private void addEvents() {

    }

    @OnClick({R.id.btnAdd, R.id.btnRemove, R.id.btnComfirm, R.id.fbGoToCart})
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
                mPresenter.clickConfirm(mServicePrice, mSelectedDate, mSelectedRoom,
                        mSelectedTime, txtAmount.getText().toString(),
                        txtCustomerName.getText().toString(),
                        txtPhone.getText().toString(),
                        txtEmail.getText().toString(),
                        txtNote.getText().toString());
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
    public void attachData(ServicePrice servicePrice, Date selectedDate, Room selectedRoom, BookingTimeFB selectedTime) {
        mServicePrice = servicePrice;
        mSelectedDate = selectedDate;
        mSelectedRoom = selectedRoom;
        mSelectedTime = selectedTime;

        RequestOptions options = new RequestOptions().error(R.drawable.error).placeholder(R.drawable.paceholder);

        String image = "", name = "", time = "";
        if(servicePrice.getService() != null){
            image = servicePrice.getService().getImage();
            name = servicePrice.getService().getServiceName();
//            time = servicePrice.getService().getServiceTime().getTime() + "";
        }else if(servicePrice.getServicePackage() != null){
            image = servicePrice.getServicePackage().getImage();
            name = servicePrice.getServicePackage().getServicePackageName();
//            time = mPresenter.sumTotalTimeOfServicePackage(servicePrice.getServicePackage());
        }

        Glide.with(this).load(image)
                .apply(options)
                .into(img);

        txtServiceName.setText(name);
        String s ="Giá: " + CommonUtils.formatToCurrency(servicePrice.getRetailPrice()) + " - " + time + " phút";
        txtPriceAndTime.setText(s);
        s = "Ngày: " + DateTimeUtils.formatDateDDMMYYYY(mSelectedDate);
        txtDate.setText(s);
        s = "Tên phòng: " + mSelectedRoom.getRoomName();
        txtRoom.setText(s);
        s = "Từ: " + mSelectedTime.getTimeStart();
        txtTimeStart.setText(s);
        s = "Đến: " ;
        Calendar calendar = Calendar.getInstance();
        Date temp = CommonUtils.getDateTimeHHMM(mSelectedDate, mSelectedTime.getTimeStart());
        calendar.setTime(temp);
//        calendar.add(Calendar.MINUTE, Integer.parseInt(servicePrice.getService().getServiceTime().getTime()));
        s += DateTimeUtils.formatTime(calendar.getTime());
        txtTimeEnd.setText(s);
        txtAmount.setText("1");
        Customer customer = SharedPreferenceUtils.getUser(this);
        txtCustomerName.setText(customer.getFullname());
        txtPhone.setText(customer.getPhone());
        txtEmail.setText(customer.getEmail());
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
    public boolean onNavigateUp() {
        onBackPressed();
        return super.onNavigateUp();
    }
}
