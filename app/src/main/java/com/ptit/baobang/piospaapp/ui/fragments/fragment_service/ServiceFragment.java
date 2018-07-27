package com.ptit.baobang.piospaapp.ui.fragments.fragment_service;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.ServiceGroup;
import com.ptit.baobang.piospaapp.data.model.ServicePrice;
import com.ptit.baobang.piospaapp.ui.activities.all_services.AllServiceActivity;
import com.ptit.baobang.piospaapp.ui.activities.service_detail.ServiceDetailActivity;
import com.ptit.baobang.piospaapp.ui.adapter.ServiceGroupAdapter;
import com.ptit.baobang.piospaapp.ui.base.BaseFragment;
import com.ptit.baobang.piospaapp.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServiceFragment extends BaseFragment<ServicePresenter> implements IServiceView {

    @BindView(R.id.rvServiceGroups)
    RecyclerView rvServiceGroups;
    private List<ServiceGroup> mServiceGroups;
    private ServiceGroupAdapter mAdapter;

    private SearchView searchView;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);
        SearchManager searchManager = (SearchManager)
                getBaseContext().getSystemService(Context.SEARCH_SERVICE);

        searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconified(false);

        mPresenter.filter(searchView);
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
        mAdapter.setmItemSelected(servicePrice -> mPresenter.clickItem(servicePrice));

        mAdapter.setOnItemClickMoreListerner(position -> mPresenter.onClickMore(mServiceGroups.get(position)));
    }

    private void addControls(View view) {
        mUnBinder = ButterKnife.bind(this, view);
        mPresenter = new ServicePresenter(getBaseContext(), this);

        mServiceGroups = new ArrayList<>();
        mAdapter = new ServiceGroupAdapter(getContext(), mServiceGroups, this, mPresenter.getmApiService());
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
        mServiceGroups = new ArrayList<>(serviceGroups);
        mAdapter = new ServiceGroupAdapter(getContext(), mServiceGroups, this, mPresenter.getmApiService());
        rvServiceGroups.setLayoutManager(new LinearLayoutManager(getContext()));
        rvServiceGroups.setAdapter(mAdapter);
        addEvents();
    }

    @Override
    public ServiceGroupAdapter getServiceGroupAdapter() {
        return mAdapter;
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
