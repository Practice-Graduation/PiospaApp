package com.ptit.baobang.piospaapp.ui.dialogs.ward;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.District;
import com.ptit.baobang.piospaapp.data.model.Ward;
import com.ptit.baobang.piospaapp.ui.adapter.WardAdapter;
import com.ptit.baobang.piospaapp.ui.base.BaseActivity;
import com.ptit.baobang.piospaapp.utils.KeyBundleConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WardActivity extends BaseActivity implements IWardView {

    private WardPresenter mPresenter;
    @BindView(R.id.toolbar)
     Toolbar toolbar;
    private List<Ward> mWards;
    private WardAdapter mAdapter;
    @BindView(R.id.rvContent)
    RecyclerView rvContent;

    private SearchView searchView;

    private District mDistrictselected = null;
    private Ward mWardSelected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province);
        setSupportToolbar();
        addControls();
        addEvents();
    }

    private void addEvents() {
        mAdapter.setmListener(position -> {
            mPresenter.clickItem(mWards.get(position));
        });
    }
    private void setSupportToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.ward);
        centerToolbarTitle(toolbar, 0);
    }
    private void addControls() {
        mPresenter = new WardPresenter(this, this);
        mUnbind = ButterKnife.bind(this);

        mWards = new ArrayList<>();
        mAdapter = new WardAdapter(this, mWards);
        rvContent.setLayoutManager(new LinearLayoutManager(this));
        rvContent.setAdapter(mAdapter);

        mDistrictselected = mPresenter.getDistrict(getIntent());
        mWardSelected = mPresenter.getWard(getIntent());
        mPresenter.loadData(mDistrictselected);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);

        searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchView.setIconified(false);

        mPresenter.filter(searchView);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public void updateRecyleView(List<Ward> wards) {
        mWards = new ArrayList<>(wards);
        mAdapter = new WardAdapter(this, mWards);
        mAdapter.setmWardSelected(mWardSelected);
        rvContent.setLayoutManager(new LinearLayoutManager(this));
        rvContent.setAdapter(mAdapter);
        addEvents();
    }

    @Override
    public void backToPaymentActivity(Ward ward) {
        Intent intent = new Intent();
        intent.putExtra(KeyBundleConstant.WARD, ward);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public WardAdapter getWardAdapter() {
        return mAdapter;
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return (super.onOptionsItemSelected(item));
    }
}
