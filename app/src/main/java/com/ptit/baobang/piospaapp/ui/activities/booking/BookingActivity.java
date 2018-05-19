package com.ptit.baobang.piospaapp.ui.activities.booking;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.ui.base.BaseActivity;
import com.ptit.baobang.piospaapp.ui.fragments.fragment_booking.BookingFragment;
import com.ptit.baobang.piospaapp.utils.AppConstants;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class BookingActivity extends BaseActivity implements IBookingView{

    private static final String TAG = "BookingActivity";

    private BookingPresenter mPresenter;
    private HorizontalCalendar mCalendar;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private int servicePriceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        getServicePriceId();
        addControls();
        addEvents();
        BookingFragment fragment = BookingFragment.newInstance(servicePriceId, new Date());
        if (fragment != null) {
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.layoutBooking, fragment).commit();
        }
    }

    private void addEvents() {
        mCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                BookingFragment fragment = BookingFragment.newInstance(servicePriceId, date.getTime());
                if(fragment != null){
                    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.layoutBooking, fragment).commit();
                }
            }
        });


    }

    private void addControls() {
        addHorizontalCalendar();
        mPresenter = new BookingPresenter(this);
        mUnbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Đặt Hẹn");
    }

    private void addHorizontalCalendar() {
        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        mCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .configure()
                    .formatTopText("MMM")
                    .formatMiddleText("dd")
                    .formatBottomText("EEE")
                    .showTopText(true)
                    .showBottomText(true)
                    .textColor(Color.LTGRAY, Color.WHITE)
                    .selectorColor(Color.rgb(255, 213,79))
                    .colorTextMiddle(Color.LTGRAY, Color.parseColor("#ffd54f"))
                .end()
                .defaultSelectedDate(Calendar.getInstance())
                .build();
    }

    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return (super.onOptionsItemSelected(item));
    }

    public int getServicePriceId() {
        Intent intent = getIntent();
        servicePriceId = intent.getIntExtra(AppConstants.SERVICE_PRICE_ID, 0);
        return servicePriceId;
    }
}
