package com.ptit.baobang.piospaapp.data.cart;

import android.util.Log;

import com.ptit.baobang.piospaapp.data.model.Product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Cart implements Serializable{
    Map<Product, Integer> cartItemMaps = new HashMap<>();
    Map<BookingItem, Integer> cartServiceItems = new HashMap<>();
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private int totalQuantity = 0;

    /**
     * Add a quantity of a certain {@link Product} product to this shopping cart
     *
     * @param product the product will be added to this shopping cart
     */
    private Product containsProduct(Product product){

        for (Map.Entry<Product, Integer> entry : cartItemMaps.entrySet()) {
            Product key =  entry.getKey();
            if(key.compareTo(product) == 0){
                return key;
            }
        }

        return  null;
    }

    private BookingItem containsBookingItem(BookingItem bookingItem){

        for (Map.Entry<BookingItem, Integer> entry : cartServiceItems.entrySet()) {
            BookingItem key =  entry.getKey();
            if(key.compareTo(bookingItem) == 0){
                return key;
            }
        }

        return  null;
    }

    public void add(final Product product, int quantity) {

        Product key = containsProduct(product);
        if (key != null) {
            cartItemMaps.put(key, cartItemMaps.get(key) + quantity);
        } else {
            cartItemMaps.put(product, quantity);
        }

        totalPrice = totalPrice.add(BigDecimal.valueOf(quantity).multiply(BigDecimal.valueOf(product.getPrice())));
        totalQuantity += quantity;
    }

    public void add(final BookingItem bookingItem, int quantity) {

        BookingItem key = containsBookingItem(bookingItem);
        if (key != null) {
            cartServiceItems.put(key, cartServiceItems.get(key) + quantity);
            Log.e("CART", key.getServicePrice().getServicePriceId() + "-" + (cartServiceItems.get(key) + quantity));
        } else {
            cartServiceItems.put(bookingItem, quantity);
            Log.e("CART", bookingItem.getServicePrice().getServicePriceId() + "-" + quantity);

        }

        totalPrice = totalPrice.add(BigDecimal.valueOf(quantity).multiply(BigDecimal.valueOf(bookingItem.getServicePrice().getAllPrice())));
        totalQuantity += quantity;
    }

    /**
     * Set new quantity for a {@link Product} product in this shopping cart
     *
     * @param product the product which quantity will be updated
     * @param quantity the new quantity will be assigned for the product
     */
    public void update(final Product product, int quantity) throws Exception {
        Product key = containsProduct(product);
        if (key == null){
            throw  new Exception("Product not found");
        }
        if (quantity < 0)
            throw new Exception(quantity + " is not a valid quantity. It must be non-negative.");

        int productQuantity = cartItemMaps.get(key);
        BigDecimal productPrice = BigDecimal.valueOf(product.getPrice()).multiply(BigDecimal.valueOf(productQuantity));

        cartItemMaps.put(key, quantity);

        totalQuantity = totalQuantity - productQuantity + quantity;
        totalPrice = totalPrice.subtract(productPrice).add(BigDecimal.valueOf(product.getPrice()).multiply(BigDecimal.valueOf(quantity)));
    }

    public void update(final BookingItem bookingItem, int quantity) throws Exception {
        BookingItem key = containsBookingItem(bookingItem);
        if (key == null){
            throw  new Exception("Product not found");
        }
        if (quantity < 0)
            throw new Exception(quantity + " is not a valid quantity. It must be non-negative.");

        int productQuantity = cartServiceItems.get(key);
        BigDecimal servicePrices = BigDecimal.valueOf(bookingItem.getServicePrice().getAllPrice()).multiply(BigDecimal.valueOf(productQuantity));

        cartServiceItems.put(key, quantity);

        totalQuantity = totalQuantity - productQuantity + quantity;
        totalPrice = totalPrice.subtract(servicePrices).add(BigDecimal.valueOf(bookingItem.getServicePrice().getAllPrice()).multiply(BigDecimal.valueOf(quantity)));
    }

    /**
     * Remove a certain quantity of a {@link Product} product from this shopping cart
     *
     * @param product the product which will be removed
     * @param quantity the quantity of product which will be removed
     */
    public void remove(final Product product, int quantity) throws Exception {
        Product key = containsProduct(product);
        if (key == null) throw new Exception();

        int productQuantity = cartItemMaps.get(key);

        if (quantity < 0 || quantity > productQuantity)
            throw new Exception(quantity + " is not a valid quantity. It must be non-negative and less than the current quantity of the product in the shopping cart.");

        if (productQuantity == quantity) {
            cartItemMaps.remove(key);
        } else {
            cartItemMaps.put(key, productQuantity - quantity);
        }

        totalPrice = totalPrice.subtract(BigDecimal.valueOf(product.getPrice()).multiply(BigDecimal.valueOf(quantity)));
        totalQuantity -= quantity;
    }

    public void remove(final BookingItem bookingItem, int quantity) throws Exception {
        BookingItem key = containsBookingItem(bookingItem);
        if (key == null) throw new Exception();

        int serviceQuantity = cartServiceItems.get(key);

        if (quantity < 0 || quantity > serviceQuantity)
            throw new Exception(quantity + " is not a valid quantity. It must be non-negative and less than the current quantity of the product in the shopping cart.");

        if (serviceQuantity == quantity) {
            cartServiceItems.remove(key);
        } else {
            cartServiceItems.put(key, serviceQuantity - quantity);
        }

        totalPrice = totalPrice.subtract(BigDecimal.valueOf(bookingItem.getServicePrice().getAllPrice()).multiply(BigDecimal.valueOf(quantity)));
        totalQuantity -= quantity;
    }

    /**
     * Remove a certain quantity of a {@link Product} product from this shopping cart
     *
     * @param product the product which will be removed
     */
    public void remove(final Product product) throws Exception {
        Product key = containsProduct(product);
        if (key == null)  throw new Exception();

        int quantity = cartItemMaps.get(key);
        cartItemMaps.remove(key);
        totalPrice = totalPrice.subtract(BigDecimal.valueOf(product.getPrice()).multiply(BigDecimal.valueOf(quantity)));
        totalQuantity -= quantity;
    }

    public void remove
            (final BookingItem bookingItem) throws Exception {
        BookingItem key = containsBookingItem(bookingItem);
        if (key == null)  throw new Exception();

        int quantity = cartServiceItems.get(key);
        cartServiceItems.remove(key);
        totalPrice = totalPrice.subtract(BigDecimal.valueOf(bookingItem.getServicePrice().getAllPrice()).multiply(BigDecimal.valueOf(quantity)));
        totalQuantity -= quantity;
    }

    /**
     * Remove all products from this shopping cart
     */
    public void clear() {
        cartItemMaps.clear();
        cartServiceItems.clear();
        totalPrice = BigDecimal.ZERO;
        totalQuantity = 0;
    }

    /**
     * Get quantity of a {@link Product} product in this shopping cart
     *
     * @param product the product of interest which this method will return the quantity
     * @return The product quantity in this shopping cart
     * @throws Exception if the product is not found in this shopping cart
     */
    public int getQuantity(final Product product) throws Exception {
        Product key = containsProduct(product);
        if (key == null) throw new Exception();
        return cartItemMaps.get(key);
    }

    public int getQuantity(final BookingItem bookingItem) throws Exception {
        BookingItem key = containsBookingItem(bookingItem);
        if (key == null) throw new Exception();
        return cartServiceItems.get(key);
    }

    /**
     * Get total cost of a {@link Product} product in this shopping cart
     *
     * @param product the product of interest which this method will return the total cost
     * @return Total cost of the product
     * @throws Exception if the product is not found in this shopping cart
     */
    public BigDecimal getCost(final Product product) throws Exception {
        Product key = containsProduct(product);
        if (key == null) throw new Exception();
        return BigDecimal.valueOf(key.getPrice()).multiply(BigDecimal.valueOf(cartItemMaps.get(key)));
    }

    public BigDecimal getCost(final BookingItem bookingItem) throws Exception {
        BookingItem key = containsBookingItem(bookingItem);
        if (key == null) throw new Exception();
        return BigDecimal.valueOf(key.getServicePrice().getAllPrice()).multiply(BigDecimal.valueOf(cartItemMaps.get(key)));
    }

    /**
     * Get total price of all products in this shopping cart
     *
     * @return Total price of all products in this shopping cart
     */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    /**
     * Get total quantity of all products in this shopping cart
     *
     * @return Total quantity of all products in this shopping cart
     */
    public int getTotalQuantity() {
        return totalQuantity;
    }

    /**
     * Get set of products in this shopping cart
     *
     * @return Set of {@link Product} products in this shopping cart
     */
    public Set<Product> getProducts() {
        return cartItemMaps.keySet();
    }

    public Set<BookingItem> getServicePrices() {
        return cartServiceItems.keySet();
    }

    /**
     * Get a map of products to their quantities in the shopping cart
     *
     * @return A map from product to its quantity in this shopping cart
     */
    public Map<Product, Integer> getItemWithQuantityProduct() {
        Map<Product, Integer> cartItemMap = new HashMap<>();
        cartItemMap.putAll(this.cartItemMaps);
        return cartItemMap;
    }

    public Map<BookingItem, Integer> getItemWithQuantityServices() {
        Map<BookingItem, Integer> cartItemMap = new HashMap<>();
        cartItemMap.putAll(this.cartServiceItems);
        return cartItemMap;
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        for (Map.Entry<Product, Integer> entry : cartItemMaps.entrySet()) {
            strBuilder.append(String.format("Product: %s, Unit Price: %f, Quantity: %d%n", entry.getKey().getProductName(), entry.getKey().getPrice(), entry.getValue()));
        }
        strBuilder.append(String.format("Total Quantity: %d, Total Price: %f", totalQuantity, totalPrice));

        return strBuilder.toString();
    }
}
