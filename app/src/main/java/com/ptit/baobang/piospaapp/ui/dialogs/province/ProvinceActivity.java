package com.ptit.baobang.piospaapp.ui.dialogs.province;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.Province;
import com.ptit.baobang.piospaapp.ui.adapter.ProvinceAdapter;
import com.ptit.baobang.piospaapp.ui.base.BaseActivity;
import com.ptit.baobang.piospaapp.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProvinceActivity extends BaseActivity implements IProvinceView{

    private ProvincePresenter mPresenter;

    private List<Province> mProvinces;
    private ProvinceAdapter mAdapter;
    @BindView(R.id.rvContent)
    RecyclerView rvContent;

    @BindView(R.id.shimmerLayout)
    ShimmerFrameLayout shimmerFrameLayout;

    private Province mProvinceSelected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province);
        addControls();
        addEvents();
    }

    private void addEvents() {
        mAdapter.setmListener(position -> {
            mPresenter.clickItem(mProvinces.get(position));
        });
    }

    private void addControls() {
        setTitle(R.string.province);
        mPresenter = new ProvincePresenter(this);
        mUnbinder = ButterKnife.bind(this);

        mProvinces = new ArrayList<>();
        mAdapter = new ProvinceAdapter(this, mProvinces);
        rvContent.setLayoutManager(new LinearLayoutManager(this));
        rvContent.setAdapter(mAdapter);

        mProvinceSelected = mPresenter.getData(getIntent());
        mPresenter.loadData();
    }

    @Override
    public void updateRecyleView(List<Province> provinces) {
        mProvinces.clear();
        mProvinces.addAll(provinces);
        mAdapter.setmProvinceSelected(mProvinceSelected);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void backToPaymentActivity(Province province) {
        Intent intent = new Intent();
        intent.putExtra(AppConstants.PROVINCE, province);
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
