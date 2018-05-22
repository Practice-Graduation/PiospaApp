package com.ptit.baobang.piospaapp.ui.fragments.fragment_cart_service;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.cart.CartServicePriceItem;
import com.ptit.baobang.piospaapp.ui.adapter.ShoppingCartServicePriceAdapter;
import com.ptit.baobang.piospaapp.ui.base.BaseFragment;
import com.ptit.baobang.piospaapp.ui.fragments.fragment_cart.CartFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartServiceFragment extends BaseFragment implements ICartServiceFragmentView {

    private CartServiceFragmentPresenter mPresenter;

    private CartFragment cartFragment;


    @BindView(R.id.rvServices)
    RecyclerView rvServices;
    ShoppingCartServicePriceAdapter mAdapter;
    private List<CartServicePriceItem> mItems;


    public CartServiceFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CartServiceFragment newInstance() {
        CartServiceFragment fragment = new CartServiceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart_service, container, false);
        mUnBinder = ButterKnife.bind(this, view);
        addControls(view);
        return view;
    }

    private void addControls(View view) {
        mPresenter = new CartServiceFragmentPresenter(this);
        mItems = new ArrayList<>();
        mAdapter = new ShoppingCartServicePriceAdapter(getContext(), mItems);
        rvServices.setLayoutManager(new LinearLayoutManager(getContext()));
        rvServices.setAdapter(mAdapter);
        mPresenter.loadData();
        mAdapter.onSetAddClickListener(position -> {
            mPresenter.addCartItem(mItems.get(position));
            if(cartFragment != null){
                cartFragment.updateUI();
            }
        });
        mAdapter.onSetRemoveClickListener(position -> {
            mPresenter.removeCartItem(mItems.get(position));
            if(cartFragment != null){
                cartFragment.updateUI();
            }
        });
    }

    @Override
    public void showCartItemList(List<CartServicePriceItem> cartItems) {
        mItems.clear();
        mItems.addAll(cartItems);
        mAdapter.notifyDataSetChanged();
    }

    public void setCartFragment(CartFragment cartFragment) {
        this.cartFragment = cartFragment;
    }
}
