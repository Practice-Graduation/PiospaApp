package com.ptit.baobang.piospaapp.ui.activities.all_services;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.ServicePrice;
import com.ptit.baobang.piospaapp.ui.activities.service_detail.ServiceDetailActivity;
import com.ptit.baobang.piospaapp.ui.adapter.ServiceAdapter;
import com.ptit.baobang.piospaapp.ui.base.BaseActivity;
import com.ptit.baobang.piospaapp.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AllServiceActivity extends BaseActivity<AllServicePresenter> implements IAllServiceView {

    private static final String TAG = "AllServiceActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rvProducts)
    RecyclerView rvServicePrice;
    ServiceAdapter mServiceAdapter;
    List<ServicePrice> mServicePrices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product);
        addControls();
        addEvents();
    }

    private void addEvents() {
        mServiceAdapter.setOnClickListener(position -> {
            mPresenter.onSelectedProduct(mServicePrices.get(position));
        });
    }

    private void addControls() {

        mPresenter = new AllServicePresenter(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(getTitleFromBundle());
        centerToolbarTitle(toolbar, 0);

        mServicePrices = new ArrayList<>();
        mServiceAdapter = new ServiceAdapter(this, mServicePrices);
        rvServicePrice.setLayoutManager(new GridLayoutManager(this, AppConstants.SPAN_COUNT));
        rvServicePrice.setAdapter(mServiceAdapter);

        mPresenter.loadData(getGroupIdFromBundle());
    }

    @Override
    public void onUpdateRecyleView(List<ServicePrice> servicePrices) {
        mServicePrices.clear();
        mServicePrices.addAll(servicePrices);
        mServiceAdapter.notifyDataSetChanged();
    }

    @Override
    public void openProductDetailActivity(ServicePrice servicePrice) {
        Intent intent = new Intent(this, ServiceDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstants.PRODUCT_ID, servicePrice);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public int getGroupIdFromBundle() {

        Bundle bundle = getIntent().getExtras();

        return bundle.getInt(AppConstants.SERVICE_GROUP_ID);
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
    public boolean onNavigateUp() {
        onBackPressed();
        return true;
    }

    public String getTitleFromBundle() {
        Bundle bundle = getIntent().getExtras();
        String title = "";
        try {
            title = bundle.getString(AppConstants.TOOL_BAR_TITLE);
        } catch (Exception e) {
            title = getString(R.string.title_product) + "    ";
        }
        return title;
    }
}
