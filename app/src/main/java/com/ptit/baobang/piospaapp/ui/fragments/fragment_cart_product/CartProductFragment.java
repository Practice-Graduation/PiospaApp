package com.ptit.baobang.piospaapp.ui.fragments.fragment_cart_product;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ptit.baobang.piospaapp.R;
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

//    @BindView(R.id.txtQuanlity)
//    TextView txtQuanlity;
//    @BindView(R.id.txtTotal)
//    TextView txtTotal;

//    @BindView(R.id.rvCart)
//    RecyclerView rvCart;
//    ShoppingCartProductAdapter mAdapter;
//    List<CartProductItem> mCartItems;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart_product, container, false);
        mUnBinder = ButterKnife.bind(this, view);
        addControls(view);
        return view;
    }

    private void addControls(View view) {
        mPresenter = new CartProductFragmentPresenter(this);
        mItems = new ArrayList<>();
        mAdapter = new ShoppingCartProductAdapter(getContext(), mItems);
        rvProducts.setLayoutManager(new LinearLayoutManager(getContext()));
        rvProducts.setAdapter(mAdapter);
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
    public void showCartItemList(List<CartProductItem> cartItems) {
        mItems.clear();
        mItems.addAll(cartItems);
        mAdapter.notifyDataSetChanged();
    }

    public void setCartFragment(CartFragment cartFragment) {
        this.cartFragment = cartFragment;
    }
}
