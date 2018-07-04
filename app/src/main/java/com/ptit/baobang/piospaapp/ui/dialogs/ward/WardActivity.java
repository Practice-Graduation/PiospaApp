package com.ptit.baobang.piospaapp.ui.dialogs.ward;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.District;
import com.ptit.baobang.piospaapp.data.model.Ward;
import com.ptit.baobang.piospaapp.ui.adapter.WardAdapter;
import com.ptit.baobang.piospaapp.ui.base.BaseActivity;
import com.ptit.baobang.piospaapp.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WardActivity extends BaseActivity implements IWardView {

    private WardPresenter mPresenter;

    private List<Ward> mWards;
    private WardAdapter mAdapter;
    @BindView(R.id.rvContent)
    RecyclerView rvContent;

    @BindView(R.id.shimmerLayout)
    ShimmerFrameLayout shimmerFrameLayout;

    private District mDistrictselected = null;
    private Ward mWardSelected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province);
        addControls();
        addEvents();
    }

    private void addEvents() {
        mAdapter.setmListener(position -> {
            mPresenter.clickItem(mWards.get(position));
        });
    }

    private void addControls() {
        setTitle(R.string.ward);
        mPresenter = new WardPresenter(this);
        mUnbinder = ButterKnife.bind(this);

        mWards = new ArrayList<>();
        mAdapter = new WardAdapter(this, mWards);
        rvContent.setLayoutManager(new LinearLayoutManager(this));
        rvContent.setAdapter(mAdapter);

        mDistrictselected = mPresenter.getDistrict(getIntent());
        mWardSelected = mPresenter.getWard(getIntent());
        mPresenter.loadData(mDistrictselected);
    }

    @Override
    public void updateRecyleView(List<Ward> wards) {
        mWards.clear();
        mWards.addAll(wards);
        mAdapter.setmWardSelected(mWardSelected);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void backToPaymentActivity(Ward ward) {
        Intent intent = new Intent();
        intent.putExtra(AppConstants.WARD, ward);
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
