package com.ptit.baobang.piospaapp.ui.dialogs.room;

import android.content.Intent;
import android.support.v7.widget.SearchView;

import com.ptit.baobang.piospaapp.data.model.Room;

public interface IRoomPresenter {
    void loadData(Intent intent);

    void clickItem(Room room);

    Room getData(Intent intent);

    void filter(SearchView searchView);
}
