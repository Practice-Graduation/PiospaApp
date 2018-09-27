package com.ptit.baobang.piospaapp.ui.dialogs.room;

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
import com.ptit.baobang.piospaapp.data.model.Room;
import com.ptit.baobang.piospaapp.ui.adapter.RoomAdapter;
import com.ptit.baobang.piospaapp.ui.base.BaseActivity;
import com.ptit.baobang.piospaapp.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoomActivity extends BaseActivity<RoomPresenter> implements IRoomView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private List<Room> mRooms;
    private RoomAdapter mAdapter;
    @BindView(R.id.rvContent)
    RecyclerView rvContent;
    private Room mRoomSelected;

    private SearchView searchView;
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
        setTitle("Chọn phòng");
        centerToolbarTitle(toolbar, 0);
    }

    private void addControls() {
        mPresenter = new RoomPresenter(this, this);
        mUnbinder = ButterKnife.bind(this);

        mRooms = new ArrayList<>();
        mAdapter = new RoomAdapter(this, mRooms);
        rvContent.setLayoutManager(new LinearLayoutManager(this));
        rvContent.setAdapter(mAdapter);

        mRoomSelected = mPresenter.getData(getIntent());
        mPresenter.loadData(getIntent());
    }

    private void addEvents() {
        mAdapter.setmListener(position -> {
            mPresenter.clickItem(mRooms.get(position));
        });
    }
    public void backToBookingActivity(Room room) {
        Intent intent = new Intent();
        intent.putExtra(AppConstants.ROOM_SELECTED, room);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public RoomAdapter getRoomAdapter() {
        return mAdapter;
    }

    @Override
    public void showRooms(ArrayList<Room> rooms) {
        mRooms = new ArrayList<>(rooms);
        mAdapter = new RoomAdapter(this, mRooms);
        mAdapter.setmRoomSelected(mRoomSelected);
        rvContent.setLayoutManager(new LinearLayoutManager(this));
        rvContent.setAdapter(mAdapter);
        addEvents();
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
}
