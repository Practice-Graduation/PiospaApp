package com.ptit.baobang.piospaapp.ui.dialogs.room;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.SearchView;

import com.ptit.baobang.piospaapp.data.model.Room;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.RxSearchObservable;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RoomPresenter  extends BasePresenter implements IRoomPresenter{
    private Context mContext;
    private IRoomView mView;

    public RoomPresenter(Context mContext, IRoomView mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void loadData(Intent intent) {
        ArrayList<Room> rooms = (ArrayList<Room>) intent.getSerializableExtra("list");
        mView.showRooms(rooms);
    }

    @Override
    public void clickItem(Room room) {
        mView.backToBookingActivity(room);
    }

    @Override
    public Room getData(Intent intent) {
        return (Room) intent.getSerializableExtra(AppConstants.ROOM_SELECTED);
    }

    @Override
    public void filter(SearchView searchView) {
        RxSearchObservable.fromView(searchView)
                .debounce(100, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handFilterResponse);
    }

    private void handFilterResponse(String s) {
        mView.getRoomAdapter().getFilter().filter(s);
    }
}
