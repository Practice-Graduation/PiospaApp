package com.ptit.baobang.piospaapp.ui.fragments.fragment_cart_service;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.cart.Cart;
import com.ptit.baobang.piospaapp.data.cart.CartHelper;
import com.ptit.baobang.piospaapp.data.cart.CartServicePriceItem;
import com.ptit.baobang.piospaapp.ui.adapter.ShoppingCartServicePriceAdapter;
import com.ptit.baobang.piospaapp.ui.base.BaseFragment;
import com.ptit.baobang.piospaapp.ui.fragments.fragment_cart.CartFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartServiceFragment extends BaseFragment<CartServiceFragmentPresenter> implements ICartServiceFragmentView {

    private CartFragment cartFragment;

    @BindView(R.id.layout_empty)
    LinearLayout layoutEmpty;
    @BindView(R.id.rvServices)
    RecyclerView rvServices;
    ShoppingCartServicePriceAdapter mAdapter;
    private List<CartServicePriceItem> mItems;


    private SearchView searchView;
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
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart_service, container, false);
        mUnBinder = ButterKnife.bind(this, view);
        addControls();
        addEvents();
        return view;
    }

    private void addEvents() {
        mAdapter.onSetAddClickListener(position -> {
            mPresenter.addCartItem(mItems.get(position));
            if(cartFragment != null){
                cartFragment.updateUI();
                showCartEmpty();
            }
        });
        mAdapter.onSetRemoveClickListener(position -> {
            mPresenter.removeCartItem(mItems.get(position));
            if(cartFragment != null){
                cartFragment.updateUI();
                showCartEmpty();
            }
        });
    }

    private void addControls() {
        mPresenter = new CartServiceFragmentPresenter(this);
        mItems = new ArrayList<>();
        mAdapter = new ShoppingCartServicePriceAdapter(getContext(), mItems);
        rvServices.setLayoutManager(new LinearLayoutManager(getContext()));
        rvServices.setAdapter(mAdapter);
        mPresenter.loadData();
    }

    @Override
    public void showCartItemList(List<CartServicePriceItem> cartItems) {
        mItems.clear();
        mItems.addAll(cartItems);
        mAdapter.notifyDataSetChanged();
        showCartEmpty();
    }

    @Override
    public void showCartEmpty() {

        Cart cart = CartHelper.getCart();
        if(cart.getServicePrices().size() == 0){
            layoutEmpty.setVisibility(View.VISIBLE);
            rvServices.setVisibility(View.GONE);
        }else{
            layoutEmpty.setVisibility(View.GONE);
            rvServices.setVisibility(View.VISIBLE);
        }
    }

    public void setCartFragment(CartFragment cartFragment) {
        this.cartFragment = cartFragment;
    }
}
