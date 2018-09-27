package com.ptit.baobang.piospaapp.ui.dialogs.room;

import com.ptit.baobang.piospaapp.data.model.Room;
import com.ptit.baobang.piospaapp.ui.adapter.RoomAdapter;
import com.ptit.baobang.piospaapp.ui.base.BaseView;

import java.util.ArrayList;

public interface IRoomView extends BaseView {
    void showRooms(ArrayList<Room> rooms);

    void backToBookingActivity(Room room);

    RoomAdapter getRoomAdapter();
}
