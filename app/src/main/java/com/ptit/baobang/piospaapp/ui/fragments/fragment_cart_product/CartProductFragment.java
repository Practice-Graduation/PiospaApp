package com.ptit.baobang.piospaapp.ui.fragments.fragment_cart_product;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.cart.Cart;
import com.ptit.baobang.piospaapp.data.cart.CartHelper;
import com.ptit.baobang.piospaapp.data.cart.CartProductItem;
import com.ptit.baobang.piospaapp.ui.adapter.ShoppingCartProductAdapter;
import com.ptit.baobang.piospaapp.ui.base.BaseFragment;
import com.ptit.baobang.piospaapp.ui.fragments.fragment_cart.CartFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartProductFragment extends BaseFragment implements ICartProductFragmentView {

    private CartProductFragmentPresenter mPresenter;

    @BindView(R.id.layout_empty)
    LinearLayout layoutEmpty;

    @BindView(R.id.rvProducts)
    RecyclerView rvProducts;
    ShoppingCartProductAdapter mAdapter;
    private List<CartProductItem> mItems;

    private CartFragment cartFragment;

    public CartProductFragment() {
        // Required empty public constructor
    }

    public static CartProductFragment newInstance() {
        CartProductFragment fragment = new CartProductFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mPresenter != null) {
            mPresenter.loadData();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (mPresenter != null) {
//            mPresenter.loadData();
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart_product, container, false);
        mUnBinder = ButterKnife.bind(this, view);
        addControls(view);
        addEvents();
        return view;
    }

    private void addEvents() {
        mAdapter.onSetAddClickListener(position -> {
            mPresenter.addCartItem(mItems.get(position));
            if (cartFragment != null) {
                cartFragment.updateUI();
                showEmptyCart();
            }
        });
        mAdapter.onSetRemoveClickListener(position -> {
            mPresenter.removeCartItem(mItems.get(position));
            if (cartFragment != null) {
                cartFragment.updateUI();
                showEmptyCart();
            }
        });
    }

    private void addControls(View view) {
        mPresenter = new CartProductFragmentPresenter(this);
        mItems = new ArrayList<>();
        mAdapter = new ShoppingCartProductAdapter(getContext(), mItems);
        rvProducts.setLayoutManager(new LinearLayoutManager(getContext()));
        rvProducts.setAdapter(mAdapter);
        mPresenter.loadData();
    }

    @Override
    public void showCartItemList(List<CartProductItem> cartItems) {
//        mItems.clear();
//        mItems.addAll(cartItems);
//        mAdapter.notifyDataSetChanged();
        mItems = new ArrayList<>();
        mItems.addAll(cartItems);
        mAdapter = new ShoppingCartProductAdapter(getContext(), mItems);
        rvProducts.setLayoutManager(new LinearLayoutManager(getContext()));
        rvProducts.setAdapter(mAdapter);
        showEmptyCart();
    }

    @Override
    public void showEmptyCart() {
        Cart cart = CartHelper.getCart();
        int size = cart.getProducts().size();
        if (size == 0) {
            layoutEmpty.setVisibility(View.VISIBLE);
            rvProducts.setVisibility(View.GONE);
        } else {
            layoutEmpty.setVisibility(View.GONE);
            rvProducts.setVisibility(View.VISIBLE);
        }
    }

    public void setCartFragment(CartFragment cartFragment) {
        this.cartFragment = cartFragment;
    }
}
