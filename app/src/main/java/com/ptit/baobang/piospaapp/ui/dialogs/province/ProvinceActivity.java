package com.ptit.baobang.piospaapp.ui.dialogs.province;

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
import com.ptit.baobang.piospaapp.data.model.Province;
import com.ptit.baobang.piospaapp.ui.adapter.ProvinceAdapter;
import com.ptit.baobang.piospaapp.ui.base.BaseActivity;
import com.ptit.baobang.piospaapp.utils.KeyBundleConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProvinceActivity extends BaseActivity implements IProvinceView {

    private ProvincePresenter mPresenter;
    @BindView(R.id.toolbar)
     Toolbar toolbar;
    private List<Province> mProvinces;
    private ProvinceAdapter mAdapter;
    @BindView(R.id.rvContent)
    RecyclerView rvContent;

    private SearchView searchView;
    private Province mProvinceSelected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province);
        setSupportToolbar();
        addControls();
        addEvents();
    }

    private void setSupportToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.province);
        centerToolbarTitle(toolbar, 0);
    }

    private void addEvents() {
        mAdapter.setmListener(position -> {
            mPresenter.clickItem(mProvinces.get(position));
        });
    }

    private void addControls() {
        mPresenter = new ProvincePresenter(this, this);
        mUnbinder = ButterKnife.bind(this);

        mProvinces = new ArrayList<>();
        mAdapter = new ProvinceAdapter(this, mProvinces);
        rvContent.setLayoutManager(new LinearLayoutManager(this));
        rvContent.setAdapter(mAdapter);

        mProvinceSelected = mPresenter.getData(getIntent());
        mPresenter.loadData();
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
    public void updateRecyleView(List<Province> provinces) {
        mProvinces = new ArrayList<>(provinces);
        mAdapter = new ProvinceAdapter(this, mProvinces);
        mAdapter.setmProvinceSelected(mProvinceSelected);
        rvContent.setLayoutManager(new LinearLayoutManager(this));
        rvContent.setAdapter(mAdapter);
        addEvents();
    }

    @Override
    public void backToPaymentActivity(Province province) {
        Intent intent = new Intent();
        intent.putExtra(KeyBundleConstant.PROVINCE, province);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public ProvinceAdapter getProvinceAdapter() {
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
