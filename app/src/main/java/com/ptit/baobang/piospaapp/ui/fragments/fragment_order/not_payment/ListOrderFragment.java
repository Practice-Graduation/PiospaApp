package com.ptit.baobang.piospaapp.ui.fragments.fragment_order.not_payment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.Order;
import com.ptit.baobang.piospaapp.data.model.OrderStatus;
import com.ptit.baobang.piospaapp.ui.adapter.OrderAdapter;
import com.ptit.baobang.piospaapp.ui.base.BaseFragment;
import com.ptit.baobang.piospaapp.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ListOrderFragment extends BaseFragment implements IListOrderView{

    private ListOderPresenter mPresenter;
    private OrderStatus orderStatus;
    List<Order> mOrders;

    private OrderAdapter mAdapter;

    @BindView(R.id.rvOrders)
    RecyclerView rvOrders;

    public ListOrderFragment() {
        // Required empty public constructor
    }


    public static ListOrderFragment newInstance(OrderStatus orderStatus) {
        ListOrderFragment fragment = new ListOrderFragment();
        Bundle args = new Bundle();
        args.putSerializable(AppConstants.ORDER_STATUS, orderStatus);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            orderStatus = (OrderStatus) getArguments()
                    .getSerializable(AppConstants.ORDER_STATUS);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_order, container, false);
        addControls(view);
        return view;
    }

    private void addControls(View view) {
        mUnBinder = ButterKnife.bind(this,view);
        mPresenter = new ListOderPresenter(this);

        mOrders = new ArrayList<>();
        mAdapter = new OrderAdapter(getBaseContext(), mOrders);
        rvOrders.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        rvOrders.setAdapter(mAdapter);

        mPresenter.loadOrder(getBaseContext(), orderStatus);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void updateRecycleView(List<Order> orders) {
        mOrders.clear();
        mOrders.addAll(orders);
        mAdapter.notifyDataSetChanged();
    }
}
