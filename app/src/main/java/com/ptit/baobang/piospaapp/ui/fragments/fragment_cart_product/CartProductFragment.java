package com.ptit.baobang.piospaapp.ui.fragments.fragment_cart_product;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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

public class CartProductFragment extends BaseFragment<CartProductFragmentPresenter> implements ICartProductFragmentView {

    @BindView(R.id.layout_empty)
    LinearLayout layoutEmpty;

    @BindView(R.id.rvProducts)
    RecyclerView rvProducts;
    ShoppingCartProductAdapter mAdapter;
    private List<CartProductItem> mItems;

    private CartFragment cartFragment;

    private SearchView searchView;
    public CartProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
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
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControls();
        addEvents();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart_product, container, false);
        mUnBinder = ButterKnife.bind(this, view);
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

    private void addControls() {
        mPresenter = new CartProductFragmentPresenter(this);
        mItems = new ArrayList<>();
        mAdapter = new ShoppingCartProductAdapter(getContext(), mItems);
        rvProducts.setLayoutManager(new LinearLayoutManager(getContext()));
        rvProducts.setAdapter(mAdapter);
        mPresenter.loadData();
    }

    @Override
    public void showCartItemList(List<CartProductItem> cartItems) {
        mItems.clear();
        mItems.addAll(cartItems);
        mAdapter.notifyDataSetChanged();
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
