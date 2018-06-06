package com.ptit.baobang.piospaapp.ui.fragments.fragment_booking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.Room;
import com.ptit.baobang.piospaapp.data.network.firebase.BookingTimeFB;
import com.ptit.baobang.piospaapp.ui.activities.booking_info.BookingInfoActivity;
import com.ptit.baobang.piospaapp.ui.adapter.RoomAdapter;
import com.ptit.baobang.piospaapp.ui.adapter.TimeAdapter;
import com.ptit.baobang.piospaapp.ui.base.BaseFragment;
import com.ptit.baobang.piospaapp.ui.listener.OnItemClickListener;
import com.ptit.baobang.piospaapp.utils.AppConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BookingFragment extends BaseFragment implements IBookingFragmentView{
    private static final String TAG = "BookingFragment";
    private static final String DATE = "DATE";
    private static final int TIME_SPAN_COUNT = 5;
    private BookingFragmentPresenter mPresenter;
    @BindView(R.id.rvRooms)
    RecyclerView rvRooms;
    @BindView(R.id.rvTimes)
    RecyclerView rvTimes;
    private List<Room> mRooms;
    private List<String> mTimes;
    private RoomAdapter mRoomAdapter;
    private TimeAdapter mTimeAdapter;
    private int mServicePriceSelected = 0;
    private Date mSelectedDate = null;
    private Room mRoomSelected = null;
    private BookingTimeFB mTimeSelected = null;

    public BookingFragment() {
        // Required empty public constructor
    }


    public static BookingFragment newInstance(int servicePriceId, Date date) {
        BookingFragment fragment = new BookingFragment();
        Bundle args = new Bundle();
        args.putSerializable(DATE,date);
        args.putInt(AppConstants.SERVICE_PRICE_ID, servicePriceId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSelectedDate = (Date) getArguments().getSerializable(DATE);
        mServicePriceSelected = getArguments().getInt(AppConstants.SERVICE_PRICE_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        addControls(view);
        addEvents();
        mPresenter.loadData();
        return view;
    }

    private void addEvents() {
        mRoomAdapter.setOnItemClick(position ->
        {
            mRoomSelected = mRooms.get(position);
            mPresenter.loadBookingTime(mRoomSelected, mSelectedDate);
        });

        mTimeAdapter.setmListener(new OnItemClickListener() {
            @Override
            public void onItemSelected(int position) {
//                mTimeSelected = mTimes.get(position);
                mPresenter.clickBookingButton(mServicePriceSelected, mSelectedDate, mRoomSelected, mTimeSelected);
            }
        });
    }

    private void addControls(View view) {
        mUnBinder = ButterKnife.bind(this, view);

        mPresenter = new BookingFragmentPresenter(this);
        mRooms = new ArrayList<>();
        mTimes = new ArrayList<>();

        mRoomAdapter = new RoomAdapter(getContext(), mRooms);
        rvRooms.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvRooms.setAdapter(mRoomAdapter);

        mTimeAdapter = new TimeAdapter(getContext(), mTimes);
        rvTimes.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvTimes.setAdapter(mTimeAdapter);
    }

    @Override
    public void updateRecycleViewRoom(List<Room> rooms) {
        mRooms.clear();
        mRooms.addAll(rooms);
        mRoomAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateRecycleViewTime(List<String> times) {
        mTimes.clear();
        mTimes.addAll(times);
        mTimeAdapter.notifyDataSetChanged();
    }

    @Override
    public void openBookingInfoActivity(int mServicePriceSelected, Date mSelectedDate) {
        Intent intent = new Intent(getBaseContext(), BookingInfoActivity.class);
        intent.putExtra(AppConstants.SERVICE_PRICE_ID, mServicePriceSelected);
        intent.putExtra(AppConstants.DATE_SELECTED, mSelectedDate);
        intent.putExtra(AppConstants.ROOM_SELECTED, mRoomSelected);
        intent.putExtra(AppConstants.TIME_SELECRED, mTimeSelected);
        startActivity(intent);
    }
}
