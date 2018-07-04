package com.ptit.baobang.piospaapp.ui.activities.order;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.OrderStatus;
import com.ptit.baobang.piospaapp.ui.adapter.PagerApdater;
import com.ptit.baobang.piospaapp.ui.base.BaseActivity;
import com.ptit.baobang.piospaapp.ui.fragments.fragment_order.not_payment.ListOrderFragment;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class OrderActivity extends BaseActivity<OrderPresenter> implements IOrderView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.tabOrder)
    TabLayout mTabOrder;

    int selectedTab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        addControls();
        addEvents();
    }

    private void addEvents() {

    }

    private void addControls() {
        mPresenter = new OrderPresenter(this);
        selectedTab = mPresenter.getSelectedTab(getIntent());
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Đơn Hàng");
        mTabOrder.setTabGravity(TabLayout.GRAVITY_FILL);
        mPresenter.loadData(getBaseContext());
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
    public void addTabLayout(List<OrderStatus> orderStatuses) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        PagerApdater adapter = new PagerApdater(fragmentManager);

        for(OrderStatus status : orderStatuses){
            adapter.addTab(ListOrderFragment.newInstance(status), status.getOrderStatusName());
        }

        mViewPager.setAdapter(adapter);
        mTabOrder.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabOrder));
        mTabOrder.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(selectedTab);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
