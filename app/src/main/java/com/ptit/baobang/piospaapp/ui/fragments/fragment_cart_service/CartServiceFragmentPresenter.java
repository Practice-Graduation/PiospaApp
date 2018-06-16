package com.ptit.baobang.piospaapp.ui.fragments.fragment_cart_service;

import com.ptit.baobang.piospaapp.data.cart.BookingItem;
import com.ptit.baobang.piospaapp.data.cart.Cart;
import com.ptit.baobang.piospaapp.data.cart.CartHelper;
import com.ptit.baobang.piospaapp.data.cart.CartServicePriceItem;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartServiceFragmentPresenter extends BasePresenter implements ICartServiceFragmentPresenter {

    private ICartServiceFragmentView mView;

    CartServiceFragmentPresenter(ICartServiceFragmentView mView) {
        this.mView = mView;
    }

    @Override
    public void loadData() {
        List<CartServicePriceItem> cartItems = getCartItems();
        mView.showCartItemList(cartItems);
    }

    @Override
    public void addCartItem(CartServicePriceItem cartItem) {
        Cart cart = CartHelper.getCart();
        cart.add(cartItem.getBookingItem(), 1);
        mView.showCartItemList(getCartItems());
    }

    @Override
    public void removeCartItem(CartServicePriceItem cartItem) {
        Cart cart = CartHelper.getCart();
        try {
            cart.remove(cartItem.getBookingItem(), 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mView.showCartItemList(getCartItems());
    }

    private List<CartServicePriceItem> getCartItems() {
        List<CartServicePriceItem> cartItems = new ArrayList<>();
        Cart cart = CartHelper.getCart();

        Map<BookingItem, Integer> itemMap = cart.getItemWithQuantityServices();

        for (Map.Entry<BookingItem, Integer> entry : itemMap.entrySet()) {
            CartServicePriceItem cartItem = new CartServicePriceItem();
            cartItem.setBookingItem( entry.getKey());
            cartItem.setNumberCustomer(entry.getValue());
            cartItems.add(cartItem);
        }
        return cartItems;
    }
}
