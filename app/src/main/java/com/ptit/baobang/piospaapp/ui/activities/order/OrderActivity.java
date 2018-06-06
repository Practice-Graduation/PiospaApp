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

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderActivity extends BaseActivity implements IOrderView {

    private OrderPresenter mPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.tabOrder)
    TabLayout mTabOrder;

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
        mUnbinder = ButterKnife.bind(this);
        mPresenter = new OrderPresenter(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Đơn Hàng");
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

    }
}
