package com.ptit.baobang.piospaapp.ui.fragments.fragment_cart_product;

import com.ptit.baobang.piospaapp.data.cart.Cart;
import com.ptit.baobang.piospaapp.data.cart.CartHelper;
import com.ptit.baobang.piospaapp.data.cart.CartProductItem;
import com.ptit.baobang.piospaapp.data.model.Product;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartProductFragmentPresenter extends BasePresenter implements ICartProductFragmentPresenter {

    private ICartProductFragmentView mView;

    CartProductFragmentPresenter(ICartProductFragmentView mICartFragmentView) {
        this.mView = mICartFragmentView;
    }

    @Override
    public void loadData() {
        List<CartProductItem> cartItems = getCartItems();
        mView.showCartItemList(cartItems);
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
