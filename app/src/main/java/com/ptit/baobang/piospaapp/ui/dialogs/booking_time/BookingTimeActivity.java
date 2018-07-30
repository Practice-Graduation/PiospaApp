package com.ptit.baobang.piospaapp.ui.dialogs.booking_time;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.ServicePrice;
import com.ptit.baobang.piospaapp.ui.adapter.TimeAdapter;
import com.ptit.baobang.piospaapp.ui.base.BaseActivity;
import com.ptit.baobang.piospaapp.ui.listener.OnItemClickListener;
import com.ptit.baobang.piospaapp.utils.AppConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookingTimeActivity extends BaseActivity implements IBookingTimeView {

    private static final String TAG = "BookingTimeActivity";

    private BookingTimePresenter mPresenter;
    private Date mSelectedDate;
    private ServicePrice mServicePrice;


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rvTimes)
    RecyclerView rvTimes;
    List<String> mTimes;
    TimeAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_time);
        setSupportToolbar();
        addControls();
        addEvents();
    }

    private void setSupportToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.pick_time);
        centerToolbarTitle(toolbar, 30);
    }

    private void addEvents() {
        mAdapter.setmListener(new OnItemClickListener() {
            @Override
            public void onItemSelected(int position) {
                mPresenter.clickBooking(mTimes.get(position));
            }
        });
    }

    private void addControls() {
        mPresenter = new BookingTimePresenter(this, this);
        mUnbinder = ButterKnife.bind(this);

        mSelectedDate = mPresenter.getSelectedDateFromIntent(getIntent());
        mServicePrice = mPresenter.getServicePriceFromIntent(getIntent());

        mTimes = new ArrayList<>();
        mAdapter = new TimeAdapter(this, mTimes);
        rvTimes.setLayoutManager(new LinearLayoutManager(this));
        rvTimes.setAdapter(mAdapter);

        mPresenter.loadData(mServicePrice, mSelectedDate);
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

    @Override
    public void updateRecycleViewTime(List<String> times) {
        mTimes.clear();
        mTimes.addAll(times);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void backToBookingActivity(String timeStart) {
        Intent intent = new Intent();
        intent.putExtra(AppConstants.TIME_SELECRED, timeStart);
        setResult(RESULT_OK, intent);
        finish();
    }
}
