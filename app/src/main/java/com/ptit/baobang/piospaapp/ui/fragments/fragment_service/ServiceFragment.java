package com.ptit.baobang.piospaapp.ui.fragments.fragment_service;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.ServiceGroup;
import com.ptit.baobang.piospaapp.ui.activities.all_product.AllProductActivity;
import com.ptit.baobang.piospaapp.ui.activities.service_detail.ServiceDetailActivity;
import com.ptit.baobang.piospaapp.ui.adapter.ServiceGroupAdapter;
import com.ptit.baobang.piospaapp.ui.base.BaseFragment;
import com.ptit.baobang.piospaapp.ui.listener.OnItemClickListener;
import com.ptit.baobang.piospaapp.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServiceFragment extends BaseFragment implements IServiceView {

    private ServicePresenter mPresenter;

    @BindView(R.id.shimmerLayout)
    ShimmerFrameLayout shimmerFrameLayout;

    @BindView(R.id.rvServiceGroups)
    RecyclerView rvServiceGroups;
    private List<ServiceGroup> mServiceGroups;
    private ServiceGroupAdapter mAdapter;

    public ServiceFragment() {
        // Required empty public constructor
    }


    public static ServiceFragment newInstance() {
        ServiceFragment fragment = new ServiceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LayoutInflater layoutInflater = LayoutInflater.from(container.getContext());
        View view = layoutInflater.inflate(R.layout.fragment_service, container, false);
        addControls(view);
        addEvents();
        return view;
    }

    private void addEvents() {
        mAdapter.setOnItemClickListerner(new OnItemClickListener() {
            @Override
            public void onItemSelected(int position) {
            }
        });
    }

    private void addControls(View view) {
        mUnBinder = ButterKnife.bind(this, view);
        mPresenter = new ServicePresenter(this);

        mServiceGroups = new ArrayList<>();
        mAdapter = new ServiceGroupAdapter(getContext(), mServiceGroups, mPresenter.getmApiService());
        mAdapter.setServiceView(this);
        rvServiceGroups.setLayoutManager(new LinearLayoutManager(getContext()));
        rvServiceGroups.setAdapter(mAdapter);
        mPresenter.loadData();
    }

    @Override
    public void openAllServiceActivity(int serviceGroupId) {
        Intent intent = new Intent(getContext(), AllProductActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.PRODUCT_GROUP_ID, serviceGroupId);
        intent.putExtra(AppConstants.PRODUCT_GROUP_BUNDLE, bundle);
        startActivity(intent);
    }

    @Override
    public void openServiceDetailActivity(int serviceId) {
        Intent intent = new Intent(getContext(), ServiceDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.SERVICE_PRICE_ID, serviceId);
        intent.putExtra(AppConstants.SERVICE_PRICE_BUNDLE, bundle);
        startActivity(intent);
    }

    @Override
    public void onUpdateRecycleView(List<ServiceGroup> serviceGroups) {
        mServiceGroups.clear();
        mServiceGroups.addAll(serviceGroups);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        startShirrmentAnimation();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopShirrmentAnimation();
    }

    @Override
    public void startShirrmentAnimation() {
        if (shimmerFrameLayout != null)
            shimmerFrameLayout.startShimmerAnimation();
    }

    @Override
    public void stopShirrmentAnimation() {
        if(shimmerFrameLayout != null){
            shimmerFrameLayout.stopShimmerAnimation();
            shimmerFrameLayout.setVisibility(View.GONE);
        }

    }
}
