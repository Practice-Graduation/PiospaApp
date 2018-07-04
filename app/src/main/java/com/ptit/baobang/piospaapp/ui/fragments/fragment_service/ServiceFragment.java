package com.ptit.baobang.piospaapp.ui.fragments.fragment_service;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.ServiceGroup;
import com.ptit.baobang.piospaapp.data.model.ServicePrice;
import com.ptit.baobang.piospaapp.ui.activities.all_services.AllServiceActivity;
import com.ptit.baobang.piospaapp.ui.activities.service_detail.ServiceDetailActivity;
import com.ptit.baobang.piospaapp.ui.adapter.ServiceGroupAdapter;
import com.ptit.baobang.piospaapp.ui.base.BaseFragment;
import com.ptit.baobang.piospaapp.ui.listener.OnItemClickListener;
import com.ptit.baobang.piospaapp.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServiceFragment extends BaseFragment<ServicePresenter> implements IServiceView {

    @BindView(R.id.rvServiceGroups)
    RecyclerView rvServiceGroups;
    private List<ServiceGroup> mServiceGroups;
    private ServiceGroupAdapter<ServicePresenter> mAdapter;

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LayoutInflater layoutInflater = LayoutInflater.from(container.getContext());
        View view = layoutInflater.inflate(R.layout.fragment_service, container, false);
        addControls(view);
        addEvents();
        return view;
    }

    private void addEvents() {
        mAdapter.setmItemSelected(new ServiceGroupAdapter.OnSelectedItem() {
            @Override
            public void itemSelected(ServicePrice servicePrice) {
                mPresenter.clickItem(servicePrice);

            }
        });

        mAdapter.setOnItemClickMoreListerner(new OnItemClickListener() {
            @Override
            public void onItemSelected(int position) {
                mPresenter.onClickMore(mServiceGroups.get(position));
            }
        });
    }

    private void addControls(View view) {
        mUnBinder = ButterKnife.bind(this, view);
        mPresenter = new ServicePresenter(this);

        mServiceGroups = new ArrayList<>();
        mAdapter = new ServiceGroupAdapter<>(getContext(), mServiceGroups, mPresenter, mPresenter.getmApiService());
        rvServiceGroups.setLayoutManager(new LinearLayoutManager(getContext()));
        rvServiceGroups.setAdapter(mAdapter);
        mPresenter.loadData();
    }

    @Override
    public void openAllServiceActivity(int serviceGroupId, String serviceGroupName) {
        Intent intent = new Intent(getContext(), AllServiceActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.SERVICE_GROUP_ID, serviceGroupId);
        bundle.putString(AppConstants.TOOL_BAR_TITLE, serviceGroupName);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void openServiceDetailActivity(ServicePrice servicePrice) {
        Intent intent = new Intent(getContext(), ServiceDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstants.SERVICE_PRICE_ID, servicePrice);
        intent.putExtras(bundle);
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
    }

    @Override
    public void onPause() {
        super.onPause();
    }


}
