package com.ptit.baobang.piospaapp.data.cart;

import com.ptit.baobang.piospaapp.data.model.Product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Cart implements Serializable{
    Map<Product, Integer> cartItemMaps = new HashMap<>();
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

    /**
     * Remove all products from this shopping cart
     */
    public void clear() {
        cartItemMaps.clear();
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

    /**
     * Get a map of products to their quantities in the shopping cart
     *
     * @return A map from product to its quantity in this shopping cart
     */
    public Map<Product, Integer> getItemWithQuantity() {
        Map<Product, Integer> cartItemMap = new HashMap<Product, Integer>();
        cartItemMap.putAll(this.cartItemMaps);
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
