package com.ptit.baobang.piospaapp.ui.fragments.fragment_cart;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.cart.Cart;
import com.ptit.baobang.piospaapp.data.cart.CartHelper;
import com.ptit.baobang.piospaapp.data.cart.CartItem;
import com.ptit.baobang.piospaapp.ui.adapter.ShoppingCartAdapter;
import com.ptit.baobang.piospaapp.ui.base.BaseFragment;
import com.ptit.baobang.piospaapp.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartFragment extends BaseFragment implements ICartFragmentView {

    private CartFragmentPresenter mPresenter;

    @BindView(R.id.txtQuanlity)
    TextView txtQuanlity;
    @BindView(R.id.txtTotal)
    TextView txtTotal;
    @BindView(R.id.rvCart)
    RecyclerView rvCart;
    ShoppingCartAdapter mAdapter;
    List<CartItem> mCartItems;

    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        mUnBinder = ButterKnife.bind(this, view);
        addControls(view);
        return view;
    }

    private void addControls(View view) {
        mPresenter = new CartFragmentPresenter(this);
        mCartItems = new ArrayList<>();
        mAdapter = new ShoppingCartAdapter(getContext(), mCartItems);
        rvCart.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCart.setAdapter(mAdapter);
        mPresenter.loadData();
        mAdapter.onSetAddClickListener(position -> {
            mPresenter.addCartItem(mCartItems.get(position));
        });
        mAdapter.onSetRemoveClickListener(position -> {
            mPresenter.removeCartItem(mCartItems.get(position));
        });
    }

    @Override
    public void showCartItemList(List<CartItem> cartItems) {
        mCartItems.clear();
        mCartItems.addAll(cartItems);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateUI() {
        Cart cart = CartHelper.getCart();
        txtQuanlity.setText(new StringBuilder("Số lượng " + cart.getTotalQuantity()));
        txtTotal.setText(new StringBuilder("Tổng tiền " + CommonUtils.formatToCurrency(cart.getTotalPrice())));
    }

}
