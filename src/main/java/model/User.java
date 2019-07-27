package model;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String name;
    private String password;
    private Map<Product, Integer> productsInCart;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.productsInCart = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void addAndCountProducts(Product productToAdd) {
        Integer count = productsInCart.get(productToAdd);
        if (count != null) {
            count++;
        } else {
            count = 1;
        }
        productsInCart.put(productToAdd, count);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<Product, Integer> getProductsInCart() {
        return productsInCart;
    }

    public void setProductsInCart(Map<Product, Integer> productsInCart) {
        this.productsInCart = productsInCart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        return productsInCart != null ? productsInCart.equals(user.productsInCart) : user.productsInCart == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (productsInCart != null ? productsInCart.hashCode() : 0);
        return result;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Product product : getProductsInCart().keySet()) {
            sb.append(product + " " + "quantity: " + getProductsInCart().get(product));
        }
        return sb.toString();
    }
}
