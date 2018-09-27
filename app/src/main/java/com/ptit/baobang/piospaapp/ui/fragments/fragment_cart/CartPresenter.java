package com.ptit.baobang.piospaapp.ui.fragments.fragment_cart;

import android.content.Context;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.cart.Cart;
import com.ptit.baobang.piospaapp.data.cart.CartHelper;
import com.ptit.baobang.piospaapp.data.cart.CartProductItem;
import com.ptit.baobang.piospaapp.data.model.Product;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CartPresenter extends BasePresenter implements ICartPresenter {
    private ICartView mView;
    private Context mContext;

    public CartPresenter(Context mContext, ICartView mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void clickPayment() {
        Cart cart = CartHelper.getCart();
        if (cart.getTotalQuantity() == 0) {
            mView.showMessage(mContext.getString(R.string.message), mContext.getString(R.string.cart_empty_message), SweetAlertDialog.WARNING_TYPE);
            return;
        }
        mView.openPaymentActivity();
    }

    @Override
    public void addCartItem(CartProductItem cartItem) {
        Cart cart = CartHelper.getCart();
        cart.add(cartItem.getProduct(), 1);
        mView.showCartItemList(getCartItems());
    }

    @Override
    public void removeCartItem(CartProductItem cartItem) {
        Cart cart = CartHelper.getCart();
        try {
            cart.remove(cartItem.getProduct(), 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mView.showCartItemList(getCartItems());
    }

    @Override
    public void loadData() {
        List<CartProductItem> cartItems = getCartItems();
        mView.showCartItemList(cartItems);
    }

    private List<CartProductItem> getCartItems() {

        List<CartProductItem> cartItems = new ArrayList<>();
        Cart cart = CartHelper.getCart();
        Map<Product, Integer> itemMap = cart.getItemWithQuantityProduct();

        for (Map.Entry<Product, Integer> entry : itemMap.entrySet()) {
            CartProductItem cartItem = new CartProductItem();
            cartItem.setProduct(entry.getKey());
            cartItem.setQuanlity(entry.getValue());
            cartItems.add(cartItem);
        }
        return cartItems;
    }
}
