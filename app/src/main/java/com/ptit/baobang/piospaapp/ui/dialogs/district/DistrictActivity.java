package com.ptit.baobang.piospaapp.ui.dialogs.district;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.District;
import com.ptit.baobang.piospaapp.data.model.Province;
import com.ptit.baobang.piospaapp.ui.adapter.DistrictAdapter;
import com.ptit.baobang.piospaapp.ui.base.BaseActivity;
import com.ptit.baobang.piospaapp.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DistrictActivity extends BaseActivity implements IDistrictView {

    private DistrictPresenter mPresenter;

    private List<District> mDistricts;
    private DistrictAdapter mAdapter;
    @BindView(R.id.rvContent)
    RecyclerView rvContent;

    @BindView(R.id.shimmerLayout)
    ShimmerFrameLayout shimmerFrameLayout;

    private Province mProvinceSelected = null;
    private District mDistrictSelected = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province);
        addControls();
        addEvents();
    }

    private void addEvents() {
        mAdapter.setmListener(position -> {
            mPresenter.clickItem(mDistricts.get(position));
        });
    }

    private void addControls() {
        setTitle(R.string.district);
        mPresenter = new DistrictPresenter(this);
        mUnbinder = ButterKnife.bind(this);

        mDistricts = new ArrayList<>();
        mAdapter = new DistrictAdapter(this, mDistricts);
        rvContent.setLayoutManager(new LinearLayoutManager(this));
        rvContent.setAdapter(mAdapter);

        mProvinceSelected = mPresenter.getProvince(getIntent());
        mDistrictSelected = mPresenter.getDistrict(getIntent());
        mPresenter.loadData(mProvinceSelected);
    }

    @Override
    public void updateRecyleView(List<District> districts) {
        mDistricts.clear();
        mDistricts.addAll(districts);
        mAdapter.setmDistrictSelected(mDistrictSelected);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void backToPaymentActivity(District district) {
        Intent intent = new Intent();
        intent.putExtra(AppConstants.DISTRICT, district);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startShimmerAnimation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopShimmerAnimation();
    }

    @Override
    public void startShimmerAnimation() {
        if(shimmerFrameLayout != null){
            shimmerFrameLayout.startShimmerAnimation();
        }
    }

    @Override
    public void stopShimmerAnimation() {
        if(shimmerFrameLayout != null){
            shimmerFrameLayout.stopShimmerAnimation();
            shimmerFrameLayout.setVisibility(View.GONE);
        }
    }
}
