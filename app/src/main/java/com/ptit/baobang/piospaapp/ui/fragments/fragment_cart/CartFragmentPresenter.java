package com.ptit.baobang.piospaapp.ui.fragments.fragment_cart;

import com.ptit.baobang.piospaapp.data.cart.Cart;
import com.ptit.baobang.piospaapp.data.cart.CartHelper;
import com.ptit.baobang.piospaapp.data.cart.CartItem;
import com.ptit.baobang.piospaapp.data.model.Product;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartFragmentPresenter  extends BasePresenter implements ICartFragmentPresenter{

    private ICartFragmentView mView;

    public CartFragmentPresenter(ICartFragmentView mICartFragmentView) {
        this.mView = mICartFragmentView;
    }

    @Override
    public void loadData() {
        List<CartItem> cartItems = getCartItems();
        mView.showCartItemList(cartItems);
        mView.updateUI();
    }

    @Override
    public void addCartItem(CartItem cartItem) {
        Cart cart = CartHelper.getCart();
        cart.add(cartItem.getProduct(), 1);
        mView.updateUI();
        mView.showCartItemList(getCartItems());
    }

    @Override
    public void removeCartItem(CartItem cartItem) {
        Cart cart = CartHelper.getCart();
        try {
            cart.remove(cartItem.getProduct(), 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mView.updateUI();
        mView.showCartItemList(getCartItems());
    }

    public List<CartItem> getCartItems() {

        List<CartItem> cartItems = new ArrayList<>();
        Cart cart = CartHelper.getCart();
        Map<Product, Integer> itemMap = cart.getItemWithQuantity();

        for (Map.Entry<Product, Integer> entry : itemMap.entrySet()) {
            CartItem cartItem = new CartItem();
            cartItem.setProduct((Product) entry.getKey());
            cartItem.setQuanlity(entry.getValue());
            cartItems.add(cartItem);
        }
        return cartItems;
    }
}
