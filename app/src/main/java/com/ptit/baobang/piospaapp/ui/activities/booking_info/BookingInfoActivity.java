package com.ptit.baobang.piospaapp.ui.activities.booking_info;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.Room;
import com.ptit.baobang.piospaapp.data.model.ServicePrice;
import com.ptit.baobang.piospaapp.ui.activities.main.MainActivity;
import com.ptit.baobang.piospaapp.ui.base.BaseActivity;
import com.ptit.baobang.piospaapp.ui.dialogs.room.RoomActivity;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.CommonUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class BookingInfoActivity extends BaseActivity<BookingInfoPresenter> implements IBookingInfoView {

    @BindView(R.id.root)
    CoordinatorLayout root;
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

    @BindView(R.id.txtRoom)
    TextView txtRoom;

    private ServicePrice mServicePrice;
    private Date mSelectedDate;
    private String mTimeSelected = "";
    private Room mRoomSelected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_info);
        hideKeyboardOutside(root);
        addControls();
        addEvents();
    }

    private void addEvents() {

    }

    @OnClick({R.id.btnAdd, R.id.btnRemove,
            R.id.btnComfirm, R.id.fbGoToCart,
            R.id.txtDate, R.id.txtTimeStart, R.id.txtRoom})
    void onClick(View view) {
        switch (view.getId()) {
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
                mPresenter.clickConfirm(mServicePrice, mSelectedDate, mTimeSelected, txtAmount.getText().toString(), mRoomSelected);
                break;
            case R.id.txtDate:
                mPresenter.clickSelectDate();
                break;
            case R.id.txtTimeStart:
                mPresenter.clickSelectTime(mSelectedDate);
                break;
            case R.id.txtRoom:
                mPresenter.clickRoom(mSelectedDate, mTimeSelected, mRoomSelected);
                break;
        }
    }

    private void addControls() {
        mPresenter = new BookingInfoPresenter(this, this);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(getString(R.string.booking) + "       ");
        centerToolbarTitle(toolbar, 40);
        mPresenter.loadDataFromBunble(getIntent());
    }

    @Override
    public void attachData(ServicePrice servicePrice) {
        mServicePrice = servicePrice;
        RequestOptions options = new RequestOptions()
                .error(R.drawable.error)
                .placeholder(R.drawable.paceholder);

        String image = "", name = "", time = "";
        if (servicePrice.getService() != null) {
            image = servicePrice.getService().getImage();
            name = servicePrice.getService().getServiceName();
            time = servicePrice.getService().getServiceTime().getTime() + "";
        } else if (servicePrice.getServicePackage() != null) {
            image = servicePrice.getServicePackage().getImage();
            name = servicePrice.getServicePackage().getServicePackageName();
        }

        Glide.with(this).load(image)
                .apply(options)
                .into(img);

        txtServiceName.setText(name);
        String s = getString(R.string.price) + ": " + CommonUtils.formatToCurrency(servicePrice.getAllPrice()) + " - " + time + " " + getString(R.string.minute);
        txtPriceAndTime.setText(s);
        txtAmount.setText("1");
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

            if(mSelectedDate != null){
                calendar.setTime(mSelectedDate);// all done
            }
        }

        Calendar calendar1 = Calendar.getInstance();

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
        datePickerDialog.getDatePicker().setMinDate(calendar1.getTime().getTime());
        datePickerDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstants.REQUEST_ROOM) {
            if (resultCode == RESULT_OK) {
                mRoomSelected = (Room) data.getSerializableExtra(AppConstants.ROOM_SELECTED);
                txtRoom.setText(mRoomSelected.getRoomName());
            }
        }
    }

    @Override
    public void openTimePicker(Date date) {
        Calendar mcurrentTime = Calendar.getInstance();

        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        if (mTimeSelected.length() != 0) {
            try {
                String[] items = mTimeSelected.split(":");
                hour = Integer.parseInt(items[0]);
                minute = Integer.parseInt(items[1]);
            } catch (Exception e) {
                hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                minute = mcurrentTime.get(Calendar.MINUTE);
            }
        }
        if(hour < 8 || hour >= 22){
            hour = 8;
            minute = 30;
        }
        CustomTimePicker mTimePicker;
        mTimePicker = new CustomTimePicker(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                mTimeSelected = selectedHour + ":" + (selectedMinute < 10 ? ("0" + selectedMinute) : selectedMinute);
                txtTimeStart.setText(new StringBuilder(getString(R.string.time_booking) + ": " + mTimeSelected));
                mSelectedDate = CommonUtils.getDateTime(mSelectedDate, mTimeSelected);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Chọn thời gian");


        mTimePicker.setMax(21, 59);
        mTimePicker.setMin(8, 30);
        mTimePicker.show();
    }

    @Override
    public void openRoom(List<Room> data) {
        ArrayList<Room> list = new ArrayList<>(data);
        Intent intent = new Intent(this, RoomActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", list);
        bundle.putSerializable(AppConstants.ROOM_SELECTED, mRoomSelected);
        intent.putExtras(bundle);
        startActivityForResult(intent, AppConstants.REQUEST_ROOM);
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

    private class CustomTimePicker extends TimePickerDialog {
        private int minHour = -1;
        private int minMinute = -1;

        private int maxHour = 25;
        private int maxMinute = 25;

        private int currentHour = 0;
        private int currentMinute = 0;

        private Calendar calendar = Calendar.getInstance();
        private DateFormat dateFormat;


        public CustomTimePicker(Context context, OnTimeSetListener callBack, int hourOfDay, int minute, boolean is24HourView) {
            super(context, callBack, hourOfDay, minute, is24HourView);
            currentHour = hourOfDay;
            currentMinute = minute;
            dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
            fixSpinner(context, hourOfDay, minute, is24HourView);

            try {
                Class<?> superclass = getClass().getSuperclass();
                Field mTimePickerField = superclass.getDeclaredField("mTimePicker");
                mTimePickerField.setAccessible(true);
                TimePicker mTimePicker = (TimePicker) mTimePickerField.get(this);
                mTimePicker.setOnTimeChangedListener(this);
            } catch (NoSuchFieldException e) {
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            }
        }

        public void setMin(int hour, int minute) {
            minHour = hour;
            minMinute = minute;
        }

        public void setMax(int hour, int minute) {
            maxHour = hour;
            maxMinute = minute;
        }

        @Override
        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

            boolean validTime = true;
            if (hourOfDay < minHour || (hourOfDay == minHour && minute < minMinute)) {
                validTime = false;
            }

            if (hourOfDay > maxHour || (hourOfDay == maxHour && minute > maxMinute)) {
                validTime = false;
            }

            if (validTime) {
                currentHour = hourOfDay;
                currentMinute = minute;
            }

            updateTime(currentHour, currentMinute);
            updateDialogTitle(view, currentHour, currentMinute);
        }

        private void updateDialogTitle(TimePicker timePicker, int hourOfDay, int minute) {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            String title = dateFormat.format(calendar.getTime());
            setTitle(title);
        }


        private void fixSpinner(Context context, int hourOfDay, int minute, boolean is24HourView) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // android:timePickerMode spinner and clock began in Lollipop
                try {
                    // Get the theme's android:timePickerMode
                    //two modes are available clock mode and spinner mode ... selecting spinner mode for latest versions
                    final int MODE_SPINNER = 2;
                    Class<?> styleableClass = Class.forName("com.android.internal.R$styleable");
                    Field timePickerStyleableField = styleableClass.getField("TimePicker");
                    int[] timePickerStyleable = (int[]) timePickerStyleableField.get(null);
                    final TypedArray a = context.obtainStyledAttributes(null, timePickerStyleable, android.R.attr.timePickerStyle, 0);
                    Field timePickerModeStyleableField = styleableClass.getField("TimePicker_timePickerMode");
                    int timePickerModeStyleable = timePickerModeStyleableField.getInt(null);
                    final int mode = a.getInt(timePickerModeStyleable, MODE_SPINNER);
                    a.recycle();
                    if (mode == MODE_SPINNER) {
                        TimePicker timePicker = (TimePicker) findField(TimePickerDialog.class, TimePicker.class, "mTimePicker").get(this);
                        Class<?> delegateClass = Class.forName("android.widget.TimePicker$TimePickerDelegate");
                        Field delegateField = findField(TimePicker.class, delegateClass, "mDelegate");
                        Object delegate = delegateField.get(timePicker);
                        Class<?> spinnerDelegateClass;
                        if (Build.VERSION.SDK_INT != Build.VERSION_CODES.LOLLIPOP) {
                            spinnerDelegateClass = Class.forName("android.widget.TimePickerSpinnerDelegate");
                        } else {

                            spinnerDelegateClass = Class.forName("android.widget.TimePickerClockDelegate");
                        }
                        if (delegate.getClass() != spinnerDelegateClass) {
                            delegateField.set(timePicker, null); // throw out the TimePickerClockDelegate!
                            timePicker.removeAllViews(); // remove the TimePickerClockDelegate views
                            Constructor spinnerDelegateConstructor = spinnerDelegateClass.getConstructor(TimePicker.class, Context.class, AttributeSet.class, int.class, int.class);
                            spinnerDelegateConstructor.setAccessible(true);
                            // Instantiate a TimePickerSpinnerDelegate
                            delegate = spinnerDelegateConstructor.newInstance(timePicker, context, null, android.R.attr.timePickerStyle, 0);
                            delegateField.set(timePicker, delegate); // set the TimePicker.mDelegate to the spinner delegate
                            // Set up the TimePicker again, with the TimePickerSpinnerDelegate
                            timePicker.setIs24HourView(is24HourView);
                            timePicker.setCurrentHour(hourOfDay);
                            timePicker.setCurrentMinute(minute);
                            timePicker.setOnTimeChangedListener(this);
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        private Field findField(Class objectClass, Class fieldClass, String expectedName) {
            try {
                Field field = objectClass.getDeclaredField(expectedName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {
            } // ignore
            // search for it if it wasn't found under the expected ivar name
            for (Field searchField : objectClass.getDeclaredFields()) {
                if (searchField.getType() == fieldClass) {
                    searchField.setAccessible(true);
                    return searchField;
                }
            }
            return null;
        }
    }
}
